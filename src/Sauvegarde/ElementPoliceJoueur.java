/*
 * 
 */
package Sauvegarde;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementPoliceJoueur.
 */
//Classe repr�sentant un �l�ment avec ses propri�t�s
public class ElementPoliceJoueur {
	
	/** The visible. */
	private boolean visible;
	
	/** The font. */
	private String font;
	
	/** The color. */
	private String color;
	
	/** The taille. */
	private int taille;
	
	/**
	 * Sets the taille.
	 *
	 * @param taille the new taille
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}
	
	/**
	 * Sets the visible.
	 *
	 * @param b the new visible
	 */
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	/**
	 * Sets the font.
	 *
	 * @param Font the new font
	 */
	public void setFont(String Font) {
		this.font = Font;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param Color the new color
	 */
	public void setColor(String Color) {
		this.color = Color;
	}
	
	/**
	 * Gets the taille.
	 *
	 * @return the taille
	 */
	public int getTaille() {
		return taille;
	}
	
	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public String getFont() {
		return font;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
}