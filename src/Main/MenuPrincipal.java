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
import javax.swing.border.TitledBorder;

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
//        this.pack();
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        // Create a main panel with a gradient background
        JPanel mainPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(50, 53, 53), 0, h, new Color(55, 58, 58));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Add a title panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titlePanel);
        
        // Add the dimension panel
        JPanel dimensionPanel = createStyledDimensionPanel();
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(dimensionPanel);
        
        // Add the selection panel
        JPanel selectionPanel = createStyledSelectionPanel();
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(selectionPanel);
        
        // Add the screen selection panel
        JPanel screenPanel = createStyledScreenSelectionPanel();
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(screenPanel);
        
        mainPanel.add(Box.createVerticalStrut(30));
        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Tournament Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        return panel;
    }

    private JPanel createStyledDimensionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);
        panel.setBorder(createStyledBorder("Window Dimensions"));

        JLabel widthLabel = createStyledLabel("Width:");
        JLabel heightLabel = createStyledLabel("Height:");
        
        sizeFenetreX = createStyledSpinner(new SpinnerNumberModel(0, 0, 10000, 10));
        sizeFenetreY = createStyledSpinner(new SpinnerNumberModel(0, 0, 10000, 10));

        panel.add(widthLabel);
        panel.add(sizeFenetreX);
        panel.add(heightLabel);
        panel.add(sizeFenetreY);

        return panel;
    }

    private JPanel createStyledSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(createStyledBorder("Tournament Setup"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Event Selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createStyledLabel("Event:"), gbc);
        
        gbc.gridx = 1;
        eventComboBox = createStyledComboBox(BDD_v2.getNamesFromDatabase("event"));
        eventComboBox.setSelectedIndex(-1);
        panel.add(eventComboBox, gbc);

        // Player List Selection
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Player List:"), gbc);
        
        gbc.gridx = 1;
        bddPLayersComboBox = createStyledComboBox(BDD_v2.tabBdd.toArray(new String[0]));
        bddPLayersComboBox.setSelectedIndex(-1);
        panel.add(bddPLayersComboBox, gbc);

        // Number of Players
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Players:"), gbc);
        
        gbc.gridx = 1;
        Integer[] allowedValues = {8, 16, 32, 64};
        spinnerNbJoueur = createStyledSpinner(new SpinnerListModel(allowedValues));
        panel.add(spinnerNbJoueur, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        selectAthleteButton = createStyledButton("Select Athletes");
        selectAthleteButton.addActionListener(e -> openAthleteSelection());
        
        JButton diffusionButton = createStyledButton("Start Tournament");
        diffusionButton.addActionListener(e -> {
            try {
                handleDiffusionButton();
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
        });
        
        buttonPanel.add(selectAthleteButton);
        buttonPanel.add(diffusionButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createStyledScreenSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);
        panel.setBorder(createStyledBorder("Display Configuration"));

        Point location = getLocation();
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        String[] str_screens = new String[screens.length];
        JLabel actualDisplayLabel = createStyledLabel("Current Screen: "+actualScreen);
        int i =0;
        for (GraphicsDevice screen : screens) {
            Rectangle bounds = screen.getDefaultConfiguration().getBounds();
            str_screens[i] = screen.getIDstring().substring(screen.getIDstring().length() - 1);
            if (bounds.contains(location)) {
                actualScreen = screen.getIDstring();
                actualDisplayLabel.setText("Current screen : " + actualScreen.substring(actualScreen.length() - 1));
                revalidate();
                repaint();
                //break;
            }
            i++;
        }
        spinnerScreen = createStyledSpinner(new SpinnerListModel(str_screens));
        if(str_screens.length > 1) {
            spinnerScreen.setValue(str_screens[1]);
        }

        panel.add(actualDisplayLabel);
        panel.add(createStyledLabel("Target Screen:"));
        panel.add(spinnerScreen);
        updateActualScreen(actualDisplayLabel);

        return panel;
    }

    // Keeping original functionality for athlete selection and diffusion
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

    // Original blink methods with adapted styling
    public static void blinkComponentBorder(JComponent component, int blinkCount) {
        final int[] count = {0};
        final boolean[] isRed = {false};
        final Border originalBorder = component.getBorder();
        final Color errorColor = new Color(255, 69, 58);

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
                    component.setBorder(BorderFactory.createLineBorder(errorColor, 2));
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
        final Color originalBg = button.getBackground();
        final Color errorColor = new Color(255, 69, 58);

        Timer timer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count[0] >= times * 2) {
                    ((Timer)e.getSource()).stop();
                    button.setBorder(originalBorder);
                    button.setBackground(originalBg);
                } else {
                    if (count[0] % 2 == 0) {
                        button.setBorder(BorderFactory.createLineBorder(errorColor, 2));
                        button.setBackground(errorColor);
                    } else {
                        button.setBorder(originalBorder);
                        button.setBackground(originalBg);
                    }
                    count[0]++;
                }
            }
        });
        timer.start();
    }

    // Helper methods for styling components
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JSpinner createStyledSpinner(SpinnerModel model) {
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 30));
        spinner.getEditor().setBackground(new Color(40, 43, 43));
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setForeground(Color.WHITE);
        spinner.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        return spinner;
    }

    private JComboBox<String> createStyledComboBox(String[] strings) {
        JComboBox<String> comboBox = new JComboBox<String>(strings);
        comboBox.setBackground(new Color(40, 43, 43));
        comboBox.setForeground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(40, 43, 43));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        button.setPreferredSize(new Dimension(150, 35));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(60,63,63));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(40, 43, 43));
                }
            }
        });
        
        return button;
    }

    private Border createStyledBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            title
        );
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titledBorder.setTitleColor(Color.WHITE);
        
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        return BorderFactory.createCompoundBorder(titledBorder, margin);
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
                        actualDisplayLabel.setText("Current screen : " + actualScreen.substring(actualScreen.length() - 1));
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
        	if(bddPLayersComboBox.getSelectedItem() == null) {
                blinkComponentBorder(bddPLayersComboBox, 3);
            } else
            	athleteSelection = new AthleteSelection((String) bddPLayersComboBox.getSelectedItem());
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
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
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        GraphicsDevice selectedScreen = screens[Integer.parseInt((String)spinnerScreen.getValue())];
        WindowBroadcastPublic diffusionFrame = new WindowBroadcastPublic(eventChoosen, selectedScreen, new Dimension((int)sizeFenetreX.getValue(), (int)sizeFenetreY.getValue()));
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
}