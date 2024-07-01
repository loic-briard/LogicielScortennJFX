package Flags;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public ListOfFlag() throws SQLException {
        // Initialisation de la fenêtre
        setTitle("List of Flags");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // Vérifiez si l'icône a été chargée avec succès
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'icône n'a pas pu être chargée, affichez un message d'erreur
            System.err.println("Impossible de charger l'icône.");
        }

        // Créez un modèle de table
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
        // Créez un JTable avec le modèle de table
        flagTable = new JTable(tableModel);
        flagTable.setRowHeight(60);
        // Création d'un rendu personnalisé pour afficher les images
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
        // Appliquez le rendu personnalisé à la colonne des images
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

        // Ajout du gestionnaire d'événements pour le bouton "Search"
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim().toUpperCase();
            if (!searchText.isEmpty()) {
                for (int row = 0; row < flagTable.getRowCount(); row++) {
                    String cellValue = flagTable.getValueAt(row, 0).toString(); // Assurez-vous d'adapter le numéro de colonne
                    if (cellValue.equals(searchText)) {
                        flagTable.scrollRectToVisible(flagTable.getCellRect(row, 0, true));
                        break; // Arrêtez la recherche après avoir trouvé la première correspondance
                    }
                }
            }
        });
        
        modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = flagTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les données de la ligne sélectionnée
					String flagName = (String) flagTable.getValueAt(selectedRow, 0);
					ImageUtility imageUtility = (ImageUtility) flagTable.getValueAt(selectedRow, 1);
					String imgPath = imageUtility.getImagePath();
					// Ouvrir une fenêtre de modification avec ces données
					new ModifyFlagFrame(ListOfFlag.this, flagName, imgPath);
				}
			}
		});

        // Ajout du JTable à la fenêtre
        JScrollPane scrollPane = new JScrollPane(flagTable);
        add(scrollPane);

        // Rendez la fenêtre visible
        setVisible(true);

        // Chargement des données en arrière-plan
        loadFlagsInBackground();
    }

    private void loadFlagsInBackground() {
        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Récupérez les données de la base de données
                String[][] flagData = BDD_v2.DataFlag();

                // Ajoutez les données à la table après le chargement
                for (String[] data : flagData) {
                    Object[] rowData = new Object[]{data[0], new ImageUtility(data[1], 55)};
                    publish(rowData); // Publiez les données pour les afficher dans l'EDT
                }

                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                // Ajoutez les données publiées à la table
                for (Object[] rowData : chunks) {
                    DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
                    model.addRow(rowData);
                }
            }
        };
        // Démarrer le SwingWorker
        worker.execute();
    }
    
    public void refreshModifiedRow(String oldName, String newName, String newImage) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String name = (String) model.getValueAt(row, 0);
            if (name.equals(oldName)) {
                model.setValueAt(newName, row, 0);
                model.setValueAt(new ImageUtility(newImage, 55), row, 1);
                break; // Sortez de la boucle une fois que la ligne modifiée a été trouvée
            }
        }
    }
}