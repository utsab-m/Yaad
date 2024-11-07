package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Settings extends JFrame {
    
    JButton save;
    JCheckBox darkMode;
    
    Settings() {
        
        setLayout(null);
        
        save = new JButton("Save");
        save.setBackground(Color.BLACK);
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Raleway", Font.BOLD, 16));
        save.setBounds(100, 400, 80, 40);
        add(save);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/settings.png"));
        Image i2 = i1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
 
        JLabel backgroundLabel = new JLabel("Change background color");
        backgroundLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        backgroundLabel.setForeground(Color.WHITE);
        backgroundLabel.setBounds(20, 20, 200, 20);
        add(backgroundLabel);
        
        JLabel buttonLabel = new JLabel("Change button color");
        buttonLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        buttonLabel.setForeground(Color.WHITE);
        buttonLabel.setBounds(20, 60, 200, 20);
        add(buttonLabel);
        
        JLabel fontLabel = new JLabel("Change font");
        fontLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        fontLabel.setForeground(Color.WHITE);
        fontLabel.setBounds(20, 100, 200, 20);
        add(fontLabel);
        
        setTitle("Settings");
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(300, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(i2);
    }
    
    public static void main(String args[]) {
        new Settings();
    }
}
