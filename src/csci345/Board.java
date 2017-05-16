package csci345;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Board {
	
	private static final String BOARD_FILE = "src/csci345/board.xml";
	private static final String CARDS_FILE = "src/csci345/cards.xml";
	
	private static Board instance;
	public static Board getInstance(int numPlayers) {
		if (instance == null) {
			instance = new Board(numPlayers);
		}
		return instance;
	}
	
	private void temporaryInit() {
		
		
	}
	
	private Board(int numPlayers) {
		// Initialize Rooms
		InfoParser.readBoard(BOARD_FILE);
		
		// Initialize Scenes
		sceneCardList = InfoParser.readCards(CARDS_FILE);
		Collections.shuffle(sceneCardList);		
				
		// Initialize Players
		playerQueue = new LinkedList<Player>();
		this.numPlayers = numPlayers;
		for (int i = 0; i < numPlayers; i++) {
			playerQueue.add(new Player("Player " + (i+1)));
		}
		
		setupNewDay();
		
		activePlayer = playerQueue.removeFirst();
		activePlayer.startTurn();
		
		temporaryInit();
	}

	private int numPlayers;
	private Player activePlayer;
	private LinkedList<Player> playerQueue;
	private int sceneCardTotal = 0;
	private LinkedList<Scene> sceneCardList;
	private int days = 0;
	
	public void processInput() {
		ActionValidator validator = ActionValidator.getInstance();
		String input;
		
		do {
			input = PlayerUI.getInput();
		} while (!validator.validAction(activePlayer, input));
		
		ArrayList<String> inputs = new ArrayList<String>(Arrays.asList(input.split(" ")));
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
			output.append(String.format(" (connects to %s)", room.getAdjacentRooms()));
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
			PlayerUI.output("%s you got %s.", 
					payout.wasSuccessful() ? "Success!" : "Failure.", payout);
			
			if (payout.wasSuccessful()) {
				SceneRoom sceneRoom = (SceneRoom) activePlayer.getRoom();
				
				if (sceneRoom.decrementShotCounter()) {
					boolean bonusPaid = sceneRoom.wrapScene();
					if (bonusPaid) {
						PlayerUI.output("Scene wrapped, bonus payouts distributed");
					} else {
						PlayerUI.output("Scene wrapped, no bonuses given");
					}
					
					if (--sceneCardTotal == 1) {
						setupNewDay();
						
						playerQueue.add(activePlayer);
						activePlayer = playerQueue.removeFirst();
						activePlayer.startTurn();
					}
				}
			}
			break;
		case "upgrade": 
			String currency = inputs.get(0);
			int desiredRank = Integer.parseInt(inputs.get(1));
			activePlayer.upgrade(desiredRank, currency);
			break;
		case "work":
			if (inputs.size() == 0) {
				PlayerUI.output("Roles here: %s", ((SceneRoom) activePlayer.getRoom()).getAllRoles());
				break;
			}
			String roleName = String.join(" ", inputs);
			Role role = Role.getRole(roleName);
			activePlayer.takeRole(role);
			break;
		case "end":
			playerQueue.add(activePlayer);
			activePlayer = playerQueue.removeFirst();
			activePlayer.startTurn();
			break;
		}
	}
	
	private void distributeScenes() {
		sceneCardTotal = 0;
		for (Room room : Room.getAllRooms()) {
			if (room instanceof SceneRoom) {
				((SceneRoom) room).resetCurrShotCounter();
				((SceneRoom) room).setScene(sceneCardList.removeFirst());
				sceneCardTotal++;
			}
		}
	}
	
	public void setupNewDay() {
		if (++days > getMaxDays()) {
			return;
		}
		
		Room trailers = Room.getRoom("Trailers");
		for (Player player : playerQueue) {
			player.setRoom(trailers);
			player.takeRole(null);
		}
		distributeScenes();
	}
	
	public void endGame() {
		ArrayList<Player> winners = new ArrayList<Player>();
		for (Player player : playerQueue) {
			if (winners.size() == 0 || player.getScore() > winners.get(0).getScore()) {
				winners = new ArrayList<Player>(Arrays.asList(player));
			} else if (player.getScore() == winners.get(0).getScore()) {
				winners.add(player);
			}
		}
		
		String winnerStr = winners.toString();
		winnerStr = winnerStr.substring(1, winnerStr.length()-1);
		PlayerUI.output("Game Over! %s wins!", winnerStr);
	}
	
	public int getDays() {
		return days;
	}
	
	public int getMaxDays() {
		return (numPlayers > 3) ? 4 : 3;
	}
}
