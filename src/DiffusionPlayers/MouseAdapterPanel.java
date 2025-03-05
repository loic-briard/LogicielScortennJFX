/*
 * 
 */
package DiffusionPlayers;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import Diffusion.TabConfigurationPlayerInfos;
import Diffusion.WindowBroadcastPublic;
import Diffusion.WindowConfigurationPlayerInfos;
import GlobalSettings.GlobalSettings;
import Police.TabPolice;

// TODO: Auto-generated Javadoc
/**
 * The Class MouseAdapterPanel.
 */
public class MouseAdapterPanel extends MouseAdapter {
	
	/** The panel. */
	private JPanel panel;
	
	/** The prev pos. */
	private Point prevPos;
	
	/** The playerfordifusion 2. */
	private PlayerForDiffusion playerfordifusion2;
	
	/** The frame for diffusion. */
	private WindowBroadcastPublic frameForDiffusion;
	
	/** The window configuration player infos. */
	private WindowConfigurationPlayerInfos windowConfigurationPlayerInfos;

	/**
	 * Instantiates a new mouse adapter panel.
	 *
	 * @param panel the panel
	 * @param playerfordifusion the playerfordifusion
	 * @param diffusionWindow the diffusion window
	 */
	public MouseAdapterPanel(JPanel panel, PlayerForDiffusion playerfordifusion, WindowBroadcastPublic diffusionWindow) {
		this.panel = panel;
		this.playerfordifusion2 = playerfordifusion;
		this.frameForDiffusion = diffusionWindow;
//		this.windowConfigurationPlayerInfos = windowConfigurationPlayer;
	}

	/**
	 * Mouse pressed.
	 *
	 * @param e the e
	 */
	public void mousePressed(MouseEvent e) {
		prevPos = e.getPoint();
		frameForDiffusion.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}

	/**
	 * Mouse dragged.
	 *
	 * @param e the e
	 */
	public void mouseDragged(MouseEvent e) {
		int thisX = panel.getLocation().x;
		int thisY = panel.getLocation().y;

		int xMoved = thisX + (e.getX() - prevPos.x);
		
		int yMoved = thisY + (e.getY() - prevPos.y);
//		System.out.println("composant : "+panel.getName()+"|"+xMoved+"|"+yMoved);
		panel.setLocation(xMoved, yMoved);
		
		if (this.playerfordifusion2.getTypeFen() == "full" || this.playerfordifusion2.getTypeFen() == "tab") {
			int space = GlobalSettings.getInstance().getSpaceLength();
			switch (panel.getName()) {
			case "Name":
				this.playerfordifusion2.playerSurname.setLocation(xMoved + panel.getWidth() + space, yMoved);
				this.playerfordifusion2.playerTeteDeSerie.setLocation(xMoved + this.playerfordifusion2.playerSurname.getWidth()+ panel.getWidth() + space*2, yMoved);
				break;
			case "Surname":
				this.playerfordifusion2.playerName.setLocation(xMoved - (this.playerfordifusion2.playerName.getWidth() + space), yMoved);
				this.playerfordifusion2.playerTeteDeSerie.setLocation(xMoved + panel.getWidth() + space, yMoved);
				break;
			case "Seeding":
				this.playerfordifusion2.playerSurname.setLocation(xMoved - (this.playerfordifusion2.playerSurname.getWidth() + space), yMoved);
				this.playerfordifusion2.playerName.setLocation(xMoved - (this.playerfordifusion2.playerSurname.getWidth() + this.playerfordifusion2.playerName.getWidth() + space*2), yMoved);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Mouse released.
	 *
	 * @param e the e
	 */
	public void mouseReleased(MouseEvent e) {
	    String typeFen = playerfordifusion2.getTypeFen();
	    int playerIndex = playerfordifusion2.getNumeroPlayer();
	    this.windowConfigurationPlayerInfos = playerfordifusion2.getPlacementFrameTwoPlayer();
	    switch (typeFen) {
	        case "player":
	        case "game":
	        case "tab":
	            handleStandardCase(typeFen, playerIndex);
	            break;
	        case "full":
	            handleFullCase(false);
	            break;
	        default:
	            throw new IllegalArgumentException("Unexpected value: " + typeFen);
	    }

//	    System.out.println("Type de fenetre du joueur créé : " + windowConfigurationPlayerInfos.getTypeFenetre());
	}
	
	/**
	 * Handle standard case.
	 *
	 * @param typeFen the type fen
	 * @param playerIndex the player index
	 */
	private void handleStandardCase(String typeFen, int playerIndex) {
	    int tabIndex = typeFen.equals("player") ? 0 : playerIndex;
	    windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(tabIndex);
	    updateSelectedTab(typeFen);
	}

	/**
	 * Handle full case.
	 *
	 * @param full the full
	 */
	//newPlayer = true quand player cliquer depuis window tournament tree
	public void handleFullCase(boolean full) {
		this.windowConfigurationPlayerInfos = playerfordifusion2.getPlacementFrameTwoPlayer();
	    PlayerForDiffusion[] tableauPlayerDiffusionTree = frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree();
	    ArrayList<PlayerForDiffusion> listPlayerDiffusionTree = new ArrayList<>();

	    if ((windowConfigurationPlayerInfos == null || !windowConfigurationPlayerInfos.isDisplayable())) {// la fenetre full n'existe pas
	    	System.out.println("handleFullCase => Creation de la fenetre de config full");
	        createNewFullWindowConfig(tableauPlayerDiffusionTree, listPlayerDiffusionTree);
	    } else if (windowConfigurationPlayerInfos.getTypeFenetre().equals("full") && !full) {//la fenetre existe donc mettre a jour joueur electionne si il existe pas l'inserer
	    	System.out.println("handleFullCase => Fen config full existe, update spinner selected joueur, si selected joueur doesn't exist l'inserer");
	        updateExistingFullWindowConfig(listPlayerDiffusionTree);
	    } else if(full){// si arrive ici ca veut dire qu'il faut afficher tout les joueurs selectionne de l'arbre (btn full)
	    	System.out.println("handleFullCase => Creation fen configfull a partir des joueur selectione dans windows tournament tree");
	        recreateFullWindowConfig(tableauPlayerDiffusionTree, listPlayerDiffusionTree);
	    }
	    
	    windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(findPlayerIndex(listPlayerDiffusionTree));
	    updateSelectedTab("full");
	    windowConfigurationPlayerInfos.pack();
	    frameForDiffusion.getWindowTournamentTreeFromBroadcast().getPanelAnimationConfiguration().getPanelTournamentTree().setPlayer(playerfordifusion2.getNumeroPlayer(), playerfordifusion2);
//	    animationPanel.getPanelTournamentTree().setPlayer(numeroPlayer, playerfordifusion2);
	}

	/**
	 * Creates the new full window config.
	 *
	 * @param tableauPlayerDiffusionTree the tableau player diffusion tree
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void createNewFullWindowConfig(PlayerForDiffusion[] tableauPlayerDiffusionTree, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    windowConfigurationPlayerInfos = new WindowConfigurationPlayerInfos(this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getConfigScreen(), frameForDiffusion, "full");
	    for (PlayerForDiffusion player : tableauPlayerDiffusionTree) {
	        if (player != null) {
	            addPlayerToFullConfig(player, listPlayerDiffusionTree);
	        }
	    }
	    finalizeFullWindowConfig(listPlayerDiffusionTree);
	}

	/**
	 * Update existing full window config.
	 *
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void updateExistingFullWindowConfig(ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    for (int i = 0; i < windowConfigurationPlayerInfos.tabbedPane.getTabCount() - 1; i++) {
	        Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getComponentAt(i);
	        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
	            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
	            listPlayerDiffusionTree.add(currentTab.getInfosPlayerDetails());
//	            listPlayerDiffusionTree.add(playerfordifusion2);
	        }
	    }
	    int playerIndex = findPlayerIndex(listPlayerDiffusionTree);
	    if (playerIndex == -1) {
	    	System.out.println("updateExistingFullWindowConfig => insertion d'un joeur");
	    	insertPlayerToFullConfig(playerfordifusion2, listPlayerDiffusionTree);
	    }
	    windowConfigurationPlayerInfos.getTabPolice().setListPlayer(listPlayerDiffusionTree);
	}

	/**
	 * Recreate full window config.
	 *
	 * @param tableauPlayerDiffusionTree the tableau player diffusion tree
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void recreateFullWindowConfig(PlayerForDiffusion[] tableauPlayerDiffusionTree, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    windowConfigurationPlayerInfos.tabbedPane.removeAll();
	    windowConfigurationPlayerInfos.setTypeFenetre("full");
	    for (PlayerForDiffusion player : tableauPlayerDiffusionTree) {
	        if (player != null) {
	            addPlayerToFullConfig(player, listPlayerDiffusionTree);
	        }
	    }
	    finalizeFullWindowConfig(listPlayerDiffusionTree);
	}

	/**
	 * Adds the player to full config.
	 *
	 * @param player the player
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void addPlayerToFullConfig(PlayerForDiffusion player, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    listPlayerDiffusionTree.add(player);
	    player.setPlacementFrameTwoPlayer(windowConfigurationPlayerInfos);
	    TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(player, player.getJoueur(), frameForDiffusion, windowConfigurationPlayerInfos);
	    windowConfigurationPlayerInfos.addTabJoueur(tabFull);
//	    windowConfigurationPlayerInfos.insertTabJoueur(tabFull,listPlayerDiffusionTree.size());
	    System.out.println("FULL player to display : " + player.getJoueur().getNom());
	}
	
	/**
	 * Insert player to full config.
	 *
	 * @param player the player
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void insertPlayerToFullConfig(PlayerForDiffusion player, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    player.setPlacementFrameTwoPlayer(windowConfigurationPlayerInfos);
	    TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(player, player.getJoueur(), frameForDiffusion, windowConfigurationPlayerInfos);
	    windowConfigurationPlayerInfos.insertTabJoueur(tabFull,listPlayerDiffusionTree.size());
	    listPlayerDiffusionTree.add(player);
	    System.out.println("FULL player to display : " + player.getJoueur().getNom());
	}

	/**
	 * Finalize full window config.
	 *
	 * @param listPlayerDiffusionTree the list player diffusion tree
	 */
	private void finalizeFullWindowConfig(ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    if (!listPlayerDiffusionTree.isEmpty()) {
	        windowConfigurationPlayerInfos.setTabPolice(new TabPolice(listPlayerDiffusionTree, windowConfigurationPlayerInfos));
	        windowConfigurationPlayerInfos.pack();
	    }
	    frameForDiffusion.getWindowTournamentTreeFromBroadcast().setWindowConfigPlayerFull(windowConfigurationPlayerInfos);
	    System.out.println("!!init windows config full");
	}
	
	/**
	 * Update selected tab.
	 *
	 * @param typeFen the type fen
	 */
	private void updateSelectedTab(String typeFen) {
	    Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getSelectedComponent();
	    
	    if (selectedComponent instanceof TabConfigurationPlayerInfos) {
	        TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
	        System.out.println("Player dragged : " + playerfordifusion2.getJoueur().getNom() + " for window " + typeFen.toUpperCase());
	        currentTab.refreshSpinner(playerfordifusion2);
	    } else {
	        System.out.println("Error: Selected component is not an instance of TabConfigurationPlayerInfos");
	        windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(playerfordifusion2.getNumeroPlayer() + 1);
	    }
	}
	
	/**
	 * Find player index.
	 *
	 * @param players the players
	 * @return the int
	 */
	private int findPlayerIndex(ArrayList<PlayerForDiffusion> players) {
	    return IntStream.range(0, players.size())
	            .filter(i -> playerfordifusion2.getNumeroPlayer() == players.get(i).getNumeroPlayer())
	            .findFirst()
	            .orElse(-1);
	}
}