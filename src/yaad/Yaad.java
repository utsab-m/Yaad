package yaad;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Yaad extends JFrame implements ActionListener, KeyListener {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    DeckHandler dh = new DeckHandler(width);
    
    JButton createButton, 
            // deleteButton = createStyledButton("Delete Deck"), 
            // edit = createStyledButton("Edit Deck"), 
            settingsButton;
    
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    JPanel deckDisplay;
    JScrollPane scrollPane;
    Color backgroundColor, buttonColor, fontColor;
    String fontName = "Raleway";
    Font bold = new Font(fontName, Font.BOLD, 22), italic = new Font(fontName, Font.ITALIC, 22);
    
    ArrayList<Deck> decks = new ArrayList<>();
    
    Yaad() {
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);
        
        updateSettings();
        
        createButton = new JButton("Create Deck");
        createButton.setFont(new Font(fontName, Font.BOLD, 16));
        createButton.setForeground(fontColor);
        createButton.setBackground(buttonColor);
        createButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.setOpaque(true);
        
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/whiteSettings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);
        
        settingsButton = new JButton(s3);
        settingsButton.setBackground(buttonColor);
        settingsButton.setFocusable(false);
        settingsButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension (width, 110));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(createButton);
        buttonPanel.add(settingsButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        deckDisplay = new JPanel();
        deckDisplay.setAlignmentY(Component.TOP_ALIGNMENT);
        deckDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckDisplay.setBackground(backgroundColor);
        deckDisplay.setLayout(new BoxLayout(deckDisplay, BoxLayout.Y_AXIS));
        deckDisplay.setOpaque(false);
        
        scrollPane = new JScrollPane(deckDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getViewport().setBackground(backgroundColor);
        add(scrollPane, BorderLayout.CENTER);
        
        setUpMenu();
        
        setTitle("Yaad");
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        
        update();
    }
    
    
    public void update() {
        System.out.println("updated");
        System.out.println(sh.changed());
        if (sh.changed()) {
            updateSettings();
            setColors();
        }
        
        fileLoop:
        for (File f: listFiles()) {
            for (Deck d: decks) {
                if (d.getFile().getAbsolutePath().equals(f.getAbsolutePath())) continue fileLoop;
            }
            Deck deck = new Deck(f, fontName, fontColor, backgroundColor, buttonColor, width);
            deckDisplay.add(deck);
            decks.add(deck);
            System.out.println("Added " + deck.getTitle());
        }
        
        ArrayList<Deck> removedDecks = new ArrayList();
        
        deckLoop:
        for (Deck d: decks) {
            for (File f: listFiles()) {
                if (d.getFile().getAbsolutePath().equals(f.getAbsolutePath())) continue deckLoop;
            }
            deckDisplay.remove(d);
            System.out.println("Removed " + d.getTitle());
            removedDecks.add(d);
        }
        
        for (Deck d: removedDecks) {
            decks.remove(d);
            System.out.println("Removed " + d.getFile().getName());
        }
        // printDecks();
        
        fix(deckDisplay);
        
        /*
        SortedSet<File> newFiles = setFiles();
        
        if (files.isEmpty()) {
            if (newFiles.isEmpty()) {
                System.out.println("Files and newFiles are empty");
                return;
            } else {
                System.out.println("Files is empty, but newFiles isn't");
                updateDecks(newFiles);
            }
        } else {
            if (newFiles.isEmpty()) {
                System.out.println("Files isn't empty, but newFiles is");
                removeAllDecks();
            } else {
                for (File f: files) {
                    if (newFiles.contains(f)) {
                        System.out.println("newFiles contains " + f.getName());
                        newFiles.remove(f);
                        System.out.println("Removed from newFiles");
                        
                    }
                    else {
                        System.out.println("newFiles does not contain " + f.getName());
                        removeDeck(f);
                        System.out.println("Removed from the screen");
                    }
                }
                for (File newFile: newFiles) {
                    System.out.println("Listing files: " + newFile.getName());
                    addDeck(newFile);
                }
            }
        }
        */
    }
    
    public void printDecks() {
        for (Deck d: decks) {
            System.out.println(d.getFile().getName() + ", ");
        }
    }
    
    public void fix(Component component) {
        component.revalidate();
        component.repaint();
    }
    
    public void updateSettings() {
        sh.setSettings();
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
    }
    
    public void setColors() {
        createButton.setBackground(buttonColor);
        createButton.setForeground(fontColor);
        System.out.println(createButton.getForeground().getRGB());
        System.out.println(fontColor.getRGB());
        
        // deleteButton.setBackground(buttonColor);
        // deleteButton.setForeground(fontColor);
        
        //edit.setBackground(buttonColor);
        //edit.setForeground(fontColor);
        
        settingsButton.setBackground(buttonColor);
        settingsButton.setForeground(fontColor);
        
        for (Deck deck: decks) {
            deck.setBorder(BorderFactory.createLineBorder(buttonColor));
        }
        
        scrollPane.getViewport().setBackground(backgroundColor);
        getContentPane().setBackground(backgroundColor);
    }
    
    public void setUpMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("<html><u>M</u>enu</html>");
        fileMenu.setMnemonic(KeyEvent.VK_M);
        refresh = new JMenuItem("<html><u>R</u>efresh</html>");
        refresh.addActionListener(this);
        fileMenu.add(refresh);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Yaad::new);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        if ((ke.isControlDown() || ke.isAltDown()) && ke.getKeyCode() == KeyEvent.VK_R) update();
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {}
    
    @Override
    public void keyTyped(KeyEvent ke) {}
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createButton) {
            new CreateDeck();
        } else if (ae.getSource() == settingsButton) {
            try {
                new ChangeSettings();
            } catch (IOException e) {
                System.out.println(e);
            }
        } else if (ae.getSource() == refresh) {
            update();
        }
    }
}
