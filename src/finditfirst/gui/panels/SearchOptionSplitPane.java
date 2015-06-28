package finditfirst.gui.panels;

import finditfirst.gui.table.SearchModel;
import finditfirst.gui.table.SearchTable;
import finditfirst.searchentry.SearchEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/** Custom {@code JSplitPane} that stores
 * a {@link SearchPanel} on the left component,
 * and a {@link OptionPanel} on the right component.
 * <p>
 * A selection listener is registered for the
 * {@link SearchTable} contained within
 * the {@code SearchPanel}. This way anytime a
 * different {@link SearchEntry} is selected on the table,
 * its' corresponding {@code OptionPanel} will
 * be loaded on this object's right component.
 */
public class SearchOptionSplitPane extends JSplitPane
{
	private OptionPanel loadedPanel;
	
	/** Creates a new SearchOptionsSplitPane */
	public SearchOptionSplitPane()
	{
		super(JSplitPane.HORIZONTAL_SPLIT, new SearchPanel(), null);
		this.setBounds(0, 20, 1264, 662);
		this.setDividerLocation(600);
		
		final SearchTable table;
		if(this.getLeftComponent() instanceof SearchPanel)
		{
			table = ((SearchPanel) this.getLeftComponent()).getSearchTable();
		}
		else
		{
			table = new SearchTable(new SearchModel(new ArrayList<SearchEntry>()));
		}
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int selectedRow = table.getSelectedRow();
				if(selectedRow < 0 || table.getSelectedRowCount() > 1)
				{
					setLoadedOptionPanel(null);
					return;
				}
				else
				{
					setLoadedOptionPanel(table.getModel().getTableEntries().get(selectedRow).getOptionPanel());
				}
			}
		});
	}
	
	/** Loads the {@link OptionPanel} from the
	 * selected {@link SearchEntry} onto this
	 * object's right component.
	 * @param panel  Panel to load.
	 */
	public void setLoadedOptionPanel(OptionPanel panel)
	{
		loadedPanel = panel;
		this.setRightComponent(loadedPanel);
		this.setDividerLocation(600);
	}

}
