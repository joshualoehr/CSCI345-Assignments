package csci345;

import java.util.ArrayList;

public class Scene {
	private String name;
	private String description;
	private ArrayList<StarringRole> stars;
	private int budget;

	public int getBudget() {
		return this.budget;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public ArrayList getStarringRoles() {
		return this.stars;
	}
}
