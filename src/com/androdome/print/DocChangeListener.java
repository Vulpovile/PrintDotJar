package com.androdome.print;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class DocChangeListener implements DocumentListener {

	WordProcessorFrame frm;
	public DocChangeListener(WordProcessorFrame frm)
	{
		this.frm = frm;
	}
	boolean changed = false;
	public void insertUpdate(DocumentEvent e) {
		setChanged();
	}

	public void removeUpdate(DocumentEvent e) {
		setChanged();
	}

	public void changedUpdate(DocumentEvent e) {
		setChanged();
	}

	public void setChanged() {
		frm.setTitle(frm.title + "*");
		changed = true;
	}

	public void setUnchanged() {
		frm.setTitle(frm.title);
		changed = false;
	}

	public boolean getChanged() {
		// TODO Auto-generated method stub
		return changed;
	}

}
