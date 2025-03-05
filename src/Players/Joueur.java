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
	
	/** The natio acronyme. */
	String country;
	
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
	
	String teteDeSerie;
	
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
	 * @param teteDeSerie [Q,WC,ALT]
	 */
	public Joueur(int ID, String sexe, String nom, String prenom, String display_name, String nation_acronyme, String country,
			String birthDate, String ImgJoueur, int rank, int taille, String main, int age, int weight, String prizetotal, String BirthPlace, String CityResidence, String teteDeSerie){
		
		super();
		this.ID = ID;
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
		this.display_name = display_name;
		this.natio_acronyme = nation_acronyme;
		this.country = country;
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
		this.teteDeSerie = teteDeSerie;
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
	public String getCountry() {
		return country;
	}
	
	
	/**
	 * Sets the natio acronyme.
	 *
	 * @param natio_acronyme the new natio acronyme
	 */
	public void setCountry(String Country) {
		this.country = Country;
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
		System.out.println("image joueur  : "+imgJoueur);
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


	public String getTeteDeSerie() {
		return teteDeSerie;
	}


	public void setTeteDeSerie(String teteDeSerie) {
		this.teteDeSerie = teteDeSerie;
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Joueur [ID=" + ID + ", " + sexe + ", " + nom + ", " + prenom + ", display name=" + display_name + ", nationalit�=" + natio_acronyme +", "+country+ ", main=" + main
				+ ", birthDate=" + birthDate + ", BirthPlace=" + BirthPlace + ", CityResidence=" + CityResidence + ", imgJoueur=" + imgJoueur + ", prizetotal=" + prizetotal + ", rank=" + rank
				+ ", taille=" + taille + "cm, age=" + age + ", weight=" + weight + "kg, tete de serie = "+teteDeSerie+"]";
	}
	
//	public static String formatNumber(String amount) {
//		// Vérifie que la chaîne contient bien un nombre > 0
//		if (amount.length() > 1) {
//			String currency = amount.substring(amount.length() - 1);
//
//			// Extraire la partie numérique
//			String numericPart = amount.substring(0, amount.length() - 1);
//
//			// Convertir en long
////			numericPart.replace(" ", "");
////			numericPart.replace("?", "");
//			numericPart.replaceAll("[^0-9]", "");
//			System.out.println("prizetoal formated "+numericPart);
//			long number = Long.parseLong(numericPart);
//
//			// Formater avec des espaces comme séparateurs de milliers
//			NumberFormat formatter = NumberFormat.getInstance(Locale.FRANCE);
//			String formattedNumber = formatter.format(number);
//
//			// Retourner la chaîne formatée avec le symbole '€'
//			return formattedNumber + currency;
//		}else
//			return "0";
//	}
//
//    // Méthode pour vérifier si un nombre est correctement formaté
//    public static boolean isFormattedCorrectly(String formattedAmount) {
//        if((formattedAmount.contains("€")||formattedAmount.contains("$")||formattedAmount.contains("£"))&&formattedAmount.contains(" "))
//        	return true;
//        else
//        	return false;
//    }

}
