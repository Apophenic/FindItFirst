package finditfirst.utilities;

import finditfirst.main.Program;

import javax.swing.text.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Static class of
 * Utility methods.
 */
public final class Utils
{
	private static final Logger LOG = Program.LOG;
	
	/** Serialized {@link finditfirst.searchentry.SearchEntry}s are saved to this file */
	public static final File SEARCHES_FILE = new File("searches.lst");
	
	private Utils() {}
	
	/** Serializes an object to the
	 * supplied file.
	 * @param obj  {@code Object} to serialize.
	 * @param file  File to save to.
	 */
	public static void serializeToFile(Object obj, File file)
	{
		ObjectOutputStream oos = null;
		try
		{
			file.createNewFile();
			
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(obj);
			LOG.log(Level.FINE, "File serialized successfully: " + file.getAbsolutePath());
		}
		catch (IOException e)
		{
			LOG.log(Level.SEVERE, "Failed to serialize to file. Make sure file exists: " + file.getAbsolutePath());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				oos.close();
			}
			catch (IOException e)
			{
				LOG.log(Level.WARNING, "IO stream failed to close after serialization");
			}
		}
	}
	
	/** Reads the supplied
	 * Serialized file.
	 * @param file  File containing 
	 * serialized {@code Object}
	 * @return  Serialized {@code Object}
	 */
	public static Object readSerializedFile(File file)
	{
		if(!file.exists())
		{
			return null;
		}
		
		Object obj = null;
		ObjectInputStream ois = null;
		try
		{
			ois = new ObjectInputStream(new FileInputStream(file));
			obj = ois.readObject();
			LOG.log(Level.FINE, "Serialized file read successfully: " + file.getAbsolutePath());
		}
		catch (IOException | ClassNotFoundException e)
		{
			LOG.log(Level.SEVERE, "Failed to read serialized file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				ois.close();
			}
			catch (IOException e)
			{
				LOG.log(Level.WARNING, "Failed to close IO stream after reading serialized file");
			}
		}
		return obj;
	}
	
	/** Creates a {@link PlainDocument} containing
	 * a filter that only allows user input
	 * matching a Zipcode string.
	 * <p>
	 * This is meant to be used with {@code JtextField}s.
	 * @return  New {@code PlainDocument} containing custom
	 * filter.
	 */
	public static PlainDocument getZipcodeFormattedDocument()
	{
		class CustomDocumentFilter extends DocumentFilter implements Serializable	// Anonymous class is not Serializable
		{
			@Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
            {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0,  doc.getLength()));
                sb.insert(offset, string);
                
                if(doc.getLength() >= 5)
                {
                	return;
                }
                	
                if(testInput(sb.toString()))
                {
                	super.insertString(fb, offset, string, attr);
                }

            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
            {
            	Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0,  doc.getLength()));
                sb.replace(offset, offset + length, text);
                
                if(doc.getLength() >= 5)
                {
                	return;
                }
                	
                if(testInput(sb.toString()))
                {
                	super.replace(fb, offset, length, text, attrs);
                }
            }
            
            private boolean testInput(String input)
            {
            	try
            	{
            		Integer.parseInt(input);
            		return true;
            	} catch (NumberFormatException e)
            	{
            		return false;
            	}
            }
		}
		
		PlainDocument document = new PlainDocument();
        document.setDocumentFilter(new CustomDocumentFilter());
        return document;
    }
	
	/** Creates a {@link PlainDocument} containing
	 * a filter that only allows user input
	 * matching a {@code Double} w/ 7 digits max.
	 * <p>
	 * This is meant to be used with {@code JtextField}s.
	 * @return  New {@code PlainDocument} containing custom
	 * filter.
	 */
	public static PlainDocument getDoubleFormattedDocument()
	{
		class CustomDocumentFilter extends DocumentFilter implements Serializable
		{
			@Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
        	{
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0,  doc.getLength()));
                sb.insert(offset, string);
                
                if(doc.getLength() >= 7)
                {
                	return;
                }
                
                if(testInput(sb.toString()))
                {
                	super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
            {
            	Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0,  doc.getLength()));
                sb.replace(offset, offset + length, text);
                
                if(doc.getLength() >= 7)
                {
                	return;
                }
                	
                if(testInput(sb.toString()))
                {
                	super.replace(fb, offset, length, text, attrs);
                }
            }
            
            private boolean testInput(String input)
            {
            	try
            	{
            		Double.parseDouble(input);
            		return true;
            	} 
            	catch (NumberFormatException e)
            	{
            		return false;
            	}
            }
		}
		PlainDocument document = new PlainDocument();
        document.setDocumentFilter(new CustomDocumentFilter());
        return document;
    }
	
	/** Adds the {@code Long} equivalent
	 * of 1 day to a date being represented
	 * in Milliseconds.
	 * @param date  {@code Long} representing date,
	 * in milliseconds.
	 * @return  Date + 1 day
	 */
	public static long rollDate(long date)
	{
		return date + (24 * 60 * 60 * 1000);
	}

}
