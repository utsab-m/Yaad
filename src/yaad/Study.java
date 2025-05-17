package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.temporal.ChronoUnit;

public class Study extends JFrame implements ActionListener, KeyListener {
    
    JButton back, left, right, termButton, definitionButton;
    JLabel flashcard, number;
    String deckTitle;
    Flashcard[] flashcards;
    int flashcardNumber = 0, total = 0;
    boolean term = true;
    boolean termIsShowing = true;
    
    LineBorder thinBorder, thickBorder;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName = "Raleway";
    
    ObjectMapper mapper = new ObjectMapper();
    
    Study(String deckTitle) {
        
        this.deckTitle = deckTitle;
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
        
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + File.separator + "src" + File.separator + "decks";
        File file = new File(filePath + File.separator + deckTitle + ".json");
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            flashcards = mapper.readValue(file, Flashcard[].class);
            total = flashcards.length;
            if (total > 0) flashcardNumber++;
        } catch (IOException e) {
            System.out.println(e);
        }
        
        for (Flashcard f: flashcards) {
            System.out.println(f.getTerm());
            System.out.println(f.getDefinition());
        }
        
        JLabel title = new JLabel("<html>" + deckTitle + "<html>");
        title.setFont(new Font(fontName, Font.BOLD, 20));
        title.setBounds(0, 20, 800, 40);
        title.setForeground(fontColor);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        
        String firstText = (total == 0) ? "You have no flashcards in this deck. Go back to edit this deck." : "<html>" + flashcards[0].getTerm() + "</html>";
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
        
        if (total != 0) {
            
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/add.png"));
            Image iconSmooth = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            JButton button = new JButton((Icon)iconSmooth);
            button.setBounds(250, 250, 100, 100);
            add(button);
        }
        
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
        getContentPane().setPreferredSize(new Dimension(800, 600));
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
            case 37 -> {if (left.isShowing()) left();}
            case 38 -> flip();
            case 39 -> {if (right.isShowing()) right();}
            case 40 -> flip();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println(flashcardNumber + "/" + total);
    }
    
    public void left() {
        flashcardNumber--;
        String flashcardText = term == true ? flashcards[flashcardNumber - 1].getTerm() : flashcards[flashcardNumber - 1].getDefinition();
        flashcard.setText("<html><p style=\"text-align: center;\">" + flashcardText + "</p></html>");
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber < 2) {
            left.setVisible(false);
        }
        if (total != 0) right.setVisible(true);
    }
    
    public void right() {
        flashcardNumber++;
        String flashcardText = (term == true) ? flashcards[flashcardNumber - 1].getTerm() : flashcards[flashcardNumber - 1].getDefinition();
        flashcard.setText("<html><p style=\"text-align: center;\">" + flashcardText + "</p></html>");
        number.setText(Integer.toString(flashcardNumber) + "/" + total);
        if (flashcardNumber == total) {
            right.setVisible(false);
        }
        if (total != 0) left.setVisible(true);
    }
    
    public void showCard(int number) {
        String flashcardText = (term == true) ? flashcards[flashcardNumber - 1].getTerm() : flashcards[flashcardNumber - 1].getDefinition();
    }
    
    public void flip() {
        if (termIsShowing) {
            flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getDefinition() + "</p></html>");
            termIsShowing = false;
        } else {
            flashcard.setText("<html><p style=\"text-align: center;\">" + flashcards[flashcardNumber - 1].getTerm() + "</p></html>");
            termIsShowing = true;
        }
    }
    
    public void getSettings() {
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "settings.json");
        file.mkdirs();
        try {
            JsonNode node = mapper.readTree(file);
            backgroundColor = new Color(node.get("backgroundColor").asInt());
            buttonColor = new Color(node.get("buttonColor").asInt());
            fontColor = new Color(node.get("fontColor").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        new Study("Capitals");
    }
}
