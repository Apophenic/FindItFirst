package finditfirst.searchentry;

import finditfirst.ebay.API;
import finditfirst.gui.panels.OptionPanel;
import finditfirst.main.Program;
import finditfirst.searchentry.objects.Frequency;
import finditfirst.searchentry.objects.RunSchedule;
import finditfirst.utilities.Utils;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Represents an individual eBay search.
 * Stores a search's filters, when it should run,
 * how often it should run, if its running,
 * and all previously seen items discovered
 * by this search.
 * <p>
 * Each search should be spun off on its own thread
 * by utilizing the implemented {@code Runnable} interface.
 * @see {@link OptionPanel}
 * @see {@link Frequency}
 * @see {@link RunSchedule}
 */
public class SearchEntry implements Runnable, Serializable
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	private static final Logger LOG = Program.LOG;
	
	public enum Status
	{
		Starting, Started, Pausing, Paused, Stopping, Stopped
	}
	
	/** This panel's state is used to determine the filters used by this search */
	private OptionPanel panel;
	
	private String searchName = "New Search " + UUID.randomUUID().toString().substring(0, 3);
	private Frequency frequency = new Frequency();
	private RunSchedule runSchedule = new RunSchedule();
	private Status status = Status.Stopped;
	
	private String GETRequestURL;
	
	/** Listing IDs return by eBay previously while running this search */
	private ArrayList<String> seenListingIds = new ArrayList<String>();
	
	public SearchEntry(OptionPanel panel)
	{
		this.panel = panel;
	}
	
	/** Creates a new {@code SearchEntry}
	 * @param panel  {@link OptionPanel} used to determine
	 * search filters.
	 * @param searchName  Name of search.
	 */
	public SearchEntry(OptionPanel panel, String searchName)
	{
		this(panel);
		this.setSearchName(searchName);
	}
	
	/** Defines logic on how this search should run
	 * based on its {@code Frequency}
	 * and {@code RunSchedule}
	 */
	@Override
	public void run()
	{
		GETRequestURL = API.buildGETRequestURL(panel);
		LOG.log(Level.FINE, "GET_URL: " + GETRequestURL);
		
		long startTime = runSchedule.getStartTimeInMillis();
		long stopTime = runSchedule.getStopTimeInMillis();
		long lastRunTime = 0;
		
		LOG.log(Level.FINE, "Starting at: " + new Date(startTime));
		LOG.log(Level.FINE, "Stopping at: " + new Date(stopTime));
		
		while(true)
		{
			if(System.currentTimeMillis() < startTime)
			{
				try
				{	
					LOG.log(Level.FINE, "Waiting to start. Starts again at " + new Date(startTime));
					Thread.sleep(startTime - System.currentTimeMillis());
				} 
				catch (InterruptedException e)
				{
					LOG.log(Level.FINE, Thread.currentThread().getName() + " was interrupted.");
					return;
				}
			}
				
			while(System.currentTimeMillis() < stopTime)
			{
				long elapsedTime = System.currentTimeMillis() - lastRunTime;
				if(elapsedTime < frequency.TimeInMilliSeconds)
				{
					try 
					{
						LOG.log(Level.FINER, searchName + ": Runs again in " + (frequency.TimeInMilliSeconds - elapsedTime) / 1000 + " seconds");
						Thread.sleep(frequency.TimeInMilliSeconds - elapsedTime);
						continue;
					} 
					catch (InterruptedException e)
					{
						LOG.log(Level.FINE, Thread.currentThread().getName() + " was interrupted.");
						return;
					}
				}
				
				API.submitSearchQuery(this);
				LOG.log(Level.FINE, searchName + ": query submit successful");
				lastRunTime = System.currentTimeMillis();
			}
			
			startTime = Utils.rollDate(startTime);
			stopTime = Utils.rollDate(stopTime);
		}
	}
	
	/** Creates a new {@code Thread} and passes
	 * this as the {@code Runnable} object,
	 * beginning this search.
	 * 
	 */
	public void start()
	{
		if(status == Status.Started)
		{
			return;
		}
		
		status = Status.Starting;
		if(panel.checkForErrors())
		{
			status = Status.Stopped;
			return;
		}
		
		Thread thread = new Thread(this);
		thread.setName(getSearchName());
		thread.setPriority(Thread.NORM_PRIORITY);
		thread.start();
		
		panel.disable();
		status = Status.Started;
		LOG.log(Level.FINE, searchName + " : Started Search");
	}
	
	public void pause() {}
	
	/** Finds the {@code Thread} currently
	 * executing this search and interrupts it.
	 */
	public void stop()
	{
		if(status == Status.Stopped)
		{
			return;
		}
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for(Thread thread : threadSet)
		{
			if(thread.getName().equalsIgnoreCase(searchName))
			{
				thread.interrupt();
			}
		}
		
		panel.enable();
		status = Status.Stopped;
		LOG.log(Level.FINE, searchName + " : Stopped Search");
	}
	
	public OptionPanel getOptionPanel()
	{
		return panel;
	}
	
	public void setOptionPanel(OptionPanel panel)
	{
		this.panel = panel;
	}
	
	public String getSearchName()
	{
		return searchName;
	}
	
	public void setSearchName(String searchName)
	{
		this.searchName = searchName;
		panel.setSearchName(searchName);
	}
	
	public Frequency getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(Frequency frequency)
	{
		this.frequency = frequency;
	}
	
	public RunSchedule getRunSchedule()
	{
		return runSchedule;
	}
	
	public void setRunSchedule(RunSchedule runSchedule)
	{
		this.runSchedule = runSchedule;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public String getGETRequestURL()
	{
		return GETRequestURL;
	}
	
	public ArrayList<String> getSeenListingIDs()
	{
		return seenListingIds;
	}
	
	public void clearSeenListingIDs()
	{
		seenListingIds.clear();
	}
	
	/** Verifies the chosen {@link Frequency} and
	 * {@link RunSchedule} for this search
	 * are compatible.
	 * @return  True if compatible, false otherwise.
	 */
	public boolean isValidRunScheduleForFrequenecy()	// TODO better implementation ?
	{
		if(Objects.equals(frequency.DisplayName, "Daily") || Objects.equals(frequency.DisplayName, "Weekly")
				|| Objects.equals(frequency.DisplayName, "12 Hours"))
		{
			if(!Objects.equals(runSchedule.StopTime, "Always"))
			{
				return false;
			}
		}
		else
		{
			if(Objects.equals(runSchedule.StopTime, "Always"))
			{
				return false;
			}
		}
		return true;
	}

}
