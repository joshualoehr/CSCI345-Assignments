package csci345;
import java.util.Random;

public abstract class Role {
	private String name;
	private String description;
	private int minRankNeeded;
	private Player player;

	public abstract void payout();
	public abstract void wrapScenePayout();

	public int act(SceneRoom mySceneRoom, int budget){
		Random randNum = new Random();
        int diceRoll = randNum.nextInt(6) + 1;
        if ((diceRoll + player.rehearsalChips) >= budget{//success
            mySceneRoom.decrementShotCounter();
            if (role instanceof StarringRole){
                this.role.payout();
            }
            else{
                this.role.payout()
            }
        }
        else{//Failed to act only extra gets paid
            if (role instanceof ExtraRole){
                this.dollars++;
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
