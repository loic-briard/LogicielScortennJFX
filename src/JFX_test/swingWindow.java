package JFX_test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.BDD_v2;
import Players.Joueur;

class SwingApp extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingApp() {
        setTitle("Fenêtre Swing");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton button1 = new JButton("Image 1");
        JButton button2 = new JButton("Image 2");
        JButton button3 = new JButton("Image 3");
        JButton buttonJoueur = new JButton("Joueur");

//        button1.addActionListener(new ButtonClickListener("Background/backgroundexample1.png"));
//        button2.addActionListener(new ButtonClickListener("Background/backgroundexample2.png"));
//        button3.addActionListener(new ButtonClickListener("Background/backgroundexample4.png"));
        
        buttonJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Joueur joueur = new Joueur(1, "test", "test", "test", "test", "test", "test", "Background/backgroundexample1",
						2, 3, "test", 4, 5, "test", "test", "test");
				MainApp.displayJoueur(joueur);
			}
        });
        
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(buttonJoueur);

        add(panel);
        setVisible(true);
    }
	

//    private class ButtonClickListener implements ActionListener {
//        private String imagePath;
//
//        public ButtonClickListener(String imagePath) {
//            this.imagePath = imagePath;
//        }

//        @Override
//        public void actionPerformed(ActionEvent e) {
//            MainApp.changeImage(imagePath);
//        }
//    }
}
