package GlobalSettings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Sauvegarde.GlobalSettingSave;

public class GlobalSettings {

    // Instance unique de la classe GlobalSettings
    private static GlobalSettings instance;

    // Variables globales
    private int nameMaxLength;
    private int surnameMaxLength;
    private int cityResidenceMaxLength;
    private int birthPlaceMaxLength;
    private int space;
    private String path =  "resources"+File.separator+"Config";

    // Constructeur privé pour empêcher l'instanciation directe
    private GlobalSettings() {
        // Valeurs par défaut
        this.nameMaxLength = 12;
        this.surnameMaxLength = 12;
        this.cityResidenceMaxLength = 12;
        this.birthPlaceMaxLength = 12;
        this.space = 20;
        
        getGlobalSettingsFromJson();
    }

    GlobalSettingSave saveSettings = new GlobalSettingSave();

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
    
    public int getSpaceLength() {
        return space;
    }
    
    public void setSpaceLength(int space) {
    	this.space = space;
    }
    
    public Map<String, Object> createMapSetting() {
    	Map<String, Object> mapSetting = new HashMap<String, Object>();
    	
    	mapSetting.put("name", getNameMaxLength());
    	mapSetting.put("surname", getSurnameMaxLength());
    	mapSetting.put("residence", getCityResidenceMaxLength());
    	mapSetting.put("birthplace", getBirthPlaceMaxLength());
    	mapSetting.put("space", getSpaceLength());
    	
		return mapSetting;    	
    }
    
    public void saveSettingsToJson() {
    	saveSettings.setMapSetting(createMapSetting());
    	saveSettings.saveSettingToJson(path, "globalSettings.json");
    }
    
    public void getGlobalSettingsFromJson() {
		File configFile = new File(path + File.separator + "globalSettings.json");
		if (configFile.exists()) {
			try (Reader reader = new FileReader(path + File.separator + "globalSettings.json")) {
				JsonElement jsonElement = JsonParser.parseReader(reader);
				if (jsonElement.isJsonObject()) {
					JsonObject configObject = jsonElement.getAsJsonObject();
					setNameMaxLength(configObject.getAsJsonPrimitive("name").getAsInt());
					setSurnameMaxLength(configObject.getAsJsonPrimitive("surname").getAsInt());
					setCityResidenceMaxLength(configObject.getAsJsonPrimitive("residence").getAsInt());
					setBirthPlaceMaxLength(configObject.getAsJsonPrimitive("birthplace").getAsInt());
					setSpaceLength(configObject.getAsJsonPrimitive("space").getAsInt());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			saveSettingsToJson();
		}
	}
    
}
