/*
 * 
 */
package Players;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import Flags.Drapeau;

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONException;

import Main.BDD_v2;
import Main.ImageUtility;
import Main.InternetChecker;
import Main.MainJFX;

// TODO: Auto-generated Javadoc
/**
 * The Class newPlayerFrame.
 */
public class newPlayerFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id label. */
	// D�clarez les composants que vous avez d�crits
	private JLabel idLabel;
	
	/** The sex combo box. */
	private JComboBox<String> sexComboBox;
	
	/** The name field. */
	private JTextField nameField;
	
	/** The surname field. */
	private JTextField surnameField;
	
	/** The display name field. */
	private JTextField displayNameField;
	
	/** The nationality combo box. */
	private JComboBox<String> nationalityComboBox;
	
	private JLabel countryLabel;
	
	/** The flag label. */
	private JLabel flagLabel;
	
	/** The player image label. */
	private JLabel playerImageLabel;
	
	/** The load image button. */
	private JButton loadImageButton;
	
	/** The birthdate chooser. */
	private JDateChooser birthdateChooser;
	
	/** The ranking field. */
	private JTextField rankingField;
	
	/** The height field. */
	private JTextField heightField;
	
	/** The hand combo box. */
	private JComboBox<String> handComboBox;
	
	/** The age field. */
	private JTextField ageField;
	
	/** The weight field. */
	private JTextField weightField;
	
	/** The prize field. */
	private JTextField prizeField;
	
	/** The birthplace field. */
	private JTextField birthplaceField;
	
	/** The city residence field. */
	private JTextField cityResidenceField;
	
	/** The tete de serie field. */
	private JTextField teteDeSerieField;

	/** The bdd P layers combo box. */
	private JComboBox<String> bddPLayersComboBox;
	
	/** The selected joueurs. */
	private ArrayList<Joueur> selectedJoueurs;

	/** The content pane. */
	private JPanel contentPane;

	/** The current image. */
	private String currentImage;
	
	/** The current flag. */
	private String currentFlag;
	
	/** The bdd choosen. */
	private String bddChoosen;
	
	private String lastFolder = null;;
	
	/** The date format. */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	/** The i D. */
	private int iD;
	
	private JFrame newPlayerFrame; 

	/**
	 * Instantiates a new new player frame.
	 *
	 * @param parentFrame the parent frame
	 * @param bddChoosen the bdd choosen
	 * @throws SQLException the SQL exception
	 */
	public newPlayerFrame(GraphicsDevice configScreen, ListOfPlayersFrame parentFrame, String bddChoosen) throws SQLException {
		setTitle("New players ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        
        
		ImageIcon logoIcon = new ImageIcon("resources"+File.separator+"imgInterface"+File.separator+"icon.png");
		// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
		if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(logoIcon.getImage());
		} else {
			// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
			System.err.println("Impossible de charger l'ic�ne.");
		}
		this.bddChoosen = bddChoosen;
		this.newPlayerFrame = this;
		
		//ajout d'une combobox pour choisir les joueur dune autre bdd
		JComboBox<String> selectedPLayerComboBox = new JComboBox<>();
		selectedPLayerComboBox.setEditable(true);
		// Utilisez AutoCompleteDecorator pour activer la fonction de filtrage automatique
		AutoCompleteDecorator.decorate(selectedPLayerComboBox);
		selectedPLayerComboBox.setSelectedIndex(-1);
		// menu deroulant des bdd des joueurs
		bddPLayersComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
		// Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
		bddPLayersComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
				System.out.println("     BDD selected: " + selectedEventBDD);
				if(selectedJoueurs != null)
					selectedJoueurs.clear();
				if (bddPLayersComboBox.getSelectedItem() != null) {
					try {
						selectedJoueurs = BDD_v2.getAllJoueurs((String) bddPLayersComboBox.getSelectedItem());
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					selectedPLayerComboBox.removeAllItems();
					// Ajoutez les éléments de la liste aux JComboBox
					for (Joueur joueur : selectedJoueurs) {
						selectedPLayerComboBox.addItem(joueur.getDisplay_name());
					}
				}
			}
		});
		bddPLayersComboBox.setSelectedIndex(-1);

		// Cr�ez un conteneur avec GridBagLayout
		contentPane = new JPanel(new GridBagLayout());
		setContentPane(contentPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		iD = BDD_v2.getLastPlayerID(this.bddChoosen) + 1;
		// Cr�ez les composants en fonction de vos besoins
		idLabel = new JLabel(iD + "");
		sexComboBox = new JComboBox<>(new String[] { "Men", "Women" });
		nameField = new JTextField();
		surnameField = new JTextField();
		displayNameField = new JTextField();
		nationalityComboBox = new JComboBox<>(BDD_v2.getNamesFromDatabase("flag")); // Remplacez par les acronymes de la nationalit�
		nationalityComboBox.setSelectedItem(-1);
		countryLabel = new JLabel();
		flagLabel = new JLabel();// new ImageUtility(currentFlag,75).getIcon()
		// Utilisez JDateChooser pour choisir la date de naissance
		birthdateChooser = new JDateChooser();
		playerImageLabel = new JLabel();// new ImageUtility(currentImage,200).getIcon()
		playerImageLabel.setName("playerimagelabel");
		loadImageButton = new JButton("Load");

		rankingField = new JTextField();
		heightField = new JTextField();
		handComboBox = new JComboBox<>(new String[] { "right-handed", "left-handed" });
		ageField = new JTextField();
		weightField = new JTextField();
		prizeField = new JTextField();
		birthplaceField = new JTextField();
		cityResidenceField = new JTextField();
		teteDeSerieField = new JTextField();

		// Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
		nationalityComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// R�cup�rez l'�l�ment s�lectionn�
				String selectedFlag = (String) nationalityComboBox.getSelectedItem();
				try {
					currentFlag = BDD_v2.getFlagImagePathByAcronym(selectedFlag);
					countryLabel.setText(Drapeau.getFullName(selectedFlag));
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updatePlayerFlagPreview(currentFlag);
				// Faites ce que vous voulez avec l'�v�nement s�lectionn�
				System.out.println("++++ flag selected: " + selectedFlag + "|" + currentFlag);
			}
		});
		// Ajoutez les composants � la fen�tre en utilisant GridBagLayout
		int row = 0;
		addComponent(contentPane, "ID:", idLabel, gbc, 0, row);
		addComponent(contentPane, "Sex:", sexComboBox, gbc, 0, ++row);
		addComponent(contentPane, "Name:", nameField, gbc, 0, ++row);
		addComponent(contentPane, "Surname:", surnameField, gbc, 0, ++row);
		addComponent(contentPane, "Display Name:", displayNameField, gbc, 0, ++row);
		addComponent(contentPane, "Nationality:", nationalityComboBox, gbc, 0, ++row);
		addComponent(contentPane, "Country:", countryLabel, gbc, 0, ++row);
		addComponent(contentPane, "Flag:", flagLabel, gbc, 0, ++row);
		addComponent(contentPane, "Birthdate:", birthdateChooser, gbc, 0, ++row);
		addComponent(contentPane, "Player image:", playerImageLabel, gbc, 0, ++row);
		addComponent(contentPane, "Load Image:", loadImageButton, gbc, 0, ++row);
		addComponent(contentPane, "Ranking:", rankingField, gbc, 0, ++row);
		addComponent(contentPane, "Height:", heightField, gbc, 0, ++row);
		addComponent(contentPane, "Dominant hand:", handComboBox, gbc, 0, ++row);
		addComponent(contentPane, "Age:", ageField, gbc, 0, ++row);
		addComponent(contentPane, "Weight:", weightField, gbc, 0, ++row);
		addComponent(contentPane, "Prize total:", prizeField, gbc, 0, ++row);
		addComponent(contentPane, "Birth place:", birthplaceField, gbc, 0, ++row);
		addComponent(contentPane, "Residence city:", cityResidenceField, gbc, 0, ++row);
		addComponent(contentPane, "Seeding", teteDeSerieField, gbc, 0, ++row);
		int row2 = 0;
		addComponent(contentPane, " ", new JLabel("Choose a list of players"), gbc, 2, row2);
		addComponent(contentPane, " ", bddPLayersComboBox, gbc, 2, ++row2);
		addComponent(contentPane, " ", new JLabel("Search a player"), gbc, 2, ++row2);
		addComponent(contentPane, " ", selectedPLayerComboBox, gbc, 2, ++row2);
		JButton b_recupInfoPlayer = new JButton("<- add info");
		addComponent(contentPane, " ", b_recupInfoPlayer, gbc, 2, ++row2);

		// Ajoutez un trait vertical pour s�parer la fen�tre
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = row + 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		contentPane.add(new JSeparator(JSeparator.VERTICAL), gbc);

		// Ajoutez un �couteur d'�v�nements � la date de naissance
		birthdateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// R�cup�rez la date de naissance choisie par l'utilisateur
				Date dob = birthdateChooser.getDate();
				// Calculez l'�ge en fonction de la date de naissance
				int age = calculateAge(dob);
				// Affichez l'�ge dans le champ correspondant
				ageField.setText(Integer.toString(age));
			}
		});

		// Ajoutez un gestionnaire d'action au bouton "Load" pour charger une nouvelle image
		loadImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newImgPath = ImageUtility.chargerFichier(lastFolder);
				lastFolder = newImgPath;
				ImageUtility.enregistrerFichier(newImgPath,  "resources"+File.separator+"PlayersImages");
				currentImage =  "resources"+File.separator+"PlayersImages" + File.separator + ImageUtility.getNameFile(newImgPath);
				System.out.println("++++ image selectionne : "+currentImage);
				// Chargez les images actuelles et affichez-les dans les JLabels
				updatePlayerImagePreview(currentImage);
				pack();
			}
		});

		// Create a "Valider" button at the bottom
		JButton validateButton = new JButton("Valider");
		validateButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (validateFields()) {
		            String sexe = sexComboBox.getSelectedItem().toString();
		            String playerName = nameField.getText();
		            String playerSurname = surnameField.getText();
		            String displayName = displayNameField.getText();
		            String acroNat = nationalityComboBox.getSelectedItem().toString();
		            String country = countryLabel.getText();
		            String imgJoueur = currentImage;
		            String ranking = rankingField.getText();
		            String height = heightField.getText();
		            String hand = handComboBox.getSelectedItem().toString();
		            String age = ageField.getText();
		            String weight = weightField.getText();
		            String prize = prizeField.getText();
		            String birthplace = birthplaceField.getText();
		            String cityResidence = cityResidenceField.getText();
		            String teteDeSerie = teteDeSerieField.getText();

		            Joueur player = new Joueur(iD, sexe, playerName, playerSurname, displayName, acroNat,country, getDate(birthdateChooser), imgJoueur, Integer.parseUnsignedInt(ranking),
		                    Integer.parseUnsignedInt(height), hand, Integer.parseUnsignedInt(age), Integer.parseUnsignedInt(weight), prize, birthplace, cityResidence, teteDeSerie);
		            try {
		                BDD_v2.insertionJoueurDansBDD(player, bddChoosen);

		                String[] data = BDD_v2.getObjectJoueur(bddChoosen, player.getNom());
		                String flagImagePath = BDD_v2.getFlagImagePathByAcronym(data[5]);
		                String playerImagePath = data[7];
		                if (playerImagePath == null)
		                    playerImagePath = "resources"+File.separator+"imgInterface"+File.separator+"clear.png";
		                CustomTableModelJoueur model = (CustomTableModelJoueur) parentFrame.playersTable.getModel();
		                model.addRow(new Object[] { data[0], data[1], data[2], data[3], data[4], data[5], data[6],flagImagePath, playerImagePath, data[8], data[9],
		                        data[10], data[11], data[12], data[13], data[14], data[15], data[16], data[17] });
		                System.out.println("++++ Données traitées : " + Arrays.toString(data));
		                model.loadImages();
		            } catch (ClassNotFoundException | SQLException e2) {
		                e2.printStackTrace();
		            }
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(newPlayerFrame.this, "Please complete all fields", "Incomplete fields", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		gbc.gridx = 0;
		gbc.gridy = ++row;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		contentPane.add(validateButton, gbc);
		
		this.pack();
		setSize(600, this.getHeight()+40);
		setVisible(true);
		setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
		
		
		b_recupInfoPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InternetChecker.isInternetAvailable()) {
					for (Joueur joueur : selectedJoueurs) {
			            if (joueur.getDisplay_name().equals((String) selectedPLayerComboBox.getSelectedItem())) {
			            	try {
			            		String choosenBDD = (String)bddPLayersComboBox.getSelectedItem();
			            		if (BDD_v2.verifInfosManquante(joueur.getID(), choosenBDD)) {

			        				int choice = JOptionPane.showConfirmDialog(newPlayerFrame,
			        						"The player selected is not complete. Do you want to update it ?", "Player update",
			        						JOptionPane.YES_NO_OPTION);
			        				if (choice == JOptionPane.YES_OPTION) {
			        					System.out.println("update players " + joueur.getDisplay_name() + " selected");
			        					try {
			        						MainJFX.API.insertionsInfosSupJoueur(joueur.getID(), (String)bddPLayersComboBox.getSelectedItem());
			        					} catch (ClassNotFoundException | IOException | InterruptedException | JSONException
			        							| SQLException e1) {
			        						e1.printStackTrace();
			        					}
			        				} else
			        					System.out.println("don't update players " + joueur.getDisplay_name() + " selected");

			        			}
								autoCompleteInfosJoueur(BDD_v2.getJoueurParID(joueur.getID(), (String)bddPLayersComboBox.getSelectedItem())); // Retourne le joueur si son nom correspond
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }
					}
		        }
			}
		});
	}

	/**
	 * Update player image preview.
	 *
	 * @param imagePath the image path
	 */
	private void updatePlayerImagePreview(String imagePath) {
		ImageUtility image = new ImageUtility(imagePath, 150);
		playerImageLabel.setIcon(image.getIcon());
		playerImageLabel.revalidate();
		playerImageLabel.repaint();
		pack();
	}

	/**
	 * Update player flag preview.
	 *
	 * @param imagePath the image path
	 */
	private void updatePlayerFlagPreview(String imagePath) {
		ImageUtility image = new ImageUtility(imagePath, 75);
		flagLabel.setIcon(image.getIcon());
		flagLabel.revalidate();
		flagLabel.repaint();
	}

	/**
	 * Adds the component.
	 *
	 * @param container the container
	 * @param label the label
	 * @param component the component
	 * @param gbc the gbc
	 * @param x the x
	 * @param y the y
	 */
	private void addComponent(Container container, String label, JComponent component, GridBagConstraints gbc, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		container.add(new JLabel(label), gbc);
		gbc.gridx = x + 1;
		gbc.weightx = 1.0;
		container.add(component, gbc);
	}

	/**
	 * Gets the date.
	 *
	 * @param birthdateChooser the birthdate chooser
	 * @return the date
	 */
	private String getDate(JDateChooser birthdateChooser) {
		// Obtenez la date de naissance
		Date birthdate = birthdateChooser.getDate();
		// Formatez la date de naissance en tant que cha�ne de caract�res
		String formattedDate = dateFormat.format(birthdate);
		System.out.println("++++ date de naissance formater : "+formattedDate);
		return formattedDate;
	}

	/**
	 * Calculate age.
	 *
	 * @param dob the dob
	 * @return the int
	 */
	// M�thode pour calculer l'�ge � partir de la date de naissance
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
	
	/**
	 * Auto complete infos joueur.
	 *
	 * @param selectedJoueur the selected joueur
	 */
	private void autoCompleteInfosJoueur(Joueur selectedJoueur) {
		sexComboBox.setSelectedItem(selectedJoueur.getSexe());
		nameField.setText(selectedJoueur.getNom());
		surnameField.setText(selectedJoueur.getPrenom());
		displayNameField.setText(selectedJoueur.getDisplay_name());
		nationalityComboBox.setSelectedItem(selectedJoueur.getNatio_acronyme());
		countryLabel.setText(selectedJoueur.getCountry());
		rankingField.setText(selectedJoueur.getRank()+"");
		heightField.setText(selectedJoueur.getTaille()+"");
		handComboBox.setSelectedItem(selectedJoueur.getMain());
		
		// Cr�ation d'un objet SimpleDateFormat avec le format de date sp�cifi�
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Conversion de la cha�ne en objet Date
			if (selectedJoueur.getBirthDate() != null) {
				Date dateBirthdatePlayer = simpleDateFormat.parse(selectedJoueur.getBirthDate());
				birthdateChooser.setDate(dateBirthdatePlayer);
			}
            // Affichage de la date convertie
//            System.out.println("Date convertie : " + dateBirthdatePlayer);
        } catch (ParseException e) {
            System.err.println("Erreur lors de la conversion de la date : " + e.getMessage());
        }
        
        currentImage = selectedJoueur.getImgJoueur();
        updatePlayerImagePreview(selectedJoueur.getImgJoueur());
        
		// R�cup�rez la date de naissance choisie par l'utilisateur
        int age = 0;
		if (birthdateChooser.getDate() != null) {
			Date dob = birthdateChooser.getDate();
			// Calculez l'�ge en fonction de la date de naissance
			age = calculateAge(dob);
		} // Affichez l'�ge dans le champ correspondant
		ageField.setText(Integer.toString(age));
		
		weightField.setText(selectedJoueur.getWeight()+"");
		prizeField.setText(selectedJoueur.getPrizetotal());
		birthplaceField.setText(selectedJoueur.getBirthPlace());
		cityResidenceField.setText(selectedJoueur.getCityResidence());
		teteDeSerieField.setText(selectedJoueur.getTeteDeSerie());

		this.pack();
		setSize(600, this.getHeight());
	}
	
	/**
	 * Validate fields.
	 *
	 * @return true, if successful
	 */
	private boolean validateFields() {
	    return !nameField.getText().isEmpty() &&
	           !surnameField.getText().isEmpty() &&
	           !displayNameField.getText().isEmpty() &&
	           nationalityComboBox.getSelectedIndex() != -1 &&
	           birthdateChooser.getDate() != null &&
	           !rankingField.getText().isEmpty() &&
	           !heightField.getText().isEmpty() &&
	           !ageField.getText().isEmpty() &&
	           !weightField.getText().isEmpty() &&
	           !prizeField.getText().isEmpty() &&
	           !birthplaceField.getText().isEmpty() &&
	           !cityResidenceField.getText().isEmpty();
	}
}
