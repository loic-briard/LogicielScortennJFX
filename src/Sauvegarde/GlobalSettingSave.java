package Sauvegarde;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GlobalSettingSave {
	/** The map zoom. */
	private Map<String, Object> mapSetting = new HashMap<>();

	public Map<String, Object> getMapSetting() {
		return mapSetting;
	}

	public void setMapSetting(Map<String, Object> mapSetting) {
		this.mapSetting = mapSetting;
	}
	
	public void saveSettingToJson(String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  + Folder " + filePath + " has been created with success");
			} else {
				System.out.println("  ! Folder creation : " + filePath + " failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(mapSetting);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
