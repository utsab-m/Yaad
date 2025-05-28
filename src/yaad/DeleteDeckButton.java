package yaad;

public class DeleteDeckButton extends DeleteButton {
    
    final int W = 60, H = 60;
    
    DeckHandler dh;
    
    Deck deck;
    
    public DeleteDeckButton(Deck deck, DeckActionListener listener) {
        this.deck = deck;
        
        addActionListener(e -> listener.onDeleteDeck(deck));
    }
    
}
