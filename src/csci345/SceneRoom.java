package csci345;

import java.util.ArrayList;

public class SceneRoom extends Room {
	private Scene scene;
	private ArrayList<ExtraRole> extras;
	private int maxShotCounter;
	private int currShotCounter;

	public void wrapScene() {
		this.scene = null;
//NOTE need to do payout and rolls here
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

	public int getMaxShotCounter(){
		return this.maxShotCounter;
	}

	public int getCurrShotCounter(){
		return this.currShotCounter;
	}


}
