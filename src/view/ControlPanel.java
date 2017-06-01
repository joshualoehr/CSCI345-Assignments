package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.ActionValidator;

public class ControlPanel extends JLayeredPane implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Player) {
			playersPanel.update((model.Player) arg);
			buttonsPanel.update((model.Player) arg);
		} else if (arg instanceof String) {
			outputArea.append((String) arg + "\n");
		}
	}
	
	private class PlayerPanel extends JLayeredPane {
		
		private static final String NO_PLAYER_IMG = "assets/dice/no_player.png";
		private static final int ROWS = 2;
		private static final int COLS = 4;
		
		private void update(model.Player activePlayer) {
			int r = activePlayer.getPlayerNum() / COLS;
			int c = activePlayer.getPlayerNum() % COLS;
			if (activeHighlight != null) activeHighlight.setVisible(false);
			activeHighlight = highlights[r][c];
			activeHighlight.setVisible(true);
		}
		
		private JLabel activeHighlight;
		private JLabel[][] highlights;
		private JLabel[][] players;
		private ImageIcon[][] icons;
		
		public PlayerPanel(int x, int y, int width, int height, List<String> playerImgs) {
			super();
			setLayout(null);
			setBounds(x, y, width, height);
			
			highlights = new JLabel[ROWS][COLS];
			players = new JLabel[ROWS][COLS];
			icons = new ImageIcon[ROWS][COLS];
			
			int labelWidth = width / COLS;
			int labelHeight = height / ROWS;
			
			for (int r = 0; r < ROWS; r++) {
				for (int c = 0; c < COLS; c++) {
					int num = (COLS * r) + c;
					if (num < playerImgs.size()) {
						icons[r][c] = new ImageIcon(playerImgs.get(num));
					} else {
						icons[r][c] = new ImageIcon(NO_PLAYER_IMG);
					}
					
					players[r][c] = new JLabel(icons[r][c]);
					int imgWidth = 46;
					int imgHeight = 46;
					int labelX = ((labelWidth - imgWidth)/2) + (labelWidth * c);
					int labelY = ((labelHeight - imgHeight)/2) + (labelHeight * r);
					players[r][c].setBounds(labelX, labelY, imgWidth, imgHeight);
					add(players[r][c], new Integer(1));
					
					highlights[r][c] = new JLabel();
					highlights[r][c].setVisible(false);
					highlights[r][c].setBounds(labelX - 5, labelY - 5, imgWidth + 10, imgHeight + 10);
					highlights[r][c].setBackground(Color.YELLOW);
					highlights[r][c].setOpaque(true);
					add(highlights[r][c], new Integer(0));
				}
			}
			
			activeHighlight = highlights[0][0];
			activeHighlight.setVisible(true);
		}
	}

	private class ButtonPanel extends JPanel {
		
		private void update(model.Player activePlayer) {
			ActionValidator validator = ActionValidator.getInstance();
			actBtn.setEnabled(validator.validAction(activePlayer, "act"));
			rehBtn.setEnabled(validator.validAction(activePlayer, "rehearse"));
			upgBtn.setEnabled(activePlayer.getRoom().equals(model.Room.getRoom("Casting Office")));
		}
		
		private final Dimension GAP = new Dimension(10, 10);
		private JButton actBtn;
		private JButton rehBtn;
		private JButton upgBtn;
		private JButton endBtn;
		
		public ButtonPanel(int x, int y, int width, int height, model.Board board) {
			super();
			setLayout(null);
			setBounds(x, y, width, height);
			
			Dimension btnRow1Dim = new Dimension((width/3) - 2 * GAP.width, (2 * height / 3) - 2 * GAP.height);
			Dimension btnRow2Dim = new Dimension(width - 2 * GAP.width, (height / 3) - GAP.height);
			
			actBtn = new JButton("Act");
			actBtn.setLocation(GAP.width, GAP.height);
			actBtn.setSize(btnRow1Dim);
			actBtn.setEnabled(false);
			actBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.processInput("act");
				}
			});
			
			rehBtn = new JButton("Rehearse");
			rehBtn.setLocation(3*GAP.width + btnRow1Dim.width, GAP.height);
			rehBtn.setSize(btnRow1Dim);
			rehBtn.setMargin(new Insets(0, 0, 0, 0));
			rehBtn.setEnabled(false);
			rehBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.processInput("rehearse");
				}
			});
			
			upgBtn = new JButton("Upgrade");
			upgBtn.setLocation(5*GAP.width + 2*btnRow1Dim.width, GAP.height);
			upgBtn.setSize(btnRow1Dim);
			upgBtn.setEnabled(false);
			upgBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			
			endBtn = new JButton("End");
			endBtn.setLocation(GAP.width, 3*GAP.height + btnRow1Dim.height);
			endBtn.setSize(btnRow2Dim);
			endBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.processInput("end");
				}
			});
			
			add(actBtn);
			add(rehBtn);
			add(upgBtn);
			add(endBtn);
		}
	}
	
	private JLabel playersLabel;
	private PlayerPanel playersPanel;
	private ButtonPanel buttonsPanel;
	private JScrollPane scrollPane;
	private JTextArea outputArea;
	
	public ControlPanel(int x, int y, int width, int height, model.Board b) {
		setLayout(null);
		setBounds(x, y, width, height);
		
		Point p;
		Dimension d;
		Dimension gap = new Dimension(width, 25);
		
		p = new Point(5, 5);
		d = new Dimension(width-10, 20);
		playersLabel = new JLabel("Players: ");
		playersLabel.setBounds(p.x, p.y, d.width, d.height);
		add(playersLabel, new Integer(0));
		p.y += d.height + gap.height;
		
		p.x = 20;
		d = new Dimension(width-40, 120);
		playersPanel = new PlayerPanel(p.x, p.y, d.width, d.height, b.getPlayerImgs());
		add(playersPanel, new Integer(0));
		p.y += d.height + gap.height;
		
		d = new Dimension(width-40, 200);
		buttonsPanel = new ButtonPanel(p.x, p.y, d.width, d.height, b);
		add(buttonsPanel, new Integer(0));
		p.y += d.height + 2*gap.height;
		
		d = new Dimension(width-40, 400);
		outputArea = new JTextArea(5, 20);
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		scrollPane = new JScrollPane(outputArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(p.x, p.y, d.width, d.height);
		add(scrollPane, new Integer(0));
		
		b.addObserver(this);
	}
	
}
