package yaad;

public class DeleteFlashcardButton extends DeleteButton {
    
    Flashcard flashcard;
    
    public DeleteFlashcardButton(Flashcard flashcard, FlashcardActionListener listener) {
        addActionListener(e -> listener.onDeleteFlashcard(flashcard));
    }
}
