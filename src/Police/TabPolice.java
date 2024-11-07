/*
 * 
 */
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
import DiffusionPlayers.PlayerForDiffusion;

// TODO: Auto-generated Javadoc
/**
 * The Class TabPolice.
 */
public class TabPolice extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The checkbox name. */
	public JCheckBox checkboxName;
	
	/** The checkbox surname. */
	public JCheckBox checkboxSurname;
	
	/** The checkbox acro. */
	public JCheckBox checkboxAcro;
	
	/** The checkbox img. */
	public JCheckBox checkboxImg;
	
	/** The checkbox flag. */
	public JCheckBox checkboxFlag;
	
	/** The checkbox rank. */
	public JCheckBox checkboxRank;
	
	/** The checkbox birth date. */
	public JCheckBox checkboxBirthDate;
	
	/** The checkbox birth place. */
	public JCheckBox checkboxBirthPlace;
	
	/** The checkbox height. */
	public JCheckBox checkboxHeight;
	
	/** The checkbox weight. */
	public JCheckBox checkboxWeight;
	
	/** The checkbox hand. */
	public JCheckBox checkboxHand;
	
	/** The checkbox age. */
	public JCheckBox checkboxAge;
	
	/** The checkbox prizetotal. */
	public JCheckBox checkboxPrizetotal;
	
	/** The checkbox cityresidence. */
	public JCheckBox checkboxCityresidence;
	
	/** The checkbox line. */
	public JCheckBox checkboxLine;
	
	/** The Taille img. */
	// ---------------------------------
	public JSpinner TailleImg;
	
	/** The Taille flag. */
	public JSpinner TailleFlag;	
	
	/** The list player. */
	private ArrayList<PlayerForDiffusion> listPlayer;
	
	/**
	 * Sets the list player.
	 *
	 * @param listPlayerUpdate the new list player
	 */
	public void setListPlayer(ArrayList<PlayerForDiffusion> listPlayerUpdate) {
		this.listPlayer = listPlayerUpdate;
	}

	/**
	 * Instantiates a new tab police.
	 *
	 * @param listPlayerDisplay the list player display
	 * @param placemenFenetre the placemen fenetre
	 */
	public TabPolice(ArrayList<PlayerForDiffusion> listPlayerDisplay, WindowConfigurationPlayerInfos placemenFenetre) {
		// Initialisation des JCheckBox pour chaque �l�ment
		this.listPlayer = listPlayerDisplay;
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
		
		TailleImg = new JSpinner(new SpinnerNumberModel(listPlayer.get(0).tailleImgJoueur, 0, 10000, 1));
		TailleFlag = new JSpinner(new SpinnerNumberModel(listPlayer.get(0).tailleImgFlag, 0, 10000, 1));		

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
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerName.getComponents()[0].getFont(), listPlayer.get(0).playerName.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerName.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerName.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeName.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeName.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerName.setSize(playerForDiffusion.playerName.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontSurname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerSurname.getComponents()[0].getFont(), listPlayer.get(0).playerSurname.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerSurname.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerSurname.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeSurname.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeSurname.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerSurname.setSize(playerForDiffusion.playerSurname.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontAcro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerAcro.getComponents()[0].getFont(), listPlayer.get(0).playerAcro.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerAcro.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerAcro.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeAcro.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeAcro.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerAcro.setSize(playerForDiffusion.playerAcro.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerRank.getComponents()[0].getFont(), listPlayer.get(0).playerRank.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerRank.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerRank.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeRank.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeRank.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerRank.setSize(playerForDiffusion.playerRank.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontBirthDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerBirthdate.getComponents()[0].getFont(), listPlayer.get(0).playerBirthdate.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerBirthdate.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerBirthdate.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeBirthDate.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeBirthDate.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerBirthdate.setSize(playerForDiffusion.playerBirthdate.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontBirthPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerBirthplace.getComponents()[0].getFont(), listPlayer.get(0).playerBirthplace.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerBirthplace.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerBirthplace.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeBirthPlace.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeBirthPlace.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerBirthplace.setSize(playerForDiffusion.playerBirthplace.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerHeight.getComponents()[0].getFont(), listPlayer.get(0).playerHeight.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerHeight.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerHeight.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeHeight.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeHeight.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerHeight.setSize(playerForDiffusion.playerHeight.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontWeigh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerWeight.getComponents()[0].getFont(), listPlayer.get(0).playerWeight.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerWeight.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerWeight.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeWeight.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeWeight.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerWeight.setSize(playerForDiffusion.playerWeight.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerHand.getComponents()[0].getFont(), listPlayer.get(0).playerHand.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerHand.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerHand.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeHand.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeHand.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerHand.setSize(playerForDiffusion.playerHand.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontAge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerAge.getComponents()[0].getFont(), listPlayer.get(0).playerAge.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerAge.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerAge.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeAge.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeAge.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerAge.setSize(playerForDiffusion.playerAge.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontPrizetotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerPrizetotal.getComponents()[0].getFont(), listPlayer.get(0).playerPrizetotal.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerPrizetotal.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerPrizetotal.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policePrizetotal.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policePrizetotal.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerPrizetotal.setSize(playerForDiffusion.playerPrizetotal.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontCityresidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerCityresidence.getComponents()[0].getFont(), listPlayer.get(0).playerCityresidence.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerCityresidence.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerCityresidence.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeCityresidence.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeCityresidence.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerCityresidence.setSize(playerForDiffusion.playerCityresidence.getComponents()[0].getPreferredSize());
				}
			}
		});
		buttonFontLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelector fontSelector = new FontSelector(listPlayer.get(0).playerLine.getComponents()[0].getFont(), listPlayer.get(0).playerLine.getComponents()[0].getForeground());
				for (PlayerForDiffusion playerForDiffusion : listPlayer) {
					playerForDiffusion.playerLine.getComponents()[0].setFont(fontSelector.getPoliceComlet());
					playerForDiffusion.playerLine.getComponents()[0].setForeground(fontSelector.getChoosenColor());
					playerForDiffusion.policeLine.setNewfont(fontSelector.getPoliceComlet());
					playerForDiffusion.policeLine.setNewColor(fontSelector.getChoosenColor());
					playerForDiffusion.playerLine.setSize(playerForDiffusion.playerLine.getComponents()[0].getPreferredSize());
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
		addComponent(panelTab, gbc, new JLabel("Line :"), 0, choixColonne);
		addComponent(panelTab, gbc, checkboxLine, 1, choixColonne);
		addComponent(panelTab, gbc, buttonFontLine, 2, choixColonne);
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
		
		this.setLayout(new BorderLayout());
		this.add(panelTab, BorderLayout.NORTH);// ,BorderLayout.NORTH);
//		this.add(validationButtonAll, BorderLayout.SOUTH);
//		placemenFrame.refreshAllTab();
	}
	
	/**
	 * Adds the component.
	 *
	 * @param container the container
	 * @param gbc the gbc
	 * @param component the component
	 * @param gridx the gridx
	 * @param gridy the gridy
	 */
	private void addComponent(Container container, GridBagConstraints gbc, Component component, int gridx, int gridy) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets.set(2, 4, 6, 8);
		container.add(component, gbc);
	}
}
