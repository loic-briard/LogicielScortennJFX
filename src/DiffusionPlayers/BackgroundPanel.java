package DiffusionPlayers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	private Image backgroundImage;
	private double scale = 1.0;
	
    public BackgroundPanel(String backgroundImage) {
    	// Charger l'image
        try {
			this.backgroundImage = ImageIO.read(new File(backgroundImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setScale(double scale) {
        this.scale = scale;
    }

    public void addComponent(Component comp) {
        add(comp);
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        if (backgroundImage != null) {
        	g2d.drawImage(backgroundImage, this.getLocation().x, this.getLocation().y, this.getWidth(), this.getHeight(), this);
        }
        super.paintChildren(g2d);
    }
}
