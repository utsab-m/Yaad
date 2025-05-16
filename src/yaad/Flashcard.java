package yaad;

public class Flashcard {
    
    private String term, definition;
    
    Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }
    
    Flashcard() {
        
    }
    
    public void setTerm(String term) {
        this.term = term;
    }
    
    public String getTerm() {
        return term;
    }
    
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
    public String getDefinition() {
        return definition;
    }
    
}
