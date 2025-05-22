package yaad;

import com.fasterxml.jackson.databind.*;
import java.awt.*;
import java.io.File;

public class SettingsHandler {
    
    Settings settings;
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    File settingsFile = new File(currentPath + File.separator + "settings.json");
    
    ObjectMapper mapper = new ObjectMapper();
    
    public SettingsHandler() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        setSettings();
    }
    
    public Color getBackgroundColor() {return settings.getBackgroundColor();}
    
    public Color getButtonColor() {return settings.getButtonColor();}
    
    public Color getFontColor() {return settings.getFontColor();}
    
    public String getFontName() {return settings.getFontName();}
    
    public Settings getSettings() {
        return settings;
    }
    
    public void setSettings() {
        settings = getSettingsData();
    }
    
    public Settings getSettingsData() {
        checkSettingsFile();
        Settings s = new Settings();
        if (settingsFile.length() == 0) defaultSettings();
        try {
            Settings s2 = mapper.readValue(settingsFile, Settings.class);
            s.setBackgroundColor(s2.getBackgroundColor());
            s.setButtonColor(s2.getButtonColor());
            s.setFontColor(s2.getFontColor());
            s.setFontName(s2.getFontName());
        } catch (Exception e) {
            makeSettingsFile();
            defaultSettings();
            e.printStackTrace();
        }
        return s;
    }
    
    public boolean updateSettings(Settings s) {
        try {
            mapper.writeValue(settingsFile, s);
            settings = s;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void makeSettingsFile() {
        settingsFile.getParentFile().mkdirs();
        try {
            settingsFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean defaultSettings() {
        Settings def = new Settings();
        return updateSettings(def);        
    }
    
    public void checkSettingsFile() {
        if (!settingsFile.exists()) makeSettingsFile();
    }
    
    public boolean changed() {
        Settings newSettings = getSettingsData();
        System.out.println(newSettings.toString());
        System.out.println(settings.toString());
        return !settings.equals(newSettings);
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + settings.backgroundColor.getRGB() + ", \"buttonColor\": " + settings.buttonColor.getRGB() + ", \"fontColor\": " + settings.fontColor.getRGB() + 
               ", \"fontName\": " + settings.fontName + "}";
    }
    
}
