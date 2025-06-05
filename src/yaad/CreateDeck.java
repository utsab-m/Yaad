package yaad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public final class CreateDeck extends JFrame implements ActionListener {
    
    final int W = 600, H = 120;
    
    SettingsHandler sh = new SettingsHandler();
    
    JTextField deckTF;
    JButton next, cancel;
    String deckTitle;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    CreateDeck() {
        
        getSettings();        
        setLayout(null);
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/create.png"));
        Image iconSmooth = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        setIconImage(iconSmooth);
        
        JLabel text = new JLabel("Deck Title");
        text.setFont(new Font(fontName, Font.BOLD, 16));
        text.setBackground(buttonColor);
        text.setForeground(fontColor);
        text.setBounds(20, 20, 100, 40);
        add(text);
        
        deckTF = new JTextField();
        deckTF.setBounds(120, 20, 460, 40);
        add(deckTF);
        
        next = createButton("Next", 240);
        add(next);
        
        cancel = createButton("Cancel", 360);
        add(cancel);
        
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(W, H));
        pack();
        setTitle("Create Deck");
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public JButton createButton(String text, int x) {
        JButton button = new JButton(text);
        button.setFont(new Font(fontName, Font.BOLD, 12));
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setBounds(x, 70, 100, 30);
        button.addActionListener(this);
        return button;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            try {
                deckTitle = deckTF.getText();
                if (deckTitle.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a title.");
                } else {
                    String currentPath = System.getProperty("user.dir");
                    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
                    File dir = new File(filePath);
                    dir.mkdirs();
                    File newFile = new File(filePath + File.separator + deckTitle + ".json");
                    if (newFile.createNewFile()) {
                        AddFlashcards addFlashcards = new AddFlashcards(newFile); 
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Deck with this title already exists!");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } else {
            int c = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?");
            setVisible(!(c == JOptionPane.YES_OPTION));
        }
    }
    
    public void getSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
    public static void main(String args[]) {
        new CreateDeck();
    }
}
