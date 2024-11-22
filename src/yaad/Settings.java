package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;

public class Settings extends JFrame implements ActionListener {
    
    JButton save, backgroundChooser, buttonChooser, fontColorChooser;
    JCheckBox darkMode;
    
    Color backgroundColor, buttonColor, fontColor;
    Color[] colors = new Color[3];
    Font font;
    
    ObjectMapper mapper = new ObjectMapper();
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src";
    File dir = new File(filePath);
    File file;
    
    Settings() throws IOException {
        
        setLayout(null);
        
        colors[0] = Color.DARK_GRAY;
        backgroundColor = colors[0];
        colors[1] = Color.BLACK;
        buttonColor = colors[1];
        colors[2] = Color.WHITE;
        fontColor = colors[2];
        
        dir.mkdirs();
        file = new File(filePath + File.separator + "settings.json");
        
        String jsonSource = "{\"backgroundColor\": " + backgroundColor.getRGB() + ", \"buttonColor\": " + buttonColor.getRGB() + ", \"fontColor\": " + fontColor.getRGB() + "}";
        System.out.println(jsonSource);
        JsonNode node = mapper.readTree(jsonSource);
        
        
        System.out.println(file.length());
        
        
        
        save = new JButton("Save");
        save.setBackground(buttonColor);
        save.setForeground(fontColor);
        save.setFont(new Font("Raleway", Font.BOLD, 16));
        save.setBounds(100, 400, 80, 40);
        save.setFocusable(false);
        add(save);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/blackSettings.png"));
        Image i2 = i1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
 
        JLabel backgroundLabel = new JLabel("Change background color");
        backgroundLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        backgroundLabel.setForeground(fontColor);
        backgroundLabel.setBounds(20, 20, 200, 20);
        add(backgroundLabel);
        
        backgroundChooser = new JButton();
        backgroundChooser.setBackground(backgroundColor);
        backgroundChooser.setBounds(240, 20, 20, 20);
        backgroundChooser.addActionListener(this);
        add(backgroundChooser);
        
        JLabel buttonLabel = new JLabel("Change button color");
        buttonLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        buttonLabel.setForeground(fontColor);
        buttonLabel.setBounds(20, 60, 200, 20);
        add(buttonLabel);
        
        buttonChooser = new JButton();
        buttonChooser.setBackground(buttonColor);
        buttonChooser.setBounds(240, 60, 20, 20);
        buttonChooser.addActionListener(this);
        add(buttonChooser);
        
        JLabel fontColorLabel = new JLabel("Change font color");
        fontColorLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        fontColorLabel.setForeground(fontColor);
        fontColorLabel.setBounds(20, 100, 200, 20);
        add(fontColorLabel);
        
        fontColorChooser = new JButton();
        fontColorChooser.setBackground(fontColor);
        fontColorChooser.setBounds(240, 100, 20, 20);
        fontColorChooser.addActionListener(this);
        add(fontColorChooser);
        
        JLabel fontLabel = new JLabel("Change font");
        fontLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        fontLabel.setForeground(fontColor);
        fontLabel.setBounds(20, 140, 200, 20);
        add(fontLabel);
        
        setTitle("Settings");
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(300, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(i2);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backgroundChooser) {
            Color initialColor = backgroundColor;
            backgroundColor = JColorChooser.showDialog(this, "Select a color", initialColor);
            backgroundChooser.setBackground(backgroundColor);
            System.out.println("(" + backgroundColor.getRed() + ", " + backgroundColor.getGreen() + ", " + backgroundColor.getBlue() + ")");
        } else if (ae.getSource() == buttonChooser) {
            Color initialColor = buttonColor;
            buttonColor = JColorChooser.showDialog(this, "Select a color", initialColor);
            buttonChooser.setBackground(buttonColor);
            System.out.println("(" + buttonColor.getRed() + ", " + buttonColor.getGreen() + ", " + buttonColor.getBlue() + ")");
        } else if (ae.getSource() == fontColorChooser) {
            Color initialColor = fontColor;
            fontColor = JColorChooser.showDialog(this, "Select a color", initialColor);
            fontColorChooser.setBackground(fontColor);
            System.out.println("(" + fontColor.getRed() + ", " + fontColor.getGreen() + ", " + fontColor.getBlue() + ")");
        } else if (ae.getSource() == save) {
            colors[0] = backgroundColor;
            colors[1] = buttonColor;
            colors[2] = fontColor;
            try {
                mapper.writeValue(file, colors);
                JOptionPane.showMessageDialog(null, "Successfully saved settings!");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void main(String args[]) {
        try {
            new Settings();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
