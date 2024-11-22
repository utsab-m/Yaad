package yaad;

import com.fasterxml.jackson.databind.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Study extends JFrame implements ActionListener, KeyListener {
    
    JButton back, left, right;
    JLabel flashcard, number;
    String deckTitle;
    Flashcard[] flashcards;
    int flashcardNumber = 0, total = 0;
    boolean term = true, definition = false;
    
    Study(String deckTitle) {
        
        this.deckTitle = deckTitle;
        
        setLayout(null);
        
        setFocusable(true);
        addKeyListener(this);
        
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/study.jpeg"));
        setIconImage(image.getImage());
        
        JPanel deckDisplay = new JPanel();
        deckDisplay.setBounds(20, 100, 600, 600);
        deckDisplay.setOpaque(true);
        deckDisplay.setBackground(Color.DARK_GRAY);
        
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + File.separator + "src" + File.separator + "decks";
        File file = new File(filePath + File.separator + deckTitle + ".json");
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            flashcards = mapper.readValue(file, Flashcard[].class);
            total = flashcards.length;
            flashcardNumber++;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        for (Flashcard f: flashcards) {
            System.out.println(f.getTerm());
            System.out.println(f.getDefinition());
        }
        
        JLabel title = new JLabel(deckTitle);
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setBounds(350, 20, 100, 40);
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        
        String firstText = (total == 0) ? "You have no flashcards in this deck. Go back to edit this deck." : "<html>" + flashcards[0].getTerm() + "</html>";
        flashcard = new JLabel(firstText);
        flashcard.setOpaque(true);
        flashcard.setBackground(Color.BLACK);
        flashcard.setForeground(Color.WHITE);
        flashcard.setBounds(200, 80, 400, 300);
        flashcard.setHorizontalAlignment(JLabel.CENTER);
        flashcard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                flip();
            }
        });
        add(flashcard);
        
        left = new JButton("←");
        left.setBackground(Color.BLACK);
        left.setForeground(Color.WHITE);
        left.setBounds(300, 400, 50, 30);
        left.addActionListener(this);
        left.setVisible(false);
        left.setFocusable(false);
        add(left);
        
        number = new JLabel(flashcardNumber + "/" + total);
        number.setOpaque(true);
        number.setBackground(Color.BLACK);
        number.setForeground(Color.WHITE);
        number.setBounds(350, 400, 100, 30);
        number.setHorizontalAlignment(SwingConstants.CENTER);
        add(number);
        
        right = new JButton("→");
        right.setBackground(Color.BLACK);
        right.setForeground(Color.WHITE);
        right.setBounds(450, 400, 50, 30);
        right.addActionListener(this);
        right.setFocusable(false);
        if (total == 0 || total == 1) right.setVisible(false);
        add(right);
        
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(680, 530, 100, 30);
        back.addActionListener(this);
        back.setFocusable(false);
        add(back);
        
        setTitle("Study " + deckTitle);
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(800, 600);
        setVisible(true);
        setResizable(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        } else if (ae.getSource() == left) {
            left();
        } else if (ae.getSource() == right) {
            right();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 32 -> flip();
            case 37 -> {if (left.isShowing()) left();}
            case 39 -> {if (right.isShowing()) right();}
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println(flashcardNumber + "/" + total);
    }
    
    public void left() {
        flashcardNumber--;
        flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getTerm() + "</p></html>");
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber < 2) {
            left.setVisible(false);
        }
        if (total != 0) right.setVisible(true);
    }
    
    public void right() {
        flashcardNumber++;
        flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getTerm() + "</p></html>");
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber == total) {
            right.setVisible(false);
        }
        if (total != 0) left.setVisible(true);
    }
    
    public void flip() {
        if (term) {
            flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getDefinition() + "</p></html>");
            term = false;
            definition = true;
        } else {
            flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getTerm() + "</p></html>");
            definition = false;
            term = true;
        }
    }
    
    public static void main(String args[]) {
        new Study("Capitals");
    }
}
