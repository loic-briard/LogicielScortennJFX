package GlobalSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlobalSettingsUI {

    private static JFrame parametersWindow = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GlobalSettingsUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Récupération de l'instance GlobalSettings
        GlobalSettings settings = GlobalSettings.getInstance();

        // Création de la JFrame
        JFrame frame = new JFrame("Global Settings");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        frame.setIconImage(new ImageIcon("icon.png").getImage());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des JLabels et JComboBoxes
        JLabel nameLabel = new JLabel("Name Max Length:");
        JSpinner nameSpinner = new JSpinner();
        nameSpinner.setValue(settings.getNameMaxLength());

        JLabel surnameLabel = new JLabel("Surname Max Length:");
        JSpinner surnameSpinner = new JSpinner();
        surnameSpinner.setValue(settings.getSurnameMaxLength());

        JLabel cityResidenceLabel = new JLabel("City Residence Max Length:");
        JSpinner cityResidenceSpinner = new JSpinner();
        cityResidenceSpinner.setValue(settings.getCityResidenceMaxLength());

        JLabel birthPlaceLabel = new JLabel("Birth Place Max Length:");
        JSpinner birthPlaceSpinner = new JSpinner();
        birthPlaceSpinner.setValue(settings.getBirthPlaceMaxLength());
        
        JLabel spaceLabel = new JLabel("Space Length:");
        JSpinner spaceSpinner = new JSpinner();
        spaceSpinner.setValue(settings.getSpaceLength());

        // Ajout des composants à la JFrame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        gbc.gridx = 1;
        frame.add(nameSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(surnameLabel, gbc);

        gbc.gridx = 1;
        frame.add(surnameSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(cityResidenceLabel, gbc);

        gbc.gridx = 1;
        frame.add(cityResidenceSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(birthPlaceLabel, gbc);

        gbc.gridx = 1;
        frame.add(birthPlaceSpinner, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(spaceLabel, gbc);
        
        gbc.gridx = 1;
        frame.add(spaceSpinner, gbc);

        // Bouton pour enregistrer les valeurs
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mise à jour des valeurs dans GlobalSettings
                settings.setNameMaxLength((Integer) nameSpinner.getValue());
                settings.setSurnameMaxLength((Integer) surnameSpinner.getValue());
                settings.setCityResidenceMaxLength((Integer) cityResidenceSpinner.getValue());
                settings.setBirthPlaceMaxLength((Integer) birthPlaceSpinner.getValue());
                settings.setSpaceLength((Integer) spaceSpinner.getValue());
                
                settings.saveSettingsToJson();

                JOptionPane.showMessageDialog(frame, "Settings updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(saveButton, gbc);

        // Rendre la JFrame visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void openListParametersWindow() {
        if (parametersWindow == null || !parametersWindow.isVisible()) {
            parametersWindow = new JFrame("Global Settings");
            parametersWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            createAndShowGUI();
        } else {
            parametersWindow.toFront();
            parametersWindow.setState(JFrame.NORMAL);
        }
    }
}

