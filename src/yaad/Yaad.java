package yaad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Yaad extends JFrame implements ActionListener, KeyListener, DeckActionListener {
    
    int width, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    DeckHandler dh = new DeckHandler();
    
    JButton importButton, createButton, settingsButton;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    JPanel buttonPanel, deckDisplay;
    JScrollPane scrollPane;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName = "Raleway";
    Font bold = new Font(fontName, Font.BOLD, 22), italic = new Font(fontName, Font.ITALIC, 22);
    
    List<DeckPanel> deckPanels = new ArrayList();
    
    Yaad() {
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);
        
        updateSettings();
        
        importButton = createButton("Import from Quizlet");
        createButton = createButton("Create Deck");
        
        ImageIcon settingsImage = ImageHandler.scaleImageIcon("whiteSettings", 60, 60);
        settingsButton = new JButton(settingsImage);
        settingsButton.setBackground(buttonColor);
        settingsButton.setFocusable(false);
        settingsButton.addActionListener(this);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension (width, 110));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(importButton);
        buttonPanel.add(createButton);
        buttonPanel.add(settingsButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        deckDisplay = new JPanel();
        deckDisplay.setAlignmentY(Component.TOP_ALIGNMENT);
        deckDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckDisplay.setBackground(backgroundColor);
        deckDisplay.setLayout(new BoxLayout(deckDisplay, BoxLayout.Y_AXIS));
        deckDisplay.setOpaque(false);
        
        scrollPane = new JScrollPane(deckDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getViewport().setBackground(backgroundColor);
        add(scrollPane, BorderLayout.CENTER);
        
        setUpMenu();
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        
        update();
    }
    
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(fontName, Font.BOLD, 16));
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setOpaque(true);
        return button;
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
        for (DeckPanel deckPanel: deckPanels) {
            System.out.println(deckPanel.getDeckTitle() + ", ");
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
        width = sh.getWidth();
    }
    
    public void updateDecks() {
        deckDisplay.removeAll();
        dh.updateDecks();
        for (Deck deck: dh.getDecks()) {
            DeckPanel deckPanel = new DeckPanel(deck, sh.getSettings(), this);
            deckDisplay.add(deckPanel);
        }
        fix(deckDisplay);
    }
    
    public void setColors() {
        for (Component component: buttonPanel.getComponents()) {
            JButton button = (JButton)component;
            button.setBackground(buttonColor);
            button.setForeground(fontColor);
        }
        
        for (DeckPanel deckPanel: deckPanels) {
            deckPanel.setBorder(BorderFactory.createLineBorder(buttonColor));
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
        if (ae.getSource() == importButton) {
            new Import();
        } else if (ae.getSource() == createButton) {
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
    
    @Override
    public void onEditDeck(Deck deck) {
        new EditDeck(deck);
    }
    
    @Override
    public void onDeleteDeck(DeckPanel deckPanel) {
        Deck deck = deckPanel.getDeck();
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + deck.getTitle());
        if (choice == JOptionPane.YES_OPTION) {
            if (dh.deleteDeckFile(deck)) deckDisplay.remove(deckPanel);
            else JOptionPane.showMessageDialog(null, "Unable to delete " + deck.getTitle());
        }
    }
}
