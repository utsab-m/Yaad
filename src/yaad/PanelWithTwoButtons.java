package yaad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelWithTwoButtons extends JFrame implements ActionListener {
    
    int width = 600, height = 600;
    Color backgroundColor = Color.BLACK;
    
    public PanelWithTwoButtons() {
        setLayout(new BorderLayout());
        
        for (int i = 0; i < 10; i++) {
            JPanel panel = new JPanel();
            JLabel deck = new JLabel(String.valueOf(i));
            panel.add(deck);
            JButton delete = createButton("delete", 40);
            panel.add(delete);
            JButton edit = createButton("edit", 40);
            panel.add(edit);
            add(panel);
        }
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
    }
    
    public JButton createButton(String text, int size) {
        ImageIcon imageIcon = ImageHandler.scaleImageIcon(text, size, size);
        JButton button = new JButton(imageIcon);
        button.setBackground(Color.RED);
        return button;
    }
    
    public static void main(String args[]) {
        new PanelWithTwoButtons();
    }
    
    public void actionPerformed(ActionEvent ae) {
        JButton button = (JButton)ae.getSource();
        System.out.println(button.getText());
    }
}
