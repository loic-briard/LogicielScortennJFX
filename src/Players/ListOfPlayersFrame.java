package Players;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import Main.BDD_v2;
import Main.ImageUtility;

public class ListOfPlayersFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 5;  // Ajustez selon vos besoins
//	private static final int MAX_PLAYERS = 100; // Limite de joueurs � charger
    private int currentPage = 0;
    private boolean loadingData = false;  // Indique si les donn�es sont actuellement en cours de chargement

    public JTable playersTable;
    private JScrollPane scrollPane;
	private JTextField searchField = new JTextField();
	private JComboBox<String> bddPLayersComboBox;
	private static boolean clignotementActif = false;
	private final Object workerLock = new Object();
	SwingWorker<Void, Void> currentWorker;
	

	public ListOfPlayersFrame() throws SQLException, ClassNotFoundException {
		setTitle("List of Players");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1400, 600); // Augmentez la largeur de la fen�tre pour mieux afficher les donn�es
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
        // Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
//		bddPLayersComboBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
//				System.out.println("BDD selected to display : " + selectedEventBDD);
//
//				if (selectedEventBDD != null) {
//					if (currentWorker != null && !currentWorker.isDone()) {
//						currentWorker.cancel(true);
//					} else {
//						CustomTableModel model = (CustomTableModel) playersTable.getModel();
//						model.clearData();
//						model.fireTableDataChanged();
//						currentPage = 0; // R�initialiser la page actuelle � z�ro
//						loadMorePlayersData();  // Ajoutez cette ligne pour charger les nouvelles donn�es
//						buttonsAndSearchPanel.setVisible(true);
//						scrollPane.setVisible(true);
//						deleteTableButton.setVisible(true);
//						exportButton.setVisible(true);
//					}
//				}
//			}
//		});
		bddPLayersComboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
		        System.out.println("BDD selected to display : " + selectedEventBDD);

		        if (selectedEventBDD != null) {
		            stopCurrentWorker().thenRun(() -> {
		                SwingUtilities.invokeLater(() -> {
		                	CustomTableModel model = (CustomTableModel) playersTable.getModel();
							model.clearData();
							model.fireTableDataChanged();
							currentPage = 0; // R�initialiser la page actuelle � z�ro
							loadMorePlayersData();  // Ajoutez cette ligne pour charger les nouvelles donn�es
							buttonsAndSearchPanel.setVisible(true);
							scrollPane.setVisible(true);
							deleteTableButton.setVisible(true);
							exportButton.setVisible(true);
		                });
		            });
		        }
		    }
		});
        bddPLayersComboBox.setSelectedIndex(-1);

		// ---------------------------------------------creation du tableau des joueurs--------------------------------------------------------------------------
        
        CustomTableModel tableModel = new CustomTableModel();
        playersTable = new JTable(tableModel);
        playersTable.setRowHeight(60);
        
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

        playersTable.getColumnModel().getColumn(6).setCellRenderer(imageRenderer);
        playersTable.getColumnModel().getColumn(7).setCellRenderer(imageRenderer);


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
                CustomTableModel model = (CustomTableModel) playersTable.getModel(); // Utilisez CustomTableModel ici

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
		scrollPane = new JScrollPane(playersTable);
	    add(scrollPane);
	    scrollPane.setVisible(false);
		setVisible(true);
		
		/*//ajout d'un ecouteur d'event sur la barre de defilement verticale, chragement lorsque on atteint le bas
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
		    @Override
		    public void adjustmentValueChanged(AdjustmentEvent e) {
		        // V�rifier si la barre de d�filement est en bas
//		        if (e.getValueIsAdjusting() && e.getAdjustable().getValue() == e.getAdjustable().getMaximum() - e.getAdjustable().getVisibleAmount()) {
//		            loadMorePlayersData();
//		        }

//		        // V�rifier si la barre de d�filement est d�sactiv�e (en bas et ne peut pas descendre davantage)
//		        if (e.getValue() == e.getAdjustable().getMaximum() - e.getAdjustable().getVisibleAmount()) {
//		            System.out.println("La barre de d�filement est en bas et ne peut pas descendre davantage.");
//		            loadMorePlayersData();
//		        }
		    }
		});*/
		
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
		                	if (currentWorker != null && !currentWorker.isDone()) {
					            currentWorker.cancel(true);
					        }
		                    CustomTableModel model = (CustomTableModel) playersTable.getModel();
							model.clearData();
							model.fireTableDataChanged();
							currentPage = 0; // R�initialiser la page actuelle � z�ro
							loadMorePlayersData();  // Ajoutez cette ligne pour charger les nouvelles donn�es
							buttonsAndSearchPanel.setVisible(true);
							scrollPane.setVisible(true);
							deleteTableButton.setVisible(true);
							exportButton.setVisible(true);
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
					} catch (SQLException e1) {
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
					ImageUtility flag = (ImageUtility) playersTable.getValueAt(selectedRow, 6);
					ImageUtility imgJoueur = (ImageUtility) playersTable.getValueAt(selectedRow, 7);
					String ranking = (String) playersTable.getValueAt(selectedRow, 8);
					String prize = (String) playersTable.getValueAt(selectedRow, 9);
					String height = (String) playersTable.getValueAt(selectedRow, 10);
					String hand = (String) playersTable.getValueAt(selectedRow, 11);
					String age = (String) playersTable.getValueAt(selectedRow, 12);
					String weight = (String) playersTable.getValueAt(selectedRow, 13);
					String bithdate = (String) playersTable.getValueAt(selectedRow, 14);
					String birthplace = (String) playersTable.getValueAt(selectedRow, 15);
					String cityResidence = (String) playersTable.getValueAt(selectedRow, 16);

					new ModifyPlayersFrame(ListOfPlayersFrame.this, ID, sexe, playerName, playerSurname, DisplayName, acroNat, flag, bithdate, imgJoueur, ranking, height, hand, age, weight, prize,
							birthplace, cityResidence, (String) bddPLayersComboBox.getSelectedItem(),selectedRow);
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
			            CustomTableModel model = (CustomTableModel) playersTable.getModel();
			            model.removeRow(selectedRow);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // a modifier pour event
				}
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new newPlayerFrame(ListOfPlayersFrame.this, (String) bddPLayersComboBox.getSelectedItem());
				} catch (SQLException e1) {
					clignotementActif = true;
					changerContourTemporaire(bddPLayersComboBox);
					JOptionPane.showMessageDialog(null, "no database selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDD_v2.exportTable((String) bddPLayersComboBox.getSelectedItem());
			}
		});
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cr�er une bo�te de dialogue de s�lection de fichier
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select CSV File");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                String fileName = "";
                // Afficher la bo�te de dialogue de s�lection de fichier
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // R�cup�rer le fichier s�lectionn� par l'utilisateur
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    fileName = selectedFile.getName().replace(".csv", "");
                    try {
                        // Appeler la m�thode d'importation de CSV avec le chemin du fichier
                        BDD_v2.importCSV(fileName, filePath);
                        JOptionPane.showMessageDialog(null, "CSV file imported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException | IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error importing CSV file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                try {
					BDD_v2.getAllListPlayerTableName();
					updateComboboxTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bddPLayersComboBox.setSelectedItem(fileName.toUpperCase());
			    String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
			    System.out.println("Creation de la bdd du fichier " + selectedEventBDD);

                if (selectedEventBDD != null) {
//                	if (worker != null && !worker.isDone()) {
//                		System.out.println("! Chargement annulee dans import CSV !");
//			            worker.cancel(true);
//			        }
                    CustomTableModel model = (CustomTableModel) playersTable.getModel();
					model.clearData();
					model.fireTableDataChanged();
					currentPage = 0; // R�initialiser la page actuelle � z�ro
					loadMorePlayersData();  // Ajoutez cette ligne pour charger les nouvelles donn�es
					buttonsAndSearchPanel.setVisible(true);
					scrollPane.setVisible(true);
					deleteTableButton.setVisible(true);
					exportButton.setVisible(true);
                }
			}
		});
		// Appel de l'annulation lorsque la fen�tre est ferm�e
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        if (currentWorker != null && !currentWorker.isDone()) {
		            currentWorker.cancel(true);
		        }
		        try {
					Main.MenuPrincipal.refreshPlayerTableCombobox();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
	}

	private void loadMorePlayersData() {
	    if (loadingData) {
	        System.out.println("Les donnees chargent...");
	        return;
	    }
	    if (currentWorker != null && !currentWorker.isDone()) {
	        System.out.println("Une tâche de chargement est déjà en cours.");
	        return;
	    }

	    String selectedBDD = (String) bddPLayersComboBox.getSelectedItem();
	    if (selectedBDD == null) {
	        clignotementActif = true;
	        changerContourTemporaire(bddPLayersComboBox);
	        JOptionPane.showMessageDialog(null, "no database selected", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    loadingData = true;
	    
	    // Attendre que le worker précédent soit terminé
	    if (currentWorker != null && !currentWorker.isDone()) {
	        try {
	            currentWorker.get(); // Attendre que le worker précédent se termine
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	        }
	    }

	    currentWorker = new SwingWorker<Void, Void>() {
	        @Override
	        protected Void doInBackground() throws Exception {
	            try {
	                int loadNbjoueur = PAGE_SIZE;
	                if (currentPage == 0) {
	                    loadNbjoueur += 500;
	                }

	                int nbelemntBdd = BDD_v2.compterNbElementsBDD(selectedBDD);
	                System.out.println("Nombre d'elements dans la base de donnees "+selectedBDD+" : " + nbelemntBdd);
	                if (nbelemntBdd < loadNbjoueur) {
	                    loadNbjoueur = nbelemntBdd;
	                }
	                
	                if(playersTable.getModel().getRowCount() == nbelemntBdd)      {
	                	return null;
	                }

	                int startIndex = currentPage * PAGE_SIZE;
	                System.out.println("Chargement des joueurs de " + startIndex + " a " + loadNbjoueur);
	                CustomTableModel model = (CustomTableModel) playersTable.getModel();
	                List<String[]> playersData = BDD_v2.getData(selectedBDD, startIndex, loadNbjoueur);
	                int i = 0;
	                for (String[] data : playersData) {
	                    if (isCancelled()) {
	                        // Si l'annulation est demand�e, sortez de la boucle
	                        System.out.println("! Chargement annule.");
	                        return null;
	                    }

	                    System.out.println("Traitement des donn�es : " + Arrays.toString(data));
	                    String flagImagePath = BDD_v2.getFlagImagePathByAcronym(data[5]);
	                    String playerImagePath = data[6];
	                    if (playerImagePath == null)
	                        playerImagePath = "clear.png";
	                    model.addRow(new Object[] { data[0], data[1], data[2], data[3], data[4], data[5],
	                            new ImageUtility(flagImagePath, 55), new ImageUtility(playerImagePath, 55), data[7],
	                            data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15] });
	                    System.out.println(""+i+" Donnees traitees : " + Arrays.toString(data));
	                    i++;
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }

	            return null;
	        }

	        @Override
	        protected void done() {
	            loadingData = false;
	        }
	    };
	    
	    currentWorker.execute();
	}
	
	private CompletableFuture<Void> stopCurrentWorker() {
	    CompletableFuture<Void> future = new CompletableFuture<>();
	    
	    synchronized (workerLock) {
	        if (currentWorker != null && !currentWorker.isDone()) {
	            currentWorker.cancel(true);
	            currentWorker.addPropertyChangeListener(evt -> {
	                if ("state".equals(evt.getPropertyName()) && 
	                    SwingWorker.StateValue.DONE == evt.getNewValue()) {
	                    future.complete(null);
	                }
	            });
	        } else {
	            future.complete(null);
	        }
	        currentWorker = null;
	    }
	    
	    return future;
	}
	
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
	
	private void updateComboboxTable() throws SQLException {
		BDD_v2.getAllListPlayerTableName();
		bddPLayersComboBox.setModel(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
	}
}
