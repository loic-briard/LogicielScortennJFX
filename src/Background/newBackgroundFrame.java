package Background;

import javax.swing.*;
import Main.BDD_v2;
import Main.ImageUtility;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

public class newBackgroundFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private String newName;
    private String lastFolder = null;
    private String[] currentImages;
    private JLabel[] imageLabels;
    private JButton[] loadButtons;
    private JPanel imagePreviewPanel, buttonPanel;
    private String[] buttonName = {"Load Full","Load PLayer","Load Game","Load Tab","Load waiting background"};


    public newBackgroundFrame(GraphicsDevice configScreen, ListOfBackgroundFrame parentFrame) {
        setTitle("New Background");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 800);
        setLayout(new BorderLayout());
        Rectangle bounds = configScreen.getDefaultConfiguration().getBounds();
        setLocation(bounds.x + ((configScreen.getDisplayMode().getWidth() - getWidth()) / 2), bounds.y + ((configScreen.getDisplayMode().getHeight() - getHeight()) / 2)); // Positionner la fenêtre
        
        
        // Charger l'icône de la fenêtre
        ImageIcon logoIcon = new ImageIcon("icon.png");
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(logoIcon.getImage());
        } else {
            System.err.println("Impossible de charger l'icône.");
        }
        
        // Initialisation des images par défaut
        currentImages = new String[5];
        imageLabels = new JLabel[5];
        loadButtons = new JButton[5];
        
        for (int i = 0; i < 5; i++) {
            currentImages[i] = "Background/transparent.png";
            imageLabels[i] = new JLabel(new ImageUtility(currentImages[i], 150).getIcon());
            loadButtons[i] = new JButton(buttonName[i]);
            final int index = i;
            loadButtons[i].addActionListener((ActionEvent e) -> loadImage(index));
        }
        
        // Champ de texte pour le nom
        nameField = new JTextField("name");
        add(nameField, BorderLayout.NORTH);
        
        // Bouton de validation
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> validateAndSave(parentFrame));
        add(validateButton, BorderLayout.SOUTH);
        
        // Panel pour les boutons à gauche
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));
        for (JButton button : loadButtons) {
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.WEST);
        
        // Panel pour l'affichage des images au centre
        imagePreviewPanel = new JPanel();
        imagePreviewPanel.setLayout(new GridLayout(5, 1, 5, 5));
        for (JLabel label : imageLabels) {
            imagePreviewPanel.add(label);
        }
        add(imagePreviewPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void loadImage(int index) {
        String newImgPath = ImageUtility.chargerFichier(lastFolder);
        if (newImgPath != null) {
            lastFolder = newImgPath;
            ImageUtility.enregistrerFichier(newImgPath, "Background");
            currentImages[index] = "Background" + File.separator + ImageUtility.getNameFile(newImgPath);
            imageLabels[index].setIcon(new ImageUtility(currentImages[index], 150).getIcon());
            revalidate();
            repaint();
        }
    }
    
    private void validateAndSave(ListOfBackgroundFrame parentFrame) {
        newName = nameField.getText();
        String[] newBackground = {newName, currentImages[0], currentImages[1], currentImages[2], currentImages[3], currentImages[4]};
        BDD_v2.updateBackgroundInDatabase("empty", newBackground);
        dispose();
        try {
            parentFrame.refreshData();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}