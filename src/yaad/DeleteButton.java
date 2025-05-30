package yaad;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class DeleteButton extends JButton {
    
    final int W = 60, H = 60;
    
    public DeleteButton() {
        setBackground(Color.RED);
        Dimension dimension = new Dimension(W, H);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setIcon(ImageHandler.scaleImageIcon("delete", W, H));
    }
    
}
