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
import javax.swing.*;
import javax.swing.border.Border;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import Animation.PanelAnimationConfiguration;
import Animation.ZoomablePanel;
import DiffusionPlayers.PlayerForDiffusion;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowTournamentTree.
 */
public class WindowTournamentTree extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public int bgSGT = 110;

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
	public final PlayerForDiffusion[] tabPlayerForTree;

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

	private String typeFen = "autre";

	ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
	ArrayList<PlayerForDiffusion> ListSelectedJoueurFull = new ArrayList<>();

	/**
	 * Instantiates a new window tournament tree.
	 * 
	 * @param configScreen
	 *
	 * @param selectedJoueurs the selected joueurs
	 * @param event           the event
	 * @param diffusionFrame  the diffusion frame
	 * @param nbJoueur        the nb joueur
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public WindowTournamentTree(GraphicsDevice configScreen, ArrayList<Joueur> selectedJoueurs, Evenement event,
			WindowBroadcastPublic diffusionFrame, int nbJoueur)
			throws ClassNotFoundException, SQLException, IOException {
		this.selectedJoueurs = selectedJoueurs;
		this.windowBroadcastPublic = diffusionFrame;
		this.event = event;
		this.nbJoueur = nbJoueur;
		this.configScreen = configScreen;
		this.tabPlayerForTree = new PlayerForDiffusion[nbJoueur];
		this.playerPanel = new JPanel[4];
		if (this.selectedJoueurs.get(this.selectedJoueurs.size() - 1).getNom() != "QUALIFIER")
			this.selectedJoueurs.add(this.selectedJoueurs.size(),
					new Joueur(0, "men", "QUALIFIER", " ", "QUALIFIER", " ", " ", " ",
							"resources" + File.separator + "imgInterface" + File.separator + "clear.png", 0, 0, " ", 0,
							0, " ", " ", " ", " "));
		tabComboBox = new JComboBox[this.nbJoueur];

		setupFrame();

		finalizeSetup();

		playerForDifusionSolo = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,
				"player", 0, this);
		playerForDifusionGame1 = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,
				"game", 0, this);
		playerForDifusionGame2 = new PlayerForDiffusion(this.event, windowBroadcastPublic, panelAnimationConfiguration,
				"game", 1, this);
		playerForDifusionSolo.setPlayer(this.selectedJoueurs.get(0), -1);
		playerForDifusionGame1.setPlayer(this.selectedJoueurs.get(0), -1);
		playerForDifusionGame1.setPlayer(this.selectedJoueurs.get(0), -1);

		zoomBackground = new ZoomablePanel();
		zoomBackground.setLayout(null);
		zoomBackground.setOpaque(false);

		blackButtonAppuyer = false;
		toggleBlackBackground();
	}

	/**
	 * Setup frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void setupFrame() throws ClassNotFoundException, SQLException, IOException {
		setTitle("Broadcast configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setIconImage(
				new ImageIcon("resources" + File.separator + "imgInterface" + File.separator + "icon.png").getImage());
		// Obtenir l'emplacement de l'écran secondaire
		Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
		setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2),
				bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre

		// Ajouter un JScrollPane pour permettre le défilement
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

		ConfigurationSaveLoad.initJson(this.nbJoueur, this.eventName());
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

		JPanel rockBottomSectionsPanel = new JPanel();
		rockBottomSectionsPanel.setLayout(new GridLayout(1, 1));
		JButton playerButton = new JButton("All Player");
		playerButton.addActionListener(e -> {
			try {
				handleFullCompetition();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		rockBottomSectionsPanel.add(playerButton);

		// Ajoutez les conteneurs des sections é votre fenétre
		panel.setLayout(new BorderLayout());
		panel.add(topSectionsPanel, BorderLayout.NORTH);
		panel.add(bottomSectionsPanel, BorderLayout.CENTER);
		panel.add(rockBottomSectionsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the section panel.
	 *
	 * @param sectionName the section name
	 * @param indexPanel  the index panel
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
	 * @param panel       the panel
	 * @param playerIndex the player index
	 * @param panelIndex  the panel index
	 */
	private void addPlayerSelection(JPanel panel, int playerIndex, int panelIndex) {
		JComboBox<String> comboBox = new JComboBox<>(
				selectedJoueurs.stream().map(Joueur::getDisplay_name).toArray(String[]::new));

		comboBox.setEditable(true);
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setSelectedIndex(-1);

		JButton playerButton = new JButton("Player");
		playerButton.addActionListener(e -> {
			if (!panelAnimationConfiguration.isAnimRunning())
				try {
					handlePlayerSelection(comboBox, playerIndex, panelIndex);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		int ligne = playerIndex + (nbJoueur / 4) * panelIndex + 1;

		tabComboBox[ligne - 1] = comboBox;
		JPanel comboButtonPanel = new JPanel();
		comboButtonPanel.add(new JLabel(ligne + ""));
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
			gameButton.addActionListener(e -> {
				if (!panelAnimationConfiguration.isAnimRunning())
					try {
						handleGameSelection(buttonIndex, indexPanel);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			});
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
		tabButton.addActionListener(e -> {
			if (!panelAnimationConfiguration.isAnimRunning())
				try {
					handleTabSelection(indexPanel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		return tabButton;
	}

	/**
	 * Setup bottom panel.
	 */
	private void setupBottomPanel() throws ClassNotFoundException, SQLException {
		Border contour = BorderFactory.createLineBorder(Color.black);
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel bottomPanelButton = new JPanel();
		JPanel bottomPanelConfigPlayerButton = new JPanel(new BorderLayout());
		bottomPanelButton.setBorder(contour);

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

		JButton tabConfigPlayerButton = new JButton("Player disposition");
		tabConfigPlayerButton.addActionListener(e -> {
			if (ListSelectedJoueur.size() != 0 && typeFen != "full") {
				displayWindowConfig();
			}
			for (PlayerForDiffusion playerForDiffusion : tabPlayerForTree) {
				if (playerForDiffusion != null) {
					displayWindowConfigFull();
					break;
				}
			}
		});
		bottomPanelConfigPlayerButton.add(tabConfigPlayerButton, BorderLayout.CENTER);

		bottomPanelConfigPlayerButton.add(bottomPanelButton, BorderLayout.NORTH);
		bottomPanelConfigPlayerButton.add(tabConfigPlayerButton);

		bottomPanel.add(bottomPanelConfigPlayerButton, BorderLayout.NORTH);

		bottomPanel.add(this.panelAnimationConfiguration, BorderLayout.CENTER);
		JButton btnSaveConfigAnimation = new JButton("Save config animation");

		btnSaveConfigAnimation.addActionListener(e -> {
			int choice = JOptionPane.showConfirmDialog(this, "Do you want to update animation?", "Animation update",
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				System.out.println("update animationselected for event : " + this.eventName());
				ConfigurationSaveLoad.saveConfigAnimation(panelAnimationConfiguration, this.eventName());
			} else {
				System.out.println("don't update animation selected");
				ConfigurationSaveLoad.setConfigAnimation(this.eventName(), panelAnimationConfiguration);
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
	 * @param comboBox    the combo box
	 * @param playerIndex the player index
	 * @param panelIndex  the panel index
	 * @throws IOException
	 */
	private void handlePlayerSelection(JComboBox<String> comboBox, int playerIndex, int panelIndex) throws IOException {
		String selectedItem = (String) comboBox.getSelectedItem();
		if (selectedItem != null) {
			// changer le fond de la fenetre
			windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
			// nettoyage du layer du joueur
			windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);

			System.gc();
			Joueur soloPlayer = foundPlayer(selectedItem);
//			if(soloPlayer != null) {
//				if(soloPlayer.getNom() != "QUALIFIER")
//					displayFondJoueur("player");
//			}
//			else
			windowBroadcastPublic.removeLayerContent(bgSGT);// nettoyage du layer
//			ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
			ListSelectedJoueur.clear();
			int ligne = playerIndex + (nbJoueur / 4) * panelIndex;
//			SwingUtilities.invokeLater(() -> {
			if (soloPlayer != null) {
				// create player for tournament tree
				if (tabPlayerForTree[ligne] == null) {
					tabPlayerForTree[ligne] = new PlayerForDiffusion(this.event, windowBroadcastPublic,	panelAnimationConfiguration, "full", ligne, this);
				}
				try {
					tabPlayerForTree[ligne].setPlayer(soloPlayer, ligne + 1);
//					panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne]);
				tabPlayerForTree[ligne].setVisible(false);
				playerForDifusionSolo.removeAll();

				// initialisation and display of player solo
				playerForDifusionSolo.setNumeroPlayer(ligne);
				try {
					playerForDifusionSolo.setPlayer(soloPlayer, ligne + 1);
					playerForDifusionSolo.setWindowConfigurationPlayerInfos(this.windowConfigPlayer);
					ListSelectedJoueur.add(playerForDifusionSolo);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if (soloPlayer.getNom() != "QUALIFIER")
					this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDifusionSolo);
				
				typeFen = "player";
			}
			// création/mise à jour de la fenetre de config de game
			if (windowConfigPlayer != null) {
				windowConfigPlayer.tabbedPane.removeAll();
				windowConfigPlayer.tabbedPane.revalidate();
				windowConfigPlayer.tabbedPane.repaint();
				windowConfigPlayer.setTypeFenetre("player");
				updateWindowConfig();
			}
		}
	}

	/**
	 * Handle game selection.
	 *
	 * @param buttonIndex the button index
	 * @param indexPanel  the index panel
	 * @throws IOException
	 */
	private void handleGameSelection(int buttonIndex, int indexPanel) throws IOException {
		// Retrieve the selected players from the corresponding section
		int playerIndex1 = (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel) - 1; // Calculate the index of the
																						// first player
		int playerIndex2 = (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel); // Calculate the index of the second
																					// player
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
		windowBroadcastPublic.removeLayerContent(bgSGT);
		// Check if the indices are within bounds before accessing the list
//		System.out.println(" index Button: " + buttonIndex + ", index P1: " + playerIndex1 + ", index P2: " + playerIndex2);
//		displayFondJoueur("game");
		System.out.println("P1 : " + playerIndex1 + ", P2 : " + playerIndex2);
//		SwingUtilities.invokeLater(() -> {
//			if (playerIndex1 < selectedJoueurs.size() && playerIndex2 < selectedJoueurs.size()) {
		Joueur Player1 = foundPlayer((String) tabComboBox[playerIndex1].getSelectedItem());
		Joueur Player2 = foundPlayer((String) tabComboBox[playerIndex2].getSelectedItem());
//			PlayerForDiffusion PlayerDetails1 = new PlayerForDiffusion(this.eventName(), windowBroadcastPublic, "game",0);
//			PlayerForDiffusion PlayerDetails2 = new PlayerForDiffusion(this.eventName(), windowBroadcastPublic, "game",1);
		int ligne1 = (2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel);
		int ligne2 = ((2 * buttonIndex + 1) + ((nbJoueur / 4) * indexPanel) + 1);
		try {
			ListSelectedJoueur.clear();
			if (Player1 != null) {
				playerForDifusionGame1.setPlayer(Player1, ligne1);
				playerForDifusionGame1.setWindowConfigurationPlayerInfos(this.windowConfigPlayer);
				ListSelectedJoueur.add(playerForDifusionGame1);
			}
			if (Player2 != null) {
				playerForDifusionGame2.setPlayer(Player2, ligne2);
				playerForDifusionGame2.setWindowConfigurationPlayerInfos(this.windowConfigPlayer);
				ListSelectedJoueur.add(playerForDifusionGame2);
			}
//			panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);

			for (PlayerForDiffusion playerForDiffusion : ListSelectedJoueur) {
				this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, playerForDiffusion);
			}
			typeFen = "game";
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

		// création/mise à jour de la fenetre de config de game
		if (windowConfigPlayer != null) {
			windowConfigPlayer.tabbedPane.removeAll();
			windowConfigPlayer.tabbedPane.revalidate();
			windowConfigPlayer.tabbedPane.repaint();
			windowConfigPlayer.setTypeFenetre("game");
			updateWindowConfig();
		}
	}

	/**
	 * Handle tab selection.
	 *
	 * @param indexPanel the index panel
	 * @throws IOException
	 */
	private void handleTabSelection(int indexPanel) throws IOException {
		windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
		// cr�ation d'une liste de PlayerForDiffusion pour aficher les pool
//		ArrayList<PlayerForDiffusion> ListSelectedJoueur = new ArrayList<>();
		windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
		windowBroadcastPublic.removeLayerContent(bgSGT);

//		displayFondJoueur("tab");

		System.out.println("indexpanel : " + indexPanel + ", nbjoueur : " + nbJoueur);
		ListSelectedJoueur.clear();
//		SwingUtilities.invokeLater(() -> {
		for (int i = 0; i < (nbJoueur / 4); i++) {
			// System.out.println((nbJoueur / 4) * indexPanel + i);
			Joueur Player = foundPlayer((String) tabComboBox[(nbJoueur / 4) * indexPanel + i].getSelectedItem());
			PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic,
					panelAnimationConfiguration, "tab", i, this);
			int ligne = (nbJoueur / 4) * indexPanel + i + 1;
			try {
				if (Player != null) {
					PlayerDetails.setPlayer(Player, ligne);
					PlayerDetails.setWindowConfigurationPlayerInfos(this.windowConfigPlayer);
					ListSelectedJoueur.add(PlayerDetails);
					this.windowBroadcastPublic.addContent(JLayeredPane.MODAL_LAYER, PlayerDetails);
				}
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		typeFen = "tab";
//		panelAnimationConfiguration.zoomPanel(zoomBackground, this.windowBroadcastPublic, null);
		// création/mise à jour de la fenetre de config de game
		if (windowConfigPlayer != null) {
			windowConfigPlayer.tabbedPane.removeAll();
			windowConfigPlayer.tabbedPane.revalidate();
			windowConfigPlayer.tabbedPane.repaint();
			windowConfigPlayer.setTypeFenetre("tab");
			updateWindowConfig();
		}
	}

	/** The index player. */
//	private int indexPlayer = -1;

	/**
	 * Handle full competition.
	 * 
	 * @throws IOException
	 */
	private void handleFullCompetition() throws IOException {
		SwingUtilities.invokeLater(() -> {
			windowBroadcastPublic.setBackgroundImage(event.getBackground().getImage_1());
			windowBroadcastPublic.removeLayerContent(bgSGT);// nettoyage du layer
			windowBroadcastPublic.removeLayerContent(JLayeredPane.MODAL_LAYER);// nettoyage du layer
//			windowBroadcastPublic.removeLayerContent(JLayeredPane.PALETTE_LAYER);// nettoyage du layer
			windowBroadcastPublic.repaint();
		});
		
//		indexPlayer = -1;
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
						PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic,panelAnimationConfiguration, "full", ligne-1/*y * (totalPlayers / 4) + i*/, this);
						tabPlayerForTree[ligne - 1] = PlayerDetails;
					}
					try {
						tabPlayerForTree[ligne - 1].setPlayer(Player, ligne);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tabPlayerForTree[ligne - 1].setWindowConfigurationPlayerInfos(windowConfigPlayerFull);
//					indexPlayer = ligne - 1;
					this.windowBroadcastPublic.addContent(JLayeredPane.PALETTE_LAYER, tabPlayerForTree[ligne - 1]);
					tabPlayerForTree[ligne - 1].setVisible(true);
//					this.windowBroadcastPublic.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[tabPlayerForTree[ligne - 1].getNumeroPlayer()].setVisible(true);
					
				}
			}
		}
		typeFen = "full";
		if (windowConfigPlayerFull != null) {
			this.windowConfigPlayerFull.tabbedPane.removeAll();
			this.windowConfigPlayerFull.tabbedPane.revalidate();
			this.windowConfigPlayerFull.tabbedPane.repaint();
			this.windowConfigPlayerFull.setTypeFenetre("full");
			updateWindowConfigFull(tabPlayerForTree, new ArrayList<>(Arrays.asList(tabPlayerForTree)));
		}
	}

	/**
	 * Toggle background.
	 */
	private void toggleBackground() {

		if (fondButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered(event.getBackground().getImage_5(),
					JLayeredPane.POPUP_LAYER);
		else
			windowBroadcastPublic.removeLayerContent(JLayeredPane.POPUP_LAYER);
		fondButtonAppuyer = !fondButtonAppuyer;
	}

	/**
	 * Toggle black background.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	private void toggleBlackBackground() throws ClassNotFoundException, SQLException {
		if (blackButtonAppuyer == false)
			windowBroadcastPublic.setBackgroundImageLayered(
					"resources" + File.separator + "imgInterface" + File.separator + "black.jpg",
					JLayeredPane.POPUP_LAYER);
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
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 */
	public ArrayList<PlayerForDiffusion> initListPlayerForDiffusion()
			throws ClassNotFoundException, SQLException, IOException {
		playerForDifusionListInit = new ArrayList<PlayerForDiffusion>();
		for (int i = 0; i < this.nbJoueur; i++) {
			PlayerForDiffusion PlayerDetails = new PlayerForDiffusion(this.event, windowBroadcastPublic,
					panelAnimationConfiguration, "full", i, this);
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
//	private void displayFondJoueur(String typeJoueur) {
//		// nettoyage du layer du fond du joueur
//		windowBroadcastPublic.removeLayerContent(bgSGT);
//		zoomBackground.removeAll();
//		switch (typeJoueur) {
//		case "player":
//			System.out.println("-- fond pour joueur solo --");
//			ImageUtility imageFond = new ImageUtility(event.getBackground().getImage_2(), 0);
//			imageFond.setLocation(0, 0);
//			imageFond.setSize(imageFond.getPreferredSize());
//			zoomBackground.add(imageFond);
//			zoomBackground.setSize(this.windowBroadcastPublic.getWidth()/10,this.windowBroadcastPublic.getHeight()/10);
//			zoomBackground.setLocation((this.windowBroadcastPublic.getWidth()/2)-(zoomBackground.getWidth()/2), (this.windowBroadcastPublic.getHeight()/2)-(zoomBackground.getHeight()/2));
//			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
//			break;
//		case "game":
//			System.out.println("-- fond pour joueur game --");
//			ImageUtility imageFond2= new ImageUtility(event.getBackground().getImage_3(), 0);
//			imageFond2.setLocation(0, 0);
//			imageFond2.setSize(imageFond2.getPreferredSize());
//			zoomBackground.add(imageFond2);
//			zoomBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
//			zoomBackground.setSize(10,10);
//			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
//			break;
//		case "tab":
//			System.out.println("-- fond pour joueur tab --");
//			ImageUtility imageFond3 = new ImageUtility(event.getBackground().getImage_4(), 0);
//			imageFond3.setLocation(0, 0);
//			imageFond3.setSize(imageFond3.getPreferredSize());
//			zoomBackground.add(imageFond3);
//			zoomBackground.setLocation(this.windowBroadcastPublic.getWidth() / 2,this.windowBroadcastPublic.getHeight() / 2 );
//			zoomBackground.setSize(10,10);
//			windowBroadcastPublic.addContent(bgSGT, zoomBackground);
//			break;
//
//		default:
//			break;
//		}
//		typeFen = typeJoueur;
//	}

	private void displayWindowConfig() {
		if (this.windowConfigPlayer == null || !this.windowConfigPlayer.isDisplayable() || this.windowConfigPlayer.getTypeFenetre() == "full") {
			this.windowConfigPlayer = new WindowConfigurationPlayerInfos(getConfigScreen(), windowBroadcastPublic,typeFen);
			this.windowConfigPlayer.setLocation(this.windowConfigPlayer.getX() / 2, this.windowConfigPlayer.getY());
		} else {
			this.windowConfigPlayer.tabbedPane.removeAll();
			this.windowConfigPlayer.tabbedPane.revalidate();
			this.windowConfigPlayer.tabbedPane.repaint();
			this.windowConfigPlayer.setTypeFenetre(typeFen);
		}
		this.windowConfigPlayer.setVisible(true);

		updateWindowConfig();
	}

	private void updateWindowConfig() {
		for (PlayerForDiffusion playerForDiffusionList : ListSelectedJoueur) {
			// this.frameForDiffusion.addContent(JLayeredPane.MODAL_LAYER,
			// playerForDiffusion);
			playerForDiffusionList.setWindowConfigurationPlayerInfos(this.windowConfigPlayer);
			TabConfigurationPlayerInfos tabPool = new TabConfigurationPlayerInfos(playerForDiffusionList,
					playerForDiffusionList.getJoueur(), this.windowBroadcastPublic, windowConfigPlayer);
			this.windowConfigPlayer.addTabJoueur(tabPool);
			System.out.println("    TAB player to display  : " + playerForDiffusionList.getJoueur().getNom());
		}
		this.windowConfigPlayer.setTabPolice(new TabPolice(ListSelectedJoueur, windowConfigPlayer));
		this.windowConfigPlayer.pack();
	}

	private void displayWindowConfigFull() {
		ArrayList<PlayerForDiffusion> listPlayerDiffusionTreeForFull = new ArrayList<>();
		if ((this.windowConfigPlayerFull == null || !this.windowConfigPlayerFull.isDisplayable())) {// la fenetre full
																									// n'existe pas
			System.out.println("handleFullCase => Creation de la fenetre de config full");
			createNewFullWindowConfig(tabPlayerForTree, listPlayerDiffusionTreeForFull);
		} else {
			this.windowConfigPlayerFull.tabbedPane.removeAll();
			this.windowConfigPlayerFull.tabbedPane.revalidate();
			this.windowConfigPlayerFull.tabbedPane.repaint();
			this.windowConfigPlayerFull.setTypeFenetre("full");
			updateWindowConfigFull(tabPlayerForTree, listPlayerDiffusionTreeForFull);
		}
	}

	private void createNewFullWindowConfig(PlayerForDiffusion[] tableauPlayerDiffusionTree,
			ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
		this.windowConfigPlayerFull = new WindowConfigurationPlayerInfos(getConfigScreen(), windowBroadcastPublic,
				"full");
		updateWindowConfigFull(tableauPlayerDiffusionTree, listPlayerDiffusionTree);
	}

	private void updateWindowConfigFull(PlayerForDiffusion[] tableauPlayerDiffusionTree,
			ArrayList<PlayerForDiffusion> listPlayerDiffusionTreeForFull) {
		for (PlayerForDiffusion player : tableauPlayerDiffusionTree) {
			if (player != null) {
				addPlayerToFullConfig(player, listPlayerDiffusionTreeForFull);
			}
		}
		finalizeFullWindowConfig(listPlayerDiffusionTreeForFull);
	}

	private void finalizeFullWindowConfig(ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
		if (!listPlayerDiffusionTree.isEmpty()) {
			this.windowConfigPlayerFull
					.setTabPolice(new TabPolice(listPlayerDiffusionTree, this.windowConfigPlayerFull));
			this.windowConfigPlayerFull.pack();
			System.out.println("!!init windows config full");
			this.windowConfigPlayerFull.setVisible(true);
			this.windowConfigPlayerFull.setLocation(
					this.windowConfigPlayerFull.getX() + this.windowConfigPlayerFull.getWidth() / 2,
					this.windowConfigPlayerFull.getY());
		}
	}

	private void addPlayerToFullConfig(PlayerForDiffusion player,
			ArrayList<PlayerForDiffusion> listPlayerDiffusionTreeForFull) {
		listPlayerDiffusionTreeForFull.add(player);
		player.setWindowConfigurationPlayerInfos(this.windowConfigPlayerFull);
		TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(player, player.getJoueur(),
				windowBroadcastPublic, this.windowConfigPlayerFull);
		this.windowConfigPlayerFull.addTabJoueur(tabFull);
//	    windowTournamentTree.windowConfigPlayerFull.insertTabJoueur(tabFull,listPlayerDiffusionTree.size());
		System.out.println("FULL player to display : " + player.getJoueur().getNom());
	}

	/**
	 * Event name.
	 *
	 * @return the string
	 */
	public String eventName() {
		return this.event.getNom();
	}

	public Evenement getEvent() {
		return this.event;
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