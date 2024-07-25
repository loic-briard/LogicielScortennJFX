package Police;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

public class ColorSelection extends JDialog{
	private static final long serialVersionUID = 1L;
	private Color selectedColor = null;
	
	public ColorSelection(Color initialColor) {
		this.setModal(true);
        this.setLayout(new FlowLayout());
        selectedColor = JColorChooser.showDialog(null, "Choose a color", initialColor);
        if (selectedColor != null) {
            // Faites quelque chose avec la couleur s�lectionn�e
            System.out.println("Couleur selectionnee : " + selectedColor);
            if (selectedColor != null) {
            	initialColor = selectedColor;
            }
        }
	}
}
