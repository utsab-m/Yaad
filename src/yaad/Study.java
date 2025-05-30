package yaad;

import java.util.List;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Study extends JFrame implements ActionListener, KeyListener {
    
    final int W = 800, H = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    Deck deck;
    
    JButton back, left, right, termButton, definitionButton;
    JLabel flashcard, number;
    String deckTitle;
    List<Flashcard> flashcards;
    int flashcardNumber = 0, total = 0;
    boolean term = true, termIsShowing = true;
    
    LineBorder thinBorder, thickBorder;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName = "Raleway";
    
    public Study(Deck deck) {
        this.deck = deck;
        this.deckTitle = deck.getTitle();
        
        getSettings();
        setLayout(null);
        
        thinBorder = (LineBorder)BorderFactory.createLineBorder(fontColor, 1);
        thickBorder = (LineBorder)BorderFactory.createLineBorder(fontColor, 2);
        
        setFocusable(true);
        addKeyListener(this);
        
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/study.jpeg"));
        setIconImage(image.getImage());
        
        JPanel deckDisplay = new JPanel();
        deckDisplay.setBounds(100, 100, 600, 600);
        deckDisplay.setOpaque(true);
        deckDisplay.setBackground(buttonColor);
        
        flashcards = deck.getFlashcards();
        total = flashcards.size();
        if (total > 0) flashcardNumber++;
        
        for (Flashcard flashcard: flashcards) {
            System.out.println(flashcard.getTerm());
            System.out.println(flashcard.getDefinition());
        }
        
        JLabel title = new JLabel("<html>" + deckTitle + "<html>");
        title.setFont(new Font(fontName, Font.BOLD, 20));
        title.setBounds(0, 20, 800, 40);
        title.setForeground(fontColor);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        
        String firstText = (total == 0) ? "You have no flashcards in this deck. Go back to edit this deck." : "<html>" + flashcards.get(0).getTerm() + "</html>";
        flashcard = new JLabel(firstText);
        flashcard.setOpaque(true);
        flashcard.setBackground(buttonColor);
        flashcard.setForeground(fontColor);
        flashcard.setBounds(200, 80, 400, 300);
        flashcard.setHorizontalAlignment(JLabel.CENTER);
        flashcard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                flip();
            }
        });
        add(flashcard);
        
        left = createStyledButton("←", 300, 400, 50, 30);
        left.setVisible(false);
        add(left);
        
        number = new JLabel(flashcardNumber + "/" + total);
        number.setOpaque(true);
        number.setBackground(buttonColor);
        number.setForeground(fontColor);
        number.setBounds(350, 400, 100, 30);
        number.setHorizontalAlignment(SwingConstants.CENTER);
        add(number);
        
        right = createStyledButton("→", 450, 400, 50, 30);
        if (total < 2) right.setVisible(false);
        add(right);
        
        back = createStyledButton("Back", 680, 550, 100, 30);
        add(back);
        
        termButton = createStyledButton("Term", 300, 450, 100, 30);
        termButton.setBorder(BorderFactory.createLineBorder(fontColor, 2));
        add(termButton);
        
        definitionButton = createStyledButton("Definition", 400, 450, 100, 30);
        definitionButton.setBorder(BorderFactory.createLineBorder(fontColor, 1));
        add(definitionButton);
        
        setTitle("Study " + deckTitle);
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(W, H));
        pack();
        setVisible(true);
        setResizable(false);
        
    }
    
    public JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        button.setFocusable(false);
        button.addActionListener(this);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        } else if (ae.getSource() == left) {
            left();
        } else if (ae.getSource() == right) {
            right();
        } else if (ae.getSource() == termButton) {
            term = true;
            termButton.setBorder(thickBorder);
            definitionButton.setBorder(thinBorder);
            flip();
        } else if (ae.getSource() == definitionButton) {
            term = false;
            definitionButton.setBorder(thickBorder);
            termButton.setBorder(thinBorder);
            flip();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 8 -> setVisible(false);
            case 32 -> flip();
            case 37 -> {
                if (left.isShowing()) {
                    pressButton(left);
                    left();
                }
            }
            case 38 -> flip();
            case 39 -> {
                if (right.isShowing()) {
                    pressButton(right);
                    right();
                }
            }
            case 40 -> flip();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 37 -> {if (left.isShowing()) unpressButton(left);}
            case 39 -> {if (right.isShowing()) unpressButton(right);}
        }
        System.out.println(flashcardNumber + "/" + total);
    }
    
    public void left() {
        flashcardNumber--;
        String flashcardText = getFlashcardText();
        flashcard.setText(getCenteredText(flashcardText));
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber < 2) {
            left.setVisible(false);
        }
        if (total != 0) right.setVisible(true);
    }
    
    public void right() {
        flashcardNumber++;
        String flashcardText = getFlashcardText();
        flashcard.setText(getCenteredText(flashcardText));
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber == total) {
            right.setVisible(false);
        }
        if (total != 0) left.setVisible(true);
    }
    
    public void pressButton(JButton button) {
        button.getModel().setPressed(true);
    }
    
    public void unpressButton(JButton button) {
        button.getModel().setPressed(false);
    }
    
    public String getFlashcardText() {
        String flashcardText = term == true ? flashcards.get(flashcardNumber - 1).getTerm() : flashcards.get(flashcardNumber - 1).getDefinition();
        return flashcardText;
    }
    
    public void showCard(int number) {
        String flashcardText = getFlashcardText();
    }
    
    public void flip() {
        if (termIsShowing) {
            String definition = flashcards.get(flashcardNumber - 1).getDefinition();
            flashcard.setText(getCenteredText(definition));
            termIsShowing = false;
        } else {
            String term = flashcards.get(flashcardNumber - 1).getTerm();
            flashcard.setText(getCenteredText(term));
            termIsShowing = true;
        }
    }
    
    public String getCenteredText(String text) {
        return "<html><p style=\"text-align: center;\">" + text + "</p></html>";
    }
    
    public void getSettings() {
        Settings settings = sh.getSettings();
        backgroundColor = settings.getBackgroundColor();
        buttonColor = settings.getButtonColor();
        fontColor = settings.getFontColor();
        fontName = settings.getFontName();
    }
    
    public static void main(String[] args) {
        File file = FileHandler.getDeckFile("Capitals");
        System.out.println(file.getName());
        Deck d = new Deck(file);
        new Study(d);
    }
}
