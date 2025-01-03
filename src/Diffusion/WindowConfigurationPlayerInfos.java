/*
 * 
 */
package Diffusion;

/*
 * fenetre qui comporte les onglet de position de chaque joueur et l'onglet de style
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import DiffusionPlayers.PlayerForDiffusion;
import Police.TabPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Sauvegarde.ElementJoueur;
import Sauvegarde.ElementJoueurFull;
import Sauvegarde.ElementPoliceJoueur;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowConfigurationPlayerInfos.
 */
public class WindowConfigurationPlayerInfos extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The Enum FrameType.
	 */
	public enum FrameType {
/** The player. */
player, 
 /** The game. */
 game, 
 /** The tab. */
 tab, 
 /** The full. */
 full,
/** The autre. */
autre}
	
	/** The frame type. */
	private FrameType frameType;
	
	/** The display frame. */
	public WindowBroadcastPublic displayFrame;

/** The tabbed pane. */
//	private String typeFenetre;
	public JTabbedPane tabbedPane = new JTabbedPane();
	
	/** The button save config. */
	private JButton buttonSaveConfig = new JButton("Save config");
	
	/** The tab infos J 1. */
	public TabConfigurationPlayerInfos tabInfosJ1;
	
	/** The tab infos J 2. */
	public TabConfigurationPlayerInfos tabInfosJ2;
	
	/** The tab police. */
	public TabPolice tabPolice;
	
	/** The name event. */
	private String nameEvent;
	
	/** The Constant CONFIG_DIR. */
	private static final String CONFIG_DIR = "Config/";
	
	/** The Constant JSON_EXT. */
	private static final String JSON_EXT = ".json";
	
	/**
	 * Instantiates a new window configuration player infos.
	 *
	 * @param sonFrame the son frame
	 * @param typeFrame the type frame
	 */
	public WindowConfigurationPlayerInfos(WindowBroadcastPublic sonFrame, String typeFrame) {
		this.displayFrame = sonFrame;
		this.frameType = FrameType.valueOf(typeFrame.toLowerCase());
		
		initializeFrame();
		initializeSaveButton();
		initializePanel();
		
		this.nameEvent = this.displayFrame.getNameEvent();
	}
	
	/**
	 * Sets the type fenetre.
	 *
	 * @param typeFenetre the new type fenetre
	 */
	public void setTypeFenetre(String typeFenetre) {
		this.frameType = FrameType.valueOf(typeFenetre.toLowerCase());
		setTitle("Configuration Player Information : "+typeFenetre);
	}
	
	/**
	 * Gets the type fenetre.
	 *
	 * @return the type fenetre
	 */
	public String getTypeFenetre() {
		return this.frameType.toString().toLowerCase();
	}
	
	/**
	 * Sets the tab police.
	 *
	 * @param tabPolice the new tab police
	 */
	public void setTabPolice(TabPolice tabPolice) {
		this.tabPolice = tabPolice;
		tabbedPane.addTab("Style", tabPolice);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
		confirmAllTab();
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();	
	}
	
	/**
	 * Gets the tab police.
	 *
	 * @return the tab police
	 */
	public TabPolice getTabPolice() {
		return tabPolice;
	}
	
	/**
	 * Adds the tab joueur.
	 *
	 * @param tabInfos the tab infos
	 */
	public void addTabJoueur(TabConfigurationPlayerInfos tabInfos) {
		this.tabbedPane.addTab(tabInfos.getEnteteTab(), tabInfos);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
	}
	
	/**
	 * Insert tab joueur.
	 *
	 * @param tabInfos the tab infos
	 * @param indexTab the index tab
	 */
	public void insertTabJoueur(TabConfigurationPlayerInfos tabInfos, int indexTab) {
		this.tabbedPane.insertTab(tabInfos.getEnteteTab(), null, tabInfos, null, indexTab);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
	}
	
	/**
	 * Gets the all tab.
	 *
	 * @return the all tab
	 */
	public JTabbedPane getAllTab() {
		return tabbedPane;
	}

	/**
	 * Initialize frame.
	 */
	private void initializeFrame() {
        setTitle("Configuration Player Information :  "+this.frameType.toString().toLowerCase());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 700);
        setIconImage(new ImageIcon("icon.png").getImage());
    }
    
    /**
     * Initialize save button.
     */
    private void initializeSaveButton() {
        buttonSaveConfig.addActionListener(this::saveConfig);
    }
    
    /**
     * Initialize panel.
     */
    private void initializePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.NORTH);
        panel.add(buttonSaveConfig, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
    
    /**
     * Save config.
     *
     * @param e the e
     */
    private void saveConfig(ActionEvent e) {
    	int choice = JOptionPane.showConfirmDialog(null, "Do you want to update position and font of players?",
				"Players position, font update", JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.YES_OPTION)
			System.out.println("update position, font players selected");
		else
			System.out.println("don't update position, font players selected");
		
		if (choice == JOptionPane.YES_OPTION) {
			confirmAllTab();
//        refreshAllTab();

			switch (frameType) {
			case player:
				saveSinglePlayer();
				break;
			case game:
				saveGame();
				break;
			case tab:
				saveTab();
				break;
			case full:
				saveFull();
				break;
			default:
				break;
			}
		}
    }
    
    /**
     * Save single player.
     */
    private void saveSinglePlayer() {
        TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) tabbedPane.getComponentAt(0);
        PlayerForDiffusion infosPlayerDetails = currentTab.getInfosPlayerDetails();
        if (infosPlayerDetails != null) {
            infosPlayerDetails.enegistrerDetailsJoueurs();
            ConfigurationSaveLoad.saveWindows(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), infosPlayerDetails.mapJoueurDetails.getMapJoueurDetails(), null);
        }
    }
    
    /**
     * Save game.
     */
    private void saveGame() {
        ArrayList<Map<JPanel, JLabel>> joueurDetailsGame = getJoueurDetails();
        ConfigurationSaveLoad.saveWindowsMultiTab(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), joueurDetailsGame);
    }
    
    /**
     * Save tab.
     */
    private void saveTab() {
    	ArrayList<Map<JPanel, JLabel>> joueurDetails = getJoueurDetails();
        ConfigurationSaveLoad.saveWindowsMultiTab(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), joueurDetails);
    }
    
    /**
     * Save full.
     */
    private void saveFull() {
        ElementJoueurFull elementJoueurFull = new ElementJoueurFull();
        for (int i = 0; i < tabbedPane.getTabCount() - 1; i++) {
            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) tabbedPane.getComponentAt(i);
            PlayerForDiffusion pfdFromTab = currentTab.getInfosPlayerDetails();
            if (pfdFromTab != null) {
                pfdFromTab.enegistrerDetailsJoueurs();
                processPlayerDetails(pfdFromTab, elementJoueurFull);
            }
        }
        
        ElementJoueurFull ReadJson = ConfigurationSaveLoad.readJsonFileFull("Config/" + displayFrame.getNameEvent() + "/" + frameType.toString().toLowerCase() + ".json");
        ConfigurationSaveLoad.updateElementJoueurFull(displayFrame.getNameEvent(), ReadJson, elementJoueurFull);
    }
    
    /**
     * Gets the joueur details.
     *
     * @return the joueur details
     */
    private ArrayList<Map<JPanel, JLabel>> getJoueurDetails() {
    	ArrayList<Map<JPanel, JLabel>> joueurDetails = new ArrayList<>();
        for (int i = 0; i < tabbedPane.getTabCount() - 1; i++) {
            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) tabbedPane.getComponentAt(i);
            PlayerForDiffusion infosDetails = currentTab.getInfosPlayerDetails();
            if (infosDetails != null) {
                infosDetails.enegistrerDetailsJoueurs();
                joueurDetails.add(infosDetails.mapJoueurDetails.getMapJoueurDetails());
            }
        }
        return joueurDetails;
    }
    
    /**
     * Process player details.
     *
     * @param pfdFromTab the pfd from tab
     * @param elementJoueurFull the element joueur full
     */
    private void processPlayerDetails(PlayerForDiffusion pfdFromTab, ElementJoueurFull elementJoueurFull) {
        Map<String, Map<String, ElementJoueur>> playerList = new HashMap<>();
        Map<String, ElementJoueur> player = new HashMap<>();
        ConfigurationSaveLoad configData = ConfigurationSaveLoad.loadConfigFromFile(getEmplacementPlayer());
        
        for (Map.Entry<JPanel, JLabel> entry : pfdFromTab.mapJoueurDetails.getMapJoueurDetails().entrySet()) {
            JPanel panel = entry.getKey();
            JLabel label = entry.getValue();
            
            ElementJoueur playerElement = new ElementJoueur();
            ElementPoliceJoueur playerPoliceFull = new ElementPoliceJoueur();
            
            ElementJoueur element = configData.getElement(getEmplacementPlayer(), this.nameEvent, getTypeFenetre(), panel.getName(), pfdFromTab.getNumeroPlayer());
			if (panel.isVisible()) {
				playerElement.setPositionX(panel.getX());
				playerElement.setPositionY(panel.getY());
			}else {
				playerElement.setPositionX(element.getPositionX());
				playerElement.setPositionY(element.getPositionY());
			}
            
            playerPoliceFull.setVisible(panel.isVisible());
            if ("ImgJoueur".equals(panel.getName()) || "ImgFlag".equals(panel.getName())) {
                playerPoliceFull.setTaille(panel.getHeight());
            }
            if(panel.getComponents()[0] instanceof JLabel)
				label = (JLabel) panel.getComponents()[0];
            playerPoliceFull.setFont(FontSerializer(label.getFont()));
            playerPoliceFull.setColor(ColorSerializer(label.getForeground()));
            
            player.put(panel.getName(), playerElement);
            elementJoueurFull.getPlayerPolice().put(panel.getName(), playerPoliceFull);
        }
        
        playerList.put("player" + pfdFromTab.getNumeroPlayer(), player);
        elementJoueurFull.getPlayer().add(playerList);
    }

	/**
	 * Refresh all tab.
	 */
	public void refreshAllTab() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setSelectedIndex(i);
			// Check if the selected component is an instance of tabInfosPlayer
			Component selectedComponent = tabbedPane.getSelectedComponent();
			if (selectedComponent instanceof TabConfigurationPlayerInfos) {
				TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
				currentTab.refreshSpinner(currentTab.getInfosPlayerDetails());
			}
		}
	}

	/**
	 * Confirm all tab.
	 */
	public void confirmAllTab() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setSelectedIndex(i);
			// Check if the selected component is an instance of tabInfosPlayer
			Component selectedComponent = tabbedPane.getSelectedComponent();
			if (selectedComponent instanceof TabConfigurationPlayerInfos) {
				TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
				currentTab.confirmTabPlayer();
			}
		}
	}

	/**
	 * Font serializer.
	 *
	 * @param fontToSerialize the font to serialize
	 * @return the string
	 */
	public static String FontSerializer(Font fontToSerialize) {
		String SerializeFont = fontToSerialize.getName() + "," + fontToSerialize.getStyle() + ","
				+ fontToSerialize.getSize();
		return SerializeFont;
	}

	/**
	 * Color serializer.
	 *
	 * @param colorToSerialize the color to serialize
	 * @return the string
	 */
	public static String ColorSerializer(Color colorToSerialize) {
		String SerializeColor = colorToSerialize.getRed() + "," + colorToSerialize.getGreen() + ","
				+ colorToSerialize.getBlue();
		return SerializeColor;
	}
	
	/**
	 * Gets the emplacement player.
	 *
	 * @return the emplacement player
	 */
	private String getEmplacementPlayer() {
	    return switch (frameType) {
	        case player -> CONFIG_DIR + this.nameEvent + "/player" + JSON_EXT;
	        case game -> CONFIG_DIR + this.nameEvent + "/game" + JSON_EXT;
	        case tab -> CONFIG_DIR + this.nameEvent + "/tab" + JSON_EXT;
	        case full -> CONFIG_DIR + this.nameEvent + "/full" + JSON_EXT;
	        default -> throw new IllegalArgumentException("Unexpected value: " + frameType);
	    };
	}
}
