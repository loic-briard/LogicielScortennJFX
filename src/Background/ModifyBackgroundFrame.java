package Background;

import javax.swing.*;

import Main.BDD_v2;
import Main.ImageUtility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class ModifyBackgroundFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField nameField;
	private String currentName;
	private String newName;
	
    private JPanel imagePreviewPanel;   
    private String currentImage1;
    private String currentImage2;
    private String currentImage3;
    private String currentImage4;
    private String currentImage5;
    
    private JButton loadButton1;
    private JButton loadButton2;
    private JButton loadButton3;
    private JButton loadButton4;
    private JButton loadButton5;

    public ModifyBackgroundFrame(ListOfBackgroundFrame parentFrame, String currentName, String currentImageA, String currentImageB, String currentImageC, String currentImageD,String currentImageE) {
    	this.currentName = currentName;
        this.currentImage1 = currentImageA;
        this.currentImage2 = currentImageB;
        this.currentImage3 = currentImageC;
        this.currentImage4 = currentImageD;
        this.currentImage5 = currentImageE;
        loadButton1 = new JButton("Load 1");
        loadButton2 = new JButton("Load 2");
        loadButton3 = new JButton("Load 3");
        loadButton4 = new JButton("Load 4");
        loadButton5 = new JButton("Load wait background");

        setTitle("Modify Background "+currentName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 800);
        
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
                String newBackground[] = {newName, currentImage1,currentImage2,currentImage3,currentImage4,currentImage5};
                BDD_v2.updateBackgroundInDatabase(currentName, newBackground);
                // Fermez la fen�tre de modification
                dispose();
                try {
					parentFrame.refreshData();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        // Ajoutez un gestionnaire d'action au bouton "load 1"
        loadButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "Background");
                currentImage1 = "Background"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ background image 1 : "+currentImage1);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updateImagePreviews();
            }
        });
     // Ajoutez un gestionnaire d'action au bouton "load 1"
        loadButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "Background");
                currentImage2 = "Background"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ background image 2 : "+currentImage2);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updateImagePreviews();
            }
        });
     // Ajoutez un gestionnaire d'action au bouton "load 1"
        loadButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "Background");
                currentImage3 = "Background"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ background image 3 : "+currentImage3);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updateImagePreviews();
            }
        });
     // Ajoutez un gestionnaire d'action au bouton "load 1"
        loadButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "Background");
                currentImage4 = "Background"+File.separator+ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ background image 4 : "+currentImage4);
                // Chargez les images actuelles et affichez-les dans les JLabels
                updateImagePreviews();
            }
        });
	    // Ajoutez un gestionnaire d'action au bouton "load 1"
	    loadButton5.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String newImgPath = ImageUtility.chargerFichier();
	    		ImageUtility.enregistrerFichier(newImgPath, "Background");
	    		currentImage5 = "Background"+File.separator+ImageUtility.getNameFile(newImgPath);
	    		System.out.println("++++ background image 5 : "+currentImage4);
	    		// Chargez les images actuelles et affichez-les dans les JLabels
	    		updateImagePreviews();
	    	}
	    });
        setVisible(true);
    }

    // Mettez � jour les aper�us des images
    private void updateImagePreviews() {
    	// Chargez les images actuelles et affichez-les dans les JLabels
    	ImageUtility image1 = new ImageUtility(currentImage1, 150);
    	ImageUtility image2 = new ImageUtility(currentImage2, 150);
    	ImageUtility image3 = new ImageUtility(currentImage3, 150);
    	ImageUtility image4 = new ImageUtility(currentImage4, 150);
    	ImageUtility image5 = new ImageUtility(currentImage5, 150);

    	// Cr�ez des JLabels pour afficher les images actuelles
    	JLabel labelImage1 = new JLabel(image1.getIcon());
    	JLabel labelImage2 = new JLabel(image2.getIcon());
    	JLabel labelImage3 = new JLabel(image3.getIcon());
    	JLabel labelImage4 = new JLabel(image4.getIcon());
    	JLabel labelImage5 = new JLabel(image5.getIcon());
    	
    	// image 1 -----------------------------------------------------------------------------
    	JPanel boxImage1 = new JPanel();
    	boxImage1.setLayout(new BoxLayout(boxImage1, BoxLayout.X_AXIS));
    	boxImage1.add(labelImage1);
    	boxImage1.add(loadButton1);
    	// image 2 -----------------------------------------------------------------------------
    	JPanel boxImage2 = new JPanel();
    	boxImage2.setLayout(new BoxLayout(boxImage2, BoxLayout.X_AXIS));
    	boxImage2.add(labelImage2);
    	boxImage2.add(loadButton2);
    	// image 3 -----------------------------------------------------------------------------
    	JPanel boxImage3 = new JPanel();
    	boxImage3.setLayout(new BoxLayout(boxImage3, BoxLayout.X_AXIS));
    	boxImage3.add(labelImage3);
    	boxImage3.add(loadButton3);
    	// image 4 -----------------------------------------------------------------------------
    	JPanel boxImage4 = new JPanel();
    	boxImage4.setLayout(new BoxLayout(boxImage4, BoxLayout.X_AXIS));
    	boxImage4.add(labelImage4);
    	boxImage4.add(loadButton4);
    	// image 5 -----------------------------------------------------------------------------
    	JPanel boxImage5 = new JPanel();
    	boxImage5.setLayout(new BoxLayout(boxImage5, BoxLayout.X_AXIS));
    	boxImage5.add(labelImage5);
    	boxImage5.add(loadButton5);

        imagePreviewPanel.removeAll();  // Effacez les aper�us pr�c�dents
        imagePreviewPanel.add(boxImage1);
        imagePreviewPanel.add(boxImage2);
        imagePreviewPanel.add(boxImage3);
        imagePreviewPanel.add(boxImage4);
        imagePreviewPanel.add(boxImage5);

        revalidate();  // Rafra�chissez l'affichage
    }
}
