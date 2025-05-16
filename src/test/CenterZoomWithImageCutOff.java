package test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;


/* ---------- 2. Exemple complet ------------------------------------ */
public class CenterZoomWithImageCutOff {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Zoom (snapshot + cut-off)");
            JComponent content = buildContent();

            SnapshotLayerUI ui = new SnapshotLayerUI();
            JLayer<JComponent> layer = new JLayer<>(content, ui);

            JButton zoomBtn = new JButton("Zoom !");
            zoomBtn.addActionListener(e -> animateZoom(ui, layer, zoomBtn));

            JPanel root = new JPanel(new BorderLayout());
            root.add(layer, BorderLayout.CENTER);
            root.add(zoomBtn, BorderLayout.SOUTH);

            f.setContentPane(root);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(600, 600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    /* Panneau 1 image + 8 labels */
    private static JComponent buildContent() {
        JPanel p = new JPanel(new GridLayout(3, 3, 20, 20));

        try {
            BufferedImage img = ImageIO.read(Paths.get("resources/imgInterface/icon.png").toFile());
            JLabel pic = new JLabel(new ImageIcon(img));
            pic.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            p.add(pic);
        } catch (Exception ex) {
            p.add(new JLabel("Image manquante", JLabel.CENTER));
        }

        for (int i = 1; i <= 8; i++) {
            JLabel lab = new JLabel("Label " + i, JLabel.CENTER);
            lab.setFont(lab.getFont().deriveFont(24f));
            lab.setOpaque(true);
            lab.setBackground(new Color(240, 240, 255));
            p.add(lab);
        }
        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        return p;
    }

    /* ---------- 3. Animation  ------------------------------------- */
    private static void animateZoom(final SnapshotLayerUI ui,
                                    final JLayer<JComponent> layer,
                                    final JButton trigger) {

        trigger.setEnabled(false);
        final JComponent view = layer.getView();

        /* 1) snapshot & état initial */
        ui.takeSnapshot(view);
        ui.setFocus(new Point(layer.getWidth() / 2, layer.getHeight() / 2));
        ui.setScale(0.0);
        layer.repaint();

        /* 2) boucle à 120 FPS */
        final long begin    = System.nanoTime();
        final long duration = 2_000_000_000L;          // 200 ms
        final double start  = 0.1, target = 1.0;
        final double cutoff = 0.002;                 // seuil sub-pixel
        final double[] prev = { start };

        Timer timer = new Timer(8, null);            // 8 ms → ≈120 FPS
        timer.addActionListener(ev -> {
            double t = (System.nanoTime() - begin) / (double) duration;
            if (t >= 1) t = 1;

            /* easing sinus (décélération douce) */
            double ease = Math.sin((t * Math.PI) / 2);

            double next = start + (target - start) * ease;

            /* --- Cut-off sub-pixel --- */
            if (Math.abs(next - prev[0]) < cutoff || t >= 1) {
                next = target;
            }
            ui.setScale(next);
            prev[0] = next;

            layer.repaint();

            if (t >= 1) {
                ((Timer) ev.getSource()).stop();
                ui.releaseSnapshot(view);           // réactive la vraie vue
                trigger.setEnabled(true);
            }
        });
        timer.start();
    }
}
