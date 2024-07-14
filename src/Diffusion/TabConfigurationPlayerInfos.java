package Diffusion;

/*
 * permet de recuperer et modifier les position de chaque element des joueurs
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Main.BDD_v2;
import Main.ImageUtility;
import Players.Joueur;

public class TabConfigurationPlayerInfos extends JPanel {
	private static final long serialVersionUID = 1L;

	private JSpinner positionXname;
	public SpinnerNumberModel nameXvalue;
	private JSpinner positionYname;
	public SpinnerNumberModel nameYvalue;

	private JSpinner positionXSurname;
	public SpinnerNumberModel SurnameXvalue;
	private JSpinner positionYSurname;
	public SpinnerNumberModel SurnameYvalue;

	private JSpinner positionXAcro;
	public SpinnerNumberModel AcroXvalue;
	private JSpinner positionYAcro;
	public SpinnerNumberModel AcroYvalue;

	private JSpinner positionXImg;
	public SpinnerNumberModel ImgXvalue;
	private JSpinner positionYImg;
	public SpinnerNumberModel ImgYvalue;
//	private JSpinner TailleImg;

	private JSpinner positionXFlag;
	public SpinnerNumberModel FlagXvalue;
	private JSpinner positionYFlag;
	public SpinnerNumberModel FlagYvalue;
//	private JSpinner TailleFlag;

	private JSpinner positionXRank;
	public SpinnerNumberModel RankXvalue;
	private JSpinner positionYRank;
	public SpinnerNumberModel RankYvalue;

	private JSpinner positionXBirthDate;
	public SpinnerNumberModel BirthDateXvalue;
	private JSpinner positionYBirthDate;
	public SpinnerNumberModel BirthDateYvalue;

	private JSpinner positionXBirthPlace;
	public SpinnerNumberModel BirthPlaceXvalue;
	private JSpinner positionYBirthPlace;
	public SpinnerNumberModel BirthPlaceYvalue;

	private JSpinner positionXHeight;
	public SpinnerNumberModel HeightXvalue;
	private JSpinner positionYHeight;
	public SpinnerNumberModel HeightYvalue;

	private JSpinner positionXWeight;
	public SpinnerNumberModel WeightXvalue;
	private JSpinner positionYWeight;
	public SpinnerNumberModel WeightYvalue;

	private JSpinner positionXHand;
	public SpinnerNumberModel HandXvalue;
	private JSpinner positionYHand;
	public SpinnerNumberModel HandYvalue;

	private JSpinner positionXAge;
	public SpinnerNumberModel AgeXvalue;
	private JSpinner positionYAge;
	public SpinnerNumberModel AgeYvalue;

	private JSpinner positionXPrizetotal;
	public SpinnerNumberModel PrizetotalXvalue;
	private JSpinner positionYPrizetotal;
	public SpinnerNumberModel PrizetotalYvalue;

	private JSpinner positionXCityresidence;
	public SpinnerNumberModel CityresidenceXvalue;
	private JSpinner positionYCityresidence;
	public SpinnerNumberModel CityresidenceYvalue;

	private JSpinner positionXLine;
	public SpinnerNumberModel lineXvalue;
	private JSpinner positionYLine;
	public SpinnerNumberModel lineYvalue;

	private WindowBroadcastPublic dysplayFrame;
	private Joueur joueur;
	private PlayerForDiffusion infosPlayerDetails;
	private WindowConfigurationPlayerInfos placementFrame;

	public String getJoueurName() {
		return joueur.getDisplay_name();
	}

	public PlayerForDiffusion getInfosPlayerDetails() {		
		return infosPlayerDetails;
	}

	public TabConfigurationPlayerInfos(PlayerForDiffusion infosPlayerDetail, Joueur joueure, WindowBroadcastPublic displayFrame, WindowConfigurationPlayerInfos placementFrame) {
		this.dysplayFrame = displayFrame;
		this.joueur = joueure;
		this.infosPlayerDetails = infosPlayerDetail;
		this.placementFrame = placementFrame;
		// choix position nom -----------------------------------------------------
		nameXvalue = new SpinnerNumberModel();
		nameXvalue.setValue(0);
		nameYvalue = new SpinnerNumberModel();
		nameYvalue.setValue(0);
		positionXname = new JSpinner(nameXvalue);
		positionYname = new JSpinner(nameYvalue);
//		JButton validationButtonName = new JButton("confirm");
		// choix position pr�nom ---------------------------------------------------
		SurnameXvalue = new SpinnerNumberModel();
		SurnameXvalue.setValue(0);
		SurnameYvalue = new SpinnerNumberModel();
		SurnameYvalue.setValue(0);
		positionXSurname = new JSpinner(SurnameXvalue);
		positionYSurname = new JSpinner(SurnameYvalue);
//		JButton validationButtonSurname = new JButton("confirm");
		// choix position + taille image joueur ---------------------------------------------------
		ImgXvalue = new SpinnerNumberModel();
		ImgXvalue.setValue(0);
		ImgYvalue = new SpinnerNumberModel();
		ImgYvalue.setValue(0);
		positionXImg = new JSpinner(ImgXvalue);
		positionYImg = new JSpinner(ImgYvalue);
//		TailleImg = new JSpinner(new SpinnerNumberModel(200, 0, 1980, 1));
//		JButton validationButtonIMG = new JButton("confirm");
//			        choix position + taille image drapeau ----------------------------------------------------
		FlagXvalue = new SpinnerNumberModel();
		FlagXvalue.setValue(0);
		FlagYvalue = new SpinnerNumberModel();
		FlagYvalue.setValue(0);
		positionXFlag = new JSpinner(FlagXvalue);
		positionYFlag = new JSpinner(FlagYvalue);
//		TailleFlag = new JSpinner(new SpinnerNumberModel(200, 0, 1980, 1));
//		JButton validationButtonFlag = new JButton("confirm");
		// choix position acronyme pays --------------------------------------------------------------
		AcroXvalue = new SpinnerNumberModel();
		AcroXvalue.setValue(0);
		AcroYvalue = new SpinnerNumberModel();
		AcroYvalue.setValue(0);
		positionXAcro = new JSpinner(AcroXvalue);
		positionYAcro = new JSpinner(AcroYvalue);
//		JButton validationButtonAcro = new JButton("confirm");
		// choix position rank ----------------------------------------------------------------------
		RankXvalue = new SpinnerNumberModel();
		RankXvalue.setValue(0);
		RankYvalue = new SpinnerNumberModel();
		RankYvalue.setValue(0);
		positionXRank = new JSpinner(RankXvalue);
		positionYRank = new JSpinner(RankYvalue);
//		JButton validationButtonRank = new JButton("confirm");
		// choix position brithdate --------------------------------------------------------------------
		BirthDateXvalue = new SpinnerNumberModel();
		BirthDateXvalue.setValue(0);
		BirthDateYvalue = new SpinnerNumberModel();
		BirthDateYvalue.setValue(0);
		positionXBirthDate = new JSpinner(BirthDateXvalue);
		positionYBirthDate = new JSpinner(BirthDateYvalue);
//		JButton validationButtonBirthDate = new JButton("confirm");
		// choix position brithplace --------------------------------------------------------------------
		BirthPlaceXvalue = new SpinnerNumberModel();
		BirthPlaceXvalue.setValue(0);
		BirthPlaceYvalue = new SpinnerNumberModel();
		BirthPlaceYvalue.setValue(0);
		positionXBirthPlace = new JSpinner(BirthPlaceXvalue);
		positionYBirthPlace = new JSpinner(BirthPlaceYvalue);
//		JButton validationButtonBirthPlace = new JButton("confirm");
		// Choix de position height ------------------------------------------------------
		HeightXvalue = new SpinnerNumberModel();
		HeightXvalue.setValue(0);
		HeightYvalue = new SpinnerNumberModel();
		HeightYvalue.setValue(0);
		positionXHeight = new JSpinner(HeightXvalue);
		positionYHeight = new JSpinner(HeightYvalue);
//		JButton validationButtonHeight = new JButton("confirm");
		// Choix de position weight ------------------------------------------------------------
		WeightXvalue = new SpinnerNumberModel();
		WeightXvalue.setValue(0);
		WeightYvalue = new SpinnerNumberModel();
		WeightYvalue.setValue(0);
		positionXWeight = new JSpinner(WeightXvalue);
		positionYWeight = new JSpinner(WeightYvalue);
//		JButton validationButtonWeight = new JButton("confirm");
		// Choix de position hand ------------------------------------------------------------------------
		HandXvalue = new SpinnerNumberModel();
		HandXvalue.setValue(0);
		HandYvalue = new SpinnerNumberModel();
		HandYvalue.setValue(0);
		positionXHand = new JSpinner(HandXvalue);
		positionYHand = new JSpinner(HandYvalue);
//		JButton validationButtonHand = new JButton("confirm");
		// Choix de position age ----------------------------------------------------------------------------
		AgeXvalue = new SpinnerNumberModel();
		AgeXvalue.setValue(0);
		AgeYvalue = new SpinnerNumberModel();
		AgeYvalue.setValue(0);
		positionXAge = new JSpinner(AgeXvalue);
		positionYAge = new JSpinner(AgeYvalue);
//		JButton validationButtonAge = new JButton("confirm");
		// Choix de position prizetotal ------------------------------------------------------------------------
		PrizetotalXvalue = new SpinnerNumberModel();
		PrizetotalXvalue.setValue(0);
		PrizetotalYvalue = new SpinnerNumberModel();
		PrizetotalYvalue.setValue(0);
		positionXPrizetotal = new JSpinner(PrizetotalXvalue);
		positionYPrizetotal = new JSpinner(PrizetotalYvalue);
//		JButton validationButtonPrizetotal = new JButton("confirm");
		// Choix de position cityresidence ----------------------------------------------------------------------
		CityresidenceXvalue = new SpinnerNumberModel();
		CityresidenceXvalue.setValue(0);
		CityresidenceYvalue = new SpinnerNumberModel();
		CityresidenceYvalue.setValue(0);
		positionXCityresidence = new JSpinner(CityresidenceXvalue);
		positionYCityresidence = new JSpinner(CityresidenceYvalue);
//		JButton validationButtonCityresidence = new JButton("confirm");
		// Choix de position Ligne joueur ----------------------------------------------------------------------
		lineXvalue = new SpinnerNumberModel();
		lineXvalue.setValue(0);
		lineYvalue = new SpinnerNumberModel();
		lineYvalue.setValue(0);
		positionXLine = new JSpinner(lineXvalue);
		positionYLine = new JSpinner(lineYvalue);
		
		JPanel panelTab = new JPanel();
		panelTab.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		int choixColonne = 0;
		// Ajoutez vos composants avec les contraintes appropri�es
		addComponent(panelTab, gbc, new JLabel("     "), 0, choixColonne);
		addComponent(panelTab, gbc, new JLabel("X"), 1, choixColonne);
		addComponent(panelTab, gbc, new JLabel("Y"), 2, choixColonne);
		// ligne tableau pour name
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Name :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXname, 1, choixColonne);
		addComponent(panelTab, gbc, positionYname, 2, choixColonne);
		// ligne tableau pour Surname
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Surname :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXSurname, 1, choixColonne);
		addComponent(panelTab, gbc, positionYSurname, 2, choixColonne);
		// ligne tableau pour Img player
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Img player :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXImg, 1, choixColonne);
		addComponent(panelTab, gbc, positionYImg, 2, choixColonne);
		// ligne tableau pour Flag
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Flag :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXFlag, 1, choixColonne);
		addComponent(panelTab, gbc, positionYFlag, 2, choixColonne);
		// ligne tableau pour Country acronyme
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Country :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXAcro, 1, choixColonne);
		addComponent(panelTab, gbc, positionYAcro, 2, choixColonne);
		// ligne tableau pour Rank
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Rank :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXRank, 1, choixColonne);
		addComponent(panelTab, gbc, positionYRank, 2, choixColonne);
		// ligne tableau pour BirthDate
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Birthdate :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXBirthDate, 1, choixColonne);
		addComponent(panelTab, gbc, positionYBirthDate, 2, choixColonne);
		// ligne tableau pour BirthPlace
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Birth Place :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXBirthPlace, 1, choixColonne);
		addComponent(panelTab, gbc, positionYBirthPlace, 2, choixColonne);
		// ligne tableau pour Height
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Height :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXHeight, 1, choixColonne);
		addComponent(panelTab, gbc, positionYHeight, 2, choixColonne);
		// ligne tableau pour Weight
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Weight :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXWeight, 1, choixColonne);
		addComponent(panelTab, gbc, positionYWeight, 2, choixColonne);
		// ligne tableau pour Hand
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Hand :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXHand, 1, choixColonne);
		addComponent(panelTab, gbc, positionYHand, 2, choixColonne);
		// ligne tableau pour Age
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Age :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXAge, 1, choixColonne);
		addComponent(panelTab, gbc, positionYAge, 2, choixColonne);
		// ligne tableau pour PrizeTotal
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Prize total :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXPrizetotal, 1, choixColonne);
		addComponent(panelTab, gbc, positionYPrizetotal, 2, choixColonne);
		// ligne tableau pour CityResidence
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("City residence :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXCityresidence, 1, choixColonne);
		addComponent(panelTab, gbc, positionYCityresidence, 2, choixColonne);
		// ligne tableau pour Line
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Line :"), 0, choixColonne);
		addComponent(panelTab, gbc, positionXLine, 1, choixColonne);
		addComponent(panelTab, gbc, positionYLine, 2, choixColonne);
		choixColonne++;
		this.setLayout(new BorderLayout());
		this.add(panelTab, BorderLayout.NORTH);
		
		refreshSpinner(infosPlayerDetails);
	}

	protected void setPositionLine(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerLine.setVisible(placementFrame.getTabPolice().checkboxLine.isSelected());
		infosPlayerDetails.playerLine.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionBirthDate(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerBirthdate.setVisible(placementFrame.getTabPolice().checkboxBirthDate.isSelected());
		infosPlayerDetails.playerBirthdate.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionRank(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerRank.setVisible(placementFrame.getTabPolice().checkboxRank.isSelected());
		infosPlayerDetails.playerRank.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionAcro(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerAcro.setVisible(placementFrame.getTabPolice().checkboxAcro.isSelected());
		infosPlayerDetails.playerAcro.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionFlag(PlayerForDiffusion infosPlayerDetails, Joueur joueur, int x, int y, int taille) throws ClassNotFoundException, SQLException {
		infosPlayerDetails.playerFlag.setVisible(placementFrame.getTabPolice().checkboxFlag.isSelected());
		infosPlayerDetails.tailleImgFlag = taille;
		JLabel labelFlag = new ImageUtility(BDD_v2.getFlagImagePathByAcronym(joueur.getNatio_acronyme()), taille);
		infosPlayerDetails.playerFlag.removeAll();
		infosPlayerDetails.playerFlag.add(labelFlag);
		infosPlayerDetails.playerFlag.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
		infosPlayerDetails.playerFlag.setSize(labelFlag.getWidth(), labelFlag.getHeight());
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionImg(PlayerForDiffusion infosPlayerDetails, Joueur joueur, int x, int y, int taille) {
		infosPlayerDetails.playerImg.setVisible(placementFrame.getTabPolice().checkboxImg.isSelected());
		infosPlayerDetails.tailleImgJoueur = taille;
		JLabel labelImg = new ImageUtility(joueur.getImgJoueur(), taille);
		infosPlayerDetails.playerImg.removeAll();
		infosPlayerDetails.playerImg.add(labelImg);
		infosPlayerDetails.playerImg.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
		infosPlayerDetails.playerImg.setSize(labelImg.getWidth(), labelImg.getHeight());
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionSurname(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerSurname.setVisible(placementFrame.getTabPolice().checkboxSurname.isSelected());
		infosPlayerDetails.playerSurname.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	public void setPositionName(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerName.setVisible(placementFrame.getTabPolice().checkboxName.isSelected());
		infosPlayerDetails.playerName.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionBirthPlace(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerBirthplace.setVisible(placementFrame.getTabPolice().checkboxBirthPlace.isSelected());
		infosPlayerDetails.playerBirthplace.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionHeight(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerHeight.setVisible(placementFrame.getTabPolice().checkboxHeight.isSelected());
		infosPlayerDetails.playerHeight.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionWeight(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerWeight.setVisible(placementFrame.getTabPolice().checkboxHeight.isSelected());
		infosPlayerDetails.playerWeight.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionHand(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerHand.setVisible(placementFrame.getTabPolice().checkboxHand.isSelected());
		infosPlayerDetails.playerHand.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionAge(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerAge.setVisible(placementFrame.getTabPolice().checkboxAge.isSelected());
		infosPlayerDetails.playerAge.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionPrizetotal(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerPrizetotal.setVisible(placementFrame.getTabPolice().checkboxPrizetotal.isSelected());
		infosPlayerDetails.playerPrizetotal.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	protected void setPositionCityresidence(PlayerForDiffusion infosPlayerDetails, int x, int y) {
		infosPlayerDetails.playerCityresidence.setVisible(placementFrame.getTabPolice().checkboxCityresidence.isSelected());
		infosPlayerDetails.playerCityresidence.setLocation(x, y);
		dysplayFrame.revalidate();
		dysplayFrame.repaint();
	}

	private void addComponent(Container container, GridBagConstraints gbc, Component component, int gridx, int gridy) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets.set(2, 4, 6, 8);
		container.add(component, gbc);
	}

	public void confirmTabPlayer() {
		System.out.println("  Update position elements player from windowConfigurationPlayerInfos");
		setPositionName(infosPlayerDetails, (int) positionXname.getValue(), (int) positionYname.getValue());
		setPositionSurname(infosPlayerDetails, (int) positionXSurname.getValue(), (int) positionYSurname.getValue());
		setPositionImg(infosPlayerDetails, joueur, (int) positionXImg.getValue(), (int) positionYImg.getValue(), (int) infosPlayerDetails.getPlacementFrameTwoPlayer().getTabPolice().TailleImg.getValue());
		setPositionAcro(infosPlayerDetails, (int) positionXAcro.getValue(), (int) positionYAcro.getValue());
		setPositionRank(infosPlayerDetails, (int) positionXRank.getValue(), (int) positionYRank.getValue());
		setPositionBirthDate(infosPlayerDetails, (int) positionXBirthDate.getValue(), (int) positionYBirthDate.getValue());
		setPositionBirthPlace(infosPlayerDetails, (int) positionXBirthPlace.getValue(), (int) positionYBirthPlace.getValue());
		setPositionHeight(infosPlayerDetails, (int) positionXHeight.getValue(), (int) positionYHeight.getValue());
		setPositionWeight(infosPlayerDetails, (int) positionXWeight.getValue(), (int) positionYWeight.getValue());
		setPositionHand(infosPlayerDetails, (int) positionXHand.getValue(), (int) positionYHand.getValue());
		setPositionAge(infosPlayerDetails, (int) positionXAge.getValue(), (int) positionYAge.getValue());
		setPositionPrizetotal(infosPlayerDetails, (int) positionXPrizetotal.getValue(), (int) positionYPrizetotal.getValue());
		setPositionCityresidence(infosPlayerDetails, (int) positionXCityresidence.getValue(), (int) positionYCityresidence.getValue());
		setPositionLine(infosPlayerDetails, (int) positionXLine.getValue(), (int) positionYLine.getValue());
		try {
			setPositionFlag(infosPlayerDetails, joueur, (int) positionXFlag.getValue(), (int) positionYFlag.getValue(), (int) infosPlayerDetails.getPlacementFrameTwoPlayer().getTabPolice().TailleFlag.getValue());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refreshSpinner(PlayerForDiffusion infosPlayerDetails) {
		System.out.println("  Refresh player spinner : "+infosPlayerDetails.nameLabel.getText()+" current player : "+this.getJoueurName());
		nameXvalue.setValue(infosPlayerDetails.playerName.getLocation().x);
		nameYvalue.setValue(infosPlayerDetails.playerName.getLocation().y);
		SurnameXvalue.setValue(infosPlayerDetails.playerSurname.getLocation().x);
		SurnameYvalue.setValue(infosPlayerDetails.playerSurname.getLocation().y);
		ImgXvalue.setValue(infosPlayerDetails.playerImg.getLocation().x);
		ImgYvalue.setValue(infosPlayerDetails.playerImg.getLocation().y);
		FlagXvalue.setValue(infosPlayerDetails.playerFlag.getLocation().x);
		FlagYvalue.setValue(infosPlayerDetails.playerFlag.getLocation().y);
		AcroXvalue.setValue(infosPlayerDetails.playerAcro.getLocation().x);
		AcroYvalue.setValue(infosPlayerDetails.playerAcro.getLocation().y);
		RankXvalue.setValue(infosPlayerDetails.playerRank.getLocation().x);
		RankYvalue.setValue(infosPlayerDetails.playerRank.getLocation().y);
		BirthDateXvalue.setValue(infosPlayerDetails.playerBirthdate.getLocation().x);
		BirthDateYvalue.setValue(infosPlayerDetails.playerBirthdate.getLocation().y);
		BirthPlaceXvalue.setValue(infosPlayerDetails.playerBirthplace.getLocation().x);
		BirthPlaceYvalue.setValue(infosPlayerDetails.playerBirthplace.getLocation().y);
		HeightXvalue.setValue(infosPlayerDetails.playerHeight.getLocation().x);
		HeightYvalue.setValue(infosPlayerDetails.playerHeight.getLocation().y);
		WeightXvalue.setValue(infosPlayerDetails.playerWeight.getLocation().x);
		WeightYvalue.setValue(infosPlayerDetails.playerWeight.getLocation().y);
		HandXvalue.setValue(infosPlayerDetails.playerHand.getLocation().x);
		HandYvalue.setValue(infosPlayerDetails.playerHand.getLocation().y);
		AgeXvalue.setValue(infosPlayerDetails.playerAge.getLocation().x);
		AgeYvalue.setValue(infosPlayerDetails.playerAge.getLocation().y);
		PrizetotalXvalue.setValue(infosPlayerDetails.playerPrizetotal.getLocation().x);
		PrizetotalYvalue.setValue(infosPlayerDetails.playerPrizetotal.getLocation().y);
		CityresidenceXvalue.setValue(infosPlayerDetails.playerCityresidence.getLocation().x);
		CityresidenceYvalue.setValue(infosPlayerDetails.playerCityresidence.getLocation().y);
		lineXvalue.setValue(infosPlayerDetails.playerLine.getLocation().x);
		lineYvalue.setValue(infosPlayerDetails.playerLine.getLocation().y);
	}

}
