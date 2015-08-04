package finditfirst.main;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton stores pertinent eBay API related settings.
 */
public class APISettings
{
    public static final Logger LOG = Program.LOG;

    /** Manually add to this file using a text-editor */
    public static final File APISETTINGS_FILE = new File("api-settings.txt");

    private String EBAY_DEV_KEY = "";
    private String FINDITFIRST_EMAIL = "";
    private String EMAIL_PASSWORD = "";

    private static APISettings settings;

    protected APISettings()
    {
        load();
    }

    public static APISettings getInstance()
    {
        if (settings == null)
        {
            settings = new APISettings();
        }
        return settings;
    }

    public static void create()
    {
        try
        {
            APISETTINGS_FILE.createNewFile();

            FileWriter fw = new FileWriter(APISETTINGS_FILE);
            fw.write("EBAY_DEV_KEY=\r\n");
            fw.write("FINDITFIRST_EMAIL=\r\n");
            fw.write("EMAIL_PASSWORD=");
            fw.close();
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, "Failed to create api-settings file");
        }
    }

    private void load()
    {
        if (!APISETTINGS_FILE.exists())
        {
            create();
            return;
        }

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(APISETTINGS_FILE));
            String line;
            while ((line = br.readLine()) != null)
            {
                String key = line.split("=")[0];
                String value = line.split("=")[1];

                switch(key)
                {
                    case "EBAY_DEV_KEY":
                        EBAY_DEV_KEY = value;
                        break;
                    case "FINDITFIRST_EMAIL":
                        FINDITFIRST_EMAIL = value;
                        break;
                    case "EMAIL_PASSWORD":
                        EMAIL_PASSWORD = value;
                        break;
                    default:
                        break;
                }
            }
            br.close();
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, "Failed to read api-settings file");
        }
    }

    public String getEbayDevKey()
    {
        return EBAY_DEV_KEY;
    }

    public String getFinditfirstEmail()
    {
        return FINDITFIRST_EMAIL;
    }

    public String getEmailPassword()
    {
        return EMAIL_PASSWORD;
    }
}
