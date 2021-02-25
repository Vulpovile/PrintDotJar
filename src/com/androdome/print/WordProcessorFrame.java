package com.androdome.print;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.androdome.print.util.PrintUtil;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import java.awt.GridLayout;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WordProcessorFrame extends JFrame implements ActionListener, MouseListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	FormattableTextArea textArea = new FormattableTextArea();

	JButton btnMakeMatrix = new JButton("Make Matrix");
	public int rows = 66, cols = 80, dotsperpage = 22;
	public boolean greenbar = true;
	JMenuItem mntmSave = new JMenuItem("Save");
	JMenuItem mntmLoad = new JMenuItem("Load");
	JMenuItem mntmPrint = new JMenuItem("Print");
	JMenuItem mntmPageSetup = new JMenuItem("Page Setup");
	JButton btnGenerateMegaword = new JButton("Generate Megaword");
	JMenuItem mntmSaveAs = new JMenuItem("Save As...");
	JMenuItem mntmClose = new JMenuItem("Close");
	JPanel panel_3;
	JSlider slider = new JSlider();
	File file = null;
	String title = PROG_NAME + " - Untitled Document";
	DocChangeListener myChange = new DocChangeListener(this);
	public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2, 0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF, 0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4, 0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2, 0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA, 0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD, 0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502, 0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557, 0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C, 0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566, 0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559, 0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588, 0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0, 0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4, 0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264, 0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A, 0x207F, 0x00B2, 0x25A0, 0x00A0 };
	private static final String PROG_NAME = "LimeWare Printmaster";
	
	public static final char getAscii(int code) {
		if (code >= 0x80 && code <= 0xFF)
		{
			return EXTENDED[code - 0x7F];
		}
		return (char) code;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("file.encoding", "Cp1252");
		boolean found = false;
		try
		{
			LookAndFeelInfo[] feels = UIManager.getInstalledLookAndFeels();
			for (int i = 0; i < feels.length; i++)
			{
				if ("Nimbus".equals(feels[i].getName()))
				{
					found = true;
					UIManager.setLookAndFeel(feels[i].getClassName());
					break;
				}
			}
			if (!found)
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception ex)
				{
				}//welp
		}
		catch (Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception ex)
			{
			}//welp

		}
		WordProcessorFrame frame = new WordProcessorFrame();
		frame.setVisible(true);
	}


	public WordProcessorFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle(title);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					if (file == null)
					{
						saveChooserFile();
					}
					else
					{
						if (!writeFile(file))
							JOptionPane.showMessageDialog(null, "File failed to save! Do you have access to that directory?", "Fail!", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		});
		textArea.getDocument().addDocumentListener(myChange);
		textArea.setOpaque(false);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		textArea.setColumns(cols);
		textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 611);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mnFile.add(mntmSave);

		mnFile.add(mntmSaveAs);
		mnFile.add(mntmLoad);

		mnFile.add(mntmClose);
		mnFile.add(mntmPrint);

		JMenu mnPage = new JMenu("Page");
		menuBar.add(mnPage);

		mnPage.add(mntmPageSetup);

		JMenu mnThemes = new JMenu("Themes");
		menuBar.add(mnThemes);
		LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		if (laf.length > 0)
		{
			for (int i = 0; i < laf.length; i++)
			{
				JMenuItem mntm = new JMenuItem(laf[i].getName());
				final LookAndFeelInfo linf = laf[i];
				mntm.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						try
						{
							UIManager.setLookAndFeel(linf.getClassName());

							SwingUtilities.updateComponentTreeUI(WordProcessorFrame.this);
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
					}

				});
				mnThemes.add(mntm);
			}
		}
		else
		{
			mnThemes.setEnabled(false);
			mnThemes.setToolTipText("No themes are installed");
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);

		toolBar.add(btnGenerateMegaword);
		btnMakeMatrix.addActionListener(this);
		toolBar.add(btnMakeMatrix);
		btnGenerateMegaword.addActionListener(this);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("ASCII Character Map");
		panel_1.add(label, BorderLayout.NORTH);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		scrollPane_1.setViewportView(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setHgap(8);
		panel_4.add(panel_5, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 4, 0, 0));

		for (int i = 0; i < 255; i++)
		{
			JButton btn = new JButton(getAscii(i) + "");
			btn.setToolTipText((int) getAscii(i) + "");
			btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textArea.insert(e.getActionCommand(), textArea.getCaretPosition());
				}

			});
			panel_2.add(btn);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panel_3 = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			void makeVGradient(Graphics g, int inc, int x1, int y1, int x2, int y2) {
				Color col = g.getColor();
				for (int i = Math.min(x1, x2); i < Math.max(x1, x2); i++)
				{
					if (inc < 0)
						col = new Color(Math.max(col.getRed() + inc, 0), Math.max(col.getGreen() + inc, 0), Math.max(col.getBlue() + inc, 0));
					else if (inc > 0)
						col = new Color(Math.min(col.getRed() + inc, 255), Math.min(col.getGreen() + inc, 255), Math.min(col.getBlue() + inc, 255));
					g.setColor(col);
					g.drawLine(i, y1, i, y2);
				}
			}

			public void paintComponent(Graphics g) {
				//TODO clean up this function
				Graphics2D g2d = (Graphics2D) g;
				Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1 }, 0);
				Stroke normal = g2d.getStroke();

				g.setFont(textArea.getFont());
				super.paintComponent(g);
				int center = this.getWidth() / 2;
				int width = textArea.getWidth() + 76;
				g.setFont(this.getFont());
				g.setColor(Color.WHITE);
				g.fillRect(center - width / 2, 0, width, Math.max(this.getHeight(), textArea.getHeight()));
				g.setColor(Color.GRAY.darker());
				int spacing = getLAFSpacing();

				double heightpp = (g.getFontMetrics().getHeight() + spacing) * rows;
				double hppp = heightpp / (double) dotsperpage;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				for (double i = 0; i < Math.max(this.getHeight(), textArea.getHeight()); i += hppp)
				{
					g.setColor(this.getBackground().darker());
					g.fillArc(center - width / 2 + 11, (int) (i + 10), 15, 15, 0, 360);
					g.fillArc(center + width / 2 - (11 + 15), (int) (i + 10), 15, 15, 0, 360);
					g.setColor(Color.GRAY.darker());
					g.drawArc(center - width / 2 + 11, (int) (i + 10), 15, 15, 0, 360);
					g.drawArc(center + width / 2 - (11 + 15), (int) (i + 10), 15, 15, 0, 360);

				}

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				if (greenbar)
				{
					Color cOld = g.getColor();
					g.setColor(new Color(200, 245, 200));

					for (int i = 0; i < Math.max(this.getHeight(), textArea.getHeight()); i += (g.getFontMetrics().getHeight() + spacing) * 2)
					{
						for (int x = 0; x < g.getFontMetrics().getHeight(); x++)
						{
							if (x % 2 == 0)
							{
								g.drawLine(center + textArea.getWidth() / 2 - 5, i + x, center - textArea.getWidth() / 2 + 5, i + x);
							}
						}
					}
					g.setColor(cOld);
				}
				g2d.setStroke(dashed);
				for (double i = 0; i < Math.max(this.getHeight(), textArea.getHeight()); i += hppp * (double) dotsperpage)
				{
					g2d.drawLine(center - width / 2, (int) i - 4, center + width / 2, (int) i - 4);
				}
				g2d.setStroke(normal);
				g.drawLine(center - width / 2 - 1, 0, center - width / 2 - 1, Math.max(this.getHeight(), textArea.getHeight()));
				g.drawLine(center + width / 2 + 1, 0, center + width / 2 + 1, Math.max(this.getHeight(), textArea.getHeight()));
				g2d.setStroke(dashed);
				g.drawLine(center + textArea.getWidth() / 2 + 1, 0, center + textArea.getWidth() / 2 + 1, Math.max(this.getHeight(), textArea.getHeight()));
				g.drawLine(center - textArea.getWidth() / 2 - 1, 0, center - textArea.getWidth() / 2 - 1, Math.max(this.getHeight(), textArea.getHeight()));
				g2d.setStroke(normal);

				g.setColor(this.getBackground());
				makeVGradient(g, -4, center - width / 2 - 1, 0, center - width / 2 - 10, Math.max(this.getHeight(), textArea.getHeight()));
				makeVGradient(g, 4, center + width / 2 + 2, 0, center + width / 2 + 11, Math.max(this.getHeight(), textArea.getHeight()));
			}
		};
		panel_3.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		panel_3.addMouseListener(this);
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		scrollPane.setViewportView(panel_3);
		panel_3.add(textArea);
		slider.setInverted(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textArea.setBorder(new EmptyBorder(slider.getValue(), 10, 10, 10));
			}
		});

		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setMaximum(slider.getHeight());
		slider.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				slider.setMaximum(slider.getHeight());
			}
		});
		contentPane.add(slider, BorderLayout.WEST);
		
		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JSlider slider_1 = new JSlider();
		panel_6.add(slider_1, BorderLayout.EAST);
		mntmSave.addActionListener(this);
		mntmSaveAs.addActionListener(this);
		mntmClose.addActionListener(this);
		mntmLoad.addActionListener(this);
		mntmPrint.addActionListener(this);
		mntmPageSetup.addActionListener(this);
		addWindowListener(this);
	}

	protected int getLAFSpacing() {
		if (UIManager.getLookAndFeel().getName().contains("Windows"))
			return 1;
		return -1;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmSave)
		{
			if (file == null)
			{
				saveChooserFile();
			}
			else
			{
				if (writeFile(this.file))
					//JOptionPane.showMessageDialog(null, "File saved successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
				//else 
					JOptionPane.showMessageDialog(null, "File failed to save! Do you have access to that directory?", "Fail!", JOptionPane.ERROR_MESSAGE);

			}
		}
		if (e.getSource() == mntmSaveAs)
		{
			saveChooserFile();
		}
		else if (e.getSource() == mntmLoad)
		{
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = chooser.getSelectedFile();
					if (!file.exists())
					{
						JOptionPane.showMessageDialog(null, "This file does not exist!", "Fail!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					String line;
					String whole = "";
					while ((line = reader.readLine()) != null)
						whole += line + "\n";
					reader.close();
					this.textArea.setText(whole);
					this.title = PROG_NAME + " - " + file.getName();
					this.file = file;
					this.myChange.setUnchanged();
				}
				catch (IOException e1)
				{

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "File failed to load! Do you have access to that file?", "Fail!", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		else if (e.getSource() == mntmPrint)
		{
			new PrintSelector(textArea.getText()).setVisible(true);
		}
		else if (e.getSource() == btnGenerateMegaword)
		{
			String megaword = PrintUtil.getMegaword(cols);
			if (megaword != null)
			{
				textArea.insert(megaword, textArea.getCaretPosition());
			}
		}
		else if (e.getSource() == mntmPageSetup)
		{
			new PageSetup(this).setVisible(true);
		}
		else if (e.getSource() == btnMakeMatrix)
		{
			new MatrixMaker(this).setVisible(true);
		}
	}

	private boolean saveChooserFile() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File file = chooser.getSelectedFile();
				if (!file.getName().contains("."))
				{
					file = new File(file.getAbsolutePath() + ".txt");
				}
				if (file.exists())
				{
					if (JOptionPane.showConfirmDialog(null, "The file \"" + file.getName() + "\" already exists!\rDo you want to overwrite it?", "Overwrite File?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.NO_OPTION)
					{
						return false;
					}
				}
				else file.createNewFile();
				this.file = file;

				boolean ret = writeFile(file);
				if (!ret)
					//JOptionPane.showMessageDialog(null, "File saved successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "File failed to save! Do you have access to that directory?", "Fail!", JOptionPane.ERROR_MESSAGE);
				return ret;
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null, "File failed to save! Do you have access to that directory?", "Fail!", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		return false;
	}

	private boolean writeFile(File file) {
		System.setProperty("file.encoding", "UTF-8");
		FileOutputStream fs = null;
		try
		{
			fs = new FileOutputStream(file);
			fs.write(textArea.getText().getBytes("UTF-8"));
			fs.close();
			this.title = PROG_NAME + " - " + file.getName();
			this.myChange.setUnchanged();
			return true;
		}
		catch (IOException ex)
		{
			if (fs != null)
				try
				{
					fs.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			return false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == panel_3)
		{
			this.textArea.requestFocus();
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		if(this.myChange.getChanged())
		{
			switch(JOptionPane.showConfirmDialog(null, "Do you want to save your changes before closing?", "Save changes?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE))
			{
				case JOptionPane.YES_OPTION:
					if (file == null)
					{
						if(saveChooserFile())
							dispose();
					}
					else
					{
						if (!writeFile(file))
							JOptionPane.showMessageDialog(null, "File failed to save! Do you have access to that directory?", "Fail!", JOptionPane.ERROR_MESSAGE);
						else dispose();
					}
					break;
				case JOptionPane.NO_OPTION:
					dispose();
					break;
				default:
					break;
			}
		}
		else dispose();
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
