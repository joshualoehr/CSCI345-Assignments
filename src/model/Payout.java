package model;

public class Payout {
	
	private boolean success;
	private int dollars;
	private int credits;
	
	public Payout(int dollars, int credits, boolean success) {
		this.dollars = dollars;
		this.credits = credits;
		this.success = success;
	}
	
	public Payout(int dollars, int credits) {
		this.dollars = dollars;
		this.credits = credits;
		this.success = false;
	}
	
	@Override
	public String toString() {
		if (dollars == 0 && credits == 0) {
			return "nothing";
		}
		return String.format("$%d, %dcr", dollars, credits);
	}
	
	public boolean wasSuccessful() {
		return success;
	}
	
	public int getDollars() {
		return dollars;
	}
	
	public int getCredits() {
		return credits;
	}

}
