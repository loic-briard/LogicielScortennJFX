package Diffusion;

/*
 * permet de placer tout les elements du joueur sur la fenetre de diffusion et de les deplacer
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.BDD_v2;
import Main.ImageUtility;
import Players.Joueur;
import Police.chosenPolice;

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
//	private PlacementDiffusionFrame placementFrame;
	private WindowConfigurationPlayerInfos placementFrameTwoPlayer;
	public JoueurDetails mapJoueurDetails;
	private String typeFen;
	private PlayerForDiffusion playerfordifusion2;
	private String nomEvent;
	private int numeroPlayer;

	public int getNumeroPlayer() {
		return numeroPlayer;
	}
	public void setPlacementFrameTwoPlayer(WindowConfigurationPlayerInfos placementFrameTwoPlayer) {
		this.placementFrameTwoPlayer = placementFrameTwoPlayer;
	}
	public WindowConfigurationPlayerInfos getPlacementFrameTwoPlayer() {
		return placementFrameTwoPlayer;
	}
	public String getTypeFen() {
		return typeFen;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}

	public PlayerForDiffusion(String nomEvent, WindowBroadcastPublic diffusionFrame,String typeFrame, int numeroPlayer) {
		this.frameForDiffusion = diffusionFrame;
		this.typeFen = typeFrame;
		this.nomEvent = nomEvent;
		this.numeroPlayer = numeroPlayer;
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
		ImgLabel = new ImageUtility(joueur.getImgJoueur(), 200);
		playerImg.removeAll();
		playerImg.add(ImgLabel);
		// ajout affichage drapeau --------------------------------------------------------------
		FlagLabel = new ImageUtility(BDD_v2.getFlagImagePathByAcronym(joueur.getNatio_acronyme()), 200);
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

		this.frameForDiffusion.add(playerName);
		this.frameForDiffusion.add(playerSurname);
		this.frameForDiffusion.add(playerAcro);
		this.frameForDiffusion.add(playerRank);
		this.frameForDiffusion.add(playerBirthdate);
		this.frameForDiffusion.add(playerBirthplace);
		this.frameForDiffusion.add(playerHeight);
		this.frameForDiffusion.add(playerWeight);
		this.frameForDiffusion.add(playerHand);
		this.frameForDiffusion.add(playerAge);
		this.frameForDiffusion.add(playerPrizetotal);
		this.frameForDiffusion.add(playerCityresidence);
		this.frameForDiffusion.add(playerLine);
		this.frameForDiffusion.revalidate();
		this.frameForDiffusion.repaint();
		this.frameForDiffusion.add(playerImg);
		this.frameForDiffusion.add(playerFlag);
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

		this.frameForDiffusion.revalidate();
		this.frameForDiffusion.repaint();		
	}
	
	private void recupInfosPlayer() {
		int index = getNumeroPlayer();
		String emplacementPlayer = null;
		switch (typeFen) {
		case "player":
			index = 0;
			emplacementPlayer = "Config/"+nomEvent+"/player.json";
			break;
		case "game":
			emplacementPlayer = "Config/"+nomEvent+"/game.json";
			index = getNumeroPlayer();
			break;
		case "tab":
			emplacementPlayer = "Config/"+nomEvent+"/tab.json";
			//index = getNumeroPlayer();
			break;
		case "full":
			emplacementPlayer = "Config/"+nomEvent+"/full.json";
			//index = getNumeroPlayer();
			break;

		default:
			break;
		}
		System.out.println("-----> type de fenetre : "+typeFen+", numero du joueur : "+index+", emlacement : "+emplacementPlayer);
		ConfigurationSaveLoad configData = ConfigurationSaveLoad.loadConfigFromFile(emplacementPlayer);
		if (configData != null) {
			ElementJoueur elementName = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Name", index);// elementWindows.getPlayer().get("Name");
			ElementPoliceJoueur elementPoliceName = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Name", index);// elementWindows.getPlayer().get("Name");
			if (elementName != null) {
				// System.out.println(elementName.getPositionX() + "|" + elementName.getPositionY() + "|" + elementName.isVisible() + "|" + elementName.getFont() + "|" + elementName.getColor());
				playerName.setBounds(elementName.getPositionX(), elementName.getPositionY(), 10, 10);
				playerName.setVisible(elementPoliceName.isVisible());
				//System.out.println("|"+elementPoliceName.getFont().split(",")[0]+"|"+Integer.parseInt(elementPoliceName.getFont().split(",")[1])+"|"+Integer.parseInt(elementPoliceName.getFont().split(",")[2]));
				policeName.setNewfont(new Font(elementPoliceName.getFont().split(",")[0], Integer.parseInt(elementPoliceName.getFont().split(",")[1]), Integer.parseInt(elementPoliceName.getFont().split(",")[2])));
				policeName.setNewColor(new Color(Integer.parseInt(elementPoliceName.getColor().split(",")[0]), Integer.parseInt(elementPoliceName.getColor().split(",")[1]),Integer.parseInt(elementPoliceName.getColor().split(",")[2])));
			}

			ElementJoueur elementSurname = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Surname", index);// elementWindows.getPlayer().get("Surname");
			ElementPoliceJoueur elementPoliceSurname = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Surname", index);
			if (elementSurname != null) {
				// System.out.println(
				// elementSurname.getPositionX() + "|" + elementSurname.getPositionY() + "|" + elementSurname.isVisible() + "|" + elementSurname.getFont() + "|" + elementSurname.getColor());
				playerSurname.setBounds(elementSurname.getPositionX(), elementSurname.getPositionY(), 10, 10);
				playerSurname.setVisible(elementPoliceSurname.isVisible());
				policeSurname.setNewfont(
						new Font(elementPoliceSurname.getFont().split(",")[0], Integer.parseInt(elementPoliceSurname.getFont().split(",")[1]), Integer.parseInt(elementPoliceSurname.getFont().split(",")[2])));
				policeSurname.setNewColor(new Color(Integer.parseInt(elementPoliceSurname.getColor().split(",")[0]), Integer.parseInt(elementPoliceSurname.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceSurname.getColor().split(",")[2])));
			}
			ElementJoueur elementImg = configData.getElement(emplacementPlayer, nomEvent, typeFen, "ImgJoueur", index);// elementWindows.getPlayer().get("ImgJoueur");
			ElementPoliceJoueur elementPoliceImg = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "ImgJoueur", index);
			if (elementImg != null) {
				playerImg.setBounds(elementImg.getPositionX(), elementImg.getPositionY(), 10, 10);
				//System.out.println("taille image joueur : "+elementPoliceImg.getTaille());
				tailleImgJoueur = elementPoliceImg.getTaille();
				playerImg.setVisible(elementPoliceImg.isVisible());
			}
			ElementJoueur elementFlag = configData.getElement(emplacementPlayer, nomEvent, typeFen, "ImgFlag", index);// elementWindows.getPlayer().get("ImgFlag");
			ElementPoliceJoueur elementPoliceFlag = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "ImgFlag", index);
			if (elementFlag != null) {
				playerFlag.setBounds(elementFlag.getPositionX(), elementFlag.getPositionY(), 10, 10);
				//System.out.println("taille image joueur : "+elementPoliceFlag.getTaille());
				tailleImgFlag = elementPoliceFlag.getTaille();
				playerFlag.setVisible(elementPoliceFlag.isVisible());
			}
			ElementJoueur elementAcro = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Acronyme", index);// elementWindows.getPlayer().get("Acronyme");
			ElementPoliceJoueur elementPoliceAcro = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Acronyme", index);
			if (elementAcro != null) {
				// System.out.println(elementAcro.getPositionX() + "|" + elementAcro.getPositionY() + "|" + elementAcro.isVisible() + "|" + elementAcro.getFont() + "|" + elementAcro.getColor());
				playerAcro.setBounds(elementAcro.getPositionX(), elementAcro.getPositionY(), 10, 10);
				playerAcro.setVisible(elementPoliceAcro.isVisible());
				policeAcro.setNewfont(new Font(elementPoliceAcro.getFont().split(",")[0], Integer.parseInt(elementPoliceAcro.getFont().split(",")[1]), Integer.parseInt(elementPoliceAcro.getFont().split(",")[2])));
				policeAcro.setNewColor(new Color(Integer.parseInt(elementPoliceAcro.getColor().split(",")[0]), Integer.parseInt(elementPoliceAcro.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceAcro.getColor().split(",")[2])));
			}
			ElementJoueur elementRank = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Rank", index);// elementWindows.getPlayer().get("Rank");
			ElementPoliceJoueur elementPoliceRank = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Rank", index);
			if (elementRank != null ) {
				// System.out.println(elementRank.getPositionX() + "|" + elementRank.getPositionY() + "|" + elementRank.isVisible() + "|" + elementRank.getFont() + "|" + elementRank.getColor());
				playerRank.setBounds(elementRank.getPositionX(), elementRank.getPositionY(), 10, 10);
				playerRank.setVisible(elementPoliceRank.isVisible());
				policeRank.setNewfont(new Font(elementPoliceRank.getFont().split(",")[0], Integer.parseInt(elementPoliceRank.getFont().split(",")[1]), Integer.parseInt(elementPoliceRank.getFont().split(",")[2])));
				policeRank.setNewColor(new Color(Integer.parseInt(elementPoliceRank.getColor().split(",")[0]), Integer.parseInt(elementPoliceRank.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceRank.getColor().split(",")[2])));
			}
			ElementJoueur elementBirthDate = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Birthdate", index);// elementWindows.getPlayer().get("Birthdate");
			ElementPoliceJoueur elementPoliceBirthDate= configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Birthdate", index);
			if (elementBirthDate != null) {
				// System.out.println(elementBirthDate.getPositionX() + "|" + elementBirthDate.getPositionY() + "|" + elementBirthDate.isVisible() + "|" + elementBirthDate.getFont() + "|"
				// + elementBirthDate.getColor());
				playerBirthdate.setBounds(elementBirthDate.getPositionX(), elementBirthDate.getPositionY(), 10, 10);
				playerBirthdate.setVisible(elementPoliceBirthDate.isVisible());
				policeBirthDate.setNewfont(
						new Font(elementPoliceBirthDate.getFont().split(",")[0], Integer.parseInt(elementPoliceBirthDate.getFont().split(",")[1]), Integer.parseInt(elementPoliceBirthDate.getFont().split(",")[2])));
				policeBirthDate.setNewColor(new Color(Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[0]), Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceBirthDate.getColor().split(",")[2])));
			}
			ElementJoueur elementBirthPlace = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Birthplace", index);// elementWindows.getPlayer().get("Birthplace");
			ElementPoliceJoueur elementPoliceBirthPlace = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Birthplace", index);
			if (elementBirthPlace != null) {
				// System.out.println(elementBirthPlace.getPositionX() + "|" + elementBirthPlace.getPositionY() + "|" + elementBirthPlace.isVisible() + "|" + elementBirthPlace.getFont() + "|"
				// + elementBirthPlace.getColor());
				playerBirthplace.setBounds(elementBirthPlace.getPositionX(), elementBirthPlace.getPositionY(), 10, 10);
				playerBirthplace.setVisible(elementPoliceBirthPlace.isVisible());
				policeBirthPlace.setNewfont(
						new Font(elementPoliceBirthPlace.getFont().split(",")[0], Integer.parseInt(elementPoliceBirthPlace.getFont().split(",")[1]), Integer.parseInt(elementPoliceBirthPlace.getFont().split(",")[2])));
				policeBirthPlace.setNewColor(new Color(Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[0]), Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceBirthPlace.getColor().split(",")[2])));
			}
			ElementJoueur elementHeight = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Height", index);// elementWindows.getPlayer().get("Height");
			ElementPoliceJoueur elementPoliceHeight= configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Height", index);
			if (elementHeight != null) {
				// System.out
				// .println(elementHeight.getPositionX() + "|" + elementHeight.getPositionY() + "|" + elementHeight.isVisible() + "|" + elementHeight.getFont() + "|" + elementHeight.getColor());
				playerHeight.setBounds(elementHeight.getPositionX(), elementHeight.getPositionY(), 10, 10);
				playerHeight.setVisible(elementPoliceHeight.isVisible());
				policeHeight
						.setNewfont(new Font(elementPoliceHeight.getFont().split(",")[0], Integer.parseInt(elementPoliceHeight.getFont().split(",")[1]), Integer.parseInt(elementPoliceHeight.getFont().split(",")[2])));
				policeHeight.setNewColor(new Color(Integer.parseInt(elementPoliceHeight.getColor().split(",")[0]), Integer.parseInt(elementPoliceHeight.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceHeight.getColor().split(",")[2])));
			}
			ElementJoueur elementWeight = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Weight", index);// elementWindows.getPlayer().get("Weight");
			ElementPoliceJoueur elementPoliceWeight = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Weight", index);
			if (elementWeight != null) {
				// System.out
				// .println(elementWeight.getPositionX() + "|" + elementWeight.getPositionY() + "|" + elementWeight.isVisible() + "|" + elementWeight.getFont() + "|" + elementWeight.getColor());
				playerWeight.setBounds(elementWeight.getPositionX(), elementWeight.getPositionY(), 10, 10);
				playerWeight.setVisible(elementPoliceWeight.isVisible());
				policeWeight
						.setNewfont(new Font(elementPoliceWeight.getFont().split(",")[0], Integer.parseInt(elementPoliceWeight.getFont().split(",")[1]), Integer.parseInt(elementPoliceWeight.getFont().split(",")[2])));
				policeWeight.setNewColor(new Color(Integer.parseInt(elementPoliceWeight.getColor().split(",")[0]), Integer.parseInt(elementPoliceWeight.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceWeight.getColor().split(",")[2])));
			}
			ElementJoueur elementHand = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Hand", index);// elementWindows.getPlayer().get("Hand");
			ElementPoliceJoueur elementPoliceHand = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Hand", index);
			if (elementHand != null) {
				// System.out.println(elementHand.getPositionX() + "|" + elementHand.getPositionY() + "|" + elementHand.isVisible() + "|" + elementHand.getFont() + "|" + elementHand.getColor());
				if(typeFen != "tab")playerHand.setBounds(elementHand.getPositionX(), elementHand.getPositionY(), 10, 10);
				playerHand.setVisible(elementPoliceHand.isVisible());
				policeHand.setNewfont(new Font(elementPoliceHand.getFont().split(",")[0], Integer.parseInt(elementPoliceHand.getFont().split(",")[1]), Integer.parseInt(elementPoliceHand.getFont().split(",")[2])));
				policeHand.setNewColor(new Color(Integer.parseInt(elementPoliceHand.getColor().split(",")[0]), Integer.parseInt(elementPoliceHand.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceHand.getColor().split(",")[2])));
			}
			ElementJoueur elementAge = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Age", index);// elementWindows.getPlayer().get("Age");
			ElementPoliceJoueur elementPoliceAge = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Age", index);
			if (elementAge != null) {
				// System.out.println(elementAge.getPositionX() + "|" + elementAge.getPositionY() + "|" + elementAge.isVisible() + "|" + elementAge.getFont() + "|" + elementAge.getColor());
				playerAge.setBounds(elementAge.getPositionX(), elementAge.getPositionY(), 10, 10);
				playerAge.setVisible(elementPoliceAge.isVisible());
				policeAge.setNewfont(new Font(elementPoliceAge.getFont().split(",")[0], Integer.parseInt(elementPoliceAge.getFont().split(",")[1]), Integer.parseInt(elementPoliceAge.getFont().split(",")[2])));
				policeAge.setNewColor(
						new Color(Integer.parseInt(elementPoliceAge.getColor().split(",")[0]), Integer.parseInt(elementPoliceAge.getColor().split(",")[1]), Integer.parseInt(elementPoliceAge.getColor().split(",")[2])));
			}
			ElementJoueur elementPrizeTotal = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Prizetotal", index);// elementWindows.getPlayer().get("Prizetotal");
			ElementPoliceJoueur elementPolicePrizeTotal = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Prizetotal", index);
			if (elementPrizeTotal != null) {
				// System.out.println(elementPrizeTotal.getPositionX() + "|" + elementPrizeTotal.getPositionY() + "|" + elementPrizeTotal.isVisible() + "|" + elementPrizeTotal.getFont() + "|"
				// + elementPrizeTotal.getColor());
				playerPrizetotal.setBounds(elementPrizeTotal.getPositionX(), elementPrizeTotal.getPositionY(), 10, 10);
				playerPrizetotal.setVisible(elementPolicePrizeTotal.isVisible());
				policePrizetotal.setNewfont(
						new Font(elementPolicePrizeTotal.getFont().split(",")[0], Integer.parseInt(elementPolicePrizeTotal.getFont().split(",")[1]), Integer.parseInt(elementPolicePrizeTotal.getFont().split(",")[2])));
				policePrizetotal.setNewColor(new Color(Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[0]), Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[1]),
						Integer.parseInt(elementPolicePrizeTotal.getColor().split(",")[2])));
			}
			ElementJoueur elementCityResidence = configData.getElement(emplacementPlayer, nomEvent, typeFen, "CityResidence", index);// elementWindows.getPlayer().get("CityResidence");
			ElementPoliceJoueur elementPoliceCityResidence = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "CityResidence", index);
			if (elementCityResidence != null) {
				playerCityresidence.setBounds(elementCityResidence.getPositionX(), elementCityResidence.getPositionY(), 10, 10);
				playerCityresidence.setVisible(elementPoliceCityResidence.isVisible());
				policeCityresidence.setNewfont(new Font(elementPoliceCityResidence.getFont().split(",")[0], Integer.parseInt(elementPoliceCityResidence.getFont().split(",")[1]),
						Integer.parseInt(elementPoliceCityResidence.getFont().split(",")[2])));
				policeCityresidence.setNewColor(new Color(Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[0]), Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceCityResidence.getColor().split(",")[2])));
			}
			ElementJoueur elementLine = configData.getElement(emplacementPlayer, nomEvent, typeFen, "Line", index);// elementWindows.getPlayer().get("Line");
			ElementPoliceJoueur elementPoliceLine = configData.getElementPolice(emplacementPlayer, nomEvent, typeFen, "Line", index);
			if (elementLine != null) {
				// System.out.println(elementLine.getPositionX() + "|" + elementLine.getPositionY() + "|" + elementLine.isVisible() + "|" + elementLine.getFont() + "|" + elementLine.getColor());
				playerLine.setBounds(elementLine.getPositionX(), elementLine.getPositionY(), 10, 10);
				playerLine.setVisible(elementPoliceLine.isVisible());
				policeLine.setNewfont(new Font(elementPoliceLine.getFont().split(",")[0], Integer.parseInt(elementPoliceLine.getFont().split(",")[1]), Integer.parseInt(elementPoliceLine.getFont().split(",")[2])));
				policeLine.setNewColor(new Color(Integer.parseInt(elementPoliceLine.getColor().split(",")[0]), Integer.parseInt(elementPoliceLine.getColor().split(",")[1]),
						Integer.parseInt(elementPoliceLine.getColor().split(",")[2])));
			}
		}else {
			System.out.println("-----> config data null");
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
				//System.out.println(typeFen);
				//placementFrameTwoPlayer.tabInfosJ1.confirmTabPlayer();
				placementFrameTwoPlayer.tabInfosJ1.refreshSpinner(playerfordifusion2);
				break;
			}
			case "game": {
				//System.out.println(typeFen);
				//System.out.println("joueur selectionn� : "+numeroPlayer);
				if(numeroPlayer == 1) {
					placementFrameTwoPlayer.tabbedPane.setSelectedIndex(0);
					placementFrameTwoPlayer.tabInfosJ1.refreshSpinner(playerfordifusion2);					
				}
				
				if(numeroPlayer == 2) {
					placementFrameTwoPlayer.tabbedPane.setSelectedIndex(1);
					placementFrameTwoPlayer.tabInfosJ2.refreshSpinner(playerfordifusion2);
				}
				break;
			}
			case "tab": {
				placementFrameTwoPlayer.tabbedPane.setSelectedIndex(getNumeroPlayer());
				Component selectedComponent = placementFrameTwoPlayer.tabbedPane.getSelectedComponent();
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
		            currentTab.refreshSpinner(playerfordifusion2);
		        } else {
		            // Handle the case where the selected component is not of type tabInfosPlayer
		        	placementFrameTwoPlayer.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
		            System.out.println("----- Error: Selected component is not an instance of tabInfosPlayer");
		        }
                break;
			}
			case "full": {
				placementFrameTwoPlayer.tabbedPane.setSelectedIndex(getNumeroPlayer());
				Component selectedComponent = placementFrameTwoPlayer.tabbedPane.getSelectedComponent();
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
		            currentTab.refreshSpinner(playerfordifusion2);
		        } else {
		            // Handle the case where the selected component is not of type tabInfosPlayer
		        	placementFrameTwoPlayer.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
		            System.out.println("----- Error: Selected component is not an instance of tabInfosPlayer");
		        }
                break;
//				System.out.println("numer player selectionn� : "+getNumeroPlayer());
//				placementFrameTwoPlayer.tabbedPane.setSelectedIndex(getNumeroPlayer()+1);
//				tabInfosPlayer currentTab = (tabInfosPlayer) placementFrameTwoPlayer.tabbedPane.getSelectedComponent();
//				currentTab.refreshSpinner(playerfordifusion2);
//				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + typeFen);
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
		this.placementFrameTwoPlayer.dispose();
//		new Configuration().saveConfiguration(playerName.getX(), playerName.getY(), placementFrame.checkboxName.isSelected(), playerName.getFont().toString());
	}
}
