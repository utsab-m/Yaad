package yaad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FlashcardPanel extends JPanel {
    
    Flashcard flashcard;
    JTextField termTextField, definitionTextField;
    DeleteFlashcardButton deleteButton;
    
    public FlashcardPanel(Flashcard flashcard) {
        this.flashcard = flashcard;
        
        setLayout(new BorderLayout());
        
        termTextField = new JTextField(flashcard.getTerm());
        termTextField.setMaximumSize(new Dimension(50, 50));
        
        definitionTextField = new JTextField(flashcard.getDefinition());
        definitionTextField.setMaximumSize(new Dimension(50, 50));
        
        deletebutton = new DeleteFlashcardButton(flashcard, listener);
    }
    
    public JTextField getTermTextField() {return termTextField;}
    
    public JTextField getDefinitionTextField() {return definitionTextField;}
}
