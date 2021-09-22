package com.flaremicrosystems.printdotjar;

import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FormattableTextArea extends JTextArea {
	private static final long serialVersionUID = 1L;
	private static final byte BOLD = Byte.parseByte("00000001", 2);
	private static final byte ITALIC = Byte.parseByte("00000010", 2);
	private static final byte UNDERLINE = Byte.parseByte("00000100", 2);
	private static final byte SUPER = Byte.parseByte("00001000", 2);
	private static final byte SUB = Byte.parseByte("00010000", 2);
	private String format = "";
	private byte fmt = Byte.parseByte("00000000", 2);

	public FormattableTextArea() {
		super();
		((AbstractDocument) this.getDocument()).setDocumentFilter(new FTADocFilter());
	}

	public String getFormat() {
		// TODO Auto-generated method stub
		return format;
	}

	public void setFormat(String format2) {
		format = format2;

	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}

class FTADocFilter extends DocumentFilter {
	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		/*if (offset >= fb.getDocument().getLength())
		{
			//System.out.println("Added: " + text);
		}
		else
		{
			//String old = fb.getDocument().getText(offset, length);
			//System.out.println("Replaced " + old + " with " + text);
		}*/
		super.replace(fb, offset, length, text, attrs);
	}

	@Override
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
		//System.out.println("Added: " + text);
		super.insertString(fb, offset, text, attr);
	}

	@Override
	public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
		//System.out.println("Removed: " + fb.getDocument().getText(offset, length));
		super.remove(fb, offset, length);
	}
}