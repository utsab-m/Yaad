package yaad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Directions extends JFrame implements ActionListener {
    
    int W = 420, H = 160;
    
    SettingsHandler sh = new SettingsHandler();
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    JButton exit;
    
    public Directions() {
        updateSettings();
        
        setLayout(null);
        
        JLabel directionsLabel = new JLabel(directions());
        directionsLabel.setFont(new Font(fontName, Font.BOLD, 20));
        directionsLabel.setForeground(fontColor);
        directionsLabel.setBounds(10, 0, 400, 120);
        add(directionsLabel);
        
        exit = new JButton("Exit");
        exit.setBounds(160, 120, 100, 30);
        exit.setBackground(buttonColor);
        exit.setForeground(fontColor);
        exit.addActionListener(this);
        add(exit);
        
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
        String text = "<html>" +
                "1) Go to Quizlet<br>" +
                "2) Go to the deck you would like to import<br>" +
                "3) Click the three dots and export<br>" +
                "4) Click copy text and paste it in" +
                "</html>";
        return text;
    }
    
    public void updateSettings() {
        sh.setSettings();
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
    }
    
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton)ae.getSource();
        if (source == exit) setVisible(false);
    }
    
    public static void main(String[] args) {
        new Directions();
    }
    
}
