package yaad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FlashcardPanel extends JPanel {
    
    Flashcard flashcard;
    FlashcardActionListener listener;
    JTextField termTextField, definitionTextField;
    DeleteFlashcardButton deleteButton;
    Font font = new Font("Raleway", Font.BOLD, 12);
    
    public FlashcardPanel(FlashcardActionListener listener) {
        this.listener = listener;
        
        setUpPanel("", "");
    }
    
    public FlashcardPanel(Flashcard flashcard, FlashcardActionListener listener) {
        this.flashcard = flashcard;
        this.listener = listener;
        
        setUpPanel(flashcard.getTerm(), flashcard.getDefinition());
    }
    
    public void setUpPanel(String termText, String definitionText) {
        setLayout(new BorderLayout());
        
        termTextField = createTextField(termText);
        add(termTextField, BorderLayout.WEST);
        
        definitionTextField = createTextField(definitionText);
        add(definitionTextField, BorderLayout.CENTER);
        
        deleteButton = new DeleteFlashcardButton(this, listener);
        add(deleteButton, BorderLayout.EAST);
    }
    
    public JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(100, 50));
        textField.setFont(font);
        return textField;
    }
    
    public String getTermText() {return termTextField.getText();}
    
    public String getDefinitionText() {return definitionTextField.getText();}
    
    public Flashcard getFlashcard() {return flashcard;}
}
