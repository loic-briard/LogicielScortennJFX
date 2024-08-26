package JFX_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class zoomClaude extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
    private double progress = 0;
    private long lastTime;
    private final double ANIMATION_DURATION = 2500; // 2 seconds
    private boolean isAnimating = false;

    public zoomClaude(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);

        new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAnimating) {
                    long currentTime = System.nanoTime();
                    double deltaTime = (currentTime - lastTime) / 1e6; // Convert to milliseconds
                    lastTime = currentTime;

                    progress += deltaTime / ANIMATION_DURATION;
                    if (progress >= 1.0) {
                        progress = 1.0;
                        isAnimating = false;
                    }
                    repaint();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            double scale = easeInOutQuad(progress);
            int w = (int) (getWidth() * scale);
            int h = (int) (getHeight() * scale);
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            
            g2d.drawImage(image, x, y, w, h, null);
        }
    }

    private double easeInOutQuad(double t) {
        return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }

    public void startAnimation() {
        progress = 0;
        isAnimating = true;
        lastTime = System.nanoTime();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Zoom Animation");
            zoomClaude panel = new zoomClaude("Background/fondPourSolo.png");
            frame.add(panel);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            panel.startAnimation();
        });
    }
}