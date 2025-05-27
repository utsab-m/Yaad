package yaad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.awt.*;

public class SettingsHandler {
    
    Settings settings;
    
    ObjectMapper mapper = new ObjectMapper();
    
    public SettingsHandler() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        setSettings();
        updateSettings();
    }
    
    public Color getBackgroundColor() {return settings.getBackgroundColor();}
    
    public Color getButtonColor() {return settings.getButtonColor();}
    
    public Color getFontColor() {return settings.getFontColor();}
    
    public String getFontName() {return settings.getFontName();}
    
    public int getWidth() {return settings.getWidth();}
    
    public Settings getSettings() {
        return settings;
    }
    
    public void setSettings() {
        settings = getSettingsData();
    }
    
    public Settings getSettingsData() {
        FileHandler.checkSettingsFile();
        Settings s = new Settings();
        try {
            String settingsString = FileHandler.readSettingsFile();
            s = mapper.readValue(settingsString, Settings.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public boolean updateSettings(Settings s) {
        FileHandler.checkSettingsFile();
        try {
            mapper.writeValue(FileHandler.getSettingsFile(), s);
            settings = s;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateSettings() {
        Settings s = new Settings(getBackgroundColor(), getButtonColor(), getFontColor(), getFontName(), 600);
        FileHandler.checkSettingsFile();
        try {
            mapper.writeValue(FileHandler.getSettingsFile(), s);
            settings = s;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean defaultSettings() {
        Settings def = new Settings();
        return updateSettings(def);        
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
    
    public boolean equals(Settings settings) {
        return this.settings.equals(settings);
    }
    
    public static void main(String[] LOL) {
        System.out.println(LOL.length);
        new SettingsHandler();
    }
}
