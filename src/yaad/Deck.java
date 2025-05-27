package yaad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class Deck extends JPanel {
    
    int width;
    File file;
    String fontName, title;    
    Color fontColor, backgroundColor, borderColor;
    List<Flashcard> flashcards;
    DeckActionListener listener;
    
    public Deck(File f) {
        this.file = f;
        setFlashcards();
    }
    
    public Deck(File f, Settings s, DeckActionListener listener) {
        this.file = f;
        this.backgroundColor = s.getBackgroundColor();
        this.borderColor = s.getButtonColor();
        this.fontColor = s.getFontColor();
        this.fontName = s.getFontName();
        this.width = s.getWidth();
        this.title = removeExt(f);
        this.listener = listener;
        
        setFlashcards();
        setPanel(s);
    }
    
    public void setPanel(Settings settings) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setBorder(BorderFactory.createLineBorder(borderColor));
        
        DeckLabel deckLabel = new DeckLabel(this, settings);
        add(deckLabel, BorderLayout.CENTER);
        
        JButton deleteButton = new DeleteDeckButton(this, listener);
        add(deleteButton, BorderLayout.EAST);
    }
    
    public int getDeckWidth() {return width;}
    
    public void setDeckWidth(int w) {width=w;}
    
    public File getFile() {return file;}
    
    public void setFile(File f) {file = f;}
    
    public String getFontName() {return fontName;}
    
    public void setFontName(String name) {fontName = name;}
        
    public Color getFontColor() {return fontColor;}
    
    public void setFontColor(Color c) {fontColor = c;}
    
    public Color getBackgroundColor() {return backgroundColor;}
    
    public void setBackgroundColor(Color c) {
        setBackground(c);
        backgroundColor = c;
    }
    
    public Color getBorderColor() {return borderColor;}
    
    public void setBorderColor(Color c) {borderColor = c;}
    
    public String getTitle() {return title;}
    
    public void setTitle(String t) {title = t;}
    
    public List<Flashcard> getFlashcards() {return flashcards;}
    
    public void setFlashcards() {flashcards = DeckHandler.getFlashcards(this);}
    
    public static String removeExt(File f) {return f.getName().replace(".json", "");}
    
    public boolean equals(Deck d) {
        return this.file.equals(d.getFile());
    }
}
