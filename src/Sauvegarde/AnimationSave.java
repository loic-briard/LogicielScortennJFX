package Sauvegarde;

import java.util.HashMap;
import java.util.Map;

public class AnimationSave {
	private boolean checkboxDisplayPlayerTree;
	private Map<String, Object> mapZoom = new HashMap<>();
	private Map<String, Object> mapLabel = new HashMap<>();
	private Map<String, Object> mapTournamenTree = new HashMap<>();
	private Map<String, Object> mapAnimationTournamenTree = new HashMap<>();
	
	public boolean isCheckboxDisplayPlayerTree() {
		return checkboxDisplayPlayerTree;
	}
	public void setCheckboxDisplayPlayerTree(boolean checkboxDisplayPlayerTree) {
		this.checkboxDisplayPlayerTree = checkboxDisplayPlayerTree;
	}
	public Map<String, Object> getMapZoom() {
		return mapZoom;
	}
	public void setMapZoom(Map<String, Object> mapZoom) {
		this.mapZoom = mapZoom;
	}
	public Map<String, Object> getMapLabel() {
		return mapLabel;
	}
	public void setMapLabel(Map<String, Object> mapLabel) {
		this.mapLabel = mapLabel;
	}
	public Map<String, Object> getMapTournamenTree() {
		return mapTournamenTree;
	}
	public void setMapTournamenTree(Map<String, Object> mapTournamenTree) {
		this.mapTournamenTree = mapTournamenTree;
	}
	public Map<String, Object> getMapAnimationTournamenTree() {
		return mapAnimationTournamenTree;
	}
	public void setMapAnimationTournamenTree(Map<String, Object> mapAnimationTournamenTree) {
		this.mapAnimationTournamenTree = mapAnimationTournamenTree;
	}

}
