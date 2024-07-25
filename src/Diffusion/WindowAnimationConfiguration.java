package Diffusion;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Arrays;

public class WindowAnimationConfiguration extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JCheckBox zoomAnimationCheckBox;
    private final JSpinner zoomAnimationSpinner;
    private final JCheckBox labelAnimationCheckBox;
    private final JSpinner labelAnimationSpinner;
    private final JCheckBox displayTreeCheckBox;
    private final JSpinner xLeftSpinner;
    private final JSpinner xRightSpinner;
    private final JSpinner largeurTreeSpinner;
    private final JSpinner epaisseurTreeSpinner;
    private Color treeColor;
    private final JButton treeColorButton;
    private PanelTournamentTree tournamentTree;
    private WindowTournamentTree windowTournamentTree;

    public WindowAnimationConfiguration(WindowTournamentTree windowTournamentTreee) throws ClassNotFoundException, SQLException {
        setTitle("Animation Configuration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon("icon.png").getImage());
        setLocationRelativeTo(null);
        
        this.windowTournamentTree = windowTournamentTreee;
        
        zoomAnimationCheckBox = new JCheckBox("Zoom Animation");
        zoomAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 100));
        labelAnimationCheckBox = new JCheckBox("Label Animation");
        labelAnimationSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 10000, 100));
        
        displayTreeCheckBox = new JCheckBox("Display tournament tree");
        xLeftSpinner = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 1));
        xRightSpinner = new JSpinner(new SpinnerNumberModel(1285, 0, 10000, 1));
        largeurTreeSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 10000, 1));
        epaisseurTreeSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 10000, 1));
        treeColorButton = new JButton("Select a color");
        treeColor = Color.BLACK;
        treeColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor = JColorChooser.showDialog(null, "Choose a color", treeColor);
                if (selectedColor != null) {
                    // Faites quelque chose avec la couleur s�lectionn�e
                    System.out.println("Couleur selectionnee : " + selectedColor);
                    if (selectedColor != null) {
                    	treeColor = selectedColor;
                    	tournamentTree.setTreeColor(treeColor);
                    }
                }
            }
        });
        tournamentTree = new PanelTournamentTree(this,windowTournamentTree.initListPlayerForDiffusion(), getWidthTree(), getThicknessTree());
        tournamentTree.setBounds(0, 0, windowTournamentTree.getWindowBroadcastPublic().getWidth(), windowTournamentTree.getWindowBroadcastPublic().getHeight());
        windowTournamentTree.getWindowBroadcastPublic().addContent(250,tournamentTree);
        tournamentTree.setTreeColor(treeColor);
        tournamentTree.setVisible(isTournamentTreeEnabled());
        displayTreeCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Checkbox cochée");
                    tournamentTree.setVisible(true);
                } else {
                    System.out.println("Checkbox décochée");
                    tournamentTree.setVisible(false);
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
        largeurTreeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tournamentTree.setHorizontalSpacing(getWidthTree());				
			}
		});
        epaisseurTreeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	tournamentTree.setLineWidth(getThicknessTree());
            }
        });
        
            
        setupComponents();
        setVisible(true);
        this.pack();
    }

    private void setupComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        addRow(gbc, 0, zoomAnimationCheckBox, zoomAnimationSpinner, new JLabel("ms"));
        addRow(gbc, 1, labelAnimationCheckBox, labelAnimationSpinner,new JLabel("ms"));
        addRow(gbc, 2, new JLabel(), new JLabel("Begining of tree Left"),new JLabel("Begining of tree Right"),new JLabel("Tree Width"),new JLabel("Tree thickness"));
        addRow(gbc, 3, displayTreeCheckBox, xLeftSpinner,xRightSpinner,largeurTreeSpinner,epaisseurTreeSpinner,treeColorButton);
    }

    private void addRow(GridBagConstraints gbc, int row, JComponent... components) {
        gbc.gridy = row;
        Arrays.stream(components).forEach(component -> {
            gbc.gridx = Arrays.asList(components).indexOf(component);
            add(component, gbc);
        });
        gbc.gridx = components.length;
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
    	return (int)largeurTreeSpinner.getValue();
    }
    public int getThicknessTree() {
    	return (int)epaisseurTreeSpinner.getValue();
    }
    public PanelTournamentTree getPanelTournamentTree() {
    	return tournamentTree;
    }

    public void animateLabel(JPanel panel, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, Integer layer, JLayeredPane layeredPane, Runnable onComplete) {
        JLabel startLabel = (JLabel) panel.getComponents()[0];
        JLabel animatedLabel = new JLabel();
        if (startLabel.getIcon() != null && startLabel.getText() == null) {
        	animatedLabel = new JLabel(startLabel.getIcon());
        }else
        	animatedLabel = new JLabel(startLabel.getText());
        
        setupAnimatedLabel(animatedLabel, startLabel, panel, layeredPane, layer);
        animateLabel(animatedLabel, targetLocation, targetSize, targetColor, targetFont, getLabelAnimationDuration(), layeredPane, layer, onComplete);
    }
    public void animateImage(JPanel imagePanel, JLabel imageLabel, Point targetLocation, Dimension targetSize, Integer layer, JLayeredPane layeredPane, Runnable onComplete) {
        JLabel startLabel = imageLabel;
        JLabel animatedLabel = imageLabel;
        Font targetFont = new Font("Arial", 1, 25);
        Color targetColor = Color.BLACK;
        setupAnimatedLabel(animatedLabel, startLabel, imagePanel, layeredPane, layer);
        animateLabel(animatedLabel, targetLocation, targetSize, targetColor, targetFont, getLabelAnimationDuration(), layeredPane, layer, onComplete);
    }

    public void zoomPanel(JPanel panel, WindowBroadcastPublic frame, Runnable onComplete) {
        System.out.println("Animation ZOOM");
        int initialWidth = panel.getWidth();
        int initialHeight = panel.getHeight();
        int targetWidth = frame.getWidth();
        int targetHeight = frame.getHeight();
        Point initialLocation = panel.getLocation();
        
        int duration = getZoomAnimationDuration();

        Timer timer = createTimer(duration, (progress) -> {
            int newWidth = (int) (initialWidth + progress * (targetWidth - initialWidth));
            int newHeight = (int) (initialHeight + progress * (targetHeight - initialHeight));
            
            // Calcul de la nouvelle position pour garder le panel centré
            int newX = initialLocation.x + (initialWidth - newWidth) / 2;
            int newY = initialLocation.y + (initialHeight - newHeight) / 2;

            panel.setBounds(newX, newY, newWidth, newHeight);
            
            if (panel instanceof ZoomablePanel) {
                ((ZoomablePanel) panel).setScale(progress);
            }
            
            panel.revalidate();
            panel.repaint();

            if (progress >= 1.0 && onComplete != null) {
                onComplete.run();
            }
        });
        
        timer.start();
    }


    private void animateLabel(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, int duration, JLayeredPane layeredPane, Integer layer, Runnable onComplete) {
        Timer timer = createTimer(duration, (progress) -> {
            updateLabelProperties(label, targetLocation, targetSize, targetColor, targetFont, progress);
            if (progress >= 1.0 || (label.getLocation().equals(targetLocation.getLocation()))) {
                if (onComplete != null) {
                    onComplete.run();
                }
                cleanupAnimation(layeredPane, layer);
            }
        });
        timer.start();
    }
    private Point interpolatePoint(Point start, Point end, double progress) {
        int newX = (int) (start.x + progress * (end.x - start.x));
        int newY = (int) (start.y + progress * (end.y - start.y));
        return new Point(newX, newY);
    }

    private Dimension interpolateDimension(Dimension start, Dimension end, double progress) {
        int newWidth = (int) (start.width + progress * (end.width - start.width));
        int newHeight = (int) (start.height + progress * (end.height - start.height));
        return new Dimension(newWidth, newHeight);
    }

    private Timer createTimer(int duration, ProgressCallback callback) {
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();

        ActionListener listener = e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.min((double) elapsed / duration, 1.0);
            callback.onProgress(progress);
            if (progress >= 1.0) {
                timer.stop();
            }
        };

        timer.addActionListener(listener);
        return timer;
    }

    @FunctionalInterface
    private interface ProgressCallback {
        void onProgress(double progress);
    }

    private void updateLabelProperties(JLabel label, Point targetLocation, Dimension targetSize, Color targetColor, Font targetFont, double progress) {
        Point startLocation = label.getLocation();
        Color startColor = label.getForeground();
        Font startFont = label.getFont();

        label.setLocation(interpolatePoint(startLocation, targetLocation, progress));
        label.setForeground(interpolateColor(startColor, targetColor, progress));
        label.setFont(interpolateFont(startFont, targetFont, progress));
        if (label.getText() == null || label.getText() == "") {
        	if (label.getIcon() != null) {
        		// Interpoler la taille
              Dimension newSize = interpolateDimension(label.getPreferredSize(), targetSize, progress);
              label.setSize(newSize);
              Image img = ((ImageIcon) label.getIcon()).getImage();
              Image newImg = img.getScaledInstance(newSize.width, newSize.height, Image.SCALE_SMOOTH);
              label.setIcon(new ImageIcon(newImg));
        	}
        }else
        	label.setSize(label.getPreferredSize());

        label.revalidate();
        label.repaint();
    }

    private void setupAnimatedLabel(JLabel animatedLabel, JLabel startLabel, JPanel panel, JLayeredPane layeredPane, Integer layer) {
        animatedLabel.setFont(startLabel.getFont());
        animatedLabel.setForeground(startLabel.getForeground());
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

    private Font interpolateFont(Font start, Font end, double progress) {
        float newSize = (float) (start.getSize() + progress * (end.getSize() - start.getSize()));
        return start.deriveFont(newSize);
    }
    
}