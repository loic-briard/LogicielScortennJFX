package Diffusion;

/*
 * fenetre de diffusion du tournoi pour les spectateurs
 */

import javax.swing.*;
import Event.Evenement;
import java.awt.*;
import java.sql.SQLException;

public class WindowBroadcastPublic extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Evenement actualEvent;
    private final JLayeredPane layeredPane;
    private final JLabel backgroundLabel;
    private WindowTournamentTree windowTournamentTree;
    private WindowAnimationConfiguration animationFrame;

    public WindowBroadcastPublic(Evenement eventChoosen, GraphicsDevice screen, Dimension windowDimension) {
    	this.actualEvent = eventChoosen;        
    	setupFrame(screen, windowDimension);
        
        layeredPane = new JLayeredPane();
        setupLayeredPane();
        
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
        setBackgroundImage("black.jpg"); // Mettre le chemin de l'image initiale
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        
        this.add(layeredPane);
        this.setVisible(true);
    }

    private void setupFrame(GraphicsDevice screen, Dimension windowDimension) {
    	if(windowDimension.getHeight() == 0.0 || windowDimension.getWidth() == 0.0)
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else
			this.setSize(windowDimension);
		setUndecorated(true);
		//placement dans l'ecran choisit
		Rectangle bounds = screen.getDefaultConfiguration().getBounds();
		// D�finissez la position initiale de la fen�tre sur le deuxi�me �cran
		setLocation(bounds.x, bounds.y);

		ImageIcon logoIcon = new ImageIcon("icon.png");
		// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
		if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(logoIcon.getImage());
		} else {
			// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
			System.err.println("Impossible de charger l'ic�ne.");
		}
		setTitle("Diffusion : "+actualEvent.getNom());	
		this.setVisible(true);
    }

    private void setupLayeredPane() {
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    }

    public void setBackgroundImage(String imagePath) {
        ImageIcon imageBackground = new ImageIcon(imagePath);
		Image scaledImageBackground = imageBackground.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		System.out.println("> switch background to : "+imagePath);
		backgroundLabel.setIcon(new ImageIcon(scaledImageBackground));
    }

    public void setBackgroundImageLayered(String imagePath, Integer layer) {
        removeLayerContent(layer);
		ImageIcon imageBackground = new ImageIcon(imagePath);
		Image scaledImageBackground = imageBackground.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		System.out.println("> switch background to : "+imagePath+" on layer : "+layer);
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		imageLabel.setIcon(new ImageIcon(scaledImageBackground));
		layeredPane.add(imageLabel, layer);
    }

    public void addContent(Integer layer, JPanel panelContent) {
        layeredPane.add(panelContent, layer);
    }

    public void removeLayerContent(int layer) {
        for (Component component : layeredPane.getComponentsInLayer(layer)) {
            layeredPane.remove(component);
        }
        layeredPane.repaint();
    }

    public void close() {
        this.dispose();
    }

    // Getters
    public JLayeredPane getLayeredPaneWindowBroadcastPublic() { return layeredPane; }
    public WindowAnimationConfiguration getAnimationFrame() { return animationFrame; }
    public WindowTournamentTree getWindowTournamentTreeFromBroadcast() { return windowTournamentTree; }
    public String getNameEvent() { return this.actualEvent.getNom(); }

    // Setter
	public void setWindowTournamentTreeFromBroadcast(WindowTournamentTree windowTournamentTree) {
		this.windowTournamentTree = windowTournamentTree;
		try {
			animationFrame = new WindowAnimationConfiguration(windowTournamentTree);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
