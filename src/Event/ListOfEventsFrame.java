/*
 * 
 */
package Event;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.*;
import javax.swing.table.*;

import Background.Background;
import Main.BDD_v2;
import Main.ImageUtility;
import Main.MenuPrincipal;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfEventsFrame.
 */
public class ListOfEventsFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The event table. */
	private JTable eventTable;
	/** The event combo box. */
    private JComboBox<String> eventComboBox;
	
	/** The parent frame. */
	private MenuPrincipal parentFrame; // R�f�rence � la fen�tre ListOfEventsFrame
	private static final String CONFIG_DIR = "Config"+File.separator;

	/**
	 * Instantiates a new list of events frame.
	 *
	 * @param menuPrincipal the menu principal
	 * @throws SQLException the SQL exception
	 */
	public ListOfEventsFrame(MenuPrincipal menuPrincipal) throws SQLException {
		this.parentFrame = menuPrincipal;
		// Initialisation de la fen�tre
        setTitle("List of Events");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);  
        ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }

        // Cr�ez un mod�le de table
        String[] columnNames = {"Event name", "Background name", "Background pictures 1", "Background pictures 2", "Background pictures 3", "Background pictures 4", "waiting Background"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			@Override
            public Class<?> getColumnClass(int column) {
                if (column == 2 || column == 3 || column == 4 || column == 5 || column == 6) {
                    return ImageUtility.class; // La colonne des images utilise la classe ImageUtility
                } else {
                    return Object.class; // Les autres colonnes sont de type Object
                }
            }
        };
        // Cr�ez un JTable avec le mod�le de table
        eventTable = new JTable(tableModel);
        // D�finir la hauteur des lignes � 80 pixels
        eventTable.setRowHeight(85);
        // Cr�ation d'un rendu personnalis� pour afficher les images
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
        // Appliquez le rendu personnalis� � la colonne des images
        eventTable.getColumnModel().getColumn(2).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(3).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(4).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(5).setCellRenderer(imageRenderer);
        eventTable.getColumnModel().getColumn(6).setCellRenderer(imageRenderer);
        // R�cup�rez les donn�es de la base de donn�es
        String[][] eventData = BDD_v2.DataEvent();
       
		
		for (String[] data : eventData) {
			if (data[2] != null) {
				// Ajouter les donn�es � la table, y compris l'image
				ImageUtility imageRezize[] = new ImageUtility[5];
				for (int i = 2; i <= 6; i++)
					imageRezize[i - 2] = new ImageUtility(data[i], 80);
				// Ajoutez les donn�es � la table
				System.out.println("++++ evenement : "+data[0]+", background : "+data[1]);
				tableModel.addRow(new Object[] { data[0], data[1], imageRezize[0], imageRezize[1], imageRezize[2], imageRezize[3], imageRezize[4] });
			} else {
				System.out.println("---- probleme background");
				tableModel.addRow(new Object[] { data[0]});
			}
		}
		
		// Cr�ez un JPanel pour les boutons
		JPanel buttonPanel = new JPanel();
		JButton newButton = new JButton("New");
		JButton modifyButton = new JButton("Modify");
		JButton deleteButton = new JButton("Delete");
		// Cr�ez un JPanel pour les boutons export
		JPanel buttonPanelExport = new JPanel();
		eventComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.getNamesFromDatabase("event")));
		eventComboBox.setSelectedIndex(-1);
		JButton exportButton = new JButton("Export");
		JButton importButton = new JButton("Import");
		
		// Ajoutez les boutons au panneau
		buttonPanel.add(newButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(deleteButton);
		// Ajoutez les boutons au panneau export
		buttonPanelExport.add(eventComboBox);
		buttonPanelExport.add(exportButton);
		buttonPanelExport.add(importButton);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

		// Ajoutez le JPanel des boutons et du champ de recherche en haut au milieu
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;  // Pour  tirer le panneau et occuper tout l'espace disponible
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(buttonPanelExport, gbc);

		// Ajoutez le panneau de boutons en haut de la fen�tre
		add(mainPanel, BorderLayout.NORTH);
		

        // Ajout du JTable � la fen�tre
        JScrollPane scrollPane = new JScrollPane(eventTable);
        add(scrollPane);

        // Rendez la fen�tre visible
        setVisible(true);
        
        modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = eventTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String eventName = (String) eventTable.getValueAt(selectedRow, 0);
					String backgroundName = (String) eventTable.getValueAt(selectedRow, 1);
					// Ouvrir une fen�tre de modification avec ces donn�es
					try {
						new ModifyEventFrame(ListOfEventsFrame.this, eventName, backgroundName);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = eventTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String eventName = (String) eventTable.getValueAt(selectedRow, 0);
					try {
						BDD_v2.deleteEvent(eventName);
						refreshData();
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//a modifier pour event
				}
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new newEventFrame(ListOfEventsFrame.this);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File zipFile = new File("");
				String eventName = eventComboBox.getSelectedItem().toString();
				// Demande à l'utilisateur où enregistrer le fichier ZIP
				if(eventComboBox.getSelectedItem().toString() != null)
					zipFile = askUserForSaveLocation(eventName+".zip");

		        if (zipFile != null) {
		            try {
						createZipFile(eventName, zipFile);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		});
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File zipFile = askUserForZipFile();

		        if (zipFile != null) {
		            try {
						importZip(zipFile);
						refreshData();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		});
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Code � ex�cuter lorsque la fen�tre est ferm�e
                // Par exemple, appeler la m�thode pour rafra�chir la fen�tre principale
            	parentFrame.refreshEventComboBox();
            }
        });
	}
	
	/**
	 * Refresh data.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void refreshData() throws SQLException {
		// Effacez les donn�es actuelles de la table
        DefaultTableModel model = (DefaultTableModel) eventTable.getModel();
        model.setRowCount(0);

        // Rechargez les donn�es depuis la base de donn�es et mettez � jour la table
        String[][] eventData = BDD_v2.DataEvent();

        for (String[] data : eventData) {
            if (data[2] != null) {
                // Ajouter les donn�es � la table, y compris l'image
				ImageUtility imageRezize[] = new ImageUtility[5];
                for (int i = 2; i <= 6; i++)
                    imageRezize[i - 2] = new ImageUtility(data[i], 80);
                // Ajoutez les donn�es � la table
                System.out.println("++++ evenement : "+data[0]+", background : "+data[1]);

                model.addRow(new Object[]{data[0], data[1], imageRezize[0], imageRezize[1], imageRezize[2], imageRezize[3], imageRezize[4]});
            } else {
                System.out.println("---- probleme background");
                model.addRow(new Object[]{data[0]});
            }
        }
        this.parentFrame.refreshEventComboBox();
    }
	
	/**
     * Crée un fichier ZIP à partir d'une liste de fichiers JSON.
     * @param jsonFiles Liste des fichiers JSON à compresser.
     * @param zipFile Fichier ZIP de destination.
	 * @throws SQLException 
     */
    public static void createZipFile(String eventName, File zipFile) throws SQLException {
    	List<File> jsonFiles= new ArrayList<File>();
    	File globalsettingFile = new File("config"+File.separator+"globalSettings.json");
    	File directory = new File("config"+File.separator+eventName);
    	if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                for (File file : files) {
                    jsonFiles.add(file);
                }
            }
        } else {
            System.err.println("Le dossier n'existe pas ou n'est pas valide : " + directory.getAbsolutePath());
        }
    	
    	List<File> imageFiles= new ArrayList<File>();
    	Evenement selectedEvent = BDD_v2.getEvenementByName(eventName);
		imageFiles.add(new File(selectedEvent.getBackground().getImage_1()));
		imageFiles.add(new File(selectedEvent.getBackground().getImage_2()));
		imageFiles.add(new File(selectedEvent.getBackground().getImage_3()));
		imageFiles.add(new File(selectedEvent.getBackground().getImage_4()));
		imageFiles.add(new File(selectedEvent.getBackground().getImage_5()));
			
    	try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            
            // Ajouter les fichiers JSON à la racine du ZIP
            for (File file : jsonFiles) {
                addFileToZip(zipOut, file,selectedEvent.getNom()+File.separator+ file.getName());
            }
            addFileToZip(zipOut, globalsettingFile, globalsettingFile.getName());

            // Ajouter les images dans un dossier "background"
            
            for (int i=0; i<imageFiles.size();i++) {
                addFileToZip(zipOut, imageFiles.get(i), "background_"+selectedEvent.getBackground().getNom()+File.separator+i+"_" +imageFiles.get(i).getName());
            }

            System.out.println("Fichier ZIP créé : " + zipFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier ZIP : " + e.getMessage());
        }

    }
    
    private static void addFileToZip(ZipOutputStream zipOut, File file, String zipPath) throws IOException {
        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(zipPath);
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }
                zipOut.closeEntry();
                System.out.println("Ajouté : " + zipPath);
            }
        } else {
            System.err.println("Fichier introuvable : " + file.getAbsolutePath());
        }
    }

    /**
     * Demande à l'utilisateur où enregistrer le fichier ZIP via un JFileChooser.
     * @param defaultZipName Nom par défaut du fichier ZIP.
     * @return File représentant l'emplacement choisi, ou null si l'utilisateur annule.
     */
    public static File askUserForSaveLocation(String defaultZipName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisissez l'emplacement de sauvegarde");
        fileChooser.setSelectedFile(new File(defaultZipName)); // Propose un nom par défaut

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Ajoute ".zip" si l'utilisateur n'a pas mis d'extension
            if (!selectedFile.getName().toLowerCase().endsWith(".zip")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".zip");
            }

            return selectedFile;
        } else {
            System.out.println("Enregistrement annulé par l'utilisateur.");
            return null;
        }
    }
    
    /**
     * Importe un fichier ZIP en extrayant et traitant son contenu.
     * @param zipFile Fichier ZIP à importer.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public static void importZip(File zipFile) throws ClassNotFoundException, SQLException {
        try {
            Path tempDir = Files.createTempDirectory("zip_extract_"); // Crée un dossier temporaire
            unzipFile(zipFile, tempDir); // Extraction du ZIP

            File extractedFolder = tempDir.toFile();
//            System.out.println("path extracted folder : "+extractedFolder);
            // Récupérer le vrai dossier d'extraction (évite le dossier intermédiaire)
            File extractedFolder2 = getRealExtractedFolder(tempDir.toFile());
//            System.out.println("path real extracted folder : "+extractedFolder2);
            Background backgrounImport = processBackgroundFolder(extractedFolder2); // Vérifie et traite le background

            if (backgrounImport == null) {
                System.err.println("ERREUR : Aucun dossier 'background_XYZ' trouvé. Import annulé.");
                return;
            }

            // Traitement des autres fichiers après le background
            processExtractedFiles(extractedFolder2,backgrounImport);

            System.out.println("Importation du ZIP terminée !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'importation du ZIP : " + e.getMessage());
        }
    }
    /**
     * Trouve le dossier principal extrait (évite un dossier intermédiaire inutile)
     */
    private static File getRealExtractedFolder(File tempExtractedFolder) {
        File[] files = tempExtractedFolder.listFiles();
        if (files != null && files.length == 1 && files[0].isDirectory()) {
            return files[0]; //Retourne le dossier unique (ex: eventExample/)
        }
        return tempExtractedFolder; //Sinon, retourne le dossier temporaire de base
    }


    /**
     * Décompresse un fichier ZIP dans un dossier temporaire.
     * @param zipFile Le fichier ZIP à extraire.
     * @param outputDir Dossier de destination des fichiers extraits.
     */
    private static void unzipFile(File zipFile, Path outputDir) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File extractedFile = new File(outputDir.toFile(), entry.getName());

                if (entry.isDirectory()) {
                    extractedFile.mkdirs();
                } else {
                    extractedFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(extractedFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipIn.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipIn.closeEntry();
            }
        }
    }
    /**
     * Vérifie et traite en premier le dossier 'background_XYZ'.
     * @param extractedFolder Dossier temporaire contenant les fichiers extraits.
     * @return `true` si un dossier 'background_XYZ' est trouvé, sinon `false`.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    private static Background processBackgroundFolder(File extractedFolder) throws ClassNotFoundException, SQLException {
        File[] files = extractedFolder.listFiles();
        if (files == null) return null;

        for (File file : files) {
        	if(file.isDirectory() && file.getName().split("_").length > 1 && file.getName().split("_")[0].equals("background")) {
//        	System.out.println("file dans temp : "+file.getName());
//        	if() {
//            	System.out.println("background : "+file.getName());
//            	System.out.println("background split : "+file.getName().split("_")[0]);
//            	if () {
//            		System.out.println("background size : "+file.getName());
            		
                String backgroundName = file.getName().substring(11); // Récupère "XYZ"
                System.out.println("Background trouvé : " + backgroundName);
                Background importBackground = new Background(backgroundName);

                File[] imageFiles = file.listFiles();
//                for (File fileimg : imageFiles) {
//					System.out.println("img dans background : "+fileimg);
//				}
                
                if (imageFiles != null) {
                    System.out.println("Images dans " + file.getName() + " :");
           
                    File destImg = new File("Background"+File.separator);
                    for (File fileImagesBg : imageFiles) {
						copyFolder(fileImagesBg, destImg);
						String indexImg = fileImagesBg.getName().split("_")[0];
						String nameImg = fileImagesBg.getName().split("_")[1];
						System.out.println("   - index img : "+indexImg+", name img : "+nameImg);
						switch (indexImg) {
						case "0":
							importBackground.setImage_1("Background"+File.separator+nameImg);
							break;
						case "1":
							importBackground.setImage_2("Background"+File.separator+nameImg);
							break;
						case "2":
							importBackground.setImage_3("Background"+File.separator+nameImg);
							break;
						case "3":
							importBackground.setImage_4("Background"+File.separator+nameImg);
							break;
						case "4":
							importBackground.setImage_5("Background"+File.separator+nameImg);
							break;

						default:
							break;
						}
					}
                }
                BDD_v2.insertionBackgroundDansBDD(importBackground);
                return importBackground; // Un dossier "background_XYZ" a bien été trouvé
            }
            
        }
        return null; // Aucun dossier "background_XYZ" trouvé
    }

    /**
     * Traite les autres fichiers extraits après le dossier background.
     * @param extractedFolder Le dossier temporaire contenant les fichiers extraits.
     * @param backgrounImport 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    private static void processExtractedFiles(File extractedFolder, Background backgrounImport) throws ClassNotFoundException, SQLException {
        File[] files = extractedFolder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String folderName = file.getName();
                if (!folderName.startsWith("background_")) {
                    // 📌 Copier le dossier dans "config/"
                    File destFolder = new File(CONFIG_DIR, folderName);
                    copyFolder(file, destFolder);
                    System.out.println("Copie du dossier " + folderName + " vers config/");
                    Evenement importEvent = new Evenement(folderName);
                    importEvent.setBackground(backgrounImport);
                    BDD_v2.insertionEventDansBDD(importEvent);
                }
            } else if (file.getName().equals("globalsetting.json")) {
                // 📌 Remplacer "globalsetting.json" dans "config/"
                File destFile = new File(CONFIG_DIR, "globalsetting.json");
                copyFile(file, destFile);
                System.out.println("globalsetting.json mis à jour.");
            }
        }
    }

    /**
     * Copie un fichier source vers un fichier destination.
     * @param source Fichier source.
     * @param destination Fichier destination.
     */
    private static void copyFile(File source, File destination) {
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier copié : " + source.getName());
        } catch (IOException e) {
            System.err.println(" Erreur de copie du fichier " + source.getName() + " : " + e.getMessage());
        }
    }

    /**
     * Copie un dossier et son contenu récursivement.
     * @param sourceDir Dossier source.
     * @param destinationDir Dossier destination.
     */
    private static void copyFolder(File sourceDir, File destinationDir) {
        if (!sourceDir.exists()) return;
        if (!destinationDir.exists()) destinationDir.mkdirs();

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File newFile = new File(destinationDir, file.getName());
                if (file.isDirectory()) {
                    copyFolder(file, newFile);
                } else {
                    copyFile(file, newFile);
                }
            }
        }
    }

    /**
     * Demande à l'utilisateur de sélectionner un fichier ZIP à importer.
     * @return Le fichier sélectionné ou null si l'utilisateur annule.
     */
    public static File askUserForZipFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionnez un fichier ZIP");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers ZIP", "zip"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
        	System.out.println("selected file : "+fileChooser.getSelectedFile().getName());
            return fileChooser.getSelectedFile();
        } else {
            System.out.println("Importation annulée.");
            return null;
        }
    }
}