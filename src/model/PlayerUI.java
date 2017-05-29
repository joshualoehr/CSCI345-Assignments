package model;

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
	
	/* Print a message to stdout */
	public static void output(String str, Object... params) {
		System.out.println(String.format(str, params));
	}
}
