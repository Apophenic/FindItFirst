package finditfirst.ebay;

import finditfirst.gui.panels.OptionPanel;
import finditfirst.main.Program;
import finditfirst.searchentry.SearchEntry;
import finditfirst.utilities.MailService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Provides a number of static
 * functions that interface with
 * eBay's RESTful API.
 */
public final class API	//TODO XML parsing / JSON
{
	private static final Logger LOG = Program.LOG;
	
	/** eBay Developer key, gotten from eBay.
	 * Be sure to use the "Production Key,"
	 * and not the "Sandbox Key." You need the AppID.
	 */
	public static final String EBAY_DEV_KEY_STRING		= "";
	
	/** GET requests utilizing eBay's "findItemsAdvanced"
	 * are required to have at least parameters.
	 */
	public static final String GET_REQUEST_TEMPLATE 	= "http://svcs.ebay.com/services/"
														+ "search/FindingService/"
														+ "v1?SECURITY-APPNAME=" + EBAY_DEV_KEY_STRING
														+ "&OPERATION-NAME=findItemsAdvanced"
														+ "&SERVICE-VERSION=1.0.0"
														+ "&RESPONSE-DATA-FORMAT=XML"
														+ "&REST-PAYLOAD"
														+ "&outputSelector=SellerInfo"
														+ "&keywords=";
	
	/** Returns eBay's official time in UTC format  */
	public static final String EBAY_TIME_API_CALL 		= "http://open.api.ebay.com/shopping?callname="
														+ "GeteBayTime&responseencoding=XML&"
														+ "appid=" + EBAY_DEV_KEY_STRING
														+ "&siteid=0&version=533";
	
	private static final Pattern PATTERN_ITEMS = Pattern.compile("(?<=\\<item\\>)((?!\\<\\/item\\>).)*");
	private static final Pattern PATTERN_ITEM_IDS = Pattern.compile("(?<=\\<itemId\\>)[0-9]*(?=\\<\\/itemId\\>)");
	
	/** Used to build GETRequestURLs */
	private static StringBuilder sb;
	private static int filterCounter;
	
	protected API() {}
	
	/** Calls the eBay API and returns
	 * the raw XML.
	 * @param GETRequestURL  The GET URL
	 * @return  eBay XML response
	 * @see {@link #buildGETRequestURL(OptionPanel)}
	 */
	public static synchronized String callAndGetXmlResponse(String GETRequestURL)
	{
		StringBuilder sbb = new StringBuilder();
		try
		{
			URL obj = new URL(GETRequestURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line = br.readLine()) != null)
			{
				sbb.append(line);
			}
		}
		catch (IOException e)
		{
			LOG.log(Level.SEVERE, "Failed to get response from eBay for: " + GETRequestURL);
		}
		return sbb.toString();
	}
	
	/** Submits a GET request to the eBay API,
	 * determines if there are new item results,
	 * and emails them if there are.
	 * @param entry  {@link SearchEntry} used to build
	 * GET request and check item results against
	 * @see {@link MailService#sendResults(String, java.util.Collection)}
	 */
	public static void submitSearchQuery(SearchEntry entry)
	{
		Queue<Listing> listingsToEmail = new LinkedList<Listing>();
		
		String xml = callAndGetXmlResponse(entry.getGETRequestURL());
		Matcher findItems = PATTERN_ITEMS.matcher(xml);
		
		while(findItems.find())
		{
			if(findItems.group().length() != 0)
			{
				String itemXml = findItems.group();
				String itemXmlID = getItemID(itemXml);
				if(!entry.getSeenListingIDs().contains(itemXmlID))
				{
					listingsToEmail.add(new Listing(itemXml));
					entry.getSeenListingIDs().add(itemXmlID);
					LOG.log(Level.FINER, "Added Item ID : " + itemXmlID);
				}
			}
		}
		
		if(!listingsToEmail.isEmpty())
		{
			MailService.sendResults(entry.getSearchName(), listingsToEmail);
		}
		else
		{
			LOG.log(Level.FINEST, "No new results: " + entry.getSearchName());
		}
	}
	
	/** Calls eBay's API for the
	 * official time.
	 * @return  {@code Date} representing eBay time.
	 */
	public static Date callEbayTime()
	{
		Date date = null;
		
		String xml = callAndGetXmlResponse(EBAY_TIME_API_CALL);
		Matcher findTimestamp = Pattern.compile("(?<=\\<Timestamp\\>)((?!\\<\\/Timestamp\\>).)*").matcher(xml);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		if(findTimestamp.find())
		{
			try
			{
				date = sdf.parse(findTimestamp.group());
			}
			catch (ParseException e)
			{
				LOG.log(Level.SEVERE, "Failed to parse eBay time");
			}
		}
		
		return date;
	}
	
	private static String getItemID(String itemXml) {
		Matcher findId = PATTERN_ITEM_IDS.matcher(itemXml);
		
		if(findId.find())
		{
			return findId.group();
		}
		return "";
	}
	
	/** Reads panel state and builds a properly
	 * formatted GET request URL ready to be sent
	 * to eBay's API.
	 * @param panel  Panel to read state from.
	 * @return  GET url.
	 * @see {@link OptionPanel}
	 */
	public static String buildGETRequestURL(OptionPanel panel)
	{
		sb = new StringBuilder();
		filterCounter = 0;
		
		String categoryId = panel.getCategoryId();
		if(categoryId != null && !Objects.equals(categoryId, "ALL"))
		{
			createKeyValueFilter("categoryID", categoryId);
		}
		
		String distance = panel.getDistance();
		if(distance != null)
		{
			createKeyValueFilter("buyerPostalCode" , panel.getZipcode());
			createItemFilter("MaxDistance", distance);
		}
		
		ArrayList<String> condition = panel.getCondition();
		createItemFilter("Condition", condition.toArray());
		
		ArrayList<String> listingTypes = panel.getListingTypes();
		createItemFilter("ListingType", listingTypes.toArray());
		
		String listedIn = panel.getListedIn();
		if(listedIn != null)
		{
			createItemFilter("ListedIn", listedIn);
		}
		
		String feedbackScore = panel.getFeedbackScore();
		if(panel.getFeedbackScoreName().charAt(0) == '>')
		{
			createItemFilter("FeedbackScoreMin", feedbackScore);
		}
		else
		{
			createItemFilter("FeedbackScoreMax", feedbackScore);
		}
		
		String maxPrice = panel.getMaxPrice();
		if(maxPrice != null)
		{
			createItemFilter("MaxPrice", maxPrice);
		}
		
		String minPrice = panel.getMinPrice();
		if(minPrice != null)
		{
			createItemFilter("MinPrice", minPrice);
		}
		
		String locatedIn = panel.getLocatedIn();
		if(locatedIn != null)
		{
			createItemFilter("LocatedIn", locatedIn);
		}
		
		String availableTo = panel.getAvailableTo();
		if(availableTo != null)
		{
			createItemFilter("AvailableTo", availableTo);
		}
		
		boolean isUseExcludedSellers = panel.isUseExcludedSellerList();
		if(isUseExcludedSellers)
		{
			createItemFilter("ExcludeSeller", panel.getExcludedSellers().toArray());
		}
		
		boolean isUseIncludedSellers = panel.isUseIncludedSellerList();
		if(isUseIncludedSellers)
		{
			createItemFilter("Seller", panel.getIncludedSellers().toArray());
		}

		boolean isAuthorizedSeller = panel.isAuthorized();
		if(isAuthorizedSeller)
		{
			createItemFilter("AuthorizedSellerOnly", true);
		}
		
		boolean isBestOffer = panel.isBestOffer();
		if(isBestOffer)
		{
			createItemFilter("BestOfferOnly", true);
		}
		
		boolean isExcludeAutoPay = panel.isNoAutoPay();
		if(isExcludeAutoPay)
		{
			createItemFilter("ExcludeAutoPay", true);
		}
		
		boolean isFeatured = panel.isFeatured();
		if(isFeatured)
		{
			createItemFilter("FeaturedOnly", true);
		}
		
		boolean isFreeShipping = panel.isFreeShipping();
		if(isFreeShipping)
		{
			createItemFilter("FreeShippingOnly", true);
		}
		
		boolean isGetItFast = panel.isGetItFast();
		if(isGetItFast)
		{
			createItemFilter("GetItFastOnly", true);
		}
		
		boolean isLocalPickup = panel.isLocalPickup();
		if(isLocalPickup)
		{
			createItemFilter("LocalPickupOnly", true);
		}
		
		boolean isOutlet = panel.isOutlet();
		if(isOutlet)
		{
			createItemFilter("OutletSellerOnly", true);
		}
		
		boolean isReturnsAccepted = panel.isReturnsAccepted();
		if(isReturnsAccepted)
		{
			createItemFilter("ReturnsAcceptedOnly", true);
		}
		
		boolean isTopRatedSeller = panel.isTopRated();
		if(isTopRatedSeller)
		{
			createItemFilter("TopRatedSellerOnly", true);
		}
		
		/*	String expeditedShippingType =
		if(expeditedShippingType!=null)
		{
			createItemFilter("ExpeditedShippingType", expeditedShippingType);
		} 
		
		String maxBids =
		if(maxBids!=Integer.MIN_VALUE)
		{
			createItemFilter("MaxBids", maxBids);
		}
		
		String maxHandlingTime = 
		if(maxHandlingTime!=Integer.MIN_VALUE)
		{
			createItemFilter("MaxHandlingTime", maxHandlingTime);
		}
		
		String maxQuantity = 
		if(maxQuantity != null)
		{
			createItemFilter("MaxQuantity", maxQuantity);
		}
		
		String minQuantity = 
		if(minQuantity != null)
		{
			createItemFilter("MinQuantity", minQuantity);
		}
		
		String minBids = 
		if(minBids != null)
		{
			createItemFilter("MinBids", minBids);
		}
		
		String modTimeFrom = 
		if(modTimeFrom != null)
		{
			createItemFilter("ModTimeFrom", modTimeFrom);
		}
		
		String startTimeFrom = 
		if(startTimeFrom!=null)
		{
			createItemFilter("StartTimeFrom", startTimeFrom);
		}
		
		String startTimeTo = 
		if(startTimeTo!=null)
		{
			createItemFilter("StartTimeTo", startTimeTo);
		}
		
		String sellerBusinessType = 
		if(sellerBusinessType != null)
		{
			createItemFilter("SellerBusinessType", sellerBusinessType);
		}
		
		boolean isCharityOnly = 
		if(charityOnly)
		{
			createItemFilter("CharityOnly", true);
		}
		
		boolean isHideDuplicates = 
		if(isHideDuplicates)
		{
			createItemFilter("HideDuplicateItems", true);
		}
		
		boolean isLocalSearch = 
		if(localSearchOnly)
		{
			createItemFilter("LocalSearchOnly", true);
		}
		
		boolean isLots = 
		if(isLots)
		{
			createItemFilter("LotsOnly", true);
		}
		
		boolean isSold = 
		if(soldItemsOnly)
		{
			createItemFilter("SoldItemsOnly", true);
		}
		
		boolean isValueBoxInventory = 
		if(valueBoxInventory)
		{
			createItemFilter("ValueBoxInventory", true);
		}
		
		boolean isWorldOfGood = 
		if(worldOfGoodOnly)
		{
			createItemFilter("WorldOfGoodOnly", true);
		} */
		
		return GET_REQUEST_TEMPLATE + panel.getKeywords() + sb.toString();
	}
	
	private static void createKeyValueFilter(String name, String value)
	{
		sb.append("&" + name + "=" + value);
	}
	
	private static void createItemFilter(String name, Object value)
	{
		sb.append("&itemFilter(" + filterCounter + ").name=" + name + "&itemFilter("+ filterCounter++ + ").value=" + value);
	}
	
	private static void createItemFilter(String name, Object[] value)
	{
		sb.append("&itemFilter(" + filterCounter + ").name=" + name);
		
		for(int i = 0; i < value.length; i++)
		{
			sb.append("&itemFilter(" + filterCounter + ").value(" + i + ")=" + value[i]);
		}
		filterCounter++;
	}

}
