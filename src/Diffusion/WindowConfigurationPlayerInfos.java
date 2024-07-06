package Diffusion;

/*
 * fenetre qui comporte les onglet de position de chaque joueur et l'onglet de style
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Police.TabPolice;

public class WindowConfigurationPlayerInfos extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public WindowBroadcastPublic dysplayFrame;
	private String typeFenetre;
	public JTabbedPane tabbedPane = new JTabbedPane();
	private JButton buttonSaveConfig = new JButton("Save config");
	
	public TabConfigurationPlayerInfos tabInfosJ1;
	public TabConfigurationPlayerInfos tabInfosJ2;
	
	public TabPolice tabPolice;
	
	public void setTypeFenetre(String typeFenetre) {
		this.typeFenetre = typeFenetre;
	}
	
	public void setTabPolice(TabPolice tabPolice) {
		this.tabPolice = tabPolice;
		tabbedPane.addTab("Style", tabPolice);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
		confirmAllTab();
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();	
	}
	public TabPolice getTabPolice() {
		return tabPolice;
	}
	
	public void addTabJoueur(TabConfigurationPlayerInfos tabInfos) {
		this.tabbedPane.addTab(tabInfos.getJoueurName(), tabInfos);
		this.tabbedPane.revalidate();
		this.tabbedPane.repaint();
	}
	
	public JTabbedPane getAllTab() {
		return tabbedPane;
	}
	public WindowConfigurationPlayerInfos(WindowBroadcastPublic sonFrame, String typeFrame/*, TabPolice tabPolice*/) {
			this.dysplayFrame = sonFrame;
			this.typeFenetre = typeFrame;
			new JFrame();
			setTitle("Configuration Player Information");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(500, 700);
			ImageIcon logoIcon = new ImageIcon("icon.png");
			// V�rifiez si l'ic�ne a �t� charg�e avec succ�s
			if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
				setIconImage(logoIcon.getImage());
			} else {
				// Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
				System.err.println("Impossible de charger l'ic�ne.");
			}
//			this.tabPolice=tabPolice;
			buttonSaveConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					confirmAllTab();
					refreshAllTab();
					switch (typeFenetre) {
					case "player":
						System.out.println("Save SINGLE");
//						ArrayList<Map<JPanel, JLabel>> joueurDetailsPlayer = new ArrayList<Map<JPanel, JLabel>>();
						tabbedPane.setSelectedIndex(0);
		                Component selectedComponentPlayer = tabbedPane.getSelectedComponent();
				        if (selectedComponentPlayer instanceof TabConfigurationPlayerInfos) {
				            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponentPlayer;
				            PlayerForDiffusion infosPlayerDetails = currentTab.getInfosPlayerDetails();
				            if (infosPlayerDetails != null) {
				            	infosPlayerDetails.enegistrerDetailsJoueurs();
				                ConfigurationSaveLoad.saveWindows(dysplayFrame.getNameEvent(), typeFenetre, infosPlayerDetails.mapJoueurDetails.getMapJoueurDetails(), null);
				            }
				        } else {
				            // Handle the case where the selected component is not of type tabInfosPlayer
				            System.out.println("! Error: Selected component is not an instance of tabInfosPlayer");
				        }
						break;
						
					case "game":
						System.out.println("Save GAME");
						
						ArrayList<Map<JPanel, JLabel>> joueurDetailsGame = new ArrayList<Map<JPanel, JLabel>>();
						for (int i = 0; i < tabbedPane.getTabCount()-1; i++) {
			                tabbedPane.setSelectedIndex(i);
			                Component selectedComponent = tabbedPane.getSelectedComponent();
					        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
					            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
					            PlayerForDiffusion infosGameDetails = currentTab.getInfosPlayerDetails();
					            if (infosGameDetails != null) {
					            	infosGameDetails.enegistrerDetailsJoueurs();
					                joueurDetailsGame.add(infosGameDetails.mapJoueurDetails.getMapJoueurDetails());
					            }
					        } else {
					            // Handle the case where the selected component is not of type tabInfosPlayer
					            System.out.println("! Error: Selected component is not an instance of gameInfosPlayer");
					        }
			            }
						ConfigurationSaveLoad.saveWindowsMultiTab(dysplayFrame.getNameEvent(), typeFenetre, joueurDetailsGame);
						break;
					case "tab":
						System.out.println("Save TAB");
						ArrayList<Map<JPanel, JLabel>> joueurDetails = new ArrayList<Map<JPanel, JLabel>>();
						for (int i = 0; i < tabbedPane.getTabCount()-1; i++) {
			                tabbedPane.setSelectedIndex(i);
			                Component selectedComponent = tabbedPane.getSelectedComponent();
					        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
								TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
					            PlayerForDiffusion infosTabDetails = currentTab.getInfosPlayerDetails();
					            if (infosTabDetails != null) {
					                infosTabDetails.enegistrerDetailsJoueurs();
					                joueurDetails.add(infosTabDetails.mapJoueurDetails.getMapJoueurDetails());
					            }
					        } else {
					            // Handle the case where the selected component is not of type tabInfosPlayer
					            System.out.println("! Error: Selected component is not an instance of tabInfosPlayer");
					        }
			            }
						ConfigurationSaveLoad.saveWindowsMultiTab(dysplayFrame.getNameEvent(), typeFenetre, joueurDetails);
						break;
					case "full":
						System.out.println("Save FULL");
						ArrayList<Map<JPanel, JLabel>> joueurDetailsFull = new ArrayList<Map<JPanel, JLabel>>();						
						for (int i = 0; i < tabbedPane.getTabCount()-1; i++) {
							tabbedPane.setSelectedIndex(i);
							// Check if the selected component is an instance of tabInfosPlayer
					        Component selectedComponent = tabbedPane.getSelectedComponent();
					        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
					            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
					            PlayerForDiffusion infosTabDetails = currentTab.getInfosPlayerDetails();
					            // Ensure that infosTabDetails is not null before using it
					            if (joueurDetailsFull != null) {
					                infosTabDetails.enegistrerDetailsJoueurs();
					                joueurDetailsFull.add(infosTabDetails.mapJoueurDetails.getMapJoueurDetails());
					            }
					        } else {
					            // Handle the case where the selected component is not of type tabInfosPlayer
					            System.out.println("! Error: Selected component is not an instance of tabInfosPlayer");
					        }
						}
						for (int i = 0; i < joueurDetailsFull.size(); i++) {
				            Map<JPanel, JLabel> panelLabelMap = joueurDetailsFull.get(i);
				            System.out.println("Map " + i + ":");
				            for (Map.Entry<JPanel, JLabel> entry : panelLabelMap.entrySet()) {
				                JPanel panel = entry.getKey();
				                JLabel label = entry.getValue();
				                System.out.println("\tJPanel Name: " + panel.getName());
				                System.out.println("\tJLabel Text: " + label.getText());
				            }
				        }
//						ConfigurationSaveLoad.saveWindowsMultiTab(dysplayFrame.getNameEvent(), typeFenetre, joueurDetailsFull);
						break;

					default:
						break;
					}
				}
			});
			
//			this.tabbedPane.add("Style",tabPolice);
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(tabbedPane,BorderLayout.NORTH);
			panel.add(buttonSaveConfig, BorderLayout.SOUTH);
	        this.add(panel);
	        
	        setVisible(true);
		}

		public void refreshAllTab() {
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				tabbedPane.setSelectedIndex(i);
				// Check if the selected component is an instance of tabInfosPlayer
		        Component selectedComponent = tabbedPane.getSelectedComponent();
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
		            currentTab.refreshSpinner(currentTab.getInfosPlayerDetails());
		        }
			}
		}
		public void confirmAllTab() {
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				tabbedPane.setSelectedIndex(i);
				// Check if the selected component is an instance of tabInfosPlayer
		        Component selectedComponent = tabbedPane.getSelectedComponent();
		        if (selectedComponent instanceof TabConfigurationPlayerInfos) {
		            TabConfigurationPlayerInfos currentTab = (TabConfigurationPlayerInfos) selectedComponent;
		            currentTab.confirmTabPlayer();
		        }
			}
		}
}
