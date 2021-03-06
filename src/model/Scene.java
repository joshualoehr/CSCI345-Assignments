package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Scene extends Observable {

	private String name;
	private String description;
	private ArrayList<StarringRole> stars;
	private int budget;
	private int sceneNumber;

	public Scene(int budget, int sceneNumber, String name, String description, ArrayList<StarringRole> stars){
		this.name = name;
		this.description = description;
		this.stars = stars;
		this.budget = budget;
		this.sceneNumber = sceneNumber;
	}
	
	@Override
	public String toString() {
		return String.format("%s scene %d", name, sceneNumber);
	}

	public int getBudget() {
		return this.budget;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getSceneNumber() { 
		return this.sceneNumber; 
	}

	public ArrayList<StarringRole> getStarringRoles() {
		return this.stars;
	}
	
	public ArrayList<StarringRole> getSortedStarringRoles() {
		// Create a copy
		ArrayList<StarringRole> stars = new ArrayList<StarringRole>(this.stars);
		
		// Sort it and return
		stars.sort((s1, s2) -> s1.compareTo(s2));
		Collections.reverse(stars);
		return stars;
	}
	
	public void wrap() {
		for (StarringRole role : stars) {
			if (role.getPlayer() != null) {
				role.getPlayer().resetRehearsalChips();
				role.getPlayer().takeRole(null);
			}
		}
	}
}
