package finditfirst.gui.dialogs;

import finditfirst.gui.panels.OptionPanel;

import javax.swing.*;
import java.awt.*;

/** Creates a dialog that builds more advanced
 * keyword queries based on user specification.
 * Keyword queries are parsed directly into
 * the built GET Request URL, so advanced users
 * can enter their keyword query directly into
 * the field on {@link OptionPanel}.
 */
public class AdvancedKeywordsDialog extends JDialog
{
	/** Label representing how many words of 'n' words a query myst have */
	public static final String[] WORDLIST = {"1 of 2", "2 of 3", "3 of 4", "4 of 5"};
	
	private JTextField txtWords;
	private JTextField txtPhrases;
	private JTextField txtExclude;
	private JTextField txtMustHave;
	private JTextArea txtaWordsList;
	
	private JCheckBox chckWordsList;
	private JCheckBox chckMustHaveWord;
	private JComboBox comboWordsList;

	/** Creates a new {@code AdvancedKeywordsDialog}.
	 * @param panel  {@link OptionPanel} to set
	 * keyword string on.
	 */
	public AdvancedKeywordsDialog(final OptionPanel panel)
	{
		this.getContentPane().setLayout(null);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Advanced Keywords");
		this.setBounds(0, 0, 580, 480);
		
		JLabel lblInclude = new JLabel("Included Words*:");
		JLabel lblPhrases = new JLabel("Included Phrases:");
		JLabel lblWords = new JLabel("Excluded Words:");
		txtWords = new JTextField();
		txtPhrases = new JTextField();
		txtExclude = new JTextField();
		txtMustHave = new JTextField();
		chckWordsList = new JCheckBox("Must have");
		comboWordsList = new JComboBox(WORDLIST);
		JLabel lblOfFollowing = new JLabel("of the following words:");
		chckMustHaveWord = new JCheckBox("Must ALWAYS have this word:");
		txtaWordsList = new JTextArea();
		txtaWordsList.setRows(5);
		txtaWordsList.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JLabel lblNotes = new JLabel("NOTES");
		JLabel lblNotes1 = new JLabel("Included/Excluded Words: Seperate words with commas. No spaces");
		JLabel lblNotes2 = new JLabel("Included Phrases: Seperate words with spaces, seperate phrases with commas");
		JLabel lblNotes3 = new JLabel("Word List: One word per line. No spaces or punctuation");
		JLabel lblNotes4 = new JLabel("*This is the same as using the simplified keyword field. Words here");
		JLabel lblNotes5 = new JLabel("will sometimes be ignored by eBay to expand search results.");
		JButton btnDone = new JButton("Done");
		
		lblInclude.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPhrases.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblWords.setFont(new Font("Verdana", Font.PLAIN, 14));
		chckWordsList.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblOfFollowing.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNotes.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNotes1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNotes2.setFont(new Font("Verdana", Font.PLAIN, 12));
		chckMustHaveWord.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNotes3.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNotes4.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNotes5.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		lblInclude.setBounds(10, 11, 131, 20);
		lblPhrases.setBounds(10, 42, 131, 20);
		lblWords.setBounds(10, 73, 131, 20);
		txtWords.setBounds(150, 13, 234, 20);
		txtPhrases.setBounds(151, 44, 233, 20);
		txtExclude.setBounds(151, 75, 233, 20);
		txtMustHave.setBounds(239, 111, 145, 20);
		chckWordsList.setBounds(10, 148, 101, 23);
		comboWordsList.setBounds(117, 152, 62, 20);
		lblOfFollowing.setBounds(189, 154, 164, 14);
		txtaWordsList.setBounds(117, 185, 220, 120);
		lblNotes.setBounds(10, 320, 62, 14);
		lblNotes1.setBounds(10, 338, 550, 14);
		lblNotes2.setBounds(10, 357, 550, 14);
		chckMustHaveWord.setBounds(10, 109, 223, 23);
		lblNotes3.setBounds(10, 375, 550, 14);
		lblNotes4.setBounds(10, 400, 550, 14);
		lblNotes5.setBounds(10, 417, 550, 14);
		btnDone.setBounds(450, 287, 89, 23);
		
		txtMustHave.setColumns(10);
		txtExclude.setColumns(10);
		txtPhrases.setColumns(10);
		txtWords.setColumns(10);
		
		txtMustHave.setEnabled(false);
		comboWordsList.setEnabled(false);
		txtaWordsList.setEnabled(false);
		
		chckMustHaveWord.addActionListener(e ->
		{
            if(chckMustHaveWord.isSelected())
            {
                txtMustHave.setEnabled(true);
            }
            else
            {
                txtMustHave.setEnabled(false);
            }
        });
		
		chckWordsList.addActionListener(arg0 ->
		{
            if(chckWordsList.isSelected())
            {
                comboWordsList.setEnabled(true);
                txtaWordsList.setEnabled(true);
            }
            else
            {
                comboWordsList.setEnabled(false);
                txtaWordsList.setEnabled(false);
            }
        });
		
		btnDone.addActionListener(arg0 ->
		{
            panel.setKeywords(getBuiltKeywordString());
            AdvancedKeywordsDialog.this.dispose();
        });
		
		this.getContentPane().add(lblInclude);
		this.getContentPane().add(lblPhrases);
		this.getContentPane().add(lblWords);
		this.getContentPane().add(txtWords);
		this.getContentPane().add(txtPhrases);
		this.getContentPane().add(txtExclude);
		this.getContentPane().add(txtMustHave);
		this.getContentPane().add(chckWordsList);
		this.getContentPane().add(comboWordsList);
		this.getContentPane().add(lblOfFollowing);
		this.getContentPane().add(txtaWordsList);
		this.getContentPane().add(lblNotes);
		this.getContentPane().add(lblNotes1);
		this.getContentPane().add(lblNotes2);
		this.getContentPane().add(chckMustHaveWord);
		this.getContentPane().add(lblNotes3);
		this.getContentPane().add(lblNotes4);
		this.getContentPane().add(lblNotes5);
		this.getContentPane().add(btnDone);
		
		this.setVisible(true);
	}
	
	/** Builds the string pased on dialog state.
	 * @return  Advanced keyword query
	 */
	private String getBuiltKeywordString()
	{
		String builtKeywords;
		String includedWords = txtWords.getText();
		String includedPhrases = txtPhrases.getText();
		String excludedWords = txtExclude.getText();
		String musthaveWord = txtMustHave.getText();
		String mustHaveList = txtaWordsList.getText();
		
		includedWords = includedWords.replace(",", " ");
		
		if(!includedPhrases.isEmpty())
		{
			includedPhrases = "\"" + includedPhrases.replace(",", "\" \"") + "\"";
		}
		
		if(!excludedWords.isEmpty())
		{
			excludedWords = "-" + excludedWords.replace(",", " -");
		}
		
		if(!musthaveWord.isEmpty() && chckMustHaveWord.isSelected())
		{
			musthaveWord = "%2B" + musthaveWord;
		}
		else
		{
			musthaveWord = "";
		}
		
		String[] list;
		if(!mustHaveList.isEmpty() && chckWordsList.isSelected())
		{
			list = mustHaveList.split("\n");
			switch(list.length)
			{
				case(1) :
					mustHaveList = list[0];
				case(2) :
					mustHaveList = "(" + list[0] + "," + list[1] + ")";
					break;
				case(3) :
					mustHaveList = "@1 " + list[0] + " " + list[1] + " " + list[2];
					break;
				case(4) :
					mustHaveList = "@2 " + list[0] + " " + list[1] + " " + list[2] + " " + list[3];
					break;
				case(5) :
					mustHaveList = "@3 " + list[0] + " " + list[1] + " " + list[2] + " " + list[3] + " " + list[4];
					break;
			}
		}
		else
		{
			mustHaveList = "";
		}
		
		builtKeywords = includedWords + " " + includedPhrases + " " +  excludedWords + " " +  mustHaveList + " " +  musthaveWord;
		
		return builtKeywords;
	}
}
