package finditfirst.main;

import finditfirst.gui.dialogs.LicenseDialog;
import finditfirst.gui.dialogs.SettingsDialog;
import finditfirst.gui.panels.SearchOptionSplitPane;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/** User Interface class.
 * Provides logic for saving and
 * accessing {@code Settings}.
 */
public class GUI
{
	/** {@code JFrame} on which all components reside */
	public static JFrame frame;
	
	public GUI()
	{
		init();
	}
	
	private void init()
	{
		frame = new JFrame();
		frame.setTitle("FindItFirst - eBay Search Automation");
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		JMenuItem mnitemSave = new JMenuItem("Save");
		JMenuItem mnitemExit = new JMenuItem("Exit");
		JMenuItem mnitemSettings = new JMenuItem("Settings");
		JMenuItem mnitemAbout = new JMenuItem("About FindItFirst");
		
		SearchOptionSplitPane splitPane = new SearchOptionSplitPane();
		
		frame.addWindowListener(new WindowListener()
		{
			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e)
			{
				Settings.getInstance().save();
				frame.dispose();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});
		
		mnitemSave.addActionListener(e ->
		{
            Settings.getInstance().save();	// TODO also need to save searches.lst
        });
		
		mnitemExit.addActionListener(arg0 ->
		{
            Settings.getInstance().save();
            frame.dispose();
        });
		
		mnitemSettings.addActionListener(e -> new SettingsDialog());
		
		mnitemAbout.addActionListener(e -> new LicenseDialog());
		
		frame.setBounds(100, 100, 1280, 720);
		menu.setBounds(0, 0, 1264, 20);
		
		menuFile.add(mnitemSave);
		menuFile.add(mnitemExit);
		menuHelp.add(mnitemSettings);
		menuHelp.add(mnitemAbout);
		menu.add(menuFile);
		menu.add(menuHelp);
		
		frame.getContentPane().add(menu);
		frame.getContentPane().add(splitPane);
	}
	
}
