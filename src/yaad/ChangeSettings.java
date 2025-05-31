package yaad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ChangeSettings extends JFrame implements ActionListener {
    
    int W, H = 270;
    
    SettingsHandler sh = new SettingsHandler();
    Settings settings;
    
    JButton save, close, reset, backgroundChooser, buttonChooser, fontColorChooser;
    JButton[] buttons = new JButton[3];
    
    JLabel backgroundLabel, buttonLabel, fontColorLabel, fontLabel, widthLabel;
    JLabel[] labels = new JLabel[5];
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fonts = ge.getAvailableFontFamilyNames();
    JComboBox<String> fontComboBox = new JComboBox(fonts);
    
    JTextField widthTextField = new JTextField(String.valueOf(W));
    
    ChangeSettings() throws IOException {
        
        changeSettings();
        
        setLayout(null);
        
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
        
        widthLabel = createLabel("Change width", 4, 180);
        add(widthLabel);
        
        widthTextField.setBounds(150, 180, 125, 25);
        widthTextField.setHorizontalAlignment(JTextField.CENTER);
        add(widthTextField);
        
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
        button.setBounds(x, 220, 80, 40);
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
        sh.getSettings();
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
        W = sh.getWidth()/2;
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
                int choice = confirmNoChanges();
                if (choice == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
                return;
            }
            settings = getSelections();
            if (sh.updateSettings(settings)) {
                color();
                success();
            }
        } else if (ae.getSource() == close) {
            if (!same()) {
                int choice = confirmNoSave();
                if (choice == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
            } else {
                setVisible(false);
            }
        } else if (ae.getSource() == reset) {
            int choice = confirmReset();
            if (choice == JOptionPane.YES_OPTION) {
                if (sh.defaultSettings()) {
                    color();
                    success();
                }
            }
        }
    }
    
    public int confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message);
    }
    
    public int confirmReset() {
        return confirm("Are you sure you want to reset the settings?");
    }
    
    public int confirmNoChanges() {
        return confirm("Are you sure you don't want to make any changes?");
    }
    
    public int confirmNoSave() {
        return confirm("Are you sure you want to close settings without saving your choices?");
    }
    
    public void success() {
        JOptionPane.showMessageDialog(null, "Successfully saved settings!");
    }
    
    public Settings getSelections() {
        Color backgroundC = backgroundChooser.getBackground();
        Color buttonC = buttonChooser.getBackground();
        Color fontC = fontColorChooser.getBackground();
        String fontN = fontComboBox.getSelectedItem().toString();
        int w = Integer.parseInt(widthTextField.getText());
        return new Settings(backgroundC, buttonC, fontC, fontN, w);
    }
    
    public Color showColorChooser(Color initialColor) {
        Color color = JColorChooser.showDialog(this, "Select a color", initialColor);
        return (color == null) ? initialColor : color;
    }
    
    public boolean same() {
        return sh.equals(getSelections());
    }
    
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
            e.printStackTrace();
        }
    }
}
