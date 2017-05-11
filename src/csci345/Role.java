package csci345;
import java.util.Random;

public abstract class Role {
	private String name;
	private String description;
	private int minRankNeeded;
	private Player player;

	public abstract void payout();
	public abstract void wrapScenePayout();

	public void act(SceneRoom mySceneRoom, int budget){
		Random randNum = new Random();
        int diceRoll = randNum.nextInt(6) + 1;
        if ((diceRoll + player.getRehearsalChips()) >= budget){//success
            mySceneRoom.decrementShotCounter();
            payout();
        }
        else{//Failed to act only extra gets paid
            if (this instanceof ExtraRole){
                player.increaseDollars(1);
            }
        }
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getMinRank() {
		return this.minRankNeeded;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}
}
