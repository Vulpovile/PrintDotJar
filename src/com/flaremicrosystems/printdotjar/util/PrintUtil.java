package com.flaremicrosystems.printdotjar.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;


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
		return getGraphicsSymbol() + print.replace('\r', '\0').replace("\0", "").replace("\\r", "\r");
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
	public static void SetLPTOnFire(String LPT)
	{
		try
		{
			
			@SuppressWarnings("resource")
			PrintStream printer = new PrintStream(LPT, "CP858");
			printer.print(getGraphicsSymbol());
			for(;;)
			{
				printer.print("\u2588");
			}
			//printer.close();
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
	
	
	public static String getMegaword(int cols)
	{
		String text = "";
		String bold = JOptionPane.showInputDialog("Enter text for the Megaword").toUpperCase();
		char wantchar = 'H';
		if (bold != null && bold.trim().length() > 0)
		{
			if(bold.length()*11 > cols)
			{
				if(JOptionPane.showConfirmDialog(null, "The word \""+bold+"\" will extend\nbeyond " + cols + " columns and will be deformed.\n\nAre you sure you want to continue?", "Size Mismatch", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
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
