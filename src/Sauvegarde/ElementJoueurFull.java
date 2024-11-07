/*
 * 
 */
package Sauvegarde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementJoueurFull.
 */
public class ElementJoueurFull {
	
	/** The full. */
	private ArrayList<Map<String,Map<String, ElementJoueur>>> full = new ArrayList<>();
	
	/** The player police. */
	private Map<String, ElementPoliceJoueur> playerPolice = new HashMap<>();
    
    /**
     * Gets the player.
     *
     * @return the player
     */
    public ArrayList<Map<String, Map<String, ElementJoueur>>> getPlayer() {
        return full;
    }
    
    /**
     * Gets the player police.
     *
     * @return the player police
     */
    public Map<String, ElementPoliceJoueur> getPlayerPolice() {
		return playerPolice;
	}
}
