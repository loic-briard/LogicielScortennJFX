package Animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/** Panel qui affiche une image et hérite du zoom déjà géré par ZoomablePanel. */
public class SnapshotPanel extends ZoomablePanel {

    private static final long serialVersionUID = 1L;
    private final BufferedImage snapshot;

    public SnapshotPanel(BufferedImage snapshot) {
        this.snapshot = snapshot;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // applique déjà le scale
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(snapshot, 0, 0, null);
        g2d.dispose();
    }
}
