/*
 * 
 */

package Diffusion;

/*
 * fenetre permetant de choisir les joueur selectionne 
 * et les actions a afficher
 */

import Players.Joueur;
import Police.TabPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Event.Evenement;
import Main.ImageUtility;

import javax.swing.*;
import javax.swing.border.Border;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import DiffusionPlayers.PlayerForDiffusion;
import DiffusionPlayers.ZoomablePanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowTournamentTree.
 */
public class WindowTournamentTree extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private static int bgSGT = 110;

	/** The selected joueurs. */
	private final ArrayList<Joueur> selectedJoueurs;
	
	/** The window broadcast public. */
	private WindowBroadcastPublic windowBroadcastPublic;
	
	/** The event. */
	private final Evenement event;
	
	/** The window config player. */
	public WindowConfigurationPlayerInfos windowConfigPlayer;
	
	/** The window config player full. */
	public WindowConfigurationPlayerInfos windowConfigPlayerFull;
	
	/** The panel animation configuration. */
	private PanelAnimationConfiguration panelAnimationConfiguration;
	
	/** The tab player for tree. */
	private final PlayerForDiffusion[] tabPlayerForTree;
	
	/** The player panel. */
	private final JPanel[] playerPanel;
	
	/** The black button appuyer. */
	private boolean blackButtonAppuyer = false;
	
	/** The fond button appuyer. */
	private boolean fondButtonAppuyer = false;
	
	/** The nb joueur. */
	private final int nbJoueur;

	/** The player for difusion list init. */
	private ArrayList<PlayerForDiffusion> playerForDifusionListInit;
	
	/** The player for difusion solo. */
	private PlayerForDiffusion playerForDifusionSolo;
	
	/** The player for difusion game 1. */
	private PlayerForDiffusion playerForDifusionGame1;
	
	/** The player for difusion game 2. */
	private PlayerForDiffusion playerForDifusionGame2;
	
	/** The zoom background. */
	private ZoomablePanel zoomBackground;
	
	private JComboBox<String>[] tabComboBox;
	
	private GraphicsDevice configScreen;

	/**
	 * Instantiates a new window tournament tree.
	 * @param configScreen 
	 *
	 * @param selectedJoueurs the selected joueurs
	 * @param event the event
	 * @param diffusionFrame the diffusion frame
	 * @param nbJoueur the nb joueur
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("unchecked")
	public WindowTournamentTree(GraphicsDevice configScreen, ArrayList<Joueur> selectedJoueurs, Evenement event,
			WindowBroadcastPublic diffusionFrame, int nbJoueur) throws ClassNotFoundException, SQLException {
		this.selectedJoueurs = selectedJoueurs;
		this.windowBroadcastPublic = diffusionFrame;
		this.event = event;
		this.nbJoueur = nbJoueur;
		this.configScreen = configScreen;
		this.tabPlayerForTree = new PlayerForDiffusion[nbJoueur];
		this.playerPanel = new JPanel[4];
		if(this.selectedJoueurs.get(this.selectedJoueurs.size()-1).getNom() != "QUALIFIER")
			this.selectedJoueurs.add(this.selectedJoueurs.size(), new Joueur(0, "men", "QUALIFIER", " ", "QUALIFIER", " "," ",
				" ", "clear.png", 0, 0, " ", 0, 0, " ", " ", " "," "));
		tabComboBox = new JComboBox[this.nbJoueur];
		
		setupFrame();
		
		
		finalizeSetup();
		

		playerForDifusionSolo = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,"player", 0);
		playerForDifusionGame1 = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,"game", 0);
		playerForDifusionGame2 = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,"game", 1);
		playerForDifusionSolo.setPlayer(this.selectedJoueurs.get(0), -1);
		playerForDifusionGame1.setPlayer(this.selectedJoueurs.get(0), -1);
		playerForDifusionGame1.setPlayer(this.selectedJoueurs.get(0), -1);
		
		zoomBackground= new ZoomablePanel();
		zoomBackground.setLayout(null);
		zoomBackground.setOpaque(false);
		
		blackButtonAppuyer = false;
		toggleBlackBackground();
	}

	/**
	 * Setup frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void setupFrame() throws ClassNotFoundException, SQLException {
		setTitle("Broadcast configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("icon.png").getImage());
        // Obtenir l'emplacement de l'écran secondaire
        Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
        
        // Ajouter un JScrollPane pour permettre le défilement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        this.panelAnimationConfiguration = new PanelAnimationConfiguration(this);
        setupPanels(contentPanel);
        setupBottomPanel();
        
        // Définir une hauteur adaptable en fonction du nombre de joueurs
//        int heightNeeded = Math.max(800, (nbJoueur / 4) * 50); // Ajuste la hauteur minimum
//        contentPanel.setPreferredSize(new Dimension(780, heightNeeded));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Pour un défilement fluide
        
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
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

		ConfigurationSaveLoad.initJson(this.nbJoueur, this.eventName());
	}

	/**
	 * Setup panels.
	 */
	private void setupPanels(JPanel panel) {
		Border contour = BorderFactory.createLineBorder(Color.black);
		// Créez un conteneur pour les sections supérieures
		JPanel topSectionsPanel = new JPanel();
		topSectionsPanel.setLayout(new GridLayout(1, 2));

		// Créez un conteneur pour chaque section supérieure
		JPanel topLeftPanel = createSectionPanel("En haut gauche", 0);
		JPanel topRightPanel = createSectionPanel("En haut droite", 2);
		topLeftPanel.setBorder(contour);
		topRightPanel.setBorder(contour);
		// Ajoutez les sections supérieures é leur conteneur
		topSectionsPanel.add(topLeftPanel);
		topSectionsPanel.add(topRightPanel);

		// Créez un conteneur pour les sections inférieures
		JPanel bottomSectionsPanel = new JPanel();
		bottomSectionsPanel.setLayout(new GridLayout(1, 2));

		// Créez un conteneur pour chaque section inférieure
		JPanel bottomLeftPanel = createSectionPanel("En bas gauche", 1);
		JPanel bottomRightPanel = createSectionPanel("En bas droite", 3);
		bottomLeftPanel.setBorder(contour);
		bottomRightPanel.setBorder(contour);
		// Ajoutez les sections inférieures é leur conteneur
		bottomSectionsPanel.add(bottomLeftPanel);
		bottomSectionsPanel.add(bottomRightPanel);

		// Ajoutez les conteneurs des sections é votre fenétre
		panel.setLayout(new BorderLayout());
		panel.add(topSectionsPanel, BorderLayout.NORTH);
		panel.add(bottomSectionsPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates the section panel.
	 *
	 * @param sectionName the section name
	 * @param indexPanel the index panel
	 * @return the j panel
	 */
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

	/**
	 * Adds the player selection.
	 *
	 * @param panel the panel
	 * @param playerIndex the player index
	 * @param panelIndex the panel index
	 */
	private void addPlayerSelection(JPanel panel, int playerIndex, int panelIndex) {
		JComboBox<String> comboBox = new JComboBox<>(
				selectedJoueurs.stream().map(Joueur::getDisplay_name).toArray(String[]::new));
		
		comboBox.setEditable(true);
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setSelectedIndex(-1);

		JButton playerButton = new JButton("Player");
		playerButton.addActionListener(e -> handlePlayerSelection(comboBox, playerIndex, panelIndex));
		int ligne = playerIndex + (nbJoueur / 4) * panelIndex+1;
		
		tabComboBox[ligne-1] = comboBox;
		JPanel comboButtonPanel = new JPanel();
		comboButtonPanel.add(new JLabel(ligne+""));
		comboButtonPanel.add(comboBox);
		comboButtonPanel.add(playerButton);
		panel.add(comboButtonPanel);
	}

	/**
	 * Creates the game panel.
	 *
	 * @param indexPanel the index panel
	 * @return the j panel
	 */
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

	/**
	 * Creates the tab button.
	 *
	 * @param indexPanel the index panel
	 * @return the j button
	 */
	private JButton createTabButton(int indexPanel) {
		JButton tabButton = new JButton("Tab");
		tabButton.addActionListener(e -> handleTabSelection(indexPanel));
		return tabButton;
	}

	/**
	 * Setup bottom panel.
	 */
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
				e1.printStackTrace();
			}
		});
		bottomPanelButton.add(blackButton, BorderLayout.NORTH);

		bottomPanel.add(bottomPanelButton, BorderLayout.NORTH);
		bottomPanel.add(this.panelAnimationConfiguration, BorderLayout.CENTER);
		JButton btnSaveConfigAnimation = new JButton("Save config animation");
		
		btnSaveConfigAnimation.addActionListener(
				e -> {
					int choice = JOptionPane.showConfirmDialog(this, "Do you want to update animation?",
							"Animation update", JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.YES_OPTION) {
						System.out.println("update animationselected");
						ConfigurationSaveLoad.saveConfigAnimation(panelAnimationConfiguration, this.eventName());
					}else {
						System.out.println("don't update animation selected");
						ConfigurationSaveLoad.setConfigAnimation(this.eventName(),panelAnimationConfiguration);
					}
				});
		bottomPanel.add(btnSaveConfigAnimation, BorderLayout.SOUTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Finalize setup.
	 */
	private void finalizeSetup() {
		pack();
		setVisible(true);
	}

	/**
	 * Handle player selection.
	 *
	 * @param comboBox the combo box
	 * @param playerIndex the player index
	 * @param panelIndex the panel index
	 */
	private void handlePlayerSelection(JComboBox<String> comboBox, int playerIndex, int panelIndex) {
		String selectedItem = (String) comboBox.getSelectedItem();
		if (selectedItem != null) {
			// changer le fond de la fenetre
			windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1()); 
			// nettoyage du layer du joueur
			windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);
						
//			ImageUtility imageFond = new ImageUtility("Background/fondPourSolo.png", 0);
//			imageFond.setLocation(0, 0);
//			imageFond.setSize(imageFond.getPreferredSize());
//			zoomBackground.add(imageFond);
//			zoomBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
//			zoomBackground.setSize(10,10);
//			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
			
			System.gc();
			Joueur soloPlayer = foundPlayer(selectedItem);
			if(soloPlayer.getNom() != "QUALIFIER")
				displayFondJoueur("player");
			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
			int ligne = playerIndex + (nbJoueur / 4) * panelIndex;
//			SwingUtilities.invokeLater(() -> {
				if (soloPlayer != null) {
					// create player for tournament tree
					if (tabPlayerForTree[ligne] == null) {
						tabPlayerForTree[ligne] = new PlayerForDiffusion(this.event, windowBroadcastPublic,panelAnimationConfiguration, "full", ligne);
					}
					
					try {
						tabPlayerForTree[ligne].setPlayer(soloPlayer, ligne + 1);
						panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					
					tabPlayerForTree[ligne].setVisible(false);
					this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne]);
					playerForDifusionSolo.removeAll();
					
					// initialisation and display of player solo
					playerForDifusionSolo.setNumeroPlayer(ligne);
					try {
						playerForDifusionSolo.setPlayer(soloPlayer, ligne + 1);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					if (soloPlayer.getNom() != "QUALIFIER")
						this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDifusionSolo);
				}
//			});
			SwingUtilities.invokeLater(() -> {
				if (soloPlayer != null) {
					// add this player to window config full if it exists
					if (windowConfigPlayerFull != null) {
						tabPlayerForTree[ligne].setPlacementFrameTwoPlayer(windowConfigPlayerFull);
						tabPlayerForTree[ligne].getMouseAdapterPanel().handleFullCase(false);
					}
					
					if (windowConfigPlayer == null || !windowConfigPlayer.isDisplayable()|| windowConfigPlayer.getTypeFenetre() == "full") {
						windowConfigPlayer = new WindowConfigurationPlayerInfos(getConfigScreen(), windowBroadcastPublic, "player");
					} else {
						windowConfigPlayer.tabbedPane.removeAll();
						windowConfigPlayer.tabbedPane.revalidate();
						windowConfigPlayer.tabbedPane.repaint();
						windowConfigPlayer.setTypeFenetre("player");
					}
					playerForDifusionSolo.setPlacementFrameTwoPlayer(windowConfigPlayer);
					ListSelectedJoueur.add(playerForDifusionSolo);
					TabConfigurationPlayerInfos tabOnePlayer = new TabConfigurationPlayerInfos(playerForDifusionSolo,
							soloPlayer, windowBroadcastPublic, windowConfigPlayer);
					windowConfigPlayer.addTabJoueur(tabOnePlayer);
					windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
					System.out.println("    SOLO player to dislay : " + playerForDifusionSolo.getJoueur().getNom());

					if (windowConfigPlayer != null) {
						windowConfigPlayer.pack();
					}
				}
			});
		}
	}

	/**
	 * Handle game selection.
	 *
	 * @param buttonIndex the button index
	 * @param indexPanel the index panel
	 */
	private void handleGameSelection(int buttonIndex, int indexPanel) {
		// Retrieve the selected players from the corresponding section
		int playerIndex1 =  (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel)-1; // Calculate the index of the first player
		int playerIndex2 =  (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel); // Calculate the index of the second player
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
		// Check if the indices are within bounds before accessing the list
//		System.out.println(" index Button: " + buttonIndex + ", index P1: " + playerIndex1 + ", index P2: " + playerIndex2);
		displayFondJoueur("game");
		System.out.println("P1 : "+playerIndex1+", P2 : "+playerIndex2);
//		SwingUtilities.invokeLater(() -> {
//			if (playerIndex1 < selectedJoueurs.size() && playerIndex2 < selectedJoueurs.size()) {
				Joueur Player1 = foundPlayer((String) tabComboBox[playerIndex1].getSelectedItem());
				Joueur Player2 = foundPlayer((String) tabComboBox[playerIndex2].getSelectedItem());
//			PlayerForDiffusion PlayerDetails1 = new PlayerForDiffusion(this.eventName(), windowBroadcastPublic, "game",0);
//			PlayerForDiffusion PlayerDetails2 = new PlayerForDiffusion(this.eventName(), windowBroadcastPublic, "game",1);
				int ligne1 = (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel);
				int ligne2 = ((2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel) + 1);
				ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
				try {
					if (Player1 != null) {
						playerForDifusionGame1.setPlayer(Player1, ligne1);
						ListSelectedJoueur.add(playerForDifusionGame1);
					}
					if (Player2 != null) {
						playerForDifusionGame2.setPlayer(Player2, ligne2);
						ListSelectedJoueur.add(playerForDifusionGame2);
					}
					panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				// Si la fenetre est null on la creer
				if (windowConfigPlayer == null || !windowConfigPlayer.isDisplayable()
						|| windowConfigPlayer.getTypeFenetre() == "full") {
					windowConfigPlayer = new WindowConfigurationPlayerInfos(getConfigScreen(),windowBroadcastPublic, "game");
				} else {
					windowConfigPlayer.tabbedPane.removeAll();
					windowConfigPlayer.tabbedPane.revalidate();
					windowConfigPlayer.tabbedPane.repaint();
					windowConfigPlayer.setTypeFenetre("game");
				}
				for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
					this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDiffusion);
					playerForDiffusion.setPlacementFrameTwoPlayer(windowConfigPlayer);
					TabConfigurationPlayerInfos tabPool = new TabConfigurationPlayerInfos(playerForDiffusion,
							playerForDiffusion.getJoueur(), windowBroadcastPublic, windowConfigPlayer);
					windowConfigPlayer.addTabJoueur(tabPool);
					System.out.println("    GAME player to display : " + playerForDiffusion.getJoueur().getNom() + " "
							+ playerForDiffusion.getNumeroPlayer());
				}
				windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
//			} else
//				System.out.println("    ERROR find the to player for game");
			windowConfigPlayer.pack();
//		});
	}

	/**
	 * Handle tab selection.
	 *
	 * @param indexPanel the index panel
	 */
	private void handleTabSelection(int indexPanel) {
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
		// cr�ation d'une liste de PlayerForDiffusion pour aficher les pool
		ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
		
		displayFondJoueur("tab");
		
		System.out.println("indexpanel : "+indexPanel+", nbjoueur : "+nbJoueur);
		
//		SwingUtilities.invokeLater(() -> {
			for (int i = 0; i < (nbJoueur / 4); i++) {
				// System.out.println((nbJoueur / 4) * indexPanel + i);
				Joueur Player = foundPlayer((String) tabComboBox[(nbJoueur / 4) * indexPanel + i].getSelectedItem());
				PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic,						panelAnimationConfiguration, "tab", i);
				int ligne = (nbJoueur / 4) * indexPanel + i + 1;
				try {
					if (Player != null) {
						PlayerDetails.setPlayer(Player, ligne);
						ListSelectedJoueur.add(PlayerDetails);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
			panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);
			if (windowConfigPlayer == null || !windowConfigPlayer.isDisplayable()
					|| windowConfigPlayer.getTypeFenetre() == "full") {
				windowConfigPlayer = new WindowConfigurationPlayerInfos(getConfigScreen(), windowBroadcastPublic, "tab");
			} else {
				windowConfigPlayer.tabbedPane.removeAll();
				windowConfigPlayer.tabbedPane.revalidate();
				windowConfigPlayer.tabbedPane.repaint();
				windowConfigPlayer.setTypeFenetre("tab");
			}
			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDiffusion);
				playerForDiffusion.setPlacementFrameTwoPlayer(windowConfigPlayer);
				TabConfigurationPlayerInfos tabPool = new TabConfigurationPlayerInfos(playerForDiffusion,
						playerForDiffusion.getJoueur(), windowBroadcastPublic, windowConfigPlayer);
				windowConfigPlayer.addTabJoueur(tabPool);
				System.out.println("    TAB player to display  : " + playerForDiffusion.getJoueur().getNom());
			}
			System.out.println(ListSelectedJoueur.size());
			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				
				System.out.println(playerForDiffusion.getName());
			}
			windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
			windowConfigPlayer.pack();
//		});
	}

	/** The index player. */
	private int indexPlayer = -1;

	/**
	 * Handle full competition.
	 */
	private void handleFullCompetition() {
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
		windowBroadcastPublic.removeLayerContent(bgSGT);// nettoyage du layer
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
		windowBroadcastPublic.removeLayerContent(JLayeredPane.PALETTE_LAYER);// nettoyage du layer

		indexPlayer = -1;
//		SwingUtilities.invokeLater(() -> {
		int totalPlayers = nbJoueur;
		// iteration sur les 4 partie de la fenetre tournament tree
		System.out.println("nb panel : " + playerPanel.length + " nb player per panel " + totalPlayers / 4);
		for (int y = 0; y < playerPanel.length; y++) {
			// Calculez combien de joueurs doivent être traités dans cette ranger
			int playersInThisRow = totalPlayers / 4;
			// iteration sur le nombre de joueur dans la partie de l'arbre du tournoi
			for (int i = 0; i < playersInThisRow; i++) {
				Joueur Player = foundPlayer((String) tabComboBox[y * (totalPlayers / 4) + i].getSelectedItem());
				if (Player != null) {
					int ligne = y * (totalPlayers / 4) + i + 1;
					if (tabPlayerForTree[ligne - 1] == null) {
						PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration, "full", y * (totalPlayers / 4) + i);
						tabPlayerForTree[ligne - 1] = PlayerDetails;
					}
					try {
						tabPlayerForTree[ligne - 1].setPlayer(Player, ligne);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tabPlayerForTree[ligne - 1].setPlacementFrameTwoPlayer(windowConfigPlayerFull);
					indexPlayer = ligne - 1;
					this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne - 1]);
				}
			}
		}
//		});
		SwingUtilities.invokeLater(() -> {
			if (indexPlayer != -1)
				tabPlayerForTree[indexPlayer].getMouseAdapterPanel().handleFullCase(true);
		});
	}

	/**
	 * Toggle background.
	 */
	private void toggleBackground() {
//		windowBroadcastPublic.removeLayerContent(bgSGT);
//		ZoomablePanel testBackground = new ZoomablePanel();
//		testBackground.setLayout(null);
//		testBackground.setOpaque(false);
//		ImageUtility imageFond = new ImageUtility("Background/fondPourSolo.png", 0);
//		imageFond.setLocation(0, 0);
//		imageFond.setSize(imageFond.getPreferredSize());
//		testBackground.add(imageFond);
//		testBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
//		testBackground.setSize(10,10);
//		windowBroadcastPublic.addContent(bgSGT, testBackground);
//		panelAnimationConfiguration.zoomPanel(testBackground, this.windowBroadcastPublic, null);
		
    	if(fondButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered(event.getBackground().getImage_5(), JLayeredPane.POPUP_LAYER);
		else
			windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
		fondButtonAppuyer = !fondButtonAppuyer;
	}

	/**
	 * Toggle black background.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void toggleBlackBackground() throws ClassNotFoundException, SQLException {
		if (blackButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered("black.jpg", JLayeredPane.POPUP_LAYER);
		else
			windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
		blackButtonAppuyer = !blackButtonAppuyer;

//		initListPlayerForDiffusion(selectedJoueurs);
	}

	/**
	 * Found player.
	 *
	 * @param nomJoueur the nom joueur
	 * @return the joueur
	 */
	private Joueur foundPlayer(String nomJoueur) {
		for (Joueur joueur : selectedJoueurs) {
			if (joueur.getDisplay_name().equals(nomJoueur)) {
				return joueur;
			}
		}
		return null;
	}

	/**
	 * Inits the list player for diffusion.
	 *
	 * @return the array list
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<PlayerForDiffusion> initListPlayerForDiffusion() throws ClassNotFoundException, SQLException {
		playerForDifusionListInit = new ArrayList<PlayerForDiffusion>();
		for (int i = 0; i < this.nbJoueur; i++) {
			PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic,
					panelAnimationConfiguration, "full", i);
			PlayerDetails.setPlayer(this.selectedJoueurs.get(0), i);
			playerForDifusionListInit.add(PlayerDetails);
		}
		return playerForDifusionListInit;
	}

	/**
	 * Display fond joueur.
	 *
	 * @param typeJoueur the type joueur
	 */
	private void displayFondJoueur(String typeJoueur) {
		// nettoyage du layer du fond du joueur
		windowBroadcastPublic.removeLayerContent(bgSGT);
		zoomBackground.removeAll();
		switch (typeJoueur) {
		case "player":
			System.out.println("-- fond pour joueur solo --");
			ImageUtility imageFond = new ImageUtility(event.getBackground().getImage_2(), 0);
			imageFond.setLocation(0, 0);
			imageFond.setSize(imageFond.getPreferredSize());
			zoomBackground.add(imageFond);
			zoomBackground.setSize(this.windowBroadcastPublic.getWidth()/10,this.windowBroadcastPublic.getHeight()/10);
			zoomBackground.setLocation((this.windowBroadcastPublic.getWidth()/2)-(zoomBackground.getWidth()/2), (this.windowBroadcastPublic.getHeight()/2)-(zoomBackground.getHeight()/2));
			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
			break;
		case "game":
			System.out.println("-- fond pour joueur game --");
			ImageUtility imageFond2= new ImageUtility(event.getBackground().getImage_3(), 0);
			imageFond2.setLocation(0, 0);
			imageFond2.setSize(imageFond2.getPreferredSize());
			zoomBackground.add(imageFond2);
			zoomBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
			zoomBackground.setSize(10,10);
			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
			break;
		case "tab":
			System.out.println("-- fond pour joueur tab --");
			ImageUtility imageFond3 = new ImageUtility(event.getBackground().getImage_4(), 0);
			imageFond3.setLocation(0, 0);
			imageFond3.setSize(imageFond3.getPreferredSize());
			zoomBackground.add(imageFond3);
			zoomBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
			zoomBackground.setSize(10,10);
			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
			break;

		default:
			break;
		}
		
	}

	/**
	 * Event name.
	 *
	 * @return the string
	 */
	public String eventName() {
		return this.event.getNom();
	}

	/**
	 * Gets the tab player for tree.
	 *
	 * @return the tab player for tree
	 */
	public PlayerForDiffusion[] getTabPlayerForTree() {
		return tabPlayerForTree;
	}

	/**
	 * Gets the nb joueur.
	 *
	 * @return the nb joueur
	 */
	public int getNbJoueur() {
		return nbJoueur;
	}

	/**
	 * Gets the window broadcast public.
	 *
	 * @return the window broadcast public
	 */
	public WindowBroadcastPublic getWindowBroadcastPublic() {
		return windowBroadcastPublic;
	}

	/**
	 * Gets the panel animation configuration.
	 *
	 * @return the panel animation configuration
	 */
	public PanelAnimationConfiguration getPanelAnimationConfiguration() {
		return panelAnimationConfiguration;
	}
	
	public GraphicsDevice getConfigScreen() {
		return configScreen;
	}

	/**
	 * Sets the window config player full.
	 *
	 * @param windowConfigPlayerFull the new window config player full
	 */
	public void setWindowConfigPlayerFull(WindowConfigurationPlayerInfos windowConfigPlayerFull) {
		this.windowConfigPlayerFull = windowConfigPlayerFull;
	}
	
}