package Flags;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Main.BDD_v2;
import Main.ImageUtility;

public class ListOfFlag extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable flagTable;
    private JTextField searchField;
    private JButton modifyButton;
	private JButton searchButton;
	private SwingWorker<Void, Object[]> worker;

    public ListOfFlag() throws SQLException {
        // Initialisation de la fen�tre
        setTitle("List of Flags");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }

        // Cr�ez un mod�le de table
        String[] columnNames = { "Acronym", "Flag" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 1) {
                    return ImageUtility.class; // La colonne des images utilise la classe ImageUtility
                } else {
                    return Object.class; // Les autres colonnes sont de type Object
                }
            }
        };
        // Cr�ez un JTable avec le mod�le de table
        flagTable = new JTable(tableModel);
        flagTable.setRowHeight(60);
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
        flagTable.getColumnModel().getColumn(1).setCellRenderer(imageRenderer);

        // Champ de recherche
        searchField = new JTextField(20);
        // Bouton "Modifier"
        modifyButton = new JButton("Modifier");
     // Bouton "Search"
        searchButton = new JButton("Search");
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(modifyButton);
        add(panel,BorderLayout.NORTH);

        // Ajout du gestionnaire d'�v�nements pour le bouton "Search"
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim().toUpperCase();
            if (!searchText.isEmpty()) {
                for (int row = 0; row < flagTable.getRowCount(); row++) {
                    String cellValue = flagTable.getValueAt(row, 0).toString(); // Assurez-vous d'adapter le num�ro de colonne
                    if (cellValue.equals(searchText)) {
                        flagTable.scrollRectToVisible(flagTable.getCellRect(row, 0, true));
                        break; // Arr�tez la recherche apr�s avoir trouv� la premi�re correspondance
                    }
                }
            }
        });
        
        modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = flagTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String flagName = (String) flagTable.getValueAt(selectedRow, 0);
					ImageUtility imageUtility = (ImageUtility) flagTable.getValueAt(selectedRow, 1);
					String imgPath = imageUtility.getImagePath();
					// Ouvrir une fen�tre de modification avec ces donn�es
					new ModifyFlagFrame(ListOfFlag.this, flagName, imgPath);
				}
			}
		});

        // Ajout du JTable � la fen�tre
        JScrollPane scrollPane = new JScrollPane(flagTable);
        add(scrollPane);

        // Rendez la fen�tre visible
        setVisible(true);

        // Chargement des donn�es en arri�re-plan
        loadFlagsInBackground();
        
		// Appel de l'annulation lorsque la fen�tre est ferm�e
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (worker != null && !worker.isDone()) {
					worker.cancel(true);
				}
			}
		});
    }

    private void loadFlagsInBackground() {
    	worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                // R�cup�rez les donn�es de la base de donn�es
                String[][] flagData = BDD_v2.DataFlag();

                // Ajoutez les donn�es � la table apr�s le chargement
                for (String[] data : flagData) {
                	if (isCancelled()) {
                        // Si l'annulation est demand�e, sortez de la boucle
                        System.out.println("! Chargement annule.");
                        return null;
                    }
                    Object[] rowData = new Object[]{data[0], new ImageUtility(data[1], 55)};
                    publish(rowData); // Publiez les donn�es pour les afficher dans l'EDT
                }

                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                // Ajoutez les donn�es publi�es � la table
                for (Object[] rowData : chunks) {
                    DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
                    model.addRow(rowData);
                    
                }
            }
        };
        // D�marrer le SwingWorker
        worker.execute();
    }
    
    public void refreshModifiedRow(String oldName, String newName, String newImage) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String name = (String) model.getValueAt(row, 0);
            if (name.equals(oldName)) {
                model.setValueAt(newName, row, 0);
                model.setValueAt(new ImageUtility(newImage, 55), row, 1);
                break; // Sortez de la boucle une fois que la ligne modifi�e a �t� trouv�e
            }
        }
    }
}