package Diffusion;

/*
 * permet de placer tout les elements du joueur sur la fenetre de diffusion et de les deplacer
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Main.BDD_v2;
import Main.ImageUtility;
import Players.Joueur;
import Police.TabPolice;
import Police.chosenPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Sauvegarde.ElementJoueur;
import Sauvegarde.ElementPoliceJoueur;

public class PlayerForDiffusion extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public JPanel playerName;
	public JPanel playerSurname;
	public JPanel playerImg;
	public JPanel playerFlag;
	public JPanel playerAcro;
	public JPanel playerRank;
	public JPanel playerBirthdate;
	public JPanel playerBirthplace;
	public JPanel playerHeight;
	public JPanel playerWeight;
	public JPanel playerHand;
	public JPanel playerAge;
	public JPanel playerPrizetotal;
	public JPanel playerCityresidence;
	public JPanel playerLine;
	// -----------------------------------
	public JLabel nameLabel;
	public JLabel SurnameLabel;
	public ImageUtility ImgLabel;
	public ImageUtility FlagLabel;
	public JLabel AcroLabel;
	public JLabel RankLabel;
	public JLabel birthdateLabel;
	public JLabel birthPlaceLabel;
	public JLabel heightLabel;
	public JLabel weightLabel;
	public JLabel handLabel;
	public JLabel ageLabel;
	public JLabel prizetotalLabel;
	public JLabel cityresidenceLabel;
	public JLabel lineLabel;
	// --------------------------------
	public chosenPolice policeName;
	public chosenPolice policeSurname;
	public chosenPolice policeAcro;
	public chosenPolice policeRank;
	public chosenPolice policeBirthDate;
	public chosenPolice policeBirthPlace;
	public chosenPolice policeHeight;
	public chosenPolice policeWeight;
	public chosenPolice policeHand;
	public chosenPolice policeAge;
	public chosenPolice policePrizetotal;
	public chosenPolice policeCityresidence;
	public chosenPolice policeLine;
	//-----------------------------------------
	public int tailleImgJoueur;
	public int tailleImgFlag;
	
	private Joueur joueur;
	private WindowBroadcastPublic frameForDiffusion;
	private WindowConfigurationPlayerInfos windowConfigurationPlayerInfos;
	public JoueurDetails mapJoueurDetails;
	private String typeFen;
	private PlayerForDiffusion playerfordifusion2;
	private String nomEvent;
	private int numeroPlayer;
	private PanelAnimationConfiguration animationPanel;
	private ZoomablePanel panelPlayerGlobal;
	private static final String CONFIG_DIR = "Config/";
	private static final String JSON_EXT = ".json";

	public int getNumeroPlayer() {
		return numeroPlayer;
	}
	public void setNumeroPlayer(int numeroPlayerIndex) {
		this.numeroPlayer = numeroPlayerIndex;
	}
	public void setPlacementFrameTwoPlayer(WindowConfigurationPlayerInfos placementFrameTwoPlayer) {
		this.windowConfigurationPlayerInfos = placementFrameTwoPlayer;
	}
	public WindowConfigurationPlayerInfos getPlacementFrameTwoPlayer() {
		return windowConfigurationPlayerInfos;
	}
	public String getTypeFen() {
		return typeFen;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	public JPanel getPanelGlobal() {
		return this.panelPlayerGlobal;
	}
	public PanelAnimationConfiguration getWindowAnimationConfiguration(){
		return animationPanel;
	}
	
	public PlayerForDiffusion(String nomEvent, WindowBroadcastPublic diffusionFrame,PanelAnimationConfiguration panelAnimationConfig,String typeFrame, int numeroPlayer) {
		this.frameForDiffusion = diffusionFrame;
		this.typeFen = typeFrame;
		this.nomEvent = nomEvent;
		this.numeroPlayer = numeroPlayer;
//		this.animationFrame = frameForDiffusion.getAnimationFrame();
		this.animationPanel = panelAnimationConfig;

		initializePolice();
		createPlayerPanels();
		recupInfosPlayer(getEmplacementPlayer());
		playerfordifusion2 = this;
	}
	
	private void initializePolice() {
		policeName = createPolice() ;
		policeSurname = createPolice() ;
		policeAcro = createPolice() ;
		policeRank = createPolice() ;
		policeBirthDate = createPolice() ;
		policeBirthPlace = createPolice() ;
		policeHeight = createPolice() ;
		policeWeight = createPolice() ;
		policeHand = createPolice() ;
		policeAge = createPolice() ;
		policePrizetotal = createPolice() ;
		policeCityresidence = createPolice() ;
		policeLine = createPolice() ;
	}
	private chosenPolice createPolice() {
		chosenPolice policeInitial = new chosenPolice();
		policeInitial.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeInitial.setNewColor(Color.BLACK);
		return policeInitial;
	}
	private void createPlayerPanels() {
	    String[] panelNames = {"Name", "Surname", "ImgJoueur", "ImgFlag", "Acronyme", "Rank", "Birthdate", "Birthplace", "Height", "Weight", "Hand", "Age", "Prizetotal", "CityResidence", "Line"};
	    for (String name : panelNames) {
	        JPanel panel = createPlayerPanel(name);
	        switch (name) {
	            case "Name": playerName = panel; break;
	            case "Surname": playerSurname = panel; break;
	            case "ImgJoueur": playerImg = panel; break;
	            case "ImgFlag": playerFlag = panel; break;
	            case "Acronyme": playerAcro = panel; break;
	            case "Rank": playerRank = panel; break;
	            case "Birthdate": playerBirthdate = panel; break;
	            case "Birthplace": playerBirthplace = panel; break;
	            case "Height": playerHeight = panel; break;
	            case "Weight": playerWeight = panel; break;
	            case "Hand": playerHand = panel; break;
	            case "Age": playerAge = panel; break;
	            case "Prizetotal": playerPrizetotal = panel; break;
	            case "CityResidence": playerCityresidence = panel; break;
	            case "Line": playerLine = panel; break;
	        }
	    }
	}
	private JPanel createPlayerPanel(String name) {
	    JPanel panel = new JPanel();
	    panel.setOpaque(false);
	    panel.setName(name);
	    MouseAdapterPanel mouseAdapter = new MouseAdapterPanel(panel);
	    panel.addMouseListener(mouseAdapter);
	    panel.addMouseMotionListener(mouseAdapter);
	    return panel;
	}
	private String getEmplacementPlayer() {
	    return switch (typeFen) {
	        case "player" -> CONFIG_DIR + nomEvent + "/player" + JSON_EXT;
	        case "game" -> CONFIG_DIR + nomEvent + "/game" + JSON_EXT;
	        case "tab" -> CONFIG_DIR + nomEvent + "/tab" + JSON_EXT;
	        case "full" -> CONFIG_DIR + nomEvent + "/full" + JSON_EXT;
	        default -> throw new IllegalArgumentException("Unexpected value: " + typeFen);
	    };
	}
	public void setPlayer(Joueur joueur, int ligne) throws ClassNotFoundException, SQLException {
	    this.joueur = joueur;
	    
	    // Créer une map pour stocker les informations du joueur
	    Map<JPanel, LabelInfo> playerInfo = new LinkedHashMap<>();
	    playerInfo.put(playerName, new LabelInfo(joueur.getNom(), policeName));
	    playerInfo.put(playerSurname, new LabelInfo(joueur.getPrenom(), policeSurname));
	    playerInfo.put(playerAcro, new LabelInfo(joueur.getNatio_acronyme(), policeAcro));
	    playerInfo.put(playerRank, new LabelInfo(String.valueOf(joueur.getRank()), policeRank));
	    playerInfo.put(playerBirthdate, new LabelInfo(joueur.getBirthDate(), policeBirthDate));
	    playerInfo.put(playerBirthplace, new LabelInfo(joueur.getBirthPlace(), policeBirthPlace));
	    playerInfo.put(playerHeight, new LabelInfo(String.valueOf(joueur.getTaille()), policeHeight));
	    playerInfo.put(playerWeight, new LabelInfo(String.valueOf(joueur.getWeight()), policeWeight));
	    playerInfo.put(playerHand, new LabelInfo(joueur.getMain(), policeHand));
	    playerInfo.put(playerAge, new LabelInfo(String.valueOf(joueur.getAge()), policeAge));
	    playerInfo.put(playerPrizetotal, new LabelInfo(joueur.getPrizetotal(), policePrizetotal));
	    playerInfo.put(playerCityresidence, new LabelInfo(joueur.getCityResidence(), policeCityresidence));
	    playerInfo.put(playerLine, new LabelInfo(String.valueOf(ligne), policeLine));
	    // Mettre à jour tous les panneaux de texte
	    playerInfo.forEach((panel, info) -> updateTextPanel(panel, info));

	    // Mettre à jour les panneaux d'image
	    ImgLabel = new ImageUtility(joueur.getImgJoueur(), tailleImgJoueur);
	    FlagLabel = new ImageUtility(BDD_v2.getFlagImagePathByAcronym(joueur.getNatio_acronyme()),tailleImgFlag);
	    updateImagePanel(playerImg, ImgLabel);
	    updateImagePanel(playerFlag, FlagLabel);

	    // Créer et configurer le panneau global
	    setupGlobalPanel();

	    // Mettre à jour l'affichage
	    updateDisplay(ligne);    
	}

	private void updateTextPanel(JPanel panel, LabelInfo info) {
	    panel.removeAll();
	    JLabel label = new JLabel(info.text);
	    label.setForeground(info.police.getNewColor());
	    label.setFont(info.police.getNewfont());
	    panel.add(label);
	    panel.setSize(label.getPreferredSize().width, label.getPreferredSize().height + 5);
	}

	private void updateImagePanel(JPanel panel, ImageUtility imageLabel) {
	    panel.removeAll();
	    panel.add(imageLabel);
	    panel.setSize(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height + 5);
	}

	private void setupGlobalPanel() {
	    panelPlayerGlobal = new ZoomablePanel();
	    panelPlayerGlobal.setSize(50, 50);
	    panelPlayerGlobal.setLocation(this.frameForDiffusion.getWidth()/2-panelPlayerGlobal.getWidth()/2, 
	                                  this.frameForDiffusion.getHeight()/2-panelPlayerGlobal.getHeight()/2);
	    panelPlayerGlobal.setLayout(null);
	    panelPlayerGlobal.setOpaque(false);

	    // Ajouter tous les panneaux au panneau global
	    Arrays.asList(playerName, playerSurname, playerAcro, playerRank, playerBirthdate, playerBirthplace,
	                  playerHeight, playerWeight, playerHand, playerAge, playerPrizetotal, playerCityresidence,
	                  playerLine, playerImg, playerFlag).forEach(panelPlayerGlobal::add);
	}

	private void updateDisplay(int ligne) {
	    this.setLayout(null);
	    this.setOpaque(false);
	    this.add(panelPlayerGlobal);
	    this.setSize(panelPlayerGlobal.getPreferredSize());
	    this.setBounds(0, 0, this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());

	    if(ligne>0)
	    	handleWindowTypeSpecificBehavior();

	    this.frameForDiffusion.revalidate();
	    this.frameForDiffusion.repaint();
	}

	private void handleWindowTypeSpecificBehavior() {
	    switch (typeFen) {
	        case "full":
	            panelPlayerGlobal.setSize(this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
	            panelPlayerGlobal.setLocation(0, 0);
	            break;
	        case "player":
	        	
	        	animationPanel.zoomPanel(panelPlayerGlobal, frameForDiffusion, this::animatePlayerElements);
	            break;
	        default:
	        	animationPanel.zoomPanel(panelPlayerGlobal, frameForDiffusion, null);
	    }
	}

	private void animatePlayerElements() {
		SwingUtilities.invokeLater(() -> {
			PlayerForDiffusion endPlayer = this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer];
			if (endPlayer != null) {
				for (Component endComponent : endPlayer.getPanelGlobal().getComponents()) {
					if (endComponent.isVisible()) {
						Point endPoint = endComponent.getLocation();
						animateTextElement(endComponent, endPoint);
					}
				}
			}
		});
	}

	private void animateTextElement(Component endComponent, Point endPoint) {
	    for (Component startComponent : panelPlayerGlobal.getComponents()) {
	        if (startComponent.getName().equals(endComponent.getName())) {
	            JPanel endPanel = (JPanel) endComponent;
	            JPanel startPanel = (JPanel) startComponent;
	            Font endFont = endPanel.getComponents()[0].getFont();
	            Color endColor = endPanel.getComponents()[0].getForeground();
	            Dimension endDimension = endPanel.getPreferredSize();
	            
	            
	            animationPanel.animateLABEL(startPanel, endPoint, endDimension, endColor, endFont, JLayeredPane.POPUP_LAYER,this.frameForDiffusion.getLayeredPane(),
	                    () -> displayPlayerFullAndTournamentTreeAnimation()
	                    );
	            break;
	        }
	    }
	}
	private void displayPlayerFull() {
		if(animationPanel.isPlayerFullEnabled())
			this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer].setVisible(true);
		else
			this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer].setVisible(false);
	}
	private void displayPlayerFullAndTournamentTreeAnimation() {
		displayPlayerFull();
		if(animationPanel.isPathTreeAnimationEnabled())// && animationFrame.isbeginingAnimationTreeCheckBoxEnabled()
			animationPanel.getPanelTournamentTree().animatePlayerPath(this.numeroPlayer, animationPanel.getAnimationPathTreeDuration(), animationPanel.getNbBlinkTreeDuration());
//		if(animationFrame.isPathTreeAnimationEnabled() && !animationFrame.isbeginingAnimationTreeCheckBoxEnabled())
//			animationFrame.getPanelTournamentTree().animatePlayerPath(this.numeroPlayer, animationFrame.getAnimationPathTreeDuration(), animationFrame.getNbBlinkTreeDuration());
	}

	private static class LabelInfo {
	    String text;
	    chosenPolice police;

	    LabelInfo(String text, chosenPolice police) {
	        this.text = text;
	        this.police = police;
	    }
	}
	private void recupInfosPlayer(String emplacementPlayer) {
	    int index = numeroPlayer;
	    System.out.println("--- index du joueur a recup from " + typeFen + " : " + index + " | " + numeroPlayer + ", localisation : " + emplacementPlayer + " ---");
	    ConfigurationSaveLoad configData = ConfigurationSaveLoad.loadConfigFromFile(emplacementPlayer);
	    if (configData == null) {
	        setDefaultPositions();
	        return;
	    }

	    updatePlayerElements(configData, emplacementPlayer, index);
	}

	private void setDefaultPositions() {
	    System.out.println("! Config in JSON null --> defaut config");
	    int y = 0;
	    for (JPanel panel : Arrays.asList(playerName, playerSurname, playerAcro, playerRank, playerBirthdate, 
	                                      playerBirthplace, playerHeight, playerWeight, playerHand, playerAge, 
	                                      playerPrizetotal, playerCityresidence, playerLine)) {
	        panel.setLocation(10, y);
	        y += 20;
	    }
	    playerImg.setLocation(10, 260);
	    playerFlag.setLocation(10, 260);
	}

	private void updatePlayerElements(ConfigurationSaveLoad configData, String emplacementPlayer, int index) {
	    Map<String, Object[]> elements = new HashMap<>();
	    elements.put("Surname", new Object[]{playerSurname, policeSurname});
	    elements.put("Name", new Object[]{playerName, policeName});
	    elements.put("Acronyme", new Object[]{playerAcro, policeAcro});
	    elements.put("Rank", new Object[]{playerRank, policeRank});
	    elements.put("Birthdate", new Object[]{playerBirthdate, policeBirthDate});
	    elements.put("Birthplace", new Object[]{playerBirthplace, policeBirthPlace});
	    elements.put("Height", new Object[]{playerHeight, policeHeight});
	    elements.put("Weight", new Object[]{playerWeight, policeWeight});
	    elements.put("Hand", new Object[]{playerHand, policeHand});
	    elements.put("Age", new Object[]{playerAge, policeAge});
	    elements.put("Prizetotal", new Object[]{playerPrizetotal, policePrizetotal});
	    elements.put("CityResidence", new Object[]{playerCityresidence, policeCityresidence});
	    elements.put("Line", new Object[]{playerLine, policeLine});

	    for (Map.Entry<String, Object[]> entry : elements.entrySet()) {
	        String elementName = entry.getKey();
	        JPanel panel = (JPanel) entry.getValue()[0];
	        chosenPolice police = (chosenPolice) entry.getValue()[1];

	        ElementPoliceJoueur elementPolice = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, elementName, index);
	        panel.setVisible(elementPolice.isVisible());
//	        if (elementPolice.isVisible()) {
//	            updateElement(configData, emplacementPlayer, index, elementName, panel, police, elementPolice);
//	        }
	        updateElement(configData, emplacementPlayer, index, elementName, panel, police, elementPolice);
	    }

	    updateImageElement(configData, emplacementPlayer, index, "ImgJoueur", playerImg);
	    updateImageElement(configData, emplacementPlayer, index, "ImgFlag", playerFlag);
	}

	private void updateElement(ConfigurationSaveLoad configData, String emplacementPlayer, int index, String elementName, 
	                           JPanel panel, chosenPolice police, ElementPoliceJoueur elementPolice) {
		ElementJoueur element = configData.getElement(emplacementPlayer, nomEvent, typeFen, elementName, index);
	    if (element != null) {
	        panel.setLocation(element.getPositionX(), element.getPositionY());
	        String[] fontParts = elementPolice.getFont().split(",");
	        police.setNewfont(new Font(fontParts[0], Integer.parseInt(fontParts[1]), Integer.parseInt(fontParts[2])));
	        String[] colorParts = elementPolice.getColor().split(",");
	        police.setNewColor(new Color(Integer.parseInt(colorParts[0]), Integer.parseInt(colorParts[1]), Integer.parseInt(colorParts[2])));
	    }
	}

	private void updateImageElement(ConfigurationSaveLoad configData, String emplacementPlayer, int index, String elementName, JPanel panel) {
	    ElementPoliceJoueur elementPolice = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, elementName, index);
	    panel.setVisible(elementPolice.isVisible());
//	    if (elementPolice.isVisible()) {
	        ElementJoueur element = configData.getElement(emplacementPlayer, nomEvent, typeFen, elementName, index);
	        if (element != null) {
	            panel.setLocation(element.getPositionX(), element.getPositionY());
	            if (elementName.equals("ImgJoueur")) {
	                tailleImgJoueur = elementPolice.getTaille();
	            } else if (elementName.equals("ImgFlag")) {
	                tailleImgFlag = elementPolice.getTaille();
	            }
	        }
//	    }
	}

	class MouseAdapterPanel extends MouseAdapter {
		private JPanel panel;
		private Point prevPos;

		public MouseAdapterPanel(JPanel panel) {
			this.panel = panel;
		}

		public void mousePressed(MouseEvent e) {
			prevPos = e.getPoint();
			frameForDiffusion.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}

		public void mouseDragged(MouseEvent e) {
			int thisX = panel.getLocation().x;
			int thisY = panel.getLocation().y;

			int xMoved = thisX + (e.getX() - prevPos.x);
			
			int yMoved = thisY + (e.getY() - prevPos.y);
//			System.out.println("composant : "+panel.getName()+"|"+xMoved+"|"+yMoved);
			panel.setLocation(xMoved, yMoved);
		}

		public void mouseReleased(MouseEvent e) {
		    String typeFen = playerfordifusion2.getTypeFen();
		    int playerIndex = playerfordifusion2.getNumeroPlayer();

		    switch (typeFen) {
		        case "player":
		        case "game":
		        case "tab":
		            handleStandardCase(typeFen, playerIndex);
		            break;
		        case "full":
		            handleFullCase();
		            break;
		        default:
		            throw new IllegalArgumentException("Unexpected value: " + typeFen);
		    }

//		    System.out.println("Type de fenetre du joueur créé : " + windowConfigurationPlayerInfos.getTypeFenetre());
		}
	}
	
	public class JoueurDetails {
		public Map<JPanel, JLabel> joueurDetailsMap;

		public JoueurDetails() {
			joueurDetailsMap = new LinkedHashMap<>();
		}

		public void addDetails(JPanel panel, JLabel label) {
			joueurDetailsMap.put(panel, label);
		}

		public Map<JPanel, JLabel> getMapJoueurDetails() {
			return joueurDetailsMap;
		}
	}
	private void handleStandardCase(String typeFen, int playerIndex) {
	    int tabIndex = typeFen.equals("player") ? 0 : playerIndex;
	    windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(tabIndex);
	    updateSelectedTab(typeFen);
	}

	//newPlayer = true quand player cliquer depuis window tournament tree
	public void handleFullCase() {
	    PlayerForDiffusion[] tableauPlayerDiffusionTree = frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree();
	    ArrayList<PlayerForDiffusion> listPlayerDiffusionTree = new ArrayList<>();

	    if ((windowConfigurationPlayerInfos == null || !windowConfigurationPlayerInfos.isDisplayable())) {// la fenetre full n'existe pas
	    	System.out.println("Creation de la fenetre de config full");
	        createNewFullWindowConfig(tableauPlayerDiffusionTree, listPlayerDiffusionTree);
	    } else if (windowConfigurationPlayerInfos.getTypeFenetre().equals("full")) {//la fenetre existe donc mettre a jour joueur electionne si il existe pas l'inserer
	    	System.out.println("Fen config full existe, update spinner selected joueur, si selected joueur doesn't exist l'inserer");
	        updateExistingFullWindowConfig(listPlayerDiffusionTree);
	    } else {// si arrive ici ca veut dire qu'il faut afficher tout les joueurs selectionne de l'arbre (btn full)
	    	System.out.println("Creation fen configfull a partir des joueur selectione dans windows tournament tree");
	        recreateFullWindowConfig(tableauPlayerDiffusionTree, listPlayerDiffusionTree);
	    }
	    
	    windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(findPlayerIndex(listPlayerDiffusionTree));
	    updateSelectedTab("full");
	    windowConfigurationPlayerInfos.pack();
	    animationPanel.getPanelTournamentTree().setPlayer(numeroPlayer, playerfordifusion2);
	}

	private void createNewFullWindowConfig(PlayerForDiffusion[] tableauPlayerDiffusionTree, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    windowConfigurationPlayerInfos = new WindowConfigurationPlayerInfos(frameForDiffusion, "full");
	    for (PlayerForDiffusion player : tableauPlayerDiffusionTree) {
	        if (player != null) {
	            addPlayerToFullConfig(player, listPlayerDiffusionTree);
	        }
	    }
	    finalizeFullWindowConfig(listPlayerDiffusionTree);
	}

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
	    	System.out.println("insertion d'un joeur");
	    	insertPlayerToFullConfig(playerfordifusion2, listPlayerDiffusionTree);
	    }
	    windowConfigurationPlayerInfos.getTabPolice().setListPlayer(listPlayerDiffusionTree);
	}

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

	private void addPlayerToFullConfig(PlayerForDiffusion player, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    listPlayerDiffusionTree.add(player);
	    player.setPlacementFrameTwoPlayer(windowConfigurationPlayerInfos);
	    TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(player, player.getJoueur(), frameForDiffusion, windowConfigurationPlayerInfos);
	    windowConfigurationPlayerInfos.addTabJoueur(tabFull);
//	    windowConfigurationPlayerInfos.insertTabJoueur(tabFull,listPlayerDiffusionTree.size());
	    System.out.println("FULL player to display : " + player.getJoueur().getNom());
	}
	private void insertPlayerToFullConfig(PlayerForDiffusion player, ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    player.setPlacementFrameTwoPlayer(windowConfigurationPlayerInfos);
	    TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(player, player.getJoueur(), frameForDiffusion, windowConfigurationPlayerInfos);
	    windowConfigurationPlayerInfos.insertTabJoueur(tabFull,listPlayerDiffusionTree.size());
	    listPlayerDiffusionTree.add(player);
	    System.out.println("FULL player to display : " + player.getJoueur().getNom());
	}

	private void finalizeFullWindowConfig(ArrayList<PlayerForDiffusion> listPlayerDiffusionTree) {
	    if (!listPlayerDiffusionTree.isEmpty()) {
	        windowConfigurationPlayerInfos.setTabPolice(new TabPolice(listPlayerDiffusionTree, windowConfigurationPlayerInfos));
	        windowConfigurationPlayerInfos.pack();
	    }
	    frameForDiffusion.getWindowTournamentTreeFromBroadcast().windowConfigPlayerFull = windowConfigurationPlayerInfos;
	}

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
	
	public void enegistrerDetailsJoueurs() {
		mapJoueurDetails = new JoueurDetails();
		mapJoueurDetails.addDetails(this.playerName, this.nameLabel);
		mapJoueurDetails.addDetails(this.playerSurname, this.SurnameLabel);
		mapJoueurDetails.addDetails(this.playerImg, this.ImgLabel);
		mapJoueurDetails.addDetails(this.playerFlag, this.FlagLabel);
		mapJoueurDetails.addDetails(this.playerAcro, this.AcroLabel);
		mapJoueurDetails.addDetails(this.playerRank,this.RankLabel);
		mapJoueurDetails.addDetails(this.playerBirthdate, this.birthdateLabel);
		mapJoueurDetails.addDetails(this.playerBirthplace, this.birthPlaceLabel);
		mapJoueurDetails.addDetails(this.playerHeight, this.heightLabel);
		mapJoueurDetails.addDetails(this.playerWeight, this.weightLabel);
		mapJoueurDetails.addDetails(this.playerHand, this.handLabel);
		mapJoueurDetails.addDetails(this.playerAge, this.ageLabel);
		mapJoueurDetails.addDetails(this.playerPrizetotal, this.prizetotalLabel);
		mapJoueurDetails.addDetails(this.playerCityresidence, this.cityresidenceLabel);
		mapJoueurDetails.addDetails(this.playerLine, this.lineLabel);
	}
	public void close() {
		//this.placementFrame.dispose();
		this.windowConfigurationPlayerInfos.dispose();
//		new Configuration().saveConfiguration(playerName.getX(), playerName.getY(), placementFrame.checkboxName.isSelected(), playerName.getFont().toString());
	}
	private int findPlayerIndex(ArrayList<PlayerForDiffusion> players) {
	    return IntStream.range(0, players.size())
	            .filter(i -> playerfordifusion2.getNumeroPlayer() == players.get(i).getNumeroPlayer())
	            .findFirst()
	            .orElse(-1);
	}
}
class ZoomablePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double scale = 1.0;

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void addComponent(Component comp) {
        add(comp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        super.paintChildren(g2d);
    }
}
