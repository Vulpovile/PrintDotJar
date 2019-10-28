package com.androdome.print;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.androdome.print.util.PrintUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;

public class PrintSelector extends JDialog implements ListSelectionListener{

	/**
	 * 
	 */
	JList list = new JList();
	JButton btnAdd = new JButton("Add");
	JButton btnRemove = new JButton("Remove");
	private static final long serialVersionUID = 1L;		
	JCheckBox chckbxFullFeedAt = new JCheckBox("Full feed at end");
	JCheckBox chckbxDontFormat = new JCheckBox("Don't format (Draft)");
	private final JPanel contentPanel = new JPanel();
	private JTextField printerName;
	private final JButton setFire = new JButton("Set LPT on fire");
	public PrintSelector(final String print) {
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 424, 383);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChooseOrEnter = new JLabel("Choose or enter your LPT or COM port:");
		lblChooseOrEnter.setBounds(10, 11, 202, 14);
		contentPanel.add(lblChooseOrEnter);
		
		printerName = new JTextField();
		printerName.setText("LPT1");
		printerName.setBounds(10, 36, 202, 32);
		contentPanel.add(printerName);
		printerName.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 202, 211);
		contentPanel.add(scrollPane);
		
		
		list.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"LPT1", "LPT2", "COM1", "COM2", "COM3", "COM4", "/dev/lp0", "/dev/lp1"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		

		btnAdd.setBounds(10, 289, 89, 23);
		contentPanel.add(btnAdd);
		
		btnRemove.setBounds(123, 289, 89, 23);
		contentPanel.add(btnRemove);
		

		chckbxFullFeedAt.setBounds(218, 36, 194, 23);
		contentPanel.add(chckbxFullFeedAt);
		
		chckbxDontFormat.setBounds(218, 62, 194, 23);
		contentPanel.add(chckbxDontFormat);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Print");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PrintUtil.printString(PrintUtil.getFormattedString(print), printerName.getText().trim());
						if(chckbxFullFeedAt.isSelected())
						{
							PrintUtil.printString("\f", printerName.getText().trim());
						}
					}
				});
				setFire.setEnabled(false);
				setFire.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PrintUtil.SetLPTOnFire(printerName.getText().trim());
					}
				});
				setFire.setActionCommand("OK");
				
				buttonPane.add(setFire);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		list.addListSelectionListener(this);
	}
	public void valueChanged(ListSelectionEvent e) {
		printerName.setText(String.valueOf(list.getSelectedValue()));
	}
}
