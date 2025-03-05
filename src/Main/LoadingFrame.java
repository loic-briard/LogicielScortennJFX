package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * 📌 Classe pour afficher un écran de chargement avec un GIF.
 */
class LoadingDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel loadingLabel;

	public LoadingDialog(GraphicsDevice configScreen) {
		setTitle("Chargement...");
		setUndecorated(true);
		setSize(200, 200); // Ajuste la taille ici
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
        setBackground(new Color(0, 0, 0, 0)); // Transparent
     // Obtenir l'emplacement de l'écran secondaire
        Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre

		// Charger et redimensionner le GIF
		ImageIcon originalIcon = new ImageIcon("loading.gif"); // Charge l'image
		Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		loadingLabel = new JLabel(resizedIcon);
		loadingLabel.setOpaque(false); // Important pour la transparence
		
		add(loadingLabel, BorderLayout.CENTER);
			
		// Permettre la transparence complète sur certaines versions Java
        setOpacity(0.9f); // Ajustable (0.0 = invisible, 1.0 = opaque)
	}

    public void showLoading() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(() -> dispose());
    }
}