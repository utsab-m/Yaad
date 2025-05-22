package yaad;

import java.awt.Color;

public class Settings {
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    public Settings() {
        backgroundColor = Color.DARK_GRAY;
        buttonColor = Color.BLACK;
        fontColor = Color.WHITE;
        fontName = "Raleway";
    }
    
    public Settings(Color backgroundC, Color buttonC, Color fontC, String fontN) {
        backgroundColor = backgroundC;
        buttonColor = buttonC;
        fontColor = fontC;
        fontName = fontN;
    }
    
    public Color getBackgroundColor() {return backgroundColor;}
    
    public void setBackgroundColor(Color c) {backgroundColor = c;} 
    
    public Color getButtonColor() {return buttonColor;}
    
    public void setButtonColor (Color c) {buttonColor = c;} 
    
    public Color getFontColor() {return fontColor;}
    
    public void setFontColor(Color c) {fontColor = c;} 
    
    public String getFontName() {return fontName;}
    
    public void setFontName(String n) {fontName = n;} 
    
    @Override
    public String toString() {
        return "Background color: " + backgroundColor.getRGB() + ", Button color: " + buttonColor.getRGB() + ", Font color: " + fontColor.getRGB() + ", Font name: " + fontName;
    }
    
    public boolean equals(Settings settings) {
        return (this.backgroundColor.equals(settings.backgroundColor) &&
                this.buttonColor.equals(settings.buttonColor) && 
                this.fontColor.equals(settings.fontColor) &&
                this.fontName.equals(settings.fontName)
                );
    }
}
