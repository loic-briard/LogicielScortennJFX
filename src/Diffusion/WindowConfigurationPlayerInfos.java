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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Police.TabPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Sauvegarde.ElementJoueur;
import Sauvegarde.ElementJoueurFull;
import Sauvegarde.ElementPoliceJoueur;

public class WindowConfigurationPlayerInfos extends JFrame {
	private static final long serialVersionUID = 1L;
	public enum FrameType {player, game, tab, full,autre}
	private FrameType frameType;
	
	public WindowBroadcastPublic displayFrame;
//	private String typeFenetre;
	public JTabbedPane tabbedPane = new JTabbedPane();
	private JButton buttonSaveConfig = new JButton("Save config");
	public TabConfigurationPlayerInfos tabInfosJ1;
	public TabConfigurationPlayerInfos tabInfosJ2;
	public TabPolice tabPolice;
	private String nameEvent;
	private static final String CONFIG_DIR = "Config/";
	private static final String JSON_EXT = ".json";
	
	public WindowConfigurationPlayerInfos(WindowBroadcastPublic sonFrame, String typeFrame) {
		this.displayFrame = sonFrame;
		this.frameType = FrameType.valueOf(typeFrame.toLowerCase());
		
		initializeFrame();
		initializeSaveButton();
		initializePanel();
		
		this.nameEvent = this.displayFrame.getNameEvent();
	}
	public void setTypeFenetre(String typeFenetre) {
		this.frameType = FrameType.valueOf(typeFenetre.toLowerCase());
		setTitle("Configuration Player Information : "+typeFenetre);
	}
	public String getTypeFenetre() {
		return this.frameType.toString().toLowerCase();
	}
	
	public void setTabPolice(TabPolice tabPolice) {
		this.tabPolice = tabPolice;
		tabbedPane.addTab("Style", tabPolice);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
		confirmAllTab();
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();	
	}
	public TabPolice getTabPolice() {
		return tabPolice;
	}
	
	public void addTabJoueur(TabConfigurationPlayerInfos tabInfos) {
		this.tabbedPane.addTab(tabInfos.getJoueurName(), tabInfos);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
	}
	public void insertTabJoueur(TabConfigurationPlayerInfos tabInfos, int indexTab) {
		this.tabbedPane.insertTab(tabInfos.getJoueurName(), null, tabInfos, null, indexTab);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
	}
	
	public JTabbedPane getAllTab() {
		return tabbedPane;
	}

	private void initializeFrame() {
        setTitle("Configuration Player Information :  "+this.frameType.toString().toLowerCase());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 700);
        setIconImage(new ImageIcon("icon.png").getImage());
    }
    
    private void initializeSaveButton() {
        buttonSaveConfig.addActionListener(this::saveConfig);
    }
    
    private void initializePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.NORTH);
        panel.add(buttonSaveConfig, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
    private void saveConfig(ActionEvent e) {
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
    private void saveSinglePlayer() {
        TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) tabbedPane.getComponentAt(0);
        PlayerForDiffusion infosPlayerDetails = currentTab.getInfosPlayerDetails();
        if (infosPlayerDetails != null) {
            infosPlayerDetails.enegistrerDetailsJoueurs();
            ConfigurationSaveLoad.saveWindows(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), infosPlayerDetails.mapJoueurDetails.getMapJoueurDetails(), null);
        }
    }
    private void saveGame() {
        ArrayList<Map<JPanel, JLabel>> joueurDetailsGame = getJoueurDetails();
        ConfigurationSaveLoad.saveWindowsMultiTab(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), joueurDetailsGame);
    }
    private void saveTab() {
    	ArrayList<Map<JPanel, JLabel>> joueurDetails = getJoueurDetails();
        ConfigurationSaveLoad.saveWindowsMultiTab(displayFrame.getNameEvent(), frameType.toString().toLowerCase(), joueurDetails);
    }
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

	public static String FontSerializer(Font fontToSerialize) {
		String SerializeFont = fontToSerialize.getName() + "," + fontToSerialize.getStyle() + ","
				+ fontToSerialize.getSize();
		return SerializeFont;
	}

	public static String ColorSerializer(Color colorToSerialize) {
		String SerializeColor = colorToSerialize.getRed() + "," + colorToSerialize.getGreen() + ","
				+ colorToSerialize.getBlue();
		return SerializeColor;
	}
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
