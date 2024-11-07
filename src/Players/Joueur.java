/*
 * 
 */
package Players;

// TODO: Auto-generated Javadoc
/**
 * The Class Joueur.
 */
public class Joueur {
	
	/** The id. */
	int ID;
	
	/** The sexe. */
	String sexe;
	
	/** The nom. */
	String nom;
	
	/** The prenom. */
	String prenom;
	
	/** The display name. */
	String display_name;
	
	/** The natio acronyme. */
	String natio_acronyme;
	
	/** The main. */
	String main;
	
	/** The birth date. */
	String birthDate;
	
	/** The Birth place. */
	String BirthPlace;
	
	/** The City residence. */
	String CityResidence;
	
	/** The img joueur. */
	String imgJoueur;
	
	/** The prizetotal. */
	String prizetotal;
	
	/** The rank. */
	int rank;
    
    /** The taille. */
    int taille;
	
	/** The age. */
	int age;
	
	/** The weight. */
	int weight;	
	
	/**
	 * Instantiates a new joueur.
	 *
	 * @param ID the id
	 * @param sexe the sexe
	 * @param nom the nom
	 * @param prenom the prenom
	 * @param display_name the display name
	 * @param nation_acronyme the nation acronyme
	 * @param birthDate the birth date
	 * @param ImgJoueur the img joueur
	 * @param rank the rank
	 * @param taille the taille
	 * @param main the main
	 * @param age the age
	 * @param weight the weight
	 * @param prizetotal the prizetotal
	 * @param BirthPlace the birth place
	 * @param CityResidence the city residence
	 */
	public Joueur(int ID, String sexe, String nom, String prenom, String display_name, String nation_acronyme, 
			String birthDate, String ImgJoueur, int rank, int taille, String main, int age, int weight, String prizetotal, String BirthPlace, String CityResidence){
		
		super();
		this.ID = ID;
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
		this.display_name = display_name;
		this.natio_acronyme = nation_acronyme;
		this.birthDate = birthDate;
		this.imgJoueur = ImgJoueur;
		this.rank = rank;
		this.taille = taille;
		this.main = main;
		this.age = age;
		this.weight = weight;
		this.prizetotal = prizetotal;
		this.birthDate = birthDate;
		this.CityResidence = CityResidence;
		this.BirthPlace = BirthPlace;
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return ID;
	}


	/**
	 * Sets the id.
	 *
	 * @param iD the new id
	 */
	public void setID(int iD) {
		ID = iD;
	}


	/**
	 * Gets the sexe.
	 *
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}


	/**
	 * Sets the sexe.
	 *
	 * @param sexe the new sexe
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * Gets the prenom.
	 *
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * Sets the prenom.
	 *
	 * @param prenom the new prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplay_name() {
		return display_name;
	}


	/**
	 * Sets the display name.
	 *
	 * @param display_name the new display name
	 */
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}


	/**
	 * Gets the natio acronyme.
	 *
	 * @return the natio acronyme
	 */
	public String getNatio_acronyme() {
		return natio_acronyme;
	}


	/**
	 * Sets the natio acronyme.
	 *
	 * @param natio_acronyme the new natio acronyme
	 */
	public void setNatio_acronyme(String natio_acronyme) {
		this.natio_acronyme = natio_acronyme;
	}


	/**
	 * Gets the main.
	 *
	 * @return the main
	 */
	public String getMain() {
		return main;
	}


	/**
	 * Sets the main.
	 *
	 * @param main the new main
	 */
	public void setMain(String main) {
		this.main = main;
	}


	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public String getBirthDate() {
		return birthDate;
	}


	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	/**
	 * Gets the birth place.
	 *
	 * @return the birth place
	 */
	public String getBirthPlace() {
		return BirthPlace;
	}


	/**
	 * Sets the birth place.
	 *
	 * @param birthPlace the new birth place
	 */
	public void setBirthPlace(String birthPlace) {
		BirthPlace = birthPlace;
	}


	/**
	 * Gets the city residence.
	 *
	 * @return the city residence
	 */
	public String getCityResidence() {
		return CityResidence;
	}


	/**
	 * Sets the city residence.
	 *
	 * @param cityResidence the new city residence
	 */
	public void setCityResidence(String cityResidence) {
		CityResidence = cityResidence;
	}


	/**
	 * Gets the img joueur.
	 *
	 * @return the img joueur
	 */
	public String getImgJoueur() {
		return imgJoueur;
	}


	/**
	 * Sets the img joueur.
	 *
	 * @param imgJoueur the new img joueur
	 */
	public void setImgJoueur(String imgJoueur) {
		this.imgJoueur = imgJoueur;
	}


	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}


	/**
	 * Sets the rank.
	 *
	 * @param rank the new rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
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
	 * Sets the taille.
	 *
	 * @param taille the new taille
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}


	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}


	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}


	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}


	/**
	 * Gets the prizetotal.
	 *
	 * @return the prizetotal
	 */
	public String getPrizetotal() {
		return prizetotal;
	}


	/**
	 * Sets the prizetotal.
	 *
	 * @param prizetotal the new prizetotal
	 */
	public void setPrizetotal(String prizetotal) {
		this.prizetotal = prizetotal;
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Joueur [ID=" + ID + ", " + sexe + ", " + nom + ", " + prenom + ", display name=" + display_name + ", nationalit�=" + natio_acronyme + ", main=" + main
				+ ", birthDate=" + birthDate + ", BirthPlace=" + BirthPlace + ", CityResidence=" + CityResidence + ", imgJoueur=" + imgJoueur + ", prizetotal=" + prizetotal + ", rank=" + rank
				+ ", taille=" + taille + "cm, age=" + age + ", weight=" + weight + "kg]";
	}

}
