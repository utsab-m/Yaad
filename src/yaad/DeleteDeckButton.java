package yaad;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DeleteDeckButton extends JButton {
    
    final int W = 60, H = 60;
    
    DeckHandler dh;
    
    Deck deck;
    
    public DeleteDeckButton(Deck deck, DeckActionListener listener) {
        this.deck = deck;
        
        setBackground(Color.RED);
        setPreferredSize(new Dimension(W, H));
        setIcon(ImageHandler.scaleImageIcon("delete", W, H));
        addActionListener(e -> listener.onDeleteDeck(deck));
    }
    
}
