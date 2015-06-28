//
//   eBay search automation and notification tool.
//
//   https://github.com/Apophenic
//   
//   Copyright (c) 2015 Justin Dayer (jdayer9@gmail.com)
//   
//   Permission is hereby granted, free of charge, to any person obtaining a copy
//   of this software and associated documentation files (the "Software"), to deal
//   in the Software without restriction, including without limitation the rights
//   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//   copies of the Software, and to permit persons to whom the Software is
//   furnished to do so, subject to the following conditions:
//   
//   The above copyright notice and this permission notice shall be included in
//   all copies or substantial portions of the Software.
//   
//   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//   THE SOFTWARE.

package finditfirst.main;

import finditfirst.ebay.API;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** FindItFirst application entry point.
 * <p>
 * Before you can begin using this application,
 * you'll need an eBay Developer Key, acquired at:
 * {@code https://go.developer.ebay.com/what-ebay-api}
 * <p>
 * You'll also need to supply an email address and password
 * to send notifications from. Technically, you could use
 * the same address to send and receive notifications.
 * <p>
 * See ReadMe.txt for more info.
 * @see {@link API#EBAY_DEV_KEY_STRING}
 * @see {@link finditfirst.utilities.MailService#FINDITFIRST_EMAIL}
 * @see {@link finditfirst.utilities.MailService#EMAIL_PASSWORD}
 * <p>
 * @see {@link Settings#getSendToEmailAddress()}
 */
public class Program
{
	/** Serial version used by all {@code Serializable} objects in this program */
	public static final long serialVersionUID = 1000L;
	
	/** Logger instance used by all classes */
	public static Logger LOG = Logger.getGlobal();

	public static void main(String[] args)
	{
		Settings.load();
		
		LOG.setLevel(Level.WARNING);
		
		init();
	}
	
	public static void init()
	{
		EventQueue.invokeLater(() ->
		{
            try
            {
                GUI window = new GUI();
                window.frame.setVisible(true);
            }
            catch (Exception e)
            {
                LOG.log(Level.SEVERE, "Failed to initialize FindItFirst");
            }
        });
	}
}
