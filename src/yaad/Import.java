package yaad;

import java.util.List;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Import extends JFrame implements ActionListener {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    Deck deck;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    JPanel panel = new JPanel();
    JTextField title;
    JTextArea text;
    JButton directions, save, cancel;
    
    File[] fileList;
    
    Import() {
        
        updateSettings();
        
        setTitle("Import from Quizlet");
        setLayout(new BorderLayout());
        
        JLabel deckTitleLabel = new JLabel("Import from Quizlet");
        deckTitleLabel.setForeground(fontColor);
        deckTitleLabel.setFont(new Font(fontName, Font.BOLD, 30));
        deckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(deckTitleLabel, BorderLayout.NORTH);
        
        panel.setBackground(buttonColor);
        add(panel, BorderLayout.CENTER);   
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        directions = createButton("Directions");
        buttonPanel.add(directions);
        save = createButton("Save");
        buttonPanel.add(save);
        cancel = createButton("Cancel");
        buttonPanel.add(cancel);
        add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(backgroundColor);
    }
    
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setSize(new Dimension(100, 100));
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setFont(new Font(fontName, Font.BOLD, 20));
        button.addActionListener(this);
        return button;
    }
    
    public void updateSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
    public static void main(String[] args) {
        new Import();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton)ae.getSource();
        if (source == directions) {
            
        } else if (source == save) {
            
        }
    }
    
}
