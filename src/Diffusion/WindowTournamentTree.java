package Diffusion;

/*
 * fenetre permetant de choisir les joueur selectionne 
 * et les actions a afficher
 */


import Players.Joueur;
import Police.TabPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Event.Evenement;

import javax.swing.*;
import javax.swing.border.Border;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class WindowTournamentTree extends JFrame {
    private static final long serialVersionUID = 1L;

    private final ArrayList<Joueur> selectedJoueurs;
    private WindowBroadcastPublic windowBroadcastPublic;
    private final Evenement event;
    public WindowConfigurationPlayerInfos windowConfigPlayer;
    public WindowConfigurationPlayerInfos windowConfigPlayerFull;
    private PanelAnimationConfiguration panelAnimationConfiguration;
    private final PlayerForDiffusion[] tabPlayerForTree;
    private final JPanel[] playerPanel;
    private boolean blackButtonAppuyer = false;
    private boolean fondButtonAppuyer = false;
    private final int nbJoueur;

	private ArrayList<PlayerForDiffusion> playerForDifusionListInit;

    public WindowTournamentTree(ArrayList<Joueur> selectedJoueurs, Evenement event, WindowBroadcastPublic diffusionFrame, int nbJoueur) throws ClassNotFoundException, SQLException {
        this.selectedJoueurs = selectedJoueurs;
        this.windowBroadcastPublic = diffusionFrame;
        this.event = event;
        this.nbJoueur = nbJoueur;
        this.tabPlayerForTree = new PlayerForDiffusion[nbJoueur];
        this.playerPanel = new JPanel[4];
        
        this.panelAnimationConfiguration = new PanelAnimationConfiguration(this);

        setupFrame();
        setupPanels();
        setupBottomPanel();
        finalizeSetup();
    }

    private void setupFrame() {
        setTitle("Broadcast configuration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("icon.png").getImage());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowBroadcastPublic.close();
                if (windowConfigPlayer != null) 
                    windowConfigPlayer.dispose();
                if (windowConfigPlayerFull != null) 
                	windowConfigPlayerFull.dispose();
//                windowBroadcastPublic.getAnimationFrame().dispose();
            }
        });
    }

    private void setupPanels() {
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
    }

    private JPanel createSectionPanel(String sectionName, int indexPanel) {
        JPanel panel = new JPanel(new GridBagLayout());
        playerPanel[indexPanel] = new JPanel();
        playerPanel[indexPanel].setLayout(new BoxLayout(playerPanel[indexPanel], BoxLayout.Y_AXIS));

        for (int i = 0; i < (nbJoueur / 4); i++) {
            addPlayerSelection(playerPanel[indexPanel], i, indexPanel);
        }

        JPanel gamePanel = createGamePanel(indexPanel);
        JButton tabButton = createTabButton(indexPanel);

        panel.add(playerPanel[indexPanel]);
        panel.add(gamePanel);
        panel.add(tabButton);

        return panel;
    }

    private void addPlayerSelection(JPanel panel, int playerIndex, int panelIndex) {
        JComboBox<String> comboBox = new JComboBox<>(selectedJoueurs.stream()
                .map(Joueur::getDisplay_name)
                .toArray(String[]::new));
        comboBox.setEditable(true);
        AutoCompleteDecorator.decorate(comboBox);
        comboBox.setSelectedIndex(-1);

        JButton playerButton = new JButton("Player");
        playerButton.addActionListener(e -> handlePlayerSelection(comboBox, playerIndex, panelIndex));

        JPanel comboButtonPanel = new JPanel();
        comboButtonPanel.add(comboBox);
        comboButtonPanel.add(playerButton);
        panel.add(comboButtonPanel);
    }

    private JPanel createGamePanel(int indexPanel) {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < (nbJoueur / 8); i++) {
            JButton gameButton = new JButton("Game");
            int buttonIndex = i;
            gameButton.addActionListener(e -> handleGameSelection(buttonIndex, indexPanel));
            gamePanel.add(gameButton);
        }
        
        return gamePanel;
    }

    private JButton createTabButton(int indexPanel) {
        JButton tabButton = new JButton("Tab");
        tabButton.addActionListener(e -> handleTabSelection(indexPanel));
        return tabButton;
    }

    private void setupBottomPanel() {
    	Border contour = BorderFactory.createLineBorder(Color.black);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel bottomPanelButton = new JPanel();
        bottomPanelButton.setBorder(contour);
        
        JButton playerButton = new JButton("Full Competition");
        playerButton.addActionListener(e -> handleFullCompetition());
        bottomPanelButton.add(playerButton);

        JButton fondButton = new JButton("background");
        fondButton.addActionListener(e -> toggleBackground());
        bottomPanelButton.add(fondButton);

        JButton blackButton = new JButton("Black");
        blackButton.addActionListener(e -> {
			try {
				toggleBlackBackground();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        bottomPanelButton.add(blackButton,BorderLayout.NORTH);

        bottomPanel.add(bottomPanelButton, BorderLayout.NORTH);
        bottomPanel.add(this.panelAnimationConfiguration, BorderLayout.CENTER);
        JButton btnSaveConfigAnimation = new JButton("Save config animation");
        btnSaveConfigAnimation.addActionListener(e -> ConfigurationSaveLoad.saveConfigAnimation(panelAnimationConfiguration,this.event.getNom()));
        bottomPanel.add(btnSaveConfigAnimation, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void finalizeSetup() {
        pack();
        setVisible(true);
        ConfigurationSaveLoad.initJson(nbJoueur, event.getNom());
    }

    private void handlePlayerSelection(JComboBox<String> comboBox, int playerIndex, int panelIndex) {
    	String selectedItem = (String) comboBox.getSelectedItem();
        if (selectedItem != null) {
        	windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1()); // changer le fond de la fenetre
        	try {
        		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
        		Joueur soloPlayer = foundPlayer(selectedItem);
        		int ligne = playerIndex + (nbJoueur/4)*panelIndex;
        		if(soloPlayer != null) {
        			//ajout du player dans le tableau pour l'arbre de tournoi
        			PlayerForDiffusion playerDetailsForTree = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "full",ligne);
        			playerDetailsForTree.setPlacementFrameTwoPlayer(windowConfigPlayerFull);
        			playerDetailsForTree.setPlayer(soloPlayer, ligne+1);
        			playerDetailsForTree.setVisible(false);
        			//ajouter a fenetre full si elle existe et si type joueur = full
        			if(windowConfigPlayerFull != null)
        				playerDetailsForTree.handleFullCase();
        			if(tabPlayerForTree[ligne] != null) {
        				tabPlayerForTree[ligne].removeAll();
        			}
        			tabPlayerForTree[ligne] = playerDetailsForTree;
        			
        			//initialisation et affichage de player solo
        			PlayerForDiffusion soloPlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "player",ligne);
        			soloPlayerDetails.setPlayer(soloPlayer, ligne+1);
        			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
        			ListSelectedJoueur.add(soloPlayerDetails);
        			
        			if (windowConfigPlayer == null || !windowConfigPlayer.isDisplayable()|| windowConfigPlayer.getTypeFenetre() == "full" ) {
        				windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "player");
        			} else {
        				windowConfigPlayer.tabbedPane.removeAll();
        				windowConfigPlayer.tabbedPane.revalidate();
        				windowConfigPlayer.tabbedPane.repaint();
        				windowConfigPlayer.setTypeFenetre("player");
        			}
        			this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne]);
        			soloPlayerDetails.setPlacementFrameTwoPlayer(windowConfigPlayer);
        			this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, soloPlayerDetails);
        			TabConfigurationPlayerInfos tabOnePlayer = new TabConfigurationPlayerInfos(soloPlayerDetails, soloPlayer, windowBroadcastPublic,windowConfigPlayer);
        			windowConfigPlayer.addTabJoueur(tabOnePlayer);
        			windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
        			System.out.println("    SOLO player to dislay : "+soloPlayerDetails.getJoueur().getNom());
        		}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }
//        placementFrameTwoPlayer.refreshAllTab();
        if (windowConfigPlayer != null) {
        	windowConfigPlayer.pack();
        }
    }

    private void handleGameSelection(int buttonIndex, int indexPanel) {
    	// Retrieve the selected players from the corresponding section
		int playerIndex1 = buttonIndex * 2; // Calculate the index of the first player
		int playerIndex2 = playerIndex1 + 1; // Calculate the index of the second player
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_2());
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
		// Check if the indices are within bounds before accessing the list
//		System.out.println(" index Button: " + buttonIndex + ", index P1: " + playerIndex1 + ", index P2: " + playerIndex2);
		if (playerIndex1 < selectedJoueurs.size() && playerIndex2 < selectedJoueurs.size()) {
			Joueur Player1 = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], playerIndex1));
			Joueur Player2 = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], playerIndex2));
			PlayerForDiffusion PlayerDetails1 = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "game",0);
			PlayerForDiffusion PlayerDetails2 = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "game",1);
			int ligne1 = (2*buttonIndex+1)+((nbJoueur/4)*indexPanel);
			int ligne2 = ((2*buttonIndex+1)+((nbJoueur/4)*indexPanel)+1);
			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
			try {
				if(Player1 != null) {
					PlayerDetails1.setPlayer(Player1, ligne1);
					ListSelectedJoueur.add(PlayerDetails1);
				}
				if(Player2 != null) {
					PlayerDetails2.setPlayer(Player2, ligne2);
					ListSelectedJoueur.add(PlayerDetails2);
				}
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			//Si la fenetre est null on la creer
			if(windowConfigPlayer == null|| !windowConfigPlayer.isDisplayable()|| windowConfigPlayer.getTypeFenetre() == "full" ) {
				windowConfigPlayer = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "game");
			}else {
				windowConfigPlayer.tabbedPane.removeAll();
				windowConfigPlayer.tabbedPane.revalidate();
				windowConfigPlayer.tabbedPane.repaint();
				windowConfigPlayer.setTypeFenetre("game");
			}
			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDiffusion);
				playerForDiffusion.setPlacementFrameTwoPlayer(windowConfigPlayer);
				TabConfigurationPlayerInfos tabPool = new TabConfigurationPlayerInfos(playerForDiffusion, playerForDiffusion.getJoueur(), windowBroadcastPublic, windowConfigPlayer);
				windowConfigPlayer.addTabJoueur(tabPool);
				System.out.println("    GAME player to display : "+playerForDiffusion.getJoueur().getNom()+" "+playerForDiffusion.getNumeroPlayer());
			}	
			windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
		} else
			System.out.println("    ERROR find the to player for game");
		windowConfigPlayer.pack();
	}

    private void handleTabSelection(int indexPanel) {
    	windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_3());
		// cr�ation d'une liste de PlayerForDiffusion pour aficher les pool
		ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
		for (int i = 0; i < (nbJoueur / 4); i++) {
			//System.out.println((nbJoueur / 4) * indexPanel + i);
			Joueur Player = foundPlayer(getSelectedPlayerName(playerPanel[indexPanel], i));
			PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "tab", i);
			int ligne = (nbJoueur / 4) * indexPanel + i + 1;
			try {
				if(Player != null) {
					PlayerDetails.setPlayer(Player, ligne);
					ListSelectedJoueur.add(PlayerDetails);
				}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (windowConfigPlayer == null|| !windowConfigPlayer.isDisplayable()|| windowConfigPlayer.getTypeFenetre() == "full" ) {
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
			System.out.println("    TAB player to display  : "+playerForDiffusion.getJoueur().getNom());
		}	
		windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
		windowConfigPlayer.pack();
    }

    private void handleFullCompetition() {
    	windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_4());
	    windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);//nettoyage du layer
		windowBroadcastPublic.removeLayerContent(JLayeredPane.PALETTE_LAYER);//nettoyage du layer
		if (windowConfigPlayerFull == null || !windowConfigPlayerFull.isDisplayable()) {
			windowConfigPlayerFull = new WindowConfigurationPlayerInfos(windowBroadcastPublic, "autre");
		} else {
			windowConfigPlayerFull.tabbedPane.removeAll();
			windowConfigPlayerFull.tabbedPane.revalidate();
			windowConfigPlayerFull.tabbedPane.repaint();
			windowConfigPlayerFull.setTypeFenetre("autre");
		}
	    int indexPlayer = -1;
//	    System.out.println("taille tableau " + playerPanel.length);
	    
//	    int totalPlayers = Math.min(nbJoueur, playerPanel.length * 4);  // Assurez-vous de ne pas dépasser le nombre de panneaux disponibles
	    int totalPlayers = nbJoueur;
	    //iteration sur les 4 partie de la fenetre tournament tree
	    System.out.println("nb panel : "+playerPanel.length+" nb player per panel "+totalPlayers/4);
	    for (int y = 0; y < playerPanel.length; y++) {
	        
//	        int playersInThisRow = Math.min(4, totalPlayers - y * 4);  // Calculez combien de joueurs doivent être traités dans cette rangée
	        int playersInThisRow = totalPlayers/4;  // Calculez combien de joueurs doivent être traités dans cette rangée
	        
	        //iteration sur le nombre de joueur dans la partie de l'arbre du tournoi
	        for (int i = 0; i < playersInThisRow; i++) {
	            Joueur Player = foundPlayer(getSelectedPlayerName(playerPanel[y], i));
	            if (Player != null) {
	                PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "full", y * (totalPlayers/4) + i);
	                PlayerDetails.setPlacementFrameTwoPlayer(windowConfigPlayerFull);
	                int ligne = y * (totalPlayers/4) + i + 1;
	                System.out.println("-> boucle sur tableau " + y+", joueur index "+i+", "+Player.getNom()+", ligne: "+ligne);
	                
	                try {
	                    PlayerDetails.setPlayer(Player, ligne);
	                    if(tabPlayerForTree[ligne-1] != null) {
	                        tabPlayerForTree[ligne-1].removeAll();
	                    }
	                    tabPlayerForTree[ligne-1] = PlayerDetails;
	                    indexPlayer = ligne-1;
	                    this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne-1]);
	                } catch (ClassNotFoundException | SQLException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }
	    }
	    
	    if(indexPlayer != -1) {
	        tabPlayerForTree[indexPlayer].handleFullCase();
//	        tabPlayerForTree[indexPlayer].joueurFullDragged();
	    }
    }

    private void toggleBackground() {
    	if(fondButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered(event.getBackground().getImage_5(), JLayeredPane.POPUP_LAYER);
		else
			windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
		fondButtonAppuyer = !fondButtonAppuyer;
	}

    private void toggleBlackBackground() throws ClassNotFoundException, SQLException {
    	if(blackButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered("black.jpg", JLayeredPane.POPUP_LAYER);
		else
			windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
		blackButtonAppuyer = !blackButtonAppuyer;
	    
//		initListPlayerForDiffusion(selectedJoueurs);
    }

    private Joueur foundPlayer(String nomJoueur) {
    	for (Joueur joueur : selectedJoueurs) {
	        if (joueur.getDisplay_name().equals(nomJoueur)) {
	            return joueur;
	        }
	    }
		return null;
    }
    public ArrayList<PlayerForDiffusion> initListPlayerForDiffusion() throws ClassNotFoundException, SQLException {
    	playerForDifusionListInit = new ArrayList<PlayerForDiffusion>();
    	int i=0;
    	for (Joueur joueur : selectedJoueurs) {
    		PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event.getNom(), windowBroadcastPublic, "full",i);
    		i++;
    		PlayerDetails.setPlayer(joueur, i);
    		playerForDifusionListInit.add(PlayerDetails);
		}
    	return playerForDifusionListInit;
    }

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

    public String eventName() {
        return this.event.getNom();
    }

    public PlayerForDiffusion[] getTabPlayerForTree() {
        return tabPlayerForTree;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }
    public WindowBroadcastPublic getWindowBroadcastPublic() {
    	return windowBroadcastPublic;
    }
    public PanelAnimationConfiguration getPanelAnimationConfiguration() {
    	return panelAnimationConfiguration;
    }
}