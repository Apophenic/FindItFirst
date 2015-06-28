package finditfirst.gui.table;

import finditfirst.gui.dialogs.RunScheduleDialog;
import finditfirst.gui.panels.OptionPanel;
import finditfirst.gui.table.SearchModel.SearchColumns;
import finditfirst.main.GUI;
import finditfirst.main.Program;
import finditfirst.searchentry.SearchEntry;
import finditfirst.searchentry.SearchEntry.Status;
import finditfirst.utilities.ButtonColumn;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;

/** Stores {@link SearchEntry}s, with additional
 * operations to manipulate said searches.
 *<p>
 * Backed by {@link SearchModel}
 */
public class SearchTable extends JTable
{
	public enum TableAction
	{
		Delete, Clear, Reset;
	}
	
	private static final long serialVersionUID = Program.serialVersionUID;
	
	/** Key is display name, value is frequency in seconds used by {@link SearchEntry} */
	public static final LinkedHashMap<String, Integer> FREQUENCIES = new LinkedHashMap<String, Integer>()
	{{
		put("30 Seconds", 30);
		put("1 Minute", 60);
		put("2 Minutes", 120);
		put("5 Minutes", 300);
		put("10 Minutes", 600);
		put("15 Minutes", 900);
		put("30 Minutes", 1800);
		put("1 Hour", 3600);
		put("6 Hours", 21600);
		put("12 Hours", 43200);
		put("Daily", 86400);
		put("Weekly", 604800);
	}};
	
	private SearchModel model;
	
	private JPopupMenu rightclickmenu = new JPopupMenu();
	
	/** Opens a {@link RunScheduleDialog} for the selected row's {@link SearchEntry} */
	private Action openRunScheduleDialog = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			int selectedrow = SearchTable.this.getSelectedRow();
			SearchEntry entry = SearchTable.this.getModel().getTableEntries().get(selectedrow);
			new RunScheduleDialog(entry);
			SearchTable.this.changeSelection(	// Forces ButtonColumn to render new value
					SearchTable.this.getSelectedRow(), SearchColumns.RunSchedule.getValue(), false, false);
		}
	};

	/** Creates a new {@code SearchTable}
	 * @param model  {@link SearchModel} backing this table.
	 */
	public SearchTable(SearchModel model)
	{
		this.setModel(model);
		
		init();
		initRightClick();
	}
	
	private void init()
	{
		this.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.resizeColumns();
		
		ButtonColumn buttonColumn = new ButtonColumn(this, openRunScheduleDialog, SearchColumns.RunSchedule.getValue());
		JComboBox comboFrequency = new JComboBox(FREQUENCIES.keySet().toArray());
		TableColumn columnFrequency = this.getColumnModel().getColumn(SearchColumns.Frequency.getValue());
		
		columnFrequency.setCellEditor(new DefaultCellEditor(comboFrequency));
	}
	
	private void initRightClick()
	{
		final JMenuItem itemStart = new JMenuItem("Start");
		final JMenuItem itemPause = new JMenuItem("Pause");
		final JMenuItem itemStop = new JMenuItem("Stop");
		final JSeparator separator1 = new JSeparator();
		final JMenuItem itemRename = new JMenuItem("Rename");
		final JMenuItem itemSetFrequency = new JMenuItem("Set Frequency");
		final JMenuItem itemSetRunSchedule = new JMenuItem("Set Run Schedule");
		final JSeparator separator2 = new JSeparator();
		final JMenuItem itemDelete = new JMenuItem("Delete Selected");
		final JMenuItem itemReset = new JMenuItem("Reset to Default");
		final JMenuItem itemClear = new JMenuItem("Clear Previously Seen Listings");
		
		itemStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				changeRunStatus(Status.Started);
			}
		});
		
		itemPause.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeRunStatus(Status.Paused);
			}
		});
		
		itemStop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeRunStatus(Status.Stopped);
			}
		});
		
		itemRename.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchTable.this.editCellAt(SearchTable.this.getSelectedRow(), SearchColumns.SearchName.getValue());
				
		        JTextField editor = (JTextField) ((DefaultCellEditor) SearchTable.this.getCellEditor
		        		(SearchTable.this.getSelectedRow(), SearchColumns.SearchName.getValue())).getComponent();
		        editor.selectAll();
		        editor.requestFocusInWindow();
			}
		});
		
		itemSetFrequency.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchTable.this.editCellAt(SearchTable.this.getSelectedRow(), SearchColumns.Frequency.getValue());
				
		        JComboBox editor = (JComboBox) ((DefaultCellEditor) SearchTable.this.getCellEditor
		        		(SearchTable.this.getSelectedRow(), SearchColumns.Frequency.getValue())).getComponent();
		        editor.showPopup();
			}
		});
		
		itemSetRunSchedule.addActionListener(openRunScheduleDialog);
		
		itemDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				performTableAction(TableAction.Delete);
			}
		});
		
		itemReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				performTableAction(TableAction.Reset);
			}
		});
		
		itemClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				performTableAction(TableAction.Clear);
			}
		});
		
		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int r = SearchTable.this.rowAtPoint(e.getPoint());
		        if (r >= 0 && r < SearchTable.this.getRowCount())
		        {
		        	int[] selected = SearchTable.this.getSelectedRows();
		        	
		        	if(selected.length > 1)
		        	{
		        		itemRename.setVisible(false);
		        		itemSetFrequency.setVisible(false);
		        		itemSetRunSchedule.setVisible(false);
		        		separator1.setVisible(false);
		        	}
		        	else 
		        	{
		        		itemRename.setVisible(true);
		        		itemSetFrequency.setVisible(true);
		        		itemSetRunSchedule.setVisible(true);
		        		separator1.setVisible(true);
		        	}
		        	
		        	if(model.getTableEntries().get(r).getStatus() != Status.Stopped)
		        	{
		        		itemRename.setEnabled(false);	// Disable editing running searches
		        		itemSetFrequency.setEnabled(false);
		        		itemSetRunSchedule.setEnabled(false);
		        	}
		        	else
		        	{
		        		itemRename.setEnabled(true);
		        		itemSetFrequency.setEnabled(true);
		        		itemSetRunSchedule.setEnabled(true);
		        	}
		        	
		        	for(int current : selected)
		        	{
		        		if(r == current)
		        		{
		        			return;
		        		}
		        	}
		        	SearchTable.this.setRowSelectionInterval(r, r);
		        }
		        else
		        {
		        	SearchTable.this.clearSelection();
		        }
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					rightclickmenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
		});
		
		rightclickmenu.add(itemStart);
		rightclickmenu.add(itemPause);
		rightclickmenu.add(itemStop);
		rightclickmenu.add(separator1);
		rightclickmenu.add(itemRename);
		rightclickmenu.add(itemSetFrequency);
		rightclickmenu.add(itemSetRunSchedule);
		rightclickmenu.add(separator2);
		rightclickmenu.add(itemDelete);
		rightclickmenu.add(itemReset);
		rightclickmenu.add(itemClear);
	}
	
	/** Calls the corresponding status
	 * method in {@link SearchEntry} for the selected
	 * table entries.
	 * @param status  Status to change the entry to.
	 */
	private void changeRunStatus(Status status)
	{
		int[] selectedrows = this.getSelectedRows();
		for(int currentrow : selectedrows)
		{	
			SearchEntry entry = this.getModel().getTableEntries().get(currentrow);
			
			switch (status)
			{
				case Started :
					entry.start();
					break;
				case Paused :
					entry.pause();
					break;
				case Stopped :
					entry.stop();
					break;
				default:
					break;
			}
			
			model.fireTableCellUpdated(currentrow, SearchColumns.Status.getValue());
		}
	}
	
	/** Confirms the user wishes to commit
	 * a table action, and peforms the
	 * selected action.
	 * @param action  Action to perform.
	 * @see {@link TableAction}
	 */
	private void performTableAction(TableAction action)
	{
		int n;
		if(this.getSelectedRowCount() == 1)
		{
			n = JOptionPane.showConfirmDialog(GUI.frame, action.toString() + " '" 
					+ model.getValueAt(this.getSelectedRow(), SearchColumns.SearchName.getValue()) + "'?");
		}
		else
		{
			n = JOptionPane.showConfirmDialog(GUI.frame, action.toString() + " all selected searches? (Started searches WON'T be effected)");
		}
		
		if(n == JOptionPane.OK_OPTION)
		{
			int[] selectedrows = this.getSelectedRows();
			for(int i = selectedrows.length - 1; i >= 0; i--)	// Backwards iteration for Delete (concurrent removal)
			{
				SearchEntry entry = this.getModel().getTableEntries().get(selectedrows[i]);
				if(entry.getStatus() == Status.Stopped)
				{
					switch (action)
					{
						case Delete:
							this.getModel().getTableEntries().remove(entry);
							this.getModel().fireTableRowsDeleted(selectedrows[i], selectedrows[i]);
							break;
						case Clear :
							entry.clearSeenListingIDs();
							break;
						case Reset :
							entry.setOptionPanel(new OptionPanel());
							break;
						default:
							break;
					}
				}
			}
		}
		
		this.clearSelection();
	}
	
	public SearchModel getModel()
	{
		return model;
	}
	
	public void setModel(SearchModel model)
	{
		this.model = model;
		super.setModel(model);
	}
	
	/** Forces the tables columns to an optimal size layout */
	public void resizeColumns()
	{
		this.getColumnModel().getColumn(0).setPreferredWidth(275);
		this.getColumnModel().getColumn(1).setPreferredWidth(80);
		this.getColumnModel().getColumn(2).setPreferredWidth(135);
		this.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	
}
