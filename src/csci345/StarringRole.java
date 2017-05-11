package csci345;

public class StarringRole extends Role {
	public void payout() {
		Player myPlayer = getPlayer();
		myPlayer.increaseCredits(2);
	}
	public void wrapScenePayout() {
		
	}
}
