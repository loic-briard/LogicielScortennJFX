/*
 * 
 */
package Sauvegarde;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimationSave.
 */
public class AnimationSave {
	
	/** The checkbox display player tree. */
	private boolean checkboxDisplayPlayerTree;
	
	/** The map zoom. */
	private Map<String, Object> mapZoom = new HashMap<>();
	
	/** The map label. */
	private Map<String, Object> mapLabel = new HashMap<>();
	
	/** The map tournamen tree. */
	private Map<String, Object> mapTournamenTree = new HashMap<>();
	
	/** The map animation tournamen tree. */
	private Map<String, Object> mapAnimationTournamenTree = new HashMap<>();
	
	/**
	 * Checks if is checkbox display player tree.
	 *
	 * @return true, if is checkbox display player tree
	 */
	public boolean isCheckboxDisplayPlayerTree() {
		return checkboxDisplayPlayerTree;
	}
	
	/**
	 * Sets the checkbox display player tree.
	 *
	 * @param checkboxDisplayPlayerTree the new checkbox display player tree
	 */
	public void setCheckboxDisplayPlayerTree(boolean checkboxDisplayPlayerTree) {
		this.checkboxDisplayPlayerTree = checkboxDisplayPlayerTree;
	}
	
	/**
	 * Gets the map zoom.
	 *
	 * @return the map zoom
	 */
	public Map<String, Object> getMapZoom() {
		return mapZoom;
	}
	
	/**
	 * Sets the map zoom.
	 *
	 * @param mapZoom the map zoom
	 */
	public void setMapZoom(Map<String, Object> mapZoom) {
		this.mapZoom = mapZoom;
	}
	
	/**
	 * Gets the map label.
	 *
	 * @return the map label
	 */
	public Map<String, Object> getMapLabel() {
		return mapLabel;
	}
	
	/**
	 * Sets the map label.
	 *
	 * @param mapLabel the map label
	 */
	public void setMapLabel(Map<String, Object> mapLabel) {
		this.mapLabel = mapLabel;
	}
	
	/**
	 * Gets the map tournamen tree.
	 *
	 * @return the map tournamen tree
	 */
	public Map<String, Object> getMapTournamenTree() {
		return mapTournamenTree;
	}
	
	/**
	 * Sets the map tournamen tree.
	 *
	 * @param mapTournamenTree the map tournamen tree
	 */
	public void setMapTournamenTree(Map<String, Object> mapTournamenTree) {
		this.mapTournamenTree = mapTournamenTree;
	}
	
	/**
	 * Gets the map animation tournamen tree.
	 *
	 * @return the map animation tournamen tree
	 */
	public Map<String, Object> getMapAnimationTournamenTree() {
		return mapAnimationTournamenTree;
	}
	
	/**
	 * Sets the map animation tournamen tree.
	 *
	 * @param mapAnimationTournamenTree the map animation tournamen tree
	 */
	public void setMapAnimationTournamenTree(Map<String, Object> mapAnimationTournamenTree) {
		this.mapAnimationTournamenTree = mapAnimationTournamenTree;
	}

}
