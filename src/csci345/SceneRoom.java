package csci345;
import java.util.Random;
import java.util.ArrayList;

public class SceneRoom extends Room {
	private Scene scene;
	private ArrayList<ExtraRole> extras;
	private int maxShotCounter;
	private int currShotCounter;

	public void wrapScene() {
		this.scene = null;
		SceneRoom myRoom = (SceneRoom)myPlayer.getRoom();

		if (getScene().getStarringRoles().size() != 0){
			Random randNum = new Random();
			int[getBudget()] rolls;

			for (int i = 0; i < getExtraRoles().size(); i++){
				Role currRole = getExtraRoles().get(i);
				currRole.wrapScenePayout();
			}

			for(int i = 0; i < getBudget(); i++){
				rolls[i] = randNum.nextInt(6) + 1;
			}
			rolls.sort();
		}
	}

	public void decrementShotCounter() {
		this.currShotCounter--;

		if (this.currShotCounter == 0){
			wrapScene();
		}
	}

	public Scene getScene(){
		return this.scene;
	}

	public ArrayList getExtraRoles(){
		return this.extras;
	}

	public ArrayList<StarringRole> getStarringRoles(){
		return getScene.getStarringRoles();
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




}
