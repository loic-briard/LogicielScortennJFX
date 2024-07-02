package JFX_test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

class ZoomPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private double scale = 1.0;

    public ZoomPanel() {
        setOpaque(false); // Pour rendre le panneau transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        
        // Appliquer une transformation d'échelle progressive
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        g2d.setTransform(at);

        // Dessiner votre contenu ici (par exemple, un texte)
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Hello, world!", 20, 30);

        g2d.dispose();
    }

    // Méthode pour mettre à jour le facteur d'échelle
    public void setScale(double scale) {
        this.scale = scale;
        repaint(); // Redessiner avec le nouvel échelle
    }
}

public class ZoomPanelExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Zoom Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        ZoomPanel zoomPanel = new ZoomPanel();
        frame.add(zoomPanel);

        frame.setVisible(true);

        // Exemple d'animation : augmenter progressivement l'échelle
        for (double s = 0.1; s <= 2.0; s += 0.1) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zoomPanel.setScale(s);
        }
    }
}
