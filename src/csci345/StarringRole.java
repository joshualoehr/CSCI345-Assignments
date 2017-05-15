package csci345;

public class StarringRole extends Role {
	
	public StarringRole(String name, String description, int level) {
		super(name, description, level);
	}
	
	public Payout payout(boolean success) {
		return new Payout(0, success ? 2 : 0, success);
	}
	
	public Payout wrapScenePayout() {
		return new Payout(0, 0);
	}
}
