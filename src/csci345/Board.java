package csci345;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {
	
	public static void main(String args[]) {
		Board board = Board.getInstance();
		while (board.getDays() <= board.getMaxDays()) {
			board.processInput();
		}
	}
	
	private static Board instance = new Board();
	
	public static Board getInstance() {
		return instance;
	}
	
	private Board() {
		// Setup Scenes
		// TODO setup the scenes
		
		// Setup Players
		playerQueue = new LinkedList<Player>();
		numPlayers = PlayerUI.getPlayerCount();
		
		for (int i = 0; i < numPlayers; i++) {
			playerQueue.add(new Player("Player"+i, Room.getRoom("Trailer Room")));
		}
		
		days = 0;
		maxDays = (numPlayers > 3) ? 4 : 3;
		
		activePlayer = playerQueue.removeFirst();
	}

	private int numPlayers;
	private Player activePlayer;
	private LinkedList<Player> playerQueue;
	private int sceneCardTotal;
	private ArrayList<Scene> sceneCardList;
	private int days;
	private int maxDays;
	
	public void processInput() {
		ActionValidator validator = ActionValidator.getInstance();
		String input;
		
		do {
			input = PlayerUI.getInput();
		} while (!validator.validAction(activePlayer, input));
		
		List<String> inputs = Arrays.asList(input);
		String cmd = inputs.remove(0);
		
		switch (cmd) {
		case "who":
			PlayerUI.output("%s", activePlayer);
			break;
		case "where":
			Room room = activePlayer.getRoom();
			StringBuilder output = new StringBuilder();
			output.append(room);
			if (room instanceof SceneRoom) {
				Scene scene = ((SceneRoom) room).getScene();
				if (scene == null) {
					output.append(", scene wrapped");
				} else {
					output.append(String.format(", shooting %s", scene));
				}
			}
			PlayerUI.output(output.toString());
			break;
		case "move":
			Room target = Room.getRoom(String.join(" ", inputs));
			activePlayer.move(target); 
			break;
		case "rehearse": 
			activePlayer.rehearse(); 
			break;
		case "act": 
			Payout payout = activePlayer.act();
			PlayerUI.output("%s you got %s", 
					payout.wasSuccessful() ? "Success!" : "Failure.", payout);
			break;
		case "upgrade": 
			String currency = inputs.get(0);
			int desiredRank = Integer.parseInt(inputs.get(1));
			activePlayer.upgrade(desiredRank, currency);
			break;
		case "work":
			Role role = Role.getRole(String.join(" ", inputs.get(0)));
			activePlayer.takeRole(role);
			break;
		case "end":
			playerQueue.add(activePlayer);
			activePlayer = playerQueue.removeFirst();
			break;
		}
	}
	
	public void setupNewDay() {}
	
	public void endGame() {}
	
	public int getDays() {
		return days;
	}
	
	public int getMaxDays() {
		return maxDays;
	}
}
