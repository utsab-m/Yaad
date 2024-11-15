package yaad;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Yaad extends JFrame implements ActionListener {
    
    JButton create, delete, edit, settings;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    Color fontColor, backgroundColor, buttonColor;
    JPanel deckDisplay;
    
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
    
    Yaad() {
        
        fontColor = Color.WHITE;
        backgroundColor = Color.DARK_GRAY;
        buttonColor = Color.BLACK;
        
        setLayout(null);
        
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/Yaad.jpg"));
        setIconImage(image.getImage());
        
        menuBar = new JMenuBar();
        refresh = new JMenu("Refresh");
        menuBar.add(refresh);
        add(menuBar);
        
        
        create = new JButton("Create Deck");
        create.setBounds(20, 20, 200, 80);
        create.setFont(new Font("Raleway", Font.BOLD, 16));
        create.setForeground(fontColor);
        create.setBackground(buttonColor);
        create.addActionListener(this);
        add(create);
        
        delete = new JButton("Delete Deck");
        delete.setBounds(240, 20, 200, 80);
        delete.setFont(new Font("Raleway", Font.BOLD, 16));
        delete.setForeground(fontColor);
        delete.setBackground(buttonColor);
        delete.addActionListener(this);
        add(delete);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font("Raleway", Font.BOLD, 16));
        edit.setForeground(fontColor);
        edit.setBackground(buttonColor);
        edit.addActionListener(this);
        add(edit);
        
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/whiteSettings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);
        
        settings = new JButton(s3);
        settings.setBounds(680, 20, 80, 80);
        settings.setBackground(buttonColor);
        settings.addActionListener(this);
        add(settings);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font("Raleway", Font.BOLD, 16));
        edit.setForeground(fontColor);
        edit.setBackground(buttonColor);
        edit.addActionListener(this);
        add(edit);
        
        deckDisplay = new JPanel();
        deckDisplay.setBounds(100, 120, 600, 600);
        deckDisplay.setOpaque(false);
        deckDisplay.setBackground(backgroundColor);
        
        File dir = new File(filePath);
        dir.mkdirs();
        File[] fileList = dir.listFiles();
        for (File f: fileList) {
            System.out.println(f.getName());
        }
        GridLayout gl = new GridLayout(fileList.length, 1);
        deckDisplay.setLayout(gl);
        
        
        for (File f: fileList) {
            String fileName = f.getName();
            JLabel deck = new JLabel(fileName.replace(".json", ""));
            
            Font bold = new Font("Raleway", Font.BOLD, 22);
            Font italic = new Font("Raleway", Font.ITALIC, 22);
            
            deck.setFont(bold);
            deck.setForeground(fontColor);
            deck.setBorder(BorderFactory.createLineBorder(buttonColor));
            deck.setHorizontalAlignment(SwingConstants.CENTER);
            deck.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    String text = sourceText.replace("<html><u>", "");
                    text = text.replace("</u></html>", "");
                    new Study(text);
                }
                public void mouseEntered(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    source.setFont(italic);
                    source.setText("<html><u>" + sourceText + "</u></html>");
                    source.setForeground(Color.BLUE);
                }
                public void mouseExited(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    String text = sourceText.replace("<html><u>", "");
                    text = text.replace("</u></html>", "");
                    source.setFont(bold);
                    source.setText(text);
                    source.setForeground(fontColor);
                }
            });
            deckDisplay.add(deck);
        }        
        add(deckDisplay, BorderLayout.CENTER);
        
        
        
        
        setTitle("Yaad");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
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
    
    public void update() {
        
        
        repaint();
    }
    
    public static void main(String[] args) {
        new Yaad();
    }
    
}
