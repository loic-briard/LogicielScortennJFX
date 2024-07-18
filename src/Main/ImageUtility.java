package Main;

/*
 * permet de charger/enregistrer, modifier, recuperer infos d'une image
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class ImageUtility extends JLabel {
    private static final long serialVersionUID = 1L;
    private int width; // Largeur de l'image redimensionn�e
    private String imagePath;
    private BufferedImage rezizedImage;

    public ImageUtility(String imagePath, int height) {
        this.imagePath = imagePath;
        try {
            BufferedImage originalImage = null;//ImageIO.read(new File(this.imagePath));
            if(imagePath != "" || imagePath != "''") {
            	System.out.println("chemin vers l'image : "+this.imagePath);
            	originalImage = ImageIO.read(new File(this.imagePath));
            }else originalImage = ImageIO.read(new File("clear.png"));
            	
            
            if(height>0) {
            	// Calculer la largeur proportionnellement � la hauteur
                double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                this.width = (int) (aspectRatio * height);
            }else {
            	System.out.println("  taille de l'image originale");
            	height = originalImage.getHeight();
            	width = originalImage.getWidth();
            }
            

            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Cr�ez un nouvel objet BufferedImage de la taille souhait�e
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Dessinez l'image redimensionn�e dans le nouvel objet BufferedImage
            Graphics g = resizedImage.getGraphics();
            g.drawImage(scaledImage, 0, 0, null);
            g.dispose();
            
            this.rezizedImage = resizedImage;
            // Affichez l'image redimensionn�e dans le JLabel
            setIcon(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // M�thode pour d�finir la taille de l'image
    public void setImageSize(int width, int height) {
        this.width = width;
    }
    public String getImagePath() {
        return imagePath;
    }
    public BufferedImage getRezizedImage() {
    	return rezizedImage;
    }
    
    public static String chargerFichier() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Images/xls", "jpg", "png", "gif","xls"));
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String filePath = selectedFile.getAbsolutePath();
			System.out.println("++ file Path : " + filePath + ", selected File : " + selectedFile);
			return filePath;
		} else
			JOptionPane.showMessageDialog(null, "il n'y a pas d'image de charger", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.out.println("-- probleme lors du chargemment de l'image");
		return null;
	}
    public static void enregistrerFichier (String sourceFilePath, String destinationDirectoryPath) {
        File sourceFile = new File(sourceFilePath);
        File destinationDirectory = new File(destinationDirectoryPath);

        // V�rifier si le fichier source existe
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.err.println("Le fichier source n'existe pas ou n'est pas un fichier valide.");
            return;
        }
        // V�rifier si le r�pertoire de destination existe
        if (!destinationDirectory.exists() || !destinationDirectory.isDirectory()) {
            System.err.println("Le r�pertoire de destination n'existe pas ou n'est pas un r�pertoire valide.");
            return;
        }

        // Obtenir le nom du fichier source
        String sourceFileName = sourceFile.getName();
        // Construire le chemin de destination en joignant le nom de fichier au r�pertoire de destination
        Path destinationFilePath = new File(destinationDirectory, sourceFileName).toPath();

        try {
            // Copier le fichier source dans le r�pertoire de destination
            Files.copy(sourceFile.toPath(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("++ Le fichier a ete copie avec succes.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("-- Erreur lors de la copie du fichier.");
        }
    }
    public static String getNameFile (String sourceFilePath) {
    	File sourceFile = new File(sourceFilePath);
    	// V�rifier si le fichier source existe
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.err.println("-- Le fichier source n'existe pas ou n'est pas un fichier valide.");
            return null;
        }
        // Obtenir le nom du fichier source
        String sourceFileName = sourceFile.getName();    
        return sourceFileName;
    }
}
