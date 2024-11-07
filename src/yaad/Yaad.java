package yaad;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Yaad extends JFrame implements ActionListener {
    
    JButton create, delete, edit, settings;
    
    Yaad() {
        
        setLayout(null);
        
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/Yaad.jpg"));
        setIconImage(image.getImage());
        
        create = new JButton("Create Deck");
        create.setBounds(20, 20, 200, 80);
        create.setFont(new Font("Raleway", Font.BOLD, 16));
        create.setForeground(Color.WHITE);
        create.setBackground(Color.BLACK);
        create.addActionListener(this);
        add(create);
        
        delete = new JButton("Delete Deck");
        delete.setBounds(240, 20, 200, 80);
        delete.setFont(new Font("Raleway", Font.BOLD, 16));
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.BLACK);
        delete.addActionListener(this);
        add(delete);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font("Raleway", Font.BOLD, 16));
        edit.setForeground(Color.WHITE);
        edit.setBackground(Color.BLACK);
        edit.addActionListener(this);
        add(edit);
        
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/settings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);
        
        settings = new JButton(s3);
        settings.setBounds(680, 20, 80, 80);
        settings.setBackground(Color.BLACK);
        add(settings);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font("Raleway", Font.BOLD, 16));
        edit.setForeground(Color.WHITE);
        edit.setBackground(Color.BLACK);
        edit.addActionListener(this);
        add(edit);
        
        JPanel deckDisplay = new JPanel();
        String currentPath = System.getProperty("user.dir");
        File dir = new File(currentPath + File.separator + "decks");
        dir.mkdirs();
        File[] fileList = dir.listFiles();
        GridLayout gl = new GridLayout(1, fileList.length);
        deckDisplay.setLayout(gl);
        
        
        setTitle("Yaad");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            new CreateDeck();
        } else if (ae.getSource() == delete) {
            new DeleteDeck();
        } else if (ae.getSource() == edit) {
            new EditDeck();
        } else if (ae.getSource() == settings) {
            new Settings();
        }
    }
    
    public static void main(String[] args) {
        new Yaad();
    }
    
}
