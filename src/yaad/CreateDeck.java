package yaad;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateDeck extends JFrame implements ActionListener {
    
    JTextField deckTF;
    JButton next, cancel;
    String deckTitle;
    
    CreateDeck() {
        
        setLayout(null);
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/create.png"));
        Image iconSmooth = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        setIconImage(iconSmooth);
        
        JLabel text = new JLabel("Deck Title");
        text.setFont(new Font("Raleway", Font.BOLD, 16));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.DARK_GRAY);
        text.setBounds(20, 20, 100, 40);
        add(text);
        
        deckTF = new JTextField();
        deckTF.setBounds(120, 20, 460, 40);
        add(deckTF);
        
        next = new JButton("Next");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setBounds(240, 70, 100, 30);
        next.addActionListener(this);
        add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(360, 70, 100, 30);
        cancel.addActionListener(this);
        add(cancel);
        
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(600, 150);
        setTitle("Create Deck");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            try {
                deckTitle = deckTF.getText();
                if (deckTitle.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a title.");
                } else {
                    String currentPath = System.getProperty("user.dir");
                    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
                    File dir = new File(filePath);
                    dir.mkdirs();
                    File newFile = new File(filePath + File.separator + deckTitle + ".json");
                    if (newFile.createNewFile()) {
                        new AddFlashcards(deckTitle); 
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Deck with this title already exists!");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } else {
            
        }
    }
    
    public static void main(String args[]) {
        new CreateDeck();
    }
}
