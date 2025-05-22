/*
 * 
 */
package Diffusion;

/*
 * fenetre de diffusion du tournoi pour les spectateurs
 */

import javax.swing.*;

import Animation.PanelAnimationConfiguration;
import Event.Evenement;

import java.awt.*;
import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowBroadcastPublic.
 */
public class WindowBroadcastPublic extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The actual event. */
	private final Evenement actualEvent;

	/** The layered pane. */
	private final JLayeredPane layeredPane;

	/** The background label. */
	private final JLabel backgroundLabel;

	/** The window tournament tree. */
	private WindowTournamentTree windowTournamentTree;

	/** The animation frame. */
	private PanelAnimationConfiguration animationFrame;
	private GraphicsDevice screen;

	/**
	 * Instantiates a new window broadcast public.
	 *
	 * @param eventChoosen    the event choosen
	 * @param screen          the screen
	 * @param windowDimension the window dimension
	 */
	public WindowBroadcastPublic(Evenement eventChoosen, GraphicsDevice screen, Dimension windowDimension) {
		this.actualEvent = eventChoosen;
		this.screen = screen;
		setupFrame(screen, windowDimension);

		layeredPane = new JLayeredPane();
		setupLayeredPane();

		backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		setBackgroundImage(eventChoosen.getBackground().getImage_1()); // Mettre le chemin de l'image initiale
		layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

		this.add(layeredPane);
		this.setVisible(true);
	}

	/**
	 * Setup frame.
	 *
	 * @param screen          the screen
	 * @param windowDimension the window dimension
	 */
	private void setupFrame(GraphicsDevice screen, Dimension windowDimension) {
		if (windowDimension.getHeight() == 0.0 || windowDimension.getWidth() == 0.0)
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else
			this.setSize(windowDimension);
		setUndecorated(true);
		// placement dans l'ecran choisit
		Rectangle bounds = screen.getDefaultConfiguration().getBounds();
		// D�finissez la position initiale de la fen�tre sur le deuxi�me �cran
		setLocation(bounds.x, bounds.y);

		ImageIcon logoIcon = new ImageIcon("resources" + File.separator + "imgInterface" + File.separator + "icon.png");
		// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
		if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(logoIcon.getImage());
		} else {
			// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
			System.err.println("Impossible de charger l'ic�ne.");
		}
		setTitle("Diffusion : " + actualEvent.getNom());
		this.setVisible(true);
	}

	/**
	 * Setup layered pane.
	 */
	private void setupLayeredPane() {
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	/**
	 * Sets the background image.
	 *
	 * @param imagePath the new background image
	 */
	public void setBackgroundImage(String imagePath) {
		ImageIcon imageBackground = new ImageIcon(imagePath);
		Image scaledImageBackground = imageBackground.getImage().getScaledInstance(this.getWidth(), this.getHeight(),
				Image.SCALE_SMOOTH);
		System.out.println("> switch background to : " + imagePath);
		backgroundLabel.setIcon(new ImageIcon(scaledImageBackground));
		layeredPane.revalidate();
		layeredPane.repaint();
	}

	/**
	 * Sets the background image layered.
	 *
	 * @param imagePath the image path
	 * @param layer     the layer
	 */
	public void setBackgroundImageLayered(String imagePath, Integer layer) {
		removeLayerContent(layer);
		ImageIcon imageBackground = new ImageIcon(imagePath);
		Image scaledImageBackground = imageBackground.getImage().getScaledInstance(this.getWidth(), this.getHeight(),
				Image.SCALE_SMOOTH);
		System.out.println("> switch background to : " + imagePath + " on layer : " + layer);
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		imageLabel.setIcon(new ImageIcon(scaledImageBackground));
		layeredPane.add(imageLabel, layer);
	}

	/**
	 * Adds the content.
	 *
	 * @param layer        the layer
	 * @param panelContent the panel content
	 */
	public void addContent(Integer layer, JPanel panelContent) {
		layeredPane.add(panelContent, layer);
	}

	/**
	 * Adds the content label.
	 *
	 * @param layer   the layer
	 * @param Content the content
	 */
//    public void addContentLabel(Integer layer, JLabel Content) {
//    	layeredPane.add(Content, layer);
//    }

	/**
	 * Removes the layer content.
	 *
	 * @param layer the layer
	 */
	public void removeLayerContent(int layer) {
		for (Component component : layeredPane.getComponentsInLayer(layer)) {
			layeredPane.remove(component);
		}
		layeredPane.revalidate();
		layeredPane.repaint();
	}

	/**
	 * Close.
	 */
	public void close() {
		this.dispose();
	}

	/**
	 * Gets the layered pane window broadcast public.
	 *
	 * @return the layered pane window broadcast public
	 */
	// Getters
	public JLayeredPane getLayeredPaneWindowBroadcastPublic() {
		return layeredPane;
	}

	/**
	 * Gets the animation frame.
	 *
	 * @return the animation frame
	 */
	public PanelAnimationConfiguration getAnimationFrame() {
		return animationFrame;
	}

	public GraphicsDevice getScreen() {
		return screen;
	}

	/**
	 * Gets the window tournament tree from broadcast.
	 *
	 * @return the window tournament tree from broadcast
	 */
	public WindowTournamentTree getWindowTournamentTreeFromBroadcast() {
		return windowTournamentTree;
	}

	/**
	 * Gets the name event.
	 *
	 * @return the name event
	 */
	public String getNameEvent() {
		return this.actualEvent.getNom();
	}

	/**
	 * Sets the window tournament tree from broadcast.
	 *
	 * @param windowTournamentTree the new window tournament tree from broadcast
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Setter
	public void setWindowTournamentTreeFromBroadcast(WindowTournamentTree windowTournamentTree)
			throws ClassNotFoundException {
		this.windowTournamentTree = windowTournamentTree;
		animationFrame = windowTournamentTree.getPanelAnimationConfiguration();
	}
}
