package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;

public class Settings extends JFrame implements ActionListener {
    
    JButton save, close, reset, backgroundChooser, buttonChooser, fontColorChooser;
    JButton[] buttons = new JButton[3];
    JLabel backgroundLabel, buttonLabel, fontColorLabel, fontLabel;
    JLabel[] labels = new JLabel[4];
    
    Color backgroundColor, buttonColor, fontColor;
    int backgroundColorRGB, buttonColorRGB, fontColorRGB;
    String fontName;
    
    ObjectMapper mapper = new ObjectMapper();
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src";
    File dir = new File(filePath);
    File file;
    
    Settings() throws IOException {
        
        setLayout(null);
        
        // make directories in case they have not alr been created
        
        dir.mkdirs();
        file = new File(filePath + File.separator + "settings.json");
        
        // tries to read settings file and store colors and stuff in node var

        try {
            read();
        } catch (Exception e) {
            e.printStackTrace();
            backgroundColor = Color.DARK_GRAY;
            buttonColor = Color.BLACK;
            fontColor = Color.WHITE;
        }
        
        System.out.println(getJsonSource());
        
        save = new JButton("Save");
        buttons[0] = save;
        save.setBackground(buttonColor);
        save.setForeground(fontColor);
        save.setFont(new Font(fontName, Font.BOLD, 16));
        save.setBounds(10, 400, 80, 40);
        save.setFocusable(false);
        save.addActionListener(this);
        add(save);
        
        close = new JButton("Close");
        buttons[1] = close;
        close.setBackground(buttonColor);
        close.setForeground(fontColor);
        close.setFont(new Font(fontName, Font.BOLD, 16));
        close.setBounds(110, 400, 80, 40);
        close.setFocusable(false);
        close.addActionListener(this);
        add(close);
        
        reset = new JButton("Reset");
        buttons[2] = reset;
        reset.setBackground(buttonColor);
        reset.setForeground(fontColor);
        reset.setFont(new Font(fontName, Font.BOLD, 16));
        reset.setBounds(210, 400, 80, 40);
        reset.setFocusable(false);
        reset.addActionListener(this);
        add(reset);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/blackSettings.png"));
        Image i2 = i1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
 
        backgroundLabel = new JLabel("Change background color");
        labels[0] = backgroundLabel;
        backgroundLabel.setFont(new Font(fontName, Font.BOLD, 16));
        backgroundLabel.setForeground(fontColor);
        backgroundLabel.setBounds(20, 20, 200, 20);
        add(backgroundLabel);
        
        backgroundChooser = new JButton();
        backgroundChooser.setBackground(backgroundColor);
        backgroundChooser.setBounds(240, 20, 20, 20);
        backgroundChooser.addActionListener(this);
        add(backgroundChooser);
        
        buttonLabel = new JLabel("Change button color");
        labels[1] = buttonLabel;
        buttonLabel.setFont(new Font(fontName, Font.BOLD, 16));
        buttonLabel.setForeground(fontColor);
        buttonLabel.setBounds(20, 60, 200, 20);
        add(buttonLabel);
        
        buttonChooser = new JButton();
        buttonChooser.setBackground(buttonColor);
        buttonChooser.setBounds(240, 60, 20, 20);
        buttonChooser.addActionListener(this);
        add(buttonChooser);
        
        fontColorLabel = new JLabel("Change font color");
        labels[2] = fontColorLabel;
        fontColorLabel.setFont(new Font(fontName, Font.BOLD, 16));
        fontColorLabel.setForeground(fontColor);
        fontColorLabel.setBounds(20, 100, 200, 20);
        add(fontColorLabel);
        
        fontColorChooser = new JButton();
        fontColorChooser.setBackground(fontColor);
        fontColorChooser.setBounds(240, 100, 20, 20);
        fontColorChooser.addActionListener(this);
        add(fontColorChooser);
        
        fontLabel = new JLabel("Change font");
        labels[3] = fontLabel;
        fontLabel.setFont(new Font(fontName, Font.BOLD, 16));
        fontLabel.setForeground(fontColor);
        fontLabel.setBounds(20, 140, 200, 20);
        add(fontLabel);
        
        setTitle("Settings");
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(300, 500));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(i2);
    }
    
    @Override
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
            System.out.println("save");
            System.out.println(getJsonSource());
            write();
            // write();
            color();
        } else if (ae.getSource() == close) {
            read();
            if (backgroundColor != backgroundChooser.getBackground() || buttonColor != buttonChooser.getBackground() || fontColor != fontColorChooser.getBackground()) {
                int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to close settings without saving your choices?");
                if (a == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
            }
        }
    }
    
    public void read() {
        JsonNode node;
        try {
            node = mapper.readTree(file);
            backgroundColorRGB = node.get("backgroundColor").asInt();
            backgroundColor = new Color(backgroundColorRGB);
            buttonColorRGB = node.get("buttonColor").asInt();
            buttonColor = new Color(buttonColorRGB);
            fontColorRGB = node.get("fontColor").asInt();
            fontColor = new Color(fontColorRGB);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void write() {
        try {
            System.out.println(getJsonSource());
            FileWriter f2 = new FileWriter(file, false);
            f2.write(getJsonSource());
            f2.close();
            JOptionPane.showMessageDialog(null, "Successfully saved settings!");
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + backgroundColor.getRGB() + ", \"buttonColor\": " + buttonColor.getRGB() + ", \"fontColor\": " + fontColor.getRGB() + "}";
    }
    
    public void color() {
        getContentPane().setBackground(backgroundColor);
        for (int i = 0; i < buttons.length; i++) {
            System.out.println(buttons[i].getBackground().getRGB());
        }
        for (JButton button: buttons) {
            if (button == null) break;
            button.setBackground(buttonColor);
            button.setForeground(fontColor);
        }
        for (JLabel label: labels) {
            if (label == null) break;
            label.setForeground(fontColor);
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
