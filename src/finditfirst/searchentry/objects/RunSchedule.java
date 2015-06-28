package finditfirst.searchentry.objects;

import finditfirst.main.Program;

import java.io.Serializable;
import java.util.Calendar;

/** Determines when a search should
 * start running and stop running,
 * if applicable. Stores a "readable"
 * string and the literal hour
 * for the start and stop times.
 * <p>
 * Note that an hour value of -1
 * will set an indefinite time for
 * starting and / or stopping.
 * @see {@link finditfirst.searchentry.SearchEntry}
 */
public class RunSchedule implements Serializable
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	public String StartTime;
	public String StopTime;
	
	private int startHour;
	private int stopHour;
	
	public RunSchedule()
	{
		this("Always", -1, "Always", -1);
	}
	
	public RunSchedule(String startTime, int startHour, String stopTime, int stopHour)
	{
		this.StartTime = startTime;
		this.startHour = startHour;
		this.StopTime = stopTime;
		this.stopHour = stopHour;
	}
	
	/** Gets what time a search should start
	 * on the current day.
	 * @return   Starting time as {@code Long}
	 */
	public long getStartTimeInMillis()
	{
		if(startHour == -1)
		{
			return Long.MIN_VALUE;
		}
		else 
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, startHour);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTimeInMillis();
		}
	}
	
	/** Gets what time a search should stop
	 * on the current day. If current time is greater
	 * than stop time, the date is rolled to the next day/
	 * @return  Stopping time as {@code Long}
	 */
	public long getStopTimeInMillis()
	{
		if(stopHour == -1)
		{
			return Long.MAX_VALUE;
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			if(stopHour < startHour)
			{
				cal.roll(Calendar.DATE, true);
			}
			
			cal.set(Calendar.HOUR_OF_DAY, stopHour);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTimeInMillis();
		}
	}
}
