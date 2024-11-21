package yaad;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Test extends JFrame implements ActionListener, KeyListener {
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem refresh;
    
    JPanel display;

    Test() {

        setLayout(null);
        
        addKeyListener(this);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menu");
        refresh = new JMenuItem("Refresh");
        refresh.addActionListener(this);
        fileMenu.add(refresh);

        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);

        display = new JPanel();
        display.setBounds(100, 100, 400, 400);
        display.setOpaque(true);
        display.setLayout(new GridLayout(1, 1));
        display.setBackground(Color.BLUE);
        add(display);
        
        JLabel label = new JLabel("text");
        label.setForeground(Color.WHITE);
        label.setBackground(Color.DARK_GRAY);
        label.setFont(new Font("Raleway", Font.BOLD, 22));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        display.add(label);
        
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refresh) {
            System.out.println("ok");
            
            display.removeAll();
            
            JLabel text = new JLabel("Text");
            text.setOpaque(true);
            text.setBackground(Color.BLACK);
            text.setForeground(Color.GREEN);
            
            display.add(text);            
            display.setLayout(new GridLayout(2, 1));
            display.revalidate();
            display.repaint();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyChar());
    }
    
    public void keyReleased(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
    }
    
    public static void main(String args[]) {
        new Test();
    }

}
