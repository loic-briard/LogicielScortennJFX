package Players;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MediaTracker;
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

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONException;

import Main.BDD_v2;
import Main.ImageUtility;
import Main.MainJFX;

public class newPlayerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	// D�clarez les composants que vous avez d�crits
	private JLabel idLabel;
	private JComboBox<String> sexComboBox;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField displayNameField;
	private JComboBox<String> nationalityComboBox;
	private JLabel flagLabel;
	private JLabel playerImageLabel;
	private JButton loadImageButton;
	private JDateChooser birthdateChooser;
	private JTextField rankingField;
	private JTextField heightField;
	private JComboBox<String> handComboBox;
	private JTextField ageField;
	private JTextField weightField;
	private JTextField prizeField;
	private JTextField birthplaceField;
	private JTextField cityResidenceField;

	private JComboBox<String> bddPLayersComboBox;
	private ArrayList<Joueur> selectedJoueurs;

	private JPanel contentPane;

	private String currentImage;
	private String currentFlag;
	private String bddChoosen;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private int iD;

	public newPlayerFrame(ListOfPlayersFrame parentFrame, String bddChoosen) throws SQLException {
		setTitle("New players ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ImageIcon logoIcon = new ImageIcon("icon.png");
		// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
		if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(logoIcon.getImage());
		} else {
			// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
			System.err.println("Impossible de charger l'ic�ne.");
		}
		this.bddChoosen = bddChoosen;
		
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

		// Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
		nationalityComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// R�cup�rez l'�l�ment s�lectionn�
				String selectedFlag = (String) nationalityComboBox.getSelectedItem();
				try {
					currentFlag = BDD_v2.getFlagImagePathByAcronym(selectedFlag);
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
				String newImgPath = ImageUtility.chargerFichier();
				ImageUtility.enregistrerFichier(newImgPath, "PlayersImages");
				currentImage = "PlayersImages" + File.separator + ImageUtility.getNameFile(newImgPath);
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
		            String imgJoueur = currentImage;
		            String ranking = rankingField.getText();
		            String height = heightField.getText();
		            String hand = handComboBox.getSelectedItem().toString();
		            String age = ageField.getText();
		            String weight = weightField.getText();
		            String prize = prizeField.getText();
		            String birthplace = birthplaceField.getText();
		            String cityResidence = cityResidenceField.getText();

		            Joueur player = new Joueur(iD, sexe, playerName, playerSurname, displayName, acroNat, getDate(birthdateChooser), imgJoueur, Integer.parseUnsignedInt(ranking),
		                    Integer.parseUnsignedInt(height), hand, Integer.parseUnsignedInt(age), Integer.parseUnsignedInt(weight), prize, birthplace, cityResidence);
		            try {
		                BDD_v2.insertionJoueurDansBDD(player, bddChoosen);

		                String[] data = BDD_v2.getObjectJoueur(bddChoosen, player.getNom());
		                String flagImagePath = BDD_v2.getFlagImagePathByAcronym(data[5]);
		                String playerImagePath = data[6];
		                if (playerImagePath == null)
		                    playerImagePath = "clear.png";
		                CustomTableModelJoueur model = (CustomTableModelJoueur) parentFrame.playersTable.getModel();
		                model.addRow(new Object[] { data[0], data[1], data[2], data[3], data[4], data[5], flagImagePath, playerImagePath, data[7], data[8],
		                        data[9], data[10], data[11], data[12], data[13], data[14], data[15] });
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
		
		
		b_recupInfoPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Joueur joueur : selectedJoueurs) {
		            if (joueur.getDisplay_name().equals((String) selectedPLayerComboBox.getSelectedItem())) {
		            	try {
							MainJFX.API.insertionsInfosSupJoueur(joueur.getID(), (String)bddPLayersComboBox.getSelectedItem());
							autoCompleteInfosJoueur(BDD_v2.getJoueurParID(joueur.getID(), (String)bddPLayersComboBox.getSelectedItem())); // Retourne le joueur si son nom correspond
						} catch (ClassNotFoundException | IOException | InterruptedException | JSONException
								| SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            }
		        }
			}
		});
	}

	private void updatePlayerImagePreview(String imagePath) {
		ImageUtility image = new ImageUtility(imagePath, 150);
		playerImageLabel.setIcon(image.getIcon());
		playerImageLabel.revalidate();
		playerImageLabel.repaint();
		pack();
	}

	private void updatePlayerFlagPreview(String imagePath) {
		ImageUtility image = new ImageUtility(imagePath, 75);
		flagLabel.setIcon(image.getIcon());
		flagLabel.revalidate();
		flagLabel.repaint();
	}

	private void addComponent(Container container, String label, JComponent component, GridBagConstraints gbc, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		container.add(new JLabel(label), gbc);
		gbc.gridx = x + 1;
		gbc.weightx = 1.0;
		container.add(component, gbc);
	}

	private String getDate(JDateChooser birthdateChooser) {
		// Obtenez la date de naissance
		Date birthdate = birthdateChooser.getDate();
		// Formatez la date de naissance en tant que cha�ne de caract�res
		String formattedDate = dateFormat.format(birthdate);
		System.out.println("++++ date de naissance formater : "+formattedDate);
		return formattedDate;
	}

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
	
	private void autoCompleteInfosJoueur(Joueur selectedJoueur) {
		sexComboBox.setSelectedItem(selectedJoueur.getSexe());
		nameField.setText(selectedJoueur.getNom());
		surnameField.setText(selectedJoueur.getPrenom());
		displayNameField.setText(selectedJoueur.getDisplay_name());
		nationalityComboBox.setSelectedItem(selectedJoueur.getNatio_acronyme());
		rankingField.setText(selectedJoueur.getRank()+"");
		heightField.setText(selectedJoueur.getTaille()+"");
		handComboBox.setSelectedItem(selectedJoueur.getMain());
		
		// Cr�ation d'un objet SimpleDateFormat avec le format de date sp�cifi�
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Conversion de la cha�ne en objet Date
            Date dateBirthdatePlayer = simpleDateFormat.parse(selectedJoueur.getBirthDate());
            birthdateChooser.setDate(dateBirthdatePlayer);
            // Affichage de la date convertie
            System.out.println("++++ Date convertie : " + dateBirthdatePlayer);
        } catch (ParseException e) {
            System.err.println("++++ Erreur lors de la conversion de la date : " + e.getMessage());
        }
        
        currentImage = selectedJoueur.getImgJoueur();
        updatePlayerImagePreview(selectedJoueur.getImgJoueur());
        
		// R�cup�rez la date de naissance choisie par l'utilisateur
		Date dob = birthdateChooser.getDate();
		// Calculez l'�ge en fonction de la date de naissance
		int age = calculateAge(dob);
		// Affichez l'�ge dans le champ correspondant
		ageField.setText(Integer.toString(age));
		
		weightField.setText(selectedJoueur.getWeight()+"");
		prizeField.setText(selectedJoueur.getPrizetotal());
		birthplaceField.setText(selectedJoueur.getBirthPlace());
		cityResidenceField.setText(selectedJoueur.getCityResidence());
		

		this.pack();
		setSize(600, this.getHeight());
	}
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
