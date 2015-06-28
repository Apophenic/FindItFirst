package finditfirst.gui.dialogs;

import finditfirst.main.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/** Used to add / remove
 * included and / or excluded sellers
 * to API calls. These lists
 * are maintained in {@code Settings}.
 *@see {@link Settings}
 */
public abstract class AbstractSellersDialog extends JDialog
{
	protected JTextArea txtaSellers;
	
	protected JLabel lblDialogType;
	protected JLabel lblSellerInfo;
	
	protected ArrayList<String> sellers;
	
	/** Creates a new SellersDialog.
	 * @param sellers  List of sellers,
	 * either included or excluded.
	 */
	public AbstractSellersDialog(ArrayList<String> sellers)
	{
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 640, 480);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Edit eBay Sellers");
		this.sellers = sellers;
		
		txtaSellers = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(txtaSellers);
		
		lblDialogType = new JLabel();
		lblSellerInfo = new JLabel();
		JLabel lblNotes2 = new JLabel("One seller name per line");
		JLabel lblNotes3 = new JLabel("100 names maximum (eBay limit)");
		JButton btnDone = new JButton("Done");
		
		lblDialogType.setFont(new Font("Verdana", Font.PLAIN, 24));
		lblSellerInfo.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNotes2.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNotes3.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		scrollPane.setBounds(10, 11, 190, 420);
		lblDialogType.setBounds(210, 17, 315, 32);
		lblSellerInfo.setBounds(210, 73, 404, 23);
		lblNotes2.setBounds(210, 110, 404, 23);
		lblNotes3.setBounds(210, 144, 404, 23);
		btnDone.setBounds(525, 408, 89, 23);
		
		btnDone.addActionListener(e ->
		{
            String[] list = txtaSellers.getText().split("\n");
            if(list.length > 100)
            {
                JOptionPane.showMessageDialog(new JFrame(), "Too many seller names: "
						+ list.length, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!txtaSellers.getText().isEmpty())
            {
                AbstractSellersDialog.this.sellers.clear();

				Collections.addAll(AbstractSellersDialog.this.sellers, list);
            }

            Settings.getInstance().save();

            AbstractSellersDialog.this.dispose();
        });
		
		this.getContentPane().add(scrollPane);
		this.getContentPane().add(lblDialogType);
		this.getContentPane().add(lblSellerInfo);
		this.getContentPane().add(lblNotes2);
		this.getContentPane().add(lblNotes3);
		this.getContentPane().add(btnDone);
		
		loadSellerList();
		
		this.setVisible(true);
	}
	
	private void loadSellerList()
	{
		StringBuilder sb = new StringBuilder();
		if(sellers.isEmpty())
		{
			return;
		}
		else
		{
			for(String seller : sellers)
			{
				sb.append(seller + "\n");
			}
		}
		txtaSellers.setText(sb.toString());
	}
	
}
