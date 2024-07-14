package Sauvegarde;

//Classe représentant un élément avec ses propriétés
public class ElementPoliceJoueur {
	private boolean visible;
	private String font;
	private String color;
	private int taille;
	
	public void setTaille(int taille) {
		this.taille = taille;
	}
	public void setVisible(boolean b) {
		this.visible = b;
	}
	public void setFont(String Font) {
		this.font = Font;
	}
	public void setColor(String Color) {
		this.color = Color;
	}
	
	public int getTaille() {
		return taille;
	}
	public boolean isVisible() {
		return visible;
	}
	public String getFont() {
		return font;
	}
	public String getColor() {
		return color;
	}
}