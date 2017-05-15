package csci345;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	private void temporaryInit() {
		// Temporary Initialization
		Room trailerRoom = new TrailerRoom("Trailer Room");
		Room castingRoom = new CastingRoom("Casting Room");
		Room trainRoom = new SceneRoom("Train Station");
		Room jailRoom = new SceneRoom("Jail");
		
		trailerRoom.setAdjacentRooms(castingRoom, trainRoom, jailRoom);
		castingRoom.setAdjacentRooms(trailerRoom, trainRoom, jailRoom);
		trainRoom.setAdjacentRooms(trailerRoom, castingRoom, jailRoom);
		jailRoom.setAdjacentRooms(trailerRoom, castingRoom, trainRoom);
		
		StarringRole role1 = new StarringRole("Defrocked Priest", "Look out below!", 2);
		ArrayList<StarringRole> roles = new ArrayList<StarringRole>(Arrays.asList(role1));
		Scene scene = new Scene(4, 7, "Evil Wears a Hat", 
				"Calhoun is separated from the group...", roles);
		((SceneRoom) trainRoom).setScene(scene);
		sceneCardTotal = 1;
		sceneCardList = new LinkedList<Scene>(Arrays.asList(scene));
	}
	
	private Board() {
		
		InfoParser.readBoard();
		sceneCardList = InfoParser.readCards();
		Collections.shuffle(sceneCardList);
		
		// temporaryInit();
		
		for (Room room : Room.getAllRooms()) {
			if (room instanceof SceneRoom)
				((SceneRoom) room).setScene(sceneCardList.removeFirst());
		}
		
		for (Room room : Room.getAllRooms()) {
			StringBuilder sb = new StringBuilder();
			sb.append(room);
			if (room instanceof SceneRoom)
				sb.append(String.format(" with %s", ((SceneRoom) room).getScene()));
			System.out.println(sb.toString());
		}
		
		
		
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
	private LinkedList<Scene> sceneCardList;
	private int days;
	private int maxDays;
	
	public void processInput() {
		ActionValidator validator = ActionValidator.getInstance();
		String input;
		
		do {
			input = PlayerUI.getInput();
		} while (!validator.validAction(activePlayer, input));
		
		ArrayList<String> inputs = new ArrayList<String>(Arrays.asList(input));
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
			PlayerUI.output("%s you got %s.", 
					payout.wasSuccessful() ? "Success!" : "Failure.", payout);
			
			if (payout.wasSuccessful()) {
				SceneRoom sceneRoom = (SceneRoom) activePlayer.getRoom();
				List<Payout> wrapPayouts = new ArrayList<Payout>();
				
				if (sceneRoom.decrementShotCounter()) {
					wrapPayouts = sceneRoom.wrapScene();
				}
				
				PlayerUI.output("Scene wrapped, all starring roles paid");
			}
			
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
