package yaad;

public class DeleteDeckButton extends DeleteButton {
    
    final int W = 60, H = 60;
    
    DeckHandler dh;
    
    DeckPanel deckPanel;
    
    public DeleteDeckButton(DeckPanel deckPanel, DeckActionListener listener) {
        this.deckPanel = deckPanel;
        
        addActionListener(e -> listener.onDeleteDeck(deckPanel));
    }
    
}
