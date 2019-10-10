package com.androdome.print.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import com.androdome.print.WordProcessorFrame;


public class PrintUtil {
	public static void printString(String str, String LPT) {
		try
		{
			PrintStream printer = new PrintStream(LPT, "CP858");
			printer.println(str);
			printer.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getFormattedString(String print) {
		// TODO Auto-generated method stub
		return getGraphicsSymbol() + print.replace("\\r", "\r").replace("\\\r", "\\r");
	}
	
	public static String getGraphicsSymbol()
	{
		return ((char)0x1b) + "t1";
	}
	
	public static String getItalicsSymbol()
	{
		return ((char)0x1b) + "t0";
	}
	

	
	public static void setGraphics(String LPT)
	{
		try
		{
			PrintStream printer = new PrintStream(LPT, "CP858");
			printer.print(getGraphicsSymbol());
			printer.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void sendSymbol(String LPT, char symbol)
	{
		try
		{
			PrintStream printer = new PrintStream(LPT, "CP858");
			printer.print(symbol + "");
			printer.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getMegaword()
	{
		String text = "";
		String bold = JOptionPane.showInputDialog("Enter text to bold");
		char wantchar = 'H';
		if (bold != null && bold.trim().length() > 0)
		{
			if(bold.length()*11 > WordProcessorFrame.cols)
			{
				if(JOptionPane.showConfirmDialog(null, "The word you are inserting will extend\nbeyond " + WordProcessorFrame.cols + " columns and will be deformed.\n\nAre you sure you want to continue?", "Size Mismatch", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
				{
					return null;
				}
			}
			String chstring = JOptionPane.showInputDialog("Enter character to display");
			if (chstring != null && chstring.trim().length() > 0)
			{
				wantchar = chstring.charAt(0);
			}
			else
			{
				return null;
			}
			for (int x = 0; x < 9; x++)
			{

				String addme = "";
				for (int i = 0; i < bold.length(); i++)
				{
					Character ch = Character.valueOf(bold.charAt(i));
					String[] cline = (String[]) StaticBoldmap.map.get(ch);
					if (cline == null)
					{
						addme += "         ";
					}
					else
					{
						addme += cline[x].replace('H', wantchar);
					}
					if(i < bold.length()-1)
					{
						addme += "  ";
					}
				}
				text += addme + System.getProperty("line.separator");
			}
		}
		else
		{
			return null;
		}
		return text;
	}
}
