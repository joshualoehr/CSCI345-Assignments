import java.io.IOException;

import javax.swing.JFrame;

public class Deadwood {
	
	private static final String USAGE = "Usage: Deadwood.java numPlayers";
	private static final String BOARD_IMG = "assets/board.jpg";
	
	@SuppressWarnings("serial")
	private static class DeadwoodWindow extends JFrame {
		
		view.Board 		 view;
		controller.Board controller;
		
		public DeadwoodWindow(model.Board model, String boardFile) throws IOException {
			view  	   = new view.Board(model, boardFile);
			controller = new controller.Board(model);
			
			setTitle("Deadwood");
			setPreferredSize(view.getIconDimension());
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			getContentPane().add(view);
			getContentPane().add(controller);
			pack();
			
			setVisible(true);
		}
	}
	
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
		DeadwoodWindow window;
		
		try {
			window = new DeadwoodWindow(board, BOARD_IMG);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		board.startGame();
		
		
//		while (board.getDays() <= board.getMaxDays()) {
//			board.processInput();
//		}
//		board.endGame();
	}
}
