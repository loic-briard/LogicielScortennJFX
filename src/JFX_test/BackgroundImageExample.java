package JFX_test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackgroundImageExample extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
    private JLabel backgroundLabel;
    public BackgroundImageExample() {
        setTitle("Background Image Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un JLayeredPane pour superposer les composants
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Ajouter une image de fond avec un JLabel
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 800, 600);
        setBackgroundImage("icon.png"); // Mettre le chemin de l'image initiale
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

//        // Ajouter des composants supplémentaires (JLabel et JPanel)
//        contentLabel = new JLabel("Hello, World!");
//        contentLabel.setBounds(50, 50, 100, 30);
//        layeredPane.add(contentLabel, JLayeredPane.PALETTE_LAYER);
//
//        contentPanel = new JPanel();
//        contentPanel.setBounds(200, 200, 200, 100);
//        contentPanel.setBackground(new Color(255, 255, 255, 150)); // Panel semi-transparent
//        layeredPane.add(contentPanel, JLayeredPane.MODAL_LAYER);

        // Ajouter le layeredPane à la JFrame
        add(layeredPane);

//        // Bouton pour ouvrir la fenêtre de modification de l'image de fond
//        JButton changeBackgroundButton = new JButton("Change Background Image");
//        changeBackgroundButton.setBounds(600, 500, 180, 30);
//        changeBackgroundButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Ouvrir la fenêtre de modification de l'image de fond
//                new BackgroundChangerWindow(BackgroundImageExample.this).setVisible(true);
//            }
//        });
//        layeredPane.add(changeBackgroundButton, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    // Méthode pour mettre à jour l'image de fond
    public void setBackgroundImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        backgroundLabel.setIcon(icon);
    }

    // Fenêtre pour changer l'image de fond
    class BackgroundChangerWindow extends JFrame {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BackgroundChangerWindow(BackgroundImageExample mainFrame) {
            setTitle("Change Background Image");
            setSize(300, 150);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextField imagePathField = new JTextField();
            JButton changeButton = new JButton("Change");

            changeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String imagePath = imagePathField.getText();
                    mainFrame.setBackgroundImage(imagePath);
                }
            });

            add(imagePathField, BorderLayout.CENTER);
            add(changeButton, BorderLayout.SOUTH);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BackgroundImageExample());
    }
}
