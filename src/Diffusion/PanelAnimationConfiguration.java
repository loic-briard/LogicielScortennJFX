package Diffusion;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Main.ImageUtility;
import Sauvegarde.ConfigurationSaveLoad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Arrays;

public class PanelAnimationConfiguration extends JPanel {
    private static final long serialVersionUID = 1L;

    private JCheckBox displayJFullCheckBox;
    private JCheckBox zoomAnimationCheckBox;
    private JSpinner zoomAnimationSpinner;
    private JCheckBox labelAnimationCheckBox;
    private JSpinner labelAnimationSpinner;
    private JCheckBox displayTreeCheckBox;
    private JSpinner xLeftSpinner;
    private JSpinner xRightSpinner;
    private JSpinner widthTreeSpinner;
    private JSpinner thicknessTreeSpinner;
    private JCheckBox animationTreeCheckBox;
    private JSpinner animationTimeTreeSpinner;
    private JSpinner clignotementNumberTreeSpinner;
    private Color treeColor = Color.BLACK;
    private JButton treeColorButton;
    private Color pathTreeColor = Color.RED;
    private JButton pathTreeColorButton;
    private PanelTournamentTree tournamentTree;
    private WindowTournamentTree windowTournamentTree;
    private Timer currentAnimationTimer;

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

    private void addRow(GridBagConstraints gbc, int row, JComponent... components) {
        gbc.gridy = row;
        Arrays.stream(components).forEach(component -> {
            gbc.gridx = Arrays.asList(components).indexOf(component);
            add(component, gbc);
        });
        gbc.gridx = components.length;
    }

    public boolean isPlayerFullEnabled() {
        return displayJFullCheckBox.isSelected();
    }
    public boolean isZoomAnimationEnabled() {
        return zoomAnimationCheckBox.isSelected();
    }

    public int getZoomAnimationDuration() {
        return isZoomAnimationEnabled() ? (int) zoomAnimationSpinner.getValue() : 0;
    }

    public boolean isLabelAnimationEnabled() {
        return labelAnimationCheckBox.isSelected();
    }
    public int getLabelAnimationDuration() {
        return isLabelAnimationEnabled() ? (int) labelAnimationSpinner.getValue() : 0;
    }
    public boolean isTournamentTreeEnabled() {
        return displayTreeCheckBox.isSelected();
    }
    public int getXLeftTree() {
        return (int)xLeftSpinner.getValue();
    }
    public int getXRightTree() {
        return (int)xRightSpinner.getValue();
    }
    public int getWidthTree() {
    	return (int)widthTreeSpinner.getValue();
    }
    public int getThicknessTree() {
    	return (int)thicknessTreeSpinner.getValue();
    }
    public PanelTournamentTree getPanelTournamentTree() {
    	return tournamentTree;
    }
    public int getAnimationPathTreeDuration() {
        return (int) animationTimeTreeSpinner.getValue();
    }
    public int getNbBlinkTreeDuration() {
        return (int) clignotementNumberTreeSpinner.getValue();
    }
    public boolean isPathTreeAnimationEnabled() {
        return animationTreeCheckBox.isSelected();
    }
    public Color getTreeColor() {
		return treeColor;
	}
	public Color getPathTreeColor() {
		return pathTreeColor;
	}

	public void setDisplayJFullCheckBox(boolean displayJFullCheckBox) {
		this.displayJFullCheckBox.setSelected(displayJFullCheckBox);
	}
	public void setZoomAnimationCheckBox(boolean zoomAnimationCheckBox) {
		this.zoomAnimationCheckBox.setSelected(zoomAnimationCheckBox);
	}
	public void setZoomAnimationSpinner(int zoomAnimationSpinner) {
		this.zoomAnimationSpinner.setValue(zoomAnimationSpinner);
	}
	public void setLabelAnimationCheckBox(boolean labelAnimationCheckBox) {
		this.labelAnimationCheckBox.setSelected(labelAnimationCheckBox);
	}
	public void setLabelAnimationSpinner(int labelAnimationSpinner) {
		this.labelAnimationSpinner.setValue(labelAnimationSpinner);
	}
	public void setDisplayTreeCheckBox(boolean displayTreeCheckBox) {
		this.displayTreeCheckBox.setSelected(displayTreeCheckBox);
	}
	public void setxLeftSpinner(int xLeftSpinner) {
		this.xLeftSpinner.setValue(xLeftSpinner);
	}
	public void setxRightSpinner(int xRightSpinner) {
		this.xRightSpinner.setValue(xRightSpinner);
	}
	public void setWidthTreeSpinner(int largeurTreeSpinner) {
		this.widthTreeSpinner.setValue(largeurTreeSpinner);
	}
	public void setThicknessTreeSpinner(int epaisseurTreeSpinner) {
		this.thicknessTreeSpinner.setValue(epaisseurTreeSpinner);
	}
	public void setAnimationTreeCheckBox(boolean animationTreeCheckBox) {
		this.animationTreeCheckBox.setSelected(animationTreeCheckBox);
	}
	public void setAnimationTimeTreeSpinner(int animationTimeTreeSpinner) {
		this.animationTimeTreeSpinner.setValue(animationTimeTreeSpinner); ;
	}
	public void setClignotementNumberTreeSpinner(int clignotementNumberTreeSpinner) {
		this.clignotementNumberTreeSpinner.setValue(clignotementNumberTreeSpinner);
	}
	public void setTreeColor(Color treeColor) {
		this.treeColor = treeColor;
		tournamentTree.setTreeColor(this.treeColor);
	}
	public void setPathTreeColor(Color pathTreeColor) {
		this.pathTreeColor = pathTreeColor;
		tournamentTree.setSelectedpathColor(this.pathTreeColor);
	}
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

	private void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration, JLayeredPane layeredPane, Integer layer, Runnable onComplete) {
	    currentAnimationTimer = createTimer(false, duration, (progress) -> {
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
    public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
        System.out.println("Animation ZOOM");
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();
        Point initialLocation = panel.getLocation();
        
        int duration = getZoomAnimationDuration();
        if (!isZoomAnimationEnabled()) {
            panel.setBounds(0, 0, targetWidth, targetHeight);
            panel.revalidate();
            panel.repaint();
            if(onComplete != null)
                onComplete.run();   
        } else {
            final long startTime = System.currentTimeMillis();
            final int widthDiff = targetWidth - initialWidth;
            final int heightDiff = targetHeight - initialHeight;

            Timer timer = new Timer(5, null); // Réduire le délai à 5ms pour plus de fluidité
            timer.addActionListener(e -> {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double progress = Math.min((double) elapsedTime / duration, 1.0);
                double easedProgress = easeInOutCubic(progress);

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

                    panel.revalidate();
                    panel.repaint();
                });

                if (progress >= 1.0) {
                    timer.stop();
                    if (onComplete != null) {
                        SwingUtilities.invokeLater(onComplete);
                    }
                }
            });

            timer.start();
        }
    }


//    private void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration, JLayeredPane layeredPane, Integer layer, Runnable onComplete) {
//    	currentAnimationTimer  = createTimer(false, duration, (progress) -> {
//            boolean targetReached = updateLabelProperties(label, targetLocation, targetSize, targetColor, targetFont, progress);
//            if (progress >= 1.0 || targetReached) {
//            	currentAnimationTimer.stop();
//                if (onComplete != null) {
//                    onComplete.run();
//                }
//                cleanupAnimation(layeredPane, layer);
//            }
//        });
//    	currentAnimationTimer.start();
//    }
    private Point interpolatePoint(Point start, Point end, double progress) {
        int newX = (int) (start.x + progress * (end.x - start.x));
        int newY = (int) (start.y + progress * (end.y - start.y));
        return new Point(newX, newY);
    }

    private Timer createTimer(boolean zoom,int duration, ProgressCallback callback) {
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();

        ActionListener listener = e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.min((double) elapsed / duration, 1.0);
            if(zoom)
            	progress = easeInOutCubic(progress); // Fonction d'interpolation plus douce
            callback.onProgress(progress);
            if (progress >= 1.0) {
                timer.stop();
            }
        };

        timer.addActionListener(listener);
        return timer;
    }
    private double easeInOutCubic(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }
    
    private interface ProgressCallback {
        void onProgress(double progress);
    }

    private void setupAnimatedLabel(JLabel animatedLabel, JLabel startLabel, JPanel panel, JLayeredPane layeredPane, Integer layer) {
        animatedLabel.setForeground(startLabel.getForeground());
        if(animatedLabel.getName() != ("image"))
        	animatedLabel.setSize(panel.getSize());
        animatedLabel.setLocation(panel.getLocation());
        layeredPane.add(animatedLabel, layer);
    }

    private void cleanupAnimation(JLayeredPane layeredPane, Integer layer) {
        Arrays.stream(layeredPane.getComponentsInLayer(layer)).forEach(layeredPane::remove);
        layeredPane.repaint();
        layeredPane.revalidate();
    }

    private Color interpolateColor(Color start, Color end, double progress) {
        int newRed = interpolateColorComponent(start.getRed(), end.getRed(), progress);
        int newGreen = interpolateColorComponent(start.getGreen(), end.getGreen(), progress);
        int newBlue = interpolateColorComponent(start.getBlue(), end.getBlue(), progress);
        return new Color(newRed, newGreen, newBlue);
    }

    private int interpolateColorComponent(int start, int end, double progress) {
        return (int) (start + progress * (end - start));
    }
}