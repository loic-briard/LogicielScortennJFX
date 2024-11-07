/*
 * 
 */
package Main;

/*
 * fenetre qui permet de choisir les joueurs participant au tournoi depuis les liste de joueur cree
 */
import java.awt.BorderLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import Players.Joueur;

// TODO: Auto-generated Javadoc
/**
 * The Class AthleteSelection.
 */
public class AthleteSelection extends JFrame {  
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The all players table. */
	private JTable allPlayersTable;
    
    /** The selected players table. */
    private JTable selectedPlayersTable;
    
    /** The all joueurs. */
    private ArrayList<Joueur> allJoueurs = new ArrayList<>();
    
    /** The selected players. */
    private ArrayList<Joueur> selectedPlayers = new ArrayList<>();

    /** The add button. */
    private JButton addButton = new JButton("Add ->");
    
    /** The remove button. */
    private JButton removeButton = new JButton("<- Delete");
    
    /** The validate button. */
    private JButton validateButton = new JButton("<html><u>V</u>alidate</html>");
    
    /** The search field. */
    private JTextField searchField = new JTextField();
    
    /** The model right table. */
    private DefaultTableModel modelRightTable;
    
    /** The choosen BDD. */
    private String choosenBDD;

	/**
	 * Instantiates a new athlete selection.
	 *
	 * @param choosenBDD the choosen BDD
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public AthleteSelection(String choosenBDD) throws SQLException, ClassNotFoundException {
        setTitle("Selection of Players");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 600); // Augmentez la largeur de la fen�tre pour mieux afficher les donn�es
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("- Impossible de charger l'icone.");
        }
        this.choosenBDD = choosenBDD;
        allJoueurs = BDD_v2.getAllJoueurs(choosenBDD);
        // R�cup�rez les donn�es des joueurs depuis la base de donn�es.
        String[][] playerDataList = BDD_v2.getAllPlayersInfoFromDatabase(choosenBDD);

        // Triez les joueurs par ordre alphab�tique en fonction de leur nom d'affichage.
        Arrays.sort(playerDataList, (a, b) -> a[0].compareTo(b[0]));
        
        String[] columnNames = { "Display name", "ID"};
//        DefaultTableModel modelLeft = new DefaultTableModel(playerDataList, columnNames);
//        allPlayersTable = new JTable(modelLeft);
        allPlayersTable = new JTable(playerDataList, columnNames) {
			private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false; // Rend toutes les cellules non éditables
            }
        };
		JScrollPane allPlayersScrollPane = new JScrollPane(allPlayersTable);
		
//        modelRightTable = new DefaultTableModel(null, columnNames);
//        selectedPlayersTable = new JTable(modelRightTable);
		modelRightTable = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
		    public boolean isCellEditable(int row, int column) {
		        return false; // Rend toutes les cellules non éditables
		    }
		};
		selectedPlayersTable = new JTable(modelRightTable);
		JScrollPane selectedPlayersScrollPane = new JScrollPane(selectedPlayersTable);
		
		addButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	ajoutPlyareToSelectedList();
		    }
		});
		allPlayersTable.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	ajoutPlyareToSelectedList();
		        }
		    }
		});
		selectedPlayersTable.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
		        	int selectedRow = selectedPlayersTable.getSelectedRow();
					if (selectedRow >= 0) {
						modelRightTable.removeRow(selectedRow);
					}
		        }
		    }
		});
		removeButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	int selectedRow = selectedPlayersTable.getSelectedRow();
				if (selectedRow >= 0) {
					modelRightTable.removeRow(selectedRow);
				}
		    }
		});
		ActionListener actionValidate = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	for (int row = 0; row < modelRightTable.getRowCount(); row++) {
		    	    String displayName = (String) modelRightTable.getValueAt(row, 0);
		    	    // Recherchez le joueur correspondant dans votre liste de joueurs compl�te
		    	    for (Joueur joueur : allJoueurs) {
		    	        if (joueur.getDisplay_name().equals(displayName)) {
		    	        	Joueur joueurAvecVerif=null;
		    	        	try {
								joueurAvecVerif = BDD_v2.getJoueurParID(joueur.getID(), choosenBDD);
								// R�cup�rer la date de naissance du joueur
							    String dobString = joueurAvecVerif.getBirthDate();
							    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							    Date dob = dateFormat.parse(dobString);
							    // Calculer l'�ge � partir de la date de naissance
							    int age = calculateAge(dob);
							    
							    // Comparer l'�ge actuel avec l'�ge enregistr� dans le joueur
							    boolean ageChanged = (age != joueurAvecVerif.getAge());
							    if (ageChanged) {
							        // Mettre � jour l'�ge du joueur
							        joueurAvecVerif.setAge(age);
							        BDD_v2.updateAgeJoueur(joueurAvecVerif.getID(), age, choosenBDD);
							    }
							} catch (ClassNotFoundException | SQLException | ParseException e1) {
								e1.printStackTrace();
							}
		    	            selectedPlayers.add(joueurAvecVerif);
		    	            //break; // Sortez de la boucle interne une fois que le joueur est trouv�
		    	        }
		    	    } 
		    	}
		    	dispose();
		    }
		};
		validateButton.addActionListener(actionValidate);
		// Associer l'action à la touche "V" (en minuscule)
		
//		validateButton.addActionListener(new ActionListener() {
//		    @Override
//		    public void actionPerformed(ActionEvent e) {
//		    	for (int row = 0; row < modelRightTable.getRowCount(); row++) {
//		    	    String displayName = (String) modelRightTable.getValueAt(row, 0);
//		    	    // Recherchez le joueur correspondant dans votre liste de joueurs compl�te
//		    	    for (Joueur joueur : allJoueurs) {
//		    	        if (joueur.getDisplay_name().equals(displayName)) {
//		    	        	Joueur joueurAvecVerif=null;
//		    	        	try {
//								joueurAvecVerif = BDD_v2.getJoueurParID(joueur.getID(), choosenBDD);
//								// R�cup�rer la date de naissance du joueur
//							    String dobString = joueurAvecVerif.getBirthDate();
//							    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//							    Date dob = dateFormat.parse(dobString);
//							    // Calculer l'�ge � partir de la date de naissance
//							    int age = calculateAge(dob);
//							    
//							    // Comparer l'�ge actuel avec l'�ge enregistr� dans le joueur
//							    boolean ageChanged = (age != joueurAvecVerif.getAge());
//							    if (ageChanged) {
//							        // Mettre � jour l'�ge du joueur
//							        joueurAvecVerif.setAge(age);
//							        BDD_v2.updateAgeJoueur(joueurAvecVerif.getID(), age, choosenBDD);
//							    }
//							} catch (ClassNotFoundException | SQLException | ParseException e1) {
//								e1.printStackTrace();
//							}
//		    	            selectedPlayers.add(joueurAvecVerif);
//		    	            //break; // Sortez de la boucle interne une fois que le joueur est trouv�
//		    	        }
//		    	    } 
//		    	}
//		    	dispose();
//		    }
//		});
        
		// Cr�ez un conteneur pour les boutons
        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(validateButton);
        
        // Ajouter une KeyBinding pour la touche "v" seule
        InputMap inputMap = validateButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = validateButton.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), "validateAction"); // 0 pour aucun modificateur
        actionMap.put("validateAction", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                actionValidate.actionPerformed(e);
            }
        });

        // Cr�ez un conteneur pour les listes et les boutons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(allPlayersScrollPane, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(selectedPlayersScrollPane, BorderLayout.EAST);

        // Ajoutez le conteneur principal � la fen�tre
        add(mainPanel);
     // Cr�er un panneau pour le champ de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(new JLabel("Search a player : "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Ajouter le panneau de recherche en haut de la fen�tre
        add(searchPanel, BorderLayout.NORTH);

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
                DefaultTableModel model = (DefaultTableModel) allPlayersTable.getModel();

                for (int row = 0; row < model.getRowCount(); row++) {
                    String displayName = (String) model.getValueAt(row, 0).toString().toLowerCase();
                    boolean match = displayName.contains(searchText);

                    if (match) {
                        // D�s�lectionnez toutes les autres lignes
                        allPlayersTable.clearSelection();
                        // S�lectionnez uniquement la ligne correspondante
                        allPlayersTable.getSelectionModel().setSelectionInterval(row, row);
                        // Faites d�filer jusqu'� la ligne s�lectionn�e pour la rendre visible
                        allPlayersTable.scrollRectToVisible(allPlayersTable.getCellRect(row, 0, true));
                        break; // Sortez de la boucle d�s qu'une correspondance est trouv�e
                    }
                }
            }
        });

        setVisible(true);
    }
    
    /**
     * Gets the selected players.
     *
     * @return the selected players
     */
    public ArrayList<Joueur> getSelectedPlayers(){
    	return this.selectedPlayers;
    }
    
    /**
     * Ajout plyare to selected list.
     */
    public void ajoutPlyareToSelectedList() {
    	int selectedRow = allPlayersTable.getSelectedRow();
		if (selectedRow >= 0) {
			// Obtenez les donn�es de la ligne s�lectionn�e
			String displayName = (String) allPlayersTable.getValueAt(selectedRow, 0);
			String id = (String) allPlayersTable.getValueAt(selectedRow, 1);
			String[] joueur = new String[] {displayName, id};
			modelRightTable.addRow(joueur);
			try {
				MainJFX.API.insertionsInfosSupJoueur(Integer.parseInt(id), choosenBDD);
			} catch (ClassNotFoundException | IOException | InterruptedException | JSONException | SQLException e1) {
				e1.printStackTrace();
			}
			
			
		}
    }
    
    /**
     * Calculate age.
     *
     * @param dob the dob
     * @return the int
     */
    private int calculateAge(Date dob) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dob);
        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}