package finditfirst.utilities;

import finditfirst.ebay.Listing;
import finditfirst.main.GUI;
import finditfirst.main.Program;
import finditfirst.main.Settings;
import finditfirst.searchentry.SearchEntry;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Static class handles preparing and
 * sending emails.
 */
public final class MailService
{
	/** Determines the service provider of
	 * {@code FINDITFIRST_EMAIL}
	 */
	public enum EmailServiceProvider
	{
		Gmail, Hotmail;
	}
	
	private static final Logger LOG = Program.LOG;
	
	/** This email address will be used to SEND notifications */
	public static final String FINDITFIRST_EMAIL 		= "";
	
	/** Password for above email address */
	public static final String EMAIL_PASSWORD 			= "";
	
	/** Used to construct HTML formatted email messages */
	public static final String EMAIL_HTML_HEADER 		= "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
														+"<html xmlns=\"http://www.w3.org/1999/xhtml\">"
														+"<head>"
														+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
														+"<title>eBay Search Results</title>"
														+"</head>"
														+"<body>";
	
	/** HTML used to represent each search result in an email message */
	public static final String INDIVIDUAL_LISTING_TEMPLATE 	= "<div id=\"Result%s\">"
														+"<a href=\"%s\">"
														+"<h2>%s</h2>"
														+"</a><table width=\"500\">"
														+"<tr>"
														+   "<td><img src=\"%s\"/>"
														+   "</td>"
														+   "<td><div style=\"float: left;width: 360px;\">"
														+   "<b>%s</b><br><i>%s</i><br>%s<br><br>Seller: %s - %s<br><br>Price: <b>$%s</b></div>"
														+   "</td>"
														+ "</tr>"
														+"</table>"
														+"</div>";
	
	/** Service provider of {@code FINDITFIRST_EMAIL} */
	private static EmailServiceProvider serviceProvider = determineEmailServiceProvider();
	
	private static final Object lock = new Object();
	
	protected MailService() {}
	
	/** Sends an html formatted email containing
	 * the provided {@link Listing}s.
	 * @param emailSubject  Email subject title
	 * @param listings  List of {@code Listings}
	 * @see {@link finditfirst.ebay.API#submitSearchQuery(SearchEntry)}
	 */
	public static void sendResults(String emailSubject, Collection<Listing> listings)
	{
		String emailHtml = buildHtmlFromResults(listings);
		
		Properties props = new Properties();
		switch(serviceProvider)
		{
			default :
			case Gmail :
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				break;
			case Hotmail :
				break;
		}
		
		synchronized (lock)
		{
			Session session = Session.getInstance(props,	// TODO initialize props + session w/ class ?
					new Authenticator()
					{
						@Override
						protected PasswordAuthentication getPasswordAuthentication()
						{
							return new PasswordAuthentication(FINDITFIRST_EMAIL, EMAIL_PASSWORD);
						}
					});
			
			try
			{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(FINDITFIRST_EMAIL));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(Settings.getInstance().getSendToEmailAddress()));
					
				message.setSubject(emailSubject);
				message.setContent(emailHtml, "text/html");
		 
				Transport.send(message);
			} 
			catch (MessagingException e)
			{
				JOptionPane.showMessageDialog(GUI.frame, 
						"Connection certification failed."
						+ " Please make sure your antivirus isn't blocking outbound mail (SMTP)", "Error", JOptionPane.ERROR_MESSAGE);
				LOG.log(Level.SEVERE, "Failed to send email for: " + emailSubject);
			}
		}	
	}
	
	/** Builds an html formatted email
	 * with the provided {@code Listings},
	 * representing item results.
	 * @param listings  Item results
	 * @return  Raw html, ready to be emailed.
	 */
	private static String buildHtmlFromResults(Collection<Listing> listings)
	{		
		StringBuilder sb = new StringBuilder();
		sb.append(EMAIL_HTML_HEADER);
		
		int i = 1;
		for(Listing listing : listings)
		{
			sb.append(String.format(INDIVIDUAL_LISTING_TEMPLATE, i++,
					listing.getViewItemUrl(), listing.getListingTitle(), listing.getGalleryUrl(),
					listing.getConditionName(), listing.getListingType(), listing.getTimeLeft(),
					listing.getSeller(), listing.getFeedbackScore(),
					listing.getCurrentPrice()));
		}
		
		sb.append("</body></html>");
		return sb.toString();
	}
	
	private static EmailServiceProvider determineEmailServiceProvider()
	{
		if(FINDITFIRST_EMAIL.contains("@gmail.com"))
		{
			return EmailServiceProvider.Gmail;
		}
		else if(FINDITFIRST_EMAIL.contains("@hotmail.com"))
		{
			return EmailServiceProvider.Hotmail;
		}
		return null;
	}
	
}
