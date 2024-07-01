package JFX_test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

        button1.addActionListener(new ButtonClickListener("resources/backgroundexample1.png"));
        button2.addActionListener(new ButtonClickListener("resources/backgroundexample2.png"));
        button3.addActionListener(new ButtonClickListener("resources/backgroundexample3.png"));

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        add(panel);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private String imagePath;

        public ButtonClickListener(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainApp.changeImage(imagePath);
        }
    }
}
