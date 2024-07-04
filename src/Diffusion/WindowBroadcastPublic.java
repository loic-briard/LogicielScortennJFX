package Diffusion;

/*
 * fenetre de diffusion du tournoi pour les spectateurs
 */

import javax.swing.*;

import Event.Evenement;
import java.awt.*;

public class WindowBroadcastPublic extends JFrame {
	private static final long serialVersionUID = 1L;
	private Evenement actualEvent;
	private JLayeredPane layeredPane;
    private JLabel backgroundLabel;
    private WindowAnimationConfiguration animationFrame;

	public JLayeredPane getLayeredPaneWindowBroadcastPublic() {
		return layeredPane;
	}
	public WindowAnimationConfiguration getAnimationFrame() {
		return animationFrame;
	}
	public WindowBroadcastPublic(Evenement eventChoosen,GraphicsDevice screen ) {
		//new JFrame("Broadcast");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		this.actualEvent = eventChoosen;
		setTitle("Diffusion : "+actualEvent.getNom());	
		this.setVisible(true);
		
		// Créer un JLayeredPane pour superposer les composants
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		// Ajouter une image de fond avec un JLabel
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
        setBackgroundImage("black.jpg"); // Mettre le chemin de l'image initiale
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        
        animationFrame = new WindowAnimationConfiguration();
        
        this.add(layeredPane);
	}
	public String getNameEvent() {
		return this.actualEvent.getNom();
	}
	// Méthode pour mettre à jour l'image de fond
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
	    Component[] components = layeredPane.getComponentsInLayer(layer);
	    for (Component component : components) {
	        layeredPane.remove(component);
	    }
	    layeredPane.repaint();
	}
//	public JPanel setBackgroundImage(String img, String temp) throws IOException {
//		JPanel panel = new JPanel() {
//			private static final long serialVersionUID = 1;
//			private BufferedImage buf = ImageIO.read(new File(img));
//
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.drawImage(buf, 0, 0, this.getWidth(), this.getHeight(), null);
//			}
//		};
//		panel.setLayout(null);
//		this.setContentPane(panel);
//		this.revalidate();
//		this.repaint();
//
//		return panel;
//	}
	


	public void close() {
		//this.placementFrame.dispose();
		this.dispose();
//		new Configuration().saveConfiguration(playerName.getX(), playerName.getY(), placementFrame.checkboxName.isSelected(), playerName.getFont().toString());
	}
}
