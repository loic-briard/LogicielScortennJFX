package GlobalSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlobalSettingsUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GlobalSettingsUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Récupération de l'instance GlobalSettings
        GlobalSettings settings = GlobalSettings.getInstance();

        // Création de la JFrame
        JFrame frame = new JFrame("Global Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des JLabels et JComboBoxes
        JLabel nameLabel = new JLabel("Name Max Length:");
        JComboBox<Integer> nameComboBox = new JComboBox<>(generateOptions(settings.getNameMaxLength()));
        nameComboBox.setSelectedItem(settings.getNameMaxLength());

        JLabel surnameLabel = new JLabel("Surname Max Length:");
        JComboBox<Integer> surnameComboBox = new JComboBox<>(generateOptions(settings.getSurnameMaxLength()));
        surnameComboBox.setSelectedItem(settings.getSurnameMaxLength());

        JLabel cityResidenceLabel = new JLabel("City Residence Max Length:");
        JComboBox<Integer> cityResidenceComboBox = new JComboBox<>(generateOptions(settings.getCityResidenceMaxLength()));
        cityResidenceComboBox.setSelectedItem(settings.getCityResidenceMaxLength());

        JLabel birthPlaceLabel = new JLabel("Birth Place Max Length:");
        JComboBox<Integer> birthPlaceComboBox = new JComboBox<>(generateOptions(settings.getBirthPlaceMaxLength()));
        birthPlaceComboBox.setSelectedItem(settings.getBirthPlaceMaxLength());

        // Ajout des composants à la JFrame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        gbc.gridx = 1;
        frame.add(nameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(surnameLabel, gbc);

        gbc.gridx = 1;
        frame.add(surnameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(cityResidenceLabel, gbc);

        gbc.gridx = 1;
        frame.add(cityResidenceComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(birthPlaceLabel, gbc);

        gbc.gridx = 1;
        frame.add(birthPlaceComboBox, gbc);

        // Bouton pour enregistrer les valeurs
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mise à jour des valeurs dans GlobalSettings
                settings.setNameMaxLength((Integer) nameComboBox.getSelectedItem());
                settings.setSurnameMaxLength((Integer) surnameComboBox.getSelectedItem());
                settings.setCityResidenceMaxLength((Integer) cityResidenceComboBox.getSelectedItem());
                settings.setBirthPlaceMaxLength((Integer) birthPlaceComboBox.getSelectedItem());

                JOptionPane.showMessageDialog(frame, "Settings updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(saveButton, gbc);

        // Rendre la JFrame visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Integer[] generateOptions(int maxLength) {
        Integer[] options = new Integer[maxLength + 1];
        for (int i = 1; i <= maxLength; i++) {
            options[i] = i;
        }
        return options;
    }
}
