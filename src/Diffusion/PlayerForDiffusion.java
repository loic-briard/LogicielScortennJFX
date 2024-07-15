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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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
	public JLabel ImgLabel;
	public JLabel FlagLabel;
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
	private WindowAnimationConfiguration animationFrame;

	public int getNumeroPlayer() {
		return numeroPlayer;
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
	public int getPlayerIndex() {
		return this.numeroPlayer;
	}
	
	public PlayerForDiffusion(String nomEvent, WindowBroadcastPublic diffusionFrame,String typeFrame, int numeroPlayer) {
		this.frameForDiffusion = diffusionFrame;
		this.typeFen = typeFrame;
		this.nomEvent = nomEvent;
		this.numeroPlayer = numeroPlayer;
		this.animationFrame = frameForDiffusion.getAnimationFrame();
		System.out.println("--- index du joueur a afficher from "+typeFen+" : "+numeroPlayer+" ---");
		policeName = new chosenPolice();
		policeSurname = new chosenPolice();
		policeAcro = new chosenPolice();
		policeRank = new chosenPolice();
		policeBirthDate = new chosenPolice();
		policeBirthPlace = new chosenPolice();
		policeHeight = new chosenPolice();
		policeWeight = new chosenPolice();
		policeHand = new chosenPolice();
		policeAge = new chosenPolice();
		policePrizetotal = new chosenPolice();
		policeCityresidence = new chosenPolice();
		policeLine = new chosenPolice();
		policeName.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeName.setNewColor(Color.BLACK);
		policeSurname.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeSurname.setNewColor(Color.BLACK);
		policeAcro.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeAcro.setNewColor(Color.BLACK);
		policeRank.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeRank.setNewColor(Color.BLACK);
		policeBirthDate.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeBirthDate.setNewColor(Color.BLUE);
		policeBirthPlace.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeBirthPlace.setNewColor(Color.BLUE);
		policeHeight.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeHeight.setNewColor(Color.BLUE);
		policeWeight.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeWeight.setNewColor(Color.BLUE);
		policeHand.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeHand.setNewColor(Color.BLUE);
		policeAge.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeAge.setNewColor(Color.BLUE);
		policePrizetotal.setNewfont(new Font("Arial", Font.BOLD, 25));
		policePrizetotal.setNewColor(Color.BLUE);
		policeCityresidence.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeCityresidence.setNewColor(Color.BLUE);
		policeLine.setNewfont(new Font("Arial", Font.BOLD, 25));
		policeLine.setNewColor(Color.BLUE);

		playerName = new JPanel();
		playerName.setOpaque(false);
		playerName.setName("Name");
		MouseAdapterPanel mouseAdapterPanelName = new MouseAdapterPanel(playerName);
		playerName.addMouseListener(mouseAdapterPanelName);
		playerName.addMouseMotionListener(mouseAdapterPanelName);

		playerSurname = new JPanel();
		playerSurname.setOpaque(false);
		playerSurname.setName("Surname");
		MouseAdapterPanel mouseAdapterPanelSurname = new MouseAdapterPanel(playerSurname);
		playerSurname.addMouseListener(mouseAdapterPanelSurname);
		playerSurname.addMouseMotionListener(mouseAdapterPanelSurname);

		playerImg = new JPanel();
		playerImg.setOpaque(false);
		playerImg.setName("ImgJoueur");
		MouseAdapterPanel mouseAdapterPanelImg = new MouseAdapterPanel(playerImg);
		playerImg.addMouseListener(mouseAdapterPanelImg);
		playerImg.addMouseMotionListener(mouseAdapterPanelImg);

		playerFlag = new JPanel();
		playerFlag.setOpaque(false);
		playerFlag.setName("ImgFlag");
		MouseAdapterPanel mouseAdapterPanelFlag = new MouseAdapterPanel(playerFlag);
		playerFlag.addMouseListener(mouseAdapterPanelFlag);
		playerFlag.addMouseMotionListener(mouseAdapterPanelFlag);

		playerAcro = new JPanel();
		playerAcro.setOpaque(false);
		playerAcro.setName("Acronyme");
		MouseAdapterPanel mouseAdapterPanelAcro = new MouseAdapterPanel(playerAcro);
		playerAcro.addMouseListener(mouseAdapterPanelAcro);
		playerAcro.addMouseMotionListener(mouseAdapterPanelAcro);

		playerRank = new JPanel();
		playerRank.setOpaque(false);
		playerRank.setName("Rank");
		MouseAdapterPanel mouseAdapterPanelRank = new MouseAdapterPanel(playerRank);
		playerRank.addMouseListener(mouseAdapterPanelRank);
		playerRank.addMouseMotionListener(mouseAdapterPanelRank);

		playerBirthdate = new JPanel();
		playerBirthdate.setOpaque(false);
		playerBirthdate.setName("Birthdate");
		MouseAdapterPanel mouseAdapterPanelBirthdate = new MouseAdapterPanel(playerBirthdate);
		playerBirthdate.addMouseListener(mouseAdapterPanelBirthdate);
		playerBirthdate.addMouseMotionListener(mouseAdapterPanelBirthdate);

		playerBirthplace = new JPanel();
		playerBirthplace.setOpaque(false);
		playerBirthplace.setName("Birthplace");
		MouseAdapterPanel mouseAdapterPanelBirthplace = new MouseAdapterPanel(playerBirthplace);
		playerBirthplace.addMouseListener(mouseAdapterPanelBirthplace);
		playerBirthplace.addMouseMotionListener(mouseAdapterPanelBirthplace);

		playerHeight = new JPanel();
		playerHeight.setOpaque(false);
		playerHeight.setName("Height");
		MouseAdapterPanel mouseAdapterPanelHeight = new MouseAdapterPanel(playerHeight);
		playerHeight.addMouseListener(mouseAdapterPanelHeight);
		playerHeight.addMouseMotionListener(mouseAdapterPanelHeight);

		playerWeight = new JPanel();
		playerWeight.setOpaque(false);
		playerWeight.setName("Weight");
		MouseAdapterPanel mouseAdapterPanelWeight = new MouseAdapterPanel(playerWeight);
		playerWeight.addMouseListener(mouseAdapterPanelWeight);
		playerWeight.addMouseMotionListener(mouseAdapterPanelWeight);

		playerHand = new JPanel();
		playerHand.setOpaque(false);
		playerHand.setName("Hand");
		MouseAdapterPanel mouseAdapterPanelHand = new MouseAdapterPanel(playerHand);
		playerHand.addMouseListener(mouseAdapterPanelHand);
		playerHand.addMouseMotionListener(mouseAdapterPanelHand);

		playerAge = new JPanel();
		playerAge.setOpaque(false);
		playerAge.setName("Age");
		MouseAdapterPanel mouseAdapterPanelAge = new MouseAdapterPanel(playerAge);
		playerAge.addMouseListener(mouseAdapterPanelAge);
		playerAge.addMouseMotionListener(mouseAdapterPanelAge);

		playerPrizetotal = new JPanel();
		playerPrizetotal.setOpaque(false);
		playerPrizetotal.setName("Prizetotal");
		MouseAdapterPanel mouseAdapterPanelPrizetotal = new MouseAdapterPanel(playerPrizetotal);
		playerPrizetotal.addMouseListener(mouseAdapterPanelPrizetotal);
		playerPrizetotal.addMouseMotionListener(mouseAdapterPanelPrizetotal);

		playerCityresidence = new JPanel();
		playerCityresidence.setOpaque(false);
		playerCityresidence.setName("CityResidence");
		MouseAdapterPanel mouseAdapterPanelCityresidence = new MouseAdapterPanel(playerCityresidence);
		playerCityresidence.addMouseListener(mouseAdapterPanelCityresidence);
		playerCityresidence.addMouseMotionListener(mouseAdapterPanelCityresidence);

		playerLine = new JPanel();
		playerLine.setOpaque(false);
		playerLine.setName("Line");
		MouseAdapterPanel mouseAdapterPanelLine = new MouseAdapterPanel(playerLine);
		playerLine.addMouseListener(mouseAdapterPanelLine);
		playerLine.addMouseMotionListener(mouseAdapterPanelLine);
		switch (typeFen) {
		case "player": {
			recupInfosPlayer();
			break;
		}
		case "game": {
			recupInfosPlayer();
			break;
		}
		case "tab": {
			recupInfosPlayer();
			break;
		}
		case "full": {
			recupInfosPlayer();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + typeFen);
		}
		playerfordifusion2 = this;
	}
	
	public void setPlayer(Joueur player, int i) throws ClassNotFoundException, SQLException {
		this.joueur = player;

		// ajout affichage nom --------------------------------------------------
		nameLabel = new JLabel(joueur.getNom());//nameLabel = new JLabel(joueur.getNom());
		nameLabel.setForeground(policeName.getNewColor());
		nameLabel.setFont(policeName.getNewfont());
		playerName.removeAll();
		playerName.add(nameLabel);
		// ajout affichage prenom ------------------------------------------------
		SurnameLabel = new JLabel(joueur.getPrenom());
		SurnameLabel.setForeground(policeSurname.getNewColor());
		SurnameLabel.setFont(policeSurname.getNewfont());
		playerSurname.removeAll();
		playerSurname.add(SurnameLabel);
		// ajout affichage image joueur ------------------------------------------
		ImgLabel = new ImageUtility(joueur.getImgJoueur(), tailleImgJoueur);
		playerImg.removeAll();
		playerImg.add(ImgLabel);
		// ajout affichage drapeau --------------------------------------------------------------
		FlagLabel = new ImageUtility(BDD_v2.getFlagImagePathByAcronym(joueur.getNatio_acronyme()), tailleImgFlag);
		playerFlag.removeAll();
		playerFlag.add(FlagLabel);
		// ajout affichage acronyme pays --------------------------------------------------------------
		AcroLabel = new JLabel(joueur.getNatio_acronyme());
		AcroLabel.setForeground(policeAcro.getNewColor());
		AcroLabel.setFont(policeAcro.getNewfont());
		playerAcro.removeAll();
		playerAcro.add(AcroLabel);
		// ajout affichage Rank -------------------------------------------------------------------
		RankLabel = new JLabel(joueur.getRank() + "");
		RankLabel.setForeground(policeRank.getNewColor());
		RankLabel.setFont(policeRank.getNewfont());
		playerRank.removeAll();
		playerRank.add(RankLabel);

		birthdateLabel = new JLabel(joueur.getBirthDate());
		birthdateLabel.setForeground(policeBirthDate.getNewColor());
		birthdateLabel.setFont(policeBirthDate.getNewfont());
		playerBirthdate.removeAll();
		playerBirthdate.add(birthdateLabel);

		birthPlaceLabel = new JLabel(joueur.getBirthPlace());
		birthPlaceLabel.setForeground(policeBirthPlace.getNewColor());
		birthPlaceLabel.setFont(policeBirthPlace.getNewfont());
		playerBirthplace.removeAll();
		playerBirthplace.add(birthPlaceLabel);

		heightLabel = new JLabel(String.valueOf(joueur.getTaille()));
		heightLabel.setForeground(policeHeight.getNewColor());
		heightLabel.setFont(policeHeight.getNewfont());
		playerHeight.removeAll();
		playerHeight.add(heightLabel);

		weightLabel = new JLabel(String.valueOf(joueur.getWeight()));
		weightLabel.setForeground(policeWeight.getNewColor());
		weightLabel.setFont(policeWeight.getNewfont());
		playerWeight.removeAll();
		playerWeight.add(weightLabel);

		handLabel = new JLabel(joueur.getMain());
		handLabel.setForeground(policeHand.getNewColor());
		handLabel.setFont(policeHand.getNewfont());
		playerHand.removeAll();
		playerHand.add(handLabel);

		ageLabel = new JLabel(String.valueOf(joueur.getAge()));
		ageLabel.setForeground(policeAge.getNewColor());
		ageLabel.setFont(policeAge.getNewfont());
		playerAge.removeAll();
		playerAge.add(ageLabel);

		prizetotalLabel = new JLabel(joueur.getPrizetotal());
		prizetotalLabel.setForeground(policePrizetotal.getNewColor());
		prizetotalLabel.setFont(policePrizetotal.getNewfont());
		playerPrizetotal.removeAll();
		playerPrizetotal.add(prizetotalLabel);

		cityresidenceLabel = new JLabel(joueur.getCityResidence());
		cityresidenceLabel.setForeground(policeCityresidence.getNewColor());
		cityresidenceLabel.setFont(policeCityresidence.getNewfont());
		playerCityresidence.removeAll();
		playerCityresidence.add(cityresidenceLabel);

		lineLabel = new JLabel(""+i);
		lineLabel.setForeground(policeLine.getNewColor());
		lineLabel.setFont(policeLine.getNewfont());
		playerLine.removeAll();
		playerLine.add(lineLabel);
		
		ZoomablePanel panelPlayerGlobal = new ZoomablePanel();
		panelPlayerGlobal.setSize(50, 50);
		panelPlayerGlobal.setLocation(this.frameForDiffusion.getWidth()/2-panelPlayerGlobal.getWidth()/2, this.frameForDiffusion.getHeight()/2-panelPlayerGlobal.getHeight()/2);
//		panelPlayerGlobal.setSize(this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
		panelPlayerGlobal.setLayout(null);
		panelPlayerGlobal.setOpaque(false);
//		panelPlayerGlobal.setBackground(Color.RED);

		panelPlayerGlobal.add(playerName);
		panelPlayerGlobal.add(playerSurname);
		panelPlayerGlobal.add(playerAcro);
		panelPlayerGlobal.add(playerRank);
		panelPlayerGlobal.add(playerBirthdate);
		panelPlayerGlobal.add(playerBirthplace);
		panelPlayerGlobal.add(playerHeight);
		panelPlayerGlobal.add(playerWeight);
		panelPlayerGlobal.add(playerHand);
		panelPlayerGlobal.add(playerAge);
		panelPlayerGlobal.add(playerPrizetotal);
		panelPlayerGlobal.add(playerCityresidence);
		panelPlayerGlobal.add(playerLine);
		panelPlayerGlobal.revalidate();
		panelPlayerGlobal.repaint();
		panelPlayerGlobal.add(playerImg);
		panelPlayerGlobal.add(playerFlag);
		playerName.setSize((int)nameLabel.getPreferredSize().getWidth(), (int)nameLabel.getPreferredSize().getHeight()+5);
		playerSurname.setSize((int)SurnameLabel.getPreferredSize().getWidth(), (int)SurnameLabel.getPreferredSize().getHeight()+5);
		playerImg.setSize((int)ImgLabel.getPreferredSize().getWidth(), (int)ImgLabel.getPreferredSize().getHeight()+5);
		playerFlag.setSize((int)FlagLabel.getPreferredSize().getWidth(), (int)FlagLabel.getPreferredSize().getHeight()+5);
		playerAcro.setSize((int)AcroLabel.getPreferredSize().getWidth(), (int)AcroLabel.getPreferredSize().getHeight()+5);
		playerRank.setSize((int)RankLabel.getPreferredSize().getWidth(), (int)RankLabel.getPreferredSize().getHeight()+5);
		playerBirthdate.setSize((int)birthdateLabel.getPreferredSize().getWidth(), (int)birthdateLabel.getPreferredSize().getHeight()+5);
		playerBirthplace.setSize((int)birthPlaceLabel.getPreferredSize().getWidth(), (int)birthPlaceLabel.getPreferredSize().getHeight()+5);
		playerHeight.setSize((int)heightLabel.getPreferredSize().getWidth(), (int)heightLabel.getPreferredSize().getHeight()+5);
		playerWeight.setSize((int)weightLabel.getPreferredSize().getWidth(), (int)weightLabel.getPreferredSize().getHeight()+5);
		playerHand.setSize((int)handLabel.getPreferredSize().getWidth(), (int)handLabel.getPreferredSize().getHeight()+5);
		playerAge.setSize((int)ageLabel.getPreferredSize().getWidth(), (int)ageLabel.getPreferredSize().getHeight()+5);
		playerPrizetotal.setSize((int)prizetotalLabel.getPreferredSize().getWidth(), (int)prizetotalLabel.getPreferredSize().getHeight()+5);
		playerCityresidence.setSize((int)cityresidenceLabel.getPreferredSize().getWidth(), (int)cityresidenceLabel.getPreferredSize().getHeight()+5);
		playerLine.setSize((int)lineLabel.getPreferredSize().getWidth(), (int)lineLabel.getPreferredSize().getHeight()+5);
		this.setLayout(null);
		this.setOpaque(false);
		this.add(panelPlayerGlobal);
		this.setBounds(0, 0,this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
		
		switch (typeFen) {
		case "full":{
			panelPlayerGlobal.setSize(this.frameForDiffusion.getWidth(), this.frameForDiffusion.getHeight());
			panelPlayerGlobal.setLocation(0, 0);
			break;
		}
		case "player":{
			animationFrame.zoomPanel(panelPlayerGlobal, frameForDiffusion, () -> {
				ConfigurationSaveLoad configData = ConfigurationSaveLoad
						.loadConfigFromFile("Config/" + nomEvent + "/full.json");
				try {
						Map<String, Map<String, Object>> playerData = configData.getAllElementVisible(this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getNbJoueur(),"Config/" + nomEvent + "/full.json");//modifier "8" pour remplacer par le nombre de joueur selectionné
						// Parcourir les données récupérées pour les joueurs
						for (Map.Entry<String, Map<String, Object>> playerEntry : playerData.entrySet()) {
							String playerNameFull = playerEntry.getKey();
							if (playerNameFull.equals("player" + (Integer.parseInt(lineLabel.getText()) - 1))) {
								Map<String, Object> elements = playerEntry.getValue();
								for (Map.Entry<String, Object> elementEntry : elements.entrySet()) {
									String elementName = elementEntry.getKey();
									for (Component searchedPanel : panelPlayerGlobal.getComponents()) {
										if (elementName.equals(searchedPanel.getName())) {
											JPanel startPanel = (JPanel) searchedPanel;
											@SuppressWarnings("unchecked")
											Map<String, Object> elementData = (Map<String, Object>) elementEntry.getValue();
											Point endPoint = (Point) elementData.get("position");
											String font = (String) elementData.get("font");
											Font endFont = new Font(font.split(",")[0],Integer.parseInt(font.split(",")[1]),Integer.parseInt(font.split(",")[2]));
											Color endColor = (Color) elementData.get("color");
											JLabel dimensionLabel = new JLabel(nameLabel.getText());
											dimensionLabel.setFont(endFont);
											Dimension endDimension = new Dimension(dimensionLabel.getWidth(), dimensionLabel.getHeight());
	
											animationFrame.animateLabel(startPanel, endPoint, endDimension, endColor, endFont, JLayeredPane.POPUP_LAYER, this.frameForDiffusion.getLayeredPane(),() -> {
												this.frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree()[this.numeroPlayer].setVisible(true);
											});
										}
									}
								}
							}
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			break;
		}
		default:{
			animationFrame.zoomPanel(panelPlayerGlobal, frameForDiffusion, null);	
		}
		}
		
		this.frameForDiffusion.revalidate();
		this.frameForDiffusion.repaint();		
	}

	private void recupInfosPlayer() {
		int index = numeroPlayer;
		String emplacementPlayer = null;
		switch (typeFen) {
		case "player":
			index = 0;
			emplacementPlayer = "Config/"+nomEvent+"/player.json";
			break;
		case "game":
			emplacementPlayer = "Config/"+nomEvent+"/game.json";
			break;
		case "tab":
			emplacementPlayer = "Config/"+nomEvent+"/tab.json";
			break;
		case "full":
			emplacementPlayer = "Config/"+nomEvent+"/full.json";
			break;

		default:
			break;
		}
		System.out.println("--- index du joueur a recup from "+typeFen+" : "+index+" | "+numeroPlayer+" ---");
		System.out.println("Get JSON info for : "+typeFen+", player index : "+index+", localisation : "+emplacementPlayer);
		ConfigurationSaveLoad configData = ConfigurationSaveLoad.loadConfigFromFile(emplacementPlayer);
		if (configData != null) {
			ElementPoliceJoueur elementPoliceSurname = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,"Surname", index);
			playerSurname.setVisible(elementPoliceSurname.isVisible());
			if (elementPoliceSurname.isVisible()) {
				ElementJoueur elementSurname = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Surname",index);// elementWindows.getPlayer().get("Surname");
				if (elementSurname != null) {
					playerSurname.setBounds(elementSurname.getPositionX(), elementSurname.getPositionY(), 10, 10);
					policeSurname.setNewfont(new Font(elementPoliceSurname.getFont().split(",")[0],
							Integer.parseInt(elementPoliceSurname.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceSurname.getFont().split(",")[2])));
					policeSurname.setNewColor(new Color(Integer.parseInt(elementPoliceSurname.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceSurname.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceSurname.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceName = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Name", index);// elementWindows.getPlayer().get("Name");
			playerName.setVisible(elementPoliceName.isVisible());
			if (elementPoliceName.isVisible()) {
				ElementJoueur elementName = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Name", index);// elementWindows.getPlayer().get("Name");
				if (elementName != null) {
					playerName.setBounds(elementName.getPositionX(), elementName.getPositionY(), 10, 10);
					// System.out.println("|"+elementPoliceName.getFont().split(",")[0]+"|"+Integer.parseInt(elementPoliceName.getFont().split(",")[1])+"|"+Integer.parseInt(elementPoliceName.getFont().split(",")[2]));
					policeName.setNewfont(new Font(elementPoliceName.getFont().split(",")[0],
							Integer.parseInt(elementPoliceName.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceName.getFont().split(",")[2])));
					policeName.setNewColor(new Color(Integer.parseInt(elementPoliceName.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceName.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceName.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceImg = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"ImgJoueur", index);
			playerImg.setVisible(elementPoliceImg.isVisible());
			if (elementPoliceImg.isVisible()) {
				ElementJoueur elementImg = configData.getElement(emplacementPlayer, nomEvent, typeFen, "ImgJoueur",
						index);// elementWindows.getPlayer().get("ImgJoueur");
				if (elementImg != null) {
					playerImg.setBounds(elementImg.getPositionX(), elementImg.getPositionY(), 10, 10);
					// System.out.println("taille image joueur : "+elementPoliceImg.getTaille());
					tailleImgJoueur = elementPoliceImg.getTaille();
				}
			}
			ElementPoliceJoueur elementPoliceFlag = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,"ImgFlag", index);
			playerFlag.setVisible(elementPoliceFlag.isVisible());
			if (elementPoliceFlag.isVisible()) {
				ElementJoueur elementFlag = configData.getElement(emplacementPlayer, nomEvent, typeFen, "ImgFlag",index);
				if (elementFlag != null) {
					playerFlag.setBounds(elementFlag.getPositionX(), elementFlag.getPositionY(), 10, 10);
					 System.out.println("--- taille image flag : "+elementPoliceFlag.getTaille());
					tailleImgFlag = elementPoliceFlag.getTaille();
				}
			}
			ElementPoliceJoueur elementPoliceAcro = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Acronyme", index);
			playerAcro.setVisible(elementPoliceAcro.isVisible());
			if (elementPoliceAcro.isVisible()) {
				ElementJoueur elementAcro = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Acronyme",
						index);// elementWindows.getPlayer().get("Acronyme");
				if (elementAcro != null) {
					playerAcro.setBounds(elementAcro.getPositionX(), elementAcro.getPositionY(), 10, 10);
					policeAcro.setNewfont(new Font(elementPoliceAcro.getFont().split(",")[0],
							Integer.parseInt(elementPoliceAcro.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceAcro.getFont().split(",")[2])));
					policeAcro.setNewColor(new Color(Integer.parseInt(elementPoliceAcro.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceAcro.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceAcro.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceRank = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Rank", index);
			playerRank.setVisible(elementPoliceRank.isVisible());
			if (elementPoliceRank.isVisible()) {
				ElementJoueur elementRank = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Rank", index);// elementWindows.getPlayer().get("Rank");
				if (elementRank != null) {
					playerRank.setBounds(elementRank.getPositionX(), elementRank.getPositionY(), 10, 10);
					policeRank.setNewfont(new Font(elementPoliceRank.getFont().split(",")[0],
							Integer.parseInt(elementPoliceRank.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceRank.getFont().split(",")[2])));
					policeRank.setNewColor(new Color(Integer.parseInt(elementPoliceRank.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceRank.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceRank.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceBirthDate = configData.getElementPolice(emplacementPlayer, nomEvent,
					typeFen, "Birthdate", index);
			playerBirthdate.setVisible(elementPoliceBirthDate.isVisible());
			if (elementPoliceBirthDate.isVisible()) {
				ElementJoueur elementBirthDate = configData.getElement(emplacementPlayer, nomEvent, typeFen,
						"Birthdate", index);// elementWindows.getPlayer().get("Birthdate");
				if (elementBirthDate != null) {
					playerBirthdate.setBounds(elementBirthDate.getPositionX(), elementBirthDate.getPositionY(), 10, 10);
					policeBirthDate.setNewfont(new Font(elementPoliceBirthDate.getFont().split(",")[0],
							Integer.parseInt(elementPoliceBirthDate.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceBirthDate.getFont().split(",")[2])));
					policeBirthDate
							.setNewColor(new Color(Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[0]),
									Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[1]),
									Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceBirthPlace = configData.getElementPolice(emplacementPlayer, nomEvent,
					typeFen, "Birthplace", index);
			playerBirthplace.setVisible(elementPoliceBirthPlace.isVisible());
			if (elementPoliceBirthPlace.isVisible()) {
				ElementJoueur elementBirthPlace = configData.getElement(emplacementPlayer, nomEvent, typeFen,
						"Birthplace", index);// elementWindows.getPlayer().get("Birthplace");
				if (elementBirthPlace != null) {
					playerBirthplace.setBounds(elementBirthPlace.getPositionX(), elementBirthPlace.getPositionY(), 10,
							10);
					policeBirthPlace.setNewfont(new Font(elementPoliceBirthPlace.getFont().split(",")[0],
							Integer.parseInt(elementPoliceBirthPlace.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceBirthPlace.getFont().split(",")[2])));
					policeBirthPlace
							.setNewColor(new Color(Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[0]),
									Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[1]),
									Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceHeight = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Height", index);
			playerHeight.setVisible(elementPoliceHeight.isVisible());
			if (elementPoliceHeight.isVisible()) {
				ElementJoueur elementHeight = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Height",
						index);// elementWindows.getPlayer().get("Height");
				if (elementHeight != null) {
					playerHeight.setBounds(elementHeight.getPositionX(), elementHeight.getPositionY(), 10, 10);
					policeHeight.setNewfont(new Font(elementPoliceHeight.getFont().split(",")[0],
							Integer.parseInt(elementPoliceHeight.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceHeight.getFont().split(",")[2])));
					policeHeight.setNewColor(new Color(Integer.parseInt(elementPoliceHeight.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceHeight.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceHeight.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceWeight = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Weight", index);
			playerWeight.setVisible(elementPoliceWeight.isVisible());
			if (elementPoliceWeight.isVisible()) {
				ElementJoueur elementWeight = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Weight",
						index);// elementWindows.getPlayer().get("Weight");
				if (elementWeight != null) {
					playerWeight.setBounds(elementWeight.getPositionX(), elementWeight.getPositionY(), 10, 10);
					policeWeight.setNewfont(new Font(elementPoliceWeight.getFont().split(",")[0],
							Integer.parseInt(elementPoliceWeight.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceWeight.getFont().split(",")[2])));
					policeWeight.setNewColor(new Color(Integer.parseInt(elementPoliceWeight.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceWeight.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceWeight.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceHand = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Hand", index);
			playerHand.setVisible(elementPoliceHand.isVisible());
			if (elementPoliceHand.isVisible()) {
				ElementJoueur elementHand = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Hand", index);// elementWindows.getPlayer().get("Hand");
				if (elementHand != null) {
					if (typeFen != "tab")
						playerHand.setBounds(elementHand.getPositionX(), elementHand.getPositionY(), 10, 10);
					policeHand.setNewfont(new Font(elementPoliceHand.getFont().split(",")[0],
							Integer.parseInt(elementPoliceHand.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceHand.getFont().split(",")[2])));
					policeHand.setNewColor(new Color(Integer.parseInt(elementPoliceHand.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceHand.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceHand.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceAge = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Age", index);
			playerAge.setVisible(elementPoliceAge.isVisible());
			if (elementPoliceAge.isVisible()) {
				ElementJoueur elementAge = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Age", index);// elementWindows.getPlayer().get("Age");
				if (elementAge != null) {
					playerAge.setBounds(elementAge.getPositionX(), elementAge.getPositionY(), 10, 10);
					policeAge.setNewfont(new Font(elementPoliceAge.getFont().split(",")[0],
							Integer.parseInt(elementPoliceAge.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceAge.getFont().split(",")[2])));
					policeAge.setNewColor(new Color(Integer.parseInt(elementPoliceAge.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceAge.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceAge.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPolicePrizeTotal = configData.getElementPolice(emplacementPlayer, nomEvent,
					typeFen, "Prizetotal", index);
			playerPrizetotal.setVisible(elementPolicePrizeTotal.isVisible());
			if (elementPolicePrizeTotal.isVisible()) {
				ElementJoueur elementPrizeTotal = configData.getElement(emplacementPlayer, nomEvent, typeFen,
						"Prizetotal", index);// elementWindows.getPlayer().get("Prizetotal");
				if (elementPrizeTotal != null) {
					playerPrizetotal.setBounds(elementPrizeTotal.getPositionX(), elementPrizeTotal.getPositionY(), 10,
							10);
					policePrizetotal.setNewfont(new Font(elementPolicePrizeTotal.getFont().split(",")[0],
							Integer.parseInt(elementPolicePrizeTotal.getFont().split(",")[1]),
							Integer.parseInt(elementPolicePrizeTotal.getFont().split(",")[2])));
					policePrizetotal
							.setNewColor(new Color(Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[0]),
									Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[1]),
									Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceCityResidence = configData.getElementPolice(emplacementPlayer, nomEvent,
					typeFen, "CityResidence", index);
			playerCityresidence.setVisible(elementPoliceCityResidence.isVisible());
			if (elementPoliceCityResidence.isVisible()) {
				ElementJoueur elementCityResidence = configData.getElement(emplacementPlayer, nomEvent, typeFen,
						"CityResidence", index);// elementWindows.getPlayer().get("CityResidence");
				if (elementCityResidence != null) {
					playerCityresidence.setBounds(elementCityResidence.getPositionX(),
							elementCityResidence.getPositionY(), 10, 10);
					policeCityresidence.setNewfont(new Font(elementPoliceCityResidence.getFont().split(",")[0],
							Integer.parseInt(elementPoliceCityResidence.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceCityResidence.getFont().split(",")[2])));
					policeCityresidence.setNewColor(
							new Color(Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[0]),
									Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[1]),
									Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[2])));
				}
			}
			ElementPoliceJoueur elementPoliceLine = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen,
					"Line", index);
			playerLine.setVisible(elementPoliceLine.isVisible());
			if (elementPoliceLine.isVisible()) {
				ElementJoueur elementLine = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Line", index);// elementWindows.getPlayer().get("Line");
				if (elementLine != null) {
					playerLine.setBounds(elementLine.getPositionX(), elementLine.getPositionY(), 10, 10);
					policeLine.setNewfont(new Font(elementPoliceLine.getFont().split(",")[0],
							Integer.parseInt(elementPoliceLine.getFont().split(",")[1]),
							Integer.parseInt(elementPoliceLine.getFont().split(",")[2])));
					policeLine.setNewColor(new Color(Integer.parseInt(elementPoliceLine.getColor().split(",")[0]),
							Integer.parseInt(elementPoliceLine.getColor().split(",")[1]),
							Integer.parseInt(elementPoliceLine.getColor().split(",")[2])));
				}
			}
		}else {
			System.out.println("Config in JSON null --> defaut config");
			// Positions des nouveaux panneaux
			playerName.setLocation(10, 0);
			playerSurname.setLocation(10, 20);
			playerImg.setLocation(10, 260);
			playerFlag.setLocation(10, 260);
			playerAcro.setLocation(10, 40);
			playerRank.setLocation(10, 60);
			playerBirthdate.setLocation(10, 80);
			playerBirthplace.setLocation(10, 100);
			playerHeight.setLocation(10, 120);
			playerWeight.setLocation(10, 140);
			playerHand.setLocation(10, 160);
			playerAge.setLocation(10, 180);
			playerPrizetotal.setLocation(10, 200);
			playerCityresidence.setLocation(10, 220);
			playerLine.setLocation(10, 240);
		}
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
			//frameForDiffusion.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			switch (typeFen) {
			case "player": {
				windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()); //mets l'onglet correspondant au joueur
				Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getSelectedComponent();//recupere les infos de l'onglet
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;//
		    		System.out.println(" Player draged : "+playerfordifusion2.nameLabel.getText()+" for window PLAYER");		
		            currentTab.refreshSpinner(playerfordifusion2);//mise a jour des positions de l'onglet du joueur selectionné
		        } else {
		            // Handle the case where the selected component is not of type tabInfosPlayer
		        	windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
		            System.out.println("! Error: Selected component is not an instance of tabInfosPlayer");
		        }				
				break;
			}
			case "game": {
				windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()); //mets l'onglet correspondant au joueur
				Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getSelectedComponent();//recupere les infos de l'onglet
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;//
		    		System.out.println(" Player draged : "+playerfordifusion2.nameLabel.getText()+" for window GAME");		
		            currentTab.refreshSpinner(playerfordifusion2);//mise a jour des positions de l'onglet du joueur selectionné
		        } else {
		            // Handle the case where the selected component is not of type tabInfosPlayer
		        	windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
		            System.out.println(" tabInfosPlayer");
		        }
				break;
			}
			case "tab": {
				windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()); //mets l'onglet correspondant au joueur
				Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getSelectedComponent();//recupere les infos de l'onglet
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;//
		    		System.out.println(" Player draged : "+playerfordifusion2.nameLabel.getText()+" for window TAB");		
		            currentTab.refreshSpinner(playerfordifusion2);//mise a jour des positions de l'onglet du joueur selectionné
		        } else {
		            // Handle the case where the selected component is not of type tabInfosPlayer
		        	windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
		            System.out.println(" tabInfosPlayer");
		        }
                break;
			}
			case "full": {
				joueurFullDragged();
                break;
			}
			default:
				throw new IllegalArgumentException("! Unexpected value: " + typeFen);
			}
			//placementFrame.refreshSpinner();
		}
	}
	
	class JoueurDetails {
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
	
	public void joueurFullDragged() {
		PlayerForDiffusion[] tableauPlayerDiffusionTree = frameForDiffusion.getWindowTournamentTreeFromBroadcast().getTabPlayerForTree();//tableau des joueur full actuellement afficher
		ArrayList<PlayerForDiffusion> listPlayerDiffusionTree = new ArrayList<PlayerForDiffusion>();
		int indexTab = 0;
		if (windowConfigurationPlayerInfos == null || !windowConfigurationPlayerInfos.isDisplayable()) {
			windowConfigurationPlayerInfos = new WindowConfigurationPlayerInfos(frameForDiffusion, "full");
//				placementFrameTwoPlayer.setTabPolice(new TabPolice(ListSelectedJoueur));
		} else {
			windowConfigurationPlayerInfos.tabbedPane.removeAll();
			windowConfigurationPlayerInfos.tabbedPane.revalidate();
			windowConfigurationPlayerInfos.tabbedPane.repaint();
			windowConfigurationPlayerInfos.setTypeFenetre("full");
		}
		for (int i=0; i<tableauPlayerDiffusionTree.length;i++) {
			if(tableauPlayerDiffusionTree[i] != null) {
				listPlayerDiffusionTree.add(tableauPlayerDiffusionTree[i]);
				tableauPlayerDiffusionTree[i].setPlacementFrameTwoPlayer(windowConfigurationPlayerInfos);
				TabConfigurationPlayerInfos tabFull = new TabConfigurationPlayerInfos(tableauPlayerDiffusionTree[i], tableauPlayerDiffusionTree[i].getJoueur(), frameForDiffusion, windowConfigurationPlayerInfos);
				windowConfigurationPlayerInfos.addTabJoueur(tabFull);
				System.out.println("    FULL player to disply : "+tableauPlayerDiffusionTree[i].nameLabel.getText());
			}
		}
		if(listPlayerDiffusionTree.size() != 0) {
			windowConfigurationPlayerInfos.setTabPolice(new TabPolice(listPlayerDiffusionTree, windowConfigurationPlayerInfos));
			windowConfigurationPlayerInfos.pack();
		}
		for (int i = 0; i < listPlayerDiffusionTree.size(); i++) {
			if(playerfordifusion2.getNumeroPlayer() == listPlayerDiffusionTree.get(i).getNumeroPlayer()) {
				indexTab = i;
			}						
		}				
		
		windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(indexTab);
		Component selectedComponent = windowConfigurationPlayerInfos.tabbedPane.getSelectedComponent();
        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
            System.out.println(" Player draged : "+playerfordifusion2.nameLabel.getText()+" for window FULL");	
            currentTab.refreshSpinner(playerfordifusion2);
        } else {
            // Handle the case where the selected component is not of type tabInfosPlayer
        	windowConfigurationPlayerInfos.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
            System.out.println(" tabInfosPlayer");
        }
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
