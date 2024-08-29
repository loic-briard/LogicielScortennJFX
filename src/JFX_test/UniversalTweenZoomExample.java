package JFX_test;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.BaseTween;

import javax.swing.*;
import java.awt.*;

public class UniversalTweenZoomExample {

	private JPanel panel;
    private JFrame frame;
    private TweenManager tweenManager;
    private int startWidth;
    private int startHeight;
    private float duration;

    // Constructeur modifié pour accepter la taille de départ et la durée
    public UniversalTweenZoomExample(JPanel panel, JFrame frame, int startWidth, int startHeight, float duration) {
        this.panel = panel;
        this.frame = frame;
        this.startWidth = startWidth;
        this.startHeight = startHeight;
        this.duration = duration;
        this.tweenManager = new TweenManager();

        // Enregistrer l'accessor pour JPanel
        Tween.registerAccessor(JPanel.class, new JPanelAccessor());
    }

    public void startZoomAnimation() {
        // Taille finale (plein écran)
        int endWidth = frame.getWidth();
        int endHeight = frame.getHeight();

        // Positionner le JPanel au centre avec la taille de départ spécifiée
        panel.setSize(startWidth, startHeight);
        panel.setLocation((frame.getWidth() - startWidth) / 2, (frame.getHeight() - startHeight) / 2);

        // Ajouter le JPanel au JFrame
        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        // Créer l'animation de zoom
        Tween.to(panel, JPanelAccessor.SIZE_XY, duration)
            .target(endWidth, endHeight)
            .ease(Quad.INOUT)
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    if (type == TweenCallback.COMPLETE) {
                        // Animation terminée, faire une action si nécessaire
                    }
                }
            })
            .start(tweenManager);
    }

    public void update() {
        tweenManager.update(0.016f); // Appeler dans la boucle principale ou via un Timer
    }

    // Accessor pour JPanel pour permettre la manipulation de ses propriétés via TweenEngine
    private static class JPanelAccessor implements TweenAccessor<JPanel> {

        public static final int SIZE_XY = 1;

        @Override
        public int getValues(JPanel target, int tweenType, float[] returnValues) {
            switch (tweenType) {
                case SIZE_XY:
                    returnValues[0] = target.getWidth();
                    returnValues[1] = target.getHeight();
                    return 2;
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
                    target.setSize(newWidth, newHeight);
                    target.setLocation(
                            (target.getParent().getWidth() - newWidth) / 2,
                            (target.getParent().getHeight() - newHeight) / 2);
                    break;
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Zoom Animation Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1900, 1000);
            frame.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBackground(Color.RED);

            // Spécifiez la taille de départ et la durée de l'animation
            int startWidth = frame.getWidth()/10;  // Largeur initiale
            int startHeight = frame.getHeight()/10; // Hauteur initiale
            float duration = 1.5f; // Durée en secondes

            UniversalTweenZoomExample zoomAnimation = new UniversalTweenZoomExample(panel, frame, startWidth, startHeight, duration);
            zoomAnimation.startZoomAnimation();

            // Créer un Timer pour mettre à jour l'animation
            Timer timer = new Timer(10, e -> zoomAnimation.update());
            timer.start();

            frame.setVisible(true);
        });
    }
}
