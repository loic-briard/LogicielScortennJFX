/*
 * 
 */
package Event;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.BDD_v2;

// TODO: Auto-generated Javadoc
/**
 * The Class newEventFrame.
 */
public class newEventFrame extends JFrame{

/** The Constant serialVersionUID. */
private static final long serialVersionUID = 1L;

	/** The name field. */
	private JTextField nameField;

/** The new name. */
//	private String currentName;
	private String newName;
	
	/** The background new name. */
	private String backgroundNewName;
	
	/**
	 * Instantiates a new new event frame.
	 *
	 * @param parentFrame the parent frame
	 * @throws SQLException the SQL exception
	 */
	public newEventFrame(ListOfEventsFrame parentFrame) throws SQLException{
		setTitle("New Event ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        // Ajoutez le champ de texte pour le nom
        nameField = new JTextField("name");
        
        String[] backgroundNames = BDD_v2.getNamesFromDatabase("background");
        // Cr�ez un JComboBox pour le menu d�roulant
        JComboBox<String> backgroundComboBox = new JComboBox<>(backgroundNames);
        // Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
        backgroundComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // R�cup�rez l'�l�ment s�lectionn�
            	backgroundNewName = (String) backgroundComboBox.getSelectedItem();
                // Faites ce que vous voulez avec l'�v�nement s�lectionn�
                System.out.println("Background selected: " + backgroundNewName);
            }
        });
        backgroundComboBox.setSelectedIndex(-1);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameField, BorderLayout.NORTH);
        panel.add(backgroundComboBox, BorderLayout.CENTER);
        add(panel);
        
        // Cr�ez un bouton "Valider"
        JButton validateButton = new JButton("Validate");
        add(validateButton, BorderLayout.SOUTH);
        // Ajoutez un gestionnaire d'action au bouton "Valider"
        validateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newName = nameField.getText();
                Evenement NewEvent = new Evenement(newName);
                try {
					NewEvent.setBackground(BDD_v2.getOneBackground(backgroundNewName));
					BDD_v2.insertionEventDansBDD(NewEvent);
					dispose();
					parentFrame.refreshData();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
            }
        });
        setVisible(true);
	}
}
