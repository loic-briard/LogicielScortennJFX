/*
 * 
 */
package Diffusion;

import java.awt.*;
import javax.swing.*;

import DiffusionPlayers.PlayerForDiffusion;

import java.util.HashMap;
import java.util.Map;
import Main.ImageUtility;
import Players.Joueur;
import Police.TabPolice;

// TODO: Auto-generated Javadoc
/**
 * The Class TabConfigurationPlayerInfos.
 */
public class TabConfigurationPlayerInfos extends JPanel {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The spinners. */
    private Map<String, JSpinner> spinners = new HashMap<>();
    
    /** The display frame. */
    private WindowBroadcastPublic displayFrame;
    
    /** The joueur. */
    private Joueur joueur;
    
    /** The infos player details. */
    private PlayerForDiffusion infosPlayerDetails;
    
    /** The placement frame. */
    private WindowConfigurationPlayerInfos placementFrame;
    
    /** The elements. */
    private String[] elements = {"name", "Surname", "Img", "Flag", "Acro", "Line", "Rank", "BirthDate", "BirthPlace", "Height", "Weight", "Hand", "Age", "Prizetotal", "Cityresidence","Seeding"};

    /**
     * Instantiates a new tab configuration player infos.
     *
     * @param infosPlayerDetail the infos player detail
     * @param joueur the joueur
     * @param displayFrame the display frame
     * @param placementFrame the placement frame
     */
    public TabConfigurationPlayerInfos(PlayerForDiffusion infosPlayerDetail, Joueur joueur, WindowBroadcastPublic displayFrame, WindowConfigurationPlayerInfos placementFrame) {
        this.displayFrame = displayFrame;
        this.joueur = joueur;
        this.infosPlayerDetails = infosPlayerDetail;
        this.placementFrame = placementFrame;

        initializeSpinners();
        createLayout();
        refreshSpinner(infosPlayerDetails);
    }

    /**
     * Initialize spinners.
     */
    private void initializeSpinners() {
        for (String element : elements) {
            spinners.put(element + "X", createSpinner());
            spinners.put(element + "Y", createSpinner());
        }
    }

    /**
     * Creates the spinner.
     *
     * @return the j spinner
     */
    private JSpinner createSpinner() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        return new JSpinner(model);
    }

    /**
     * Creates the layout.
     */
    private void createLayout() {
        setLayout(new BorderLayout());
        JPanel panelTab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);

        addComponent(panelTab, gbc, new JLabel("     "), 0, 0);
        addComponent(panelTab, gbc, new JLabel("X"), 1, 0);
        addComponent(panelTab, gbc, new JLabel("Y"), 2, 0);

        int row = 1;
        for (String element : elements) {
            addComponent(panelTab, gbc, new JLabel(element + " :"), 0, row);
            addComponent(panelTab, gbc, spinners.get(element + "X"), 1, row);
            addComponent(panelTab, gbc, spinners.get(element + "Y"), 2, row);
            row++;
        }

        add(panelTab, BorderLayout.NORTH);
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
        container.add(component, gbc);
    }

    /**
     * Confirm tab player.
     */
    public void confirmTabPlayer() {
        System.out.println("Update position elements player from windowConfigurationPlayerInfos");
        for (Map.Entry<String, JSpinner> entry : spinners.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("X")) {
                String element = key.substring(0, key.length() - 1);
                int x = (int) spinners.get(element + "X").getValue();
                int y = (int) spinners.get(element + "Y").getValue();
                setPositionElement(element, x, y);
            }
        }
    }

    /**
     * Sets the position element.
     *
     * @param element the element
     * @param x the x
     * @param y the y
     */
    private void setPositionElement(String element, int x, int y) {
        TabPolice tabPolice = placementFrame.getTabPolice();
        switch (element) {
            case "name":
                setPosition(infosPlayerDetails.playerName, x, y, tabPolice.checkboxName);
                break;
            case "Surname":
                setPosition(infosPlayerDetails.playerSurname, x, y, tabPolice.checkboxSurname);
                break;
            case "Img":
                setPositionImg(x, y);
                break;
            case "Flag":
                setPositionFlag(x, y);
                break;
            case "Acro":
                setPosition(infosPlayerDetails.playerAcro, x, y, tabPolice.checkboxAcro);
                break;
            case "Rank":
                setPosition(infosPlayerDetails.playerRank, x, y, tabPolice.checkboxRank);
                break;
            case "BirthDate":
                setPosition(infosPlayerDetails.playerBirthdate, x, y, tabPolice.checkboxBirthDate);
                break;
            case "BirthPlace":
                setPosition(infosPlayerDetails.playerBirthplace, x, y, tabPolice.checkboxBirthPlace);
                break;
            case "Height":
                setPosition(infosPlayerDetails.playerHeight, x, y, tabPolice.checkboxHeight);
                break;
            case "Weight":
                setPosition(infosPlayerDetails.playerWeight, x, y, tabPolice.checkboxHeight);
                break;
            case "Hand":
                setPosition(infosPlayerDetails.playerHand, x, y, tabPolice.checkboxHand);
                break;
            case "Age":
                setPosition(infosPlayerDetails.playerAge, x, y, tabPolice.checkboxAge);
                break;
            case "Prizetotal":
                setPosition(infosPlayerDetails.playerPrizetotal, x, y, tabPolice.checkboxPrizetotal);
                break;
            case "Cityresidence":
                setPosition(infosPlayerDetails.playerCityresidence, x, y, tabPolice.checkboxCityresidence);
                break;
            case "Seeding":
                setPosition(infosPlayerDetails.playerTeteDeSerie, x, y, tabPolice.checkboxTeteDeSerie);
                break;
            case "Line":
                setPosition(infosPlayerDetails.playerLine, x, y, tabPolice.checkboxLine);
                break;
        }
    }

    /**
     * Sets the position.
     *
     * @param component the component
     * @param x the x
     * @param y the y
     * @param checkbox the checkbox
     */
    private void setPosition(JComponent component, int x, int y, JCheckBox checkbox) {
        component.setVisible(checkbox.isSelected());
        component.setLocation(x, y);
        displayFrame.revalidate();
        displayFrame.repaint();
    }

    /**
     * Sets the position img.
     *
     * @param x the x
     * @param y the y
     */
    private void setPositionImg(int x, int y) {
        TabPolice tabPolice = infosPlayerDetails.getPlacementFrameTwoPlayer().getTabPolice();
        int taille = (int) tabPolice.TailleImg.getValue();
        infosPlayerDetails.playerImg.setVisible(tabPolice.checkboxImg.isSelected());
        infosPlayerDetails.tailleImgJoueur = taille;
        JLabel labelImg = new ImageUtility(joueur.getImgJoueur(), taille);
        infosPlayerDetails.playerImg.removeAll();
        infosPlayerDetails.playerImg.add(labelImg);
        infosPlayerDetails.playerImg.setLocation(x, y);
        displayFrame.revalidate();
        displayFrame.repaint();
        infosPlayerDetails.playerImg.setSize(labelImg.getWidth(), labelImg.getHeight());
        displayFrame.revalidate();
        displayFrame.repaint();
    }

    /**
     * Sets the position flag.
     *
     * @param x the x
     * @param y the y
     */
    private void setPositionFlag(int x, int y) {
        TabPolice tabPolice = infosPlayerDetails.getPlacementFrameTwoPlayer().getTabPolice();
        int taille = (int) tabPolice.TailleFlag.getValue();
        infosPlayerDetails.playerFlag.setVisible(tabPolice.checkboxFlag.isSelected());
        infosPlayerDetails.tailleImgFlag = taille;
        JLabel labelFlag = new ImageUtility(infosPlayerDetails.FlagLabel.getImagePath(), taille);
		infosPlayerDetails.playerFlag.removeAll();
		infosPlayerDetails.playerFlag.add(labelFlag);
		infosPlayerDetails.playerFlag.setLocation(x, y);
		displayFrame.revalidate();
		displayFrame.repaint();
		infosPlayerDetails.playerFlag.setSize(labelFlag.getWidth(), labelFlag.getHeight());
		displayFrame.revalidate();
		displayFrame.repaint();
    }

    /**
     * Refresh spinner.
     *
     * @param infosPlayerDetails the infos player details
     */
    public void refreshSpinner(PlayerForDiffusion infosPlayerDetails) {
//        System.out.println("Refresh player spinner : " + infosPlayerDetails.nameLabel.getText() + " current player : " + this.joueur.getDisplay_name());
        for (Map.Entry<String, JSpinner> entry : spinners.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("X")) {
                String element = key.substring(0, key.length() - 1);
                JComponent component = getComponentByName(element, infosPlayerDetails);
                if (component != null) {
                    ((SpinnerNumberModel) spinners.get(element + "X").getModel()).setValue(component.getLocation().x);
                    ((SpinnerNumberModel) spinners.get(element + "Y").getModel()).setValue(component.getLocation().y);
                }
            }
        }
    }

    /**
     * Gets the component by name.
     *
     * @param name the name
     * @param infosPlayerDetails the infos player details
     * @return the component by name
     */
    private JComponent getComponentByName(String name, PlayerForDiffusion infosPlayerDetails) {
        switch (name) {
            case "name": return infosPlayerDetails.playerName;
            case "Surname": return infosPlayerDetails.playerSurname;
            case "Img": return infosPlayerDetails.playerImg;
            case "Flag": return infosPlayerDetails.playerFlag;
            case "Acro": return infosPlayerDetails.playerAcro;
            case "Line": return infosPlayerDetails.playerLine;
            case "Rank": return infosPlayerDetails.playerRank;
            case "BirthDate": return infosPlayerDetails.playerBirthdate;
            case "BirthPlace": return infosPlayerDetails.playerBirthplace;
            case "Height": return infosPlayerDetails.playerHeight;
            case "Weight": return infosPlayerDetails.playerWeight;
            case "Hand": return infosPlayerDetails.playerHand;
            case "Age": return infosPlayerDetails.playerAge;
            case "Prizetotal": return infosPlayerDetails.playerPrizetotal;
            case "Cityresidence": return infosPlayerDetails.playerCityresidence;
            case "Seeding": return infosPlayerDetails.playerTeteDeSerie;
            default: return null;
        }
    }
    
    /**
     * Gets the entete tab.
     *
     * @return the entete tab
     */
    public String getEnteteTab() {
    	return (infosPlayerDetails.getNumeroPlayer()+1)+", "+joueur.getDisplay_name();
    }
    
    /**
     * Gets the infos player details.
     *
     * @return the infos player details
     */
    public PlayerForDiffusion getInfosPlayerDetails() {
        return infosPlayerDetails;
    }
}