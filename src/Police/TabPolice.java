package Police;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Diffusion.WindowConfigurationPlayerInfos;
import Diffusion.PlayerForDiffusion;

public class TabPolice extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JCheckBox checkboxName;
	public JCheckBox checkboxSurname;
	public JCheckBox checkboxAcro;
	public JCheckBox checkboxImg;
	public JCheckBox checkboxFlag;
	public JCheckBox checkboxRank;
	public JCheckBox checkboxBirthDate;
	public JCheckBox checkboxBirthPlace;
	public JCheckBox checkboxHeight;
	public JCheckBox checkboxWeight;
	public JCheckBox checkboxHand;
	public JCheckBox checkboxAge;
	public JCheckBox checkboxPrizetotal;
	public JCheckBox checkboxCityresidence;
	public JCheckBox checkboxLine;
	// ---------------------------------
	public JSpinner TailleImg;
	public JSpinner TailleFlag;	
	
	

	public TabPolice(ArrayList<PlayerForDiffusion> listPlayer, WindowConfigurationPlayerInfos placemenFenetre) {
		// Initialisation des JCheckBox pour chaque �l�ment
		listPlayer.get(0).playerAcro.isVisible();
		checkboxName = new JCheckBox("Display name",listPlayer.get(0).playerName.isVisible());
		checkboxSurname = new JCheckBox("Display surname",listPlayer.get(0).playerSurname.isVisible());
		checkboxAcro = new JCheckBox("Display acronym",listPlayer.get(0).playerAcro.isVisible());
		checkboxImg = new JCheckBox("Display image",listPlayer.get(0).playerImg.isVisible());
		checkboxFlag = new JCheckBox("Display flag",listPlayer.get(0).playerFlag.isVisible());
		checkboxRank = new JCheckBox("Display rank",listPlayer.get(0).playerRank.isVisible());
		checkboxBirthDate = new JCheckBox("Display birth date",listPlayer.get(0).playerBirthdate.isVisible());
		checkboxBirthPlace = new JCheckBox("Display birth place",listPlayer.get(0).playerBirthplace.isVisible());
		checkboxHeight = new JCheckBox("Display height",listPlayer.get(0).playerHeight.isVisible());
		checkboxWeight = new JCheckBox("Display weight",listPlayer.get(0).playerWeight.isVisible());
		checkboxHand = new JCheckBox("Display hand",listPlayer.get(0).playerHand.isVisible());
		checkboxAge = new JCheckBox("Display age",listPlayer.get(0).playerAge.isVisible());
		checkboxPrizetotal = new JCheckBox("Display prize total",listPlayer.get(0).playerPrizetotal.isVisible());
		checkboxCityresidence = new JCheckBox("Display city residence",listPlayer.get(0).playerCityresidence.isVisible());
		checkboxLine = new JCheckBox("Display line",listPlayer.get(0).playerLine.isVisible());
		
		TailleImg = new JSpinner(new SpinnerNumberModel(listPlayer.get(0).tailleImgJoueur, 0, 1980, 1));
		TailleFlag = new JSpinner(new SpinnerNumberModel(listPlayer.get(0).tailleImgFlag, 0, 1980, 1));
		
//		JButton validationButtonAll = new JButton("confirm config");
//		validationButtonAll.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				placemenFrame.confirmAllTab();
//				placemenFrame.refreshAllTab();
//			}
//		});
		

		JButton buttonFontName = new JButton("Choose Font");
		JButton buttonFontSurname = new JButton("Choose Font");
		JButton buttonFontAcro = new JButton("Choose Font");
		JButton buttonFontRank = new JButton("Choose Font");
		JButton buttonFontBirthDate = new JButton("Choose Font");
		JButton buttonFontBirthPlace = new JButton("Choose Font");
		JButton buttonFontHeight = new JButton("Choose Font");
		JButton buttonFontWeigh = new JButton("Choose Font");
		JButton buttonFontHand = new JButton("Choose Font");
		JButton buttonFontAge = new JButton("Choose Font");
		JButton buttonFontPrizetotal = new JButton("Choose Font");
		JButton buttonFontCityresidence = new JButton("Choose Font");
		JButton buttonFontLine = new JButton("Choose Font");
		// --------------------------------------action des boutons--------------------------------------
		buttonFontName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).nameLabel.getFont(), listPlayer.get(0).nameLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.nameLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.nameLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerName.setSize(playerForDiffusion.nameLabel.getPreferredSize());
				}
			}
		});
		buttonFontSurname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).SurnameLabel.getFont(), listPlayer.get(0).SurnameLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.SurnameLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.SurnameLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerSurname.setSize(playerForDiffusion.SurnameLabel.getPreferredSize());
				}
			}
		});
		buttonFontAcro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).AcroLabel.getFont(), listPlayer.get(0).AcroLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.AcroLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.AcroLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerAcro.setSize(playerForDiffusion.AcroLabel.getPreferredSize());
				}
			}
		});
		buttonFontRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).RankLabel.getFont(), listPlayer.get(0).RankLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.RankLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.RankLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerRank.setSize(playerForDiffusion.RankLabel.getPreferredSize());
				}
			}
		});
		buttonFontBirthDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).birthdateLabel.getFont(), listPlayer.get(0).birthdateLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.birthdateLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.birthdateLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerBirthdate.setSize(playerForDiffusion.birthdateLabel.getPreferredSize());
				}
			}
		});
		buttonFontBirthPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).birthPlaceLabel.getFont(), listPlayer.get(0).birthPlaceLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.birthPlaceLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.birthPlaceLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerBirthplace.setSize(playerForDiffusion.birthPlaceLabel.getPreferredSize());
				}
			}
		});
		buttonFontHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).heightLabel.getFont(), listPlayer.get(0).heightLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.heightLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.heightLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerHeight.setSize(playerForDiffusion.heightLabel.getPreferredSize());
				}
			}
		});
		buttonFontWeigh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).weightLabel.getFont(), listPlayer.get(0).weightLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.weightLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.weightLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerWeight.setSize(playerForDiffusion.weightLabel.getPreferredSize());
				}
			}
		});
		buttonFontHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).handLabel.getFont(), listPlayer.get(0).handLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.handLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.handLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerHand.setSize(playerForDiffusion.handLabel.getPreferredSize());
				}
			}
		});
		buttonFontAge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).ageLabel.getFont(), listPlayer.get(0).ageLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.ageLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.ageLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerAge.setSize(playerForDiffusion.ageLabel.getPreferredSize());
				}
			}
		});
		buttonFontPrizetotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).prizetotalLabel.getFont(), listPlayer.get(0).prizetotalLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.prizetotalLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.prizetotalLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerPrizetotal.setSize(playerForDiffusion.prizetotalLabel.getPreferredSize());
				}
			}
		});
		buttonFontCityresidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).cityresidenceLabel.getFont(), listPlayer.get(0).cityresidenceLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.cityresidenceLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.cityresidenceLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerCityresidence.setSize(playerForDiffusion.cityresidenceLabel.getPreferredSize());
				}
			}
		});
		buttonFontLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).lineLabel.getFont(), listPlayer.get(0).lineLabel.getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.lineLabel.setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.lineLabel.setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.playerLine.setSize(playerForDiffusion.lineLabel.getPreferredSize());
				}
			}
		});
		// -----------------------------tableau avec les differents elements --------------------------------------------------------------------------------------
		JPanel panelTab = new JPanel();
		panelTab.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		int choixColonne = 0;
		addComponent(panelTab, gbc, new JLabel("     "), 0, choixColonne);
		addComponent(panelTab, gbc, new JLabel("Visible"), 1, choixColonne);
		addComponent(panelTab, gbc, new JLabel("ChooseFont"), 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Name :"), 0, choixColonne);		
		addComponent(panelTab, gbc, checkboxName, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontName,2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Surname :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxSurname, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontSurname, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Img player :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxImg, 1, choixColonne);
		addComponent(panelTab, gbc, TailleImg, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Flag :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxFlag, 1, choixColonne);
		addComponent(panelTab, gbc, TailleFlag, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Country :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxAcro, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontAcro, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Rank :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxRank, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontRank, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Birthdate :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxBirthDate, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontBirthDate, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Birth Place :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxBirthPlace, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontBirthPlace,2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Height :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxHeight, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontHeight, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Weight :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxWeight, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontWeigh, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Hand :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxHand, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontHand, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Age :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxAge, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontAge, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Prize total :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxPrizetotal, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontPrizetotal, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("City residence :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxCityresidence, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontCityresidence, 2, choixColonne);
		choixColonne++;
		addComponent(panelTab, gbc, new JLabel("Line :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxLine, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontLine, 2, choixColonne);
		choixColonne++;
		this.setLayout(new BorderLayout());
		this.add(panelTab, BorderLayout.NORTH);// ,BorderLayout.NORTH);
//		this.add(validationButtonAll, BorderLayout.SOUTH);
//		placemenFrame.refreshAllTab();
	}
	private void addComponent(Container container, GridBagConstraints gbc, Component component, int gridx, int gridy) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets.set(2, 4, 6, 8);
		container.add(component, gbc);
	}
}
