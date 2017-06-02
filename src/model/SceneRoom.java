package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class SceneRoom extends Room {
	private Scene scene;
	private ArrayList<ExtraRole> extras;
	private int maxShotCounter = 0;
	private int currShotCounter = 0;

	public SceneRoom(String name) {
		super(name);
		extras = new ArrayList<ExtraRole>();
	}

	public boolean wrapScene() {
		// Determine the number of Players in StarringRoles
		int numStarringPlayers = 0;
		for (StarringRole role : scene.getStarringRoles()) {
			if (role.getPlayer() != null)
				numStarringPlayers++;
		}

		// Apply bonuses only if at least one StarringRole is occupied
		if (numStarringPlayers > 0) {
			Random randNum = new Random();
			int budget = getBudget();
			LinkedList<Integer> rolls = new LinkedList<Integer>();

			for (int i = 0; i < budget; i++){
				rolls.add(randNum.nextInt(6) + 1);
			}
			Collections.sort(rolls);
			Collections.reverse(rolls);
			
			LinkedList<StarringRole> roleQueue 
				= new LinkedList<StarringRole>(scene.getSortedStarringRoles());
			roleQueue.removeIf(r -> r.getPlayer() == null);
			
			if (roleQueue.size() != 0) {
				while (rolls.size() > 0) {
					StarringRole role = roleQueue.removeFirst();
					role.addBonus(rolls.removeFirst());
					roleQueue.add(role);
				}
				
				roleQueue.forEach(r -> r.getPlayer().addPayout(r.wrapScenePayout()));
				for (ExtraRole ex : extras) {
					if (ex.getPlayer() != null)
						ex.getPlayer().addPayout(ex.wrapScenePayout());
				}
			}
		}
		
		// Destroy references
		scene.wrap();
		for (ExtraRole ex : extras) {
			if (ex.getPlayer() != null)
				ex.getPlayer().takeRole(null);
		}
		setScene(null);
		
		return numStarringPlayers > 0;
	}

	/* Decrements shot counter and returns true if the scene is complete */
	public boolean decrementShotCounter() {
		currShotCounter -= 1;
		setChanged();
		notifyObservers(maxShotCounter - currShotCounter);
		return currShotCounter == 0;
	}

	public void addExtraRole(ExtraRole newRole) {
		extras.add(newRole);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		setChanged();
		notifyObservers(scene);
	}

	public Scene getScene() {
		return this.scene;
	}

	public ArrayList<ExtraRole> getExtraRoles() {
		return this.extras;
	}

	public ArrayList<StarringRole> getStarringRoles() {
		if (scene == null)
			return new ArrayList<StarringRole>();
		else
			return scene.getStarringRoles();
	}

	public int getBudget() {
		return scene.getBudget();
	}

	public void initShotCounters(int initVal) {
		maxShotCounter = initVal;
		currShotCounter = initVal;
	}
	
	public int getMaxShotCounter(){
		return this.maxShotCounter;
	}

	public int getCurrShotCounter(){
		return this.currShotCounter;
	}
	
	public void resetCurrShotCounter() {
		this.currShotCounter = this.maxShotCounter;
		setChanged();
		notifyObservers(0);
	}
	
	@Override
	public ArrayList<Role> getAllRoles(){
		ArrayList<Role> myList = new ArrayList<Role>();
		myList.addAll(getStarringRoles());
		myList.addAll(getExtraRoles());
		return myList;
	}
}
