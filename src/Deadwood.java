import java.io.IOException;

import javax.swing.JFrame;

public class Deadwood {
	
	private static final String USAGE = "Usage: Deadwood.java numPlayers";
	private static final String BOARD_IMG = "assets/board.jpg";
	
	@SuppressWarnings("serial")
	private static class DeadwoodWindow extends JFrame {
		
		model.Board      boardModel;
		view.Board 		 view;
		controller.Board controller;
		
		public DeadwoodWindow(int numPlayers, String boardFile) throws IOException {
			boardModel = model.Board.getInstance(numPlayers);
			view  	   = new view.Board(boardModel, boardFile);
			controller = new controller.Board(boardModel);
			
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
		
		model.Board board;
		DeadwoodWindow window;
		
		try {
			window = new DeadwoodWindow(numPlayers, BOARD_IMG);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		board = model.Board.getInstance(numPlayers);
		
//		while (board.getDays() <= board.getMaxDays()) {
//			board.processInput();
//		}
//		board.endGame();
	}
}
