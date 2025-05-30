package yaad;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Directions extends JFrame {
    
    int W = 300, H = 400;
    
    SettingsHandler sh = new SettingsHandler();
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    public Directions() {
        updateSettings();
        
        JTextArea directionsLabel = new JTextArea(directions());
        directionsLabel.setSize(200, 300);
        add(directionsLabel);
        
        setTitle("Quizlet Import Directions");
        getContentPane().setPreferredSize(new Dimension(W, H));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
    }
    
    public String directions() {
        String text = "1) Go to Quizlet\n" +
                "2) Go to the deck you would like to import\n" +
                "3) Click the three dots and export\n" +
                "4) Click copy text and paste it in";
        return text;
    }
    
    public void updateSettings() {
        sh.setSettings();
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
    }
    
    public static void main(String[] args) {
        new Directions();
    }
    
}
