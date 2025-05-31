package yaad;

import java.util.List;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Import extends JFrame implements ActionListener {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    Deck deck;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    JPanel panel = new JPanel();
    JTextField title;
    JTextArea text;
    JButton directions, save, exit;
    
    File[] fileList;
    
    Import() {
        
        updateSettings();
        
        setTitle("Import from Quizlet");
        setLayout(new BorderLayout());
        
        JLabel deckTitleLabel = new JLabel("Import from Quizlet");
        deckTitleLabel.setForeground(fontColor);
        deckTitleLabel.setFont(new Font(fontName, Font.BOLD, 30));
        deckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(deckTitleLabel, BorderLayout.NORTH);
        
        panel.setBackground(buttonColor);
        panel.setForeground(fontColor);
        text = new JTextArea();
        text.setCaretPosition(0);
        text.setFont(new Font(fontName, Font.PLAIN, 12));
        text.setBackground(buttonColor);
        text.setForeground(fontColor);
        panel.add(text);
        add(panel, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        directions = createButton("Directions");
        buttonPanel.add(directions);
        save = createButton("Save");
        buttonPanel.add(save);
        exit = createButton("Exit");
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(backgroundColor);
    }
    
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setSize(new Dimension(100, 100));
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setFont(new Font(fontName, Font.BOLD, 20));
        button.setFocusable(false);
        button.addActionListener(this);
        return button;
    }
    
    public void updateSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
    public static void main(String[] args) {
        new Import();
    }
    
    public void updateDeck(String deckTitle, String text) {
        FileHandler.createDeckFile(deckTitle);
        List<Flashcard> flashcards = DeckHandler.getFlashcards(text);
        File deckFile = FileHandler.getDeckFile(deckTitle);
        Deck deck = new Deck(deckFile);
        DeckHandler.setFlashcards(deck, flashcards);
        JOptionPane.showMessageDialog(null, "Deck " + deckTitle + " successfully updated");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton)ae.getSource();
        if (source == directions) {
            new Directions();
        } else if (source == save) {
            
            String importedText = text.getText();
            importedText = importedText.trim();
            if (importedText.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "You haven't imported anything");
            } else {
                String deckTitle = JOptionPane.showInputDialog(null, "Enter title");
                if (FileHandler.deckFileExists(deckTitle)) {
                    int choice = JOptionPane.showConfirmDialog(null, "This deck already exists. Would you like to overwrite?");
                    if (choice == JOptionPane.YES_OPTION) {
                        updateDeck(deckTitle, importedText);
                    }
                } else {
                    updateDeck(deckTitle, importedText);
                }
            }
        } else if (source == exit) {
            setVisible(false);
        }
    }
    
}
