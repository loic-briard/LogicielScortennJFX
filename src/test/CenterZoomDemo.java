//package test;
//import java.awt.*;
//import javax.swing.*;
//import javax.swing.plaf.LayerUI;
//
//public class CenterZoomDemo {
//
//    /** UI qui applique un scale + translate avant de peindre la vue. */
//    static class ZoomUI extends LayerUI<JComponent> {
//        /**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		private double scale = 1e-3;             // commence presque à 0
//        private Point  focus = new Point(0, 0);  // centre du zoom
//
//        @Override
//        public void paint(Graphics g, JComponent c) {
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//
//            // translate pour que le "centre" reste fixe
//            g2.translate((1 - scale) * focus.x, (1 - scale) * focus.y);
//            g2.scale(scale, scale);
//
//            super.paint(g2, c);
//            g2.dispose();
//        }
//
//        /* accès depuis l'anim */
//        void   setScale(double s) { scale = s; }
//        double getScale()         { return scale; }
//        void   setFocus(Point p)  { focus = p; }
//    }
//
//    private static JComponent buildContent() {
//        JPanel p = new JPanel(new GridLayout(3, 3, 20, 20));
//        for (int i = 1; i <= 9; i++) {
//            JLabel lab = new JLabel("Label " + i, JLabel.CENTER);
//            lab.setFont(lab.getFont().deriveFont(24f));
//            lab.setOpaque(true);
//            lab.setBackground(new Color(240, 240, 255));
//            p.add(lab);
//        }
//        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
//        return p;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame f = new JFrame("Zoom depuis le centre");
//            ZoomUI ui     = new ZoomUI();
//            JComponent cp = buildContent();
//            JLayer<JComponent> layer = new JLayer<>(cp, ui);
//
//            JButton zoomBtn = new JButton("Zoom !");
//            zoomBtn.addActionListener(e -> animateZoom(ui, layer, zoomBtn));
//
//            JPanel root = new JPanel(new BorderLayout());
//            root.add(layer, BorderLayout.CENTER);
//            root.add(zoomBtn, BorderLayout.SOUTH);
//
//            f.setContentPane(root);
//            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            f.setSize(600, 600);
//            f.setLocationRelativeTo(null);
//            f.setVisible(true);
//        });
//    }
//
//    /** lance l'animation échelle 0 → 1 ; désactive le bouton pendant ce temps */
//    private static void animateZoom(final ZoomUI ui,
//                                    final JComponent layer,
//                                    final JButton trigger) {
//        trigger.setEnabled(false);
//
//        // focus = centre courant du layer
//        ui.setFocus(new Point(layer.getWidth() / 2, layer.getHeight() / 2));
//
//        final double start   = 0.01;    // on part de 0 (ou 0.01)
//        final double target  = 1.0;    // taille “normale”
//        final long duration  = 9_000_000_000L;      // 300 ms
//        final long beginTime = System.nanoTime();
//
//        Timer timer = new Timer(16, null);        // ~60 FPS
//        timer.addActionListener(ev -> {
//            double t = (System.nanoTime() - beginTime) / (double) duration;
//            if (t >= 1) { t = 1; ((Timer) ev.getSource()).stop(); trigger.setEnabled(true); }
//
//            // easing cubic “ease in‑out”
//            double ease = (t < .5)
//                          ? 4 * t * t * t
//                          : 1 - Math.pow(-2 * t + 2, 3) / 2;
//
//            ui.setScale(start + (target - start) * ease);
//            layer.repaint();
//            Toolkit.getDefaultToolkit().sync();
//        });
//        timer.start();
//    }
//}
