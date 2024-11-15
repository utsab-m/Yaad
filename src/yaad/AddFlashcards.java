package yaad;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddFlashcards extends JFrame implements ActionListener {
    
    String deckTitle;
    JTextField term, definition;
    JButton add, done, cancel;
    ArrayList<Flashcard> deck = new ArrayList<>();
    
    ImageIcon check = new ImageIcon(ClassLoader.getSystemResource("images/check.png")); 
    Image iconSmooth = check.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    
    AddFlashcards(String deckTitle) {
        
        this.deckTitle = deckTitle;
        
        setLayout(null);
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/add.png"));
        Image iconSmooth = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        setIconImage(iconSmooth);
        
        JLabel termLabel = new JLabel("Term");
        termLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        termLabel.setForeground(Color.WHITE);
        termLabel.setBounds(20, 5, 100, 40);
        add(termLabel);
        
        term = new JTextField();
        term.setBounds(20, 40, 140, 40);
        add(term);
        
        JLabel definitionLabel = new JLabel("Definition");
        definitionLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        definitionLabel.setForeground(Color.WHITE);
        definitionLabel.setBounds(180, 5, 100, 40);
        add(definitionLabel);
        
        definition = new JTextField();
        definition.setBounds(180, 40, 280, 40);
        add(definition);
        
        add = new JButton("Add");
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.setBounds(180, 90, 80, 30);
        add.addActionListener(this);
        add(add);
        
        done = new JButton("Done");
        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        done.setBounds(280, 90, 80, 30);
        done.addActionListener(this);
        add(done);
        
        cancel = new JButton("Cancel");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(380, 90, 80, 30);
        cancel.addActionListener(this);
        add(cancel);
        
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(500, 170);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Add Flashcards to " + deckTitle);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            if (term.getText().equals("") || definition.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Must enter text for term and definition.");
            } else {
                deck.add(new Flashcard(term.getText(), definition.getText()));
                
                JOptionPane.showMessageDialog(null, "Successfully added '" + term.getText() + "' to '" + deckTitle + "!'", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(iconSmooth));
                term.setText("");
                definition.setText("");
            }
        } else if (ae.getSource() == done) {
            if (term.getText().equals("") || definition.getText().equals("")) {
                try {
                    String currentPath = System.getProperty("user.dir");
                    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
                    File dir = new File(filePath);
                    dir.mkdirs();
                    File newFile = new File(filePath + File.separator + deckTitle + ".json");
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(newFile, deck);
                    JOptionPane.showMessageDialog(null, "Successfully added all the card(s) to '" + deckTitle + "'", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(iconSmooth));
                    setVisible(false);
                } catch (Exception e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "An error occurred.");
                    setVisible(false);
                }
            } else {
                deck.add(new Flashcard(term.getText(), definition.getText()));
                try {
                    String currentPath = System.getProperty("user.dir");
                    File newFile = new File(currentPath + File.separator + "src" + File.separator + "decks" + File.separator + deckTitle + ".json");
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(newFile, deck);
                    JOptionPane.showMessageDialog(null, "Successfully added all the card(s) to '" + deckTitle + "'", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(iconSmooth));
                    setVisible(false);
                } catch (Exception e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "An error occurred.");
                    setVisible(false);
                }

            }
        } else if (ae.getSource() == cancel) {
            int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel? You will lose all your flashcards.");
            if (a == JOptionPane.YES_OPTION) {
                setVisible(false);
            }
        }
    }
    
    public static void main(String args[]) {
        new AddFlashcards("Capitals");
    }
}
