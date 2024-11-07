/*
 * 
 */
package Police;

import java.awt.Color;
import java.awt.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class chosenPolice.
 */
public class chosenPolice {
	
	/** The newfont. */
	private Font newfont;
	
	/** The new color. */
	private Color newColor;
	
	/**
	 * Instantiates a new chosen police.
	 */
	public chosenPolice() {
		newfont = new Font("Arial", Font.PLAIN, 25);
		newColor = Color.BLACK;	
	}

	/**
	 * Gets the newfont.
	 *
	 * @return the newfont
	 */
	public Font getNewfont() {
		return newfont;
	}

	/**
	 * Sets the newfont.
	 *
	 * @param newfont the new newfont
	 */
	public void setNewfont(Font newfont) {
		this.newfont = newfont;
	}

	/**
	 * Gets the new color.
	 *
	 * @return the new color
	 */
	public Color getNewColor() {
		return newColor;
	}

	/**
	 * Sets the new color.
	 *
	 * @param newColor the new new color
	 */
	public void setNewColor(Color newColor) {
		this.newColor = newColor;
	}
}
