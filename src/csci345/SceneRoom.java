package csci345;
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

	public void wrapScene() {
		extras.removeIf(ex -> ex.getPlayer() == null);
		
		int numStarringPlayers = 0;
		for (StarringRole role : scene.getStarringRoles()) {
			if (role.getPlayer() != null)
				numStarringPlayers++;
		}
		
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
				extras.forEach(ex -> ex.getPlayer().addPayout(ex.wrapScenePayout()));
			}
		}
		
		scene.wrap();
		extras.forEach(ex -> ex.getPlayer().takeRole(null));
		this.scene = null;
	}

	public boolean decrementShotCounter() {
		System.out.println("Decrementing shot counter from " + currShotCounter + " to " + (currShotCounter-1));
		return --currShotCounter == 0;
	}

	public void addExtraRole(ExtraRole newRole) {
		extras.add(newRole);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene(){
		return this.scene;
	}

	public ArrayList<ExtraRole> getExtraRoles(){
		return this.extras;
	}

	public ArrayList<StarringRole> getStarringRoles(){
		return getScene().getStarringRoles();
	}

	public int getBudget(){
		return getScene().getBudget();
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
	
	@Override
	public ArrayList<Role> getAllRoles(){
		ArrayList<Role> myList = new ArrayList<Role>();
		myList.addAll(getStarringRoles());
		myList.addAll(getExtraRoles());
		return myList;
	}
}
