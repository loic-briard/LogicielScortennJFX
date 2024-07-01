package Diffusion;

/*
 * fenetre de diffusion du tournoi pour les spectateurs
 */

import javax.imageio.ImageIO;
import javax.swing.*;

import Event.Evenement;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WindowBroadcastPublic extends JFrame {
	private static final long serialVersionUID = 1L;
	private Evenement actualEvent;

	public WindowBroadcastPublic(Evenement eventChoosen,GraphicsDevice screen ) {
		new JFrame("Broadcast");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		this.setLayout(null);
		
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
//		InfosJoueurP1 = new panelsInfosJoueurs();		

		this.setVisible(true);
	}
	public String getNameEvent() {
		return this.actualEvent.getNom();
	}
	public JPanel setBackgroundImage(String img) throws IOException {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1;
			private BufferedImage buf = ImageIO.read(new File(img));

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(buf, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		panel.setLayout(null);
		this.setContentPane(panel);
		this.revalidate();
		this.repaint();

		return panel;
	}

	public void close() {
		//this.placementFrame.dispose();
		this.dispose();
//		new Configuration().saveConfiguration(playerName.getX(), playerName.getY(), placementFrame.checkboxName.isSelected(), playerName.getFont().toString());
	}
}
