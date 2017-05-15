package csci345;

import java.util.ArrayList;

public class Scene {

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

	public int getBudget() {
		return this.budget;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getSceneNumber(){ return this.sceneNumber; }

	public ArrayList<StarringRole> getStarringRoles() {
		return this.stars;
	}
}
