/*
 * 
 */
package DiffusionPlayers;

/*
 * permet de placer tout les elements du joueur sur la fenetre de diffusion et de les deplacer
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Animation.PanelAnimationConfiguration;
import Animation.ZoomablePanel;
import Diffusion.WindowBroadcastPublic;
import Diffusion.WindowConfigurationPlayerInfos;
import Diffusion.WindowTournamentTree;
import Event.Evenement;
import GlobalSettings.GlobalSettings;
import Main.BDD_v2;
import Main.ImageUtility;
import Players.Joueur;
import Police.chosenPolice;
import Sauvegarde.ConfigurationSaveLoad;
import Sauvegarde.ElementJoueur;
import Sauvegarde.ElementPoliceJoueur;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerForDiffusion.
 */
public class PlayerForDiffusion extends JPanel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The player name. */
	public JPanel playerName;
	
	/** The player surname. */
	public JPanel playerSurname;
	
	/** The player surname. */
	public JPanel playerDisplayName;
	
	/** The player img. */
	public JPanel playerImg;
	
	/** The player flag. */
	public JPanel playerFlag;
	
	/** The player acro. */
	public JPanel playerAcro;
	
	/** The player acro. */
	public JPanel playerCountry;
	
	/** The player rank. */
	public JPanel playerRank;
	
	/** The player birthdate. */
	public JPanel playerBirthdate;
	
	/** The player birthplace. */
	public JPanel playerBirthplace;
	
	/** The player height. */
	public JPanel playerHeight;
	
	/** The player weight. */
	public JPanel playerWeight;
	
	/** The player hand. */
	public JPanel playerHand;
	
	/** The player age. */
	public JPanel playerAge;
	
	/** The player prizetotal. */
	public JPanel playerPrizetotal;
	
	/** The player cityresidence. */
	public JPanel playerCityresidence;
	
	/** The player tete de serie. */
	public JPanel playerTeteDeSerie;
	
	/** The player line. */
	public JPanel playerLine;
	
	/** The name label. */
	// -----------------------------------
	public JLabel nameLabel;
	
	/** The Surname label. */
	public JLabel SurnameLabel;
	/** The Surname label. */
	public JLabel DisplayNameLabel;
	
	/** The Img label. */
	public ImageUtility ImgLabel;
	
	/** The Flag label. */
	public ImageUtility FlagLabel;
	
	/** The Acro label. */
	public JLabel AcroLabel;
	
	/** The Acro label. */
	public JLabel CountryLabel;
	
	/** The Rank label. */
	public JLabel RankLabel;
	
	/** The birthdate label. */
	public JLabel birthdateLabel;
	
	/** The birth place label. */
	public JLabel birthPlaceLabel;
	
	/** The height label. */
	public JLabel heightLabel;
	
	/** The weight label. */
	public JLabel weightLabel;
	
	/** The hand label. */
	public JLabel handLabel;
	
	/** The age label. */
	public JLabel ageLabel;
	
	/** The prizetotal label. */
	public JLabel prizetotalLabel;
	
	/** The cityresidence label. */
	public JLabel cityresidenceLabel;
	
	/** The cityresidence label. */
	public JLabel teteDeSerieLabel;
	
	/** The line label. */
	public JLabel lineLabel;
	
	/** The police name. */
	// --------------------------------
	public chosenPolice policeName;
	
	/** The police surname. */
	public chosenPolice policeSurname;
	/** The police surname. */
	public chosenPolice policeDisplayName;
	
	/** The police acro. */
	public chosenPolice policeAcro;
	
	/** The police acro. */
	public chosenPolice policeCountry;
	
	/** The police rank. */
	public chosenPolice policeRank;
	
	/** The police birth date. */
	public chosenPolice policeBirthDate;
	
	/** The police birth place. */
	public chosenPolice policeBirthPlace;
	
	/** The police height. */
	public chosenPolice policeHeight;
	
	/** The police weight. */
	public chosenPolice policeWeight;
	
	/** The police hand. */
	public chosenPolice policeHand;
	
	/** The police age. */
	public chosenPolice policeAge;
	
	/** The police prizetotal. */
	public chosenPolice policePrizetotal;
	
	/** The police cityresidence. */
	public chosenPolice policeCityresidence;
	
	/** The police cityresidence. */
	public chosenPolice policeTeteDeSerie;
	
	/** The police line. */
	public chosenPolice policeLine;
	
	/** The taille img joueur. */
	//-----------------------------------------
	public int tailleImgJoueur;
	
	/** The taille img flag. */
	public int tailleImgFlag;
	
	/** The joueur. */
	private Joueur joueur;
	
	/** The frame for diffusion. */
	private WindowBroadcastPublic frameForDiffusion;
	
	/** The window configuration player infos. */
	private WindowConfigurationPlayerInfos windowConfigurationPlayerInfos;
	
	private WindowTournamentTree windowTournamentTree;

	/** The map joueur details. */
	public JoueurDetails mapJoueurDetails;
	
	/** The type fen. */
	private String typeFen;
	
	/** The playerfordifusion 2. */
	private PlayerForDiffusion playerfordifusion2;
	
	/** The nom event. */
	private String nomEvent;
	
	/** The numero player. */
	private int numeroPlayer;
	
	/** The animation panel. */
	private PanelAnimationConfiguration animationPanel;
	
	/** The panel player global. */
	private ZoomablePanel panelPlayerGlobal;
	
	/** The Constant CONFIG_DIR. */
	private static final String CONFIG_DIR = "resources"+File.separator+"Config/";
	
	/** The Constant JSON_EXT. */
	private static final String JSON_EXT = ".json";
	
	/** The mouse adapter panel. */
	private MouseAdapterPanel mouseAdapterPanel;

	private GlobalSettings globalsettings;
	
	/**
 * Gets the mouse adapter panel.
 *
 * @return the mouse adapter panel
 */
public MouseAdapterPanel getMouseAdapterPanel() {
		return mouseAdapterPanel;
	}
	
	/**
	 * Gets the numero player.
	 *
	 * @return the numero player
	 */
	public int getNumeroPlayer() {
		return numeroPlayer;
	}
	
	/**
	 * Sets the numero player.
	 *
	 * @param numeroPlayerIndex the new numero player
	 */
	public void setNumeroPlayer(int numeroPlayerIndex) {
		this.numeroPlayer = numeroPlayerIndex;
	}
	
	/**
	 * Sets the placement frame two player.
	 *
	 * @param placementFrameTwoPlayer the new placement frame two player
	 */
	public void setWindowConfigurationPlayerInfos(WindowConfigurationPlayerInfos placementFrameTwoPlayer) {
		this.windowConfigurationPlayerInfos = placementFrameTwoPlayer;
	}
	
	/**
	 * Gets the placement frame two player.
	 *
	 * @return the placement frame two player
	 */
	public WindowConfigurationPlayerInfos getPlacementFrameTwoPlayer() {
		return windowConfigurationPlayerInfos;
	}
	
	/**
	 * Gets the type fen.
	 *
	 * @return the type fen
	 */
	public String getTypeFen() {
		return typeFen;
	}
	
	/**
	 * Gets the joueur.
	 *
	 * @return the joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Gets the panel global.
	 *
	 * @return the panel global
	 */
	public JPanel getPanelGlobal() {
		return this.panelPlayerGlobal;
	}
	
	/**
	 * Gets the window animation configuration.
	 *
	 * @return the window animation configuration
	 */
	public PanelAnimationConfiguration getWindowAnimationConfiguration(){
		return animationPanel;
	}
	
	/**
	 * Instantiates a new player for diffusion.
	 *
	 * @param nomEvent the nom event
	 * @param diffusionFrame the diffusion frame
	 * @param panelAnimationConfig the panel animation config
	 * @param typeFrame the type frame
	 * @param numeroPlayer the numero player
	 */
	public PlayerForDiffusion(Evenement nomEvent, WindowBroadcastPublic diffusionFrame,PanelAnimationConfiguration panelAnimationConfig,String typeFrame, int numeroPlayer, WindowTournamentTree windowTournamentTree) {
		this.frameForDiffusion = diffusionFrame;
		this.typeFen = typeFrame;
		this.nomEvent = nomEvent.getNom();
//		this.event = nomEvent;
		this.numeroPlayer = numeroPlayer;
//		this.animationFrame = frameForDiffusion.getAnimationFrame();
		this.animationPanel = panelAnimationConfig;
		this.windowTournamentTree = windowTournamentTree;
		globalsettings = GlobalSettings.getInstance();
		playerfordifusion2 = this;

		initializePolice();
		createPlayerPanels();
		recupInfosPlayer(getEmplacementPlayer());
		mouseAdapterPanel = new MouseAdapterPanel(playerfordifusion2, playerfordifusion2, this.frameForDiffusion, this.windowTournamentTree);
		
		
//		this.frameForDiffusion.removeLayerContent(55);
//		backgroundPanel = new BackgroundPanel(event.getBackground().getImage_2());
//		backgroundPanel.setOpaque(false);
//		backgroundPanel.setSize(0, 0);
//		backgroundPanel.setLocation(this.frameForDiffusion.getWidth() / 2 - backgroundPanel.getWidth() / 2,this.frameForDiffusion.getHeight() / 2 - backgroundPanel.getHeight() / 2);
//		frameForDiffusion.addContent(55, backgroundPanel);
//		backgroundPanel.setVisible(false);		
	}
	
	/**
	 * Initialize police.
	 */
	private void initializePolice() {
		policeName = createPolice() ;
		policeSurname = createPolice() ;
		policeDisplayName = createPolice() ;
		policeAcro = createPolice() ;
		policeCountry = createPolice() ;
		policeRank = createPolice() ;
		policeBirthDate = createPolice() ;
		policeBirthPlace = createPolice() ;
		policeHeight = createPolice() ;
		policeWeight = createPolice() ;
		policeHand = createPolice() ;
		policeAge = createPolice() ;
		policePrizetotal = createPolice() ;
		policeCityresidence = createPolice();
		policeTeteDeSerie = createPolice();
		policeLine = createPolice() ;
	}
	
	/**
	 * Creates the police.
	 *
	 * @return the chosen police
	 */
	private chosenPolice createPolice() {
		chosenPolice policeInitial = new chosenPolice();
		policeInitial.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeInitial.setNewColor(Color.BLACK);
		return policeInitial;
	}
	
	/**
	 * Creates the player panels.
	 */
	private void createPlayerPanels() {
	    String[] panelNames = {"Name", "Surname", "DisplayName","ImgJoueur", "ImgFlag", "Acronyme", "Country", "Rank", "Birthdate", "Birthplace", "Height", "Weight", "Hand", "Age", "Prizetotal", "CityResidence","Seeding", "Line"};
	    for (String name : panelNames) {
	        JPanel panel = createPlayerPanel(name);
	        switch (name) {
	            case "Name": playerName = panel; break;
	            case "Surname": playerSurname = panel; break;
	            case "DisplayName": playerDisplayName = panel; break;
	            case "ImgJoueur": playerImg = panel; break;
	            case "ImgFlag": playerFlag = panel; break;
	            case "Acronyme": playerAcro = panel; break;
	            case "Country": playerCountry = panel; break;
	            case "Rank": playerRank = panel; break;
	            case "Birthdate": playerBirthdate = panel; break;
	            case "Birthplace": playerBirthplace = panel; break;
	            case "Height": playerHeight = panel; break;
	            case "Weight": playerWeight = panel; break;
	            case "Hand": playerHand = panel; break;
	            case "Age": playerAge = panel; break;
	            case "Prizetotal": playerPrizetotal = panel; break;
	            case "CityResidence": playerCityresidence = panel; break;
	            case "Seeding": playerTeteDeSerie = panel; break;
	            case "Line": playerLine = panel; break;
	        }
	    }
	}
	
	/**
	 * Creates the player panel.
	 *
	 * @param name the name
	 * @return the j panel
	 */
	private JPanel createPlayerPanel(String name) {
		GridBagLayout layoutImg = new GridBagLayout();
		JPanel panel = new JPanel();
		panel.setLayout(layoutImg);
//		if(name =="ImgJoueur" || name == "ImgFlag") {
//		}
	    panel.setOpaque(false);
	    panel.setName(name);
	    MouseAdapterPanel mouseAdapter = new MouseAdapterPanel(panel, this.playerfordifusion2, this.frameForDiffusion, this.windowTournamentTree);
	    panel.addMouseListener(mouseAdapter);
	    panel.addMouseMotionListener(mouseAdapter);
	    return panel;
	}
	
	/**
	 * Gets the emplacement player.
	 *
	 * @return the emplacement player
	 */
	public String getEmplacementPlayer() {
	    return switch (typeFen) {
	        case "player" -> CONFIG_DIR + nomEvent + "/player" + JSON_EXT;
	        case "game" -> CONFIG_DIR + nomEvent + "/game" + JSON_EXT;
	        case "tab" -> CONFIG_DIR + nomEvent + "/tab" + JSON_EXT;
	        case "full" -> CONFIG_DIR + nomEvent + "/full" + JSON_EXT;
	        default -> throw new IllegalArgumentException("Unexpected value: " + typeFen);
	    };
	}
	
	private String substringMethod(String value, int length) {
		if (value.length() <= length) {
	        return value;
	    } else {
	        return value.substring(0, length);
	    }
	}
	
	/**
	 * Sets the player.
	 *
	 * @param joueur the joueur
	 * @param ligne the ligne
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void setPlayer(Joueur joueur, int ligne) throws ClassNotFoundException, SQLException {
	    this.joueur = joueur;
//	    System.out.println("---> set player, taille infos : "+"name : "+globalsettings.getNameMaxLength()+", Surname : "+globalsettings.getSurnameMaxLength());
	    // Créer une map pour stocker les informations du joueurS
	    
	    Map<JPanel, LabelInfo> playerInfo = getPlayerInfo(joueur, ligne);
		// Mettre à jour tous les panneaux de texte
	    playerInfo.forEach((panel, info) -> updateTextPanel(panel, info));

	    recupInfosPlayer(getEmplacementPlayer());
	    // Mettre à jour les panneaux d'image
	    ImgLabel = new ImageUtility(joueur.getImgJoueur(), tailleImgJoueur);
	    FlagLabel = new ImageUtility(BDD_v2.getFlagImagePathByAcronym(joueur.getNatio_acronyme()),tailleImgFlag);
	    updateImagePanel(playerImg, ImgLabel);
	    updateImagePanel(playerFlag, FlagLabel);
	    //modif
//	    updateImagePanel(playerImg, ImgLabel);
//	    updateImagePanel(playerFlag, FlagLabel);
	    // Créer et configurer le panneau global
	    setupGlobalPanel();

	    // Mettre à jour l'affichage
	    updateDisplay(ligne);    
	}
	public Map<JPanel, LabelInfo> getPlayerInfo ( Joueur joueur, int ligne) {
		Map<JPanel, LabelInfo> playerInfo = new LinkedHashMap<>();
	    playerInfo.put(playerName, new LabelInfo(substringMethod(joueur.getNom(), globalsettings.getNameMaxLength()) , policeName));
	    playerInfo.put(playerSurname, new LabelInfo(substringMethod(joueur.getPrenom(), globalsettings.getSurnameMaxLength()), policeSurname));
	    playerInfo.put(playerDisplayName, new LabelInfo(joueur.getDisplay_name(), policeDisplayName));
	    playerInfo.put(playerAcro, new LabelInfo(joueur.getNatio_acronyme(), policeAcro));
	    playerInfo.put(playerCountry, new LabelInfo(joueur.getCountry(), policeCountry));
	    playerInfo.put(playerRank, new LabelInfo(String.valueOf(joueur.getRank()), policeRank));
	    playerInfo.put(playerBirthdate, new LabelInfo(joueur.getBirthDate(), policeBirthDate));
	    playerInfo.put(playerBirthplace, new LabelInfo(substringMethod(joueur.getBirthPlace(), globalsettings.getBirthPlaceMaxLength()), policeBirthPlace));
	    playerInfo.put(playerHeight, new LabelInfo(String.valueOf(joueur.getTaille()), policeHeight));
	    playerInfo.put(playerWeight, new LabelInfo(String.valueOf(joueur.getWeight()), policeWeight));
	    playerInfo.put(playerHand, new LabelInfo(joueur.getMain(), policeHand));
	    playerInfo.put(playerAge, new LabelInfo(String.valueOf(joueur.getAge()), policeAge));
	    playerInfo.put(playerPrizetotal, new LabelInfo(joueur.getPrizetotal(), policePrizetotal));
	    playerInfo.put(playerCityresidence, new LabelInfo(substringMethod(joueur.getCityResidence(), globalsettings.getCityResidenceMaxLength()), policeCityresidence));
	    playerInfo.put(playerTeteDeSerie, new LabelInfo(joueur.getTeteDeSerie(), policeTeteDeSerie));
	    playerInfo.put(playerLine, new LabelInfo(String.valueOf(ligne), policeLine));
	    
	    return playerInfo;
		
	}

	/**
	 * Update text panel.
	 *
	 * @param panel the panel
	 * @param info the info
	 */
	public void updateTextPanel(JPanel panel, LabelInfo info) {
	    panel.removeAll();
	    JLabel label = new JLabel(info.text);
	    label.setForeground(info.police.getNewColor());
	    label.setFont(info.police.getNewfont());
	    panel.add(label);
	    panel.setSize(label.getPreferredSize().width, label.getPreferredSize().height + 5);
	}

	/**
	 * Update image panel.
	 *
	 * @param panel the panel
	 * @param imageLabel the image label
	 */
	private void updateImagePanel(JPanel panel, ImageUtility imageLabel) {
	    panel.removeAll();
	    panel.add(imageLabel);
	    panel.revalidate();
	    panel.setSize(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
	}

	/**
	 * Setup global panel.
	 */
	private void setupGlobalPanel() {
	    panelPlayerGlobal = new ZoomablePanel();
	    panelPlayerGlobal.setLocation(this.frameForDiffusion.getWidth()/2-panelPlayerGlobal.getWidth()/2,this.frameForDiffusion.getHeight()/2-panelPlayerGlobal.getHeight()/2);
	    panelPlayerGlobal.setLayout(null);
	    panelPlayerGlobal.setOpaque(false);
	    panelPlayerGlobal.setName(this.joueur.getNom());
	    // Ajouter tous les panneaux au panneau global
	    Arrays.asList(playerName, playerSurname, playerDisplayName,playerAcro,playerCountry, playerRank, playerBirthdate, playerBirthplace,
	                  playerHeight, playerWeight, playerHand, playerAge, playerPrizetotal, playerCityresidence,playerTeteDeSerie,
	                  playerLine, playerImg, playerFlag).forEach(panelPlayerGlobal::addComponent);
	}

	/**
	 * Update display.
	 *
	 * @param ligne the ligne
	 */
	private void updateDisplay(int ligne) {
	    this.setLayout(null);
	    this.setOpaque(false);
	    this.add(panelPlayerGlobal);
	    this.setSize(this.frameForDiffusion.getPreferredSize());
	    this.setBounds(0, 0, this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());

	    if(ligne>0)
	    	handleWindowTypeSpecificBehavior();

	    this.frameForDiffusion.revalidate();
	    this.frameForDiffusion.repaint();
	}

	/**
	 * Handle window type specific behavior.
	 */
	private void handleWindowTypeSpecificBehavior() {
//    	panelAnimationConfiguration.zoomPanel(backgroundPanel, this.windowBroadcastPublic, null);
	    switch (typeFen) {
	        case "full":
	            panelPlayerGlobal.setSize(this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
	            panelPlayerGlobal.setLocation(0, 0);
	            break;
	        case "player":
	        	if(joueur.getNom()!="QUALIFIER") {
//	        		this.setOpaque(true);
//	        		this.setBackground(Color.cyan);
	        		panelPlayerGlobal.setSize(frameForDiffusion.getWidth() / 10, frameForDiffusion.getHeight() / 10);
	        		panelPlayerGlobal.setLocation((this.frameForDiffusion.getWidth() / 2) - (panelPlayerGlobal.getWidth() / 2), (this.frameForDiffusion.getHeight() / 2) - (panelPlayerGlobal.getHeight() / 2));
//	        		panelPlayerGlobal.setOpaque(true);
//	        		panelPlayerGlobal.setBackground(new Color(255, 125, 50, 50));
//	        		panelPlayerGlobal.setBounds(0, 0, this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
	        		// Assurez-vous que la taille est appliquée immédiatement
	        		panelPlayerGlobal.revalidate();
	        		panelPlayerGlobal.repaint();

//	        		System.out.println("Player taille panel depart zoom : " + panelPlayerGlobal.getWidth() + "x" + panelPlayerGlobal.getHeight() + ", position : " + panelPlayerGlobal.getLocation().x + "x" + panelPlayerGlobal.getLocation().y);

	        		this.animationPanel.zoomPanel(panelPlayerGlobal, frameForDiffusion, this::animatePlayerElements);
	        		
//	        		frameForDiffusion.getLayeredPane().add(panelPlayerGlobal, JLayeredPane.MODAL_LAYER);
//	        		ZoomAnimator.zoomPanelNoSnapshot(animationPanel, frameForDiffusion, panelPlayerGlobal, JLayeredPane.MODAL_LAYER, this::animatePlayerElements);
	        		// panelJoueur est déjà configuré (labels, images…) mais pas encore animé.
//	        		frameForDiffusion.getLayeredPane().add(panelPlayerGlobal, JLayeredPane.MODAL_LAYER);
//
//	        		ZoomAnimator.zoomIn(
//	        				this.animationPanel,
//	        				frameForDiffusion,     // la JFrame
//	        				panelPlayerGlobal,               // composant à zoomer
//	        		        JLayeredPane.MODAL_LAYER,  // même couche
//	        		        this::animatePlayerElements);                     // callback optionnel
	        	}else
	        		displayPlayerFull();
	        	
	            break;
	        default:
	        	System.out.println("default animation player");
	        	animationPanel.zoomPanel(panelPlayerGlobal, frameForDiffusion, null);
	    }
	}

	/**
	 * Animate player elements.
	 */
	private void animatePlayerElements() {
//		SwingUtilities.invokeLater(() -> {
			PlayerForDiffusion endPlayer = this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer];
			if (endPlayer != null) {
				List<Component> endComps = Arrays.stream(endPlayer.getPanelGlobal().getComponents())
                        .filter(Component::isVisible)
                        .toList();
				
				if (endComps.isEmpty()) {
			        displayPlayerFullAndTournamentTreeAnimation();
			        return;
			    }
				/* ② Compteur partagé et liste des ghosts */
			    AtomicInteger remaining = new AtomicInteger(endComps.size());
			    List<JComponent> ghosts = new ArrayList<>(endComps.size());
				
			    /* ③ Lancer chaque animation */
			    endComps.forEach(endComp -> {
			        Point endPt = endComp.getLocation();
			        animateTextElement(endComp, endPt, remaining, ghosts);
			    });
			}
//		});
	}

	/**
	 * Animate text element.
	 *
	 * @param endComponent the end component
	 * @param endPoint the end point
	 * @param remaining 
	 * @param ghosts 
	 */
	private void animateTextElement(Component endComponent, Point endPoint, AtomicInteger remaining,List<JComponent> ghosts) {

		for (Component startComponent : panelPlayerGlobal.getComponents()) {
			if (!startComponent.getName().equals(endComponent.getName()))
				continue;

			JPanel startPanel = (JPanel) startComponent; // ← le “ghost”
			JPanel endPanel = (JPanel) endComponent;

			/* infos de destination */
			Font endFont = endPanel.getComponents()[0].getFont();
			Color endColor = endPanel.getComponents()[0].getForeground();
			Dimension endDim = endPanel.getPreferredSize();

			/* on mémorise le ghost avant de l’animer */
//			ghosts.add(startPanel);

			animationPanel.animateLABEL(startPanel, endPoint, endDim, endColor, endFont, JLayeredPane.POPUP_LAYER,frameForDiffusion.getLayeredPane(),
					ghosts, () -> {
						/* callback appelé à la FIN de ce label */
						if (remaining.decrementAndGet() == 0) { // ← dernier ?
							/* ① on affiche le panneau définitif */
							displayPlayerFullAndTournamentTreeAnimation();

							/* ② on enlève tous les ghosts d’un coup */
							JLayeredPane lp = frameForDiffusion.getLayeredPane();
							ghosts.forEach(lp::remove);
							lp.repaint();
						}
					});
			break;
		}
	}

//	private void animateTextElement(Component endComponent, Point endPoint, AtomicInteger remaining, List<JComponent> ghosts) {
//	    for (Component startComponent : panelPlayerGlobal.getComponents()) {
//	        if (startComponent.getName().equals(endComponent.getName())) {
//	            JPanel endPanel = (JPanel) endComponent;
//	            JPanel startPanel = (JPanel) startComponent;
//	            Font endFont = endPanel.getComponents()[0].getFont();
//	            Color endColor = endPanel.getComponents()[0].getForeground();
//	            Dimension endDimension = endPanel.getPreferredSize();
//	            
//	            animationPanel.animateLABEL(startPanel, endPoint, endDimension, endColor, endFont, JLayeredPane.POPUP_LAYER,this.frameForDiffusion.getLayeredPane(),
//	            		() -> {
//	                        // Décrément : si 0 => toutes les anim sont terminées
//	                        if (remaining.decrementAndGet() == 0) {
//	                            displayPlayerFullAndTournamentTreeAnimation();
//	                        }
//	                    });
//	            break;
//	        }
//	    }
//	}
	
	/**
	 * Display player full.
	 */
	private void displayPlayerFull() {
		if(animationPanel.isPlayerFullEnabled())
			this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer].setVisible(true);
		else
			this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer].setVisible(false);
	}
	
	/**
	 * Display player full and tournament tree animation.
	 */
	private void displayPlayerFullAndTournamentTreeAnimation() {
		displayPlayerFull();
		if(animationPanel.isPathTreeAnimationEnabled())// && animationFrame.isbeginingAnimationTreeCheckBoxEnabled()
			animationPanel.getPanelTournamentTree().animatePlayerPath(this.numeroPlayer, animationPanel.getAnimationPathTreeDuration(), animationPanel.getNbBlinkTreeDuration());
//		if(animationFrame.isPathTreeAnimationEnabled() && !animationFrame.isbeginingAnimationTreeCheckBoxEnabled())
//			animationFrame.getPanelTournamentTree().animatePlayerPath(this.numeroPlayer, animationFrame.getAnimationPathTreeDuration(), animationFrame.getNbBlinkTreeDuration());
	}

	/**
	 * The Class LabelInfo.
	 */
	public static class LabelInfo {
	    
    	/** The text. */
    	String text;
	    
    	/** The police. */
    	chosenPolice police;

	    /**
    	 * Instantiates a new label info.
    	 *
    	 * @param text the text
    	 * @param police the police
    	 */
    	LabelInfo(String text, chosenPolice police) {
	        this.text = text;
	        this.police = police;
	    }
	}
	
	/**
	 * Recup infos player.
	 *
	 * @param emplacementPlayer the emplacement player
	 */
	public void recupInfosPlayer(String emplacementPlayer) {
	    int index = numeroPlayer;
	    System.out.println("recupInfosPlayer => --- index du joueur a recup from " + typeFen + " : " + index + " | " + numeroPlayer + ", localisation : " + emplacementPlayer + " ---");
	    ConfigurationSaveLoad configData = ConfigurationSaveLoad.loadConfigFromFile(emplacementPlayer);
	    if (configData == null) {
	        setDefaultPositions();
	        return;
	    }

	    updatePlayerElements(configData, emplacementPlayer, index);
	}

	/**
	 * Sets the default positions.
	 */
	private void setDefaultPositions() {
	    System.out.println("! Config in JSON null --> defaut config");
	    int y = 0;
	    for (JPanel panel : Arrays.asList(playerName, playerSurname, playerDisplayName,playerAcro, playerCountry, playerRank, playerBirthdate, 
	                                      playerBirthplace, playerHeight, playerWeight, playerHand, playerAge, 
	                                      playerPrizetotal, playerCityresidence, playerTeteDeSerie, playerLine)) {
	        panel.setLocation(10, y);
	        y += 20;
	    }
	    playerImg.setLocation(10, 260);
	    playerFlag.setLocation(10, 260);
	}

	/**
	 * Update player elements.
	 *
	 * @param configData the config data
	 * @param emplacementPlayer the emplacement player
	 * @param index the index
	 */
	private void updatePlayerElements(ConfigurationSaveLoad configData, String emplacementPlayer, int index) {
	    Map<String, Object[]> elements = new HashMap<>();
	    elements.put("Surname", new Object[]{playerSurname, policeSurname});
	    elements.put("Name", new Object[]{playerName, policeName});
	    elements.put("DisplayName", new Object[]{playerDisplayName, policeDisplayName});
	    elements.put("Acronyme", new Object[]{playerAcro, policeAcro});
	    elements.put("Country", new Object[]{playerCountry, policeCountry});
	    elements.put("Rank", new Object[]{playerRank, policeRank});
	    elements.put("Birthdate", new Object[]{playerBirthdate, policeBirthDate});
	    elements.put("Birthplace", new Object[]{playerBirthplace, policeBirthPlace});
	    elements.put("Height", new Object[]{playerHeight, policeHeight});
	    elements.put("Weight", new Object[]{playerWeight, policeWeight});
	    elements.put("Hand", new Object[]{playerHand, policeHand});
	    elements.put("Age", new Object[]{playerAge, policeAge});
	    elements.put("Prizetotal", new Object[]{playerPrizetotal, policePrizetotal});
	    elements.put("CityResidence", new Object[]{playerCityresidence, policeCityresidence});
	    elements.put("Seeding", new Object[]{playerTeteDeSerie, policeTeteDeSerie});
	    elements.put("Line", new Object[]{playerLine, policeLine});

	    for (Map.Entry<String, Object[]> entry : elements.entrySet()) {
	        String elementName = entry.getKey();
	        JPanel panel = (JPanel) entry.getValue()[0];
	        chosenPolice police = (chosenPolice) entry.getValue()[1];

	        ElementPoliceJoueur elementPolice = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, elementName, index);
	        if(elementPolice != null)
	        	panel.setVisible(elementPolice.isVisible());
	        else
	        	System.out.println("! "+elementName+" = null");
//	        if (elementPolice.isVisible()) {
//	            updateElement(configData, emplacementPlayer, index, elementName, panel, police, elementPolice);
//	        }
	        updateElement(configData, emplacementPlayer, index, elementName, panel, police, elementPolice);
	    }

	    updateImageElement(configData, emplacementPlayer, index, "ImgJoueur", playerImg);
	    updateImageElement(configData, emplacementPlayer, index, "ImgFlag", playerFlag);
	}

	/**
	 * Update element.
	 *
	 * @param configData the config data
	 * @param emplacementPlayer the emplacement player
	 * @param index the index
	 * @param elementName the element name
	 * @param panel the panel
	 * @param police the police
	 * @param elementPolice the element police
	 */
	private void updateElement(ConfigurationSaveLoad configData, String emplacementPlayer, int index, String elementName, 
	                           JPanel panel, chosenPolice police, ElementPoliceJoueur elementPolice) {
		
		ElementJoueur element = configData.getElement(emplacementPlayer, nomEvent, typeFen, elementName, index);
	    if (element != null) {
	    	String[] fontParts = elementPolice.getFont().split(",");
	        police.setNewfont(new Font(fontParts[0], Integer.parseInt(fontParts[1]), Integer.parseInt(fontParts[2])));
	        if(panel.getComponents().length > 0)
	        	panel.getComponents()[0].setFont(police.getNewfont());
	        
	        String[] colorParts = elementPolice.getColor().split(",");
	        police.setNewColor(new Color(Integer.parseInt(colorParts[0]), Integer.parseInt(colorParts[1]), Integer.parseInt(colorParts[2])));
	        
	        if(panel.getComponents().length > 0)
	        	panel.getComponents()[0].setForeground(police.getNewColor());
	        
	    	if ((this.getTypeFen() == "full" || this.getTypeFen() == "tab") && (panel.getName() == "Name" || panel.getName() == "Surname" || panel.getName() == "Seeding")) {
				int space = globalsettings.getSpaceLength();
				switch (panel.getName()) {
				case "Name":
					panel.setLocation(element.getPositionX(), element.getPositionY());
					this.playerSurname.setLocation(element.getPositionX() + panel.getWidth() + space, element.getPositionY());
					this.playerTeteDeSerie.setLocation(element.getPositionX() + this.playerSurname.getWidth()+ panel.getWidth() + space*2, element.getPositionY());
					break;

				default:
					break;
				}				
			}else {	    	
				panel.setLocation(element.getPositionX(), element.getPositionY());
			}
	        
	    }
	}

	/**
	 * Update image element.
	 *
	 * @param configData the config data
	 * @param emplacementPlayer the emplacement player
	 * @param index the index
	 * @param elementName the element name
	 * @param panel the panel
	 */
	private void updateImageElement(ConfigurationSaveLoad configData, String emplacementPlayer, int index, String elementName, JPanel panel) {
	    ElementPoliceJoueur elementPolice = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, elementName, index);
	    panel.setVisible(elementPolice.isVisible());
//	    if (elementPolice.isVisible()) {
	        ElementJoueur element = configData.getElement(emplacementPlayer, nomEvent, typeFen, elementName, index);
	        if (element != null) {
	            panel.setLocation(element.getPositionX(), element.getPositionY());
	            if (elementName.equals("ImgJoueur")) {
	            	System.out.println("updateImageElement => --->taille img joueur recup : "+ elementPolice.getTaille());
	                tailleImgJoueur = elementPolice.getTaille();
	            } else if (elementName.equals("ImgFlag")) {
	            	System.out.println("updateImageElement => --->taille flag joueur recup : "+ elementPolice.getTaille());
	                tailleImgFlag = elementPolice.getTaille();
	            }
	        }
//	    }
	}
	
	/**
	 * The Class JoueurDetails.
	 */
	public class JoueurDetails {
		
		/** The joueur details map. */
		public Map<JPanel, JLabel> joueurDetailsMap;

		/**
		 * Instantiates a new joueur details.
		 */
		public JoueurDetails() {
			joueurDetailsMap = new LinkedHashMap<>();
		}

		/**
		 * Adds the details.
		 *
		 * @param panel the panel
		 * @param label the label
		 */
		public void addDetails(JPanel panel, JLabel label) {
			joueurDetailsMap.put(panel, label);
		}

		/**
		 * Gets the map joueur details.
		 *
		 * @return the map joueur details
		 */
		public Map<JPanel, JLabel> getMapJoueurDetails() {
			return joueurDetailsMap;
		}
	}
	
	/**
	 * Enegistrer details joueurs.
	 */
	public void enegistrerDetailsJoueurs() {
		mapJoueurDetails = new JoueurDetails();
		mapJoueurDetails.addDetails(this.playerName, this.nameLabel);
		mapJoueurDetails.addDetails(this.playerSurname, this.SurnameLabel);
		mapJoueurDetails.addDetails(this.playerDisplayName, this.DisplayNameLabel);
		mapJoueurDetails.addDetails(this.playerImg, this.ImgLabel);
		mapJoueurDetails.addDetails(this.playerFlag, this.FlagLabel);
		mapJoueurDetails.addDetails(this.playerAcro, this.AcroLabel);
		mapJoueurDetails.addDetails(this.playerCountry, this.CountryLabel);
		mapJoueurDetails.addDetails(this.playerRank,this.RankLabel);
		mapJoueurDetails.addDetails(this.playerBirthdate, this.birthdateLabel);
		mapJoueurDetails.addDetails(this.playerBirthplace, this.birthPlaceLabel);
		mapJoueurDetails.addDetails(this.playerHeight, this.heightLabel);
		mapJoueurDetails.addDetails(this.playerWeight, this.weightLabel);
		mapJoueurDetails.addDetails(this.playerHand, this.handLabel);
		mapJoueurDetails.addDetails(this.playerAge, this.ageLabel);
		mapJoueurDetails.addDetails(this.playerPrizetotal, this.prizetotalLabel);
		mapJoueurDetails.addDetails(this.playerCityresidence, this.cityresidenceLabel);
		mapJoueurDetails.addDetails(this.playerTeteDeSerie, this.teteDeSerieLabel);
		mapJoueurDetails.addDetails(this.playerLine, this.lineLabel);
	}
	
	/**
	 * Close.
	 */
	public void close() {
		//this.placementFrame.dispose();
		this.windowConfigurationPlayerInfos.dispose();
//		new Configuration().saveConfiguration(playerName.getX(), playerName.getY(), placementFrame.checkboxName.isSelected(), playerName.getFont().toString());
	}
}

