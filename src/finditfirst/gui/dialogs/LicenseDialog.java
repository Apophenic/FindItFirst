package finditfirst.gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class LicenseDialog extends JDialog
{
	private static final String INFO_TEXT = "https://github.com/Apophenic\r\n\r\n"
			+ "Copyright (C) 2015 Justin Dayer\r\n\r\n"
			+ "This program is free software; you can redistribute it and/or\r\n"
			+ "modify it under the terms of the GNU General Public License\r\n"
			+ "as published by the Free Software Foundation; either version 2\r\n"
			+ "of the License, or (at your option) any later version.";
		
	public LicenseDialog()
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setTitle("About FindItFirst");
		this.setResizable(false);
		
		JTextArea infoText = new JTextArea(INFO_TEXT);
		infoText.setEditable(false);
		infoText.setBackground(UIManager.getColor("Button.background"));
		infoText.setFont(new Font("Verdana", Font.PLAIN, 11));
		infoText.setBounds(0, 0, 459, 272);
		this.getContentPane().add(infoText);
		
		this.setBounds(100, 100, 400, 200);
		this.setVisible(true);
	}

}
