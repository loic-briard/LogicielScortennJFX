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
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import Flags.Drapeau;

import javax.swing.*;
import Main.BDD_v2;
import Main.ImageUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyPlayersFrame.
 */
public class ModifyPlayersFrame extends JFrame{
	
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
    
    /** The flag label. */
    private JLabel countryLabel;
    /** The flag label. */
    private JLabel flagLabel;

/** The player image label. */
//    private JDateChooser  birthdatePicker;
    private JLabel playerImageLabel;
    
    /** The load image button. */
    private JButton loadImageButton;
    
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
        
    /** The content pane. */
    private JPanel contentPane;
    
    /** The current image. */
    private String currentImage;
    
    /** The current flag. */
    private String currentFlag;
    
    /** The bddchoosen. */
    private String bddchoosen;
    
    /** The date format. */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    /** The joueur modifier. */
    private  Object[] joueurModifier;
    
    /** The birthdate chooser. */
    private JDateChooser birthdateChooser;
    
    private String lastFolder = null;
    
	/**
	 * Instantiates a new modify players frame.
	 *
	 * @param parentFrame the parent frame
	 * @param iD the i D
	 * @param sexe the sexe
	 * @param playerName the player name
	 * @param playerSurname the player surname
	 * @param displayName the display name
	 * @param acroNat the acro nat
	 * @param flag the flag
	 * @param bithdate the bithdate
	 * @param imgJoueur the img joueur
	 * @param ranking the ranking
	 * @param height the height
	 * @param hand the hand
	 * @param age the age
	 * @param weight the weight
	 * @param prize the prize
	 * @param birthplace the birthplace
	 * @param cityResidence the city residence
	 * @param bddChoosen the bdd choosen
	 * @param selectedRow the selected row
	 */
	public ModifyPlayersFrame(GraphicsDevice configScreen, ListOfPlayersFrame parentFrame, String iD, String sexe, String playerName, String playerSurname, String displayName, 
			String acroNat,String country, String flag, String bithdate, String imgJoueur, String ranking, String height, String hand, String age, 
			String weight, String prize, String birthplace, String cityResidence,String teteDeSerie, String bddChoosen, int selectedRow) {
		this.currentImage = imgJoueur;
		this.currentFlag = flag;
		this.bddchoosen = bddChoosen;
		
		setTitle("Modify players : "+playerName +" "+playerSurname);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 900);
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
        
     // Cr�ez un conteneur avec GridBagLayout
        contentPane = new JPanel(new GridBagLayout());
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        
     // Cr�ez les composants en fonction de vos besoins
        idLabel = new JLabel(iD);
        sexComboBox = new JComboBox<>(new String[]{"Men", "Women"});
        nameField = new JTextField(playerName);
        surnameField = new JTextField(playerSurname);
        displayNameField = new JTextField(displayName);
        nationalityComboBox = new JComboBox<>(BDD_v2.getNamesFromDatabase("flag")); // Remplacez par les acronymes de la nationalit�
        nationalityComboBox.setSelectedItem(acroNat);
        countryLabel = new JLabel(country);
        flagLabel = new JLabel(new ImageUtility(currentFlag,75).getIcon());
     // Utilisez JDateChooser pour choisir la date de naissance
        birthdateChooser = new JDateChooser(); // Assurez-vous que la date est correctement format�e
        try {
            Date dateNaissance = dateFormat.parse(bithdate);
            birthdateChooser.setDate(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace();
            birthdateChooser.setDate(new Date());
        }
        playerImageLabel = new JLabel(new ImageUtility(currentImage,200).getIcon());
        playerImageLabel.setName("playerimagelabel");
        loadImageButton = new JButton("Load");

        rankingField = new JTextField(ranking);
        heightField = new JTextField(height);
        handComboBox = new JComboBox<>(new String[]{"right-handed", "left-handed"});
        handComboBox.setSelectedItem(hand);
        ageField = new JTextField(age);
        weightField = new JTextField(weight);
        prizeField = new JTextField(prize);
        birthplaceField = new JTextField(birthplace);
        cityResidenceField = new JTextField(cityResidence);    
        teteDeSerieField = new JTextField(teteDeSerie);
        
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
                System.out.println("++++ flag selected: " + selectedFlag+"|"+currentFlag);
                
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
       addComponent(contentPane, "BirthDate:", birthdateChooser, gbc, 0, ++row);
       addComponent(contentPane, "Playe image:", playerImageLabel, gbc, 0, ++row);
       addComponent(contentPane, "Load Image:", loadImageButton, gbc, 0, ++row);
       addComponent(contentPane, "Ranking:", rankingField, gbc, 0, ++row);
       addComponent(contentPane, "Heigh:", heightField, gbc, 0, ++row);
       addComponent(contentPane, "Dominant hand:", handComboBox, gbc, 0, ++row);
       addComponent(contentPane, "Age:", ageField, gbc, 0, ++row);
       addComponent(contentPane, "Weight:", weightField, gbc, 0, ++row);
       addComponent(contentPane, "Prize total:", prizeField, gbc, 0, ++row);
       addComponent(contentPane, "Birth place:", birthplaceField, gbc, 0, ++row);
       addComponent(contentPane, "Residence city:", cityResidenceField, gbc, 0, ++row);
       addComponent(contentPane, "Seeding:", teteDeSerieField, gbc, 0, ++row);
              
     // Ajoutez un gestionnaire d'action au bouton "Load" pour charger une nouvelle image
        loadImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	String newImgPath = ImageUtility.chargerFichier(lastFolder);
            	lastFolder = newImgPath;
                ImageUtility.enregistrerFichier(newImgPath, "PlayersImages");
                currentImage = "PlayersImages"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ image charger : "+currentImage);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updatePlayerImagePreview(currentImage);
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
                    String flag = currentFlag;
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
                    
                    BDD_v2.updatePlayersInDatabase(Integer.parseUnsignedInt(iD), sexe, playerName, playerSurname, displayName, acroNat,country, flag, getDate(birthdateChooser), imgJoueur,
                            Integer.parseUnsignedInt(ranking), height,hand,age, weight,prize,birthplace,cityResidence,teteDeSerie, bddchoosen);
                    dispose();
                    
                    joueurModifier = new Object[] {iD, sexe, playerName, playerSurname, displayName, acroNat,country, flag, imgJoueur,
                            ranking,prize,height,hand,age, weight, getDate(birthdateChooser),birthplace,cityResidence,teteDeSerie};
                    CustomTableModelJoueur model = (CustomTableModelJoueur) parentFrame.playersTable.getModel();
                    model.updateRow(selectedRow, joueurModifier);
                    model.loadImages();
//                    DefaultTableCellRenderer imageRenderer = new DefaultTableCellRenderer() {
//                        private static final long serialVersionUID = 1L;
//
//                        @Override
//                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                            if (value instanceof ImageUtility) {
//                                ImageUtility imageUtility = (ImageUtility) value;
//                                return imageUtility;
//                            }
//                            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                        }
//                    };

//                    parentFrame.playersTable.getColumnModel().getColumn(6).setCellRenderer(imageRenderer);
//                    parentFrame.playersTable.getColumnModel().getColumn(7).setCellRenderer(imageRenderer);
                } else {
                    JOptionPane.showMessageDialog(ModifyPlayersFrame.this, "Please complete all fields", "Incomplete fields", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(validateButton, gbc);
        
        setVisible(true); 
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
