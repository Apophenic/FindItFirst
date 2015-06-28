package finditfirst.searchentry.objects;

import finditfirst.main.Program;

import java.io.Serializable;

/** Used to determine how often a search
 * should run. Stores a "readable" string
 * and the time to wait between searches,
 * in milliseconds.
 * @see {@link finditfirst.searchentry.SearchEntry}
 */
public class Frequency implements Serializable
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	public String DisplayName;
	public long TimeInMilliSeconds;
	
	public Frequency()
	{
		this("1 Hour", 3600);
	}
	
	public Frequency(String displayName, int timeInSeconds)
	{
		this.DisplayName = displayName;
		this.TimeInMilliSeconds = timeInSeconds * 1000;
	}

}
