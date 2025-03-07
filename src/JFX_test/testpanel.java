package JFX_test;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Main.ImageUtility;

public class testpanel extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public testpanel() {
		setTitle("Centered JPanel Example");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Création du JPanel
        JPanel panel = new JPanel(new GridBagLayout()); // Centrage parfait
        panel.setSize(200, 200);
        panel.setBackground(Color.BLUE);

        // Ajouter l’image dans le JPanel
        ImageUtility imageLabel = new ImageUtility("ressources/Background/1bgFull32.png", 200);///Tournoi_Tennis_JavaFX_v2/resources/Background/1bgFull32.png
        panel.add(imageLabel);
        panel.setSize(imageLabel.getWidthImgUtility(), 200);
        // Centrer le panel dans la fenêtre
        int x = (500 - 200) / 2;
        int y = (500 - 200) / 2;
        panel.setLocation(x, y);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(testpanel::new);
    }
}
