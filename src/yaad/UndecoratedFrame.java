package yaad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;

public class UndecoratedFrame extends JFrame {
    
    SettingsHandler sh = new SettingsHandler();
    
    Color backgroundColor;
    
    public UndecoratedFrame() {
        updateSettings();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(50, 50, 600, 600, 50, 50));
        setSize(600, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
    }
    
    public void updateSettings() {
        sh.setSettings();
        backgroundColor = sh.getBackgroundColor();
    }
    
    public static void main(String[] args) {
        new UndecoratedFrame();
    }
}
