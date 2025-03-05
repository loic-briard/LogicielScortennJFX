/*
 * 
 */
package Players;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import Main.BDD_v2;
import Main.ImageUtility;
import Main.MenuPrincipal;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfPlayersFrame.
 */
public class ListOfPlayersFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
//	private static final int PAGE_SIZE = 5;  // Ajustez selon vos besoins
//	private static final int MAX_PLAYERS = 100; // Limite de joueurs � charger
//    private int currentPage = 0;
//    private boolean loadingData = false;  // Indique si les donn�es sont actuellement en cours de chargement

    /** The players table. */
public JTable playersTable;
    
    /** The table model. */
    private CustomTableModelJoueur tableModel;
    
    private static final String PLAYERS_IMAGES_DIR = "PlayersImages/";
    
    /** The scroll pane. */
    private JScrollPane scrollPane;
    
    /** The table data. */
    Object[][] tableData = null;
	
	/** The search field. */
	private JTextField searchField = new JTextField();
	
	/** The bdd P layers combo box. */
	private JComboBox<String> bddPLayersComboBox;
	
	/** The clignotement actif. */
	private static boolean clignotementActif = false;
//	private final Object workerLock = new Object();
/** The Constant IMAGE_HEIGHT. */
//	SwingWorker<Void, Void> currentWorker;
	private static final int IMAGE_HEIGHT = 60;
	
	private MenuPrincipal parentFrame;
	private static JFrame frameForMessageFrame; 

	/**
	 * Instantiates a new list of players frame.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public ListOfPlayersFrame(GraphicsDevice configScreen, MenuPrincipal menuPrincipal) throws SQLException, ClassNotFoundException {
		this.parentFrame = menuPrincipal;
		setTitle("List of Players");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1400, 600); // Augmentez la largeur de la fen�tre pour mieux afficher les donn�es
		Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
        
		ImageIcon logoIcon = new ImageIcon("icon.png");
		// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
		if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(logoIcon.getImage());
		} else {
			// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
			System.err.println("Impossible de charger l'ic�ne.");
		}
		//------------------------------ fin chargement logo -------------------------------------------------
//		JButton validateBdd = new JButton("display player");
		JPanel buttonsAndSearchPanel = new JPanel();
		buttonsAndSearchPanel.setLayout(new BoxLayout(buttonsAndSearchPanel, BoxLayout.X_AXIS));
		buttonsAndSearchPanel.setVisible(false);
		
		JButton deleteTableButton = new JButton("delete list");
		deleteTableButton.setVisible(false);
		
		JButton exportButton = new JButton("export");
        exportButton.setVisible(false);
		
		//menu deroulant des bdd des joueurs
		bddPLayersComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
		bddPLayersComboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
		        System.out.println("BDD selected to display : " + selectedEventBDD);
		        
		        if (selectedEventBDD != null) {
		        	try {
						tableData = BDD_v2.DataJoueur(selectedEventBDD);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	SwingUtilities.invokeLater(() -> {
		                tableModel.setNewData(tableData);
		                tableModel.loadImages();
		            });
		        	buttonsAndSearchPanel.setVisible(true);
					scrollPane.setVisible(true);
					deleteTableButton.setVisible(true);
					exportButton.setVisible(true);
		        }
		    }
		});
        bddPLayersComboBox.setSelectedIndex(-1);

		// ---------------------------------------------creation du tableau des joueurs--------------------------------------------------------------------------
        
        tableModel = new CustomTableModelJoueur(new Object[][]{});
        playersTable = new JTable(tableModel){
			private static final long serialVersionUID = 1L;

			@Override
    	    public boolean getScrollableTracksViewportHeight() {
    	        return getPreferredSize().height < getParent().getHeight();
    	    }
    	};
    	playersTable.setRowHeight(IMAGE_HEIGHT);
        playersTable.setDefaultRenderer(ImageUtility.class, new ImageRenderer());
        scrollPane = new JScrollPane(playersTable);
        
		//-----------------------------------------------------------------------------------------------------------------------------------------
		// Cr�er un panneau pour le champ de recherche
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(new JLabel("Search a player: "), BorderLayout.WEST);
		searchField.setPreferredSize(new Dimension(200, searchField.getPreferredSize().height)); // Ajuster la largeur ici
		searchPanel.add(searchField, BorderLayout.CENTER);

        // Ajouter un �couteur pour le champ de recherche
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSelection();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSelection();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSelection();
            }

            private void updateSelection() {
                String searchText = searchField.getText().trim().toLowerCase();
                CustomTableModelJoueur model = (CustomTableModelJoueur) playersTable.getModel(); // Utilisez CustomTableModel ici

                for (int row = 0; row < model.getRowCount(); row++) {
                    String playerName = (String) model.getValueAt(row, 4).toString().toLowerCase();
                    boolean match = playerName.contains(searchText);

                    if (match) {
                        // D�s�lectionnez toutes les autres lignes
                        playersTable.clearSelection();
                        // S�lectionnez uniquement la ligne correspondante
                        playersTable.getSelectionModel().setSelectionInterval(row, row);
                        // Faites d�filer jusqu'� la ligne s�lectionn�e pour la rendre visible
                        playersTable.scrollRectToVisible(playersTable.getCellRect(row, 0, true));
                        break; // Sortez de la boucle d�s qu'une correspondance est trouv�e
                    }
                }
            }
        });

		// Cr�ez un JPanel pour les boutons

		JButton newButton = new JButton("New");
		JButton modifyButton = new JButton("Modify");
		JButton deleteButton = new JButton("Delete");
        searchPanel.add(searchField);

		// Cr�er un JPanel pour les boutons et le champ de recherche
        // Ajoutez les boutons au panneau

        buttonsAndSearchPanel.add(searchPanel);
        buttonsAndSearchPanel.add(newButton);
        buttonsAndSearchPanel.add(modifyButton);
        buttonsAndSearchPanel.add(deleteButton);

        // Cr�er un panneau principal avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel p_panelBDD = new JPanel();
        JButton createButton = new JButton("create");
        JButton importButton = new JButton("import");
        JTextField listPlayerField = new JTextField();
        listPlayerField.setText("enter name");
        p_panelBDD.add(new JLabel("List of Players : "));
        p_panelBDD.add(bddPLayersComboBox);
        p_panelBDD.add(deleteTableButton);
        p_panelBDD.add(exportButton);
        p_panelBDD.add(new JLabel("       New List of Players : "));
        p_panelBDD.add(listPlayerField);
        p_panelBDD.add(createButton);
        p_panelBDD.add(importButton);
        // Ajoutez la JComboBox en haut � gauche
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(p_panelBDD, gbc);

        // Ajoutez le JPanel des boutons et du champ de recherche en haut au milieu
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;  // Pour �tirer le panneau et occuper tout l'espace disponible
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(buttonsAndSearchPanel, gbc);

        // Ajoutez le panneau principal en haut de la fen�tre
        add(mainPanel, BorderLayout.NORTH);
		// ajout du tableau defilant
		
	    add(scrollPane);
	    scrollPane.setVisible(false);
		setVisible(true);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(listPlayerField.getText() != null) {
						String newTableName = listPlayerField.getText();

						BDD_v2.creationNewTable(newTableName);
						BDD_v2.getAllListPlayerTableName();
						System.out.println(BDD_v2.tabBdd.toString());
						updateComboboxTable();
						bddPLayersComboBox.setSelectedItem(newTableName.toUpperCase());
						// V�rification de la s�lection
					    String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
					    System.out.println("++++ bdd cree : " + selectedEventBDD);

		                if (selectedEventBDD != null) {
		                	try {
								tableData = BDD_v2.DataJoueur(selectedEventBDD);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				        	SwingUtilities.invokeLater(() -> {
				                tableModel.setNewData(tableData);
				                tableModel.loadImages();
				            });
							buttonsAndSearchPanel.setVisible(true);
							scrollPane.setVisible(true);
							deleteTableButton.setVisible(true);
							exportButton.setVisible(true);
							parentFrame.refreshPlayerTableCombobox();
		                }
						
//						bddPLayersComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
						//BDD_v2.deletePlayerTable();
					}
					else {
						System.out.println("---- pas de nouveau nom ecrit");
						
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteTableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bddPLayersComboBox.getSelectedItem() != null) {
					try {
						BDD_v2.deleteOnePlayerTable((String) bddPLayersComboBox.getSelectedItem());
						updateComboboxTable();
						bddPLayersComboBox.setSelectedIndex(-1);
						scrollPane.setVisible(false);
						parentFrame.refreshPlayerTableCombobox();
					} catch (SQLException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}else
					System.out.println("---- La base de donnee a supprimer est null");
			}
		});

		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = playersTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String ID = (String) playersTable.getValueAt(selectedRow, 0);
					String sexe = (String) playersTable.getValueAt(selectedRow, 1);
					String playerName = (String) playersTable.getValueAt(selectedRow, 2);
					String playerSurname = (String) playersTable.getValueAt(selectedRow, 3);
					String DisplayName = (String) playersTable.getValueAt(selectedRow, 4);
					String acroNat = (String) playersTable.getValueAt(selectedRow, 5);
					String country = (String) playersTable.getValueAt(selectedRow, 6);
					ImageIcon img_flag = (ImageIcon) playersTable.getValueAt(selectedRow, 7);
					String string_flag = img_flag.getDescription();
//					ImageUtility flag = (ImageUtility) playersTable.getValueAt(selectedRow, 6);
					ImageIcon img_Joueur = (ImageIcon) playersTable.getValueAt(selectedRow, 8);
					String string_joueur = img_Joueur.getDescription();
//					ImageUtility imgJoueur = (ImageUtility) playersTable.getValueAt(selectedRow, 7);
					String ranking = (String) playersTable.getValueAt(selectedRow, 9);
					String prize = (String) playersTable.getValueAt(selectedRow, 10);
					String height = (String) playersTable.getValueAt(selectedRow, 11);
					String hand = (String) playersTable.getValueAt(selectedRow, 12);
					String age = (String) playersTable.getValueAt(selectedRow, 13);
					String weight = (String) playersTable.getValueAt(selectedRow, 14);
					String bithdate = (String) playersTable.getValueAt(selectedRow, 15);
					String birthplace = (String) playersTable.getValueAt(selectedRow, 16);
					String cityResidence = (String) playersTable.getValueAt(selectedRow, 17);
					String teteDeSerie = (String) playersTable.getValueAt(selectedRow, 18);

					new ModifyPlayersFrame(configScreen, ListOfPlayersFrame.this, ID, sexe, playerName, playerSurname, DisplayName, acroNat,country, string_flag, bithdate, string_joueur, ranking, height, hand, age, weight, prize,
							birthplace, cityResidence,teteDeSerie, (String) bddPLayersComboBox.getSelectedItem(),selectedRow);
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = playersTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String playerName = (String) playersTable.getValueAt(selectedRow, 2);
					try {
						BDD_v2.deleteJoueur(playerName, (String) bddPLayersComboBox.getSelectedItem());
						// Supprimez la ligne du mod�le de tableau
			            CustomTableModelJoueur model = (CustomTableModelJoueur) playersTable.getModel();
			            model.removeRow(selectedRow);
					} catch (SQLException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // a modifier pour event
				}
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new newPlayerFrame(configScreen, ListOfPlayersFrame.this, (String) bddPLayersComboBox.getSelectedItem());
				} catch (SQLException e1) {
					clignotementActif = true;
					changerContourTemporaire(bddPLayersComboBox);
					JOptionPane.showMessageDialog(frameForMessageFrame, "no database selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDD_v2.exportTableToZip(null, (String) bddPLayersComboBox.getSelectedItem());
//				BDD_v2.exportTable((String) bddPLayersComboBox.getSelectedItem());
			}
		});
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
					importZipFile();
					updateComboboxTable();
					parentFrame.refreshPlayerTableCombobox();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ListOfPlayersFrame.frameForMessageFrame = this;
	}
	/**
	 * Changer contour temporaire.
	 *
	 * @param comboBox the combo box
	 */
	private static void changerContourTemporaire(JComboBox<?> comboBox) {
	    // Sauvegarde de la bordure d'origine
	    Border bordureOriginale = comboBox.getBorder();

	    // Utilisation d'un Timer pour clignoter la bordure pendant 2 secondes
	    Timer timer = new Timer(250, new ActionListener() {
	        private boolean etatClignotant = false;
	        private long debutClignotement = System.currentTimeMillis();

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (!clignotementActif) {
	                ((Timer) e.getSource()).stop();  // Arr�ter le Timer si le clignotement n'est plus actif
	                return;
	            }

	            long tempsActuel = System.currentTimeMillis();

	            if (tempsActuel - debutClignotement < 2000) {  // Clignoter pendant 2 secondes
	                if (etatClignotant) {
	                    comboBox.setBorder(new LineBorder(Color.RED));
	                } else {
	                    comboBox.setBorder(bordureOriginale);
	                }

	                etatClignotant = !etatClignotant;
	            } else {
	                comboBox.setBorder(bordureOriginale);
	                ((Timer) e.getSource()).stop();  // Arr�ter le Timer apr�s 2 secondes
	            }
	        }
	    });
	    timer.start();
	}
	
	/**
	 * Update combobox table.
	 *
	 * @throws SQLException the SQL exception
	 */
	private void updateComboboxTable() throws SQLException {
		BDD_v2.getAllListPlayerTableName();
		bddPLayersComboBox.setModel(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
	}
	
	/**
     * Importe un fichier ZIP contenant un CSV et des images de joueurs.
     */
    public static void importZipFile() {
        // 📌 Demander à l'utilisateur de choisir un ZIP
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select ZIP File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(frameForMessageFrame);

        if (returnValue != JFileChooser.APPROVE_OPTION) {
            System.out.println("Importation annulée.");
            return;
        }

        File zipFile = fileChooser.getSelectedFile();
        System.out.println("ZIP sélectionné : " + zipFile.getAbsolutePath());

        // Extraire les fichiers du ZIP
        try {
            Path tempDir = Files.createTempDirectory("zip_extract_");
            unzipFile(zipFile, tempDir);

            // Trouver le fichier CSV extrait et l'importer
            File extractedFolder = tempDir.toFile();
            File csvFile = findCSVFile(extractedFolder);
            if (csvFile != null) {
                String tableName = csvFile.getName().replace(".csv", "");
                BDD_v2.importCSV(tableName, csvFile.getAbsolutePath());
                JOptionPane.showMessageDialog(frameForMessageFrame, "CSV importé avec succès !");
            } else {
                JOptionPane.showMessageDialog(frameForMessageFrame, "Aucun fichier CSV trouvé dans le ZIP !");
            }

            // Déplacer les images extraites vers PlayersImages/
            File playerImagesFolder = new File(extractedFolder, "player_image");
            if (playerImagesFolder.exists()) {
                moveImagesToPlayersImages(playerImagesFolder);
            } else {
                System.out.println("Aucun dossier 'player_image' trouvé.");
            }

            JOptionPane.showMessageDialog(frameForMessageFrame, "Importation terminée !");
        } catch (IOException | SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frameForMessageFrame, "Erreur lors de l'importation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Décompresse un ZIP dans un dossier temporaire.
     */
    private static void unzipFile(File zipFile, Path outputDir) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File extractedFile = new File(outputDir.toFile(), entry.getName());

                if (entry.isDirectory()) {
                    extractedFile.mkdirs();
                } else {
                    extractedFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(extractedFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipIn.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipIn.closeEntry();
            }
        }
    }

    /**
     * Recherche un fichier CSV dans un dossier extrait.
     * @return Le fichier CSV trouvé ou `null` si absent.
     */
    private static File findCSVFile(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(".csv")) {
                    System.out.println("Fichier CSV trouvé : " + file.getAbsolutePath());
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * Déplace toutes les images extraites vers le dossier `PlayersImages/`.
     */
    private static void moveImagesToPlayersImages(File sourceDir) {
        if (!sourceDir.exists()) return;

        File destinationDir = new File(PLAYERS_IMAGES_DIR);
        if (!destinationDir.exists()) destinationDir.mkdirs(); // Crée `PlayersImages/` s'il n'existe pas

        File[] imageFiles = sourceDir.listFiles();
        if (imageFiles != null) {
            for (File file : imageFiles) {
                File newFile = new File(destinationDir, file.getName());
                try {
                    Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image déplacée : " + newFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Erreur de déplacement : " + file.getName());
                }
            }
        }
    }
 
}
