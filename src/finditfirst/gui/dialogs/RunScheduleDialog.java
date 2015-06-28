package finditfirst.gui.dialogs;

import finditfirst.main.GUI;
import finditfirst.main.Program;
import finditfirst.searchentry.SearchEntry;
import finditfirst.searchentry.objects.RunSchedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Objects;

/** Opens a dialog that is used to set
 * what time a search should start and stop each
 * day (depending on the frequency of the search).
 */
public class RunScheduleDialog extends JDialog
{
	/** There are three unique ways 
	 * of how a search can run:
	 * <li> {@code Always} - Runs regardless of time
	 * <li> {@code Default} - Has a daily start and stop time
	 * <li> {@code StartOnly} - Begins at a set time, and runs
	 * indefinitely. This is used for larger frequency intervals
	 * (12 hours+).
	 */
	public enum RunMode
	{
		Always, Default, StartOnly
	}
	
	private static final long serialVersionUID = Program.serialVersionUID;
	
	/** Key is for UI display, value is used to determine run schedule */
	public static final LinkedHashMap<String, Integer> TIMES = new LinkedHashMap<String, Integer>()
	{{
		put("Always", -1);
		put("12:00 AM", 0);
		put("1:00 AM", 1);
		put("2:00 AM", 2);
		put("3:00 AM", 3);
		put("4:00 AM", 4);
		put("5:00 AM", 5);
		put("6:00 AM", 6);
		put("7:00 AM", 7);
		put("8:00 AM", 8);
		put("9:00 AM", 9);
		put("10:00 AM", 10);
		put("11:00 AM", 11);
		put("12:00 PM", 12);
		put("1:00 PM", 13);
		put("2:00 PM", 14);
		put("3:00 PM", 15);
		put("4:00 PM", 16);
		put("5:00 PM", 17);
		put("6:00 PM", 18);
		put("7:00 PM", 19);
		put("8:00 PM", 20);
		put("9:00 PM", 21);
		put("10:00 PM", 22);
		put("11:00 PM", 23);
	}};
	
	private JComboBox comboStart;
	private JComboBox comboStop;
	
	private RunMode runMode = RunMode.Default;
	
	/** Creates a new RunScheduleDialog.
	 * @param entry  The {@link SearchEntry} the new
	 * run schedule will be set to.
	 * @see {@link RunSchedule}
	 */
	public RunScheduleDialog(final SearchEntry entry)
	{
		this.getContentPane().setLayout(null);
		this.setTitle(entry.getSearchName());
		this.setBounds(100, 100, 160, 240);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JLabel lblStart = new JLabel("Start Time:");
		JLabel lblStop = new JLabel("Stop Time:");
		JButton btnDone = new JButton("Done");
		
		comboStart = new JComboBox(TIMES.keySet().toArray());
		comboStop = new JComboBox(TIMES.keySet().toArray());
		
		String frequency = entry.getFrequency().DisplayName;
		if(Objects.equals(frequency, "Daily") || Objects.equals(frequency, "Weekly") ||
				Objects.equals(frequency, "12 Hours")) // TODO
		{
			comboStart.removeItemAt(0);
			runMode = RunMode.StartOnly;
		}
		
		btnDone.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(!checkForErrors())
				{
					String startTime = "";
					String stopTime = "";
					switch(runMode)
					{
						case Default :
							startTime = (String) comboStart.getSelectedItem();
							stopTime = (String) comboStop.getSelectedItem();
							break;
						case Always :
							startTime = stopTime = "Always";
							break;
						case StartOnly :
							startTime = (String) comboStart.getSelectedItem();
							stopTime = "Always";
							break;
					}
					
					entry.setRunSchedule(new RunSchedule(startTime, TIMES.get(startTime), stopTime, TIMES.get(stopTime)));
					RunScheduleDialog.this.dispose();
				}
			}
		});
		
		comboStart.addActionListener(e ->
		{
            if(runMode != RunMode.StartOnly)
            {
                if(comboStart.getSelectedItem() == "Always")
                {
                    comboStop.setEnabled(false);
                    runMode = RunMode.Always;
                }
                else
                {
                    comboStop.setEnabled(true);
                    runMode = RunMode.Default;
                }
            }
        });
		
		comboStop.removeItemAt(0);
		comboStop.setEnabled(false);
		
		lblStart.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblStop.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		lblStart.setBounds(25, 10, 96, 20);
		comboStart.setBounds(25, 35, 96, 20);
		lblStop.setBounds(25, 85, 96, 20);
		comboStop.setBounds(25, 110, 96, 20);
		btnDone.setBounds(25, 168, 89, 23);
		
		this.getContentPane().add(lblStart);
		this.getContentPane().add(comboStart);
		this.getContentPane().add(lblStop);
		this.getContentPane().add(comboStop);
		this.getContentPane().add(btnDone);
		
		this.setVisible(true);
	}
	
	/** Make sure start and stop times
	 * are valid before setting.
	 * @return  true if not valid, false otherwise.
	 */
	private boolean checkForErrors()
	{
		if(runMode == RunMode.Default)
		{
			if(comboStart.getSelectedItem() == comboStop.getSelectedItem())
			{
				JOptionPane.showMessageDialog(GUI.frame, "Start and stop times can't be the same",
						"Error", JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}

}
