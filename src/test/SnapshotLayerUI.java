package test;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

/* ---------- 1. LayerUI avec snapshot ------------------------------- */
class SnapshotLayerUI extends LayerUI<JComponent> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double        scale   = 1e-3;
    private Point         focus   = new Point();
    private BufferedImage snapshot;

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.translate((1 - scale) * focus.x, (1 - scale) * focus.y);
        g2.scale(scale, scale);

        if (snapshot != null) g2.drawImage(snapshot, 0, 0, c);
        else                  super.paint(g2, c);

        g2.dispose();
    }

    /* API */
    void   setScale(double s) { scale = s; }
    double getScale()         { return scale; }
    void   setFocus(Point p)  { focus = p; }

    void takeSnapshot(JComponent view) {
        snapshot = new BufferedImage(view.getWidth(), view.getHeight(),
                                     BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = snapshot.createGraphics();
        view.printAll(g2);
        g2.dispose();
        view.setVisible(false);
    }
    void releaseSnapshot(JComponent view) {
        snapshot = null;
        view.setVisible(true);
    }
}
