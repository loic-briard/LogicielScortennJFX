package Diffusion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

public class WindowAnimationConfiguration extends JFrame{
	private static final long serialVersionUID = 1L;

    private JCheckBox zoomAnimationCheckBox;
    private JSpinner zoomAnimationSpinner;
    private JCheckBox labelAnimationCheckBox;
    private JSpinner labelAnimationSpinner;

    public WindowAnimationConfiguration() {
        setTitle("Animation Configuration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridBagLayout());

        ImageIcon logoIcon = new ImageIcon("icon.png");
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            System.err.println("Impossible de charger l'icône.");
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Zoom Animation
        zoomAnimationCheckBox = new JCheckBox("Zoom Animation");
        zoomAnimationCheckBox.setEnabled(true);
        add(zoomAnimationCheckBox, gbc);

        gbc.gridx = 1;
        zoomAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 100));
        add(zoomAnimationSpinner, gbc);

        gbc.gridx = 2;
        add(new JLabel("ms"), gbc);

        // Label Animation
        gbc.gridx = 0;
        gbc.gridy = 1;
        labelAnimationCheckBox = new JCheckBox("Label Animation");
        labelAnimationCheckBox.setEnabled(true);
        add(labelAnimationCheckBox, gbc);

        gbc.gridx = 1;
        labelAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 100));
        add(labelAnimationSpinner, gbc);

        gbc.gridx = 2;
        add(new JLabel("ms"), gbc);
        setVisible(true);
    }


    // Getter methods for the new components
    public boolean isZoomAnimationEnabled() {
        return zoomAnimationCheckBox.isSelected();
    }

    public int getZoomAnimationDuration() {
    	if(isZoomAnimationEnabled())
    		return (int) zoomAnimationSpinner.getValue();
    	else
    		return 0;
    }

    public boolean isLabelAnimationEnabled() {
        return labelAnimationCheckBox.isSelected();
    }

    public int getLabelAnimationDuration() {
    	if(isLabelAnimationEnabled())
    		return (int) labelAnimationSpinner.getValue();
    	else
    		return 0;
    }
	
	public void zoomPanel(ZoomablePanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
        System.out.println("Animation ZOOM");
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();
        
        int duration = getZoomAnimationDuration();
        System.out.println("dure animation zoom : "+duration);

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
    public void animateLabel(/*JLabel label,*/JPanel panel ,Point targetLocation,Dimension targetSize, Color targetColor, Font targetFont, Integer layer, JLayeredPane layeredPane, Runnable onComplete) {
    	System.out.println("Animation ANIMATELABEL : "+panel.getName());
    	JLabel startLabel = new JLabel();
    	for (Component component : panel.getComponents()) {
    	    if (component.getClass().equals(JLabel.class)) {
    	    	startLabel = (JLabel) component;
    	    }
    	}
    	int duration = getLabelAnimationDuration();
    	System.out.println("dure animation label : "+duration);
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
