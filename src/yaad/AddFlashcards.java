package yaad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddFlashcards extends JFrame implements ActionListener {
    
    final int W = 480, H = 140;
    
    SettingsHandler sh = new SettingsHandler();
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    String deckTitle;
    JTextField term, definition;
    JButton add, done, cancel;
    List<Flashcard> flashcards;
    
    ImageIcon check = ImageHandler.scaleImageIcon("check", 32, 32);
    
    File deckFile;
    Deck deck;
    
    AddFlashcards(File file) {
        this.flashcards = new ArrayList();
        this.deckFile = file;
        this.deck = new Deck(file);
        
        getSettings();
        
        this.deckTitle = deckFile.getName().replace(".json", "");
        
        setLayout(null);
        
        Image icon = ImageHandler.scaleImage("add", 128, 128);
        setIconImage(icon);
        
        JLabel termLabel = createLabel("Term", 20);
        add(termLabel);
        
        term = new JTextField();
        term.setBounds(20, 40, 140, 40);
        add(term);
        
        JLabel definitionLabel = createLabel("Definition", 180);
        definitionLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        definitionLabel.setForeground(fontColor);
        definitionLabel.setBounds(180, 5, 100, 40);
        add(definitionLabel);
        
        definition = createTextField(180, 280, 40);
        add(definition);
        
        add = createButton("Add", 180);
        add(add);
        
        done = createButton("Done", 280);
        add(done);
        
        cancel = createButton("Cancel", 380);
        add(cancel);
        
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(W, H));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Add Flashcards to " + deckTitle);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            if (term.getText().equals("") || definition.getText().equals("")) {
                empty();
            } else {
                flashcards.add(new Flashcard(term.getText(), definition.getText()));
                flashcardSuccess(term.getText());
                term.setText("");
                definition.setText("");
            }
        } else if (ae.getSource() == done) {
            if (term.getText().equals("") || definition.getText().equals("")) {
                addToDeck();
            } else {
                flashcards.add(new Flashcard(term.getText(), definition.getText()));
                addToDeck();
            }
            setVisible(false);
        } else if (ae.getSource() == cancel) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel? You will lose all your new flashcards.");
            if (choice == JOptionPane.YES_OPTION) {
                setVisible(false);
            }
        }
    }
    
    public void error() {
        JOptionPane.showMessageDialog(null, "An error occurred.");
    }
    
    public void empty() {
        JOptionPane.showMessageDialog(null, "Must enter text for term and definition.");
    }
    
    public void success(String text) {
        JOptionPane.showMessageDialog(null, text, "Success", JOptionPane.INFORMATION_MESSAGE, check);
    }
    
    public void flashcardSuccess(String termText) {
        String message = "Successfully added '" + termText + "' to '" + deckTitle + "!'";
        success(message);
    }
    
    public void allFlashcardsSuccess() {
        String message = "Successfully added all the card(s) to '" + deckTitle + "'";
        success(message);
    };
    
    public void addToDeck() {
        if (DeckHandler.setFlashcards(deck, flashcards)) {
            JOptionPane.showMessageDialog(null, "Successfully added all the card(s) to '" + deckTitle + "'", "Success", JOptionPane.INFORMATION_MESSAGE, check);
            allFlashcardsSuccess();
            setVisible(false);
        } else error();
    }
    
    public JTextField createTextField(int x, int w, int h) {
        JTextField textField = new JTextField();
        textField.setBounds(x, 40, w, h);
        return textField;
    }
    
    public JButton createButton(String text, int x) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setBounds(x, 90, 80, 30);
        button.addActionListener(this);
        return button;
    }
    
    public JLabel createLabel(String text, int x) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Raleway", Font.BOLD, 22));
        label.setForeground(fontColor);
        label.setBounds(x, 5, 100, 40);
        return label;
    }
    
    public void getSettings() {
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
    }
    
    public static void main(String args[]) {
        new AddFlashcards(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "decks" + File.separator + "Capitals.json"));
    }
}
