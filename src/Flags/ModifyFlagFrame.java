package Flags;

import javax.swing.*;
import Main.BDD_v2;
import Main.ImageUtility;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The Class ModifyFlagFrame.
 */
public class ModifyFlagFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextField countryField;
    private String currentName;
    private String currentNameCountry;
    private String newName;
    private String newCountry;
    private JPanel imagePreviewPanel;
    private String currentImage;
    private JButton loadButton;

    /**
     * Instantiates a new modify flag frame.
     *
     * @param parentFrame       the parent frame
     * @param currentName       the current name
     * @param currentNameCountry the current country name
     * @param imgPath           the image path
     * @param selectedRow       the selected row
     */
    public ModifyFlagFrame(ListOfFlag parentFrame, String currentName, String currentNameCountry, String imgPath, int selectedRow) {
        this.currentName = currentName;
        this.currentNameCountry = currentNameCountry;
        this.currentImage = imgPath;
        loadButton = new JButton("Load");

        setTitle("Modify Flag : " + currentName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        
        ImageIcon logoIcon = new ImageIcon("icon.png");
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            System.err.println("Impossible de charger l'icône.");
        }

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        nameField = new JTextField(this.currentName);
        countryField = new JTextField(this.currentNameCountry);
        inputPanel.add(new JLabel("Acronym:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Country Name:"));
        inputPanel.add(countryField);
        add(inputPanel, BorderLayout.NORTH);

        JButton validateButton = new JButton("Validate");
        add(validateButton, BorderLayout.SOUTH);

        imagePreviewPanel = new JPanel();
        imagePreviewPanel.setLayout(new BoxLayout(imagePreviewPanel, BoxLayout.Y_AXIS));
        updateImagePreviews();
        add(imagePreviewPanel, BorderLayout.CENTER);

        validateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newName = nameField.getText();
                newCountry = countryField.getText();
                Object newFlagObj[] = {newName, newCountry, currentImage};
                Drapeau newDrapeau = new Drapeau(newName, newCountry, currentImage);
                BDD_v2.updateFlagInDatabase(currentName, newDrapeau);
                dispose();
                CustomTableModelFlag model = (CustomTableModelFlag) parentFrame.flagTable.getModel();
                model.updateRow(selectedRow, newFlagObj);
                model.loadImages();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newImgPath = ImageUtility.chargerFichier();
                ImageUtility.enregistrerFichier(newImgPath, "flag");
                currentImage = "flag" + File.separator + ImageUtility.getNameFile(newImgPath);
                System.out.println("++++ chemin vers le drapeau : " + imgPath);
                updateImagePreviews();
            }
        });
        setVisible(true);
    }

    /**
     * Update image previews.
     */
    private void updateImagePreviews() {
        ImageUtility image = new ImageUtility(currentImage, 150);
        JLabel labelImage1 = new JLabel(image.getIcon());
        JPanel boxImage1 = new JPanel();
        boxImage1.setLayout(new BoxLayout(boxImage1, BoxLayout.X_AXIS));
        boxImage1.add(labelImage1);
        boxImage1.add(loadButton);
        imagePreviewPanel.removeAll();
        imagePreviewPanel.add(boxImage1);
        revalidate();
    }
}
