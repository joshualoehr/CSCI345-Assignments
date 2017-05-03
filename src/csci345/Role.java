package csci345;

public abstract class Role {
	private String name;
	private String description;
	private int minRank;
	private Player player;
	
	public abstract void payout();
}
