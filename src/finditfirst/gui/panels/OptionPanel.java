package finditfirst.gui.panels;

import finditfirst.gui.dialogs.AdvancedKeywordsDialog;
import finditfirst.gui.dialogs.ExcludedSellersDialog;
import finditfirst.gui.dialogs.IncludedSellersDialog;
import finditfirst.gui.dialogs.SettingsDialog;
import finditfirst.main.Program;
import finditfirst.main.Settings;
import finditfirst.utilities.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/** eBay API filter options presented
 * in a user intractable way. Each
 * {@link SearchEntry} uses an instance of this
 * to determine how its' GET Request should be
 * built.
 * @see {@link API#buildGETRequestURL}
 */
public class OptionPanel extends JPanel implements Serializable
{
	private static final long serialVersionUID = Program.serialVersionUID;
	
	/** Key is displayed in panel, value is used in GET Request */
	public static final LinkedHashMap<String, String> CATEGORIES = new LinkedHashMap<String, String>()
	{{
		put("All Categories", "ALL");
		put("Antiques", "20081");
		put("Art", "550");
		put("Baby", "2984");
		put("Books", "267");
		put("Business & Industrial", "12576");
		put("Cameras & Photo", "625");
		put("Cell Phones & Accessories", "15032");
		put("Clothing, Shoes & Accessories", "11450");
		put("Coins & Paper Money", "11116");
		put("Collectibles", "1");
		put("Computers/Tablets & Networking", "58058");
		put("Consumer Electronics", "293");
		put("Crafts", "14339");
		put("Dolls & Bears", "237");
		put("DVDs & Movies", "11232");
		put("eBay Motors", "null");
		put("Entertainment Memorabilia", "45100");
		put("Gift Cards & Coupons", "172008");
		put("Health & Beaty", "26395");
		put("Home & Garden", "11700");
		put("Jewlery & Watches", "281");
		put("Music", "11233");
		put("Music Instruments & Gear", "619");
		put("Pet Supplies", "1281");
		put("Pottery & Glass", "870");
		put("Real Estate", "10542");
		put("Specialty Services", "316");
		put("Sporting Goods", "888");
		put("Sports Mem, Cards & Fan Shop", "64482");
		put("Stamps", "260");
		put("Tickets & Experiences", "1305");
		put("Toys & Hobbies", "220");
		put("Travel", "3252");
		put("Video Games & Consoles", "1249");
		put("Everything Else", "99");
	}};
	
	/** Key is displayed in panel, value is used in GET Request */
	public static final LinkedHashMap<String, String> COUNTRIES = new LinkedHashMap<String, String>()
	{{
		put("Argentina", "AR");
		put("Australia", "AU");
		put("Austria", "AT");
		put("Belgium", "BE");
		put("Brazil", "BR");
		put("Canada", "CA");
		put("Chile", "CL");
		put("China", "CN");
		put("Colombia", "CO");
		put("Costa Rica", "CR");
		put("Czech Republic", "CZ");
		put("Denmark", "DK");
		put("Finland", "FI");
		put("France", "FR");
		put("Germany", "DE");
		put("Greece", "GR");
		put("Hong Kong", "HK");
		put("Hungary", "HU");
		put("India", "IN");
		put("Indonesia", "ID");
		put("Ireland", "IE"); // TODO Check Israel
		put("Israel", "IE");
		put("Italy", "IT");
		put("Japan", "JP");
		put("Korea", "KR");
		put("Luxembourg", "LU");
		put("Malaysia", "MY");
		put("Mexico", "MX");
		put("Netherlands", "NL");
		put("New Zealand", "NZ");
		put("Norway", "NO");
		put("Puerto Rico", "PR");
		put("Philippines", "PH");
		put("Poland", "PL");
		put("Portugal", "PT");
		put("Russian Federation", "RU");
		put("Singapore", "SG");
		put("Spain", "ES");
		put("Sweden", "SE");
		put("Switzerland", "CH");
		put("Taiwan", "TW");
		put("Thailand", "TH");
		put("Turkey", "TR");
		put("United Arab Emirates", "AE");
		put("United Kingdom", "GB");
		put("United States", "US");
		put("Worldwide", "ALL");
	}};
	
	/** Key is displayed in panel, value is used in GET Request */
	public static final LinkedHashMap<String, String> SITES = new LinkedHashMap<String, String>()
	{{
		put("eBay.com", "EBAY-US");
		put("eBay Motors", "EBAY-MOTOR");
		put("Australia", "EBAY-AU");
		put("Austria", "EBAY-AT");
		put("Belgium (FR)", "EBAY-FRBE");
		put("Belgium (NL)", "EBAY-NLBE");
		put("Canada (EN", "EBAY-ENCA");
		put("Canada (FR)", "EBAY-FRCA");
		put("France", "EBAY-FR");
		put("Germany", "EBAY-DE");
		put("Hong Kong", "EBAY-HK");
		put("India", "EBAY-IN");
		put("Ireland", "EBAY-IE");
		put("Italy", "EBAY-IT");
		put("Malaysia", "EBAY-MY");
		put("Netherlands", "EBAY-NL");
		put("Philippines", "EBAY-PH");
		put("Poland", "EBAY-PL");
		put("United Kingdom", "EBAY-GB");
		put("Singapore", "EBAY-SG");
		put("Spain", "EBAY-ES");
		put("Switzerland", "EBAY-CH");
	}};
	
	/** Key is displayed in panel, value is used in GET Request */
	public static final LinkedHashMap<String, String> SELLER_SCORES = new LinkedHashMap<String, String>()
	{{
		put(">= 0", "0");
		put(">= 1", "1");
		put(">= 2", "2");
		put(">= 5", "5");
		put(">= 10", "10");
		put(">= 20", "20");
		put(">= 25", "25");
		put(">= 50", "50");
		put(">= 75", "75");
		put(">= 100", "100");
		put(">= 150", "150");
		put(">= 200", "200");
		put(">= 500", "500");
		put(">= 1000", "1000");
		put("<= 1", "1");
		put("<= 2", "2");
		put("<= 5", "5");
		put("<= 10", "10");
		put("<= 20", "20");
		put("<= 25", "25");
		put("<= 50", "50");
		put("<= 75", "75");
		put("<= 100", "100");
		put("<= 150", "150");
		put("<= 200", "200");
		put("<= 500", "500");
		put("<= 1000", "1000");
	}};
	
	/** Key is displayed in panel, value is used in GET Request */
	public static final LinkedHashMap<String, String> SELLER_PERCENTS = new LinkedHashMap<String, String>()
	{{
		put("= 100%", "100");
		put(">= 99%", "99");
		put(">= 98%", "98");
		put(">= 97%", "97");
		put(">= 96%", "96");
		put(">= 95%", "95");
		put(">= 90%", "90");
		put(">= 85%", "85");
		put(">= 80%", "80");
		put(">= 75%", "75");
		put(">= 70%", "70");
		put(">= 60%", "60");
		put(">= 0%", "0");
	}};
	
	public static final String[] DISTANCE = {"5", "10", "15", "20", "25", "35", "50", "75", "100", "150", "200", "300", "400", "500"};
	
	private JLabel lblSearchName;
	
	private JTextField txtKeywords;
	private JTextField txtMinPrice;
	private JTextField txtMaxPrice;
	private JTextField txtZipcode;
	
	private JCheckBox chckNew;
	private JCheckBox chckUsed;
	private JCheckBox chckUnspecified;
	private JCheckBox chckBestOffer;
	private JCheckBox chckAuthorized;
	private JCheckBox chckAutoPay;
	private JCheckBox chckFeatured;
	private JCheckBox chckGetItFast;
	private JCheckBox chckLocal;
	private JCheckBox chckOutlet;
	private JCheckBox chckReturns;
	private JCheckBox chckTopRated;
	private JCheckBox chckAuction;
	private JCheckBox chckFixedPrice;
	private JCheckBox chckBuyItNow;
	private JCheckBox chckClassified;
	private JCheckBox chckWithinZip;
	private JCheckBox chckFreeShipping;
	private JCheckBox chckItemLocation;
	
	private JComboBox comboCategoryNames;
	private JComboBox comboScore;
	private JComboBox comboSellerPercent;
	private JComboBox comboLocatedIn;
	private JComboBox comboAvailableTo;
	private JComboBox comboDistance;
	private JComboBox comboListedIn;
	
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rdbtnLocatedIn;
	private JRadioButton rdbtnAvailableTo;
	
	private JCheckBox chckUseExcluded;
	private JCheckBox chckUseIncluded;
	
	private Font fontGui = new Font("Verdana", Font.PLAIN, 14);
	
	private static ArrayList<String> includedSellers = Settings.getInstance().getIncludedSellerList();
	private static ArrayList<String> excludedSellers = Settings.getInstance().getExcludedSellerList();
	
	/** Creates a new default {@code OptionPanel} */
	public OptionPanel()
	{
		this.setLayout(null);
		init();
	}
	
	/** @see {@link OptionPanel#OptionPanel}.
	 * @param name  Search title
	 */
	public OptionPanel(String name)
	{
		this();
		setSearchName(name);
	}
	
	private void init()
	{
		lblSearchName = new JLabel("New Search");
		JLabel lblKeywords = new JLabel("Keywords:");
		JLabel lblMinPrice = new JLabel("Min Price:");
		JLabel lblMaxPrice = new JLabel("Max Price:");
		JLabel lblNewLabel = new JLabel("Condition");
		JLabel lblMisc = new JLabel("Micellaneous Options");
		JLabel lblListingType = new JLabel("Listing Type");
		JLabel lblCategory = new JLabel("Category");
		JLabel lblSeller = new JLabel("Seller Feedback");
		JLabel lblMinFeed = new JLabel("Seller Score:");
		JLabel lblSellerPercent = new JLabel("Seller Percent:");
		JLabel lblLocation = new JLabel("Item Location");
		JLabel lblSite = new JLabel("Site");
		JLabel lblZipcode = new JLabel("miles of ZipCode:");
		JLabel lblOtherOptions = new JLabel("Common Options");
		JButton btnKeywordsAdvanced = new JButton("Advanced Keywords");
		JButton btnExcludedSellers = new JButton("Open Excluded Sellers List");
		JButton btnIncludedSellers = new JButton("Open Included Sellers List");
		
		txtKeywords = new JTextField();
		txtMinPrice = new JTextField();
		txtMaxPrice = new JTextField();
		chckNew = new JCheckBox("New");
		chckUsed = new JCheckBox("Used");
		chckUnspecified = new JCheckBox("Unspecified");
		chckBestOffer = new JCheckBox("Best Offer Enabled");
		chckAuthorized = new JCheckBox("Authorized Seller Only");
		chckAutoPay = new JCheckBox("No Auto Pay");
		chckFeatured = new JCheckBox("Featured Items Only");
		chckGetItFast = new JCheckBox("Get It Fast Only");
		chckLocal = new JCheckBox("Local Pickup Only");
		chckOutlet = new JCheckBox("Outlet Seller Only");
		chckReturns = new JCheckBox("Must Accept Returns");
		chckTopRated = new JCheckBox("Top Rated Seller Only");
		comboCategoryNames = new JComboBox(CATEGORIES.keySet().toArray());
		chckAuction = new JCheckBox("Auction");
		chckFixedPrice = new JCheckBox("Fixed Price");
		chckBuyItNow = new JCheckBox("Auction w/ BIN");
		chckClassified = new JCheckBox("Classified Ads");
		comboScore = new JComboBox(SELLER_SCORES.keySet().toArray());
		comboSellerPercent = new JComboBox(SELLER_PERCENTS.keySet().toArray());
		comboLocatedIn = new JComboBox(COUNTRIES.keySet().toArray());
		comboLocatedIn.setSelectedIndex(45);
		comboAvailableTo = new JComboBox(COUNTRIES.keySet().toArray());
		comboAvailableTo.setSelectedIndex(45);
		comboAvailableTo.setEnabled(false);
		chckWithinZip = new JCheckBox("Within");
		comboDistance = new JComboBox(DISTANCE);
		chckFreeShipping = new JCheckBox("Free Shipping");
		rdbtnLocatedIn = new JRadioButton("Located In:");
		rdbtnAvailableTo = new JRadioButton("Available To:");
		txtZipcode = new JTextField();
		comboListedIn = new JComboBox(SITES.keySet().toArray());
		chckItemLocation = new JCheckBox("Specify Item Location");
		chckUseExcluded = new JCheckBox("Use Excluded Sellers List");
		chckUseIncluded = new JCheckBox("Use Included Sellers List");
		
		bg.add(rdbtnLocatedIn);
		bg.add(rdbtnAvailableTo);
		
		comboSellerPercent.setEnabled(false);
		rdbtnLocatedIn.setEnabled(false);
		rdbtnAvailableTo.setEnabled(false);
		comboLocatedIn.setEnabled(false);
		comboDistance.setEnabled(false);
		txtZipcode.setEnabled(false);
		
		chckNew.setSelected(true);
		chckUsed.setSelected(true);
		chckAuction.setSelected(true);
		chckFixedPrice.setSelected(true);
		chckBuyItNow.setSelected(true);
		lblSellerPercent.setEnabled(false);
		
		lblSearchName.setFont(new Font("Verdana", Font.PLAIN, 24));
		lblKeywords.setFont(fontGui);
		lblMinPrice.setFont(fontGui);
		lblMaxPrice.setFont(fontGui);
		lblNewLabel.setFont(fontGui);
		lblMisc.setFont(fontGui);
		lblListingType.setFont(fontGui);
		lblCategory.setFont(fontGui);
		lblSeller.setFont(fontGui);
		lblMinFeed.setFont(fontGui);
		lblSellerPercent.setFont(fontGui);
		lblLocation.setFont(fontGui);
		lblSite.setFont(fontGui);
		lblOtherOptions.setFont(fontGui);
		lblZipcode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		txtKeywords.setColumns(10);
		txtMinPrice.setColumns(10);
		txtMaxPrice.setColumns(10);
		txtZipcode.setColumns(10);
		comboScore.setMaximumRowCount(10);
		comboSellerPercent.setSelectedIndex(SELLER_PERCENTS.keySet().toArray().length - 1);
		comboListedIn.setSelectedIndex(0);
		
		txtZipcode.setDocument(Utils.getZipcodeFormattedDocument());
		txtMinPrice.setDocument(Utils.getDoubleFormattedDocument());
		txtMaxPrice.setDocument(Utils.getDoubleFormattedDocument());
		
		lblSearchName.setBounds(6, 11, 574, 30);
		lblKeywords.setBounds(6, 52, 80, 20);
		lblMinPrice.setBounds(6, 132, 80, 20);
		lblMaxPrice.setBounds(223, 132, 80, 20);
		lblNewLabel.setBounds(6, 173, 88, 20);
		lblMisc.setBounds(424, 245, 156, 20);
		lblListingType.setBounds(6, 216, 97, 20);
		lblCategory.setBounds(6, 91, 80, 20);
		lblSeller.setBounds(6, 291, 132, 20);
		lblMinFeed.setBounds(6, 322, 110, 20);
		lblSellerPercent.setBounds(6, 353, 110, 20);
		lblLocation.setBounds(6, 456, 132, 20);
		lblSite.setBounds(424, 508, 46, 20);
		lblZipcode.setBounds(126, 577, 102, 14);
		lblOtherOptions.setBounds(424, 91, 132, 20);
		btnKeywordsAdvanced.setBounds(424, 50, 150, 23);
		btnExcludedSellers.setBounds(207, 396, 189, 23);
		btnIncludedSellers.setBounds(207, 422, 189, 23);
		txtKeywords.setBounds(96, 51, 300, 20);
		txtMinPrice.setBounds(96, 131, 66, 20);
		txtMaxPrice.setBounds(313, 131, 66, 20);
		chckNew.setBounds(100, 171, 58, 23);
		chckUsed.setBounds(160, 171, 58, 23);
		chckUnspecified.setBounds(220, 171, 97, 23);
		chckAuthorized.setBounds(424, 266, 200, 23);
		chckAutoPay.setBounds(424, 292, 200, 23);
		chckFeatured.setBounds(424, 318, 200, 23);
		chckGetItFast.setBounds(424, 195, 200, 23);
		chckLocal.setBounds(424, 344, 200, 23);
		chckOutlet.setBounds(424, 370, 200, 23);
		chckReturns.setBounds(424, 143, 200, 23);
		chckTopRated.setBounds(424, 396, 200, 23);
		comboCategoryNames.setBounds(96, 90, 300, 20);
		chckBestOffer.setBounds(424, 169, 200, 23);
		chckAuction.setBounds(100, 217, 97, 23);
		chckFixedPrice.setBounds(100, 242, 97, 23);
		chckBuyItNow.setBounds(220, 217, 159, 23);
		chckClassified.setBounds(220, 242, 156, 23);
		comboScore.setBounds(126, 322, 71, 20);
		comboSellerPercent.setBounds(126, 355, 71, 20);
		comboLocatedIn.setBounds(159, 505, 110, 20);
		comboAvailableTo.setBounds(159, 536, 110, 20);
		chckWithinZip.setBounds(6, 573, 65, 23);
		comboDistance.setBounds(71, 574, 48, 20);
		txtZipcode.setBounds(223, 574, 57, 20);
		chckFreeShipping.setBounds(424, 117, 132, 23);
		rdbtnLocatedIn.setBounds(40, 504, 109, 23);
		rdbtnAvailableTo.setBounds(40, 535, 109, 23);
		comboListedIn.setBounds(424, 536, 97, 20);
		chckItemLocation.setBounds(6, 478, 191, 23);
		chckUseExcluded.setBounds(6, 396, 195, 23);
		chckUseIncluded.setBounds(6, 422, 195, 23);
		
		btnKeywordsAdvanced.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new AdvancedKeywordsDialog(OptionPanel.this);
			}
		});
		
		btnExcludedSellers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new ExcludedSellersDialog();
			}
		});
		
		btnIncludedSellers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new IncludedSellersDialog();
			}
		});
		
		chckUseExcluded.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(chckUseExcluded.isSelected())
				{
					chckUseIncluded.setSelected(false);
				}
			}
		});
		
		chckUseIncluded.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(chckUseIncluded.isSelected())
				{
					chckUseExcluded.setSelected(false);
				}
			}
		});
		
		chckItemLocation.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{
				if(!chckItemLocation.isEnabled())
				{
					return;
				}
				
				if(chckItemLocation.isSelected())
				{
					rdbtnLocatedIn.setEnabled(true);
					rdbtnAvailableTo.setEnabled(true);
					comboLocatedIn.setEnabled(true);
					comboAvailableTo.setEnabled(true);
				}
				else 
				{
					rdbtnLocatedIn.setEnabled(false);
					rdbtnAvailableTo.setEnabled(false);
					comboLocatedIn.setEnabled(false);
					comboAvailableTo.setEnabled(false);
					bg.clearSelection();
				}
			}
		});
		
		chckWithinZip.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(!chckWithinZip.isEnabled())
				{
					return;
				}
				
				if(chckWithinZip.isSelected())
				{
					comboDistance.setEnabled(true);
					txtZipcode.setEnabled(true);
				}
				else
				{
					comboDistance.setEnabled(false);
					txtZipcode.setEnabled(false);
				}
			}
		});
		
		this.add(lblSearchName);
		this.add(lblKeywords);
		this.add(lblMinPrice);
		this.add(lblMaxPrice);
		this.add(lblNewLabel);
		this.add(lblMisc);
		this.add(lblListingType);
		this.add(lblCategory);
		this.add(lblSeller);
		this.add(lblMinFeed);
		this.add(lblSellerPercent);
		this.add(lblLocation);
		this.add(lblSite);
		this.add(lblZipcode);
		this.add(lblOtherOptions);
		this.add(btnKeywordsAdvanced);
		this.add(btnExcludedSellers);
		this.add(btnIncludedSellers);
		this.add(txtKeywords);
		this.add(txtMinPrice);
		this.add(txtMaxPrice);
		this.add(chckNew);
		this.add(chckUsed);
		this.add(chckUnspecified);
		this.add(chckBestOffer);
		this.add(chckAuthorized);
		this.add(chckAutoPay);
		this.add(chckFeatured);
		this.add(chckGetItFast);
		this.add(chckLocal);
		this.add(chckOutlet);
		this.add(chckReturns);
		this.add(chckTopRated);
		this.add(comboCategoryNames);
		this.add(chckAuction);
		this.add(chckFixedPrice);
		this.add(chckBuyItNow);
		this.add(chckClassified);
		this.add(comboScore);
		this.add(comboSellerPercent);
		this.add(comboLocatedIn);
		this.add(comboAvailableTo);
		this.add(chckWithinZip);
		this.add(comboDistance);
		this.add(txtZipcode);
		this.add(chckFreeShipping);
		this.add(rdbtnLocatedIn);
		this.add(rdbtnAvailableTo);
		this.add(comboListedIn);
		this.add(chckItemLocation);
		this.add(chckUseExcluded);
		this.add(chckUseIncluded);
	}
	
	/** Called before a search is started
	 * to verify all GET Request filters will
	 * be valid.
	 * @return  True if contradictory filters,
	 * false otherwise.
	 */
	public boolean checkForErrors()
	{
		if(Settings.getInstance().getSendToEmailAddress().isEmpty())
		{
			new SettingsDialog();
			JOptionPane.showMessageDialog(new JFrame(), "Please enter your email address", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		
		if(txtKeywords.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please enter keywords!", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(!txtMinPrice.getText().isEmpty() && !txtMaxPrice.getText().isEmpty())
		{
			if(Double.parseDouble(txtMinPrice.getText()) >= Double.parseDouble(txtMaxPrice.getText()))
			{
				JOptionPane.showMessageDialog(new JFrame(), "Minimum price can't be greater than maximum price!", getSearchName(), JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		if(!chckNew.isSelected() && !chckUsed.isSelected() && !chckUnspecified.isSelected())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please select at least one condition type", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(!chckAuction.isSelected() && !chckBuyItNow.isSelected() && !chckFixedPrice.isSelected() && !chckClassified.isSelected())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please select at least one listing type", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(chckUseExcluded.isSelected() && excludedSellers.isEmpty())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Can't use 'Excluded Seller List' because it's empty", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(chckUseIncluded.isSelected() && includedSellers.isEmpty())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Can't use 'Included Seller List' because it's empty", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(chckItemLocation.isSelected() && !rdbtnLocatedIn.isSelected() && !rdbtnAvailableTo.isSelected())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please select 'Located In' / 'Available To' or deselect 'Specify Item Location'", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(chckWithinZip.isSelected() && txtZipcode.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a zipcode", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(chckTopRated.isSelected() && (chckUseExcluded.isSelected() || chckUseIncluded.isSelected()))
		{
			JOptionPane.showMessageDialog(new JFrame(), "Can't use 'Included/Excluded Seller List' with 'Top Rated Seller Only' enabled", getSearchName(), JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	/** Prevents input on an {@code OptionPanel} while its
	 * corresponding search is running.
	 * @see {@link SearchEntry}
	 */
	@Override
	public void disable()
	{
		for(Component component : this.getComponents())	// TODO
		{
			component.setEnabled(false);
		}
	}
	
	/** Allows input on an {@code OptionPanel} once
	 * its corresponding search is stopped.
	 * @see {@link SearchEntry}
	 */
	@Override
	public void enable()
	{
		for(Component component : this.getComponents())
		{
			component.setEnabled(true);
		}
		
		if(!chckItemLocation.isSelected())
		{
			rdbtnAvailableTo.setEnabled(false);
			rdbtnLocatedIn.setEnabled(false);
		}
		if(!chckWithinZip.isSelected())
		{
			comboDistance.setEnabled(false);
			txtZipcode.setEnabled(false);
		}
		
		comboSellerPercent.setEnabled(false);
	}
	
	public void setSearchName(String name)
	{
		lblSearchName.setText(name);
	}
	
	public String getSearchName()
	{
		return lblSearchName.getText();
	}
	
	public void setKeywords(String keywords)
	{
		txtKeywords.setText(keywords);
	}
	
	public String getKeywords()
	{
		return txtKeywords.getText().replace(" ", "%20");
	}
	
	public String getMinPrice()
	{
		if(!txtMinPrice.getText().isEmpty())
		{
			return txtMinPrice.getText();
		}
		return null;
	}
	
	public String getMaxPrice()
	{
		if(!txtMaxPrice.getText().isEmpty())
		{
			return txtMaxPrice.getText();
		}
		return null;
	}
	
	public ArrayList<String> getCondition()
	{
		ArrayList<String> condition = new ArrayList<String>();
		if(chckNew.isSelected())
		{
			condition.add("New");
		}
		if(chckUsed.isSelected())
		{
			condition.add("Used");
		}
		if(chckUnspecified.isSelected())
		{
			condition.add("Unspecified");
		}
		return condition;
	}
	
	public String getCategoryName()
	{
		return (String) comboCategoryNames.getSelectedItem();
	}
	
	public String getCategoryId()
	{
		return CATEGORIES.get(comboCategoryNames.getSelectedItem());
	}
	
	public String getFeedbackScoreName()
	{
		return (String) comboScore.getSelectedItem();
	}
	
	public String getFeedbackScore()
	{
		return SELLER_SCORES.get(comboScore.getSelectedItem());
	}
	
	public String getFeedbackPercent()
	{
		return SELLER_PERCENTS.get(comboSellerPercent.getSelectedItem()); 
	}
	
	public String getLocatedIn()
	{
		if(chckItemLocation.isSelected() && rdbtnLocatedIn.isSelected())
		{
			return COUNTRIES.get(comboLocatedIn.getSelectedItem());
		}
		return null;
	}
	
	public String getAvailableTo()
	{
		if(chckItemLocation.isSelected() && rdbtnAvailableTo.isSelected())
		{
			return COUNTRIES.get(comboAvailableTo.getSelectedItem());
		}
		return null;
	}
	
	public String getDistance()
	{
		if(chckWithinZip.isSelected())
		{
			return (String) comboDistance.getSelectedItem();
		}
		return null;
	}
	
	public String getZipcode()
	{
		if(chckWithinZip.isSelected())
		{
			return txtZipcode.getText();
		}
		return null;
	}
	
	public ArrayList<String> getListingTypes()
	{
		ArrayList<String> listingtypes = new ArrayList<String>();
		if(chckAuction.isSelected())
		{
			listingtypes.add("Auction");
		}
		if(chckBuyItNow.isSelected())
		{
			listingtypes.add("AuctionWithBIN");
		}
		if(chckFixedPrice.isSelected())
		{
			listingtypes.add("FixedPrice");
		}
		if(chckClassified.isSelected())
		{
			listingtypes.add("Classified");
		}
		if(listingtypes.size() == 4)
		{
			listingtypes.clear();
			listingtypes.add("All");	// Why is this necessary ?
		}
		return listingtypes;
	}
	
	public String getListedIn()
	{
		return SITES.get(comboListedIn.getSelectedItem());
	}
	
	public ArrayList<String> getIncludedSellers()
	{
		return includedSellers;
	}
	
	public ArrayList<String> getExcludedSellers()
	{
		return excludedSellers;
	}
	
	public boolean isBestOffer()
	{
		return chckBestOffer.isSelected();
	}
	
	public boolean isAuthorized()
	{
		return chckAuthorized.isSelected();
	}
	
	public boolean isNoAutoPay()
	{
		return chckAutoPay.isSelected();
	}
	
	public boolean isFeatured() 
	{
		return chckFeatured.isSelected();
	}
	
	public boolean isGetItFast()
	{
		return chckGetItFast.isSelected();
	}
	
	public boolean isLocalPickup()
	{
		return chckLocal.isSelected();
	}
	
	public boolean isOutlet()
	{
		return chckOutlet.isSelected();
	}
	
	public boolean isReturnsAccepted()
	{
		return chckReturns.isSelected();
	}
	
	public boolean isTopRated()
	{
		return chckTopRated.isSelected();
	}
	
	public boolean isAuction()
	{
		return chckAuction.isSelected();
	}
	
	public boolean isFixedPrice()
	{
		return chckFixedPrice.isSelected();
	}
	
	public boolean isBuyItNow()
	{
		return chckBuyItNow.isSelected();
	}
	
	public boolean isClassified()
	{
		return chckClassified.isSelected();
	}
	
	public boolean isFreeShipping()
	{
		return chckFreeShipping.isSelected();
	}
	
	public boolean isUseExcludedSellerList()
	{
		return chckUseExcluded.isSelected();
	}
	
	public boolean isUseIncludedSellerList()
	{
		return chckUseIncluded.isSelected();
	}
	
}
