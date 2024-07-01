package Background;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Main.BDD_v2;
import Main.ImageUtility;

public class ListOfBackgroundFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable backgroundTable;

	public ListOfBackgroundFrame() throws SQLException {
		// Initialisation de la fen�tre
		setTitle("List of Background");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1400, 500);
		ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'icone.");
        }

		// Cr�ez un mod�le de table
		String[] columnNames = { "Background name", "Background pictures 1", "Background pictures 2", "Background pictures 3", "Background pictures 4", "Background to wait " };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non �ditables
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 1 || column == 2 || column == 3 || column == 4 || column == 5) {
					return ImageUtility.class; // La colonne des images utilise la classe ImageUtility
				} else {
					return Object.class; // Les autres colonnes sont de type Object
				}
			}
		};
		// Cr�ez un JTable avec le mod�le de table
		backgroundTable = new JTable(tableModel);
		backgroundTable.setRowHeight(105);
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
		backgroundTable.getColumnModel().getColumn(1).setCellRenderer(imageRenderer);
		backgroundTable.getColumnModel().getColumn(2).setCellRenderer(imageRenderer);
		backgroundTable.getColumnModel().getColumn(3).setCellRenderer(imageRenderer);
		backgroundTable.getColumnModel().getColumn(4).setCellRenderer(imageRenderer);
		backgroundTable.getColumnModel().getColumn(5).setCellRenderer(imageRenderer);
		// R�cup�rez les donn�es de la base de donn�es
		String[][] eventData = BDD_v2.DataBackground();
		
		for (String[] data : eventData) {
			// Ajouter les donn�es � la table, y compris l'image
			ImageUtility imageRezize[] = new ImageUtility[5];
			for (int i = 1; i <= 5; i++)
				imageRezize[i - 1] = new ImageUtility(data[i], 100);
			// Ajoutez les donn�es � la table
			tableModel.addRow(new Object[] { data[0], imageRezize[0], imageRezize[1], imageRezize[2], imageRezize[3], imageRezize[4] });
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
		JScrollPane scrollPane = new JScrollPane(backgroundTable);
		add(scrollPane);
		// Rendez la fen�tre visible
		setVisible(true);

		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = backgroundTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String backgroundName = (String) backgroundTable.getValueAt(selectedRow, 0);
					String[] imagePaths = new String[5];
					for (int i = 1; i <= 5; i++) {
						ImageUtility imageUtility = (ImageUtility) backgroundTable.getValueAt(selectedRow, i);
						imagePaths[i - 1] = imageUtility.getImagePath();
					}

					// Ouvrir une fen�tre de modification avec ces donn�es
					new ModifyBackgroundFrame(ListOfBackgroundFrame.this, backgroundName, imagePaths[0], imagePaths[1], imagePaths[2], imagePaths[3], imagePaths[4]);
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = backgroundTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String backgroundName = (String) backgroundTable.getValueAt(selectedRow, 0);
					BDD_v2.deleteBackground(backgroundName);
					try {
						refreshData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new newBackgroundFrame(ListOfBackgroundFrame.this);
			}
		});
	}
	public void refreshData() throws SQLException {
	    // Effacez les donn�es actuelles de la table
	    DefaultTableModel model = (DefaultTableModel) backgroundTable.getModel();
	    model.setRowCount(0);

	    // Rechargez les donn�es depuis la base de donn�es et mettez � jour la table
	    String[][] backgroundData = BDD_v2.DataBackground();

	    for (String[] data : backgroundData) {
	        if (data[0] != null) {
	            // Ajouter les donn�es � la table, y compris les images
	            ImageUtility imageResize[] = new ImageUtility[5];
	            for (int i = 1; i <= 5; i++) {
	                if (data[i] != null) {
	                    imageResize[i - 1] = new ImageUtility(data[i], 100);
	                }
	            }

	            model.addRow(new Object[]{data[0], imageResize[0], imageResize[1], imageResize[2], imageResize[3], imageResize[4]});
	        } else {
	            System.out.println("---- Probleme avec le background");
	        }
	    }
	}
}
