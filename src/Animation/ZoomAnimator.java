package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

import test.Easings;

public final class ZoomAnimator {

    private ZoomAnimator() {}

    /* ---------- UI snapshot (D : opacité progressive) ---------------- */
    private static final class SnapshotUI extends LayerUI<JComponent> {
        private static final long serialVersionUID = 1L;
        private double scale = 1e-3;
        private Point  focus = new Point();
        private BufferedImage snap;

        @Override public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate((1 - scale) * focus.x, (1 - scale) * focus.y);
            g2.scale(scale, scale);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            if (snap != null) {
                /* fondu d’opacité : 0,96 → 1 sur les 10 % finaux */
                float alpha = (float) Math.min(1.0, 0.96 + 0.4 * scale);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(snap, 0, 0, c);
            } else {
                super.paint(g2, c);
            }
            g2.dispose();
        }
        void setScale(double s){ scale = s; }
        void setFocus(Point p){ focus = p; }
        void take(JComponent v){
            snap = new BufferedImage(v.getWidth(), v.getHeight(),
                                     BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = snap.createGraphics();
            v.printAll(g2); g2.dispose();
            v.setVisible(false);
        }
        void release(JComponent v){ snap = null; v.setVisible(true); }
    }

    /* ----------  API  ------------------------------------------------- */
    public static void zoomIn(PanelAnimationConfiguration cfg,
                              JFrame  frame,
                              JComponent comp,
                              int     layerId,
                              Runnable onEnd) {

        if (!cfg.isZoomAnimationEnabled()) {          // animation désactivée
            comp.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            comp.revalidate(); comp.repaint();
            if (onEnd != null) onEnd.run();
            return;
        }

        /* 0. parent + position d'origine -------------------------------- */
        Container parent   = comp.getParent();
        int indexInParent  = (parent != null) ? parent.getComponentZOrder(comp) : -1;

        if (comp.getWidth()==0 || comp.getHeight()==0) {
            Dimension pref = comp.getPreferredSize();
            if (pref.width==0 || pref.height==0) pref = new Dimension(20,20);
            comp.setSize(pref);
        }

        /* 1. décorateur temporaire ------------------------------------- */
        SnapshotUI ui = new SnapshotUI();
        JLayer<JComponent> deco = new JLayer<>(comp, ui);

        JLayeredPane root = frame.getLayeredPane();
        Rectangle full   = root.getBounds();
        deco.setBounds(full);
        root.add(deco, Integer.valueOf(layerId));

        ui.take(comp);
        ui.setFocus(new Point(full.width/2, full.height/2));
        ui.setScale(0.0);

        /* --------- paramètres dynamiques ---------------------------------- */
        final double START_SCALE = 0.25;                 // 15 %
        final int baseDelay  = 12;      // 62 FPS
        final int boostDelay = 1;       // 125 FPS
        final long D         = cfg.getZoomAnimationDuration() * 1_000_000L;
        final long t0        = System.nanoTime();

        double pixelCut = 1.0 / Math.max(full.width, 1);   // 1 px en échelle
        final double[] prev = {0};

        Timer tm = new Timer(baseDelay, null);
        tm.addActionListener(ev -> {
            double tNorm = Math.min(1.0, (System.nanoTime() - t0) / (double) D);

            /* easing combiné (inchangé) ------------------------------------ */
            double ease;
            if (tNorm < 0.9) {
                ease = -(Math.cos(Math.PI * tNorm) - 1) / 2;      // easeInOutSine
            } else {
                double x = (tNorm - 0.9) / 0.1;
                ease = 0.9 + (1 - Math.pow(1 - x, 2)) * 0.1;      // easeOutQuad
            }
            if (tNorm > 0.95 && tNorm < 1.0) {                    // overshoot
                double o = (tNorm - 0.95) / 0.05;
                ease += 0.015 * Math.sin(Math.PI * o);
            }
//            ease = Easings.easeInOutQuad(tNorm);
            
            /* ---------- nouveau cut-off ----------------------------------- */
            double next = ease;
            if (tNorm > 0.98 && Math.abs(ease - prev[0]) < pixelCut)
                next = 1.0;                                       // verrou visuel

            double scaled = START_SCALE + (1 - START_SCALE) * next;
            ui.setScale(scaled);

            prev[0] = next;

            if (tNorm > 0.98 && tm.getDelay() != boostDelay) tm.setDelay(boostDelay);
            deco.repaint();
            if (tNorm > 0.9) Toolkit.getDefaultToolkit().sync();

            if (scaled >= 1.0) {         // on teste la scale réelle
                tm.stop();
                ui.release(comp);

                root.remove(deco);
                if (parent != null) {
                    parent.add(comp, indexInParent);
                    parent.revalidate(); parent.repaint();
                } else {
                    root.add(comp, Integer.valueOf(layerId));
                }
                if (onEnd != null) SwingUtilities.invokeLater(onEnd);
            }
        });
        tm.start();
    }

	public static void zoomPanelNoSnapshot(PanelAnimationConfiguration cfg,
            JFrame  frame,
            ZoomablePanel comp,
            int     layerId,
            Runnable onEnd) {

		if (!cfg.isZoomAnimationEnabled()) {          // animation désactivée
            comp.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            comp.revalidate(); comp.repaint();
            if (onEnd != null) onEnd.run();
            return;
        }

		/* 1. Centrage initial + scale = START */
		final double START = 0.15;
		Dimension pref = comp.getPreferredSize();
		comp.setSize(pref); // bounding-box exacte

		Point center = new Point(frame.getWidth() / 2, frame.getHeight() / 2);
		int x0 = center.x - (int) (pref.width * START / 2);
		int y0 = center.y - (int) (pref.height * START / 2);
		comp.setLocation(x0, y0);
		comp.setScale(START); // nécessite setScale(...) public

		/* 2. Anti-scintillement : coupe le double buffer Swing */
		RepaintManager mgr = RepaintManager.currentManager(comp);
		boolean db = mgr.isDoubleBufferingEnabled();
		mgr.setDoubleBufferingEnabled(false);

		/* 3. Timer */
		final long t0 = System.nanoTime();
		final long D = cfg.getZoomAnimationDuration() * 1_000_000L;
		final int delay = 11; // ≈ 91 FPS
		double pixelCut = 1.0 / Math.max(pref.width, 1);

		final double[] prev = { START };

		Timer tm = new Timer(delay, null);
		tm.addActionListener(ev -> {
			double t = Math.min(1.0, (System.nanoTime() - t0) / (double) D);
			double ease = Easings.easeInOutSine(t); // 0-1 courbe choisie
			double scale = START + (1 - START) * ease;

			/* cut-off visuel : <1 px ET après 95 % du temps */
			if (t > 0.95 && Math.abs(scale - prev[0]) < pixelCut)
				scale = 1.0;

			/* 3a. mise à l’échelle + recentrage */
			int w = (int) (pref.width * scale);
			int h = (int) (pref.height * scale);
			comp.setBounds(center.x - w / 2, center.y - h / 2, w, h);
			comp.setScale(scale);
			prev[0] = scale;
			comp.repaint();

			if (scale >= 1.0) {
				tm.stop();
				mgr.setDoubleBufferingEnabled(db); // restaure DB
			}
		});
		tm.start();
	}
}
