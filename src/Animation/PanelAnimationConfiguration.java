/*
 * 
 */
package Animation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Diffusion.PanelTournamentTree;
import Diffusion.WindowBroadcastPublic;
import Diffusion.WindowTournamentTree;
import DiffusionPlayers.BackgroundPanel;
import DiffusionPlayers.PlayerForDiffusion;
import Main.ImageUtility;
import Sauvegarde.ConfigurationSaveLoad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

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
    private  JSpinner zoomAnimationSpinner;
    
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
    
    /**
     * Instantiates a new panel animation configuration.
     *
     * @param windowTournamentTreee the window tournament treee
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException the SQL exception
     * @throws IOException 
     */
    public PanelAnimationConfiguration(WindowTournamentTree windowTournamentTreee) throws ClassNotFoundException, SQLException, IOException {
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
     * @throws IOException 
     */
    private void initFrame() throws ClassNotFoundException, SQLException, IOException {
    	
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
    public  boolean isZoomAnimationEnabled() {
        return zoomAnimationCheckBox.isSelected();
    }

    /**
     * Gets the zoom animation duration.
     *
     * @return the zoom animation duration
     */
    public  int getZoomAnimationDuration() {
        return (int) zoomAnimationSpinner.getValue();
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
        return (int) labelAnimationSpinner.getValue();
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
	public void animateLABEL(JPanel panel, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, Integer layer, JLayeredPane layeredPane,List<JComponent> ghosts, Runnable onComplete) {
//		System.out.println("isLabelAnimationEnabled "+isLabelAnimationEnabled());
	    if(!isLabelAnimationEnabled()) {
	        if(onComplete != null) 
	            onComplete.run();
	    } else {
//	        System.out.println("Animation LABEL");
	        JLabel startLabel = (JLabel) panel.getComponents()[0];
	        JLabel animatedLabel = new JLabel();
			if (panel.getName().equals("ImgFlag") || panel.getName().equals("ImgJoueur")) {
//				System.out.println("Animation IMAGE");
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
	        
			ghosts.add(animatedLabel);
	        setupAnimatedLabel(animatedLabel, startLabel, panel, layeredPane, layer,targetSize);
	        animateLabel(animatedLabel, targetLocation, targetSize, targetColor, targetFont, getLabelAnimationDuration(), layeredPane, onComplete,ghosts);
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
//	private void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration, JLayeredPane layeredPane, Integer layer, Runnable onComplete) {
//	    animRunning = true;
//		currentAnimationTimer = createTimer(true, duration, (progress) -> {
//	        boolean targetReached = updateLabelProperties(label, targetLocation, targetSize, targetColor, targetFont, progress);
//	        if (progress >= 1.0 || targetReached) {
//	            currentAnimationTimer.stop();
//	            animRunning = false;                  // ► l’animation est finie
//	            if (onComplete != null) {
//	                onComplete.run();              // ► l’animation est finie
//	            }
//	            cleanupAnimation(layeredPane, layer);
//	        }
//	    });
//	    currentAnimationTimer.start();
//	}
	
    /** Interpolation linéaire sur chaque canal ARGB. */
    private static Color interpolateColor(Color c1, Color c2, float p) {
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * p);
        int r = (int) (c1.getRed  () + (c2.getRed  () - c1.getRed  ()) * p);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * p);
        int b = (int) (c1.getBlue () + (c2.getBlue () - c1.getBlue ()) * p);
        return new Color(r, g, b, a);
    }
    
    private static final DoubleUnaryOperator EASE =
            t -> t < .5 ? 2 * t * t : -1 + (4 - 2 * t) * t;   // quad in-out
            
            
    /**
     * Retourne un Rectangle qui englobe tous les ghosts.
     * Si la liste est vide, renvoie null.
     */
    private static Rectangle unionOfAllGhostBounds(List<? extends JComponent> ghosts) {
        Rectangle r = null;
        for (JComponent c : ghosts) {
            if (r == null) {
                r = new Rectangle(c.getBounds());
            } else {
                r = r.union(c.getBounds());
            }
        }
        return r;
    }

	/**
     * Fait glisser / redimensionner un JLabel jusqu'à la destination voulue.
     *
     * @param label        le JLabel animé (déjà ajouté au layeredPane)
     * @param targetLoc    position finale (coin gauche / haut)
     * @param targetSize   taille finale
     * @param targetColor  couleur finale (null = ne pas changer)
     * @param targetFont   police finale  (null = ne pas changer)
     * @param durationMs   durée totale en millisecondes
     * @param lp           layered pane de la fenêtre
     * @param baseLayer    couche de référence (POPUP_LAYER, etc.)
     * @param onComplete   callback facultatif à la fin
	 * @param ghosts 
     */
    public void animateLabel(
            JLabel        label,
            Point         targetLoc,
            Dimension     targetSize,
            Color         targetColor,  // null = inchangé
            Font          targetFont,   // null = inchangé
            int           durationMs,
            JLayeredPane  layeredPane,
            Runnable      onComplete, List<JComponent> ghosts) {

        /* bornes INITIAL : on ne les touche plus après cette ligne */
        final int x0 = label.getX();
        final int y0 = label.getY();
        final int w0 = label.getWidth();
        final int h0 = label.getHeight();
        final Color startColor = label.getForeground();
        final float startSize  = label.getFont().getSize2D();

        final int dx = targetLoc.x        - x0;
        final int dy = targetLoc.y        - y0;
        final int dw = targetSize.width   - w0;
        final int dh = targetSize.height  - h0;
        final float dSize = targetFont != null
                ? targetFont.getSize2D() - startSize
                : 0;

        animRunning = true;
        final long startNs = System.nanoTime();
        
        int duration = isLabelAnimationEnabled() ? (int) labelAnimationSpinner.getValue() : 0;

        Timer timer = new Timer(14, null);//60hz
        timer.addActionListener(e -> {
        	
            
            double   elapsedMs = (System.nanoTime() - startNs) / 1_000_000.0;
            double   t         = Math.min(1.0, elapsedMs / duration);
            double   p         = EASE.applyAsDouble(t);

            int  x = x0 + (int) (dx * p);
            int  y = y0 + (int) (dy * p);
            int  w = w0 + (int) (dw * p);
            int  h = h0 + (int) (dh * p);

            SwingUtilities.invokeLater(() -> {
                label.setBounds(x, y, w, h);

                if (targetColor != null)
                    label.setForeground(interpolateColor(startColor, targetColor, (float) p));

                if (targetFont != null)
                    label.setFont(targetFont.deriveFont(startSize + dSize * (float) p));

                Rectangle dirty = unionOfAllGhostBounds(ghosts);
                if (dirty != null) layeredPane.repaint(dirty);
//                layeredPane.repaint();
            });

            if (t >= 1.0) {                 // animation terminée
                timer.stop();
                SwingUtilities.invokeLater(() -> {
                	animRunning = false;
                	if (onComplete != null) onComplete.run();
//                	layeredPane.remove(label);      // <-- on enlève CE label
//                    layeredPane.repaint();          // <-- rafraîchit la couche

                });
            }
        });
        timer.setCoalesce(true);
        timer.start();
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
//	public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
////		System.out.println("Animation ZOOM "+panel.getName());
//        int initialWidth = panel.getWidth();
//        int initialHeight = panel.getHeight();
//        int targetWidth = frame.getWidth();
//        int targetHeight = frame.getHeight();
//        Point initialLocation = panel.getLocation();
//        System.out.println("Animation ZOOM "+panel.getName()+"intial location "+initialLocation.toString()+", taille intial "+initialWidth+"x"+initialHeight);
//        int duration = getZoomAnimationDuration();
//        if (!isZoomAnimationEnabled()) {
//            panel.setBounds(0, 0, targetWidth, targetHeight);
//            panel.revalidate();
//            panel.repaint();
//            if (onComplete != null) {
//                onComplete.run();
//            }
//        } else {
//            final long startTime = System.nanoTime();
//            final int widthDiff = targetWidth - initialWidth;
//            final int heightDiff = targetHeight - initialHeight;
//            
//            // Désactiver le double buffering
//            panel.setDoubleBuffered(false);
//
//            Timer timer = new Timer(10, null); // Augmentation de la fréquence des mises à jour 16 = 60fps, 25 = 40fps
//            timer.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
////                    long elapsedTime = System.currentTimeMillis() - startTime;
////                    double progress = Math.min((double) elapsedTime / duration, 1.0);
//                    long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;
//                    double progress = Math.min(1.0,(double) elapsedTime / duration);
//                    
////                    double easedProgress = easeInOutCubic(progress); // Utilisation d'une autre fonction d'easing
////                    double easedProgress = easeInOutQuad(progress); // Utilisation d'une autre fonction d'easing
//                    double easedProgress = 0.15 + 0.85 * easeInOutQuad(progress);
////                    double easedProgress = 0.15 + 0.85 * easeInOutCubic(progress);
////                    double easedProgress = 0.15 + 0.85 * easeInOutSine(progress);
////                    double easedProgress = 0.15 + 0.85 * progress;
//
//                    int newWidth = (int) (initialWidth + easedProgress * widthDiff);
//                    int newHeight = (int) (initialHeight + easedProgress * heightDiff);
//
//                    // Calcul de la nouvelle position pour garder le panel centré
//                    int newX = initialLocation.x + (initialWidth - newWidth) / 2;
//                    int newY = initialLocation.y + (initialHeight - newHeight) / 2;
//
//
//                    SwingUtilities.invokeLater(() -> {
//                        panel.setBounds(newX, newY, newWidth, newHeight);
//
//                        if (panel instanceof ZoomablePanel) {
//                        	((ZoomablePanel) panel).setScale(easedProgress);
//                        }
//                        if (panel instanceof BackgroundPanel) {
//                            ((BackgroundPanel) panel).setScale(easedProgress);
//                        }
//
////                        panel.revalidate();
////                        panel.repaint();
//                    });
//
//                    if (progress >= 1.0) {
//                        timer.stop();
//                        if (onComplete != null) {
//                        	panel.setDoubleBuffered(true);
//                            SwingUtilities.invokeLater(onComplete);
//                        }
//                    }
//                }
//            });
//            timer.setCoalesce(true); // Combine les événements en retard
//            SwingUtilities.invokeLater(() -> {
//            	timer.start();
//            });
//        }
//	}
	
	
//	public void zoomPanelSnap(JPanel panel, JFrame frame, Runnable onComplete) {
//		// 1. S'assurer que tout est à jour
//		panel.setVisible(true);
//	    panel.validate();
//	    panel.repaint();
//
//	    // 2. Désactiver double-buffering pour le snapshot
//	    RepaintManager currentManager = RepaintManager.currentManager(panel);
//	    boolean db = currentManager.isDoubleBufferingEnabled();
//	    currentManager.setDoubleBufferingEnabled(false);
//
//	    // 3. Création du snapshot
//	    BufferedImage snapshot = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
//	    panel.paint(snapshot.getGraphics());
//	    
//	    // 4. Réactiver le double-buffering
//	    currentManager.setDoubleBufferingEnabled(db);
//
//	    // On cache le vrai panel pendant l'animation
//	    panel.setVisible(false);
//
//	 // ... code d'animation identique à plus haut ...
//	    class AnimatedPanel extends JPanel {
//	        double scale = 0.15;
//	        @Override
//	        protected void paintComponent(Graphics g) {
//	            super.paintComponent(g);
//	            Graphics2D g2 = (Graphics2D) g;
//
//	            int imgW = snapshot.getWidth();
//	            int imgH = snapshot.getHeight();
//	            int drawW = (int) (imgW * scale);
//	            int drawH = (int) (imgH * scale);
//
//	            int x = (getWidth() - drawW) / 2;
//	            int y = (getHeight() - drawH) / 2;
//
//	            // Image snapshot du panel (doit contenir tous les éléments joueurs)
//	            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//	            g2.drawImage(snapshot, x, y, drawW, drawH, null);
//
//	            // Debug carré rouge zoomé
//	            g2.setColor(new Color(255,0,0,128));
//	            int debugSize = (int)(100 * scale);
//	            int debugX = (getWidth() - debugSize) / 2;
//	            int debugY = (getHeight() - debugSize) / 2;
//	            g2.fillRect(debugX, debugY, debugSize, debugSize);
//	        }
//	    }
//
//	    AnimatedPanel animationPanel = new AnimatedPanel();
//	    animationPanel.setOpaque(false);
//	    animationPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//
//	    // Utilisation du glassPane comme overlay
//	    JRootPane root = frame.getRootPane();
//	    JComponent glass = (JComponent) root.getGlassPane();
//	    glass.setVisible(true);
//	    glass.setOpaque(false);
//	    glass.setLayout(null);
//
//	    glass.add(animationPanel);
//	    animationPanel.setVisible(true);
//	    glass.repaint();
//
//	    int durationMs = getZoomAnimationDuration();
//	    int steps = 24;
//	    int delay = durationMs / steps;
//
//	    Timer timer = new Timer(delay, null);
//	    final int[] step = {0};
//	    timer.addActionListener(e -> {
//	        double t = step[0] / (double) steps;
//	        animationPanel.scale = 0.15 + t * (1.0 - 0.15);
//	        animationPanel.repaint();
//
//	        if (++step[0] > steps) {
//	            timer.stop();
//	            glass.remove(animationPanel);
//	            glass.repaint();
//	            panel.setVisible(true);
//	            if (onComplete != null) onComplete.run();
//	            glass.setVisible(false);
//	        }
//	    });
//	    timer.start();
//	}


	public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
		
//		panel.setOpaque(true);
		/* 0. Paramètres de l’animation */
		double startScale = 0.15; // 15 % au départ
		int durationMs = getZoomAnimationDuration();

		/* 1. Taille finale = taille courante du panel */
		int endW = frame.getWidth();// panel.getWidth();
		int endH = frame.getHeight(); //panel.getHeight();
		

		/* 2. Taille de départ = 15 % de la taille finale */
		double startW = (endW * startScale);
		double startH = (endH * startScale);

		/* 3. Centre de la fenêtre (fixe) */
		int cx = frame.getWidth() / 2;
		int cy = frame.getHeight() / 2;
		
		System.out.println("initial bounds : "+panel.getBounds());
		/* 5. Pas d’animation ? onComplete immédiat */
		if (!isZoomAnimationEnabled()) {
			panel.setBounds(cx - endW / 2, cy - endH / 2, endW, endH);
			if (onComplete != null)
				onComplete.run();
			return;
		}

		/* 6. Animation progressive */
		panel.setDoubleBuffered(false);
		animRunning = true;

		final long start = System.nanoTime();
		Timer timer = new Timer(16, null); // ≈ 60 fps
		timer.setCoalesce(true);

		timer.addActionListener(e -> {
			double prog = Math.min(1.0, (System.nanoTime() - start) / 1_000_000.0 / durationMs); // 0 → 1
			double eased = easeInOutQuad(prog); // lissage
			

			/* Interpolation taille + position (centrée en continu) */
			int w = (int) (startW + (endW - startW) * eased);
			int h = (int) (startH + (endH - startH) * eased);
			int x = cx - w / 2;
			int y = cy - h / 2;

			SwingUtilities.invokeLater(() -> {
				panel.setBounds(x, y, w, h);
				double s = startScale + (1 - startScale) * eased; // 0.15 → 1
				if (panel instanceof ZoomablePanel)
					 ((ZoomablePanel) panel).setScale(s);
				if (panel instanceof BackgroundPanel)
					((BackgroundPanel) panel).setScale(s);
			});

			/* Fin d’animation */
			if (prog >= 1.0) {
				timer.stop();
				SwingUtilities.invokeLater(() -> {
					panel.setDoubleBuffered(true);
					animRunning = false;
					if (onComplete != null)
						onComplete.run();
				});
			}
		});

		timer.start();
	}
//	/**
//	 * Agrandit progressivement {@code panel} jusqu’à occuper toute la fenêtre {@code frame}.
//	 * Lance le callback {@code onComplete} quand l’animation est terminée,
//	 * puis retire le panneau fantôme du layeredPane.
//	 */
//	public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
//
//	    int initialW = panel.getWidth();
//	    int initialH = panel.getHeight();
//	    int targetW  = frame.getWidth();
//	    int targetH  = frame.getHeight();
//
//	    // centre actuel du panel et centre de la fenêtre
//	    Point initialLoc = new Point(frame.getWidth()/2, frame.getHeight()/2);
//	    int initialCenterX = initialLoc.x + initialW / 2;
//	    int initialCenterY = initialLoc.y + initialH / 2;
//	    int frameCenterX   = targetW / 2;
//	    int frameCenterY   = targetH / 2;
//
//	    int duration = getZoomAnimationDuration();
//
//	    if (!isZoomAnimationEnabled()) {
//	    	System.out.println("taille et position finale 1 : "+panel.getBounds());
////	        panel.setBounds(frameCenterX - targetW / 2,frameCenterY - targetH / 2,targetW, targetH);
//	        panel.setBounds(frameCenterX - targetW / 2,frameCenterY - targetH / 2, targetW, targetH);
//	        System.out.println("taille et position finale 2 : "+panel.getBounds());
//	        
//	        if (onComplete != null) onComplete.run();
//	        return;
//	    }
//
//	    final long start = System.nanoTime();
//	    final int dW = targetW - initialW;
//	    final int dH = targetH - initialH;
//
//	    animRunning = true;
//	    panel.setDoubleBuffered(false);
//	    Timer timer = new Timer(2, null);
//	    timer.addActionListener(ev -> {
//	        long ms      = (System.nanoTime() - start) / 1_000_000;
//	        double prog  = Math.min(1.0, (double) ms / duration);
//	        double eased = 0.15 + 0.85 * easeInOutQuad(prog);
////	        double eased = 0.15 + 0.85 * prog;//linear
//
//	        int w  = (int) (initialW + eased * dW);
//	        int h  = (int) (initialH + eased * dH);
//	        // centre intermédiaire = interpolation linéaire entre les deux centres
//	        int cx = (int) (initialCenterX + (frameCenterX - initialCenterX) * eased);
//	        int cy = (int) (initialCenterY + (frameCenterY - initialCenterY) * eased);
//	        int x = cx - w / 2;
//	        int y = cy - h / 2;
//
//	        SwingUtilities.invokeLater(() -> {
//	            panel.setBounds(x, y, w, h);
//	            if (panel instanceof ZoomablePanel)
//	                ((ZoomablePanel) panel).setScale(eased);
//	            if (panel instanceof BackgroundPanel)
//	                ((BackgroundPanel) panel).setScale(eased);
//	        });
//
//	        if (prog >= 1.0) {
//	            timer.stop();
//	            panel.setDoubleBuffered(true);
//	            animRunning = false;                  // ► l’animation est finie
//	            if (onComplete != null) SwingUtilities.invokeLater(onComplete);
//	        }
//	    });
//	    timer.setCoalesce(true);
//	    SwingUtilities.invokeLater(timer::start);
//	}
    
    /**
     * Setup animated label.
     *
     * @param animatedLabel the animated label
     * @param startLabel the start label
     * @param panel the panel
     * @param layeredPane the layered pane
     * @param layer the layer
     * @param targetSize 
     */
    private void setupAnimatedLabel(JLabel animatedLabel, JLabel startLabel, JPanel panel, JLayeredPane layeredPane, Integer layer, Dimension targetSize) {
        animatedLabel.setForeground(startLabel.getForeground());
        if(animatedLabel.getName() != ("image"))
        	animatedLabel.setSize(targetSize);
        if(animatedLabel.getName() == ("image"))
        	animatedLabel.setLocation(startLabel.getX()-startLabel.getWidth()/2, startLabel.getY()-startLabel.getHeight()/2);
        animatedLabel.setLocation(panel.getLocation());
        layeredPane.add(animatedLabel, layer);
    }

    /** Retourne le conteneur réel dans lequel on peut add() des composants,
     *  qu’il soit ou non déjà emballé dans un JLayer. */
    public static JComponent getRealView(JComponent possibleLayerView) {
        return (possibleLayerView.getParent() instanceof JLayer<?>)
               ? (JComponent) ((JLayer<?>) possibleLayerView.getParent()).getView()   // cas emballé
               : possibleLayerView;                                      // cas normal
    }
    
    public volatile static boolean animRunning = false;   // accès cross-thread OK
    public boolean isAnimRunning() { return animRunning; }

    
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