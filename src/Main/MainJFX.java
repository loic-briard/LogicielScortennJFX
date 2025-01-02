/*
 * 
 */
package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatDarkLaf;

import API.GestionAPI;

// TODO: Auto-generated Javadoc
/**
 * The Class MainJFX.
 */
public class MainJFX {
	
	/** The open on eclipse. */
	public static boolean openOnEclipse = false;
	
	/** The api. */
	public static GestionAPI API = new GestionAPI();
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
    	// Obtient la classe actuelle
        Class<?> currentClass = MainJFX.class;

        // Obtient la ProtectionDomain de la classe actuelle
        ProtectionDomain protectionDomain = currentClass.getProtectionDomain();

        // Obtient la CodeSource � partir de la ProtectionDomain
        CodeSource codeSource = protectionDomain.getCodeSource();

        // v�rifie si la CodeSource est une URL
        if (codeSource != null && codeSource.getLocation() != null) {
            URL codeSourceUrl = codeSource.getLocation();

            // v�rifie si l'URL commence par "file:" (ex�cution depuis Eclipse)
            if (codeSourceUrl.getProtocol().equals("file")) {
                System.out.println("> execution from Eclipse.");
                openOnEclipse = true;
            } else {
                System.out.println("> execution from JAR file");
                // Code sp�cifique pour le traitement si le programme est ex�cut� depuis un JAR
                testFolderInJar("flag");
            }
        } else {
            System.out.println("! execution source from the program impossible !");
        } 
        // Installer FlatLaf
        try {
        	UIManager.setLookAndFeel(new FlatDarkLaf());
//        	Color colorLineBorder = new Color(150, 150, 150);
//        	Color colorBackground = new Color(40, 43, 43);
//            // Appliquer les modifications de style
//            UIManager.put("ComboBox.background", colorBackground);
//            UIManager.put("ComboBox.foreground", Color.WHITE);
//            UIManager.put("ComboBox.border", new LineBorder(colorLineBorder, 1));
//
//            UIManager.put("Button.background", colorBackground);
//            UIManager.put("Button.foreground", Color.WHITE);
//            UIManager.put("Button.border", new LineBorder(colorLineBorder, 1));
//
//            UIManager.put("Spinner.background", colorBackground);
//            UIManager.put("Spinner.foreground", Color.WHITE);
//            UIManager.put("Spinner.border", new LineBorder(colorLineBorder, 1));

        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        BDD_v2.deconnexionBDD();
		BDD_v2.connexionBDD();
		
		
//		BDD_v2.suppressionsDesTables();
//		BDD_v2.creationdesTables();
		BDD_v2.verifierEtCreerTables();
		BDD_v2.getAllListPlayerTableName();
//		API.TAB_WTA();
//		BDD_v2.insertionDrapeauDansBDD(new Drapeau("clear", "clear.png"));
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuPrincipal app = new MenuPrincipal();
                app.setVisible(true);
            }
        });
		//BDD_v2.deconnexionBDD();
	}
 
 /**
  * Test folder in jar.
  *
  * @param folderName the folder name
  * @throws IOException Signals that an I/O exception has occurred.
  */
 // M�thode pour tester si un dossier existe dans le JAR
    private static void testFolderInJar(String folderName) throws IOException {
        // Obtention du contenu du dossier depuis le JAR
        InputStream inputStream = MainJFX.class.getClassLoader().getResourceAsStream(folderName);
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("+ Contenu du dossier \"" + folderName + "\" dans le JAR :");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } else {
            System.out.println(" ! File \"" + folderName + "\" doesn't exists in JAR file");
        }
    }

}
