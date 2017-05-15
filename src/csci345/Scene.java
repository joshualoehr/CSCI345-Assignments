package csci345;

import java.util.ArrayList;
import java.util.Collections;

public class Scene {

	private String name;
	private String description;
	private ArrayList<StarringRole> stars;
	private int budget;
	private int sceneNumber;


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
}
