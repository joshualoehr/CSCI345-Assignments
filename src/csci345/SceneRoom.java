package csci345;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class SceneRoom extends Room {
	private Scene scene;
	private ArrayList<ExtraRole> extras;
	private int maxShotCounter;
	private int currShotCounter;

	public SceneRoom(String name) {
		super(name);
		this.extras = null;
		this.maxShotCounter = 0;
		this.currShotCounter = 0;
	}

	public SceneRoom(String name, int maxShotCounter, ArrayList<ExtraRole> extra){
		super(name);
		this.extras = extras;
		this.maxShotCounter = maxShotCounter;
		this.currShotCounter = maxShotCounter;

	}

	public void wrapScene() {
		if (getScene().getStarringRoles().size() != 0){
			Random randNum = new Random();
			int budget = getBudget();
			int[] rolls = new int[budget];

			for (int i = 0; i < getExtraRoles().size(); i++){
				Role currRole = getExtraRoles().get(i);
				currRole.wrapScenePayout();
			}

			for(int i = 0; i < budget; i++){
				rolls[i] = randNum.nextInt(6) + 1;
			}
			Arrays.sort(rolls);
		}
		this.scene = null;
	}

	public void decrementShotCounter() {
		this.currShotCounter--;

		if (this.currShotCounter == 0){
			wrapScene();
		}
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
