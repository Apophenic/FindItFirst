package finditfirst.main;

import finditfirst.utilities.Utils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/** Singleton class maintains
 * application settings, as well as
 * lists of sellers that should be included
 * and sellers that should be excluded from
 * searches that enable either
 * of these options.
 */
public class Settings implements Serializable
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	public static final File SETTINGS_FILE = new File("settings.ini");
	
	/** Email address to send notifications to */
	private String toEmailAddress							= "";
	
	private String prefSite									= "eBay.com";
	
	private boolean isUseIncluded							= false;
	private boolean isUseExcluded							= false;
	
	private boolean isNewResultsOnly						= false;	// TODO
	
	private ArrayList<String> includedSellers = new ArrayList<String>();
	private ArrayList<String> excludedSellers = new ArrayList<String>();
	
	private static Settings settings;
	
	protected Settings() {}
	
	public static Settings getInstance()
	{
		if(settings == null)
		{
			settings = new Settings();
		}
		return settings;
	}
	
	public void save()
	{
		Utils.serializeToFile(this, SETTINGS_FILE);
	}
	
	public static void load()
	{
		Object obj = Utils.readSerializedFile(SETTINGS_FILE);
		if(obj != null
				&& obj instanceof Settings)
		{
			settings = (Settings) obj;
		}
	}
	
	public String getSendToEmailAddress()
	{
		return toEmailAddress;
	}
	
	public void setSendToEmailAddress(String toEmailAddress)
	{
		this.toEmailAddress = toEmailAddress;
	}
	
	public String getPreferredSite()
	{
		return prefSite;
	}
	
	public void setPreferredSite(String site)
	{
		this.prefSite = site;
	}
	
	public boolean isUseIncludedSellerList()
	{
		return isUseIncluded;
	}
	
	public void setUseIncludedSellerList(boolean bool)
	{
		this.isUseIncluded = bool;
	}
	
	public boolean isUseExcludedSellerList()
	{
		return isUseExcluded;
	}
	
	public void setUseExcludedSellerList(boolean bool)
	{
		this.isUseExcluded = bool;
	}
	
	public boolean isNewResultsOnly()
	{
		return isNewResultsOnly;
	}
	
	public void setNewResultsOnly(boolean bool)
	{
		this.isNewResultsOnly = bool;
	}
	
	public ArrayList<String> getIncludedSellerList()
	{
		return includedSellers;
	}
	
	public ArrayList<String> getExcludedSellerList()
	{
		return excludedSellers;
	}

}
