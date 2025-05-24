package yaad;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageHandler {
    
    public static Image scaleImage(String imageName, int width, int height) {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/" + imageName + ".png"));
        Image i2 = i1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        return i2;
    }
    
    public static ImageIcon scaleImageIcon(String imageName, int width, int height) {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/" + imageName + ".png"));
        Image i2 = i1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        
        return i3;
    }
}
