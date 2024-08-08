package Main;
/*
 * fenetre d'accueil avec :
 * - modification des evenements
 * - modification des background
 * - modification des joueurs enregistrer
 * - selection de l'evenement
 * - selection de la liste de joueur 
 * - selection du nombre de joueur participant au tournoi
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import Background.ListOfBackgroundFrame;
import Diffusion.WindowTournamentTree;
import Diffusion.WindowBroadcastPublic;
import Event.Evenement;
import Event.ListOfEventsFrame;
import Flags.ListOfFlag;
import Players.Joueur;
import Players.ListOfPlayersFrame;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "Main Menu";
    private static final String ICON_PATH = "icon.png";
    
    private AthleteSelection athleteSelection;
    private JFrame listEventsWindow;
    private JFrame listBGWindow;
    private JFrame listPlayersWindow;
    private JFrame flagsWindow;
    private String actualScreen = "";

    private JComboBox<String> eventComboBox;
    private static JComboBox<String> bddPLayersComboBox;
    private JSpinner spinnerNbJoueur;
    private JSpinner spinnerScreen;
    private JSpinner sizeFenetreX;
    private JSpinner sizeFenetreY;
    private JButton selectAthleteButton;

    public MenuPrincipal() {
        initializeFrame();
        createMenuBar();
        createMainPanel();
        setVisible(true);
        this.pack();
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(TITLE);
        setIconImage(new ImageIcon(ICON_PATH).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    BDD_v2.deconnexionBDD();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createMenuItem("Events", e -> openListEventsWindow()));
        menuBar.add(createMenuItem("Background", e -> openListBGWindow()));
        menuBar.add(createMenuItem("Players", e -> openListPlayersWindow()));
        menuBar.add(createMenuItem("Flags", e -> openListFlagsWindow()));
        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private void openListEventsWindow() {
        if (listEventsWindow == null || !listEventsWindow.isVisible()) {
            try {
                listEventsWindow = new ListOfEventsFrame(this);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            listEventsWindow.toFront();
            listEventsWindow.setState(JFrame.NORMAL);
        }
    }

    private void openListBGWindow() {
        if (listBGWindow == null || !listBGWindow.isVisible()) {
            try {
                listBGWindow = new ListOfBackgroundFrame();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            listBGWindow.toFront();
            listBGWindow.setState(JFrame.NORMAL);
        }
    }

    private void openListPlayersWindow() {
        if (listPlayersWindow == null || !listPlayersWindow.isVisible()) {
            try {
                listPlayersWindow = new ListOfPlayersFrame();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            listPlayersWindow.toFront();
            listPlayersWindow.setState(JFrame.NORMAL);
        }
    }

    private void openListFlagsWindow() {
        if (flagsWindow == null || !flagsWindow.isVisible()) {
            try {
                flagsWindow = new ListOfFlag();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            flagsWindow.toFront();
            flagsWindow.setState(JFrame.NORMAL);
        }
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel panelDimension = createDimensionPanel();
        mainPanel.add(panelDimension, gbc);

        gbc.gridy++;
        JPanel panel = createSelectionPanel();
        mainPanel.add(panel, gbc);

        gbc.gridy++;
        JPanel choixEcran = createScreenSelectionPanel();
        mainPanel.add(choixEcran, gbc);

        add(mainPanel);
    }

    private JPanel createDimensionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.add(new JLabel("window width "));
        sizeFenetreX = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
        panel.add(sizeFenetreX);
        panel.add(new JLabel("window height "));
        sizeFenetreY = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
        panel.add(sizeFenetreY);
        return panel;
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JLabel selectEventLabel = new JLabel("Select an event");
        selectEventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        eventComboBox = new JComboBox<>(BDD_v2.getNamesFromDatabase("event"));
        refreshEventComboBox();
        eventComboBox.setSelectedIndex(1);
        
//        Integer[] allowedValues = {0, 8, 16, 32, 64};
        Integer[] allowedValues = {8, 16, 32, 64};
        SpinnerListModel spinnerInitNbJoueur = new SpinnerListModel(allowedValues);
        spinnerNbJoueur = new JSpinner(spinnerInitNbJoueur);
        
        bddPLayersComboBox = new JComboBox<>(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
        bddPLayersComboBox.setSelectedIndex(2);
        
        selectAthleteButton = new JButton("Athlete select");
        selectAthleteButton.addActionListener(e -> openAthleteSelection());
        
        JButton diffusionButton = new JButton("For diffusion");
        diffusionButton.addActionListener(e -> {
			try {
				handleDiffusionButton();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        panel.add(selectEventLabel);
        panel.add(eventComboBox);
        panel.add(bddPLayersComboBox);
        panel.add(selectAthleteButton);
        panel.add(spinnerNbJoueur);
        panel.add(diffusionButton);
        
        return panel;
    }

    private JPanel createScreenSelectionPanel() {
        JPanel panel = new JPanel();
        JLabel actualDisplayLabel = new JLabel();
        updateActualScreen(actualDisplayLabel);
        
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        SpinnerListModel spinnerInitScreen = new SpinnerListModel(screens);
        spinnerScreen = new JSpinner(spinnerInitScreen);
        if(screens.length > 1) {
            spinnerInitScreen.setValue(screens[1]);
        }
        
        panel.add(actualDisplayLabel);
        panel.add(new JLabel(", choose the screen to display the tournament : "));
        panel.add(spinnerScreen);
        
        return panel;
    }

    private void updateActualScreen(JLabel actualDisplayLabel) {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point location = getLocation();
                GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
                
                for (GraphicsDevice screen : screens) {
                    Rectangle bounds = screen.getDefaultConfiguration().getBounds();
                    if (bounds.contains(location)) {
                        actualScreen = screen.getIDstring();
                        actualDisplayLabel.setText("Actuel screen : " + actualScreen);
                        revalidate();
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    private void openAthleteSelection() {
        try {
            athleteSelection = new AthleteSelection((String) bddPLayersComboBox.getSelectedItem());
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private void handleDiffusionButton() throws ClassNotFoundException, SQLException {
        if (eventComboBox.getSelectedItem() == null) {
            blinkComponentBorder(eventComboBox, 3);
        } else if(bddPLayersComboBox.getSelectedItem() == null) {
            blinkComponentBorder(bddPLayersComboBox, 3);
        } else if(athleteSelection == null) {
        	makeButtonBlink(selectAthleteButton, 3);
        } else if((int) spinnerNbJoueur.getValue() == 0) {
            blinkComponentBorder(spinnerNbJoueur, 3);
        } else {
        	ArrayList<Joueur> selectedPlayers = athleteSelection.getSelectedPlayers();
            displaySelectedPlayers(selectedPlayers);
            createDiffusionWindow(selectedPlayers);
        }
    }

    private void displaySelectedPlayers(ArrayList<Joueur> selectedPlayers) {
        StringBuilder playersInfo = new StringBuilder("Selected players :\n");
        for (Joueur joueur : selectedPlayers) {
            playersInfo.append(joueur.getNom()).append(" ").append(joueur.getPrenom()).append("\n");
        }
        JOptionPane.showMessageDialog(null, playersInfo.toString(), "Selected players", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createDiffusionWindow(ArrayList<Joueur> selectedPlayers) throws ClassNotFoundException, SQLException {
        String selectedEvent = (String) eventComboBox.getSelectedItem();
        Evenement eventChoosen = new Evenement(selectedEvent);
        try {
            eventChoosen = BDD_v2.getEvenementByName(selectedEvent);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        WindowBroadcastPublic diffusionFrame = new WindowBroadcastPublic(eventChoosen, (GraphicsDevice) spinnerScreen.getValue(), new Dimension((int)sizeFenetreX.getValue(), (int)sizeFenetreY.getValue()));
        WindowTournamentTree windowTournamentTree = new WindowTournamentTree(selectedPlayers, eventChoosen, diffusionFrame, (int) spinnerNbJoueur.getValue());
        diffusionFrame.setWindowTournamentTreeFromBroadcast(windowTournamentTree);
        System.out.println("-> Selected event : " + eventChoosen.getNom() + ", player list : " + bddPLayersComboBox.getSelectedItem() + ", number of players : " + (int) spinnerNbJoueur.getValue()+" ,taille fenetre "+diffusionFrame.getSize().getWidth()+"x"+diffusionFrame.getSize().getHeight()+"<-");
    }

    public void refreshEventComboBox() {
        String[] eventNames = BDD_v2.getNamesFromDatabase("event");
        eventComboBox.setModel(new DefaultComboBoxModel<>(eventNames));
        eventComboBox.setSelectedIndex(-1);
    }

    public static void refreshPlayerTableCombobox() throws SQLException {
        BDD_v2.getAllListPlayerTableName();
        bddPLayersComboBox.setModel(new DefaultComboBoxModel<>(BDD_v2.tabBdd.toArray(new String[0])));
    }

	public static void blinkComponentBorder(JComponent component, int blinkCount) {
	    final int[] count = {0};
	    final boolean[] isRed = {false};
	    final Border originalBorder = component.getBorder();
	    if (originalBorder instanceof LineBorder) {
			((LineBorder) originalBorder).getLineColor();
		} else {
			component.getBackground();
		}
	    if (originalBorder instanceof LineBorder) {
			((LineBorder) originalBorder).getThickness();
		}

	    Timer timer = new Timer(250, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (count[0] >= blinkCount * 2) {
	                ((Timer) e.getSource()).stop();
	                component.setBorder(originalBorder);
	                return;
	            }

	            if (isRed[0]) {
	                component.setBorder(originalBorder);
	            } else {
	                component.setBorder(new LineBorder(Color.RED, 2));
	            }

	            isRed[0] = !isRed[0];
	            count[0]++;
	            component.repaint();
	        }
	    });

	    timer.start();
	}
	public void makeButtonBlink(JButton button, int times) {
	    final int[] count = {0};
	    final Border originalBorder = button.getBorder();
	    final Insets originalInsets = originalBorder.getBorderInsets(button);
	    
	    Border redBorder = new CompoundBorder(
	        BorderFactory.createLineBorder(Color.RED, 2),
	        BorderFactory.createEmptyBorder(
	            originalInsets.top - 2,
	            originalInsets.left - 2,
	            originalInsets.bottom - 2,
	            originalInsets.right - 2
	        )
	    );

	    Timer timer = new Timer(250, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (count[0] >= times * 2) {
	                ((Timer)e.getSource()).stop();
	                button.setBorder(originalBorder);
	            } else {
	                if (count[0] % 2 == 0) {
	                    button.setBorder(redBorder);
	                } else {
	                    button.setBorder(originalBorder);
	                }
	                count[0]++;
	            }
	        }
	    });
	    timer.start();
	}
}