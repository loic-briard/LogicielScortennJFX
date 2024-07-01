package Police;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class FontSelector extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private Font choosenFont;
	private Color choosenColor;
	private int choosenSize;
	private int choosenStyle;

	public FontSelector(Font _actualFont, Color _actualColor) {
		this.setModal(true);
        this.setLayout(new FlowLayout());
        if(_actualFont == null) 
        	choosenFont = new Font("Mistral", 1, 35);
        else
        	choosenFont = _actualFont;
        JButton colorButton2 = new JButton("Choose a color");
        
       	if(_actualColor != null)
       		choosenColor = _actualColor;
       	else 
       		choosenColor = Color.BLACK;
        colorButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	choosenColor = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            }
        });
        
        int fontTaille = choosenFont.getSize();
        SpinnerNumberModel valeurPolice = new SpinnerNumberModel(fontTaille, 1, 200, 1);
        JSpinner spinnerPolice = new JSpinner(valeurPolice);
        spinnerPolice.addChangeListener(e -> {
        	choosenSize = (int) spinnerPolice.getValue();
            choosenFont.deriveFont((float) choosenSize);
//            panelModif.setSize(textLabel.getPreferredSize());
        });
        
        choosenSize = (int) spinnerPolice.getValue();
        choosenFont.deriveFont((float) choosenSize);
     // Ajout d'une JComboBox pour les styles de police
        String[] styleNames = {"Plain", "Bold", "Italic", "Bold Italic"};
        JComboBox<String> styleComboBox = new JComboBox<>(styleNames);
        styleComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedStyle = (String) styleComboBox.getSelectedItem();
                switch (selectedStyle) {
                    case "Plain":
                        choosenStyle = Font.PLAIN;
                        break;
                    case "Bold":
                        choosenStyle = Font.BOLD;
                        break;
                    case "Italic":
                        choosenStyle = Font.ITALIC;
                        break;
                    case "Bold Italic":
                        choosenStyle = Font.BOLD | Font.ITALIC;
                        break;
                }
                choosenFont = choosenFont.deriveFont(choosenStyle);
            }
        });
        styleComboBox.setSelectedIndex(choosenFont.getStyle());
        choosenFont = choosenFont.deriveFont(choosenStyle);
        //styleComboBox.setSelectedItem(-1);
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selectedFontName = (String) fontComboBox.getSelectedItem();
        		choosenFont = new Font(selectedFontName, Font.PLAIN, (int)spinnerPolice.getValue());
        	}
        });
        fontComboBox.setSelectedItem(choosenFont.getFamily());
        
        this.add(colorButton2);
        this.add(spinnerPolice);
        this.add(fontComboBox);
        this.add(styleComboBox); // Ajout de la JComboBox pour les styles
        this.pack();
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Mettez à jour la police avec les nouvelles valeurs
                choosenFont = choosenFont.deriveFont((float) choosenSize).deriveFont(choosenStyle);
            }
        });
        this.setVisible(true);
	}

	public Font getChoosenFont() {
		return choosenFont;
	}

	public Color getChoosenColor() {
		return choosenColor;
	}

	public int getChoosenSize() {
		return choosenSize;
	}

	public int getChoosenBound() {
		return choosenStyle;
	}
	
	public Font getPoliceComlet() {
		Font policeComplete = new Font(getChoosenFont().getName(), choosenStyle, choosenSize);
		return policeComplete;
	}
}
