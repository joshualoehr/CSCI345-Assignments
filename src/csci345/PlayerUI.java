package csci345;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlayerUI {
	
	private static final String PROMPT = "> ";
	
	/* Error Messages */
	private static final String CMD_ERR = "Invalid command: %s";
	private static final String PARAM_ERR_1 = "Command requires an extra parameter: %s";
	private static final String PARAM_ERR_2 = "Command requires two extra parameters: %s";
	private static final String UPGR_ERR_1 = "Upgrade must be followed by '$' or 'cr'";
	private static final String UPGR_ERR_2 = "Upgrade %s must be followed by an int";
	private static final String NUM_PLYR_ERR_1 = "Please enter a number";
	private static final String NUM_PLYR_ERR_2 = "This game supports 2 to 8 players";
	
	private static final List<String> VALID_CMDS = new ArrayList<String>(Arrays.asList(
			"who","where","move","work","upgrade","rehearse","act","end"
	));
	
	private static Scanner scan = new Scanner(System.in);
	
	public static String getInput() { 
		System.out.print(PROMPT);
		
		String[] rawInputs = scan.nextLine().split(" ");
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(rawInputs));
		input.forEach(str -> str.toLowerCase());
		
		if (!validateInput(input))
			return getInput();
		
		return String.join(" ", input);
	}
	
	private static boolean validateInput(List<String> input) { 
		/* Validate a command was supplied */
		if (input.size() < 1) {
			return false;
		}
		
		String cmd = input.get(0);
		
		/* Validate the command exists */
		if (!VALID_CMDS.contains(cmd)) {
			output(CMD_ERR, cmd);
			return false;
		}
		
		/* Validate parameters for commands which require them */
		switch (input.get(0)) {
		case "move": 
			if (input.size() < 2) {
				output(PARAM_ERR_1, cmd);
				return false;
			}
			break;
		case "upgrade":
			if (input.size() < 2) {
				output(PARAM_ERR_2, cmd);
				return false;
			}
			
			if (!(input.get(1).equals("$") || input.get(1).equals("cr"))) {
				output(UPGR_ERR_1);
				return false;
			}
			
			if (input.size() < 3) {
				output(PARAM_ERR_2, cmd);
				return false;
			}
			
			try {
				Integer.parseInt(input.get(2));
			} catch (NumberFormatException e) {
				output(UPGR_ERR_2, input.get(1));
				return false;
			}
			break;
		}
		
		return true;
	}
	
	public static void output(String str, Object... params) {
		System.out.println(String.format(str, params));
	}
	
	public static int getPlayerCount() { 
		String input;
		int numPlayers = 0;
		
		while (numPlayers < 2 || numPlayers > 8) {
			System.out.print("How many players in this game? ");
			
			input = scan.next();
			try {
				numPlayers = Integer.parseInt(input);
				if (numPlayers < 2 || numPlayers > 8)
					output(NUM_PLYR_ERR_2);
			} catch (NumberFormatException e) {
				numPlayers = 0;
				output(NUM_PLYR_ERR_1);
			}
			
			System.out.println();
		}
		
		return numPlayers; 
	}
}
