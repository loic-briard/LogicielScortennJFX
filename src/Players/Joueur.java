package Players;

public class Joueur {
	int ID;
	String sexe;
	String nom;
	String prenom;
	String display_name;
	String natio_acronyme;
	String main;
	String birthDate;
	String BirthPlace;
	String CityResidence;
	String imgJoueur;
	String prizetotal;
	int rank;
    int taille;
	int age;
	int weight;	
	
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


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getDisplay_name() {
		return display_name;
	}


	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}


	public String getNatio_acronyme() {
		return natio_acronyme;
	}


	public void setNatio_acronyme(String natio_acronyme) {
		this.natio_acronyme = natio_acronyme;
	}


	public String getMain() {
		return main;
	}


	public void setMain(String main) {
		this.main = main;
	}


	public String getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	public String getBirthPlace() {
		return BirthPlace;
	}


	public void setBirthPlace(String birthPlace) {
		BirthPlace = birthPlace;
	}


	public String getCityResidence() {
		return CityResidence;
	}


	public void setCityResidence(String cityResidence) {
		CityResidence = cityResidence;
	}


	public String getImgJoueur() {
		return imgJoueur;
	}


	public void setImgJoueur(String imgJoueur) {
		this.imgJoueur = imgJoueur;
	}


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public int getTaille() {
		return taille;
	}


	public void setTaille(int taille) {
		this.taille = taille;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public String getPrizetotal() {
		return prizetotal;
	}


	public void setPrizetotal(String prizetotal) {
		this.prizetotal = prizetotal;
	}


	@Override
	public String toString() {
		return "Joueur [ID=" + ID + ", " + sexe + ", " + nom + ", " + prenom + ", display name=" + display_name + ", nationalit�=" + natio_acronyme + ", main=" + main
				+ ", birthDate=" + birthDate + ", BirthPlace=" + BirthPlace + ", CityResidence=" + CityResidence + ", imgJoueur=" + imgJoueur + ", prizetotal=" + prizetotal + ", rank=" + rank
				+ ", taille=" + taille + "cm, age=" + age + ", weight=" + weight + "kg]";
	}

}
