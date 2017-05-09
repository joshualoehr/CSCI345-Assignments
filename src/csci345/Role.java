package csci345;

public abstract class Role {
	private String name;
	private String description;
	private int minRankNeeded;
	private Player player;

	public abstract void payout();

	public Player getPlayer(){
		return this.player;
	}

	public int getMinRank(){
		return this.minRankNeeded;
	}

	public String getDescription(){
		return this.description;
	}

	public String getName(){
		return this.name;
	}
}
