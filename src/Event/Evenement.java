/*
 * 
 */
package Event;

import java.util.LinkedList;
import java.util.List;

import Background.Background;
import Players.Joueur;

// TODO: Auto-generated Javadoc
/**
 * The Class Evenement.
 */
public class Evenement {
	
	/** The Nom. */
	private String Nom;
	
	/** The Background. */
	private Background Background;
	
	/** The Liste joueur. */
	private List<Joueur> Liste_Joueur;
	
	/**
	 * Instantiates a new evenement.
	 *
	 * @param nom the nom
	 */
	public Evenement(String nom) {
		super();
		this.Nom = nom;
		this.Liste_Joueur = new LinkedList<Joueur>();
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return Nom;
	}

	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		Nom = nom;
	}

	/**
	 * Gets the background.
	 *
	 * @return the background
	 */
	public Background getBackground() {
		return Background;
	}

	/**
	 * Sets the background.
	 *
	 * @param background the new background
	 */
	public void setBackground(Background background) {
		Background = background;
	}

	/**
	 * Gets the liste joueur.
	 *
	 * @return the liste joueur
	 */
	public List<Joueur> getListe_Joueur() {
		return Liste_Joueur;
	}

	/**
	 * Sets the liste joueur.
	 *
	 * @param liste_Joueur the new liste joueur
	 */
	public void setListe_Joueur(List<Joueur> liste_Joueur) {
		Liste_Joueur = liste_Joueur;
	}

}
