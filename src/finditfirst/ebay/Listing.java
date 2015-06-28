package finditfirst.ebay;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parses XML retrieved from eBay's API.
 * This object is used to construct result entries
 * sent via email.
 */

public class Listing	// TODO
{	
	private String itemID					= 			"null";
	private String listingTitle				=			"null";
	private String globalID					=			"null";
	private String categoryID				=			"null";
	private String categoryName				=			"null";
	private int feedbackScore				=			Integer.MIN_VALUE;
	private double feedbackPercent			=			Integer.MIN_VALUE;
	private String galleryURL				=			"null";
	private String viewItemURL				=			"null";
	private String productIDType			=			"null";
	private String productID				=			"null";
	private String paymentMethod			=			"null";
	private boolean autoPay;
	private String postalCode				=			"null";
	private String location					=			"null";
	private String country					=			"null";
	private String shippingType				=			"null";
	private String shipToLocations			=			"null";
	private String sellerName				=			"null";
	private boolean expeditedShipping;
	private boolean oneDayShipping;
	private int handlingTime				=				0;
	private String currencyID				=			"null";
	private double currentPrice				=			Integer.MIN_VALUE;
	private String sellingState				=			"null";
	private String timeLeft					=			"null";
	private boolean bestOfferEnabled;
	private boolean buyItNow;
	private Date startTime;
	private Date endTime;
	private String listingType				=			"null";
	private boolean gift;
	private boolean returnsAccepted;
	private String conditionID				=			"null";
	private String conditionName			=			"null";
	private boolean isMultiVariationListing;
	private boolean topRatedSeller;
	
	protected Pattern getItemId = Pattern.compile("(?<=\\<itemId\\>)[0-9]*(?=\\<\\/itemId\\>)");
	protected Pattern getTitle = Pattern.compile("(?<=\\<title\\>)((?!\\<\\/title\\>).)*");
	protected Pattern getGlobalId = Pattern.compile("(?<=\\<globalId\\>)((?!\\<\\/globalId\\>).)*");
	protected Pattern getCategoryId = Pattern.compile("(?<=\\<categoryId\\>)((?!\\<\\/categoryId\\>).)*");
	protected Pattern getCategoryName = Pattern.compile("(?<=\\<categoryName\\>)((?!\\<\\/categoryName\\>).)*");
	protected Pattern getGalleryUrl = Pattern.compile("(?<=\\<galleryURL\\>)((?!\\<\\/galleryURL\\>).)*");
	protected Pattern getViewItemUrl = Pattern.compile("(?<=\\<viewItemURL\\>)((?!\\<\\/viewItemURL\\>).)*");
	protected Pattern getProductIdType = Pattern.compile("(?<=\\<productId type\\=\")((?!\").)*");
/***/	protected Pattern getProductId = Pattern.compile("(?<=\\<productId type\\=\"ReferenceID\"\\>)((?!\\<\\/productId\\>)[0-9])*");
	protected Pattern getPaymentMethod = Pattern.compile("(?<=\\<paymentMethod\\>)((?!\\<\\/paymentMethod\\>).)*");
	protected Pattern getAutoPay = Pattern.compile("(?<=\\<autoPay\\>)((?!\\<\\/autoPay\\>).)*");
	protected Pattern getPostalCode = Pattern.compile("(?<=\\<postalCode\\>)((?!\\<\\/postalCode\\>).)*");
	protected Pattern getLocation = Pattern.compile("(?<=\\<location\\>)((?!\\<\\/location\\>).)*");
	protected Pattern getCountry = Pattern.compile("(?<=\\<country\\>)((?!\\<\\/country\\>).)*");
	protected Pattern getShippingType = Pattern.compile("(?<=\\<shippingType\\>)((?!\\<\\/shippingType\\>).)*");
	protected Pattern getShipToLocations = Pattern.compile("(?<=\\<shipToLocations\\>)((?!\\<\\/shipToLocations\\>).)*");
	protected Pattern getExpeditedShipping = Pattern.compile("(?<=\\<expeditedShipping\\>)((?!\\<\\/expeditedShipping\\>).)*");
	protected Pattern getOneDayShipping = Pattern.compile("(?<=\\<oneDayShippingAvailable\\>)((?!\\<\\/oneDayShippingAvailable\\>).)*");
	protected Pattern getHandlingTime = Pattern.compile("(?<=\\<handlingTime\\>)((?!\\<\\/handlingTime\\>).)*");
	protected Pattern getCurrencyId = Pattern.compile("(?<=\\<currentPrice currencyId\\=\")((?!\").)*");
/***/	protected Pattern getCurrentPrice = Pattern.compile("(?<=\\<currentPrice currencyId\\=\"[A-Z]{3}\"\\>)((?!\\<\\/currentPrice\\>).)*");
	protected Pattern getSellingState = Pattern.compile("(?<=\\<sellingState\\>)((?!\\<\\/sellingState\\>).)*");
	protected Pattern getTimeLeft = Pattern.compile("(?<=\\<timeLeft\\>)((?!\\<\\/timeLeft\\>).)*");
	protected Pattern getBestOfferEnabled = Pattern.compile("(?<=\\<bestOfferEnabled\\>)((?!\\<\\/bestOfferEnabled\\>).)*");
	protected Pattern getBuyItNow = Pattern.compile("(?<=\\<buyItNowAvailable\\>)((?!\\<\\/buyItNowAvailable\\>).)*");
	protected Pattern getStartTime = Pattern.compile("(?<=\\<startTime\\>)((?!\\<\\/startTime\\>).)*");
	protected Pattern getEndTime = Pattern.compile("(?<=\\<endTime\\>)((?!\\<\\/endTime\\>).)*");
	protected Pattern getListingType = Pattern.compile("(?<=\\<listingType\\>)((?!\\<\\/listingType\\>).)*");
	protected Pattern getGift = Pattern.compile("(?<=\\<gift\\>)((?!\\<\\/gift\\>).)*");
	protected Pattern getReturnsAccepted = Pattern.compile("(?<=\\<returnsAccepted\\>)((?!\\<\\/returnsAccepted\\>).)*");
	protected Pattern getConditionId = Pattern.compile("(?<=\\<conditionId\\>)((?!\\<\\/conditionId\\>).)*");
	protected Pattern getConditionDisplayName = Pattern.compile("(?<=\\<conditionDisplayName\\>)((?!\\<\\/conditionDisplayName\\>).)*");
	protected Pattern getIsMultiVariationListing = Pattern.compile("(?<=\\<isMultiVariationListing\\>)((?!\\<\\/isMultiVariationListing\\>).)*");
	protected Pattern getTopRatedSeller = Pattern.compile("(?<=\\<topRatedListing\\>)((?!\\<\\/topRatedListing\\>).)*");
	protected Pattern getFeedbackScore = Pattern.compile("(?<=\\<feedbackScore\\>)((?!\\<\\/feedbackScore\\>).)*");
	protected Pattern getFeedbackPercent = Pattern.compile("(?<=\\<positiveFeedbackPercent\\>)((?!\\<\\/positiveFeedbackPercent\\>).)*");
	protected Pattern getSellerName = Pattern.compile("(?<=\\<sellerUserName\\>)((?!\\<\\/sellerUserName\\>).)*");
	protected Pattern getTimeLeftFormatted = Pattern.compile("(?<=[A-Z])[0-9]+(?=[A-Z])");
	
	/** Create a new {@code ListingObject}
	 * @param listingItemXml  XML retrieved from eBay.
	 * ItemXml is the xml contained within each
	 * {@code <item>} tag.
	 */
	public Listing(String listingItemXml)
	{
		readXml(listingItemXml);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ITEM#: "+itemID);
		sb.append("TITLE: "+listingTitle);
		sb.append("GLOBALID: "+globalID);
		sb.append("CATEGORY_ID: "+categoryID);
		sb.append("CATEGORY_NAME: "+categoryName);
		sb.append("FEEDBACK_SCORE: "+feedbackScore);
		sb.append("FEEDBACK_PERCENT: "+feedbackPercent);
		sb.append("GALLERYURL: "+galleryURL);
		sb.append("ITEM_URL: "+viewItemURL);
		sb.append("PRODUCT_ID_TYPE: "+productIDType);
		sb.append("PRODUCT_ID: "+productID);
		sb.append("PAYMETHOD: "+paymentMethod);
		sb.append("AUTOPAY: "+autoPay);
		sb.append("POSTALCODE: "+postalCode);
		sb.append("LOCATION: "+location);
		sb.append("COUNTRY: "+country);
		sb.append("SHIPPINGTYPE: "+shippingType);
		sb.append("SHIP_TO: "+shipToLocations);
		sb.append("SELLER: "+sellerName);
		sb.append("EXPEDITED_SHIPPING: "+expeditedShipping);
		sb.append("ONEDAY_SHIPPING: "+oneDayShipping);
		sb.append("HANDLINGTIME: "+handlingTime);
		sb.append("CURRENCY_ID: "+currencyID);
		sb.append("PRICE: "+currentPrice);
		sb.append("SELLING_STATE: "+sellingState);
		sb.append("TIMELEFT: "+timeLeft);
		sb.append("BESTOFFER: "+bestOfferEnabled);
		sb.append("BUYITNOW: "+buyItNow);
		sb.append("STARTTIME: "+startTime);
		sb.append("ENDTIME: "+endTime);
		sb.append("LISTINGTYPE: "+listingType);
		sb.append("ISGIFT: "+gift);
		sb.append("RETURNSACCEPTED: "+returnsAccepted);
		sb.append("CONDITION_ID: "+conditionID);
		sb.append("CONDITION_NAME: "+conditionName);
		sb.append("ISVARIABLELISTING: "+isMultiVariationListing);
		sb.append("TOPRATEDSELLER: "+topRatedSeller);
		return sb.toString();
	}
	
	/** Parses eBay response XML
	 * @param xml  Raw XML returned by
	 * eBay's API.
	 */
	private void readXml(String xml)
	{
		Matcher findItemId = getItemId.matcher(xml);
		Matcher findTitle = getTitle.matcher(xml);
		Matcher findGlobalId = getGlobalId.matcher(xml);
		Matcher findCategoryId = getCategoryId.matcher(xml);
		Matcher findCategoryName = getCategoryName.matcher(xml);
		Matcher findGalleryUrl = getGalleryUrl.matcher(xml);
		Matcher findViewItemUrl = getViewItemUrl.matcher(xml);
		Matcher findProductIdType = getProductIdType.matcher(xml);
		Matcher findProductId = getProductId.matcher(xml);
		Matcher findPaymentMethod = getPaymentMethod.matcher(xml);
		Matcher findAutoPay = getAutoPay.matcher(xml);
		Matcher findPostalCode = getPostalCode.matcher(xml);
		Matcher findLocation = getLocation.matcher(xml);
		Matcher findCountry = getCountry.matcher(xml);
		Matcher findShippingType = getShippingType.matcher(xml);
		Matcher findShipToLocations = getShipToLocations.matcher(xml);
		Matcher findExpeditedShipping = getExpeditedShipping.matcher(xml);
		Matcher findOneDayShipping = getOneDayShipping.matcher(xml);
		Matcher findHandlingTime = getHandlingTime.matcher(xml);
		Matcher findCurrencyId = getCurrencyId.matcher(xml);
		Matcher findCurrentPrice = getCurrentPrice.matcher(xml);
		Matcher findSellingState = getSellingState.matcher(xml);
		Matcher findTimeLeft = getTimeLeft.matcher(xml);
		Matcher findBestOfferEnabled = getBestOfferEnabled.matcher(xml);
		Matcher findBuyItNow = getBuyItNow.matcher(xml);
		Matcher findStartTime = getStartTime.matcher(xml);
		Matcher findEndTime = getEndTime.matcher(xml);
		Matcher findListingType = getListingType.matcher(xml);
		Matcher findGift = getGift.matcher(xml);
		Matcher findReturnsAccepted = getReturnsAccepted.matcher(xml);
		Matcher findConditionId = getConditionId.matcher(xml);
		Matcher findConditionDisplayName = getConditionDisplayName.matcher(xml);
		Matcher findIsMultiVariationListing = getIsMultiVariationListing.matcher(xml);
		Matcher findTopRatedSeller = getTopRatedSeller.matcher(xml);
		Matcher findFeedbackScore = getFeedbackScore.matcher(xml);
		Matcher findFeedbackPercent = getFeedbackPercent.matcher(xml);
		Matcher findSellerName = getSellerName.matcher(xml);
		
		if(findItemId.find())
			this.itemID = findItemId.group();
		if(findTitle.find())
			this.listingTitle = findTitle.group();
/*		if(findGlobalId.find())
			this.globalId = findGlobalId.group();
		if(findCategoryId.find())
			this.categoryId = findCategoryId.group();
		if(findCategoryName.find())
			this.categoryName = findCategoryName.group(); */
		if(findGalleryUrl.find())
			this.galleryURL = findGalleryUrl.group();
		if(findViewItemUrl.find())
			this.viewItemURL = findViewItemUrl.group();
/*		if(findProductIdType.find())
			this.productIdType = findProductIdType.group();
		if(findProductId.find())
			this.productId = findProductId.group();
		if(findPaymentMethod.find())
			this.paymentMethod = findPaymentMethod.group();
		if(findAutoPay.find())
			this.autoPay = Boolean.valueOf(findAutoPay.group());
		if(findPostalCode.find())
			this.postalCode = findPostalCode.group();
		if(findLocation.find())
			this.location = findLocation.group();
		if(findCountry.find())
			this.country = findCountry.group();
		if(findShippingType.find())
			this.shippingType = findShippingType.group();
		if(findShipToLocations.find())
			this.shipToLocations = findShipToLocations.group();
		if(findExpeditedShipping.find())
			this.expeditedShipping = Boolean.valueOf(findExpeditedShipping.group());
		if(findOneDayShipping.find())
			this.oneDayShipping = Boolean.valueOf(findOneDayShipping.group());
		if(findHandlingTime.find())
			this.handlingTime = Integer.valueOf(findHandlingTime.group());
		if(findCurrencyId.find())
			this.currencyId = findCurrencyId.group(); */
		if(findCurrentPrice.find())
			this.currentPrice = Double.valueOf(findCurrentPrice.group());
/*		if(findSellingState.find())
			this.sellingState = findSellingState.group(); */
		if(findTimeLeft.find())
			timeLeft = findTimeLeft.group();
/*		if(findBestOfferEnabled.find())
			this.bestOfferEnabled = Boolean.valueOf(findBestOfferEnabled.group());
		if(findBuyItNow.find())
			this.buyItNow = Boolean.valueOf(findBuyItNow.group());
		if(findStartTime.find())
			this.startTime = convertDateFormat(findStartTime.group());
		if(findEndTime.find())
			this.endTime = convertDateFormat(findEndTime.group()); */
		if(findListingType.find())
			this.listingType = findListingType.group();
/*		if(findGift.find())
			this.gift = Boolean.valueOf(findGift.group());
		if(findReturnsAccepted.find())
			this.returnsAccepted = Boolean.valueOf(findReturnsAccepted.group());
		if(findConditionId.find())
			this.conditionId = findConditionId.group(); */
		if(findConditionDisplayName.find())
			this.conditionName = findConditionDisplayName.group();
/*		if(findIsMultiVariationListing.find())
			this.isMultiVariationListing = Boolean.valueOf(findIsMultiVariationListing.group());
		if(findTopRatedSeller.find())
			this.topRatedSeller = Boolean.valueOf(findTopRatedSeller.group());
		if(findFeedbackPercent.find())
			this.feedbackPercent = Double.valueOf(findFeedbackPercent.group()); */
		if(findFeedbackScore.find())
			this.feedbackScore = Integer.valueOf(findFeedbackScore.group());
		if(findSellerName.find())
			this.sellerName = findSellerName.group();
	}
	
	public String getListingTitle()
	{
		return listingTitle;
	}
	
	public String getGalleryUrl()
	{
		return galleryURL;
	}
	
	public String getViewItemUrl()
	{
		return viewItemURL;
	}
	
	public double getCurrentPrice()
	{
		return currentPrice;
	}
	
	/** Converts time left to a 
	 * more readable format.
	 * @return  {@code String} representing
	 * time left for an item.
	 */
	public String getTimeLeft() // TODO
	{
		timeLeft = timeLeft.replaceFirst("P", "");
		timeLeft = timeLeft.replaceFirst("DT", " Days ");
		timeLeft = timeLeft.replaceFirst("H", " Hours ");
		timeLeft = timeLeft.replaceAll("M[0-9]+S", " Minutes");
		return timeLeft;
	}
	
	public String getConditionName()
	{
		return conditionName;
	}
	
	public int getFeedbackScore()
	{
		return feedbackScore;
	}
	
	public String getSeller()
	{
		return sellerName;
	}
	
	public String getListingType()
	{
		if(listingType.equals("FixedPrice") || listingType.equals("StoreInventory"))
			listingType = "BuyItNow";
		return listingType;
	}
}
