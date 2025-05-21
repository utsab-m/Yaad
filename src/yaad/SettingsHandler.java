package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.*;
import java.io.File;
import java.io.*;

public class SettingsHandler {
    
    Settings settings;
    Color backgroundColor, buttonColor, fontColor;
    int backgroundColorRGB, buttonColorRGB, fontColorRGB;
    String fontName;
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    File file = new File(currentPath + File.separator + "settings.json");
    
    ObjectMapper mapper = new ObjectMapper();
    
    public SettingsHandler() {
        getSettings();
    }
    
    public Color getBackgroundColor() {return backgroundColor;}
    
    public Color getButtonColor() {return buttonColor;}
    
    public Color getFontColor() {return fontColor;}
    
    public String getFontName() {return fontName;}
    
    public boolean changed() {
        Settings newSettings = getSettings();
        return !settings.equals(newSettings);
    }
    
    public boolean write(Settings s) {
        try {
            mapper.writeValue(file, s);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /*
    public boolean write(Color backgroundC, Color buttonC, Color fontC, String fontN) {
        try (FileWriter writer = new FileWriter(file, false)) {
            backgroundColor = backgroundC;
            buttonColor = buttonC;
            fontColor = fontC;
            fontName = fontN;
            
            System.out.println(getJsonSource());
            writer.write(getJsonSource());
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
    */
    public Settings getSettings() {
        checkFile();
        if (file.length() == 0) defaultSettings();
        try {
            settings = mapper.readValue(file, Settings.class);
            /*
            buttonColor = settings.getButtonColor();
            JsonNode node = mapper.readTree(file);
            backgroundColorRGB = node.get("backgroundColor").asInt(-12566464);
            buttonColorRGB = node.get("buttonColor").asInt(-16777216);
            fontColorRGB = node.get("fontColor").asInt(-1);
            fontName = node.get("fontName").asText("Raleway");
            if (backgroundColor != null && (backgroundColor.getRGB() == backgroundColorRGB &&
                buttonColor.getRGB() == buttonColorRGB &&
                fontColor.getRGB() == fontColorRGB)) return false;
            backgroundColor = new Color(backgroundColorRGB);
            buttonColor = new Color(buttonColorRGB);
            fontColor = new Color(fontColorRGB);
*/
        } catch (Exception e) {
            makeSettingsFile();
            defaultSettings();
            e.printStackTrace();
        }
        return settings;
    }
    
    public void makeSettingsFile() {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void defaultSettings() {
        Settings def = new Settings(Color.DARK_GRAY, Color.BLACK, Color.WHITE, "Raleway");
        write(def);        
    }
    
    public void checkFile() {
        if (!file.exists()) makeSettingsFile();
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + backgroundColor.getRGB() + ", \"buttonColor\": " + buttonColor.getRGB() + ", \"fontColor\": " + fontColor.getRGB() + 
               ", \"fontName\": " + fontName + "}";
    }
    
}
