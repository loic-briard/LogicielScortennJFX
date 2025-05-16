package test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class CenterZoomWithImage {

    /* ---- LayerUI identique à l’exemple précédent ---- */
    static class ZoomUI extends LayerUI<JComponent> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private double scale = 1e-3;
        private Point  focus = new Point();

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.translate((1 - scale) * focus.x, (1 - scale) * focus.y);
            g2.scale(scale, scale);
            super.paint(g2, c);
            g2.dispose();
        }

        void   setScale(double s){ scale = s; }
        double getScale()        { return scale; }
        void   setFocus(Point p) { focus = p; }
    }

    /* ---- Construit le contenu : 1 image + 8 labels ---- */
    private static JComponent buildContent() {
        JPanel p = new JPanel(new GridLayout(3, 3, 20, 20));

        // 1) Charge une image (PNG/JPG) du classpath ou du disque
        BufferedImage img = null;
        try {
            img = ImageIO.read(Paths.get("resources/imgInterface/icon.png").toFile()); // <‑— chemin ressource
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Image non trouvée ; continue sans.");
        }

        if (img != null) {
            JLabel pic = new JLabel(new ImageIcon(img));
            pic.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            p.add(pic); // l’image occupe la 1ʳᵉ case
        } else {
            p.add(new JLabel("Image manquante", JLabel.CENTER));
        }

        // 2) Remplit les 8 cases restantes par des labels texte
        for (int i = 1; i <= 8; i++) {
            JLabel lab = new JLabel("Label " + i, JLabel.CENTER);
            lab.setFont(lab.getFont().deriveFont(24f));
            lab.setOpaque(true);
            lab.setBackground(new Color(240, 240, 255));
            p.add(lab);
        }

        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Zoom depuis le centre (avec image)");
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            SnapshotLayerUI ui = new SnapshotLayerUI();
            JComponent cp = buildContent();
            JLayer<JComponent> layer = new JLayer<>(cp, ui);

            JButton zoomBtn = new JButton("Zoom !");
            zoomBtn.addActionListener(e -> animateZoom(ui, layer, zoomBtn));

            JPanel root = new JPanel(new BorderLayout());
            root.add(layer, BorderLayout.CENTER);
            root.add(zoomBtn, BorderLayout.SOUTH);

            f.setContentPane(root);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //f.setSize(600, 600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

	private static void animateZoom(final SnapshotLayerUI ui, final JLayer<JComponent> layer, final JButton trigger) {

		trigger.setEnabled(false);
		final JComponent view = layer.getView(); // notre panel labels + image

// 1) snapshot + état initial
		ui.takeSnapshot(view);
		ui.setFocus(new Point(layer.getWidth() / 2, layer.getHeight() / 2));
		ui.setScale(0.2);
		layer.repaint();

// 2) animation ~60 FPS
		final long begin = System.nanoTime();
		final long duration = 3_000_000_000L; // 300 ms

		Timer timer = new Timer(8, null);
		timer.addActionListener(ev -> {
			double t = (System.nanoTime() - begin) / (double) duration;
			if (t >= 1)	t = 1;

//			double ease =  Easings.easeInOutQuad(t);  
			double ease =  Easings.easeInOutSine(t);  
			
			
			ui.setScale(ease);
			layer.repaint();

			if (t >= 1) {
				((Timer) ev.getSource()).stop();

// 3) fin : on remet la vraie vue
				ui.releaseSnapshot(view);
				trigger.setEnabled(true);
			}
		});
		timer.start();
	}

    
}

//    /* ---- Animation identique ---- */
//    private static void animateZoom(final ZoomUI ui,
//                                    final JComponent layer,
//                                    final JButton trigger) {
//        trigger.setEnabled(false);
//
//        ui.setFocus(new Point(layer.getWidth() / 2, layer.getHeight() / 2));
//
//        final double start   = 0.0;
//        final double target  = 1.0;
//        final long   duration = 5_300_000_000L;
//        final long   begin    = System.nanoTime();
//
//        Timer timer = new Timer(16, null);
//        timer.addActionListener(ev -> {
//            double t = (System.nanoTime() - begin) / (double) duration;
//            if (t >= 1) { t = 1; ((Timer) ev.getSource()).stop(); trigger.setEnabled(true); }
//
//            double ease = t < .5 ? 4 * t*t*t
//                                 : 1 - Math.pow(-2*t + 2, 3) / 2;
//
//            ui.setScale(start + (target - start) * ease);
//            layer.repaint();
//            Toolkit.getDefaultToolkit().sync();
//        });
//        timer.start();
//    }
//}

    
