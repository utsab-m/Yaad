package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;

public class ChangeSettings extends JFrame implements ActionListener {
    
    final int W = 300, H = 250;
    
    SettingsHandler settingsHandler = new SettingsHandler();
    Settings settings;
    
    JButton save, close, reset, backgroundChooser, buttonChooser, fontColorChooser;
    JButton[] buttons = new JButton[3];
    
    JLabel backgroundLabel, buttonLabel, fontColorLabel, fontLabel;
    JLabel[] labels = new JLabel[4];
    
    Color backgroundColor, buttonColor, fontColor;
    
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fonts = ge.getAvailableFontFamilyNames();
    JComboBox<String> fontComboBox = new JComboBox(fonts);
    
    int backgroundColorRGB, buttonColorRGB, fontColorRGB;
    String fontName;
    
    ObjectMapper mapper = new ObjectMapper();
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src";
    File dir = new File(filePath);
    File file;
    
    ChangeSettings() throws IOException {
        
        changeSettings();
        
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
        
        
        save = createStyledButton("Save", 0, 10);
        add(save);
        
        close = createStyledButton("Close", 1, 110);
        add(close);
        
        reset = createStyledButton("Reset", 2, 210);
        add(reset);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/blackSettings.png"));
        Image i2 = i1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
 
        backgroundLabel = createLabel("Change background color", 0, 20);
        add(backgroundLabel);
        
        backgroundChooser = createColorChooser(backgroundColor, 20);
        add(backgroundChooser);
        
        buttonLabel = createLabel("Change button color", 1, 60);
        add(buttonLabel);
        
        buttonChooser = createColorChooser(buttonColor, 60);
        add(buttonChooser);
        
        fontColorLabel = createLabel("Change font color", 2, 100);
        add(fontColorLabel);
        
        fontColorChooser = createColorChooser(fontColor, 100);
        add(fontColorChooser);
        
        fontLabel = createLabel("Change font", 3, 140);
        add(fontLabel);
        
        fontComboBox.setBounds(150, 140, 125, 25);
        fontComboBox.setEditable(true);
        fontComboBox.setSelectedItem("Raleway");
        add(fontComboBox);
        
        setTitle("Settings");
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(W, H));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(i2);
    }
    
    public JButton createStyledButton(String text, int n, int x) {
        JButton button = new JButton(text);
        buttons[n] = button;
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setFont(new Font(fontName, Font.BOLD, 16));
        button.setBounds(x, 200, 80, 40);
        button.setFocusable(false);
        button.addActionListener(this);
        return button;
    }
    
    public JButton createColorChooser(Color color, int y) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setBounds(255, y, 20, 20);
        button.addActionListener(this);
        return button;
    }
    
    public JLabel createLabel(String text, int n, int y) {
        JLabel label = new JLabel(text);
        labels[n] = label;
        label.setFont(new Font(fontName, Font.BOLD, 16));
        label.setForeground(fontColor);
        label.setBounds(20, y, 200, 20);
        return label;
    }
    
    public void changeSettings() {
        settingsHandler.getSettings();
        backgroundColor = settingsHandler.getBackgroundColor();
        buttonColor = settingsHandler.getButtonColor();
        fontColor = settingsHandler.getFontColor();
        fontName = settingsHandler.getFontName();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backgroundChooser) {
            backgroundColor = showColorChooser(backgroundColor);
            backgroundChooser.setBackground(backgroundColor);
            System.out.println("(" + backgroundColor.getRed() + ", " + backgroundColor.getGreen() + ", " + backgroundColor.getBlue() + ")");
        } else if (ae.getSource() == buttonChooser) {
            buttonColor = showColorChooser(buttonColor);
            buttonChooser.setBackground(buttonColor);
            System.out.println("(" + buttonColor.getRed() + ", " + buttonColor.getGreen() + ", " + buttonColor.getBlue() + ")");
        } else if (ae.getSource() == fontColorChooser) {
            fontColor = showColorChooser(fontColor);
            fontColorChooser.setBackground(fontColor);
            System.out.println("(" + fontColor.getRed() + ", " + fontColor.getGreen() + ", " + fontColor.getBlue() + ")");
        } else if (ae.getSource() == save) {
            System.out.println(same());
            if (same()) {
                int input = JOptionPane.showConfirmDialog(null, "Are you sure you don't want to make any changes?");
                if (input == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
                return;
            }
            System.out.println("save");
            System.out.println(getJsonSource());
            if (write()) {
                color();
                JOptionPane.showMessageDialog(null, "Successfully saved settings!");
            }
        } else if (ae.getSource() == close) {
            if (!same()) {
                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to close settings without saving your choices?");
                if (input == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
            } else {
                setVisible(false);
            }
        } else if (ae.getSource() == reset) {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the settings?");
            if (input == JOptionPane.YES_OPTION) {
                backgroundColor = Color.DARK_GRAY;
                backgroundChooser.setBackground(backgroundColor);
                buttonColor = Color.BLACK;
                buttonChooser.setBackground(buttonColor);
                fontColor = Color.WHITE;
                fontColorChooser.setBackground(fontColor);
                if (write()) {
                    color();
                    JOptionPane.showMessageDialog(null, "Successfully saved settings!");
                }
            }
        }
    }
    
    public Color showColorChooser(Color initialColor) {
        Color color = JColorChooser.showDialog(this, "Select a color", initialColor);
        return (color == null) ? initialColor : color;
    }
    
    public boolean same() {
        // check if changes have been made to the settings
        // if changes have been made, return false. otherwise, return true.
        read();
        return (backgroundColor.getRGB() == backgroundChooser.getBackground().getRGB()
                    && buttonColor.getRGB() == buttonChooser.getBackground().getRGB() 
                    && fontColor.getRGB() == fontColorChooser.getBackground().getRGB());
    }
    
    /*
    public void read() {
        JsonNode node;
        try {
            node = mapper.readTree(file);
            backgroundColorRGB = node.get("backgroundColor").asInt(-12566464);
            backgroundColor = new Color(backgroundColorRGB);
            buttonColorRGB = node.get("buttonColor").asInt(-16777216);
            buttonColor = new Color(buttonColorRGB);
            fontColorRGB = node.get("fontColor").asInt(-1);
            fontColor = new Color(fontColorRGB);
            fontName = node.get("fontName").asText("Raleway");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public boolean write() {
        try {
            backgroundColor = backgroundChooser.getBackground();
            buttonColor = buttonChooser.getBackground();
            fontColor = fontColorChooser.getBackground();
            fontName = (String)fontComboBox.getEditor().getItem();
            
            System.out.println(getJsonSource());
            FileWriter f2 = new FileWriter(file, false);
            f2.write(getJsonSource());
            f2.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + backgroundColor.getRGB() + ", \"buttonColor\": " + buttonColor.getRGB() + ", \"fontColor\": " + fontColor.getRGB() + 
               ", \"fontName\": " + fontName + "}";
    }
    */
    
    public void color() {
        getContentPane().setBackground(backgroundColor);
        for (JButton button: buttons) {
            System.out.println(button.getBackground().getRGB());
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
            new ChangeSettings();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
