package csci345;
import java.util.HashMap;
import java.util.Random;

public abstract class Role {
	
	private static HashMap<String, Role> roles;

	public static Role getRole(String roleName) {
		return roles.get(roleName);
	}
	
	private String name;
	private String description;
	private int minRankNeeded;
	private boolean occupied;

	public abstract Payout payout(boolean success);
	public abstract Payout wrapScenePayout();
	
	public Role(String name, String description, int level) {
		this.name = name;
		this.description = description;
		this.minRankNeeded = level;
		this.occupied = false;
		
		roles.put(name, this);
	}
	
	@Override
	public String toString() {
		return String.format("%s, \"%s\"", name, description);
	}

	public Payout act(int budget, int rehearsalBonus) {
		Random randNum = new Random();
        int diceRoll = randNum.nextInt(6) + 1;
        boolean success = (diceRoll + rehearsalBonus) >= budget;
        return payout(success);
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
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
}
