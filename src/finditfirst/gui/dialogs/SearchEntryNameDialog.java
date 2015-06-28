package finditfirst.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Dialog prompt thrown when a new {@link finditfirst.searchentry.SearchEntry}
 * is created. This will set the new entry's name.
 * It's highly recommened to use unique names for each
 * {@code SearchEntry}, otherwise there will be
 * conflicting behavior.
 */
public class SearchEntryNameDialog extends JDialog
{
	private JTextField txtName;
	
	/** Creates a new {@code SearchEntryDialog}
	 * <p>
	 * {@link #getSearchName()} must be called once this
	 * dialog closes to get the inputted name.
	 * This dialog also needs to be disposed
	 * of manually.
	 */
	public SearchEntryNameDialog()
	{
		this.getContentPane().setLayout(null);
		this.setTitle("Enter Search Name");
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 210, 160);
		
		txtName = new JTextField();
		txtName.setBounds(10, 57, 170, 20);
		this.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name for New Search:");
		lblName.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblName.setBounds(10, 25, 178, 14);
		getContentPane().add(lblName);
		
		final JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(e -> SearchEntryNameDialog.this.setVisible(false));
		btnOk.setBounds(51, 88, 89, 23);
		getContentPane().add(btnOk);
		
		txtName.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnOk.doClick();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		this.setVisible(true);
	}
	
	public String getSearchName()
	{
		return txtName.getText();
	}
}
