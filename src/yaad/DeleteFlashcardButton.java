package yaad;

public class DeleteFlashcardButton extends DeleteButton {
    
    Flashcard flashcard;
    
    public DeleteFlashcardButton(FlashcardPanel flashcardPanel, FlashcardActionListener listener) {
        addActionListener(e -> listener.onDeleteFlashcard(flashcardPanel));
    }
}
