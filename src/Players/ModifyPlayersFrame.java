package Players;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import Main.BDD_v2;
import Main.ImageUtility;

public class ModifyPlayersFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	// D�clarez les composants que vous avez d�crits
    private JLabel idLabel;
    private JComboBox<String> sexComboBox;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField displayNameField;
    private JComboBox<String> nationalityComboBox;
    private JLabel flagLabel;
//    private JDateChooser  birthdatePicker;
    private JLabel playerImageLabel;
    private JButton loadImageButton;
    private JTextField rankingField;
    private JTextField heightField;
    private JComboBox<String> handComboBox;
    private JTextField ageField;
    private JTextField weightField;
    private JTextField prizeField;
    private JTextField birthplaceField;
    private JTextField cityResidenceField;
        
    private JPanel contentPane;
    
    private String currentImage;
    private String currentFlag;
    private String bddchoosen;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private  Object[] joueurModifier;
    
	public ModifyPlayersFrame(ListOfPlayersFrame parentFrame, String iD, String sexe, String playerName, String playerSurname, String displayName, 
			String acroNat, ImageUtility flag, String bithdate, ImageUtility imgJoueur, String ranking, String height, String hand, String age, 
			String weight, String prize, String birthplace, String cityResidence, String bddChoosen, int selectedRow) {
		this.currentImage = imgJoueur.getImagePath();
		this.currentFlag = flag.getImagePath();
		this.bddchoosen = bddChoosen;
		
		setTitle("Modify players : "+playerName +" "+playerSurname);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 900);
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
        flagLabel = new JLabel(new ImageUtility(currentFlag,75).getIcon());
     // Utilisez JDateChooser pour choisir la date de naissance
        JDateChooser birthdateChooser = new JDateChooser(); // Assurez-vous que la date est correctement format�e
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
              
     // Ajoutez un gestionnaire d'action au bouton "Load" pour charger une nouvelle image
        loadImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	String newImgPath = ImageUtility.chargerFichier();
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
            	String sexe = sexComboBox.getSelectedItem().toString();
            	String playerName = nameField.getText();
            	String playerSurname = surnameField.getText();
            	String displayName = displayNameField.getText();
            	String acroNat = nationalityComboBox.getSelectedItem().toString();
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
            	
            	BDD_v2.updatePlayersInDatabase(Integer.parseUnsignedInt(iD), sexe, playerName, playerSurname, displayName, acroNat, flag, getDate(birthdateChooser), imgJoueur,
            			Integer.parseUnsignedInt(ranking), height,hand,age, weight,prize,birthplace,cityResidence, bddchoosen);
            	dispose();
            	
            	joueurModifier = new Object[] {Integer.parseUnsignedInt(iD), sexe, playerName, playerSurname, displayName, acroNat, flag, imgJoueur,
            			Integer.parseUnsignedInt(ranking),prize,height,hand,age, weight, getDate(birthdateChooser),birthplace,cityResidence};
            	CustomTableModel model = (CustomTableModel) parentFrame.playersTable.getModel();
	            model.updateRow(selectedRow, joueurModifier);
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

	            parentFrame.playersTable.getColumnModel().getColumn(6).setCellRenderer(imageRenderer);
	            parentFrame.playersTable.getColumnModel().getColumn(7).setCellRenderer(imageRenderer);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(validateButton, gbc);
        
        setVisible(true); 
    }
	private void updatePlayerImagePreview(String imagePath) {
	    ImageUtility image = new ImageUtility(imagePath, 150);
	    playerImageLabel.setIcon(image.getIcon());
	    playerImageLabel.revalidate();
	    playerImageLabel.repaint();
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
}
