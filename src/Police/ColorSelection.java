/*
 * 
 */
package Police;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorSelection.
 */
public class ColorSelection extends JDialog{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The selected color. */
	private Color selectedColor = null;
	
	/**
	 * Instantiates a new color selection.
	 *
	 * @param initialColor the initial color
	 */
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
