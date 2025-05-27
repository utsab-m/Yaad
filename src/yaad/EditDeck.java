package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

public class EditDeck extends JFrame {
    
    final int width = 600, height = 600;
    
    SettingsHandler sh = new SettingsHandler();
    
    JPanel deckDisplay = new JPanel();
    Color backgroundColor, fontColor, buttonColor;
    
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
    
    ObjectMapper mapper = new ObjectMapper();
    
    File[] fileList;
    
    EditDeck() {
        
        getFiles();
        if (fileList.length == 0) {
            JOptionPane.showMessageDialog(null, "There are no decks available to delete.");
            setVisible(false);
        } else {
            setLayout(new BorderLayout());
        
            JLabel deleteDeck = new JLabel("Delete Deck");
            deleteDeck.setForeground(fontColor);
            deleteDeck.setFont(new Font("Raleway", Font.BOLD, 30));
            deleteDeck.setOpaque(false);
            deleteDeck.setBounds(100, 10, 400, 400);
            deleteDeck.setHorizontalAlignment(SwingConstants.CENTER);
            add(deleteDeck, BorderLayout.NORTH);

            getSettings();

            deckDisplay.setOpaque(false);
            deckDisplay.setLayout(new BoxLayout(deckDisplay, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(deckDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(10);
            scrollPane.getViewport().setBackground(backgroundColor);
            add(scrollPane, BorderLayout.CENTER);

            paintPanel();

            setTitle("Delete Deck");
            setVisible(true);
            setResizable(false);
            getContentPane().setBackground(backgroundColor);
            getContentPane().setPreferredSize(new Dimension(width, height));
            pack();
        }
        
    }
    
    public void delete(String deck) {
        File file = new File(filePath + File.separator + deck + ".json");
        if (file.delete()) {
            JOptionPane.showMessageDialog(null, "Successfully deleted " + deck);
            paintPanel();
        } else {
            JOptionPane.showMessageDialog(null, "Unable to delete " + deck);
        }
    }
    
    public void paintPanel() {
        
        deckDisplay.removeAll();
        
        getFiles();
        
        for (File f: fileList) {
            String fileName = f.getName();
            JLabel deck = new JLabel(fileName.replace(".json", ""));
            
            Font bold = new Font("Raleway", Font.BOLD, 22);
            Font italic = new Font("Raleway", Font.ITALIC, 22);
            
            deck.setFont(bold);
            deck.setForeground(fontColor);
            deck.setOpaque(false);
            deck.setBorder(BorderFactory.createLineBorder(buttonColor));
            deck.setHorizontalAlignment(SwingConstants.CENTER);
            deck.setAlignmentX(Component.CENTER_ALIGNMENT);
            deck.setMaximumSize(new Dimension(width, 50));
            deck.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    JLabel source = (JLabel)me.getSource();
                    String sourceText = source.getText();
                    String text = sourceText.replace("<html><u>", "");
                    text = text.replace("</u></html>", "");
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this deck? This cannot be reversed.");
                    if (input == JOptionPane.YES_OPTION) {
                        delete(text);
                    } 
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
        deckDisplay.revalidate();
        deckDisplay.repaint();
    }
    
    public void getSettings() {
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "settings.json");
        file.mkdirs();
        try {
            JsonNode node = mapper.readTree(file);
            backgroundColor = new Color(node.get("backgroundColor").asInt());
            buttonColor = new Color(node.get("buttonColor").asInt());
            fontColor = new Color(node.get("fontColor").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getFiles() {
        File dir = new File(filePath);
        dir.mkdirs();
        fileList = dir.listFiles();
        for (File f: fileList) {
            System.out.println(f.getName());
        }
    }
    
    public static void main(String args[]) {
        new EditDeck();
    }
}
