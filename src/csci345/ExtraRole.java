package csci345;

public class ExtraRole extends Role {
	public void payout() {
		Player myPlayer = getPlayer();
		myPlayer.increaseDollars(1);
		myPlayer.increaseCredits(1);
	}
	public void wrapScenePayout(){
		Player myPlayer = getPlayer();
		int minRank = getMinRank();
		myPlayer.increaseDollars(minRank);
	}
}
