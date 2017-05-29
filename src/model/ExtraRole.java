package model;

public class ExtraRole extends Role {
	
	public ExtraRole(String name, String description, int level) {
		super(name, description, level);
	}
	
	public Payout payout(boolean success) {
		return new Payout(1, success ? 1 : 0, success);
	}
	
	public Payout wrapScenePayout() {
		return new Payout(getMinRank(), 0);
	}
}
