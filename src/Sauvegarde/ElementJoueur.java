package Sauvegarde;

//Classe représentant un élément avec ses propriétés
public class ElementJoueur {
	private int positionX;
	private int positionY;
	
	public void setPositionX(int x) {
		this.positionX = x;		
	}
	public void setPositionY(int y) {
		this.positionY = y;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
}