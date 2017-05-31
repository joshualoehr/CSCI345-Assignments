import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import view.ControlPanel;

public class Deadwood {
	
	private static final String USAGE = "Usage: Deadwood.java numPlayers";
	private static final String BOARD_IMG = "assets/board.jpg";
	
	@SuppressWarnings("serial")
	private static class DeadwoodWindow extends JFrame {
		
		view.Board 		 view;
		controller.Board controller;
		ControlPanel     ctrl;
		
		public DeadwoodWindow(model.Board model, String boardFile) {
			view  	   = new view.Board(model, boardFile);
			controller = new controller.Board(model);
			ctrl       = new ControlPanel(1200, 0, 400, 900, model);
			
			setTitle("Deadwood");
			
			Dimension size = view.getIconDimension();
			size.width += 400;
			setPreferredSize(size);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			getContentPane().setLayout(null);
			getContentPane().add(view);
			getContentPane().add(controller);
			getContentPane().add(ctrl);
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
		DeadwoodWindow window = new DeadwoodWindow(board, BOARD_IMG);
		board.startGame();
		
		
//		while (board.getDays() <= board.getMaxDays()) {
//			board.processInput();
//		}
//		board.endGame();
	}
}
