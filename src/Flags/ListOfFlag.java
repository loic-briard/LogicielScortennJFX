/*
 * 
 */
package Flags;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Main.BDD_v2;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfFlag.
 */
public class ListOfFlag extends JFrame {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The search field. */
    private JTextField searchField;
    
    /** The new button. */
    private JButton newButton;
    /** The modify button. */
    private JButton modifyButton;
    private JButton deleteButton;
	
	/** The search button. */
	private JButton searchButton;
//	private SwingWorker<Void, Object[]> worker;
	
	/** The flag table. */
public JTable flagTable;
    
    /** The table model flag. */
    public CustomTableModelFlag tableModelFlag;
    
    /** The scroll pane flag. */
    private JScrollPane scrollPaneFlag;
    
    /** The table data. */
    Object[][] tableData = null;
	
	/** The Constant IMAGE_HEIGHT. */
	private static final int IMAGE_HEIGHT = 60;

    /**
     * Instantiates a new list of flag.
     *
     * @throws SQLException the SQL exception
     */
    public ListOfFlag(GraphicsDevice configScreen) throws SQLException {
        // Initialisation de la fen�tre
        setTitle("List of Flags");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
        
        ImageIcon logoIcon = new ImageIcon("resources"+File.separator+"imgInterface"+File.separator+"icon.png");
        // V�rifiez si l'ic�ne a �t� charg�e avec succ�s
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            // Si l'ic�ne n'a pas pu �tre charg�e, affichez un message d'erreur
            System.err.println("Impossible de charger l'ic�ne.");
        }

        // Cr�ez un mod�le de table -------------------------------------------------------------------------------------------------------------------------
//        tableModelFlag = new CustomTableModelFlag(BDD_v2.DataFlag());
//        flagTable.setModel(tableModelFlag);
        tableModelFlag = new CustomTableModelFlag(new Object[][]{});
        flagTable = new JTable(tableModelFlag){
			private static final long serialVersionUID = 1L;

			@Override
    	    public boolean getScrollableTracksViewportHeight() {
    	        return getPreferredSize().height < getParent().getHeight();
    	    }
    	};
    	flagTable.setRowHeight(IMAGE_HEIGHT);
        flagTable.setDefaultRenderer(ImageIcon.class, new ImageRendererFlags());
        scrollPaneFlag = new JScrollPane(flagTable);
        tableData = BDD_v2.DataFlag();
        Arrays.sort(tableData, Comparator.comparing(o -> o[0].toString()));
        SwingUtilities.invokeLater(() -> {
            tableModelFlag.setNewData(tableData);
            tableModelFlag.loadImages();
        });
        //------------------------------------------------------------------------------------------------------------------------------------------------
        // Champ de recherche
        searchField = new JTextField(20);
        newButton = new JButton("New");
        // Bouton "Modifier"
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
     // Bouton "Search"
        searchButton = new JButton("Search");
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(newButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        add(panel,BorderLayout.NORTH);

        // Ajout du gestionnaire d'�v�nements pour le bouton "Search"
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim().toUpperCase();
            if (!searchText.isEmpty()) {
                for (int row = 0; row < flagTable.getRowCount(); row++) {
                    String cellValue = flagTable.getValueAt(row, 1).toString(); // Assurez-vous d'adapter le num�ro de colonne
                    if (cellValue.equals(searchText)) {
                        flagTable.scrollRectToVisible(flagTable.getCellRect(row, 1, true));
                        break; // Arr�tez la recherche apr�s avoir trouv� la premi�re correspondance
                    }
                }
            }
        });
        
        newButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        			new NewFlagFrame(configScreen, ListOfFlag.this);
        	}
        });
        modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = flagTable.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtenez les donn�es de la ligne s�lectionn�e
					String flagName = (String) flagTable.getValueAt(selectedRow, 0);
					String flagNameCountry = (String) flagTable.getValueAt(selectedRow, 1);
//					ImageUtility imageUtility = (ImageUtility) flagTable.getValueAt(selectedRow, 1);
					ImageIcon img_flag = (ImageIcon) flagTable.getValueAt(selectedRow, 2);
					String string_flag = img_flag.getDescription();
//					String imgPath = imageUtility.getImagePath();
					// Ouvrir une fen�tre de modification avec ces donn�es
					new ModifyFlagFrame(configScreen, ListOfFlag.this, flagName, flagNameCountry, string_flag,selectedRow);
				}
			}
		});
        deleteButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = flagTable.getSelectedRow();
        		if (selectedRow >= 0) {
        			// Obtenez les donn�es de la ligne s�lectionn�e
        			String flagName = (String) flagTable.getValueAt(selectedRow, 0);
        			// Ouvrir une fen�tre de modification avec ces donn�es
        			try {
						BDD_v2.deleteFlag(flagName);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			tableModelFlag.removeRow(selectedRow);
        		}
        	}
        });

        // Ajout du JTable � la fen�tre
        add(scrollPaneFlag);

        // Rendez la fen�tre visible
        setVisible(true);
    }
//
//    private void loadFlagsInBackground() {
//    	worker = new SwingWorker<Void, Object[]>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                // R�cup�rez les donn�es de la base de donn�es
//                String[][] flagData = BDD_v2.DataFlag();
//
//                // Ajoutez les donn�es � la table apr�s le chargement
//                for (String[] data : flagData) {
//                	if (isCancelled()) {
//                        // Si l'annulation est demand�e, sortez de la boucle
//                        System.out.println("! Chargement annule.");
//                        return null;
//                    }
//                    Object[] rowData = new Object[]{data[0], new ImageUtility(data[1], 55)};
//                    publish(rowData); // Publiez les donn�es pour les afficher dans l'EDT
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void process(java.util.List<Object[]> chunks) {
//                // Ajoutez les donn�es publi�es � la table
//                for (Object[] rowData : chunks) {
//                    DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
//                    model.addRow(rowData);
//                    
//                }
//            }
//        };
//        // D�marrer le SwingWorker
//        worker.execute();
//    }
//    
//    public void refreshModifiedRow(String oldName, String newName, String newImage) throws SQLException {
//        DefaultTableModel model = (DefaultTableModel) flagTable.getModel();
//        for (int row = 0; row < model.getRowCount(); row++) {
//            String name = (String) model.getValueAt(row, 0);
//            if (name.equals(oldName)) {
//                model.setValueAt(newName, row, 0);
//                model.setValueAt(new ImageUtility(newImage, 55), row, 1);
//                break; // Sortez de la boucle une fois que la ligne modifi�e a �t� trouv�e
//            }
//        }
//    }
}