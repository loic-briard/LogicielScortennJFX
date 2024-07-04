package Diffusion;

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
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


//Classe représentant un élément avec ses propriétés
class ElementJoueur {
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
//Classe représentant un élément avec ses propriétés
class ElementPoliceJoueur {
	private boolean visible;
	private String font;
	private String color;
	private int taille;
	
	public void setTaille(int taille) {
		this.taille = taille;
	}
	public void setVisible(boolean b) {
		this.visible = b;
	}
	public void setFont(String Font) {
		this.font = Font;
	}
	public void setColor(String Color) {
		this.color = Color;
	}
	
	public int getTaille() {
		return taille;
	}
	public boolean isVisible() {
		return visible;
	}
	public String getFont() {
		return font;
	}
	public String getColor() {
		return color;
	}
}
class ElementOneJoueurForTab {
	private Map<String, ElementJoueur> player = new HashMap<>();
	
	public Map<String, ElementJoueur> getOnePlayer() {
		return player;
	}
}
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
	private ArrayList<Map<String,Map<String, ElementJoueur>>> game = new ArrayList<>();
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
		game = playerList;	}
}
class ElementJoueurTab {
	private ArrayList<Map<String,Map<String, ElementJoueur>>> tab = new ArrayList<>();
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
		tab = playerList;	}
}
class ElementJoueurFull {
	private ArrayList<Map<String,Map<String, ElementJoueur>>> full = new ArrayList<>();
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();
    
    public ArrayList<Map<String, Map<String, ElementJoueur>>> getPlayer() {
        return full;
    }
    public Map<String, ElementPoliceJoueur> getPlayerPolice() {
		return playerPolice;
	}
}

public class ConfigurationSaveLoad {
	private Map<String, Object> locations = new HashMap<>();

	public Map<String, Object> getLocations() {
		return locations;
	}
	public void setLocations(Map<String, Object> map) {
		this.locations = map;
	}
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFile(ElementOneJoueur data, String filePath, String fileName) {
	    Gson gson = new Gson();
	 // Vérifiez si le dossier existe
	    File destination = new File(filePath);
        if (!destination.exists()) {
            // Si le dossier n'existe pas, essayez de le créer
            if (destination.mkdirs()) {
                System.out.println("  + Folder "+filePath+" has been created with success");
            } else {
                System.out.println("  ! Folder creation : "+filePath+" failed");
                return; // Arrêtez l'exécution si la création du dossier échoue
            }
        }
        try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileGame(ElementJoueurGame elementsTwoPlayer, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
			} else {
				System.out.println("  ! Folder creation : "+filePath+" failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(elementsTwoPlayer);
			writer.write(json);
			System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileTab(ElementJoueurTab data, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
			} else {
				System.out.println("  ! Folder creation : "+filePath+" failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour sauvegarder les données de configuration au format JSON
	public static void saveConfigToFileFull(ElementJoueurFull data, String filePath, String fileName) {
		Gson gson = new Gson();
		// Vérifiez si le dossier existe
		File destination = new File(filePath);
		if (!destination.exists()) {
			// Si le dossier n'existe pas, essayez de le créer
			if (destination.mkdirs()) {
				System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
			} else {
				System.out.println("  ! Folder creation : "+filePath+" failed");
				return; // Arrêtez l'exécution si la création du dossier échoue
			}
		}
		try (FileWriter writer = new FileWriter(filePath + File.separator + fileName, false)) {
			String json = gson.toJson(data);
			writer.write(json);
			System.out.println("  File : " + fileName+" in folder "+filePath+" has been modified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
				// Mappez les propriétés du fichier JSON à votre structure de classe ConfigurationSaveLoad
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

	public static void saveWindows(String nomEvent, String typeWindows, Map<JPanel, JLabel> JoueurDetailsP1, Map<JPanel, JLabel> JoueurDetailsP2) {
		ConfigurationSaveLoad eventData = loadConfigFromFile("Config/" + nomEvent + "/player.json");
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
					//System.out.println("taille : " + panel.getName() + panel.getHeight());
					playerPolice.setTaille((int) panel.getHeight());
				}
				playerPolice.setFont(FontSerializer(label.getFont()));
				playerPolice.setColor(ColorSerializer(label.getForeground()));
				elementsOnePlayer.getOnePlayer().put(panel.getName(), playerElement);
				elementsOnePlayer.getOnePlayerPolice().put(panel.getName(), playerPolice);
			}
			System.out.println("PLAYER save completed");
			saveConfigToFile(elementsOnePlayer, "Config/" + nomEvent, "player.json");
			break;

		default:
			System.out.println("! ERROR windows name !");
			break;
		}
	}
	public static void saveWindowsMultiTab(String nomEvent, String typeWindows, ArrayList<Map<JPanel, JLabel>> JoueurDetails ) {
		ConfigurationSaveLoad eventData = loadConfigFromFile("Config/" + nomEvent + "/"+typeWindows+".json");
		if (eventData == null) {
			System.out.println("Content from : " + "Config/" + nomEvent + "/"+typeWindows+".json" + " is empty !");
			eventData = new ConfigurationSaveLoad();
		} else {
			System.out.println("Content from : " + "Config/" + nomEvent + "/"+typeWindows+".json" + " is full !");
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
						//System.out.println("taille : " + panel.getName() + panel.getHeight());
						playerPolice.setTaille((int) panel.getHeight());
					}
					playerPolice.setFont(FontSerializer(label.getFont()));
					playerPolice.setColor(ColorSerializer(label.getForeground()));
					
					player.put(panel.getName(), playerElement);
					elementsGamePlayer.getPlayerPolice().put(panel.getName(), playerPolice);
				}
				playerList.put("player"+k, player);
				elementsGamePlayer.getPlayer().add(playerList);
				k++;
			}
			System.out.println("GAME save completed");
			saveConfigToFileGame(elementsGamePlayer, "Config/" + nomEvent, "game.json");
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
						//System.out.println("taille : " + panel.getName() + panel.getHeight());
						playerPolice.setTaille((int) panel.getHeight());
					}
					playerPolice.setFont(FontSerializer(label.getFont()));
					playerPolice.setColor(ColorSerializer(label.getForeground()));
					
					player.put(panel.getName(), playerElement);
					elementsTabPlayer.getPlayerPolice().put(panel.getName(), playerPolice);
				}
				playerList.put("player"+i, player);
				elementsTabPlayer.getPlayer().add(playerList);
				i++;
			}
			System.out.println("TAB save completed");
			saveConfigToFileTab(elementsTabPlayer, "Config/" + nomEvent, "tab.json");
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
					if (panel.getName().equals("ImgJoueur") || panel.getName().equals("ImgFlag")) {
						//System.out.println("taille : " + panel.getName() + panel.getHeight());
						playerPoliceFull.setTaille((int) panel.getHeight());
					}
					playerPoliceFull.setFont(FontSerializer(label.getFont()));
					playerPoliceFull.setColor(ColorSerializer(label.getForeground()));
					
					player.put(panel.getName(), playerElement);
					elementsFullPlayer.getPlayerPolice().put(panel.getName(), playerPoliceFull);
				}
				playerList.put("player"+j, player);
				elementsFullPlayer.getPlayer().add(playerList);
				j++;
			}
			System.out.println("FULL save completed");
			saveConfigToFileFull(elementsFullPlayer, "Config/" + nomEvent, "full.json");
			break;

		default:
			System.out.println("! ERROR window name !");
			break;
		}
	}
	
	public ElementJoueur getElement(String emplacement, String eventName, String typeFenetre, String nomElement, int index) {
		String Player = "";
		if (typeFenetre == "player" || typeFenetre == "game") {
			switch (index) {
			case 0:
				// System.out.println("getElement Player 1");
				Player = "player";
				break;
			case 1:
				// System.out.println("getElement Player 1");
				Player = "player1";
				break;
			case 2:
				// System.out.println("getElement Player 2");
				Player = "player2";
				break;

			default:
				System.out.println("! ERROR get element : "+nomElement+" from file : "+emplacement);
				break;
			}
		}
		if(typeFenetre == "tab" || typeFenetre == "full")
			Player="player"+index;
		
		try (Reader reader = new FileReader(emplacement)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonObject elementObject = new JsonObject();//configObject.getAsJsonObject(typeFenetre).getAsJsonObject(nomElement);
				System.out.println(typeFenetre+" GET element : "+nomElement+" for player index : "+index+" from file : "+emplacement);
				switch (typeFenetre) {
				case "player":
					elementObject = configObject.getAsJsonObject(Player).getAsJsonObject(nomElement);
					break;
				case "game":
//					elementObject = configObject.getAsJsonObject(Player).getAsJsonObject(nomElement);
					if (configObject.has(typeFenetre) && configObject.get(typeFenetre).isJsonArray()) {
						JsonArray tabArray = configObject.getAsJsonArray(typeFenetre);
						// Vérifier si l'index spécifié est valide
						if(index > tabArray.size()-1) {
							System.out.println("! Player index superior to the number of elements in the "+typeFenetre+".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {
							JsonObject playerObject = tabArray.get(index).getAsJsonObject();

							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index) && playerObject.get("player" + index).isJsonObject()) {
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
						if(index > tabArray.size()-1) {
							System.out.println("! Player index superior to the number of elements in the "+typeFenetre+".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {
							JsonObject playerObject = tabArray.get(index).getAsJsonObject();

							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index) && playerObject.get("player" + index).isJsonObject()) {
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
						if(index > tabArray.size()-1) {
							System.out.println("! Player index superior to the number of elements in the "+typeFenetre+".json file");
							index = 0;
						}
						if (index >= 0 && index < tabArray.size()) {
							
							JsonObject playerObject = tabArray.get(index).getAsJsonObject();
							
							// Vérifier si le joueur spécifié existe
							if (playerObject.has("player" + index) && playerObject.get("player" + index).isJsonObject()) {
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
		        if(elementObject != null) {
		        // Set the properties based on the retrieved JSON data
		        element.setPositionX(elementObject.getAsJsonPrimitive("positionX").getAsInt());
		        element.setPositionY(elementObject.getAsJsonPrimitive("positionY").getAsInt());
		        // Return the ElementJoueur instance
		        return element;
		        }else {
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
	public ElementPoliceJoueur getElementPolice(String emplacement, String eventName, String typeFenetre, String nomElement, int index) {
		
		try (Reader reader = new FileReader(emplacement)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonObject elementObject = new JsonObject();
				switch (typeFenetre) {
				case "player":
					//System.out.println("recup player");
					elementObject = configObject.getAsJsonObject("playerPolice").getAsJsonObject(nomElement);
					break;
				case "game":
					//System.out.println("recup game");
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
				if(elementObject != null) {
		        elementPolice.setVisible(elementObject.getAsJsonPrimitive("visible").getAsBoolean());
		        elementPolice.setFont(elementObject.getAsJsonPrimitive("font").getAsString());
		        elementPolice.setColor(elementObject.getAsJsonPrimitive("color").getAsString());
		        if(nomElement.equals("ImgJoueur") || nomElement.equals("ImgFlag"))
		        {
		        	elementPolice.setTaille(elementObject.getAsJsonPrimitive("taille").getAsInt());
		        }
					return elementPolice;
				}else {
					System.out.println("! POLICE is empty in file : "+emplacement);
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
	public Map<String, Map<String, Object>> getAllElementVisible(int nbPlayer,String filePath) throws JsonIOException, JsonSyntaxException, IOException{

		Map<String, Map<String, Object>> playerData = new HashMap<>();
		try (Reader reader = new FileReader(filePath)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject configObject = jsonElement.getAsJsonObject();
				JsonArray playersArray = configObject.getAsJsonArray("full");

				JsonObject playerPolice = configObject.getAsJsonObject("playerPolice");

				for (int i = 0; i < nbPlayer; i++) {
					JsonObject playerObject = playersArray.get(i).getAsJsonObject().getAsJsonObject("player" + i);
					
					Map<String, Object> elementsData = new HashMap<>();

					for (String key : playerObject.keySet()) {
						boolean isVisible = playerPolice.getAsJsonObject(key).get("visible").getAsBoolean();

						if (isVisible) {
							//System.out.println("key recuperer : "+key);
							int positionX = playerObject.getAsJsonObject(key).getAsJsonPrimitive("positionX").getAsInt();
							int positionY = playerObject.getAsJsonObject(key).getAsJsonPrimitive("positionY").getAsInt();
							String font = playerPolice.getAsJsonObject(key).get("font").getAsString();
							String color = playerPolice.getAsJsonObject(key).get("color").getAsString();

							Map<String, Object> elementData = new HashMap<>();
							elementData.put("position", new Point(positionX, positionY));
							elementData.put("font", font);
							elementData.put("color", parseColor(color));

							elementsData.put(key, elementData);
						}
					}
					playerData.put("player" + i, elementsData);
				}
			}
		}
		return playerData;
	}
	
	public static String FontSerializer(Font fontToSerialize) {
		String SerializeFont = fontToSerialize.getName()+","+fontToSerialize.getStyle()+","+fontToSerialize.getSize();		
		return SerializeFont;
	}
	public static String ColorSerializer(Color colorToSerialize) {
		String SerializeColor = colorToSerialize.getRed()+","+colorToSerialize.getGreen()+","+colorToSerialize.getBlue();		
		return SerializeColor;
	}
	private static Color parseColor(String color) {
        String[] rgb = color.split(",");
        return new Color(Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()), Integer.parseInt(rgb[2].trim()));
    }
}
