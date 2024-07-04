package JFX_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExampleAnimationDeplacement extends JFrame {
    private JLayeredPane layeredPane;
    private JLabel originalLabel;

    public ExampleAnimationDeplacement() {
        setTitle("Label Animation Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un JLayeredPane pour superposer les composants
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.setLayout(null);
        add(layeredPane);

        // Créer un JLabel original
        originalLabel = new JLabel("Original Label");
        originalLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        originalLabel.setForeground(Color.blue);
        originalLabel.setSize(150, 30);
        originalLabel.setLocation(100, 100);
        layeredPane.add(originalLabel, JLayeredPane.DEFAULT_LAYER);

        // Bouton pour déclencher l'animation
        JButton animateButton = new JButton("Animate Label");
        animateButton.setBounds(350, 500, 150, 30);
        animateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateLabel(originalLabel, new Point(600, 400), new Dimension(300, 60), Color.RED, new Font("Arial", Font.BOLD, 55), 1000);
            }
        });
        layeredPane.add(animateButton, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    // Méthode pour animer un JLabel
    public void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration) {
        // Dupliquer le JLabel
        JLabel animatedLabel = new JLabel(label.getText());
        animatedLabel.setFont(label.getFont());
        animatedLabel.setForeground(label.getForeground());
        animatedLabel.setSize(label.getSize());
        animatedLabel.setLocation(label.getLocation());
        layeredPane.add(animatedLabel, JLayeredPane.PALETTE_LAYER);

        Point startLocation = animatedLabel.getLocation();
        Dimension startSize = animatedLabel.getSize();
        Color startColor = animatedLabel.getForeground();
        Font startFont = animatedLabel.getFont();

        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                double progress = (double) elapsed / duration;

                if (progress >= 1.0) {
                    progress = 1.0;
                    timer.stop();
                }

                // Interpolation de la position
                int newX = (int) (startLocation.x + progress * (targetLocation.x - startLocation.x));
                int newY = (int) (startLocation.y + progress * (targetLocation.y - startLocation.y));
                animatedLabel.setLocation(newX, newY);

                // Interpolation de la taille
                int newWidth = (int) (startSize.width + progress * (targetSize.width - startSize.width));
                int newHeight = (int) (startSize.height + progress * (targetSize.height - startSize.height));
                animatedLabel.setSize(newWidth, newHeight);

                // Interpolation de la couleur
                int newRed = (int) (startColor.getRed() + progress * (targetColor.getRed() - startColor.getRed()));
                int newGreen = (int) (startColor.getGreen() + progress * (targetColor.getGreen() - startColor.getGreen()));
                int newBlue = (int) (startColor.getBlue() + progress * (targetColor.getBlue() - startColor.getBlue()));
                animatedLabel.setForeground(new Color(newRed, newGreen, newBlue));
                
             // Interpolation de la taille de la police
                int startFontSize = startFont.getSize();
                int targetFontSize = targetFont.getSize();
                int newFontSize = (int) (startFontSize + progress * (targetFontSize - startFontSize));
                Font newFont = startFont.deriveFont((float) newFontSize);
                animatedLabel.setFont(newFont);


                // Changer la police à la fin de l'animation
                if (progress == 1.0) {
                    animatedLabel.setFont(targetFont);
                }

                animatedLabel.revalidate();
                animatedLabel.repaint();
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExampleAnimationDeplacement());
    }
}
