package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Yaad extends JFrame implements ActionListener, KeyListener {
    
    final int width = 800, height = 800;
    
    JButton create = createStyledButton("Create Deck"), 
            delete = createStyledButton("Delete Deck"), 
            edit = createStyledButton("Edit Deck"), 
            settings;
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
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);
        getSettings();
        
        // Button Panel (Fixed at the Top)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension (width, 110));
        buttonPanel.setBackground(backgroundColor);
        
        buttonPanel.add(create);
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        
        // Settings Button (Properly Positioned)
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("images/whiteSettings.png"));
        Image s2 = s1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon s3 = new ImageIcon(s2);

        settings = new JButton(s3);
        settings.setBackground(buttonColor);
        settings.setFocusable(false);
        settings.addActionListener(this);
        buttonPanel.add(settings);

        add(buttonPanel, BorderLayout.NORTH);
        
        // Deck Display Panel (Scrollable)
        deckDisplay = new JPanel();
        deckDisplay.setLayout(new BoxLayout(deckDisplay, BoxLayout.Y_AXIS));
        deckDisplay.setOpaque(false);
        
        update();
        
        add(deckDisplay, BorderLayout.CENTER);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(deckDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        add(scrollPane, BorderLayout.CENTER);
        
        // Menu Bar Setup
        setUpMenu();
        
        // Window Configuration
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
        System.out.println("updated");
        getSettings();
        
        File[] fileList = listFiles();
        files.clear();
        Collections.addAll(files, fileList);

        for (File f: fileList) {
            System.out.println(f.getName());
        }
        
        for (File f: fileList) {
            String fileName = f.getName();
            JLabel deck = new JLabel(fileName.replace(".json", ""));
            
            Font bold = new Font(fontName, Font.BOLD, 22);
            Font italic = new Font(fontName, Font.ITALIC, 22);
            
            deck.setOpaque(false);
            deck.setFont(bold);
            deck.setForeground(fontColor);
            deck.setBorder(BorderFactory.createLineBorder(buttonColor));
            deck.setMaximumSize(new Dimension(width, 50));
            deck.setHorizontalAlignment(SwingConstants.CENTER);
            deck.setAlignmentX(Component.CENTER_ALIGNMENT);
            deck.setFocusable(false);
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
        
        create.setBackground(buttonColor);
        create.setForeground(fontColor);
        
        delete.setBackground(buttonColor);
        delete.setForeground(fontColor);
        
        edit.setBackground(buttonColor);
        edit.setForeground(fontColor);
        
        settings.setBackground(buttonColor);
        settings.setForeground(fontColor);
        
        deckDisplay.setBackground(backgroundColor);
        deckDisplay.revalidate();
        deckDisplay.repaint();
        
        getContentPane().setBackground(backgroundColor);
        
    }
    
    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(fontName, Font.BOLD, 16));
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        button.addActionListener(this);
        button.setFocusable(false);
        return button;
    }
    
    public File[] listFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return dir.listFiles();
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
        SwingUtilities.invokeLater(Yaad::new);
    }
    
}
