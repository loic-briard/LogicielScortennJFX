package Flags;

import javax.swing.*;
import Main.BDD_v2;
import Main.ImageUtility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

/**
 * The Class ModifyFlagFrame.
 */
public class NewFlagFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextField countryField;
    private String newName;
    private String newCountry;
    private JPanel imagePreviewPanel;
    private String currentImage;
    private JButton loadButton;
    private String lastFolder = null;

    /**
     * Instantiates a new modify flag frame.
     *
     * @param parentFrame       the parent frame
     * @param currentName       the current name
     * @param currentNameCountry the current country name
     * @param imgPath           the image path
     * @param selectedRow       the selected row
     */
    public NewFlagFrame(GraphicsDevice configScreen, ListOfFlag parentFrame) {
        loadButton = new JButton("Load");

        setTitle("new Flag : ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
        
        
        ImageIcon logoIcon = new ImageIcon("resources"+File.separator+"imgInterface"+File.separator+"icon.png");
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            System.err.println("Impossible de charger l'icône.");
        }

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        nameField = new JTextField();
        countryField = new JTextField();
        inputPanel.add(new JLabel("Acronym:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Country Name:"));
        inputPanel.add(countryField);
        inputPanel.add(loadButton);
//        inputPanel.add(imagePreviewPanel);
        add(inputPanel, BorderLayout.NORTH);
        
        imagePreviewPanel = new JPanel();
        imagePreviewPanel.setLayout(new BoxLayout(imagePreviewPanel, BoxLayout.Y_AXIS));
        add(imagePreviewPanel, BorderLayout.CENTER);

        JButton validateButton = new JButton("Validate");
        add(validateButton, BorderLayout.SOUTH);


        validateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newName = nameField.getText();
                newCountry = countryField.getText();
                Object newFlagObj[] = {newName, newCountry, currentImage};
                Drapeau newDrapeau = new Drapeau(newName, newCountry, currentImage);
                try {
					BDD_v2.insertionDrapeauDansBDD(newDrapeau);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                dispose();
                CustomTableModelFlag model = (CustomTableModelFlag) parentFrame.flagTable.getModel();
                model.addRow(newFlagObj);
                model.loadImages();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier(lastFolder);
                lastFolder = newImgPath;
                ImageUtility.enregistrerFichier(newImgPath, "resources"+File.separator+"flag");
                currentImage = "flag" + File.separator + ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ chemin vers le drapeau : " + newImgPath);
                updateImagePreviews();
            }
        });
        setVisible(true);
    }

    /**
     * Update image previews.
     */
    private void updateImagePreviews() {
        System.out.println("+++ image actuelle : " + currentImage);
        ImageUtility image = new ImageUtility(currentImage, 150);
        JLabel labelImage1 = new JLabel(image.getIcon());
//        JPanel boxImage1 = new JPanel();
//        boxImage1.setLayout(new BoxLayout(boxImage1, BoxLayout.X_AXIS));
//        boxImage1.add(labelImage1);
//        boxImage1.add(loadButton);
        imagePreviewPanel.removeAll();
        imagePreviewPanel.add(labelImage1);
        revalidate();
    }
}
