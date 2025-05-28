import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FlashcardFlip extends JFrame {
    public FlashcardFlip() {
        setTitle("Flashcard Flip");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FlipPanel flipPanel = new FlipPanel();
        add(flipPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlashcardFlip::new);
    }
}

class FlipPanel extends JPanel {
    private final JPanel frontCard;
    private final JPanel backCard;
    private boolean isFront = true;

    private BufferedImage frontImage;
    private BufferedImage backImage;
    private Timer timer;
    private int step = 0;
    private final int MAX_STEPS = 20;

    public FlipPanel() {
        setLayout(null);

        frontCard = createCard("Front", Color.WHITE);
        backCard = createCard("Back", Color.LIGHT_GRAY);

        add(frontCard);
        add(backCard);

        frontCard.setBounds(0, 0, 400, 300);
        backCard.setBounds(0, 0, 400, 300);
        backCard.setVisible(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFlip();
            }
        });
    }

    private JPanel createCard(String text, Color color) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(color);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(label);
        return panel;
    }

    private void startFlip() {
        if (timer != null && timer.isRunning()) return;

        // Create images of front and back
        frontImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        frontCard.paint(frontImage.getGraphics());

        backImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        backCard.paint(backImage.getGraphics());

        step = 0;

        timer = new Timer(20, e -> {
            repaint();
            step++;
            if (step == MAX_STEPS / 2) {
                // Switch card visibility at midpoint
                isFront = !isFront;
            }
            if (step >= MAX_STEPS) {
                ((Timer) e.getSource()).stop();
                frontCard.setVisible(isFront);
                backCard.setVisible(!isFront);
            }
        });

        frontCard.setVisible(false);
        backCard.setVisible(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (timer != null && timer.isRunning()) {
            float progress = (float) step / MAX_STEPS;
            float scale = step < MAX_STEPS / 2
                    ? 1 - 2 * progress
                    : 2 * (progress - 0.5f);

            scale = Math.max(0.01f, Math.abs(scale)); // avoid 0 scale

            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            int scaledW = (int) (w * scale);

            BufferedImage imageToDraw = step < MAX_STEPS / 2
                    ? (isFront ? frontImage : backImage)
                    : (isFront ? backImage : frontImage);

            g2.translate((w - scaledW) / 2, 0);
            g2.drawImage(imageToDraw, 0, 0, scaledW, h, null);
            g2.dispose();
        }
    }
}