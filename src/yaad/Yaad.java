package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Yaad extends JFrame implements ActionListener, KeyListener {
    
    final int width = 600, height = 600;
    
    JButton create = createStyledButton("Create Deck"), 
            // delete = createStyledButton("Delete Deck"), 
            // edit = createStyledButton("Edit Deck"), 
            settings;
    
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    Color fontColor, backgroundColor, buttonColor;
    int fontColorRGB, backgroundColorRGB, buttonColorRGB;
    JPanel deckDisplay;
    JScrollPane scrollPane;
    String fontName = "Raleway";
    Font bold = new Font(fontName, Font.BOLD, 22), italic = new Font(fontName, Font.ITALIC, 22);
    
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    String decksPath = currentPath + File.separator + "decks";
    File settingsFile = new File(currentPath + File.separator + "settings.json");
    ObjectMapper mapper = new ObjectMapper();
    
    ArrayList<Deck> decks = new ArrayList<>();
    
    Yaad() {
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);
        getSettings();
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension (width, 110));
        buttonPanel.setBackground(backgroundColor);
        
        buttonPanel.add(create);
        //buttonPanel.add(delete);
        //buttonPanel.add(edit);
        
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/whiteSettings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);

        settings = new JButton(s3);
        settings.setBackground(buttonColor);
        settings.setFocusable(false);
        settings.addActionListener(this);
        buttonPanel.add(settings);

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
        
        if (getSettings()) setColors();
        update();
    }
    
    
    public void update() {
        System.out.println("updated");
        if (getSettings()) setColors();
        
        for (File f: listFiles()) {
            Deck deck = new Deck(f, fontName, fontColor, backgroundColor, buttonColor, width);
            deckDisplay.add(deck);
            decks.add(deck);
            System.out.println("added " + deck.getTitle());
        }
        
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
    /*
    public void addDeck(File f) {
        int i = findInsertionPoint(f);
        addDeck(i, f);
        System.out.println("Method: addDeck(f), File name: " + f.getName() + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void addDeck(int i, File f) {
        files.add(i, f);
        String deckTitle = removeExt(f.getName());
        JLabel deck = createDeck(deckTitle);
        
        try {
            deckDisplay.add(deck, i);
            decks.add(i, deck);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fix(deckDisplay);
        System.out.println("Method: addDeck(i, f), Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void updateDecks(Collection<File> fileList) {
        for (File f: fileList) {
            addDeck(f);
        }
        fix(deckDisplay);
    }
    
    public void removeDeck(int i) {
        deckDisplay.remove(i);
        fix(deckDisplay);
        decks.remove(i);
        files.remove(i);
        System.out.println("Method: removeDeck, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void removeDeck(File f) {
        int i = findIndex(f);
        removeDeck(i);
        System.out.println("Method: removeDeck, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void removeAllDecks() {
        for (File f: files) removeDeck(f);
        fix(deckDisplay);
    }
    
    public String removeExt(String fileName) {
        System.out.println("Removed ext for fileName");
        return (fileName.replace(".json", ""));
    }
    
    public String getFileName(int i) {
        System.out.println("Method: getFileName, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
        return files.get(i).getName();
    }
    
    public int findInsertionPoint(File f) {
        //Assume files is updated because it is called from methods where files is fixed before findIndex is called\
        int size = files.size();
        
        String newFileName = f.getName();
        
        for (int i = 0; i < size; i++) {
            String oldFileName = files.get(i).getName();
            if (newFileName.compareToIgnoreCase(oldFileName) < 0) return i;
        }
        return size;
    }
    
    public int findIndex(File f) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).equals(f)) return i;
        }
        return -1;
    }
    */
    public void fix(Component component) {
        component.revalidate();
        component.repaint();
    }
    
    public void setColors() {
        create.setBackground(buttonColor);
        create.setForeground(fontColor);
        System.out.println(create.getForeground().getRGB());
        System.out.println(fontColor.getRGB());
        
        //delete.setBackground(buttonColor);
        //delete.setForeground(fontColor);
        
        //edit.setBackground(buttonColor);
        //edit.setForeground(fontColor);
        
        settings.setBackground(buttonColor);
        settings.setForeground(fontColor);
        
        scrollPane.getViewport().setBackground(backgroundColor);
        getContentPane().setBackground(backgroundColor);
    }
    
    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(fontName, Font.BOLD, 16));
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setOpaque(true);
        return button;
    }
    
    public ArrayList<File> listFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return new ArrayList(Arrays.asList(dir.listFiles()));
    }
    
    public SortedSet<File> setFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return new TreeSet(Arrays.asList(dir.listFiles()));
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
    
    public boolean getSettings() {
        if (!settingsFile.exists()) makeSettings();
        if (settingsFile.length() == 0) defaultSettings();
        try {
            JsonNode node = mapper.readTree(settingsFile);
            backgroundColorRGB = node.get("backgroundColor").asInt();
            buttonColorRGB = node.get("buttonColor").asInt();
            fontColorRGB = node.get("fontColor").asInt();
            if (backgroundColor != null && (backgroundColor.getRGB() == backgroundColorRGB &&
                buttonColor.getRGB() == buttonColorRGB &&
                fontColor.getRGB() == fontColorRGB)) return false;
            backgroundColor = new Color(backgroundColorRGB);
            buttonColor = new Color(buttonColorRGB);
            fontColor = new Color(fontColorRGB);
        } catch (Exception e) {
            makeSettings();
            defaultSettings();
            e.printStackTrace();
        }
        return true;
    }
    
    public void makeSettings() {
        settingsFile.getParentFile().mkdirs();
        try {
            settingsFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void defaultSettings() {
        try {
            FileWriter fw = new FileWriter(settingsFile, false);
            backgroundColor = Color.DARK_GRAY;
            buttonColor = Color.BLACK;
            fontColor = Color.WHITE;
            fw.write(getJsonSource());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String getJsonSource() {
        return "{\"backgroundColor\": " + backgroundColor.getRGB() + ", \"buttonColor\": " + buttonColor.getRGB() + ", \"fontColor\": " + fontColor.getRGB() + "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Yaad::new);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
        if ((ke.isControlDown() || ke.isAltDown()) && ke.getKeyCode() == KeyEvent.VK_R) update();
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {}
    
    @Override
    public void keyTyped(KeyEvent ke) {}
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            new CreateDeck();
        } else if (ae.getSource() == settings) {
            try {
                new Settings();
            } catch (IOException e) {
                System.out.println(e);
            }
        } else if (ae.getSource() == refresh) {
            update();
        }
    }
}
