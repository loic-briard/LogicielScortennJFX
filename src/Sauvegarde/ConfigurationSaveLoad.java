/*
 * 
 */
package Sauvegarde;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import Diffusion.PanelAnimationConfiguration;

// TODO: Auto-generated Javadoc
class ElementOneJoueur {
	private Map<String, ElementJoueur> player = new HashMap<>();
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();

	public Map<String, ElementJoueur> getOnePlayer() {
		return player;
	}

	public Map<String, ElementPoliceJoueur> getOnePlayerPolice() {
		return playerPolice;
	}
}

class ElementJoueurGame {
	private ArrayList<Map<String, Map<String, ElementJoueur>>> game = new ArrayList<>();
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();

	public ArrayList<Map<String, Map<String, ElementJoueur>>> getPlayer() {
		return game;
	}

	public Map<String, ElementPoliceJoueur> getPlayerPolice() {
		return playerPolice;
	}

	public void setPlayerPolice(Map<String, ElementPoliceJoueur> playerPolice) {
		this.playerPolice = playerPolice;
	}

	public void setPlayer(ArrayList<Map<String, Map<String, ElementJoueur>>> playerList) {
		game = playerList;
	}
}

class ElementJoueurTab {
	private ArrayList<Map<String, Map<String, ElementJoueur>>> tab = new ArrayList<>();
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();

	public ArrayList<Map<String, Map<String, ElementJoueur>>> getPlayer() {
		return tab;
	}

	public Map<String, ElementPoliceJoueur> getPlayerPolice() {
		return playerPolice;
	}

	public void setPlayerPolice(Map<String, ElementPoliceJoueur> playerPolice) {
		this.playerPolice = playerPolice;
	}

	public void setPlayer(ArrayList<Map<String, Map<String, ElementJoueur>>> playerList) {
		tab = playerList;
	}
}

/**
 * The Class ConfigurationSaveLoad.
 */
public class ConfigurationSaveLoad {
	
	/** The locations. */
	private Map<String, Object> locations = new HashMap<>();

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	public Map<String, Object> getLocations() {
		return locations;
	}

	/**
	 * Sets the locations.
	 *
	 * @param map the map
	 */
	public void setLocations(Map<String, Object> map) {
		this.locations = map;
	}

	/**
	 * Save config to file.
	 *
	 * @param data the data
	 * @param filePath the file path
	 * @param fileName the file name
	 */
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFile(ElementOneJoueur data, String filePath, String fileName) {
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
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save config to file game.
	 *
	 * @param elementsTwoPlayer the elements two player
	 * @param filePath the file path
	 * @param fileName the file name
	 */
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileGame(ElementJoueurGame elementsTwoPlayer, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
			} else {
				System.out.println("  ! Folder creation : " + filePath + " failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(elementsTwoPlayer);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save config to file tab.
	 *
	 * @param data the data
	 * @param filePath the file path
	 * @param fileName the file name
	 */
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileTab(ElementJoueurTab data, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
			} else {
				System.out.println("  ! Folder creation : " + filePath + " failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save config to file full.
	 *
	 * @param data the data
	 * @param filePath the file path
	 * @param fileName the file name
	 */
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileFull(ElementJoueurFull data, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
			} else {
				System.out.println("  ! Folder creation : " + filePath + " failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(data);
//			System.out.println(json);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load config from file.
	 *
	 * @param filePath the file path
	 * @return the configuration save load
	 */
	public static ConfigurationSaveLoad loadConfigFromFile(String filePath) {
		Gson gson = new Gson();
		ConfigurationSaveLoad data = null;

		try (Reader reader = new FileReader(filePath)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				// Vérifier si le fichier est vide ou ne contient pas de propriétés
				if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonObject()) {
					System.out.println("! The FILE is EMPTY or is not a JSON file !");
					return null;
				}
				JsonObject configObject = jsonElement.getAsJsonObject();
				// Mappez les propriétés du fichier JSON à votre structure de classe
				// ConfigurationSaveLoad
				data = gson.fromJson(configObject, ConfigurationSaveLoad.class);
				// Reste du code
			} else {
				System.out.println("! The FILE is EMPTY or is not a JSON file !");
				return null;
			}
		} catch (IOException e) {
			System.out.println("! FILE doesn't exist");
		}
		return data;
	}

	/**
	 * Save windows.
	 *
	 * @param nomEvent the nom event
	 * @param typeWindows the type windows
	 * @param JoueurDetailsP1 the joueur details P 1
	 * @param JoueurDetailsP2 the joueur details P 2
	 */
	public static void saveWindows(String nomEvent, String typeWindows, Map<JPanel, JLabel> JoueurDetailsP1,
			Map<JPanel, JLabel> JoueurDetailsP2) {
		ConfigurationSaveLoad eventData = loadConfigFromFile( "resources"+File.separator+"Config/" + nomEvent+ "/player.json");
		if (eventData == null) {
			System.out.println("Content from : " + "Config/" + nomEvent + "/player.json" + " is empty !");
			eventData = new ConfigurationSaveLoad();
		} else {
			System.out.println("Content from : " + "Config/" + nomEvent + "/player.json" + " is full !");
		}

		switch (typeWindows) {
		case "player":
			ElementOneJoueur elementsOnePlayer = new ElementOneJoueur();
			for (Map.Entry<JPanel, JLabel> entry : JoueurDetailsP1.entrySet()) {
				JPanel panel = entry.getKey();
				JLabel label = entry.getValue();
				ElementJoueur playerElement = new ElementJoueur();
				ElementPoliceJoueur playerPolice = new ElementPoliceJoueur();
				playerElement.setPositionX(panel.getX());
				playerElement.setPositionY(panel.getY());
				playerPolice.setVisible(panel.isVisible());
				if (panel.getName().equals("ImgJoueur") || panel.getName().equals("ImgFlag")) {
					 System.out.println("taille des images : " + panel.getName() + panel.getHeight());
					playerPolice.setTaille((int) panel.getHeight());
				}
				if (panel.getComponents()[0] instanceof JLabel)
					label = (JLabel) panel.getComponents()[0];
				playerPolice.setFont(FontSerializer(label.getFont()));
				playerPolice.setColor(ColorSerializer(label.getForeground()));
				elementsOnePlayer.getOnePlayer().put(panel.getName(), playerElement);
				elementsOnePlayer.getOnePlayerPolice().put(panel.getName(), playerPolice);
			}
			System.out.println("PLAYER save completed");
			saveConfigToFile(elementsOnePlayer, "resources"+File.separator+"Config/" + nomEvent, "player.json");
			break;

		default:
			System.out.println("! ERROR windows name !");
			break;
		}
	}

	/**
	 * Save windows multi tab.
	 *
	 * @param nomEvent the nom event
	 * @param typeWindows the type windows
	 * @param JoueurDetails the joueur details
	 */
	public static void saveWindowsMultiTab(String nomEvent, String typeWindows,	ArrayList<Map<JPanel, JLabel>> JoueurDetails) {
		ConfigurationSaveLoad eventData = loadConfigFromFile( "resources"+File.separator+"Config/" + nomEvent +File.separator+ typeWindows + ".json");
		if (eventData == null) {
			System.out.println("Content from : " + "Config/" + nomEvent + "/" + typeWindows + ".json" + " is empty !");
			eventData = new ConfigurationSaveLoad();
		} else {
			System.out.println("Content from : " + "Config/" + nomEvent + "/" + typeWindows + ".json" + " is full !");
		}

		switch (typeWindows) {
		case "game":
			int k = 0;
			ElementJoueurGame elementsGamePlayer = new ElementJoueurGame();

			for (Map<JPanel, JLabel> map : JoueurDetails) {

				Map<String, Map<String, ElementJoueur>> playerList = new HashMap<>();
				Map<String, ElementJoueur> player = new HashMap<String, ElementJoueur>();

				for (Map.Entry<JPanel, JLabel> entry : map.entrySet()) {
					JPanel panel = entry.getKey();
					JLabel label = entry.getValue();

					ElementJoueur playerElement = new ElementJoueur();
					ElementPoliceJoueur playerPolice = new ElementPoliceJoueur();

					playerElement.setPositionX(panel.getX());
					playerElement.setPositionY(panel.getY());

					playerPolice.setVisible(panel.isVisible());
					if (panel.getName().equals("ImgJoueur") || panel.getName().equals("ImgFlag")) {
						// System.out.println("taille : " + panel.getName() + panel.getHeight());
						playerPolice.setTaille((int) panel.getHeight());
					}
					if (panel.getComponents()[0] instanceof JLabel)
						label = (JLabel) panel.getComponents()[0];
					playerPolice.setFont(FontSerializer(label.getFont()));
					playerPolice.setColor(ColorSerializer(label.getForeground()));

					player.put(panel.getName(), playerElement);
					elementsGamePlayer.getPlayerPolice().put(panel.getName(), playerPolice);
				}
				playerList.put("player" + k, player);
				elementsGamePlayer.getPlayer().add(playerList);
				k++;
			}
			System.out.println("GAME save completed");
			saveConfigToFileGame(elementsGamePlayer, "resources"+File.separator+"Config/" + nomEvent, "game.json");
			break;
		case "tab":
			int i = 0;
			ElementJoueurTab elementsTabPlayer = new ElementJoueurTab();

			for (Map<JPanel, JLabel> map : JoueurDetails) {

				Map<String, Map<String, ElementJoueur>> playerList = new HashMap<>();
				Map<String, ElementJoueur> player = new HashMap<String, ElementJoueur>();

				for (Map.Entry<JPanel, JLabel> entry : map.entrySet()) {
					JPanel panel = entry.getKey();
					JLabel label = entry.getValue();

					ElementJoueur playerElement = new ElementJoueur();
					ElementPoliceJoueur playerPolice = new ElementPoliceJoueur();

					playerElement.setPositionX(panel.getX());
					playerElement.setPositionY(panel.getY());

					playerPolice.setVisible(panel.isVisible());
					if (panel.getName().equals("ImgJoueur") || panel.getName().equals("ImgFlag")) {
						// System.out.println("taille : " + panel.getName() + panel.getHeight());
						playerPolice.setTaille((int) panel.getHeight());
					}
					if (panel.getComponents()[0] instanceof JLabel)
						label = (JLabel) panel.getComponents()[0];

					playerPolice.setFont(FontSerializer(label.getFont()));
					playerPolice.setColor(ColorSerializer(label.getForeground()));

					player.put(panel.getName(), playerElement);
					elementsTabPlayer.getPlayerPolice().put(panel.getName(), playerPolice);
				}
				playerList.put("player" + i, player);
				elementsTabPlayer.getPlayer().add(playerList);
				i++;
			}
			System.out.println("TAB save completed");
			saveConfigToFileTab(elementsTabPlayer, "resources"+File.separator+"Config/" + nomEvent, "tab.json");
			break;
		case "full":
			int j = 0;
			ElementJoueurFull elementsFullPlayer = new ElementJoueurFull();

			for (Map<JPanel, JLabel> map : JoueurDetails) {

				Map<String, Map<String, ElementJoueur>> playerList = new HashMap<>();
				Map<String, ElementJoueur> player = new HashMap<String, ElementJoueur>();

				for (Map.Entry<JPanel, JLabel> entry : map.entrySet()) {

					JPanel panel = entry.getKey();
					JLabel label = entry.getValue();
					ElementJoueur playerElement = new ElementJoueur();
					ElementPoliceJoueur playerPoliceFull = new ElementPoliceJoueur();

					playerElement.setPositionX(panel.getX());
					playerElement.setPositionY(panel.getY());

					playerPoliceFull.setVisible(panel.isVisible());
//					System.out.println("taille de " + panel.getName() + ", panel : "+panel.getHeight()+", label : "+panel.getComponents()[0].getHeight());
					if (panel.getName().equals("ImgJoueur") || panel.getName().equals("ImgFlag")) {
						playerPoliceFull.setTaille((int) panel.getHeight());
					}
					if (panel.getComponents()[0] instanceof JLabel)
						label = (JLabel) panel.getComponents()[0];
					playerPoliceFull.setFont(FontSerializer(label.getFont()));
					playerPoliceFull.setColor(ColorSerializer(label.getForeground()));

					player.put(panel.getName(), playerElement);
					elementsFullPlayer.getPlayerPolice().put(panel.getName(), playerPoliceFull);
				}
				playerList.put("player" + j, player);
				elementsFullPlayer.getPlayer().add(playerList);
				j++;
			}

			break;

		default:
			System.out.println("! ERROR window name !");
			break;
		}
	}

	/**
	 * Read json file full.
	 *
	 * @param filePath the file path
	 * @return the element joueur full
	 */
	public static ElementJoueurFull readJsonFileFull(String filePath) {
		ElementJoueurFull elementJoueurFull = new ElementJoueurFull();
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(filePath)) {
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

			// Parse "full" array
			JsonArray fullArray = jsonObject.getAsJsonArray("full");
			for (JsonElement element : fullArray) {
				JsonObject playerObject = element.getAsJsonObject();
				String playerKey = playerObject.keySet().iterator().next();
				JsonObject playerData = playerObject.getAsJsonObject(playerKey);

				Map<String, Map<String, ElementJoueur>> playerMap = new HashMap<>();
				Map<String, ElementJoueur> innerMap = new HashMap<>();

				for (Map.Entry<String, JsonElement> entry : playerData.entrySet()) {
					ElementJoueur elementJoueur = new ElementJoueur();
					JsonObject positionObject = entry.getValue().getAsJsonObject();
					elementJoueur.setPositionX(positionObject.get("positionX").getAsInt());
					elementJoueur.setPositionY(positionObject.get("positionY").getAsInt());
					innerMap.put(entry.getKey(), elementJoueur);
				}

				playerMap.put(playerKey, innerMap);
				elementJoueurFull.getPlayer().add(playerMap);
			}

			// Parse "playerPolice"
			JsonObject playerPoliceObject = jsonObject.getAsJsonObject("playerPolice");
			Map<String, ElementPoliceJoueur> playerPoliceMap = new HashMap<>();

			for (Map.Entry<String, JsonElement> entry : playerPoliceObject.entrySet()) {
				ElementPoliceJoueur elementPoliceJoueur = new ElementPoliceJoueur();
				JsonObject policeData = entry.getValue().getAsJsonObject();

				elementPoliceJoueur.setVisible(policeData.get("visible").getAsBoolean());
				elementPoliceJoueur.setFont(policeData.get("font").getAsString());
				elementPoliceJoueur.setColor(policeData.get("color").getAsString());
				elementPoliceJoueur.setTaille(policeData.get("taille").getAsInt());

				playerPoliceMap.put(entry.getKey(), elementPoliceJoueur);
			}

			elementJoueurFull.getPlayerPolice().putAll(playerPoliceMap);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return elementJoueurFull;
	}

	/**
	 * Update element joueur full.
	 *
	 * @param nomEvent the nom event
	 * @param original the original
	 * @param partial the partial
	 */
	public static void updateElementJoueurFull(String nomEvent, ElementJoueurFull original, ElementJoueurFull partial) {
		// Update players
		for (Map<String, Map<String, ElementJoueur>> partialPlayerMap : partial.getPlayer()) {
			String playerKey = partialPlayerMap.keySet().iterator().next();
			Map<String, ElementJoueur> partialInnerMap = partialPlayerMap.get(playerKey);

			// Find the corresponding player in the original ElementJoueurFull
			Map<String, Map<String, ElementJoueur>> originalPlayerMap = original.getPlayer().stream()
					.filter(map -> map.containsKey(playerKey)).findFirst().orElse(null);

			if (originalPlayerMap == null) {
				// If the player doesn't exist in the original, add it
				original.getPlayer().add(partialPlayerMap);
			} else {
				// If the player exists, update its data
				Map<String, ElementJoueur> originalInnerMap = originalPlayerMap.get(playerKey);
				for (Map.Entry<String, ElementJoueur> entry : partialInnerMap.entrySet()) {
					originalInnerMap.put(entry.getKey(), entry.getValue());
				}
			}
		}

		// Update playerPolice
		for (Map.Entry<String, ElementPoliceJoueur> entry : partial.getPlayerPolice().entrySet()) {
			original.getPlayerPolice().put(entry.getKey(), entry.getValue());
		}
//        //affichage du elementJoueurFull to save
//        System.out.println("--Elements in full from to save --:");
//	    for (Map<String, Map<String, ElementJoueur>> playerMap : original.getPlayer()) {
//	        for (Map.Entry<String, Map<String, ElementJoueur>> playerEntry : playerMap.entrySet()) {
//	            System.out.println("Player: " + playerEntry.getKey());
//	            for (Map.Entry<String, ElementJoueur> elementEntry : playerEntry.getValue().entrySet()) {
//	                ElementJoueur element = elementEntry.getValue();
//	                System.out.println("  Element: " + elementEntry.getKey() + " PositionX: " + element.getPositionX() + " PositionY: " + element.getPositionY());
//	            }
//	        }
//	    }
//
//	    System.out.println("Elements Police in full");
//	    for (Map.Entry<String, ElementPoliceJoueur> entry : original.getPlayerPolice().entrySet()) {
//	        ElementPoliceJoueur police = entry.getValue();
//	        System.out.println("Element: " + entry.getKey() + " Visible: " + police.isVisible() + " Font: " + police.getFont() + " Color: " + police.getColor() + " Taille: " + police.getTaille());
//	    }

		saveConfigToFileFull(original, "resources"+File.separator+"Config/" + nomEvent, "full.json");
		System.out.println("FULL save completed");

	}

	/**
	 * Replace player data full.
	 *
	 * @param nomEvent the nom event
	 * @param source the source
	 */
	public static void replacePlayerDataFull(String nomEvent, ElementJoueurFull source) {
		ElementJoueurFull target = readJsonFileFull( "resources"+File.separator+"Config/" + nomEvent + "/" + "full.json");
//
//	    System.out.println("--Elements in full from json:");
//	    for (Map<String, Map<String, ElementJoueur>> playerMap : target.getPlayer()) {
//	        for (Map.Entry<String, Map<String, ElementJoueur>> playerEntry : playerMap.entrySet()) {
//	            System.out.println("Player: " + playerEntry.getKey());
//	            for (Map.Entry<String, ElementJoueur> elementEntry : playerEntry.getValue().entrySet()) {
//	                ElementJoueur element = elementEntry.getValue();
//	                System.out.println("  Element: " + elementEntry.getKey() + " PositionX: " + element.getPositionX() + " PositionY: " + element.getPositionY());
//	            }
//	        }
//	    }
//	    System.out.println("Elements Police in full");
//	    for (Map.Entry<String, ElementPoliceJoueur> entry : target.getPlayerPolice().entrySet()) {
//	        ElementPoliceJoueur police = entry.getValue();
//	        System.out.println("Element: " + entry.getKey() + " Visible: " + police.isVisible() + " Font: " + police.getFont() + " Color: " + police.getColor() + " Taille: " + police.getTaille());
//	    }

		// Replace "full" part
		ArrayList<Map<String, Map<String, ElementJoueur>>> targetFull = target.getPlayer();
		ArrayList<Map<String, Map<String, ElementJoueur>>> sourceFull = source.getPlayer();

		// Using a LinkedHashMap to preserve insertion order
		LinkedHashMap<String, Map<String, ElementJoueur>> targetMap = new LinkedHashMap<>();
		for (Map<String, Map<String, ElementJoueur>> playerMap : targetFull) {
			targetMap.putAll(playerMap);
		}

		for (Map<String, Map<String, ElementJoueur>> sourcePlayerMap : sourceFull) {
			for (String playerKey : sourcePlayerMap.keySet()) {
				targetMap.put(playerKey, sourcePlayerMap.get(playerKey));
			}
		}

		// Convert back to ArrayList for target
		targetFull.clear();
		targetFull.add(new LinkedHashMap<>(targetMap));

		// Replace "playerPolice" part
		Map<String, ElementPoliceJoueur> targetPlayerPolice = target.getPlayerPolice();
		Map<String, ElementPoliceJoueur> sourcePlayerPolice = source.getPlayerPolice();

		for (String key : sourcePlayerPolice.keySet()) {
			targetPlayerPolice.put(key, sourcePlayerPolice.get(key));
		}

//	    System.out.println("--Elements in full from to save --:");
//	    for (Map<String, Map<String, ElementJoueur>> playerMap : target.getPlayer()) {
//	        for (Map.Entry<String, Map<String, ElementJoueur>> playerEntry : playerMap.entrySet()) {
//	            System.out.println("Player: " + playerEntry.getKey());
//	            for (Map.Entry<String, ElementJoueur> elementEntry : playerEntry.getValue().entrySet()) {
//	                ElementJoueur element = elementEntry.getValue();
//	                System.out.println("  Element: " + elementEntry.getKey() + " PositionX: " + element.getPositionX() + " PositionY: " + element.getPositionY());
//	            }
//	        }
//	    }
//
//	    System.out.println("Elements Police in full");
//	    for (Map.Entry<String, ElementPoliceJoueur> entry : target.getPlayerPolice().entrySet()) {
//	        ElementPoliceJoueur police = entry.getValue();
//	        System.out.println("Element: " + entry.getKey() + " Visible: " + police.isVisible() + " Font: " + police.getFont() + " Color: " + police.getColor() + " Taille: " + police.getTaille());
//	    }

		saveConfigToFileFull(target, "resources"+File.separator+"Config/" + nomEvent, "full.json");
		System.out.println("FULL save completed");
	}

	/**
	 * retourne la position d'un element d'un joueur (ex: position du label name) en
	 * fonction du type d'evenement, de fenetre, le NOM DE L'ELEMENT, et l'INDEX du
	 * joueur.
	 *
	 * @param emplacement the emplacement
	 * @param eventName   Le nom de l'événement.
	 * @param typeFenetre Le type de fenêtre (par exemple, "full").
	 * @param nomElement  l'element a recuperer (ex: name)
	 * @param index       index du joueur a recuperer dans le fichier json
	 * @return ElementJoueur qui donne le X et Y de l'element recherche
	 */
	public ElementJoueur getElement(String emplacement, String eventName, String typeFenetre, String nomElement,
			int index) {
		try (Reader reader = new FileReader(emplacement)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonObject elementObject = new JsonObject();// configObject.getAsJsonObject(typeFenetre).getAsJsonObject(nomElement);
				System.out.println(typeFenetre + " GET element : " + nomElement + " for player index : " + index
						+ " from file : " + emplacement);
				switch (typeFenetre) {
				case "player":
					elementObject = configObject.getAsJsonObject("player").getAsJsonObject(nomElement);
					break;
				case "game":
//					elementObject = configObject.getAsJsonObject(Player).getAsJsonObject(nomElement);
					if (configObject.has(typeFenetre) && configObject.get(typeFenetre).isJsonArray()) {
						JsonArray tabArray = configObject.getAsJsonArray(typeFenetre);
						// Vérifier si l'index spécifié est valide
						if (index > tabArray.size() - 1) {
							System.out.println("! Player index superior to the number of elements in the " + typeFenetre
									+ ".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {
							JsonObject playerObject = tabArray.get(index).getAsJsonObject();

							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index)
									&& playerObject.get("player" + index).isJsonObject()) {
								JsonObject player = playerObject.getAsJsonObject("player" + index);
								// Vérifier si "Prizetotal" existe
								if (player.has(nomElement) && player.get(nomElement).isJsonObject()) {
									elementObject = player.getAsJsonObject(nomElement);
								}
							}
						}
					}
					break;
				case "tab":
					if (configObject.has(typeFenetre) && configObject.get(typeFenetre).isJsonArray()) {
						JsonArray tabArray = configObject.getAsJsonArray(typeFenetre);
						// Vérifier si l'index spécifié est valide
						if (index > tabArray.size() - 1) {
							System.out.println("! Player index superior to the number of elements in the " + typeFenetre
									+ ".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {
							JsonObject playerObject = tabArray.get(index).getAsJsonObject();

							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index)
									&& playerObject.get("player" + index).isJsonObject()) {
								JsonObject player = playerObject.getAsJsonObject("player" + index);
								// Vérifier si "Prizetotal" existe
								if (player.has(nomElement) && player.get(nomElement).isJsonObject()) {
									elementObject = player.getAsJsonObject(nomElement);
								}
							}
						}
					}
					break;
				case "full":
					if (configObject.has(typeFenetre) && configObject.get(typeFenetre).isJsonArray()) {
						JsonArray tabArray = configObject.getAsJsonArray(typeFenetre);
						// Vérifier si l'index spécifié est valide index = numero du joueur enregistrer
						if (index > tabArray.size() - 1) {
							System.out.println("! Player index superior to the number of elements in the " + typeFenetre
									+ ".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {

							JsonObject playerObject = tabArray.get(index).getAsJsonObject();

							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index)
									&& playerObject.get("player" + index).isJsonObject()) {
								JsonObject player = playerObject.getAsJsonObject("player" + index);
								// Vérifier si "Prizetotal" existe
								if (player.has(nomElement) && player.get(nomElement).isJsonObject()) {
									elementObject = player.getAsJsonObject(nomElement);
								}
							}
						}
					}
					break;

				default:
					break;
				}

				ElementJoueur element = new ElementJoueur();
				if (elementObject != null) {
					// Set the properties based on the retrieved JSON data
					element.setPositionX(elementObject.getAsJsonPrimitive("positionX").getAsInt());
					element.setPositionY(elementObject.getAsJsonPrimitive("positionY").getAsInt());
					// Return the ElementJoueur instance
					return element;
				} else {
					System.out.println("! PLAYER.json is empty !");
					return null;
				}
			} else {
				System.out.println("! FILE empty or not JSON !");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets the element police.
	 *
	 * @param emplacement the emplacement
	 * @param eventName the event name
	 * @param typeFenetre the type fenetre
	 * @param nomElement the nom element
	 * @param index the index
	 * @return the element police
	 */
	public ElementPoliceJoueur getElementPolice(String emplacement, String eventName, String typeFenetre,
			String nomElement, int index) {

		try (Reader reader = new FileReader(emplacement)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonObject elementObject = new JsonObject();
				switch (typeFenetre) {
				case "player":
					// System.out.println("recup player");
					elementObject = configObject.getAsJsonObject("playerPolice").getAsJsonObject(nomElement);
					break;
				case "game":
					// System.out.println("recup game");
					elementObject = configObject.getAsJsonObject("playerPolice").getAsJsonObject(nomElement);
					break;
				case "tab":
					elementObject = configObject.getAsJsonObject("playerPolice").getAsJsonObject(nomElement);
					break;
				case "full":
					elementObject = configObject.getAsJsonObject("playerPolice").getAsJsonObject(nomElement);
					break;

				default:
					break;
				}

				ElementPoliceJoueur elementPolice = new ElementPoliceJoueur();
				if (elementObject != null) {
					elementPolice.setVisible(elementObject.getAsJsonPrimitive("visible").getAsBoolean());
					elementPolice.setFont(elementObject.getAsJsonPrimitive("font").getAsString());
					elementPolice.setColor(elementObject.getAsJsonPrimitive("color").getAsString());
					if (nomElement.equals("ImgJoueur") || nomElement.equals("ImgFlag")) {
						elementPolice.setTaille(elementObject.getAsJsonPrimitive("taille").getAsInt());
					}
					return elementPolice;
				} else {
					System.out.println("! POLICE is empty in file : " + emplacement);
					return null;
				}
			} else {
				System.out.println("! FILE is doesn't exist or not JSON !");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets the all element visible.
	 *
	 * @param nbPlayer the nb player
	 * @param filePath the file path
	 * @return the all element visible
	 * @throws JsonIOException the json IO exception
	 * @throws JsonSyntaxException the json syntax exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Map<String, Map<String, Object>> getAllElementVisible(int nbPlayer, String filePath)
			throws JsonIOException, JsonSyntaxException, IOException {
		Map<String, Map<String, Object>> playerData = new HashMap<>();
		try (Reader reader = new FileReader(filePath)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonArray playersArray = configObject.getAsJsonArray("full");

				JsonObject playerPolice = configObject.getAsJsonObject("playerPolice");

				// System.out.println();
				for (int i = 0; i < nbPlayer; i++) {
					// System.out.println("Get element visible for player " + i);
					JsonObject playerObject = playersArray.get(i).getAsJsonObject().getAsJsonObject("player" + i);
					Map<String, Object> elementsData = new HashMap<>();

					for (String key : playerObject.keySet()) {
						boolean isVisible = playerPolice.getAsJsonObject(key).get("visible").getAsBoolean();

						if (isVisible) {
							int positionX = playerObject.getAsJsonObject(key).getAsJsonPrimitive("positionX")
									.getAsInt();
							int positionY = playerObject.getAsJsonObject(key).getAsJsonPrimitive("positionY")
									.getAsInt();
							String font = playerPolice.getAsJsonObject(key).get("font").getAsString();
							String color = playerPolice.getAsJsonObject(key).get("color").getAsString();

							Map<String, Object> elementData = new HashMap<>();
							elementData.put("position", new Point(positionX, positionY));
							elementData.put("font", font);
							elementData.put("color", parseColor(color));
							elementData.put("taille", playerPolice.getAsJsonObject(key).get("taille").getAsInt());

							elementsData.put(key, elementData);
						}
					}
					playerData.put("player" + i, elementsData);
				}
			}
		}
		return playerData;
	}

	/**
	 * Inits the json.
	 *
	 * @param nbJoueur the nb joueur
	 * @param nomEvent the nom event
	 */
	public static void initJson(int nbJoueur, String nomEvent) {
		try {
			ArrayList<String> listFileName = new ArrayList<String>();
			listFileName.add("player.json");
			listFileName.add("game.json");
			listFileName.add("tab.json");
			listFileName.add("full.json");
			listFileName.add("animation.json");
			Path targetPath = Paths.get("resources"+File.separator+"Config" + File.separator + nomEvent);
			// Créer le dossier destination s'il n'existe pas
			if (!Files.exists(targetPath)) {
				Files.createDirectories(targetPath);
				System.out.println("Dossier destination créé : " + targetPath);
				for (String fileName : listFileName) {

					Path sourcePath = Paths.get("resources"+File.separator+"Config" + File.separator + "default" +File.separator + nbJoueur, fileName);
					// Construire le chemin complet du fichier destination
					Path destinationFile = targetPath.resolve(sourcePath.getFileName());

					// Copier le fichier
					Files.copy(sourcePath, destinationFile, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(sourcePath+" Fichier copié avec succès : " + destinationFile);
				}
//				ElementJoueurFull defaultData = createDefaultElementJoueurFull(nbJoueur);
//				saveConfigToFileFull(defaultData, "config" + File.separator + nomEvent, "full.json");
			}
			

		} catch (IOException e) {
			System.err.println("Erreur lors de la copie du fichier : " + e.getMessage());
		}
	}

//	/**
//	 * Creates the default element joueur full.
//	 *
//	 * @param nbJoueur the nb joueur
//	 * @return the element joueur full
//	 */
//	private static ElementJoueurFull createDefaultElementJoueurFull(int nbJoueur) {
//		ElementJoueurFull defaultData = new ElementJoueurFull();
//		// Initialiser les joueurs (par exemple, 2 joueurs)
//		for (int i = 0; i < nbJoueur; i++) {
//			Map<String, Map<String, ElementJoueur>> playerMap = new HashMap<>();
//			Map<String, ElementJoueur> elements = new HashMap<>();
//
//			String[] elementNames = { "Prizetotal", "Birthplace", "ImgJoueur", "Rank", "Birthdate", "Hand", "Weight",
//					"Name", "Acronyme", "Country",  "CityResidence", "Line", "Height", "ImgFlag", "Surname", "Age","Seeding" };
//			int j = 0;
//			for (String elementName : elementNames) {
//				int posY = 100+i*100;
//				if(i >= nbJoueur/2)
//					posY = (i-nbJoueur/2)*100;
//				int posX = j*20;
//				if(i >= nbJoueur/2)
//					posX =j*20+1500;
//				ElementJoueur element = new ElementJoueur();
//				element.setPositionX(posX);
//				element.setPositionY(posY);
//				elements.put(elementName, element);
//				j++;
//
//			}
//
//			playerMap.put("player" + i, elements);
//			defaultData.getPlayer().add(playerMap);
//		}
//
//		// Initialiser playerPolice
//		Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();
//		String[] elementNames = { "Prizetotal", "Birthplace", "ImgJoueur", "Rank", "Birthdate", "Hand", "Weight",
//				"Name", "Acronyme", "Country",  "CityResidence", "Line", "Height", "ImgFlag", "Surname", "Age","Seeding" };
//
//		for (String elementName : elementNames) {
//			ElementPoliceJoueur policeElement = new ElementPoliceJoueur();
//			if (elementName == "Name" || elementName == "Surname" || elementName == "Acronyme")
//				policeElement.setVisible(true);
//			else
//				policeElement.setVisible(false);
//			policeElement.setFont("Arial,1,25");
//			policeElement.setColor("0,0,0");
//			policeElement.setTaille(0);
//			playerPolice.put(elementName, policeElement);
//		}
//
//		defaultData.getPlayerPolice().putAll(playerPolice);
//
//		return defaultData;
//	}

	/**
	 * Font serializer.
	 *
	 * @param fontToSerialize the font to serialize
	 * @return the string
	 */
	public static String FontSerializer(Font fontToSerialize) {
		String SerializeFont = fontToSerialize.getName() + "," + fontToSerialize.getStyle() + ","
				+ fontToSerialize.getSize();
		return SerializeFont;
	}

	/**
	 * Color serializer.
	 *
	 * @param colorToSerialize the color to serialize
	 * @return the string
	 */
	public static String ColorSerializer(Color colorToSerialize) {
		String SerializeColor = colorToSerialize.getRed() + "," + colorToSerialize.getGreen() + ","
				+ colorToSerialize.getBlue();
		return SerializeColor;
	}

	/**
	 * Parses the color.
	 *
	 * @param color the color
	 * @return the color
	 */
	private static Color parseColor(String color) {
		String[] rgb = color.split(",");
		return new Color(Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()),
				Integer.parseInt(rgb[2].trim()));
	}

	/**
	 * Save config animation.
	 *
	 * @param panelAnimation the panel animation
	 * @param nomEvent the nom event
	 */
	public static void saveConfigAnimation(PanelAnimationConfiguration panelAnimation, String nomEvent) {
		AnimationSave animationSave = new AnimationSave();

		animationSave.setCheckboxDisplayPlayerTree(panelAnimation.isPlayerFullEnabled());

		Map<String, Object> mapZoomAnimation = new HashMap<>();
		mapZoomAnimation.put("checkbox", panelAnimation.isZoomAnimationEnabled());
		mapZoomAnimation.put("duration", panelAnimation.getZoomAnimationDuration());
		animationSave.setMapZoom(mapZoomAnimation);

		Map<String, Object> mapLabelAnimation = new HashMap<>();
		mapLabelAnimation.put("checkbox", panelAnimation.isLabelAnimationEnabled());
		mapLabelAnimation.put("duration", panelAnimation.getLabelAnimationDuration());
		animationSave.setMapLabel(mapLabelAnimation);

		Map<String, Object> mapTournamentTree = new HashMap<>();
		mapTournamentTree.put("checkbox", panelAnimation.isTournamentTreeEnabled());
		mapTournamentTree.put("positionLeft", panelAnimation.getXLeftTree());
		mapTournamentTree.put("positionRight", panelAnimation.getXRightTree());
		mapTournamentTree.put("width", panelAnimation.getWidthTree());
		mapTournamentTree.put("thickness", panelAnimation.getThicknessTree());
		mapTournamentTree.put("color", ColorSerializer(panelAnimation.getTreeColor()));
		animationSave.setMapTournamenTree(mapTournamentTree);

		Map<String, Object> mapAnimationTournamentTree = new HashMap<>();
		mapAnimationTournamentTree.put("checkbox", panelAnimation.isPathTreeAnimationEnabled());
		mapAnimationTournamentTree.put("duration", panelAnimation.getAnimationPathTreeDuration());
		mapAnimationTournamentTree.put("blink", panelAnimation.getNbBlinkTreeDuration());
		mapAnimationTournamentTree.put("color", ColorSerializer(panelAnimation.getPathTreeColor()));
		animationSave.setMapAnimationTournamenTree(mapAnimationTournamentTree);

		saveConfigAnimationToJson(animationSave, "Config/" + nomEvent, "animation.json");
	}

	/**
	 * Save config animation to json.
	 *
	 * @param data the data
	 * @param filePath the file path
	 * @param fileName the file name
	 */
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigAnimationToJson(AnimationSave data, String filePath, String fileName) {
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
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName + " in folder " + filePath + " has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the config animation.
	 *
	 * @param nomEvent the nom event
	 * @param panelanimation the panelanimation
	 */
	public static void setConfigAnimation(String nomEvent, PanelAnimationConfiguration panelanimation) {
		try (Reader reader = new FileReader("resources"+File.separator+"Config/" + nomEvent+ File.separator + "animation.json")) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				panelanimation.setDisplayJFullCheckBox(configObject.getAsJsonPrimitive("checkboxDisplayPlayerTree").getAsBoolean());
				
				panelanimation.setZoomAnimationCheckBox(configObject.getAsJsonObject("mapZoom").getAsJsonPrimitive("checkbox").getAsBoolean());
				panelanimation.setZoomAnimationSpinner(configObject.getAsJsonObject("mapZoom").getAsJsonPrimitive("duration").getAsInt());
				
				panelanimation.setLabelAnimationCheckBox(configObject.getAsJsonObject("mapLabel").getAsJsonPrimitive("checkbox").getAsBoolean());
				panelanimation.setLabelAnimationSpinner(configObject.getAsJsonObject("mapLabel").getAsJsonPrimitive("duration").getAsInt());
				
				panelanimation.setxLeftSpinner(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("positionLeft").getAsInt());
				panelanimation.setxRightSpinner(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("positionRight").getAsInt());
				panelanimation.setTreeColor(parseColor(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("color").getAsString()));
				panelanimation.setDisplayTreeCheckBox(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("checkbox").getAsBoolean());
				panelanimation.setWidthTreeSpinner(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("width").getAsInt());
				panelanimation.setThicknessTreeSpinner(configObject.getAsJsonObject("mapTournamenTree").getAsJsonPrimitive("thickness").getAsInt());
				
				panelanimation.setClignotementNumberTreeSpinner(configObject.getAsJsonObject("mapAnimationTournamenTree").getAsJsonPrimitive("blink").getAsInt());
				panelanimation.setAnimationTimeTreeSpinner(configObject.getAsJsonObject("mapAnimationTournamenTree").getAsJsonPrimitive("duration").getAsInt());
				panelanimation.setPathTreeColor(parseColor(configObject.getAsJsonObject("mapAnimationTournamenTree").getAsJsonPrimitive("color").getAsString()));
				panelanimation.setAnimationTreeCheckBox(configObject.getAsJsonObject("mapAnimationTournamenTree").getAsJsonPrimitive("checkbox").getAsBoolean());				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete folder.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
//	public static void deleteFolder(Path path) throws IOException {
//		System.out.println("folder to delete : "+path);
//        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                Files.delete(file);
//                return FileVisitResult.CONTINUE;
//            }
//
//            @Override
//            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                Files.delete(dir);
//                return FileVisitResult.CONTINUE;
//            }
//        });
//    }
	
	public static void deleteFolder(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            System.out.println("❌ Le dossier n'existe pas : " + dir);
            return; // Évite de tenter une suppression inutile
        }

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (Files.exists(file)) {
                    Files.delete(file); // Supprime chaque fichier
                    System.out.println("✔ Fichier supprimé : " + file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (Files.exists(dir)) {
                    Files.delete(dir); // Supprime le dossier après avoir supprimé son contenu
                    System.out.println("✔ Dossier supprimé : " + dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }


}
