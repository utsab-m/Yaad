package yaad;

import java.awt.Color;

public class Settings {
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    int width;
    
    public Settings() {
        backgroundColor = Color.DARK_GRAY;
        buttonColor = Color.BLACK;
        fontColor = Color.WHITE;
        fontName = "Raleway";
        width = 600;
    }
    
    public Settings(Color backgroundColor, Color buttonColor, Color fontColor, String fontName, int width) {
        this.backgroundColor = backgroundColor;
        this.buttonColor = buttonColor;
        this.fontColor = fontColor;
        this.fontName = fontName;
        this.width = width;
    }
    
    public Color getBackgroundColor() {return backgroundColor;}
    
    public void setBackgroundColor(Color c) {backgroundColor = c;} 
    
    public Color getButtonColor() {return buttonColor;}
    
    public void setButtonColor (Color c) {buttonColor = c;} 
    
    public Color getFontColor() {return fontColor;}
    
    public void setFontColor(Color c) {fontColor = c;} 
    
    public String getFontName() {return fontName;}
    
    public void setFontName(String n) {fontName = n;} 
    
    public int getWidth() {return width;}
    
    public void setWidth(int w) {width = w;}
    
    @Override
    public String toString() {
        return "Background color: " + backgroundColor.getRGB() + 
                ", Button color: " + buttonColor.getRGB() + 
                ", Font color: " + fontColor.getRGB() + 
                ", Font name: " + fontName + 
                ", Width: " + width;
    }
    
    public boolean equals(Settings settings) {
        return this.toString().equals(settings.toString());
    }
}
