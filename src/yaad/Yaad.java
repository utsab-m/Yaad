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
        
        ImageIcon settingsImage = ImageHandler.scaleImageIcon("whiteSettings", 60, 60);
        settingsButton = new JButton(settingsImage);
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
        updateDecks();
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
    
    public void updateDecks() {
        deckDisplay.removeAll();
        dh.updateDecks();
        for (Deck deck: dh.getDecks()) {
            deckDisplay.add(deck);
        }
        fix(deckDisplay);
    }
    
    public void setColors() {
        createButton.setBackground(buttonColor);
        createButton.setForeground(fontColor);
        System.out.println(createButton.getForeground().getRGB());
        System.out.println(fontColor.getRGB());
        
        // deleteButton.setBackground(buttonColor);
        // deleteButton.setForeground(fontColor);
        
        // edit.setBackground(buttonColor);
        // edit.setForeground(fontColor);
        
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
