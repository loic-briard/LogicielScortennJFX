package JFX_test;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.BaseTween;

import javax.swing.*;

import Main.ImageUtility;

import java.awt.*;

public class TweenEngineZoomPanel {
    private JPanel panel;
    private JFrame frame;
    private TweenManager tweenManager;
    private float startScale;
    private float duration;
    private Runnable onComplete;

    private Dimension originalPanelSize;
    private Rectangle[] originalComponentBounds;
    private float[] originalFontSizes;

    public TweenEngineZoomPanel(JPanel panel, JFrame frame, float startScale, float duration, Runnable onComplete) {
        this.panel = panel;
        this.frame = frame;
        this.startScale = startScale;
        this.duration = duration;
        this.onComplete = onComplete;
        this.tweenManager = new TweenManager();

        Tween.registerAccessor(JPanel.class, new JPanelAccessor());

        originalPanelSize = panel.getSize();
        originalComponentBounds = new Rectangle[panel.getComponentCount()];
        originalFontSizes = new float[panel.getComponentCount()];

        for (int i = 0; i < panel.getComponentCount(); i++) {
            originalComponentBounds[i] = panel.getComponent(i).getBounds();

            if (panel.getComponent(i) instanceof JLabel) {
                JLabel label = (JLabel) panel.getComponent(i);
                originalFontSizes[i] = label.getFont().getSize2D();
            }
        }
    }

    public void startZoomAnimation() {
        Rectangle originalBounds = panel.getBounds();

        int startWidth = (int) (originalBounds.width * startScale);
        int startHeight = (int) (originalBounds.height * startScale);
        int startX = originalBounds.x + (originalBounds.width - startWidth) / 2;
        int startY = originalBounds.y + (originalBounds.height - startHeight) / 2;

        panel.setBounds(startX, startY, startWidth, startHeight);
        scaleComponents(panel, startScale);

        frame.setLayout(null);
        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        Tween.to(panel, JPanelAccessor.SIZE_XY, duration)
            .target(originalBounds.width, originalBounds.height, originalBounds.x, originalBounds.y)
            .ease(Quad.INOUT)
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    if (type == TweenCallback.COMPLETE && onComplete != null) {
                        onComplete.run();
                    }
                }
            })
            .start(tweenManager);
    }

    public void update() {
        tweenManager.update(0.016f);
    }

    private void scaleComponents(Container container, float scale) {
        for (int i = 0; i < container.getComponentCount(); i++) {
            Component comp = container.getComponent(i);
            Rectangle originalBounds = originalComponentBounds[i];

            int newX = (int) (originalBounds.x * scale);
            int newY = (int) (originalBounds.y * scale);
            int newWidth = (int) (originalBounds.width * scale);
            int newHeight = (int) (originalBounds.height * scale);
            comp.setBounds(newX, newY, newWidth, newHeight);

            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                float originalFontSize = originalFontSizes[i];
                float newFontSize = originalFontSize * scale;

                if (Math.abs(label.getFont().getSize2D() - newFontSize) > 0.5f) {
                    label.setFont(label.getFont().deriveFont(newFontSize));
                }
            }

            // Gestion du redimensionnement des images
            if (comp instanceof ImageUtility) {
                ImageUtility imgUtility = (ImageUtility) comp;
                imgUtility.setImageSize(newWidth, newHeight);
                // Recharger et redimensionner l'image
                imgUtility.setIcon(new ImageIcon(imgUtility.getRezizedImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)));
            }
        }
    }

    private static class JPanelAccessor implements TweenAccessor<JPanel> {
        public static final int SIZE_XY = 1;

        @Override
        public int getValues(JPanel target, int tweenType, float[] returnValues) {
            switch (tweenType) {
                case SIZE_XY:
                    returnValues[0] = target.getWidth();
                    returnValues[1] = target.getHeight();
                    returnValues[2] = target.getX();
                    returnValues[3] = target.getY();
                    return 4;
                default:
                    return -1;
            }
        }

        @Override
        public void setValues(JPanel target, int tweenType, float[] newValues) {
            switch (tweenType) {
                case SIZE_XY:
                    int newWidth = (int) newValues[0];
                    int newHeight = (int) newValues[1];
                    int newX = (int) newValues[2];
                    int newY = (int) newValues[3];

                    target.setBounds(newX, newY, newWidth, newHeight);

                    TweenEngineZoomPanel owner = (TweenEngineZoomPanel) target.getClientProperty("owner");
                    if (owner != null) {
                        float scale = (float) newWidth / owner.originalPanelSize.width;
                        owner.scaleComponents(target, scale);
                    }

                    target.revalidate();
                    target.repaint();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tween.setCombinedAttributesLimit(4);

            JFrame frame = new JFrame("Zoom Animation Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 1000);
            frame.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBackground(Color.RED);
            panel.setLayout(null);
            panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

            JLabel labelTest = new JLabel("test label pour zoom");
            Font currentFont = labelTest.getFont();
            Font newFont = currentFont.deriveFont(24.0f);
            labelTest.setFont(newFont);
            labelTest.setBounds(150, 100, 500, 50);
            labelTest.setOpaque(true);
            labelTest.setBackground(Color.YELLOW);
            panel.add(labelTest);

            JPanel panelImage = new JPanel();
            panelImage.setBackground(Color.GREEN);
            panelImage.setBounds(250, 250, 200, 200);
            panel.add(panelImage);

            // Ajouter l'image au panneau
            String imagePath = "icon.png"; // Remplacez par le chemin de votre image
            ImageUtility imageComponent = new ImageUtility(imagePath, 200);
            imageComponent.setBounds(500, 500, 300, 300);
            panel.add(imageComponent);

            float startScale = 0.05f;
            float duration = 1.0f;

            Runnable onComplete = () -> System.out.println("Animation terminée !");

            TweenEngineZoomPanel zoomAnimation = new TweenEngineZoomPanel(panel, frame, startScale, duration, onComplete);
            panel.putClientProperty("owner", zoomAnimation);
            zoomAnimation.startZoomAnimation();

            Timer timer = new Timer(20, e -> zoomAnimation.update());
            timer.start();

            frame.setVisible(true);
        });
    }
}
