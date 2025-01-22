package yaad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

public class DeleteDeck extends JFrame {
    
    JPanel deckDisplay = new JPanel();
    Color backgroundColor, fontColor, buttonColor;
    
    String currentPath = System.getProperty("user.dir");
    String filePath = currentPath + File.separator + "src" + File.separator + "decks";
    
    ObjectMapper mapper = new ObjectMapper();
    
    DeleteDeck() {
        
        getSettings();
        setLayout(null);
        
        deckDisplay.setBounds(100, 75, 400, 400);
        deckDisplay.setOpaque(false);
        deckDisplay.setBackground(backgroundColor);
        paintPanel();
        add(deckDisplay, BorderLayout.CENTER);
        
        setTitle("Delete Decks");
        setVisible(true);
        getContentPane().setBackground(backgroundColor);
        getContentPane().setPreferredSize(new Dimension(600, 600));
        pack();
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
        
        File dir = new File(filePath);
        dir.mkdirs();
        File[] fileList = dir.listFiles();
        for (File f: fileList) {
            System.out.println(f.getName());
        }
        
        deckDisplay.setLayout(new GridLayout(fileList.length, 1));
        
        
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
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this deck? This cannot be reversed.");
                    if (input == 0) {
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
    
    public static void main(String args[]) {
        new DeleteDeck();
    }
}
