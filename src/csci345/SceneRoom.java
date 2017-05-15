package csci345;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SceneRoom extends Room {
	private Scene scene;
	private ArrayList<ExtraRole> extras = new ArrayList<ExtraRole>();
	private int maxShotCounter;
	private int currShotCounter;

	public SceneRoom(String name) {
		super(name);
		this.extras = null;
		this.maxShotCounter = 0;
		this.currShotCounter = 0;
	}

	public SceneRoom(String name, int maxShotCounter, ArrayList<ExtraRole> extras){
		super(name);
		this.extras = extras;
		this.maxShotCounter = maxShotCounter;
		this.currShotCounter = maxShotCounter;

	}

	public List<Payout> wrapScene() {
		ArrayList<Payout> payouts = new ArrayList<Payout>();
		
		if (scene.getStarringRoles().size() != 0) {
			Random randNum = new Random();
			int budget = getBudget();
			int[] rolls = new int[budget];

			for (Role currRole : extras) {
				currRole.wrapScenePayout();
			}

			for (int i = 0; i < budget; i++){
				rolls[i] = randNum.nextInt(6) + 1;
			}
			Arrays.sort(rolls);
			
			LinkedList<StarringRole> roleQueue 
				= new LinkedList<StarringRole>(scene.getSortedStarringRoles());
			for (int roll : rolls) {
				StarringRole role = roleQueue.removeFirst();
				role.addBonus(roll);
				roleQueue.add(role);
			}
		
			for (StarringRole role : roleQueue) {
				Payout payout = role.wrapScenePayout();
				role.getPlayer().addPayout(payout);
				payouts.add(payout);
			}
		}
		
		this.scene = null;
		return payouts;
	}

	public boolean decrementShotCounter() {
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

	public int getMaxShotCounter(){
		return this.maxShotCounter;
	}

	public int getCurrShotCounter(){
		return this.currShotCounter;
	}
	public ArrayList<Role> getAllRoles(){
		ArrayList<Role> myList = new ArrayList<Role>();
		myList.addAll(getStarringRoles());
		myList.addAll(getExtraRoles());
		return myList;
	}
}
