/*
 * 
 */
package DiffusionPlayers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundPanel.
 */
public class BackgroundPanel extends JPanel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The background image. */
	private Image backgroundImage;
	
	/** The scale. */
	private double scale = 1.0;
	
	
    /**
     * Gets the background image.
     *
     * @return the background image
     */
    public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	/**
	 * Sets the background image.
	 *
	 * @param backgroundImage the new background image
	 */
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	/**
	 * Instantiates a new background panel.
	 *
	 * @param backgroundImag the background imag
	 */
	public BackgroundPanel(String backgroundImag) {
    	setDoubleBuffered(true);
    }
    
    /**
     * Sets the scale.
     *
     * @param scale the new scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
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
