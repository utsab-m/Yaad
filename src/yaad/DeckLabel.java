package yaad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DeckLabel extends JLabel {
    
    Deck deck;
    Settings settings;
    String fontName, title;
    Color backgroundColor, fontColor;
    
    public DeckLabel(Deck deck, Settings settings) {
        this.settings = settings;
        this.deck = deck;
        this.fontName = settings.getFontName();
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
                new Study(title);
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
}
