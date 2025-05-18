package yaad;

import java.awt.*;
import javax.swing.*;

public class Test2 extends JFrame {
    
    public Test2() {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel("Label " + i);
            label.setSize(600, 200);
            panel.add(label);
        }
        
        add(panel);
        
        panel.remove(1);
        
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new Test2();
    }
    
}
