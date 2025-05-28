package yaad;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

public class EditDeck extends JFrame {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    Deck deck;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    JPanel flashcardDisplay = new JPanel();
    List<Flashcard> flashcards;
    
    File[] fileList;
    
    EditDeck(Deck deck) {
        getSettings();
        
        setTitle("Edit " + deck.getTitle());
        setLayout(new BorderLayout());
        
        JLabel deckTitleLabel = new JLabel(deck.getTitle());
        add(deckTitleLabel, BorderLayout.NORTH);
        
        add(flashcardDisplay, BorderLayout.SOUTH);
        
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
    }
    
    public void getSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
}
