package DiffusionPlayers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private double scale = 1.0;
	
	
    public Image getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public BackgroundPanel(String backgroundImag) {
    	setDoubleBuffered(true);
    }
    public void setScale(double scale) {
        this.scale = scale;
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Dessiner l'image en fonction de l'échelle
        if (backgroundImage != null) {
            int newWidth = (int) (this.backgroundImage.getWidth(null) * scale);
            int newHeight = (int) (backgroundImage.getHeight(null) * scale);
            g2d.drawImage(backgroundImage, 0, 0, newWidth, newHeight, null);
        }
        // Dessiner les autres composants
        g2d.scale(scale, scale);
        super.paintChildren(g2d);
    }
}
