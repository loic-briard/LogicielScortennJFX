package GlobalSettings;

public class GlobalSettings {

    // Instance unique de la classe GlobalSettings
    private static GlobalSettings instance;

    // Variables globales
    private int nameMaxLength;
    private int surnameMaxLength;
    private int cityResidenceMaxLength;
    private int birthPlaceMaxLength;

    // Constructeur privé pour empêcher l'instanciation directe
    private GlobalSettings() {
        // Valeurs par défaut
        this.nameMaxLength = 12;
        this.surnameMaxLength = 12;
        this.cityResidenceMaxLength = 12;
        this.birthPlaceMaxLength = 12;
    }

    // Méthode pour obtenir l'instance unique
    public static GlobalSettings getInstance() {
        if (instance == null) {
            synchronized (GlobalSettings.class) {
                if (instance == null) {
                    instance = new GlobalSettings();
                }
            }
        }
        return instance;
    }

    // Getters et setters pour les variables
    public int getNameMaxLength() {
        return nameMaxLength;
    }

    public void setNameMaxLength(int nameMaxLength) {
        this.nameMaxLength = nameMaxLength;
    }

    public int getSurnameMaxLength() {
        return surnameMaxLength;
    }

    public void setSurnameMaxLength(int surnameMaxLength) {
        this.surnameMaxLength = surnameMaxLength;
    }

    public int getCityResidenceMaxLength() {
        return cityResidenceMaxLength;
    }

    public void setCityResidenceMaxLength(int cityResidenceMaxLength) {
        this.cityResidenceMaxLength = cityResidenceMaxLength;
    }

    public int getBirthPlaceMaxLength() {
        return birthPlaceMaxLength;
    }

    public void setBirthPlaceMaxLength(int birthPlaceMaxLength) {
        this.birthPlaceMaxLength = birthPlaceMaxLength;
    }
}
