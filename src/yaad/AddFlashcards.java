package yaad;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddFlashcards extends JFrame implements ActionListener {
    
    Color backgroundColor, buttonColor, fontColor;
    
    String deckTitle;
    JTextField term, definition;
    JButton add, done, cancel;
    ArrayList<Flashcard> deck = new ArrayList<>();
    
    ImageIcon check = new ImageIcon(ClassLoader.getSystemResource("images/check.png")); 
    Image iconSmooth = check.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    
    File deckFile;
    
    ObjectMapper mapper = new ObjectMapper();
    
    AddFlashcards(File file) {
        
        deckFile = file;
        
        getSettings();
        
        this.deckTitle = deckFile.getName().replace(".json", "");
        
        setLayout(null);
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/add.png"));
        Image iconSmooth = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        setIconImage(iconSmooth);
        
        JLabel termLabel = createLabel("Term", 20);
        add(termLabel);
        
        term = new JTextField();
        term.setBounds(20, 40, 140, 40);
        add(term);
        
        JLabel definitionLabel = createLabel("Definition", 180);
        definitionLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        definitionLabel.setForeground(fontColor);
        definitionLabel.setBounds(180, 5, 100, 40);
        add(definitionLabel);
        
        definition = createTextField(180, 280, 40);
        add(definition);
        
        add = createButton("Add", 180);
        add(add);
        
        done = createButton("Done", 280);
        add(done);
        
        cancel = createButton("Cancel", 380);
        add(cancel);
        
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(500, 170));
        pack();
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
            int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel? You will lose all your new flashcards.");
            if (a == JOptionPane.YES_OPTION) {
                setVisible(false);
            }
        }
    }
    
    public JTextField createTextField(int x, int w, int h) {
        JTextField textField = new JTextField();
        textField.setBounds(x, 40, w, h);
        return textField;
    }
    
    public JButton createButton(String text, int x) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(fontColor);
        button.setBounds(x, 90, 80, 30);
        button.addActionListener(this);
        return button;
    }
    
    public JLabel createLabel(String text, int x) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Raleway", Font.BOLD, 22));
        label.setForeground(fontColor);
        label.setBounds(x, 5, 100, 40);
        return label;
    }
    
    public void getSettings() {
        File settings = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "settings.json");
        settings.mkdirs();
        try {
            JsonNode node = mapper.readTree(settings);
            backgroundColor = new Color(node.get("backgroundColor").asInt());
            buttonColor = new Color(node.get("buttonColor").asInt());
            fontColor = new Color(node.get("fontColor").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        new AddFlashcards(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "decks" + File.separator + "Capitals.json"));
    }
}
