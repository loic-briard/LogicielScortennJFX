package Event;

import java.util.LinkedList;
import java.util.List;

import Background.Background;
import Players.Joueur;

public class Evenement {
	private String Nom;
	private Background Background;
	private List<Joueur> Liste_Joueur;
	
	public Evenement(String nom) {
		super();
		this.Nom = nom;
		this.Liste_Joueur = new LinkedList<Joueur>();
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public Background getBackground() {
		return Background;
	}

	public void setBackground(Background background) {
		Background = background;
	}

	public List<Joueur> getListe_Joueur() {
		return Liste_Joueur;
	}

	public void setListe_Joueur(List<Joueur> liste_Joueur) {
		Liste_Joueur = liste_Joueur;
	}

}
