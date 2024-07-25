package Police;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FontAndColorSelectionDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	private JPanel panelModif;
    private Color selectedColor = null;
    private Font selectedFont = null;

    public FontAndColorSelectionDialog(JLabel labelToUpdate,JPanel playerInfo, chosenPolice nouvellePolice) {
        this.setModal(true);
        this.setLayout(new FlowLayout());

        this.textLabel = labelToUpdate;
        this.panelModif = playerInfo;
        selectedColor = labelToUpdate.getForeground();
        
        JButton colorButton2 = new JButton("Choose a color");
//		Color selectedColor = Color.black;
        colorButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
                if (selectedColor != null) {
                    // Faites quelque chose avec la couleur s�lectionn�e
                    System.out.println("Couleur selectionnee : " + selectedColor);
                    if (selectedColor != null) {
                        textLabel.setForeground(selectedColor);
                    }
                }
            }
        });
        
        SpinnerNumberModel valeurPolice = new SpinnerNumberModel(textLabel.getFont().getSize(), 1, 200, 1);
        JSpinner spinnerPolice = new JSpinner(valeurPolice);
        spinnerPolice.addChangeListener(e -> {
            int fontSize = (int) spinnerPolice.getValue();
            Font currentFont = textLabel.getFont();
            textLabel.setFont(currentFont.deriveFont((float) fontSize));
            panelModif.setSize(textLabel.getPreferredSize());
        });
        
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFontName = (String) fontComboBox.getSelectedItem();
                selectedFont = new Font(selectedFontName, Font.PLAIN, (int)spinnerPolice.getValue());
                textLabel.setFont(selectedFont);
                panelModif.setSize(textLabel.getPreferredSize());
            }
        });
        fontComboBox.setSelectedItem(textLabel.getFont().getFontName());

//        this.add(fontButton);
        this.add(colorButton2);
        this.add(spinnerPolice);
        this.add(fontComboBox);
        this.pack();
        this.setLocationRelativeTo(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	//textLabel.setFont(selectedFont);
            	textLabel.setFont(new Font(selectedFont.getName(), selectedFont.getStyle(), (int) spinnerPolice.getValue()));
            	panelModif.setSize(textLabel.getPreferredSize());
            	nouvellePolice.setNewColor(selectedColor);
            	nouvellePolice.setNewfont(selectedFont);
            }
        });
        this.setVisible(true);
    }
}

