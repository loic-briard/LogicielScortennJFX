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
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import Diffusion.TabConfigurationPlayerInfos;
import Diffusion.WindowBroadcastPublic;
import Diffusion.WindowTournamentTree;
import GlobalSettings.GlobalSettings;

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
	
	private WindowTournamentTree windowTournamentTree;

	/**
	 * Instantiates a new mouse adapter panel.
	 *
	 * @param panel the panel
	 * @param playerfordifusion the playerfordifusion
	 * @param diffusionWindow the diffusion window
	 */
	public MouseAdapterPanel(JPanel panel, PlayerForDiffusion playerfordifusion, WindowBroadcastPublic diffusionWindow, WindowTournamentTree WindowTournamentTree) {
		this.panel = panel;
		this.playerfordifusion2 = playerfordifusion;
		this.frameForDiffusion = diffusionWindow;
		this.windowTournamentTree = WindowTournamentTree;
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

		//mise à jour des fenetres de configurations
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
	    //this.windowConfigurationPlayerInfos = playerfordifusion2.getPlacementFrameTwoPlayer();
	    switch (typeFen) {
	        case "player":
	        case "game":
	        case "tab":
	        	if (this.windowTournamentTree.windowConfigPlayer != null ) {
	        		System.out.println("boucle dragged ");
		        	int tabIndex = typeFen.equals("player") ? 0 : playerIndex;
		        	this.windowTournamentTree.windowConfigPlayer.getAllTab().setSelectedIndex(tabIndex);
//		        	this.windowConfigurationPlayerInfos.getAllTab().setSelectedIndex(tabIndex);
		        	updateSelectedTab(typeFen);
	        	}
	            break;
	        case "full":
	        	if (this.windowTournamentTree.windowConfigPlayerFull != null )
	        		handleFullCase(false);
	            break;
	        default:
	            throw new IllegalArgumentException("Unexpected value: " + typeFen);
	    }
	    System.out.println(playerfordifusion2.getJoueur().toString() +" Dragged "+typeFen);
//    System.out.println("Type de fenetre du joueur créé : " + windowConfigurationPlayerInfos.getTypeFenetre());
	}
	/**
	 * Handle standard case.
	 *
	 * @param typeFen the type fen
	 * @param playerIndex the player index
	 */
//	public void handleStandardCase(String typeFen, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
//	    int tabIndex = typeFen.equals("player") ? 0 : playerIndex;
//	    windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(tabIndex);
//	    updateSelectedTab(typeFen);
//	    
	 // création/mise à jour de la fenetre de config de tab
		
	/**
	 * Handle full case.
	 *
	 * @param full the full
	 */
	//newPlayer = true quand player cliquer depuis window tournament tree
	public void handleFullCase(boolean full) {
//		this.windowTournamentTree.windowConfigPlayerFull = playerfordifusion2.getPlacementFrameTwoPlayer();
		if ((windowTournamentTree.windowConfigPlayerFull != null || windowTournamentTree.windowConfigPlayerFull.isDisplayable())) {// la fenetre full existe
		    //PlayerForDiffusion[] tableauPlayerDiffusionTree = frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree();
		    ArrayList<PlayerForDiffusion> listPlayerDiffusionTree = new ArrayList<>(Arrays.asList(windowTournamentTree.tabPlayerForTree));
		    windowTournamentTree.windowConfigPlayerFull.tabbedPane.setSelectedIndex(findPlayerIndex(listPlayerDiffusionTree));
		    updateSelectedTab("full");
		    windowTournamentTree.windowConfigPlayerFull.pack();
		    frameForDiffusion.getWindowTournamentTreeFromBroadcast().getPanelAnimationConfiguration().getPanelTournamentTree().setPlayer(playerfordifusion2.getNumeroPlayer(), playerfordifusion2);
		}
	}
	
	/**
	 * Update selected tab.
	 *
	 * @param typeFen the type fen
	 */
	private void updateSelectedTab(String typeFen) {
		Component selectedComponent = null;
		if(typeFen != "full") 
			selectedComponent = windowTournamentTree.windowConfigPlayer.tabbedPane.getSelectedComponent();
		else
			selectedComponent = windowTournamentTree.windowConfigPlayerFull.tabbedPane.getSelectedComponent();
	    
	    if (selectedComponent instanceof TabConfigurationPlayerInfos) {
	        TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
	        System.out.println("Player dragged : " + playerfordifusion2.getJoueur().getNom() + " for window " + typeFen.toUpperCase());
	        currentTab.refreshSpinner(playerfordifusion2);
	    } else {
	        System.out.println("Error: Selected component is not an instance of TabConfigurationPlayerInfos");
	        if(typeFen != "full") 
	        	windowTournamentTree.windowConfigPlayer.tabbedPane.setSelectedIndex(playerfordifusion2.getNumeroPlayer() + 1);
			else
				windowTournamentTree.windowConfigPlayerFull.tabbedPane.setSelectedIndex(playerfordifusion2.getNumeroPlayer() + 1);
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