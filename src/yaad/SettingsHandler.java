package yaad;

import com.fasterxml.jackson.map.*;
import com.fasterxml.jackson.databind.*;
import java.awt.*;
import java.io.File;
import java.io.*;

public class SettingsHandler {
    
    Settings settings;
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    File settingsFile = new File(currentPath + File.separator + "settings.json");
    
    ObjectMapper mapper = new ObjectMapper();
    
    public SettingsHandler() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        getSettingsData();
    }
    
    public Color getBackgroundColor() {return settings.getBackgroundColor();}
    
    public Color getButtonColor() {return settings.getButtonColor();}
    
    public Color getFontColor() {return settings.getFontColor();}
    
    public String getFontName() {return settings.getFontName();}
    
    public boolean changed() {
        Settings newSettings = getSettings();
        return !settings.equals(newSettings);
    }
    
    public Settings getSettings() {
        return settings;
    }
    
    public Settings getSettingsData() {
        checkSettingsFile();
        if (settingsFile.length() == 0) defaultSettings();
        try {
            settings = mapper.readValue(settingsFile, Settings.class);
        } catch (Exception e) {
            makeSettingsFile();
            defaultSettings();
            e.printStackTrace();
        }
        return settings;
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
        Settings def = new Settings(Color.DARK_GRAY, Color.BLACK, Color.WHITE, "Raleway");
        return updateSettings(def);        
    }
    
    public void checkSettingsFile() {
        if (!settingsFile.exists()) makeSettingsFile();
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + settings.backgroundColor.getRGB() + ", \"buttonColor\": " + settings.buttonColor.getRGB() + ", \"fontColor\": " + settings.fontColor.getRGB() + 
               ", \"fontName\": " + settings.fontName + "}";
    }
    
}
