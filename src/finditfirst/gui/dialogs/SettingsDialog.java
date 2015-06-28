package finditfirst.gui.dialogs;

import finditfirst.gui.panels.OptionPanel;
import finditfirst.main.Settings;

import javax.swing.*;
import java.awt.*;

/** Opens a Dialog that allows
 * changes to overall
 * application settings.
 *@see {@link Settings}
 */
public class SettingsDialog extends JDialog
{
	private JTextField txtEmail;
	
	private JCheckBox chckIncluded;
	private JCheckBox chckExcluded;
	
	private JRadioButton rdbtnNewOnly;
	private JRadioButton rdbtnNewAndOld;
	
	private JComboBox comboSite;
	
	private Settings settings = Settings.getInstance();
	
	public SettingsDialog()
	{
		init();
		loadSettings();
	}
	
	private void init()
	{
		this.getContentPane().setLayout(null);
		this.setBounds(160, 160, 640, 480);
		this.setTitle("Settings");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		JLabel lblEmail = new JLabel("Email to receive notifications:");
		txtEmail = new JTextField("youremail@address.com");
		chckIncluded = new JCheckBox("Always use Included Seller List");
		chckExcluded = new JCheckBox("Always use Excluded Seller List");
		JLabel lblSite = new JLabel("Preferred Site:");
		comboSite = new JComboBox(OptionPanel.SITES.keySet().toArray());
		JLabel lblNewSearch = new JLabel("When starting a new search / changing an existing one:");
		rdbtnNewOnly = new JRadioButton("Only show new listings");
		rdbtnNewOnly.setEnabled(false);
		rdbtnNewOnly.setSelected(true);
		rdbtnNewAndOld = new JRadioButton("Show new listings plus existing listings that match the search");
		rdbtnNewAndOld.setEnabled(false);
		rdbtnNewAndOld.setSelected(true);
		JButton btnSave = new JButton("Save");
		JButton btnClose = new JButton("Close");
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewAndOld);
		bg.add(rdbtnNewOnly);
		
		lblEmail.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblSite.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewSearch.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		lblSite.setBounds(10, 168, 113, 14);
		chckIncluded.setBounds(10, 71, 374, 23);
		chckExcluded.setBounds(10, 97, 374, 23);
		comboSite.setBounds(133, 166, 111, 20);
		lblEmail.setBounds(10, 11, 234, 16);
		txtEmail.setBounds(244, 10, 140, 20);
		lblNewSearch.setBounds(10, 220, 399, 14);
		rdbtnNewOnly.setBounds(10, 246, 213, 23);
		rdbtnNewAndOld.setBounds(10, 272, 399, 23);
		btnSave.setBounds(430, 408, 89, 23);
		btnClose.setBounds(529, 408, 89, 23);
		
		txtEmail.setColumns(10);
		comboSite.setSelectedIndex(0);
		
		chckIncluded.addChangeListener(e ->
		{
            if(chckIncluded.isSelected())
            {
                chckExcluded.setSelected(false);
            }
        });
		
		chckExcluded.addChangeListener(e ->
		{
            if(chckExcluded.isSelected())
            {
                chckIncluded.setSelected(false);
            }
        });
		
		btnSave.addActionListener(e ->
		{
            saveSettings();
            SettingsDialog.this.dispose();
        });
		
		btnClose.addActionListener(e -> SettingsDialog.this.dispose());
		
		this.getContentPane().add(lblEmail);
		this.getContentPane().add(txtEmail);
		this.getContentPane().add(chckIncluded);
		this.getContentPane().add(chckExcluded);
		this.getContentPane().add(lblSite);
		this.getContentPane().add(comboSite);
		this.getContentPane().add(lblNewSearch);
		this.getContentPane().add(rdbtnNewOnly);
		this.getContentPane().add(rdbtnNewAndOld);
		this.getContentPane().add(btnSave);
		this.getContentPane().add(btnClose);
		
		this.setVisible(true);
	}
	
	/** Creates dialog state from {@link Settings} */
	private void saveSettings()
	{
		settings.setSendToEmailAddress(txtEmail.getText());
		settings.setPreferredSite((String) comboSite.getSelectedItem());
		settings.setUseIncludedSellerList(chckIncluded.isSelected());
		settings.setUseExcludedSellerList(chckExcluded.isSelected());
		settings.setNewResultsOnly(rdbtnNewOnly.isSelected());
		
		settings.save();
	}
	
	/** Saves dialog state to {@link Settings} */
	private void loadSettings()
	{
		txtEmail.setText(settings.getSendToEmailAddress());
		chckIncluded.setSelected(settings.isUseIncludedSellerList());
		chckExcluded.setSelected(settings.isUseExcludedSellerList());
		comboSite.setSelectedItem(settings.getPreferredSite());
		
		if(settings.isNewResultsOnly())
		{
			rdbtnNewOnly.setSelected(true);
		}
		else
		{
			rdbtnNewAndOld.setSelected(true);
		}
	}
}
