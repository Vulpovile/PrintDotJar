package com.androdome.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class PageSetup extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;		
	JCheckBox chckbxIsGreebar = new JCheckBox("Greenbar");

	JButton btnCancel = new JButton("Cancel");
	JButton btnAccept = new JButton("Accept");
	private final JPanel contentPanel = new JPanel();
	private JTextField txtRows;
	private JTextField txtColumns;
	private JTextField txtDPP;
	WordProcessorFrame wpf;
	public PageSetup(WordProcessorFrame wordProcessorFrame) {
		wpf = wordProcessorFrame;
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 305, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChooseOrEnter = new JLabel("Enter your paper parameters");
		lblChooseOrEnter.setBounds(10, 11, 202, 14);
		contentPanel.add(lblChooseOrEnter);
		
		chckbxIsGreebar.setBounds(10, 32, 194, 23);
		chckbxIsGreebar.setSelected(wpf.greenbar);
		contentPanel.add(chckbxIsGreebar);
		
		txtRows = new JTextField(wpf.rows + "");
		txtRows.setBounds(10, 84, 86, 23);
		contentPanel.add(txtRows);
		txtRows.setColumns(10);
		
		JLabel lblRows = new JLabel("Rows");
		lblRows.setBounds(10, 62, 46, 14);
		contentPanel.add(lblRows);
		
		JLabel lblColumns = new JLabel("Columns");
		lblColumns.setBounds(106, 62, 46, 14);
		contentPanel.add(lblColumns);
		
		txtColumns = new JTextField(wpf.cols + "");
		txtColumns.setColumns(10);
		txtColumns.setBounds(106, 84, 86, 23);
		contentPanel.add(txtColumns);
		
		txtDPP = new JTextField(wpf.dotsperpage + "");
		txtDPP.setColumns(10);
		txtDPP.setBounds(202, 84, 86, 23);
		contentPanel.add(txtDPP);
		
		JLabel lblDotsPerPage = new JLabel("Dots Per Page");
		lblDotsPerPage.setBounds(202, 62, 86, 14);
		contentPanel.add(lblDotsPerPage);
		
		btnCancel.setActionCommand("Cancel");
		btnCancel.setBounds(214, 138, 74, 23);
		contentPanel.add(btnCancel);

		btnAccept.setActionCommand("Cancel");
		btnAccept.setBounds(139, 138, 65, 23);
		contentPanel.add(btnAccept);
		btnAccept.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAccept)
		{
			try{
				int rows = Integer.parseInt(txtRows.getText().trim());
				int cols = Integer.parseInt(txtColumns.getText().trim());
				int dpp = Integer.parseInt(txtDPP.getText().trim());
				if(rows <= 0)
				{
					JOptionPane.showMessageDialog(null, "You must at least one row!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(cols < 12)
				{
					JOptionPane.showMessageDialog(null, "You must at least 12 columns!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(dpp <= 0)
				{
					JOptionPane.showMessageDialog(null, "You must at least one dot per page!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				wpf.rows = rows;
				wpf.cols = cols;
				wpf.dotsperpage = dpp;
				wpf.greenbar = this.chckbxIsGreebar.isSelected();
				FormattableTextArea textArea = new FormattableTextArea();
				textArea.setOpaque(false);
				textArea.setBackground(new Color(0, 0, 0, 0));
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				textArea.setFont(new Font("Courier New", Font.PLAIN, 13));
				textArea.setColumns(cols);
				textArea.setBorder(wpf.textArea.getBorder());
				wpf.textArea.getParent().add(textArea);
				wpf.textArea.getParent().remove(wpf.textArea);
				textArea.setText(wpf.textArea.getText());
				textArea.setFormat(wpf.textArea.getFormat());
				textArea.getDocument().addDocumentListener(wpf.myChange);
				wpf.textArea = textArea;
				wpf.repaint();
				wpf.validate();
				dispose();
			}catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "That is not a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else dispose();
	}
}
