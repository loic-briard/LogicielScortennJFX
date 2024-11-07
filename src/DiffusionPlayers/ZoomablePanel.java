/*
 * 
 */
package DiffusionPlayers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoomablePanel.
 */
public class ZoomablePanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The scale. */
	private double scale = 1.0;
	
	
    /**
     * Sets the scale.
     *
     * @param scale the new scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }
    
    /**
     * Instantiates a new zoomable panel.
     */
    public ZoomablePanel() {
//        setDoubleBuffered(true);
    }

    /**
     * Adds the component.
     *
     * @param comp the comp
     */
    public void addComponent(Component comp) {
        add(comp);
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        super.paintChildren(g2d);
    }
}