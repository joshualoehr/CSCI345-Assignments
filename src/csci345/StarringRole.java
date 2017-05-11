package csci345;

public class StarringRole extends Role {
	public void payout() {
		myPlayer = getPlayer();
		myPlayer.increaseCredits(2);
	}
}
