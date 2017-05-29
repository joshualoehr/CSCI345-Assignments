

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
		
		if (numPlayers < 2 || numPlayers > 8) {
			System.out.println("numPlayers must be between 2 and 8");
			return;
		}
		
		model.Board board = model.Board.getInstance(numPlayers);
		while (board.getDays() <= board.getMaxDays()) {
			board.processInput();
		}
		board.endGame();
	}
}
