package yaad;

import java.io.File;
import java.util.*;

public class Deck {
    
    File file;
    String title;    
    List<Flashcard> flashcards;
    
    public Deck(File file) {
        this.file = file;
        this.title = removeExt(file);
        
        setFlashcards();
    }
    
    public Deck(String deckTitle) {
        this.file = FileHandler.getDeckFile(deckTitle);
        this.title = removeExt(file);
        
        setFlashcards();
    }
    
    public File getFile() {return file;}
    
    public void setFile(File f) {file = f;}
    
    public String getTitle() {return title;}
    
    public void setTitle(String t) {title = t;}
    
    public List<Flashcard> getFlashcards() {return flashcards;}
    
    public void setFlashcards() {flashcards = DeckHandler.getFlashcards(this);}
    
    public void setFlashcards(List flashcards) {this.flashcards = flashcards;}
    
    public static String removeExt(File f) {return f.getName().replace(".json", "");}
    
    public boolean equals(Deck d) {return this.file.equals(d.getFile());}
    
}
