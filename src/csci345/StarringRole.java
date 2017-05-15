package csci345;

public class StarringRole extends Role implements Comparable<StarringRole> {
	
	private int bonus;
	
	public StarringRole(String name, String description, int level) {
		super(name, description, level);
		bonus = 0;
	}
	
	public Payout payout(boolean success) {
		return new Payout(0, success ? 2 : 0, success);
	}
	
	public Payout wrapScenePayout() {
		return new Payout(bonus, 0);
	}

	@Override
	public int compareTo(StarringRole o) {
		return this.minRankNeeded - ((StarringRole) o).minRankNeeded;
	}
	
	public void addBonus(int bonus) {
		this.bonus += bonus;
	}
}
