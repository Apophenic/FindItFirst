package finditfirst.gui.panels;

import finditfirst.ebay.API;
import finditfirst.gui.dialogs.SearchEntryNameDialog;
import finditfirst.gui.table.SearchModel;
import finditfirst.gui.table.SearchTable;
import finditfirst.main.Program;
import finditfirst.searchentry.SearchEntry;
import finditfirst.utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/** Contains a {@link SearchTable} containing
 * all {@link SearchEntry} objects. Also maintains
 * eBay time and allows creation of new
 * {@link SearchEntry}s.
 *
 */
public class SearchPanel extends JPanel
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	/** Displays eBay official time */
	private JLabel lblTime;
	
	/** Date representing eBay official time */
	private Date clock;
	
	private SearchTable table;

	/** Creates a new {@code SearchPanel} */
	public SearchPanel()
	{
		this.setLayout(null);
		init();
		initClock();
		createSaveTimer();
	}
	
	private void init()
	{
		table = new SearchTable(new SearchModel(readSavedSearchEntries()));
		JScrollPane scrollPane = new JScrollPane(table);
		JButton btnCreateNew = new JButton("Create New Search");
		JLabel lblEbay = new JLabel("eBay Time:");
		lblTime  = new JLabel("Fetching...");
		
		lblEbay.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblTime.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		table.setBounds(20, 11, 590, 408);
		scrollPane.setBounds(0, 0, 600, 379);
		btnCreateNew.setBounds(10, 390, 155, 23);
		lblEbay.setBounds(10, 626, 81, 14);
		lblTime.setBounds(101, 626, 360, 14);
		
		btnCreateNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				SearchEntryNameDialog dialog = new SearchEntryNameDialog();	// blocking
				String name = dialog.getSearchName();
				dialog.dispose();
				
				SearchEntry entry = new SearchEntry(new OptionPanel(), name);
				table.getModel().getTableEntries().add(entry);
				table.getModel().fireTableRowsInserted(table.getRowCount() - 1, table.getRowCount() - 1);
				table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
			}
		});
		
		this.add(scrollPane);
		this.add(btnCreateNew);
		this.add(lblEbay);
		this.add(lblTime);
	}
	
	/** Calls eBay time and registers
	 * a timer to increment the time
	 * every second.
	 */
	public void initClock()
	{
		clock = API.callEbayTime();
		if(clock == null)
		{
			lblTime.setText("Error fetching eBay Time");
			return;
		}
		
		Timer t = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				clock.setTime(clock.getTime() + 1000);
				lblTime.setText(clock.toString());
			}
		});
		t.start();
	}
	
	/** Creates a timer to serialize the list
	 * of {@link SearchEntry}s that back this
	 * {@link SearchTable}s model.
	 */
	public void createSaveTimer()
	{
		Timer t = new Timer(300000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Utils.serializeToFile(SearchPanel.this.table.getModel().getTableEntries(), Utils.SEARCHES_FILE);
			}
		});
		t.start();
	}
	
	/** Attempts to load any previously saved
	 * {@link SearchEntry}s anytime a new
	 * {@link SearchTable} is created.
	 * @return  List of previously saved Entries.
	 */
	private ArrayList<SearchEntry> readSavedSearchEntries()
	{
		ArrayList<SearchEntry> tableEntries = (ArrayList<SearchEntry>) Utils.readSerializedFile(Utils.SEARCHES_FILE);
		if(tableEntries == null)
		{
			return new ArrayList<SearchEntry>();
		}
		else
		{
			for(SearchEntry entry : tableEntries)
			{
				entry.stop();	// Resets any entries left running on previous close
			}
			return tableEntries;
		}
	}
	
	public SearchTable getSearchTable()
	{
		return table;
	}
}
