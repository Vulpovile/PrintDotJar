package com.androdome.print;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MatrixMaker extends JDialog implements KeyListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtX;
	private JTextField txtY;
	private int x = 2, y = 2;
	private JPanel panelMatrix = new JPanel();
	private JTextField[][] txtMatrix;
	private JTextField txtSpac;

	/**
	 * Create the dialog.
	 */
	public MatrixMaker(final WordProcessorFrame main) {
		this.setModal(true);
		setBounds(100, 100, 322, 293);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		JLabel label = new JLabel("Enter matrix values:");
		panel.add(label);
		JPanel panelSize = new JPanel();
		panel.add(panelSize, BorderLayout.EAST);

		txtX = new JTextField();
		txtX.addKeyListener(this);
		panelSize.add(txtX);
		txtX.setColumns(2);

		JLabel lblX = new JLabel("x");
		panelSize.add(lblX);

		txtY = new JTextField();
		txtY.addKeyListener(this);
		panelSize.add(txtY);
		txtY.setColumns(2);

		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		scrollPane.setViewportView(panelMatrix);
		panelMatrix.setLayout(new GridLayout(x, y, 0, 0));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try
				{
					int spacing = Integer.parseInt(txtSpac.getText().trim());
					if(spacing < 0)
					{
						JOptionPane.showMessageDialog(null, "Your spacing cannot be less than 0!", "No!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String text = getTextMatrix(spacing);
					main.textArea.insert(text, main.textArea.getCaretPosition());
					dispose();
				} catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "You have entered an invalid number!", "No!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		txtX.setText(String.valueOf(x));
		txtY.setText(String.valueOf(y));
		
		JLabel lblSpacing = new JLabel("Spacing");
		panelSize.add(lblSpacing);
		
		txtSpac = new JTextField();
		txtSpac.setText("1");
		txtSpac.setColumns(2);
		panelSize.add(txtSpac);
		genMatrix();
	}

	public void keyTyped(KeyEvent e) {
		try
		{
			x = Integer.parseInt(txtX.getText().trim());
			y = Integer.parseInt(txtY.getText().trim());
			if (x < 1 || y < 1)
			{
				if (x < 1)
					x = 1;
				if (y < 1)
					y = 1;
				JOptionPane.showMessageDialog(null, "No!", "No!", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (Exception ex)
		{
			System.out.println("invalid");
			return;
		}
		genMatrix();
	}

	private int[] getColSize(int spacing) {
		int[] sz = new int[txtMatrix.length];
		for (int j = 0; j < y; j++)
		{
			for (int i = 0; i < x; i++)
			{
				if(i == 0)
					sz[i] = Math.max(sz[i], txtMatrix[i][j].getText().length());
				else sz[i] = Math.max(sz[i], txtMatrix[i][j].getText().length() + spacing);
			}
		}
		for (int i = 0; i < sz.length; i++)
		{
			System.out.println(sz[i]);
		}
		return sz;
	}

	private String getTextMatrix(int spacing) {
		String retString = "";
		int[] sizes = getColSize(spacing);
		for (int j = 0; j < y; j++)
		{
			if (j == 0)
				retString += '┌';
			else if (j == y - 1)
				retString += '└';
			else retString += '│';

			for (int i = 0; i < x; i++)
			{
				//System.out.println("Column has " + sizes[i] + "spacing for " + txtMatrix[i][j].getText());
				String str = "";
				for (int k = 0; k < sizes[i] - txtMatrix[i][j].getText().length(); k++)
				{
					str += ' ';
				}
				retString += str + txtMatrix[i][j].getText();
			}
			if (j == 0)
				retString += '┐';
			else if (j == y - 1)
				retString += '┘';
			else retString += '│';
			retString += '\n';
		}
		return retString;
	}

	private void genMatrix() {
		panelMatrix.removeAll();
		panelMatrix.setLayout(new GridLayout(y, x, 0, 0));
		JTextField[][] tmpMatrix = new JTextField[x][y];
		for (int ySize = 0; ySize < y; ySize++)
		{
			for (int xSize = 0; xSize < x; xSize++)
			{
				tmpMatrix[xSize][ySize] = new JTextField();
				tmpMatrix[xSize][ySize].setPreferredSize(new Dimension(50, 30));
				if (txtMatrix != null && xSize < txtMatrix.length - 1 && txtMatrix.length > 0 && ySize < txtMatrix[0].length - 1)
					tmpMatrix[xSize][ySize].setText(txtMatrix[xSize][ySize].getText());
				panelMatrix.add(tmpMatrix[xSize][ySize]);
				/*final String xy = xSize + ", " + ySize;
				tmpMatrix[xSize][ySize].addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent a) {
						System.out.println(xy);
					}
				});*/
			}
		}
		txtMatrix = tmpMatrix;
		panelMatrix.revalidate();
	}

	public void keyPressed(KeyEvent e) {
		keyTyped(e);

	}

	public void keyReleased(KeyEvent e) {
		keyTyped(e);

	}

}
