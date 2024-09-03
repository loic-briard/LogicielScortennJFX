package JFX_test;

import javax.swing.*;

import Main.ImageUtility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class ZoomAnimator {

    private JPanel panelToZoom;
    private JFrame frame;
    private Timer timer;
    private double zoomFactor = 1.0;
    private static final double ZOOM_INCREMENT = 0.02;
    private static final int ANIMATION_DELAY = 20; // milliseconds

    public ZoomAnimator(JPanel panel, JFrame frame) {
        this.panelToZoom = panel;
        this.frame = frame;
        initializeAnimation();
    }

    private void initializeAnimation() {
        timer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomFactor += ZOOM_INCREMENT;
                if (zoomFactor > 1.0) {
                    zoomFactor = 1.0; // Ensure the zoom factor doesn't exceed 1.0
                    timer.stop(); // Stop the animation when zoom is complete
                }
                panelToZoom.repaint();
            }
        });
        timer.start();
    }

    private class ZoomPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            // Calculate the center of the frame and panel
            int panelWidth = panelToZoom.getWidth();
            int panelHeight = panelToZoom.getHeight();
            int frameWidth = frame.getWidth();
            int frameHeight = frame.getHeight();

            // Calculate the new width and height of the panel
            int newWidth = (int) (panelWidth * zoomFactor);
            int newHeight = (int) (panelHeight * zoomFactor);

            // Calculate the position to center the zoomed panel
            int x = (frameWidth - newWidth) / 2;
            int y = (frameHeight - newHeight) / 2;

            // Save the current state
            AffineTransform oldTransform = g2d.getTransform();

            // Apply the zoom transformation
            g2d.translate(x, y);
            g2d.scale(zoomFactor, zoomFactor);

            // Draw the original panel content
            panelToZoom.paint(g2d);

            // Restore the original state
            g2d.setTransform(oldTransform);
        }
    }

    public void start() {
        // Add ZoomPanel to the JFrame
        JPanel zoomPanel = new ZoomPanel();
        zoomPanel.setLayout(new BorderLayout());
        zoomPanel.add(panelToZoom, BorderLayout.CENTER);
        frame.setContentPane(zoomPanel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a sample JFrame
            JFrame frame = new JFrame("Zoom Animation Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Create the JPanel with components and null layout
            JPanel panel = new JPanel(null); // Use null layout for manual positioning
            panel.setPreferredSize(new Dimension(400, 400));
            
//            // Create and position the image
//            BufferedImage image;
//            try {
//                image = ImageIO.read(ZoomAnimator.class.getResource("icon.png"));
//            } catch (IOException e) {
//                image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//                Graphics g = image.getGraphics();
//                g.setColor(Color.BLUE);
//                g.fillRect(0, 0, 100, 100);
//                g.dispose();
//            }
            ImageUtility testimage = new ImageUtility("icon.png", 100);
            testimage.setLocation(150, 50);
//            JLabel imageLabel = new JLabel(new ImageIcon(image));
//            imageLabel.setBounds(150, 50, 100, 100); // Position the image manually
            
            // Create and position the JLabel
            JLabel textLabel = new JLabel("test zoom");
            textLabel.setBounds(150, 160, 100, 30); // Position the label manually
            
            // Create and position the red JPanel
            JPanel redPanel = new JPanel();
            redPanel.setBackground(Color.RED);
            redPanel.setBounds(150, 200, 100, 100); // Position the red panel manually

            // Add components to the panel
            panel.add(testimage);
            panel.add(textLabel);
            panel.add(redPanel);

            // Create and start the zoom animator
            ZoomAnimator animator = new ZoomAnimator(panel, frame);
            animator.start();

            frame.setVisible(true);
        });
    }
}