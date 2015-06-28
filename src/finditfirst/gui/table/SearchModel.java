package finditfirst.gui.table;

import finditfirst.gui.dialogs.RunScheduleDialog;
import finditfirst.main.Program;
import finditfirst.searchentry.SearchEntry;
import finditfirst.searchentry.SearchEntry.Status;
import finditfirst.searchentry.objects.Frequency;
import finditfirst.searchentry.objects.RunSchedule;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/** The {@link AbstractTableModel} that backs
 * {@link SearchTable}.
 * @see {@link SearchEntry}
 */
public class SearchModel extends AbstractTableModel
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	/** Enum for columns used in this model (value represents literal column #)*/
	public enum SearchColumns
	{
		SearchName(0), Frequency(1), RunSchedule(2), Status(3);
		
		private final int value;
		
		private SearchColumns(final int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
		
		public static SearchColumns getEnum(int i)
		{
			for(SearchColumns clmn : SearchColumns.values())
			{
				if(clmn.getValue() == i)
				{
					return clmn;
				}
			}
			return null;
		}
	}
	
	public static final String[] HEADERS = {"Search Name", "Frequency", "Run Time", "Status"};
	
	/** List that backs this model */
	private ArrayList<SearchEntry> tableEntries;
	
	public SearchModel(ArrayList<SearchEntry> tableEntries)
	{
		this.tableEntries = tableEntries;
	}

	@Override
	public int getColumnCount()
	{
		return HEADERS.length;
	}
	
	@Override
	public String getColumnName(int column)
	{
		return HEADERS[column];
	}
	
	@Override
	public Class getColumnClass(int column)
	{
		return String.class;
	}

	@Override
	public int getRowCount()
	{
		return tableEntries.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if(tableEntries.get(rowIndex).getStatus() != Status.Stopped)
		{
			return false;	// Search MUST be stopped for it to be editable.
		}
		
		SearchColumns column = SearchColumns.getEnum(columnIndex);
		switch(column)
		{
			case SearchName :
			case Frequency :
			case RunSchedule :
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		SearchColumns column = SearchColumns.getEnum(columnIndex);
		SearchEntry entry = tableEntries.get(rowIndex);
		switch(column)
		{
			case SearchName :
				entry.setSearchName((String) aValue);
				break;
			case Frequency :
				entry.setFrequency(new Frequency((String) aValue, SearchTable.FREQUENCIES.get(aValue)));
				if(!entry.isValidRunScheduleForFrequenecy())
				{
					new RunScheduleDialog(entry);	// Force RunSchedule change; TODO better implementation ?
				}
			case RunSchedule :
				// See RunScheduleDialog
				break;
			default :
				break;
		}
		
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int clmnIndex)
	{
		SearchColumns column = SearchColumns.getEnum(clmnIndex);
		SearchEntry entry = tableEntries.get(rowIndex);
		switch(column)
		{
			case SearchName :
				return entry.getSearchName();
			case Frequency :
				return entry.getFrequency().DisplayName;
			case RunSchedule :
				RunSchedule runSchedule = entry.getRunSchedule();
				if(runSchedule.StartTime.equalsIgnoreCase("Always")  || runSchedule.StopTime.equalsIgnoreCase("Always"))
				{
					return runSchedule.StartTime;	// Returns different format when stopTime isn't required
				}
				else
				{
					return entry.getRunSchedule().StartTime + " - " + entry.getRunSchedule().StopTime;
				}
			case Status :
				return entry.getStatus().toString();
			default :
				return null;
		}
	}
	
	public ArrayList<SearchEntry> getTableEntries()
	{
		return tableEntries;
	}

}
