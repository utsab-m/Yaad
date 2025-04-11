package yaad;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneWithGridLayout extends JFrame {
    
    Font font = new Font("Raleway", Font.BOLD, 22);
    
    public ScrollPaneWithGridLayout() {
        setLayout(new BorderLayout());

        // NORTH PANEL (Buttons)
        JPanel northPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        northPanel.setPreferredSize(new Dimension(800, 110));
        northPanel.setBackground(Color.DARK_GRAY);

        northPanel.add(createStyledButton("Button1"));
        northPanel.add(createStyledButton("Button2"));
        northPanel.add(createStyledButton("Button3"));
        northPanel.add(createStyledButton("Button4"));

        add(northPanel, BorderLayout.NORTH);

        // CENTER PANEL (Scrollable List of Buttons)
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for non-stretching buttons

        for (int i = 0; i < 30; i++) {
            JButton button = new JButton("Button " + (i + 1));
            button.setFont(font);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(800, 50)); // Restrict button size
            deckPanel.add(button);
            deckPanel.add(Box.createVerticalStrut(5)); // Adds spacing between buttons
        }

        JScrollPane scrollPane = new JScrollPane(deckPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(400, 500)); // Controls scrollpane size
        add(scrollPane, BorderLayout.CENTER);

        // Frame settings
        setPreferredSize(new Dimension(800, 800));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScrollPaneWithGridLayout::new);
    }
}