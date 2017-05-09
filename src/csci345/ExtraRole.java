package csci345;

public class ExtraRole extends Role {
	public void payout() {
		Player myPlayer = getPlayer();
		myPlayer.increaseDollars(myPlayer.getRole().getMinRank());
	}
}
