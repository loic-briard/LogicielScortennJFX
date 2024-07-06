package Diffusion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WindowAnimationConfiguration extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public WindowAnimationConfiguration() {
		setTitle("Animation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 800);
		ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }
        
	}	
	
	public void zoomPanel(ZoomablePanel panel, WindowBroadcastPublic frame, int duration, Runnable onComplete) {
        System.out.println("Animation ZOOM");
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();

        // Timer pour animer le zoom
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                double progress = (double) elapsed / duration;

                if (progress >= 1.0) {
                    progress = 1.0;
                    timer.stop();

                    // Exécuter l'action une fois l'animation terminée
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }

                int newWidth = (int) (initialWidth + progress * (targetWidth - initialWidth));
                int newHeight = (int) (initialHeight + progress * (targetHeight - initialHeight));
                int newX = (targetWidth - newWidth) / 2;
                int newY = (targetHeight - newHeight) / 2;

                panel.setSize(newWidth, newHeight);
                panel.setLocation(newX, newY);
                panel.setScale(progress);
                panel.revalidate();
                panel.repaint();
            }
        });

        timer.start();
    }
    // Méthode pour animer un JLabel
    public void animateLabel(/*JLabel label,*/JPanel panel ,Point targetLocation,Dimension targetSize, Color targetColor, Font targetFont, int duration, Integer layer, JLayeredPane layeredPane, Runnable onComplete) {
    	System.out.println("Animation ANIMATELABEL : "+panel.getName());
    	JLabel startLabel = new JLabel();
    	for (Component component : panel.getComponents()) {
    	    if (component.getClass().equals(JLabel.class)) {
    	    	startLabel = (JLabel) component;
    	    }
    	}
    	// Dupliquer le JLabel    	
        JLabel animatedLabel = new JLabel(startLabel.getText());
        animatedLabel.setFont(startLabel.getFont());
        animatedLabel.setForeground(startLabel.getForeground());
        animatedLabel.setSize(panel.getSize());
        animatedLabel.setLocation(panel.getLocation());
        layeredPane.add(animatedLabel, layer);

        Point startLocation = animatedLabel.getLocation();
//        Dimension startSize = animatedLabel.getSize();
        Color startColor = animatedLabel.getForeground();
        Font startFont = animatedLabel.getFont();
        
        JLabel dimensionLabel = new JLabel(startLabel.getText());
        dimensionLabel.setFont(targetFont);
		animatedLabel.setSize(dimensionLabel.getWidth()+2,dimensionLabel.getHeight()+2);
//        animatedLabel.setSize(targetSize);
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                double progress = (double) elapsed / duration;

                if (progress >= 1.0) {
                    progress = 1.0;
                    timer.stop();
                 // Exécuter l'action une fois l'animation terminée
                    if (onComplete != null) {
                        onComplete.run();
                        // Parcourir et supprimer les composants de la couche spécifique
                        for (Component component : layeredPane.getComponentsInLayer(layer)) {
                            layeredPane.remove(component);
                        }

                        // Rafraîchir le layeredPane
                        layeredPane.repaint();
                        layeredPane.revalidate();
                    }
                }

                // Interpolation de la position
                int newX = (int) (startLocation.x + progress * (targetLocation.x - startLocation.x));
                int newY = (int) (startLocation.y + progress * (targetLocation.y - startLocation.y));
                animatedLabel.setLocation(newX, newY);

                // Interpolation de la taille
//                int newWidth = (int) (startSize.width + progress * (targetSize.width - startSize.width));
//                int newHeight = (int) (startSize.height + progress * (targetSize.height - startSize.height));
//                animatedLabel.setSize(newWidth, newHeight);
                animatedLabel.setSize((int)animatedLabel.getPreferredSize().getWidth(), (int)animatedLabel.getPreferredSize().getHeight());

                // Interpolation de la couleur
                int newRed = (int) (startColor.getRed() + progress * (targetColor.getRed() - startColor.getRed()));
                int newGreen = (int) (startColor.getGreen() + progress * (targetColor.getGreen() - startColor.getGreen()));
                int newBlue = (int) (startColor.getBlue() + progress * (targetColor.getBlue() - startColor.getBlue()));
                animatedLabel.setForeground(new Color(newRed, newGreen, newBlue));
                
                // Interpolation de la taille de la police
                int startFontSize = startFont.getSize();
                int targetFontSize = targetFont.getSize();
                int newFontSize = (int) (startFontSize + progress * (targetFontSize - startFontSize));
                Font newFont = startFont.deriveFont((float) newFontSize);
                animatedLabel.setFont(newFont);

                // Changer la police à la fin de l'animation
                if (progress == 1.0) {
                    animatedLabel.setFont(targetFont);
                    animatedLabel.setSize((int)animatedLabel.getPreferredSize().getWidth()+2, (int)animatedLabel.getPreferredSize().getHeight()+2);
                }

                animatedLabel.revalidate();
                animatedLabel.repaint();
            }
        });

        timer.start();
    }
}
