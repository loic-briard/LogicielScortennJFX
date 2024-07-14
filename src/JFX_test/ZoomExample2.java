package JFX_test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomExample2 extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
    private ZoomablePanel targetPanel;

    public ZoomExample2() {
        setTitle("Zoom Panel Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un JLayeredPane pour superposer les composants
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.setLayout(null);
        add(layeredPane);

        // Créer un ZoomablePanel pour le zoom
        targetPanel = new ZoomablePanel();
        targetPanel.setSize(100, 100);
        targetPanel.setBackground(Color.RED);
        targetPanel.setLocation(350, 250); // Position initiale centrée
        layeredPane.add(targetPanel, JLayeredPane.DEFAULT_LAYER);

        // Ajouter des composants au ZoomablePanel
        targetPanel.addComponent(new JLabel("Label 1"));
        targetPanel.addComponent(new JLabel("Label 2"));
        targetPanel.addComponent(new JButton("Button"));

        // Bouton pour déclencher le zoom
        JButton zoomButton = new JButton("Zoom Panel");
        zoomButton.setBounds(350, 500, 100, 30);
        zoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomPanel(targetPanel, ZoomExample2.this, JLayeredPane.PALETTE_LAYER, 500);
            }
        });
        layeredPane.add(zoomButton, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    // Méthode pour effectuer un zoom centré sur un JPanel
    public void zoomPanel(ZoomablePanel panel, JFrame frame, int targetLayer, int duration) {
        JLayeredPane layeredPane = frame.getLayeredPane();
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();

        // Ajouter le JPanel au JLayeredPane avec la couche cible
        layeredPane.add(panel, targetLayer);

        // Timer pour animer le zoom
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

                int newWidth = (int) (initialWidth + progress * (targetWidth - initialWidth));
                int newHeight = (int) (initialHeight + progress * (targetHeight - initialHeight));
                int newX = (targetWidth - newWidth) / 2;
                int newY = (targetHeight - newHeight) / 2;

                panel.setSize(newWidth, newHeight);
                panel.setLocation(newX, newY);
                panel.setScale(progress);
                panel.revalidate();
                panel.repaint();
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ZoomExample2());
    }
}

class ZoomablePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double scale = 1.0;

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void addComponent(Component comp) {
        add(comp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        super.paintChildren(g2d);
    }
}


