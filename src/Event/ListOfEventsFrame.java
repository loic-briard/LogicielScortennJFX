/*
 * 
 */
package Event;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.*;

import Main.BDD_v2;
import Main.ImageUtility;
import Main.MenuPrincipal;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfEventsFrame.
 */
public class ListOfEventsFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The event table. */
	private JTable eventTable;
	
	/** The parent frame. */
	private MenuPrincipal parentFrame; // R�f�rence � la fen�tre ListOfEventsFrame

	/**
	 * Instantiates a new list of events frame.
	 *
	 * @param menuPrincipal the menu principal
	 * @throws SQLException the SQL exception
	 */
	public ListOfEventsFrame(MenuPrincipal menuPrincipal) throws SQLException {
		this.parentFrame = menuPrincipal;
		// Initialisation de la fen�tre
        setTitle("List of Events");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);  
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }

        // Cr�ez un mod�le de table
        String[] columnNames = {"Event name", "Background name", "Background pictures 1", "Background pictures 2", "Background pictures 3", "Background pictures 4", "waiting Background"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			@Override
            public Class<?> getColumnClass(int column) {
                if (column == 2 || column == 3 || column == 4 || column == 5 || column == 6) {
                    return ImageUtility.class; // La colonne des images utilise la classe ImageUtility
                } else {
                    return Object.class; // Les autres colonnes sont de type Object
                }
            }
        };
        // Cr�ez un JTable avec le mod�le de table
        eventTable = new JTable(tableModel);
        // D�finir la hauteur des lignes � 80 pixels
        eventTable.setRowHeight(85);
        // Cr�ation d'un rendu personnalis� pour afficher les images
        DefaultTableCellRenderer imageRenderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageUtility) {
                    ImageUtility imageUtility = (ImageUtility) value;
                    return imageUtility;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        // Appliquez le rendu personnalis� � la colonne des images
        eventTable.getColumnModel().getColumn(2).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(3).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(4).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(5).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(6).setCellRenderer(imageRenderer);
        // R�cup�rez les donn�es de la base de donn�es
        String[][] eventData = BDD_v2.DataEvent();
       
		
		for (String[] data : eventData) {
			if (data[2] != null) {
				// Ajouter les donn�es � la table, y compris l'image
				ImageUtility imageRezize[] = new ImageUtility[5];
				for (int i = 2; i <= 6; i++)
					imageRezize[i - 2] = new ImageUtility(data[i], 80);
				// Ajoutez les donn�es � la table
				System.out.println("++++ evenement : "+data[0]+", background : "+data[1]);
				tableModel.addRow(new Object[] { data[0], data[1], imageRezize[0], imageRezize[1], imageRezize[2], imageRezize[3], imageRezize[4] });
			} else {
				System.out.println("---- probleme background");
				tableModel.addRow(new Object[] { data[0]});
			}
		}
		
		// Cr�ez un JPanel pour les boutons
		JPanel buttonPanel = new JPanel();
		JButton newButton = new JButton("New");
		JButton modifyButton = new JButton("Modify");
		JButton deleteButton = new JButton("Delete");

		// Ajoutez les boutons au panneau
		buttonPanel.add(newButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(deleteButton);

		// Ajoutez le panneau de boutons en haut de la fen�tre
		add(buttonPanel, BorderLayout.NORTH);
		

        // Ajout du JTable � la fen�tre
        JScrollPane scrollPane = new JScrollPane(eventTable);
        add(scrollPane);

        // Rendez la fen�tre visible
        setVisible(true);
        
        modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = eventTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String eventName = (String) eventTable.getValueAt(selectedRow, 0);
					String backgroundName = (String) eventTable.getValueAt(selectedRow, 1);
					// Ouvrir une fen�tre de modification avec ces donn�es
					try {
						new ModifyEventFrame(ListOfEventsFrame.this, eventName, backgroundName);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = eventTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String eventName = (String) eventTable.getValueAt(selectedRow, 0);
					try {
						BDD_v2.deleteEvent(eventName);
						refreshData();
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//a modifier pour event
				}
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new newEventFrame(ListOfEventsFrame.this);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Code � ex�cuter lorsque la fen�tre est ferm�e
                // Par exemple, appeler la m�thode pour rafra�chir la fen�tre principale
            	parentFrame.refreshEventComboBox();
            }
        });
	}
	
	/**
	 * Refresh data.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void refreshData() throws SQLException {
		// Effacez les donn�es actuelles de la table
        DefaultTableModel model = (DefaultTableModel) eventTable.getModel();
        model.setRowCount(0);

        // Rechargez les donn�es depuis la base de donn�es et mettez � jour la table
        String[][] eventData = BDD_v2.DataEvent();

        for (String[] data : eventData) {
            if (data[2] != null) {
                // Ajouter les donn�es � la table, y compris l'image
				ImageUtility imageRezize[] = new ImageUtility[5];
                for (int i = 2; i <= 6; i++)
                    imageRezize[i - 2] = new ImageUtility(data[i], 80);
                // Ajoutez les donn�es � la table
                System.out.println("++++ evenement : "+data[0]+", background : "+data[1]);

                model.addRow(new Object[]{data[0], data[1], imageRezize[0], imageRezize[1], imageRezize[2], imageRezize[3], imageRezize[4]});
            } else {
                System.out.println("---- probleme background");
                model.addRow(new Object[]{data[0]});
            }
        }
    }
}