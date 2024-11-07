/*
 * 
 */
package Flags;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import Main.BDD_v2;
import Main.MainJFX;

// TODO: Auto-generated Javadoc
/**
 * The Class Drapeau.
 */
public class Drapeau {
	
	/** The Nom. */
	private String Nom;
	
	/** The Image drapeau. */
	private String ImageDrapeau;
	
	/**
	 * Instantiates a new drapeau.
	 *
	 * @param nom the nom
	 * @param path the path
	 */
	public Drapeau(String nom, String path) {
		super();
		this.Nom = nom;
		this.ImageDrapeau = path;
	}

	/**
	 * Gets the image drapeau.
	 *
	 * @return the image drapeau
	 */
	public String getImageDrapeau() {
		return ImageDrapeau;
	}

	/**
	 * Sets the image drapeau.
	 *
	 * @param imageDrapeau the new image drapeau
	 */
	public void setImageDrapeau(String imageDrapeau) {
		ImageDrapeau = imageDrapeau;
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return Nom;
	}

	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		Nom = nom;
	}

	/**
	 * Charger drapeau.
	 */
	public static void chargerDrapeau() {
		try {
		    if (MainJFX.openOnEclipse) {
		        // Mode Eclipse: Acc�s direct aux fichiers
		        File folder = new File("flag/");
		        if (folder.exists() && folder.isDirectory() && folder.canRead()) {
		            File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg")); // Filtre pour les images
		            for (File file : files) {
		                String fileName = file.getName();
		                String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
		                String filePath = file.getPath();
		                System.out.println("++++ fichier vers drapeau " + filePath);
		                BDD_v2.insertionDrapeauDansBDD(new Drapeau(nameWithoutExtension, filePath));
		            }
		            System.out.println("++++ Drapeaux inserer dans bdd avec succes.");
		        } else {
		            System.err.println("---- Le dossier de drapeaux est introuvable ou inaccessible.");
		        }
		    } else {
		    	// Mode JAR: Utilisation de getResourceAsStream pour acc�der aux ressources
                // Supposons que nous avons un fichier 'flag_manifest.txt' qui liste tous les fichiers drapeaux
                InputStream manifestStream = MainJFX.class.getClassLoader().getResourceAsStream("manifest.txt");
                if (manifestStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(manifestStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Pour chaque ligne (nom de fichier) dans le manifeste, chargez le drapeau correspondant
                        InputStream flagStream = MainJFX.class.getClassLoader().getResourceAsStream("flag/" + line);
                        if (flagStream != null) {
                        	String nameWithoutExtension = line.substring(0, line.lastIndexOf('.'));
                            // Ici, vous pouvez utiliser l'image charg�e, par exemple l'ins�rer dans la base de donn�es
                            BDD_v2.insertionDrapeauDansBDD(new Drapeau(nameWithoutExtension, "flag/" + line));
                            System.out.println("++++ Drapeau charge: " + line);
                        } else {
                            System.err.println("---- Impossible de charger le drapeau: " + line);
                        }
                    }
                } else {
                    System.err.println("---- Impossible de trouver le manifeste des drapeaux.");
                }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
