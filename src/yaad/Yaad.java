package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Yaad extends JFrame implements ActionListener, KeyListener {
    
    JButton create, delete, edit, settings;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    Color fontColor, backgroundColor, buttonColor;
    int fontColorRGB, backgroundColorRGB, buttonColorRGB;
    JPanel deckDisplay;
    String fontName = "Raleway";
    
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    String decksPath = currentPath + File.separator + "decks";
    ObjectMapper mapper = new ObjectMapper();
    
    ArrayList<File> files = new ArrayList<File>();
    
    Yaad() {
        getSettings();
        fontColor = new Color(fontColorRGB);
        backgroundColor = new Color(backgroundColorRGB);
        buttonColor = new Color(buttonColorRGB);
        
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/Yaad.jpg"));
        setIconImage(image.getImage());
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("<html><u>M</u>enu</html>");
        refresh = new JMenuItem("<html><u>R</u>efresh</html>");
        refresh.addActionListener(this);
        fileMenu.add(refresh);
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);        
        
        create = new JButton("Create Deck");
        create.setBounds(20, 20, 200, 80);
        create.setFont(new Font(fontName, Font.BOLD, 16));
        create.setForeground(fontColor);
        create.setBackground(buttonColor);
        create.addActionListener(this);
        create.setFocusable(false);
        add(create);
        
        delete = new JButton("Delete Deck");
        delete.setBounds(240, 20, 200, 80);
        delete.setFont(new Font(fontName, Font.BOLD, 16));
        delete.setForeground(fontColor);
        delete.setBackground(buttonColor);
        delete.addActionListener(this);
        delete.setFocusable(false);
        add(delete);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font(fontName, Font.BOLD, 16));
        edit.setForeground(fontColor);
        edit.setBackground(buttonColor);
        edit.addActionListener(this);
        edit.setFocusable(false);
        add(edit);
        
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/whiteSettings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);
        
        settings = new JButton(s3);
        settings.setBounds(680, 20, 80, 80);
        settings.setBackground(buttonColor);
        settings.addActionListener(this);
        settings.setFocusable(false);
        add(settings);
        
        edit = new JButton("Edit Deck");
        edit.setBounds(460, 20, 200, 80);
        edit.setFont(new Font(fontName, Font.BOLD, 16));
        edit.setForeground(fontColor);
        edit.setBackground(buttonColor);
        edit.addActionListener(this);
        edit.setFocusable(false);
        add(edit);
        
        deckDisplay = new JPanel();
        deckDisplay.setBounds(100, 120, 600, 600);
        deckDisplay.setOpaque(false);
        deckDisplay.setBackground(backgroundColor);
        
        File[] fileList = listFiles();
        
        Collections.addAll(files, fileList);

        for (File f: fileList) {
            System.out.println(f.getName());
        }
        
        deckDisplay.setLayout(new GridLayout(fileList.length, 1));
        
        for (File f: fileList) {
            String fileName = f.getName();
            JLabel deck = new JLabel(fileName.replace(".json", ""));
            
            Font bold = new Font(fontName, Font.BOLD, 22);
            Font italic = new Font(fontName, Font.ITALIC, 22);
            
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
        getContentPane().setPreferredSize(new Dimension(800, 800));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            new CreateDeck();
        } else if (ae.getSource() == delete) {
            new DeleteDeck();
        } else if (ae.getSource() == edit) {
            new EditDeck();
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
    
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
        if ((ke.isControlDown() || ke.isAltDown()) && ke.getKeyCode() == KeyEvent.VK_R) update();
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }
    
    public void update() {
        System.out.println("ok");
        getSettings();
        
        create.setBackground(buttonColor);
        create.setForeground(fontColor);
        
        delete.setBackground(buttonColor);
        delete.setForeground(fontColor);
        
        edit.setBackground(buttonColor);
        edit.setForeground(buttonColor);
        
        settings.setBackground(buttonColor);
        settings.setForeground(buttonColor);
        
        deckDisplay.removeAll();
        
        File[] fileList = listFiles();
        files.clear();
        Collections.addAll(files, fileList);

        for (File f: fileList) {
            System.out.println(f.getName());
        }
        deckDisplay.setLayout(new GridLayout(fileList.length, 1));
        
        for (File f: fileList) {
            String fileName = f.getName();
            JLabel deck = new JLabel(fileName.replace(".json", ""));
            
            Font bold = new Font(fontName, Font.BOLD, 22);
            Font italic = new Font(fontName, Font.ITALIC, 22);
            
            deck.setFont(bold);
            deck.setForeground(fontColor);
            deck.setBorder(BorderFactory.createLineBorder(buttonColor));
            deck.setHorizontalAlignment(SwingConstants.CENTER);
            deck.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    String text = sourceText.replace("<html><u>", "");
                    text = text.replace("</u></html>", "");
                    new Study(text);
                }
                @Override
                public void mouseEntered(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    source.setFont(italic);
                    source.setText("<html><u>" + sourceText + "</u></html>");
                    source.setForeground(Color.BLUE);
                }
                @Override
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
        deckDisplay.revalidate();
        deckDisplay.repaint();
        
    }
    
    public File[] listFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return dir.listFiles();
    }
    
    public void getSettings() {
        File fSettings = new File(currentPath + File.separator + "settings.json");
        try {
            JsonNode node = mapper.readTree(fSettings);
            backgroundColorRGB = node.get("backgroundColor").asInt();
            backgroundColor = new Color(backgroundColorRGB);
            buttonColorRGB = node.get("buttonColor").asInt();
            buttonColor = new Color(buttonColorRGB);
            fontColorRGB = node.get("fontColor").asInt();
            fontColor = new Color(fontColorRGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Yaad();
    }
    
}
