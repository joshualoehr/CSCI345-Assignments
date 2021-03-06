package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class ActionValidator {

	/* ERROR HANDLING */

	private static final int VALID_ACTION = 0;
	public static final String NO_ERR     = "valid";
	
	private static final String TURN_ERR   = "You can't do that this turn";
	
	private static final String MOV_ERR_1  = "Cannot move when you are working a role";
	private static final String MOV_ERR_2  = "Cannot move to a non-adjacent room";
	private static final String[] MOV_ERR  = new String[]{ MOV_ERR_1, MOV_ERR_2, TURN_ERR };

	private static final String REH_ERR_1  = "Cannot rehearse without working a role";
	private static final String REH_ERR_2  = "You are already at max rehearsal chips - cannot rehearse more";
	private static final String[] REH_ERR  = new String[]{ REH_ERR_1, REH_ERR_2, TURN_ERR };

	private static final String ACT_ERR_1  = "Cannot act without working a role";
	private static final String[] ACT_ERR  = new String[]{ ACT_ERR_1, TURN_ERR };

	private static final String UPGR_ERR_1 = "Must be in the Casting Room to upgrade rank";
	private static final String UPGR_ERR_2 = "Cannot upgrade to a lower rank";
	private static final String UPGR_ERR_3 = "Insufficient dollars to upgrade";
	private static final String UPGR_ERR_4 = "Insufficient credits to upgrade";
	private static final String[] UPGR_ERR = new String[]{ UPGR_ERR_1, UPGR_ERR_2, UPGR_ERR_3, UPGR_ERR_4, TURN_ERR };

	private static final String WORK_ERR_1 = "This room does not have any roles";
	private static final String WORK_ERR_2 = "That role does not exist in this room";
	private static final String WORK_ERR_3 = "You already have a role";
	private static final String WORK_ERR_4 = "That role is already occupied";
	private static final String WORK_ERR_5 = "You do not have a high enough rank to take that role";
	private static final String WORK_ERR_6 = "The scene has already wrapped";
	private static final String[] WORK_ERR = new String[]{ WORK_ERR_1, WORK_ERR_2, WORK_ERR_3, WORK_ERR_4, WORK_ERR_5, WORK_ERR_6, TURN_ERR };

	public static String getErrorMsg(String action, int code) {
		if (code == 0) return NO_ERR;
		
		code--; // shift the code so it acts like an index

		switch (action) {
		case "move": 	 return MOV_ERR[code];
		case "rehearse": return REH_ERR[code];
		case "act": 	 return ACT_ERR[code];
		case "upgrade":  return UPGR_ERR[code];
		case "work": 	 return WORK_ERR[code];
		default: return "Undefined action: " + action;
		}
	}


	private static ActionValidator instance = new ActionValidator();

	public static ActionValidator getInstance() {
		return instance;
	}


	private HashMap<String, BiFunction<Player, List<String>, Integer>> validators;

	private ActionValidator() {
		BiFunction<Player, List<String>, Integer> alwaysValid 
			= (player, params) -> VALID_ACTION;
			
		// Initialize validators map
		validators = new HashMap<String, BiFunction<Player, List<String>, Integer>>();
		validators.put("who", alwaysValid);
		validators.put("where", alwaysValid);
		validators.put("end", alwaysValid);
		validators.put("move", this::canMove);
		validators.put("rehearse", this::canRehearse);
		validators.put("act", this::canAct);
		validators.put("upgrade", this::canUpgrade);
		validators.put("work", this::canTakeRole);
	}

	public String validAction(Player player, String rawInput) {
		ArrayList<String> inputs = new ArrayList<String>(Arrays.asList(rawInput.split(" ")));
		String cmd = inputs.remove(0);

		BiFunction<Player, List<String>, Integer> validator = validators.get(cmd);
		int code = validator.apply(player, inputs);

		return getErrorMsg(cmd, code);
	}
	
	/* VALIDATOR FUNCTIONS */

	private int canMove(Player player, List<String> params) {
		if (player.has(Player.MOVED, Player.REHEARSED, Player.ACTED, Player.TAKEN_ROLE))
			return 3;
		
		/* Cannot move if currently in a role */
		if (player.getRole() != null) {
			return 1;
		}

		String roomName = String.join(" ", params);
		Room target = Room.getRoom(roomName);
		
		/* Must be adjacent to the target room */
		if (!player.getRoom().getAdjacentRooms().contains(target)) {
			return 2;
		}

		return VALID_ACTION;
	}

	private int canRehearse(Player player, List<String> params) {
		/* Must be in a scene room & have a role to rehearse */
		if (player.getRole() == null) {
			return 1;
		}
		
		if (player.has(Player.REHEARSED, Player.TAKEN_ROLE, Player.ACTED))
			return 3;

		/* Rank + rehearsal chips must not exceed the scene budget */
		int max = ((SceneRoom) player.getRoom()).getScene().getBudget();
		if (player.getRehearsalChips() >= max) {
			return 2;
		}

		return VALID_ACTION;
	}

	private int canAct(Player player, List<String> params) {
		if (player.has(Player.ACTED, Player.REHEARSED, Player.TAKEN_ROLE))
			return 2;
		
		/* Must have a role to act */
		if (player.getRole() == null) {
			return 1;
		}

		return VALID_ACTION;
	}

	private int canUpgrade(Player player, List<String> params) {		
		/* Must be in Casting Office to upgrade */
		if (!(player.getRoom() instanceof CastingRoom)) {
			return 1;
		}

		String currencyType = params.get(0);
		int desiredRank     = Integer.parseInt(params.get(1));
		int playerBalance   = (currencyType.equals("$")) ? player.getDollars() : player.getCredits();
		int upgradeCost     = CastingRoom.costUpgrade(desiredRank, currencyType);

		/* Cannot upgrade to a lower/equivalent rank */
		if (desiredRank <= player.getRank())
			return 2;

		/* Must have enough credits/dollars to upgrade */
		if (playerBalance < upgradeCost)
			return (currencyType.equals("$")) ? 3 : 4;

		return VALID_ACTION;
	}

	private int canTakeRole(Player player, List<String> params) {				
		/* Player must be in a SceneRoom to take/view role */
		if (!(player.getRoom() instanceof SceneRoom)) {
			return 1;
		}
		
		if (params.size() == 0) {
			return VALID_ACTION;
		}
		
		if (player.has(Player.TAKEN_ROLE))
			return 7;
		
		String roleName = String.join(" ", params);
		Role targetRole = Role.getRole(roleName);

		/* The role must exist in the room */
		if (targetRole == null || !player.getRoom().getAllRoles().contains(targetRole)) {
			System.out.println(targetRole + " not in " + player.getRoom());
			return 2;
		}
		
		/* Cannot work a role if the scene has already wrapped */
		if (((SceneRoom)player.getRoom()).getCurrShotCounter() == 0) {
			return 6;
		}

		/* The player must not currently be working a role */
		if (player.getRole() != null) {
			return 3;
		}

		/* The target role must not be occupied by anyone else */
		if (targetRole.isOccupied()) {
			return 4;
		}

		/* The player must have sufficient rank to take the role */
		if (targetRole.getMinRank() > player.getRank()) {
			return 5;
		}

		return VALID_ACTION;
	}
}
