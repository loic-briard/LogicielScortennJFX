package Main;
/*
 * fenetre d'accueil avec :
 * - modification des evenements
 * - modification des background
 * - modification des joueurs enregistrer
 * - selection de l'evenement
 * - selection de la liste de joueur 
 * - selection du nombre de joueur participant au tournoi
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import Background.ListOfBackgroundFrame;
import Diffusion.WindowTournamentTree;
import Diffusion.WindowBroadcastPublic;
import Event.Evenement;
import Event.ListOfEventsFrame;
import Flags.ListOfFlag;
import Players.Joueur;
import Players.ListOfPlayersFrame;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private AthleteSelection athleteSelection; // Assurez-vous que cette r�f�rence est initialis�e correctement

 // Gardez une r�f�rence aux fen�tres correspondantes
    private JFrame listEventsWindow;
    private JFrame listBGWindow;
    private JFrame listPlayersWindow;
    private JFrame flagsWindow;
    private  String actualScreen = new String();

	private JComboBox<String> eventComboBox;
	private static JComboBox<String> bddPLayersComboBox;

	public MenuPrincipal() {
        // Creez la fenetre principale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        // Initialisation de la fen�tre
    	setTitle("Main Menu");
    	ImageIcon logoIcon = new ImageIcon("icon.png");
        // Verifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'icone n'a pas pu etre chargee, affichez un message d'erreur
            System.err.println("- Impossible de charger l'icone.");
        }
        
        

        // Creez un menu-------------------------------------------------------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();
        // Menu "File"
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Menu "Event"
		JMenuItem listEventMenuItem = new JMenuItem("Events");
		listEventMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listEventsWindow == null || !listEventsWindow.isVisible()) {
	                try {
	                	listEventsWindow = new ListOfEventsFrame(MenuPrincipal.this);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					listEventsWindow.toFront();
					listEventsWindow.setState(JFrame.NORMAL);					
				}
			}
		});
		menuBar.add(listEventMenuItem);
        
        //list of background
		JMenuItem listBGMenuItem = new JMenuItem("Background");
        listBGMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (listBGWindow == null || !listBGWindow.isVisible()) {
            		try {
            			listBGWindow = new ListOfBackgroundFrame();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}else {
            		listBGWindow.toFront();
            		listBGWindow.setState(JFrame.NORMAL);
            	}
            }
        });
        menuBar.add(listBGMenuItem);
        
        //List of players
        JMenuItem listPlayersMenuItem = new JMenuItem("Players");
		listPlayersMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPlayersWindow == null || !listPlayersWindow.isVisible()) {
					try {
						listPlayersWindow = new ListOfPlayersFrame();
					} catch (SQLException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					listPlayersWindow.toFront();
					listPlayersWindow.setState(JFrame.NORMAL);
				}
			}
		});
		menuBar.add(listPlayersMenuItem);
		
        //flags
		JMenuItem flagsMenuItem = new JMenuItem("Flags");
		flagsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flagsWindow == null || !flagsWindow.isVisible()) {
					try {
						flagsWindow = new ListOfFlag();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					flagsWindow.toFront();
					flagsWindow.setState(JFrame.NORMAL);
				}
			}
		});
		menuBar.add(flagsMenuItem);
		
		// Ajoutez le menuBar a la JFrame dans la region PAGE_START
        setLayout(new BorderLayout());
        add(menuBar, BorderLayout.PAGE_START);
        
		// Fin du menu-------------------------------------------------------------------------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    BDD_v2.deconnexionBDD();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // elements du menu principal ---------------------------------------------------------------------------------------------------------------------------------
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Mise en page pour centrer les composants
        
        // Creez le label "Select an event" au centre
        JLabel selectEventLabel = new JLabel("Select an event");
        selectEventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //menu deroulant de tout les events
        eventComboBox = new JComboBox<>(BDD_v2.getNamesFromDatabase("event"));
        refreshEventComboBox();
        // Ajoutez un ecouteur d'evenements au JComboBox pour gerer la selection
        eventComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // R�cup�rez l'�l�ment s�lectionn�
                String selectedEvent = (String) eventComboBox.getSelectedItem();
                // Faites ce que vous voulez avec l'�v�nement s�lectionn�
                System.out.println("++ Event selected: " + selectedEvent);
            }
        });
        eventComboBox.setSelectedIndex(-1);
        
        Integer[] allowedValues = {8, 16, 32, 64};
        SpinnerListModel spinnerInitNbJoueur = new SpinnerListModel(allowedValues);
        JSpinner spinnerNbJoueur = new JSpinner(spinnerInitNbJoueur);
        spinnerNbJoueur.addChangeListener(e -> {
        	spinnerNbJoueur.revalidate();
        	spinnerNbJoueur.repaint();
        });
        //menu deroulant des bdd des joueurs
        bddPLayersComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
        // Ajoutez un �couteur d'�v�nements au JComboBox pour g�rer la s�lection
        bddPLayersComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // R�cup�rez l'�l�ment s�lectionn�
                String selectedEventBDD = (String) bddPLayersComboBox.getSelectedItem();
                // Faites ce que vous voulez avec l'�v�nement s�lectionn�
                System.out.println("++ BDD selected: " + selectedEventBDD);
            }
        });
        bddPLayersComboBox.setSelectedIndex(-1);
        
        JButton selectAthleteButton = new JButton("Athlete select");
        JButton diffusionButton = new JButton("For diffusion");
        
        JLabel actualDisplayLabel = new JLabel();
        this.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentMoved(ComponentEvent e) {
        		Point location = getLocation();
        		// Obtenez tous les �crans disponibles
        		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        		
        		for (GraphicsDevice screen : screens) {
        			Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        			if (bounds.contains(location)) {
        				//System.out.println("La fen�tre est sur l'�cran : " + screen.getIDstring());
        				actualScreen=screen.getIDstring();
        				actualDisplayLabel.setText("Actuel screen : "+actualScreen);
        				revalidate();
        				repaint();
        				break;
        			}
        		}
        	}
        });
        Point location = getLocation();
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        
        for (GraphicsDevice screen : screens) {
        	Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        	if (bounds.contains(location)) {
        		//System.out.println("La fen�tre est sur l'�cran : " + screen.getIDstring());
        		actualScreen=screen.getIDstring();
        		actualDisplayLabel.setText("Actuel screen : "+actualScreen);
        		revalidate();
        		repaint();
        		break;
        	}
        }
        // Obtenez tous les �crans disponibles
        SpinnerListModel spinnerInitScreen = new SpinnerListModel(screens);
        JSpinner spinnerScreen = new JSpinner(spinnerInitScreen);
        JPanel choixEcran = new JPanel();
        choixEcran.add(actualDisplayLabel);
        choixEcran.add(new JLabel(", choose the screen to display the tournament : "));
        choixEcran.add(spinnerScreen);
        
        selectAthleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					athleteSelection = new AthleteSelection((String) bddPLayersComboBox.getSelectedItem());
				} catch (SQLException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        diffusionButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Appelez la m�thode getSelectedPlayers() pour r�cup�rer les joueurs s�lectionn�s
                ArrayList<Joueur> selectedPlayers = athleteSelection.getSelectedPlayers();
                
                // Affichez les joueurs s�lectionn�s, par exemple, dans une bo�te de dialogue
                StringBuilder playersInfo = new StringBuilder("Selected players :\n");
                for (Joueur joueur : selectedPlayers) {
                    playersInfo.append(joueur.getNom()).append(" ").append(joueur.getPrenom()).append("\n");
                }
                
                JOptionPane.showMessageDialog(null, playersInfo.toString(), "Selected players", JOptionPane.INFORMATION_MESSAGE);
                
                String selectedEvent = (String) eventComboBox.getSelectedItem();
                Evenement eventChoosen = new Evenement(selectedEvent);
				try {
					System.out.println("++ Evenement selectione : "+selectedEvent);
					eventChoosen = BDD_v2.getEvenementByName(selectedEvent);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				WindowBroadcastPublic diffusionFrame = new WindowBroadcastPublic(eventChoosen,(GraphicsDevice) spinnerScreen.getValue());
				try {
					diffusionFrame.setBackgroundImage("black.jpg");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}                
                new WindowTournamentTree(selectedPlayers,eventChoosen, diffusionFrame,(int) spinnerNbJoueur.getValue());
        	}
        });
        // Ajoutez le label et le JComboBox au JPanel
        panel.add(selectEventLabel, BorderLayout.CENTER);
        panel.add(eventComboBox, BorderLayout.EAST);
        panel.add(bddPLayersComboBox, BorderLayout.EAST);
        panel.add(selectAthleteButton, BorderLayout.EAST);
        panel.add(spinnerNbJoueur, BorderLayout.EAST);
        panel.add(diffusionButton, BorderLayout.EAST);
        
     // Cr�ation du conteneur principal avec un GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel); // Ajout du conteneur principal � la JFrame

        // Cr�ation d'un objet GridBagConstraints pour configurer l'ajout des composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Position en colonne
        gbc.gridy = 0; // Position en ligne
        gbc.anchor = GridBagConstraints.CENTER; // Alignement au centre

        // Ajout du panneau "panel" au centre de la cellule
        mainPanel.add(panel, gbc);

        // Changement de la position en ligne pour placer "choixEcran" en dessous de "panel"
        gbc.gridy = 1;
        // Ajout du panneau "choixEcran" au centre de la cellule
        mainPanel.add(choixEcran, gbc);

        setVisible(true);
    }
	
	public void refreshEventComboBox() {
	    // Obtenez � nouveau les noms d'�v�nements depuis la base de donn�es
	    String[] eventNames = BDD_v2.getNamesFromDatabase("event");
	    // Effacez tous les �l�ments actuels du JComboBox
	    if(eventComboBox != null)
	    	eventComboBox.removeAllItems();
	    // Ajoutez les nouveaux noms d'�v�nements
	    for (String eventName : eventNames) {
	        eventComboBox.addItem(eventName);
	    }
	    eventComboBox.setSelectedIndex(-1);
	}
	public static void refreshPlayerTableCombobox() throws SQLException {
		BDD_v2.getAllListPlayerTableName();
		bddPLayersComboBox.setModel(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
	}
}