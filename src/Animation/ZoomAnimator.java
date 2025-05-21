package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

public final class ZoomAnimator {

	private ZoomAnimator() {
	}

	/* ---------- UI snapshot (D : opacité progressive) ---------------- */
	private static final class SnapshotUI extends LayerUI<JComponent> {
		private static final long serialVersionUID = 1L;
		private double scale = 1e-3;
		private BufferedImage snap;
		@SuppressWarnings("unused")
		private Point snapshotOffset = new Point(0, 0);

		public void setSnapshotOffset(Point p) {
			snapshotOffset = p;
		}

		private Point finalLocation = new Point();
		private Dimension finalSize = new Dimension();

		public void setFinalPositionAndSize(Point loc, Dimension size) {
			finalLocation = loc;
			finalSize = size;
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			if (snap != null) {
				Graphics2D g2 = (Graphics2D) g.create();
				double eased = scale; // on passe la valeur de scale ici, interpolée de 0.15 à 1.0

				// Début: centre fenêtre, fin: emplacement final
				int w0 = (int) (snap.getWidth() * 0.15);
				int h0 = (int) (snap.getHeight() * 0.15);
				int w1 = finalSize.width;
				int h1 = finalSize.height;

				int winW = c.getWidth();
				int winH = c.getHeight();
				int x0 = (winW - w0) / 2;
				int y0 = (winH - h0) / 2;
				int x1 = finalLocation.x;
				int y1 = finalLocation.y;

				int w = (int) (w0 + (w1 - w0) * eased);
				int h = (int) (h0 + (h1 - h0) * eased);
				int x = (int) (x0 + (x1 - x0) * eased);
				int y = (int) (y0 + (y1 - y0) * eased);

				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				float alpha = (float) Math.min(1.0, 0.96 + 0.4 * scale);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				g2.drawImage(snap, x, y, w, h, null);
				g2.dispose();
			} else {
				super.paint(g, c);
			}
		}

		void setScale(double s) {
			scale = s;
		}
		void take(JComponent v) {
			Rectangle box = computeBoundingBox(v);
			if (box.width <= 0 || box.height <= 0) {
				box = new Rectangle(0, 0, v.getWidth(), v.getHeight());
			}
			snap = new BufferedImage(box.width, box.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = snap.createGraphics();
			g2.translate(-box.x, -box.y); // décale pour ne prendre que la partie utile
			v.printAll(g2);
			g2.dispose();
			setSnapshotOffset(box.getLocation());
			v.setVisible(false);
		}
		void release(JComponent v) {
			snap = null;
			v.setVisible(true);
		}
	}

	private static Rectangle computeBoundingBox(JComponent panel) {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for (Component comp : panel.getComponents()) {
			if (!comp.isVisible())
				continue;
			Rectangle b = comp.getBounds();
			minX = Math.min(minX, b.x);
			minY = Math.min(minY, b.y);
			maxX = Math.max(maxX, b.x + b.width);
			maxY = Math.max(maxY, b.y + b.height);
		}
		// Sécurité si le panel est vide
		if (minX == Integer.MAX_VALUE)
			return new Rectangle(0, 0, panel.getWidth(), panel.getHeight());
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	public static void zoomIn(PanelAnimationConfiguration cfg, JFrame frame, JComponent comp, int layerId, Runnable onEnd) {
		comp.setOpaque(false);
		comp.setDoubleBuffered(false);
		if (!cfg.isZoomAnimationEnabled()) {
			comp.setVisible(true);
			comp.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			comp.revalidate();
			comp.repaint();
			comp.setDoubleBuffered(true);
			if (onEnd != null)
				onEnd.run();
			return;
		}

		Container parent = comp.getParent();
		int indexInParent = (parent != null) ? parent.getComponentZOrder(comp) : -1;
		if (comp.getWidth() == 0 || comp.getHeight() == 0) {
			Dimension pref = comp.getPreferredSize();
			if (pref.width == 0 || pref.height == 0)
				pref = new Dimension(20, 20);
			comp.setSize(pref);
		}

		SnapshotUI ui = new SnapshotUI();
		JLayer<JComponent> deco = new JLayer<>(comp, ui);
		JLayeredPane root = frame.getLayeredPane();
		Rectangle full = root.getBounds();
		deco.setBounds(full);
		root.add(deco, Integer.valueOf(layerId));
		ui.take(comp);
		// Obtenir position finale (dans la root pane !)
		Rectangle finaleRect = computeBoundingBox(comp);
		Point finalLocationInRoot = finaleRect.getLocation();
		Dimension finalSize = finaleRect.getSize();
		System.out.println("location du panel à animer : "+finalLocationInRoot+", size panel à animer"+finalSize);
		ui.setFinalPositionAndSize(finalLocationInRoot, finalSize);

		final double startScale = 0.02;
		int durationMs = cfg.getZoomAnimationDuration();
		ui.setScale(startScale);

		final long start = System.nanoTime();
		Timer timer = new Timer(13, null); // ≈ 60 fps
		timer.setCoalesce(true);
		timer.addActionListener(e -> {
			double prog = Math.min(1.0, (System.nanoTime() - start) / 1_000_000.0 / durationMs); // 0 → 1
			//fonction qui gere l'animation
			double eased = easeInOutCustom(prog);
			double s = startScale + (1 - startScale) * eased;
			SwingUtilities.invokeLater(() -> {
				ui.setScale(s);
				deco.repaint();
			});

			if (prog >= 1.0) {
				timer.stop();
				ui.release(comp);
				root.remove(deco);
				comp.setDoubleBuffered(true);
				if (parent != null) {
					SwingUtilities.invokeLater(() -> {
						parent.add(comp, indexInParent);
						parent.revalidate();
						parent.repaint();
					});
				} else {
					root.add(comp, Integer.valueOf(layerId));
				}
				if (onEnd != null)
					SwingUtilities.invokeLater(onEnd);
			}
		});
		timer.start();
	}

	/** sinus – départ/arrêt très doux */
	public static double easeInOutSine(double t) {
		return -(Math.cos(Math.PI * t) - 1) / 2;
	}

	public static double easeInOutQuad(double t) {
		return t < .5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
	}
	public static double easeOutCubic(double t) {
	    return 1 - Math.pow(1 - t, 3);
	}
	public static double easeInOutCubic(double t) {
	    return t < 0.5
	        ? 4 * t * t * t
	        : 1 - Math.pow(-2 * t + 2, 3) / 2;
	}
	// Utilitaire Bezier easing
	public static double cubicBezier(double t, double p0, double p1, double p2, double p3) {
	    double u = 1 - t;
	    return u * u * u * p0
	        + 3 * u * u * t * p1
	        + 3 * u * t * t * p2
	        + t * t * t * p3;
	}
	// Pour un easing naturel, tu veux souvent (0.4, 0.0, 0.2, 1.0) sur Y, X va de 0 à 1 donc tu utilises t en abscisse.
	public static double easeInOutCustom(double t) {
	    // Bezier Y points (X:0,Y:0), (X:0.4,Y:0), (X:0.2,Y:1), (X:1,Y:1)
	    return cubicBezier(t, 0, 0, 1, 1); // Pour simplifier, souvent on ne se soucie que de Y
	}



}