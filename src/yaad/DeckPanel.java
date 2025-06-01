package yaad;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DeckPanel extends JPanel {
    
    Deck deck;
    int width;
    String fontName, title;    
    Color fontColor, backgroundColor, borderColor;
    DeckActionListener listener;
    
    public DeckPanel(Deck d, Settings s, DeckActionListener listener) {
        this.deck = d;
        this.backgroundColor = s.getBackgroundColor();
        this.borderColor = s.getButtonColor();
        this.fontColor = s.getFontColor();
        this.fontName = s.getFontName();
        this.width = s.getWidth();
        this.title = deck.getTitle();
        this.listener = listener;
        
        setPanel(s);
    }
    
    public void setPanel(Settings settings) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setBorder(BorderFactory.createLineBorder(borderColor));
        
        DeckLabel deckLabel = new DeckLabel(deck, settings);
        add(deckLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        
        JButton deleteButton = new DeleteDeckButton(this, listener);
        buttonPanel.add(deleteButton, BorderLayout.WEST);
        
        JButton editButton = new EditDeckButton(deck, listener);
        buttonPanel.add(editButton, BorderLayout.EAST);
        
        add(buttonPanel, BorderLayout.EAST);
    }
    
    public Deck getDeck() {return deck;}
    
    public String getDeckTitle() {return deck.getTitle();}
    
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
    
}
