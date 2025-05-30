package yaad;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class EditDeckButton extends JButton {
    
    final int W = 60, H = 60;
    
    DeckHandler dh;
    
    Deck deck;
    
    public EditDeckButton(Deck deck, DeckActionListener listener) {
        this.deck = deck;
        
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(W, H));
        setIcon(ImageHandler.scaleImageIcon("edit", W, H));
        addActionListener(e -> listener.onEditDeck(deck));
    }
    
}
