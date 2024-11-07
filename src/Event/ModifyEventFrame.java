/*
 * 
 */
package Event;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.BDD_v2;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyEventFrame.
 */
public class ModifyEventFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name field. */
	private JTextField nameField;
	
	/** The current name. */
	private String currentName;
	
	/** The new name. */
	private String newName;
	
	/** The background current name. */
	private String backgroundCurrentName;
	
	/** The background new name. */
	private String backgroundNewName;
	
	/**
	 * Instantiates a new modify event frame.
	 *
	 * @param parentFrame the parent frame
	 * @param currentName the current name
	 * @param backgroundCurrentName the background current name
	 * @throws SQLException the SQL exception
	 */
	public ModifyEventFrame(ListOfEventsFrame parentFrame, String currentName, String backgroundCurrentName) throws SQLException{
		this.currentName = currentName;
		this.backgroundCurrentName = backgroundCurrentName;
		
		setTitle("Modify Event : "+currentName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 100);
        // Ajoutez le champ de texte pour le nom
        nameField = new JTextField(this.currentName);
        nameField.setPreferredSize(new Dimension(200, 30));
        
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
        backgroundComboBox.setSelectedItem(this.backgroundCurrentName);//.setSelectedIndex(-1);
        
        // Cr�ez un bouton "Valider"
        JButton validateButton = new JButton("Validate");
        //add(validateButton, BorderLayout.SOUTH);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(nameField);
        panel.add(backgroundComboBox);
        panel.add(validateButton);
        add(panel,BorderLayout.CENTER);
        
        // Ajoutez un gestionnaire d'action au bouton "Valider"
        validateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newName = nameField.getText();
                BDD_v2.updateEventInDatabase(currentName, newName,backgroundNewName);
                dispose();
                try {
					parentFrame.refreshData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        setVisible(true);
	}
}
