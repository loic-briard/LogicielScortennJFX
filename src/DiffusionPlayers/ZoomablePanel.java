package DiffusionPlayers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ZoomablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private double scale = 1.0;
	
	
    public void setScale(double scale) {
        this.scale = scale;
    }
    public ZoomablePanel() {
//        setDoubleBuffered(true);
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