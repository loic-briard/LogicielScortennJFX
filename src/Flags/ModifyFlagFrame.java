package Flags;

import javax.swing.*;

import Main.BDD_v2;
import Main.ImageUtility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class ModifyFlagFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	//private ListOfFlag parentFrame; // R�f�rence � la fen�tre ListOfEventsFrame
	
	private JTextField nameField;
	private String currentName;
	private String newName;
	
    private JPanel imagePreviewPanel;   
    private String currentImage;
    
    private JButton loadButton;

    public ModifyFlagFrame(ListOfFlag parentFrame, String currentName, String imgPath) {
		//this.parentFrame = parentFrame;
    	this.currentName = currentName;
        this.currentImage = imgPath;
        loadButton = new JButton("Load");

        setTitle("Modify Flag : "+currentName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 800);
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }
        
        // Ajoutez le champ de texte pour le nom
        nameField = new JTextField(this.currentName);
        add(nameField, BorderLayout.NORTH);
        // Cr�ez un bouton "Valider"
        JButton validateButton = new JButton("Validate");
        add(validateButton, BorderLayout.SOUTH);
        // Cr�ez un JPanel pour afficher les aper�us d'images
        imagePreviewPanel = new JPanel();
        imagePreviewPanel.setLayout(new BoxLayout(imagePreviewPanel, BoxLayout.Y_AXIS));
        // Chargez les images actuelles et affichez-les dans les JLabels
        updateImagePreviews();
        // Ajoutez le JPanel des aper�us d'images � la fen�tre
        add(imagePreviewPanel, BorderLayout.CENTER);    

        // Ajoutez un gestionnaire d'action au bouton "Valider"
        validateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newName = nameField.getText();
                String newFlag[] = {newName, currentImage};
                BDD_v2.updateFlagInDatabase(currentName, newFlag);
                // Fermez la fen�tre de modification
                dispose();
                //mettre a jour la ligne qui a �t� moddifi� ici 
                try {
                    parentFrame.refreshModifiedRow(currentName, newName, currentImage);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Ajoutez un gestionnaire d'action au bouton "load 1"
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "flag");
                currentImage = "flag"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ chemin vers le drapeau : "+imgPath);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updateImagePreviews();
            }
        });
     
        

        setVisible(true);
    }

    // Mettez � jour les aper�us des images
    private void updateImagePreviews() {
    	// Chargez les images actuelles et affichez-les dans les JLabels
    	System.out.println("+++ image actuel : "+currentImage);
    	ImageUtility image = new ImageUtility(currentImage, 150);

    	// Cr�ez des JLabels pour afficher les images actuelles
    	JLabel labelImage1 = new JLabel(image.getIcon());
    	
    	// image 1 -----------------------------------------------------------------------------
    	JPanel boxImage1 = new JPanel();
    	boxImage1.setLayout(new BoxLayout(boxImage1, BoxLayout.X_AXIS));
    	boxImage1.add(labelImage1);
    	boxImage1.add(loadButton);

        imagePreviewPanel.removeAll();  // Effacez les aper�us pr�c�dents
        imagePreviewPanel.add(boxImage1);

        revalidate();  // Rafra�chissez l'affichage
    }
}