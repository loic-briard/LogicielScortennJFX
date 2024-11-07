/*
 * 
 */
package Diffusion;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DiffusionPlayers.BackgroundPanel;
import DiffusionPlayers.PlayerForDiffusion;
import DiffusionPlayers.ZoomablePanel;
import Main.ImageUtility;
import Sauvegarde.ConfigurationSaveLoad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class PanelAnimationConfiguration.
 */
public class PanelAnimationConfiguration extends JPanel {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The display J full check box. */
    private JCheckBox displayJFullCheckBox;
    
    /** The zoom animation check box. */
    private JCheckBox zoomAnimationCheckBox;
    
    /** The zoom animation spinner. */
    private JSpinner zoomAnimationSpinner;
    
    /** The label animation check box. */
    private JCheckBox labelAnimationCheckBox;
    
    /** The label animation spinner. */
    private JSpinner labelAnimationSpinner;
    
    /** The display tree check box. */
    private JCheckBox displayTreeCheckBox;
    
    /** The x left spinner. */
    private JSpinner xLeftSpinner;
    
    /** The x right spinner. */
    private JSpinner xRightSpinner;
    
    /** The width tree spinner. */
    private JSpinner widthTreeSpinner;
    
    /** The thickness tree spinner. */
    private JSpinner thicknessTreeSpinner;
    
    /** The animation tree check box. */
    private JCheckBox animationTreeCheckBox;
    
    /** The animation time tree spinner. */
    private JSpinner animationTimeTreeSpinner;
    
    /** The clignotement number tree spinner. */
    private JSpinner clignotementNumberTreeSpinner;
    
    /** The tree color. */
    private Color treeColor = Color.BLACK;
    
    /** The tree color button. */
    private JButton treeColorButton;
    
    /** The path tree color. */
    private Color pathTreeColor = Color.RED;
    
    /** The path tree color button. */
    private JButton pathTreeColorButton;
    
    /** The tournament tree. */
    private PanelTournamentTree tournamentTree;
    
    /** The window tournament tree. */
    private WindowTournamentTree windowTournamentTree;
    
    /** The current animation timer. */
    private Timer currentAnimationTimer;

    /**
     * Instantiates a new panel animation configuration.
     *
     * @param windowTournamentTreee the window tournament treee
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException the SQL exception
     */
    public PanelAnimationConfiguration(WindowTournamentTree windowTournamentTreee) throws ClassNotFoundException, SQLException {
    	Border contour = BorderFactory.createLineBorder(Color.black);
    	this.setBorder(contour);
        setLayout(new GridBagLayout());
        this.windowTournamentTree = windowTournamentTreee;
        
        initFrame();
        setupComponents();
        ConfigurationSaveLoad.setConfigAnimation(windowTournamentTree.eventName(),this);
        setVisible(true);
//        this.pack();
    }
    
    /**
     * Inits the frame.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException the SQL exception
     */
    private void initFrame() throws ClassNotFoundException, SQLException {
    	
    	zoomAnimationCheckBox = new JCheckBox("Zoom Animation");
        zoomAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 10));
        labelAnimationCheckBox = new JCheckBox("Label Animation");
        labelAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 10));
        
        displayJFullCheckBox = new JCheckBox("Display player tree");
		displayJFullCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					labelAnimationCheckBox.setEnabled(true);
					for (PlayerForDiffusion playerFull : windowTournamentTree.getTabPlayerForTree()) {
						if (playerFull != null)
							playerFull.setVisible(true);
					}

				} else {
					labelAnimationCheckBox.setSelected(false);
					labelAnimationCheckBox.setEnabled(false);
					for (PlayerForDiffusion playerFull : windowTournamentTree.getTabPlayerForTree()) {
						if (playerFull != null)
							playerFull.setVisible(false);
					}
				}
			}
		});
        
        displayTreeCheckBox = new JCheckBox("Display tournament tree");
        xLeftSpinner = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 1));
        xRightSpinner = new JSpinner(new SpinnerNumberModel(1285, 0, 10000, 1));
        widthTreeSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 10000, 1));
        thicknessTreeSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 10000, 1));
        treeColorButton = new JButton("Select a color");
        treeColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor = JColorChooser.showDialog(null, "Choose a color", treeColor);
                if (selectedColor != null) {
                    // Faites quelque chose avec la couleur s�lectionn�e
                    if (selectedColor != null) {
                    	treeColor = selectedColor;
                    	tournamentTree.setTreeColor(treeColor);
                    }
                }
            }
        });
        tournamentTree = new PanelTournamentTree(windowTournamentTree.getNbJoueur(),this,windowTournamentTree.initListPlayerForDiffusion(), getWidthTree(), getThicknessTree());
        tournamentTree.setBounds(0, 0, windowTournamentTree.getWindowBroadcastPublic().getWidth(), windowTournamentTree.getWindowBroadcastPublic().getHeight());
        windowTournamentTree.getWindowBroadcastPublic().addContent(50,tournamentTree);
        tournamentTree.setTreeColor(treeColor);
        tournamentTree.setVisible(isTournamentTreeEnabled());
        displayTreeCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tournamentTree.repaint();
                    tournamentTree.setVisible(true);
                } else {
                    tournamentTree.setVisible(false);
                    tournamentTree.repaint();
                }
            }
        });
        xLeftSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tournamentTree.setXLeftTree(getXLeftTree());				
			}
		});
        xRightSpinner.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		tournamentTree.setXRightTree(getXRightTree());				
        	}
        });
        widthTreeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tournamentTree.setHorizontalSpacing(getWidthTree());				
			}
		});
        thicknessTreeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	tournamentTree.setLineWidth(getThicknessTree());
            }
        });
        
        animationTreeCheckBox = new JCheckBox("Animation tournament tree");
        animationTimeTreeSpinner = new JSpinner(new SpinnerNumberModel(2000, 0, 10000, 1));
        clignotementNumberTreeSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 100, 1));
        pathTreeColorButton = new JButton("Select a color");
        pathTreeColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor = JColorChooser.showDialog(null, "Choose a color", pathTreeColor);
                if (selectedColor != null) {
                    // Faites quelque chose avec la couleur s�lectionn�e
                    if (selectedColor != null) {
                    	pathTreeColor = selectedColor;
                    	tournamentTree.setSelectedpathColor(pathTreeColor);
                    }
                }
            }
        });
    }

    /**
     * Setup components.
     */
    private void setupComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        addRow(gbc, 0, displayJFullCheckBox);
        addRow(gbc, 1, zoomAnimationCheckBox, zoomAnimationSpinner, new JLabel("ms"));
        addRow(gbc, 2, labelAnimationCheckBox, labelAnimationSpinner,new JLabel("ms"));
        addRow(gbc, 3, new JLabel(), new JLabel("Position tree Left"),new JLabel("Position tree Right"),new JLabel("Tree Width"),new JLabel("Tree thickness"));
        addRow(gbc, 4, displayTreeCheckBox, xLeftSpinner,xRightSpinner,widthTreeSpinner,thicknessTreeSpinner,treeColorButton);
        addRow(gbc, 5, new JLabel(), new JLabel("Duration animation"),new JLabel(),new JLabel("Blink number"));
        addRow(gbc, 6, animationTreeCheckBox, animationTimeTreeSpinner,new JLabel("ms"),clignotementNumberTreeSpinner,pathTreeColorButton);
    }

    /**
     * Adds the row.
     *
     * @param gbc the gbc
     * @param row the row
     * @param components the components
     */
    private void addRow(GridBagConstraints gbc, int row, JComponent... components) {
        gbc.gridy = row;
        Arrays.stream(components).forEach(component -> {
            gbc.gridx = Arrays.asList(components).indexOf(component);
            add(component, gbc);
        });
        gbc.gridx = components.length;
    }

    /**
     * Checks if is player full enabled.
     *
     * @return true, if is player full enabled
     */
    public boolean isPlayerFullEnabled() {
        return displayJFullCheckBox.isSelected();
    }
    
    /**
     * Checks if is zoom animation enabled.
     *
     * @return true, if is zoom animation enabled
     */
    public boolean isZoomAnimationEnabled() {
        return zoomAnimationCheckBox.isSelected();
    }

    /**
     * Gets the zoom animation duration.
     *
     * @return the zoom animation duration
     */
    public int getZoomAnimationDuration() {
        return isZoomAnimationEnabled() ? (int) zoomAnimationSpinner.getValue() : 0;
    }

    /**
     * Checks if is label animation enabled.
     *
     * @return true, if is label animation enabled
     */
    public boolean isLabelAnimationEnabled() {
        return labelAnimationCheckBox.isSelected();
    }
    
    /**
     * Gets the label animation duration.
     *
     * @return the label animation duration
     */
    public int getLabelAnimationDuration() {
        return isLabelAnimationEnabled() ? (int) labelAnimationSpinner.getValue() : 0;
    }
    
    /**
     * Checks if is tournament tree enabled.
     *
     * @return true, if is tournament tree enabled
     */
    public boolean isTournamentTreeEnabled() {
        return displayTreeCheckBox.isSelected();
    }
    
    /**
     * Gets the x left tree.
     *
     * @return the x left tree
     */
    public int getXLeftTree() {
        return (int)xLeftSpinner.getValue();
    }
    
    /**
     * Gets the x right tree.
     *
     * @return the x right tree
     */
    public int getXRightTree() {
        return (int)xRightSpinner.getValue();
    }
    
    /**
     * Gets the width tree.
     *
     * @return the width tree
     */
    public int getWidthTree() {
    	return (int)widthTreeSpinner.getValue();
    }
    
    /**
     * Gets the thickness tree.
     *
     * @return the thickness tree
     */
    public int getThicknessTree() {
    	return (int)thicknessTreeSpinner.getValue();
    }
    
    /**
     * Gets the panel tournament tree.
     *
     * @return the panel tournament tree
     */
    public PanelTournamentTree getPanelTournamentTree() {
    	return tournamentTree;
    }
    
    /**
     * Gets the animation path tree duration.
     *
     * @return the animation path tree duration
     */
    public int getAnimationPathTreeDuration() {
        return (int) animationTimeTreeSpinner.getValue();
    }
    
    /**
     * Gets the nb blink tree duration.
     *
     * @return the nb blink tree duration
     */
    public int getNbBlinkTreeDuration() {
        return (int) clignotementNumberTreeSpinner.getValue();
    }
    
    /**
     * Checks if is path tree animation enabled.
     *
     * @return true, if is path tree animation enabled
     */
    public boolean isPathTreeAnimationEnabled() {
        return animationTreeCheckBox.isSelected();
    }
    
    /**
     * Gets the tree color.
     *
     * @return the tree color
     */
    public Color getTreeColor() {
		return treeColor;
	}
	
	/**
	 * Gets the path tree color.
	 *
	 * @return the path tree color
	 */
	public Color getPathTreeColor() {
		return pathTreeColor;
	}

	/**
	 * Sets the display J full check box.
	 *
	 * @param displayJFullCheckBox the new display J full check box
	 */
	public void setDisplayJFullCheckBox(boolean displayJFullCheckBox) {
		this.displayJFullCheckBox.setSelected(displayJFullCheckBox);
	}
	
	/**
	 * Sets the zoom animation check box.
	 *
	 * @param zoomAnimationCheckBox the new zoom animation check box
	 */
	public void setZoomAnimationCheckBox(boolean zoomAnimationCheckBox) {
		this.zoomAnimationCheckBox.setSelected(zoomAnimationCheckBox);
	}
	
	/**
	 * Sets the zoom animation spinner.
	 *
	 * @param zoomAnimationSpinner the new zoom animation spinner
	 */
	public void setZoomAnimationSpinner(int zoomAnimationSpinner) {
		this.zoomAnimationSpinner.setValue(zoomAnimationSpinner);
	}
	
	/**
	 * Sets the label animation check box.
	 *
	 * @param labelAnimationCheckBox the new label animation check box
	 */
	public void setLabelAnimationCheckBox(boolean labelAnimationCheckBox) {
		this.labelAnimationCheckBox.setSelected(labelAnimationCheckBox);
	}
	
	/**
	 * Sets the label animation spinner.
	 *
	 * @param labelAnimationSpinner the new label animation spinner
	 */
	public void setLabelAnimationSpinner(int labelAnimationSpinner) {
		this.labelAnimationSpinner.setValue(labelAnimationSpinner);
	}
	
	/**
	 * Sets the display tree check box.
	 *
	 * @param displayTreeCheckBox the new display tree check box
	 */
	public void setDisplayTreeCheckBox(boolean displayTreeCheckBox) {
		this.displayTreeCheckBox.setSelected(displayTreeCheckBox);
	}
	
	/**
	 * Sets the x left spinner.
	 *
	 * @param xLeftSpinner the new x left spinner
	 */
	public void setxLeftSpinner(int xLeftSpinner) {
		this.xLeftSpinner.setValue(xLeftSpinner);
	}
	
	/**
	 * Sets the x right spinner.
	 *
	 * @param xRightSpinner the new x right spinner
	 */
	public void setxRightSpinner(int xRightSpinner) {
		this.xRightSpinner.setValue(xRightSpinner);
	}
	
	/**
	 * Sets the width tree spinner.
	 *
	 * @param largeurTreeSpinner the new width tree spinner
	 */
	public void setWidthTreeSpinner(int largeurTreeSpinner) {
		this.widthTreeSpinner.setValue(largeurTreeSpinner);
	}
	
	/**
	 * Sets the thickness tree spinner.
	 *
	 * @param epaisseurTreeSpinner the new thickness tree spinner
	 */
	public void setThicknessTreeSpinner(int epaisseurTreeSpinner) {
		this.thicknessTreeSpinner.setValue(epaisseurTreeSpinner);
	}
	
	/**
	 * Sets the animation tree check box.
	 *
	 * @param animationTreeCheckBox the new animation tree check box
	 */
	public void setAnimationTreeCheckBox(boolean animationTreeCheckBox) {
		this.animationTreeCheckBox.setSelected(animationTreeCheckBox);
	}
	
	/**
	 * Sets the animation time tree spinner.
	 *
	 * @param animationTimeTreeSpinner the new animation time tree spinner
	 */
	public void setAnimationTimeTreeSpinner(int animationTimeTreeSpinner) {
		this.animationTimeTreeSpinner.setValue(animationTimeTreeSpinner); ;
	}
	
	/**
	 * Sets the clignotement number tree spinner.
	 *
	 * @param clignotementNumberTreeSpinner the new clignotement number tree spinner
	 */
	public void setClignotementNumberTreeSpinner(int clignotementNumberTreeSpinner) {
		this.clignotementNumberTreeSpinner.setValue(clignotementNumberTreeSpinner);
	}
	
	/**
	 * Sets the tree color.
	 *
	 * @param treeColor the new tree color
	 */
	public void setTreeColor(Color treeColor) {
		this.treeColor = treeColor;
		tournamentTree.setTreeColor(this.treeColor);
	}
	
	/**
	 * Sets the path tree color.
	 *
	 * @param pathTreeColor the new path tree color
	 */
	public void setPathTreeColor(Color pathTreeColor) {
		this.pathTreeColor = pathTreeColor;
		tournamentTree.setSelectedpathColor(this.pathTreeColor);
	}
	
	/**
	 * Animate LABEL.
	 *
	 * @param panel the panel
	 * @param targetLocation the target location
	 * @param targetSize the target size
	 * @param targetColor the target color
	 * @param targetFont the target font
	 * @param layer the layer
	 * @param layeredPane the layered pane
	 * @param onComplete the on complete
	 */
	public void animateLABEL(JPanel panel, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, Integer layer, JLayeredPane layeredPane, Runnable onComplete) {
	    if(!isLabelAnimationEnabled()) {
	        if(onComplete != null)
	            onComplete.run();
	    } else {
	        System.out.println("Animation LABEL");
	        JLabel startLabel = (JLabel) panel.getComponents()[0];
	        JLabel animatedLabel = new JLabel();
			if (panel.getName().equals("ImgFlag") || panel.getName().equals("ImgJoueur")) {
				System.out.println("Animation IMAGE");
				ImageUtility imageLabel = (ImageUtility) panel.getComponents()[0];
				animatedLabel = new ImageUtility(imageLabel.getImagePath(),(int) targetSize.getHeight());
				targetFont = new Font("Arial", 1, 25);
				targetColor = Color.BLACK;
				animatedLabel.setName("image");
				animatedLabel.setSize(targetSize);
			}else {
				animatedLabel = new JLabel(startLabel.getText());
				animatedLabel.setFont(targetFont);
			}
	        
	        setupAnimatedLabel(animatedLabel, startLabel, panel, layeredPane, layer);
	        animateLabel(animatedLabel, targetLocation, targetSize, targetColor, targetFont, getLabelAnimationDuration(), layeredPane, layer, onComplete);
	    }
	}

	/**
	 * Animate label.
	 *
	 * @param label the label
	 * @param targetLocation the target location
	 * @param targetSize the target size
	 * @param targetColor the target color
	 * @param targetFont the target font
	 * @param duration the duration
	 * @param layeredPane the layered pane
	 * @param layer the layer
	 * @param onComplete the on complete
	 */
	private void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration, JLayeredPane layeredPane, Integer layer, Runnable onComplete) {
	    currentAnimationTimer = createTimer(true, duration, (progress) -> {
	        boolean targetReached = updateLabelProperties(label, targetLocation, targetSize, targetColor, targetFont, progress);
	        if (progress >= 1.0 || targetReached) {
	            currentAnimationTimer.stop();
	            if (onComplete != null) {
	                onComplete.run();
	            }
	            cleanupAnimation(layeredPane, layer);
	        }
	    });
	    currentAnimationTimer.start();
	}

	/**
	 * Update label properties.
	 *
	 * @param label the label
	 * @param targetLocation the target location
	 * @param targetSize the target size
	 * @param targetColor the target color
	 * @param targetFont the target font
	 * @param progress the progress
	 * @return true, if successful
	 */
	private boolean updateLabelProperties(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, double progress) {
	    Point startLocation = label.getLocation();
	    Color startColor = label.getForeground();
	    
	    Point newLocation = interpolatePoint(startLocation, targetLocation, progress);
	    label.setLocation(newLocation);
	    label.setForeground(interpolateColor(startColor, targetColor, progress));
	    if (label.getName() != ("image")) 
	        label.setSize(label.getPreferredSize());

	    label.revalidate();
	    label.repaint();

	    // Vérifier si la position actuelle est égale à la position cible
	    return newLocation.equals(targetLocation);
	}
	
	/**
	 * Ease in out sine.
	 *
	 * @param t the t
	 * @return the double
	 */
	@SuppressWarnings("unused")
	private double easeInOutSine(double t) {
        return -(Math.cos(Math.PI * t) - 1) / 2;
    }
	
	/**
	 * Ease in out cubic.
	 *
	 * @param t the t
	 * @return the double
	 */
	@SuppressWarnings("unused")
	private double easeInOutCubic(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }
	
	/**
	 * Ease in out quad.
	 *
	 * @param t the t
	 * @return the double
	 */
	private double easeInOutQuad(double t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }
	
	/**
	 * Zoom panel.
	 *
	 * @param panel the panel
	 * @param frame the frame
	 * @param onComplete the on complete
	 */
	public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
		System.out.println("Animation ZOOM "+panel.getName());
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();
        Point initialLocation = panel.getLocation();
        System.out.println("intial location "+initialLocation.toString()+", taille intial "+initialWidth+"x"+initialHeight);
        int duration = getZoomAnimationDuration();
        if (!isZoomAnimationEnabled()) {
            panel.setBounds(0, 0, targetWidth, targetHeight);
            panel.revalidate();
            panel.repaint();
            if (onComplete != null) {
                onComplete.run();
            }
        } else {
            final long startTime = System.nanoTime();
            final int widthDiff = targetWidth - initialWidth;
            final int heightDiff = targetHeight - initialHeight;
            
            // Désactiver le double buffering
            panel.setDoubleBuffered(false);

            Timer timer = new Timer(10, null); // Augmentation de la fréquence des mises à jour 16 = 60fps, 25 = 40fps
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    long elapsedTime = System.currentTimeMillis() - startTime;
//                    double progress = Math.min((double) elapsedTime / duration, 1.0);
                    long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;
                    double progress = Math.min(1.0,(double) elapsedTime / duration);
                    
//                    double easedProgress = easeInOutCubic(progress); // Utilisation d'une autre fonction d'easing
//                    double easedProgress = easeInOutQuad(progress); // Utilisation d'une autre fonction d'easing
                    double easedProgress = 0.15 + 0.85 * easeInOutQuad(progress);
//                    double easedProgress = 0.15 + 0.85 * easeInOutCubic(progress);
//                    double easedProgress = 0.15 + 0.85 * easeInOutSine(progress);
//                    double easedProgress = 0.15 + 0.85 * progress;

                    int newWidth = (int) (initialWidth + easedProgress * widthDiff);
                    int newHeight = (int) (initialHeight + easedProgress * heightDiff);

                    // Calcul de la nouvelle position pour garder le panel centré
                    int newX = initialLocation.x + (initialWidth - newWidth) / 2;
                    int newY = initialLocation.y + (initialHeight - newHeight) / 2;


                    SwingUtilities.invokeLater(() -> {
                        panel.setBounds(newX, newY, newWidth, newHeight);

                        if (panel instanceof ZoomablePanel) {
                        	((ZoomablePanel) panel).setScale(easedProgress);
                        }
                        if (panel instanceof BackgroundPanel) {
                            ((BackgroundPanel) panel).setScale(easedProgress);
                        }

//                        panel.revalidate();
//                        panel.repaint();
                    });

                    if (progress >= 1.0) {
                        timer.stop();
                        if (onComplete != null) {
                        	panel.setDoubleBuffered(true);
                            SwingUtilities.invokeLater(onComplete);
                        }
                    }
                }
            });
            timer.setCoalesce(true); // Combine les événements en retard
            SwingUtilities.invokeLater(() -> {
            	timer.start();
            });
        }
	}

    /**
     * Interpolate point.
     *
     * @param start the start
     * @param end the end
     * @param progress the progress
     * @return the point
     */
    private Point interpolatePoint(Point start, Point end, double progress) {
        int newX = (int) (start.x + progress * (end.x - start.x));
        int newY = (int) (start.y + progress * (end.y - start.y));
        return new Point(newX, newY);
    }

    /**
     * Creates the timer.
     *
     * @param zoom the zoom
     * @param duration the duration
     * @param callback the callback
     * @return the timer
     */
    private Timer createTimer(boolean zoom,int duration, ProgressCallback callback) {
        Timer timer = new Timer(5, null);
        final long startTime = System.currentTimeMillis();

        ActionListener listener = e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.min((double) elapsed / duration, 1.0);
            if(zoom) {
//            	progress = easeInOutCubic(progress); // Fonction d'interpolation plus douce
            	progress = easeInOutQuad(progress); // Fonction d'interpolation plus douce
            }
            callback.onProgress(progress);
            if (progress >= 1.0) {
                timer.stop();
            }
        };

        timer.addActionListener(listener);
        return timer;
    }
    
    
    /**
     * The Interface ProgressCallback.
     */
    private interface ProgressCallback {
        
        /**
         * On progress.
         *
         * @param progress the progress
         */
        void onProgress(double progress);
    }

    /**
     * Setup animated label.
     *
     * @param animatedLabel the animated label
     * @param startLabel the start label
     * @param panel the panel
     * @param layeredPane the layered pane
     * @param layer the layer
     */
    private void setupAnimatedLabel(JLabel animatedLabel, JLabel startLabel, JPanel panel, JLayeredPane layeredPane, Integer layer) {
        animatedLabel.setForeground(startLabel.getForeground());
        if(animatedLabel.getName() != ("image"))
        	animatedLabel.setSize(panel.getSize());
        animatedLabel.setLocation(panel.getLocation());
        layeredPane.add(animatedLabel, layer);
    }

    /**
     * Cleanup animation.
     *
     * @param layeredPane the layered pane
     * @param layer the layer
     */
    private void cleanupAnimation(JLayeredPane layeredPane, Integer layer) {
        Arrays.stream(layeredPane.getComponentsInLayer(layer)).forEach(layeredPane::remove);
        layeredPane.repaint();
        layeredPane.revalidate();
    }

    /**
     * Interpolate color.
     *
     * @param start the start
     * @param end the end
     * @param progress the progress
     * @return the color
     */
    private Color interpolateColor(Color start, Color end, double progress) {
        int newRed = interpolateColorComponent(start.getRed(), end.getRed(), progress);
        int newGreen = interpolateColorComponent(start.getGreen(), end.getGreen(), progress);
        int newBlue = interpolateColorComponent(start.getBlue(), end.getBlue(), progress);
        return new Color(newRed, newGreen, newBlue);
    }

    /**
     * Interpolate color component.
     *
     * @param start the start
     * @param end the end
     * @param progress the progress
     * @return the int
     */
    private int interpolateColorComponent(int start, int end, double progress) {
        return (int) (start + progress * (end - start));
    }
    
    /**
     * The Class AnimationData.
     */
    class AnimationData {
        
        /** The initial width. */
        int initialWidth;
        
        /** The initial height. */
        int initialHeight;
        
        /** The target width. */
        int targetWidth;
        
        /** The target height. */
        int targetHeight;
        
        /** The initial location. */
        Point initialLocation;

        /**
         * Instantiates a new animation data.
         *
         * @param initialWidth the initial width
         * @param initialHeight the initial height
         * @param targetWidth the target width
         * @param targetHeight the target height
         * @param initialLocation the initial location
         */
        public AnimationData(int initialWidth, int initialHeight, int targetWidth, int targetHeight, Point initialLocation) {
            this.initialWidth = initialWidth;
            this.initialHeight = initialHeight;
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            this.initialLocation = initialLocation;
        }
    }
}