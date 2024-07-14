package Sauvegarde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElementJoueurFull {
	private ArrayList<Map<String,Map<String, ElementJoueur>>> full = new ArrayList<>();
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();
    
    public ArrayList<Map<String, Map<String, ElementJoueur>>> getPlayer() {
        return full;
    }
    public Map<String, ElementPoliceJoueur> getPlayerPolice() {
		return playerPolice;
	}
}
