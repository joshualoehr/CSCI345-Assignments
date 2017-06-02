import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.ControlPanel;

public class Deadwood {
	
	private static final String USAGE = "Usage: Deadwood.java numPlayers";
	private static final String BOARD_IMG = "assets/board.jpg";
	
	@SuppressWarnings("serial")
	private static class DeadwoodWindow extends JFrame implements Observer {
		
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
			
			model.addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof model.Board) {
				model.Board b = (model.Board) arg;
				int winningScore = 0;
				ArrayList<model.Player> winners = new ArrayList<model.Player>();
				
				StringBuilder sb = new StringBuilder();
				sb.append("Game Over!\n\nScores:\n");
				for (model.Player p : b.getPlayers()) {
					int score = p.getScore();
					sb.append(p.getName() + ": " + score + "\n");
					
					if (winningScore == 0) {
						winners.add(p);
						winningScore = score;
					} else if (score == winningScore) {
						winners.add(p);
					} else if (score > winningScore) {
						winners = new ArrayList<model.Player>();
						winners.add(p);
						winningScore = score;
					}
				}
				sb.append("\n");
				
				boolean plural = winners.size() > 1;
				String winnersStr = winners.toString();
				winnersStr = winnersStr.substring(1, winnersStr.length()-1);
				sb.append(winnersStr);
				sb.append(plural ? " win!" : " wins!");
				
				JOptionPane.showMessageDialog(this, sb.toString());
				System.exit(0);
			}
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
	}
}
