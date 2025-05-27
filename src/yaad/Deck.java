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
    List<Flashcard> flashcards = new ArrayList();
    DeckActionListener listener;
    
    public Deck(File f) {
        this.file = f;
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
        
        setPanel();
    }
    
    public void setPanel() {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setBorder(BorderFactory.createLineBorder(borderColor));
        
        JLabel deckLabel = createDeckLabel();
        add(deckLabel, BorderLayout.CENTER);
        
        JButton deleteButton = new DeleteDeckButton(this, listener);
        add(deleteButton, BorderLayout.EAST);
    }
    
    public JLabel createDeckLabel() {
        Deck d = this;
        Font bold = new Font(fontName, Font.BOLD, 22);
        Font italic = new Font(fontName, Font.ITALIC, 22);
        
        JLabel deckLabel = new JLabel(title);
        deckLabel.setText(title);
        deckLabel.setOpaque(false);
        deckLabel.setFont(bold);
        deckLabel.setForeground(fontColor);
        deckLabel.setBackground(backgroundColor);
        deckLabel.setMaximumSize(new Dimension(width, 50));
        deckLabel.setHorizontalAlignment(SwingConstants.CENTER);
        deckLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckLabel.setFocusable(false);
        deckLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                new Study(d);
            }
            @Override
            public void mouseEntered(MouseEvent me) {
                JLabel source = (JLabel)me.getSource();
                String sourceText = source.getText();
                source.setFont(italic);
                source.setText("<html><u>" + sourceText + "</u></html>");
                source.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent me) {
                JLabel source = (JLabel)me.getSource();
                String sourceText = source.getText();
                String text = sourceText.replace("<html><u>", "");
                text = text.replace("</u></html>", "");
                source.setFont(bold);
                source.setText(text);
                source.setForeground(fontColor);
            }
        });
        
        return deckLabel;
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
    
    public void setFlashcards() {}
    
    public static String removeExt(File f) {return f.getName().replace(".json", "");}
    
    public boolean equals(Deck d) {
        return this.file.equals(d.getFile());
    }
}
