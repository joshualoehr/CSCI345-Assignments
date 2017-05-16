package csci345;

public class Deadwood {
	
	private static final String USAGE = "Usage: Deadwood.java numPlayers";
	
	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println(USAGE);
			return;
		}
		
		int numPlayers;
		try {
			numPlayers = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			return;
		}
		
		Board board = Board.getInstance(numPlayers);
		while (board.getDays() <= board.getMaxDays()) {
			board.processInput();
		}
		board.endGame();
	}
}
