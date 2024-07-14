package Diffusion;

/*
 * fenetre permetant de choisir les joueur selectionne 
 * et les actions a afficher
 */

import Players.Joueur;
import Police.TabPolice;

import javax.swing.*;
import javax.swing.border.Border;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import Event.Evenement;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class WindowTournamentTree extends JFrame {
	private static final long serialVersionUID = 1L;

	private ArrayList<Joueur> selectedJoueurs;
	private WindowBroadcastPublic windowBroadcastPublic;
	private Evenement event;
	public WindowConfigurationPlayerInfos windowConfigPlayer;
	private PlayerForDiffusion[] tabPlayerForTree;
	private int i;
	private int nbJoueur;
	private JPanel[] playerPanel;// = new JPanel();
	private boolean blackButtonAppuyer = false;
	private boolean fondButtonAppuyer;
	
	
	public WindowTournamentTree(ArrayList<Joueur> selectedJoueurs, Evenement event, WindowBroadcastPublic diffusionFrame, int nbJoueur) {
		setTitle("Broadcast configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 800);
		ImageIcon logoIcon = new ImageIcon("icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }
        this.nbJoueur = nbJoueur;
		this.selectedJoueurs = selectedJoueurs;
		this.windowBroadcastPublic =diffusionFrame;
		this.event = event;
		this.tabPlayerForTree = new PlayerForDiffusion[nbJoueur];
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	windowBroadcastPublic.close();
            }
        });
		 playerPanel = new JPanel[4];
		
		Border contour = BorderFactory.createLineBorder(Color.black);
		// Créez un conteneur pour les sections supérieures
		JPanel topSectionsPanel = new JPanel();
		topSectionsPanel.setLayout(new GridLayout(1, 2));

		// Créez un conteneur pour chaque section supérieure
		JPanel topLeftPanel = createSectionPanel("En haut gauche",0);
		JPanel topRightPanel = createSectionPanel("En haut droite",2);
		topLeftPanel.setBorder(contour);
		topRightPanel.setBorder(contour);
		// Ajoutez les sections supérieures é leur conteneur
		topSectionsPanel.add(topLeftPanel);
		topSectionsPanel.add(topRightPanel);

		// Créez un conteneur pour les sections inférieures
		JPanel bottomSectionsPanel = new JPanel();
		bottomSectionsPanel.setLayout(new GridLayout(1, 2));

		// Créez un conteneur pour chaque section inférieure
		JPanel bottomLeftPanel = createSectionPanel("En bas gauche",1);
		JPanel bottomRightPanel = createSectionPanel("En bas droite",3);
		bottomLeftPanel.setBorder(contour);
		bottomRightPanel.setBorder(contour);
		// Ajoutez les sections inférieures é leur conteneur
		bottomSectionsPanel.add(bottomLeftPanel);
		bottomSectionsPanel.add(bottomRightPanel);

		// Ajoutez les conteneurs des sections é votre fenétre
		setLayout(new BorderLayout());
		add(topSectionsPanel, BorderLayout.NORTH);
		add(bottomSectionsPanel, BorderLayout.CENTER);

		// Créez un panneau pour les éléments en bas
		JPanel bottomPanel = new JPanel();
		// Ajoutez ici les éléments que vous souhaitez afficher en bas
		JButton playerButton = new JButton("Full Competition");
		playerButton.addActionListener(e -> {
			windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_4());
			for(int u=0; u<tabPlayerForTree.length;u++) {
				if(tabPlayerForTree[u] != null)
					System.out.println("player from tab tree index : "+u+" name :"+tabPlayerForTree[u].getJoueur().getNom());
			}
			// cr�ation d'une liste de PlayerForDiffusion pour aficher le tournoi complet
			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
			windowBroadcastPublic.removeLayerContent(JLayeredPane.PALETTE_LAYER);//nettoyage du layer
			for (int y = 0; i < playerPanel.length-1; y++) {
				if(y==4)break;
				for (int i = 0; i < nbJoueur/4; i++) {
					Joueur Player = foundPlayer(getSelectedPlayerName(playerPanel[y], i));
					PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "full", (nbJoueur/4)*y + i);
					int ligne = (nbJoueur/4)*y+i+1;
					if (Player != null) {
						try {
							PlayerDetails.setPlayer(Player, ligne);
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
						ListSelectedJoueur.add(PlayerDetails);
					}
				}
			}
			if (windowConfigPlayer == null) {
				windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "full");
//					placementFrameTwoPlayer.setTabPolice(new TabPolice(ListSelectedJoueur));
			} else {
				windowConfigPlayer.tabbedPane.removeAll();
				windowConfigPlayer.tabbedPane.revalidate();
				windowConfigPlayer.tabbedPane.repaint();
				windowConfigPlayer.setTypeFenetre("full");
			}
			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, playerForDiffusion);
				playerForDiffusion.setPlacementFrameTwoPlayer(windowConfigPlayer);
				TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(playerForDiffusion, playerForDiffusion.getJoueur(), windowBroadcastPublic, windowConfigPlayer);
				windowConfigPlayer.addTabJoueur(tabFull);
				System.out.println("    FULL player to disply : "+playerForDiffusion.nameLabel.getText());
			}
			if(ListSelectedJoueur.size() != 0) {
				windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
				windowConfigPlayer.pack();
			}
		});
		bottomPanel.add(playerButton);
		
		JButton fondButton = new JButton("background");
		fondButton.addActionListener(e -> {
			if(fondButtonAppuyer == false)
				windowBroadcastPublic.setBackgroundImageLayered(event.getBackground().getImage_5(), JLayeredPane.POPUP_LAYER);
			else
				windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
			fondButtonAppuyer = !fondButtonAppuyer;
		});
		bottomPanel.add(fondButton);
		
		JButton blackButton = new JButton("Black");
		blackButton.addActionListener(e -> {
			if(blackButtonAppuyer == false)
				windowBroadcastPublic.setBackgroundImageLayered("black.jpg", JLayeredPane.POPUP_LAYER);
			else
				windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
			blackButtonAppuyer = !blackButtonAppuyer;
		});
		bottomPanel.add(blackButton);

		// Ajoutez le panneau des éléments en bas é la fenétre
		add(bottomPanel, BorderLayout.SOUTH);
		this.pack();
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (windowConfigPlayer != null) {
					windowConfigPlayer.dispose();
				}
			}
		});
	}

	private JPanel createSectionPanel(String sectionName, int indexPanel) {//index panel = 0 1 2 ou 3 permet de savoir dans quel panel on ce trouve pour les ligne
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		// Créez un JPanel pour contenir les JComboBox de maniére verticale
		playerPanel[indexPanel] = new JPanel();
		playerPanel[indexPanel].setLayout(new BoxLayout(playerPanel[indexPanel], BoxLayout.Y_AXIS));

		// Créez 8 JComboBox pour cette section
		for (i = 0; i < (nbJoueur/4); i++) {
			JComboBox<String> comboBox = new JComboBox<>();
			comboBox.setEditable(true);
			int Indexbutton =  i+1;
			// Utilisez AutoCompleteDecorator pour activer la fonction de filtrage automatique
			AutoCompleteDecorator.decorate(comboBox);

			JButton playerButton = new JButton("Player");
            playerButton.addActionListener(e -> {
                String selectedItem = (String) comboBox.getSelectedItem();
                if (selectedItem != null) {
                	//windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1()); // changer le fond de la fenetre
                	try {
                		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
                		Joueur soloPlayer = foundPlayer(selectedItem);
                		int ligne = Indexbutton + (nbJoueur/4)*indexPanel;
                		//ajout du player dans le tableau pour l'arbre de tournoi
                		PlayerForDiffusion playerDetailsForTree = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "full",ligne-1);
                		playerDetailsForTree.setPlayer(soloPlayer, ligne);
//                		playerDetailsForTree.setVisible(false);
                		playerDetailsForTree.setPlacementFrameTwoPlayer(windowConfigPlayer);
                		tabPlayerForTree[ligne-1] = playerDetailsForTree;
                		//initialisation et affichage de player solo
                		PlayerForDiffusion soloPlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "player",ligne-1);
                		soloPlayerDetails.setPlayer(soloPlayer, ligne);
                		ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
                		ListSelectedJoueur.add(soloPlayerDetails);
                		
                		if (windowConfigPlayer == null) {
        					windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "player");
        				} else {
        					windowConfigPlayer.tabbedPane.removeAll();
        					windowConfigPlayer.tabbedPane.revalidate();
        					windowConfigPlayer.tabbedPane.repaint();
        					windowConfigPlayer.setTypeFenetre("player");
        				}
                		this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne-1]);
                		this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, soloPlayerDetails);
                		soloPlayerDetails.setPlacementFrameTwoPlayer(windowConfigPlayer);
                		TabConfigurationPlayerInfos tabOnePlayer = new TabConfigurationPlayerInfos(soloPlayerDetails, soloPlayer, windowBroadcastPublic,windowConfigPlayer);
						windowConfigPlayer.addTabJoueur(tabOnePlayer);
						windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
						System.out.println("    SOLO player to dislay : "+soloPlayerDetails.nameLabel.getText());
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                }
//                placementFrameTwoPlayer.refreshAllTab();
                windowConfigPlayer.pack();
            });

			// Ajoutez les éléments de la liste aux JComboBox
			for (Joueur joueur : selectedJoueurs) {
				comboBox.addItem(joueur.getDisplay_name());
			}
			comboBox.setSelectedIndex(-1);

			JPanel comboButtonPanel = new JPanel();
			comboButtonPanel.add(comboBox);
			comboButtonPanel.add(playerButton);
			playerPanel[indexPanel].add(comboButtonPanel);
		}

		// Créez 4 boutons "Game" pour cette section
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < (nbJoueur/8); i++) {
			int buttonIndex = i; // Capture the current value of i
			JButton gameButton = new JButton("Game");
			gameButton.addActionListener(e -> {
				// Retrieve the selected players from the corresponding section
				int playerIndex1 = buttonIndex * 2; // Calculate the index of the first player
				int playerIndex2 = playerIndex1 + 1; // Calculate the index of the second player
				windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_2());
				windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
				// Check if the indices are within bounds before accessing the list
				System.out.println("++++> index Button: " + buttonIndex + ", index P1: " + playerIndex1 + ", index P2: " + playerIndex2);
				if (playerIndex1 < selectedJoueurs.size() && playerIndex2 < selectedJoueurs.size()) {
					Joueur Player1 = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], playerIndex1));
					Joueur Player2 = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], playerIndex2));
					PlayerForDiffusion PlayerDetails1 = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "game",0);
					PlayerForDiffusion PlayerDetails2 = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "game",1);
					int ligne1 = (2*buttonIndex+1)+((nbJoueur/4)*indexPanel);
					int ligne2 = ((2*buttonIndex+1)+((nbJoueur/4)*indexPanel)+1);
					try {
						PlayerDetails1.setPlayer(Player1, ligne1);
						PlayerDetails2.setPlayer(Player2, ligne2);
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
					ListSelectedJoueur.add(PlayerDetails1);
					ListSelectedJoueur.add(PlayerDetails2);
					//Si la fenetre est null on la creer
					if(windowConfigPlayer == null) {
						windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "game");
					}else {
						windowConfigPlayer.tabbedPane.removeAll();
						windowConfigPlayer.tabbedPane.revalidate();
						windowConfigPlayer.tabbedPane.repaint();
						windowConfigPlayer.setTypeFenetre("game");
					}
					this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, PlayerDetails1);
					this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, PlayerDetails2);
					PlayerDetails1.setPlacementFrameTwoPlayer(windowConfigPlayer);
					PlayerDetails2.setPlacementFrameTwoPlayer(windowConfigPlayer);
					TabConfigurationPlayerInfos tabP1 = new TabConfigurationPlayerInfos(PlayerDetails1, Player1, windowBroadcastPublic, windowConfigPlayer);
					TabConfigurationPlayerInfos tabP2 = new TabConfigurationPlayerInfos(PlayerDetails2, Player2, windowBroadcastPublic, windowConfigPlayer);
					windowConfigPlayer.addTabJoueur(tabP1);
					windowConfigPlayer.addTabJoueur(tabP2);
					windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
					System.out.println("    GAME player to display : "+PlayerDetails1.nameLabel.getText()+" "+PlayerDetails1.getNumeroPlayer()+" VS "+PlayerDetails2.nameLabel.getText());
					
				} else
					System.out.println("    ERROR find the to player for game");
				windowConfigPlayer.pack();
			});
	        gamePanel.add(gameButton);
	    }

		// Créez un bouton "Tab 1 -> 8" pour cette section
		JButton tabButton = new JButton("Tab");
		tabButton.addActionListener(e -> {
			windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_3());
//			System.out.println("++++> index panel tab : " + indexPanel);
			// cr�ation d'une liste de PlayerForDiffusion pour aficher les pool
			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
			windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
			for (int i = 0; i < (nbJoueur / 4); i++) {
				//System.out.println((nbJoueur / 4) * indexPanel + i);
				Joueur Player = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], i));
				PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "tab", i);
				int ligne = (nbJoueur / 4) * indexPanel + i + 1;
				try {
					PlayerDetails.setPlayer(Player, ligne);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ListSelectedJoueur.add(PlayerDetails);
			}
			if (windowConfigPlayer == null) {
				windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "tab");
			} else {
				windowConfigPlayer.tabbedPane.removeAll();
				windowConfigPlayer.tabbedPane.revalidate();
				windowConfigPlayer.tabbedPane.repaint();
				windowConfigPlayer.setTypeFenetre("tab");
			}
			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDiffusion);
				playerForDiffusion.setPlacementFrameTwoPlayer(windowConfigPlayer);
				TabConfigurationPlayerInfos tabPool = new TabConfigurationPlayerInfos(playerForDiffusion, playerForDiffusion.getJoueur(), windowBroadcastPublic, windowConfigPlayer);
				windowConfigPlayer.addTabJoueur(tabPool);
				System.out.println("    TAB player to display  : "+playerForDiffusion.nameLabel.getText());
			}	
			windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
			windowConfigPlayer.pack();
		});
		// Ajoutez le panel des joueurs, le panel des jeux et le bouton "Tab 1 -> 8" au panel principal
		panel.add(playerPanel[indexPanel]);
		panel.add(gamePanel);
		panel.add(tabButton);

		return panel;
	}

	private Joueur foundPlayer(String nomJoueur) {
		for (Joueur joueur : selectedJoueurs) {
	        if (joueur.getDisplay_name().equals(nomJoueur)) {
	            return joueur;
	        }
	    }
		return null;
	}
	public String eventName() {
		return this.event.getNom();
	}
	// Helper method to get the selected player name from a specific panel
	private String getSelectedPlayerName(JPanel panel, int playerIndex) {
	    Component[] components = panel.getComponents();
	    if (playerIndex < components.length && components[playerIndex] instanceof JPanel) {
	        JPanel comboButtonPanel = (JPanel) components[playerIndex];
	        Component[] subComponents = comboButtonPanel.getComponents();
	        if (subComponents.length == 2 && subComponents[0] instanceof JComboBox) {
	            @SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) subComponents[0];
	            return (String) comboBox.getSelectedItem();
	        }
	    }
	    return null;
	}

	public PlayerForDiffusion[] getTabPlayerForTree() {
		return tabPlayerForTree;
	}
}
