package yaad;

import java.util.List;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditDeck extends JFrame implements ActionListener, FlashcardActionListener {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    Deck deck;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    JPanel flashcardDisplay = new JPanel();
    List<Flashcard> flashcards;
    
    JButton save, add, cancel;
    
    File[] fileList;
    
    EditDeck(Deck deck) {
        this.deck = deck; 
        this.flashcards = deck.getFlashcards();
        
        getSettings();
        
        setTitle("Edit " + deck.getTitle());
        setLayout(new BorderLayout());
        
        JLabel deckTitleLabel = new JLabel(deck.getTitle());
        deckTitleLabel.setForeground(fontColor);
        deckTitleLabel.setFont(new Font(fontName, Font.BOLD, 30));
        deckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(deckTitleLabel, BorderLayout.NORTH);
        
        flashcardDisplay.setLayout(new BoxLayout(flashcardDisplay, BoxLayout.Y_AXIS));
        flashcardDisplay.setBackground(buttonColor);
        add(flashcardDisplay, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(flashcardDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getViewport().setBackground(backgroundColor);
        add(scrollPane, BorderLayout.CENTER);
        
        addTextFields();        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        add = createButton("Add Flashcard");
        buttonPanel.add(add);
        save = createButton("Save");
        buttonPanel.add(save);
        cancel = createButton("Cancel");
        buttonPanel.add(cancel);
        add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(backgroundColor);
        
        if (flashcards.isEmpty()) JOptionPane.showMessageDialog(null, deck.getTitle() + " is empty");
    }
    
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setSize(new Dimension(100, 100));
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setFont(new Font(fontName, Font.BOLD, 20));
        button.addActionListener(this);
        return button;
    }
    
    public void getSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
    public void addTextFields() {
        for (Flashcard flashcard: flashcards) {
            FlashcardPanel flashcardPanel = new FlashcardPanel(flashcard, this);
            flashcardDisplay.add(flashcardPanel);
        }
    }
    
    public static void main(String[] args) {
        File file = FileHandler.getDeckFile("Test");
        Deck deck = new Deck(file);
        new EditDeck(deck);
    }
    
    public void fix(Component component) {
        flashcardDisplay.revalidate();
        flashcardDisplay.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton)ae.getSource();
        if (source == add) {
            flashcardDisplay.add(new FlashcardPanel(this));
            fix(flashcardDisplay);
        } else if (source == save) {
            flashcards = new ArrayList();
            for (Component component: flashcardDisplay.getComponents()) {
                FlashcardPanel flashcardPanel = (FlashcardPanel)component;
                String term = flashcardPanel.getTermText(), definition = flashcardPanel.getDefinitionText();
                Flashcard flashcard = new Flashcard(term, definition);
                flashcards.add(flashcard);
            }
            System.out.println(flashcards.toString());
            if (DeckHandler.setFlashcards(deck, flashcards)) {
                deck.setFlashcards(flashcards);
                JOptionPane.showMessageDialog(null, "Successfully saved flashcards to " + deck.getTitle());
                cancel.setText("Exit");
            }
            else JOptionPane.showMessageDialog(null, "Unable to save flashcards to " + deck.getTitle());
        } else if (source == cancel) {
            setVisible(false);
        }
    }
    
    @Override
    public void onDeleteFlashcard(FlashcardPanel flashcardPanel) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this flashcard?");
        if (choice == JOptionPane.YES_OPTION) {
            flashcardDisplay.remove(flashcardPanel);
            fix(flashcardDisplay);
        }
    }
    
}
