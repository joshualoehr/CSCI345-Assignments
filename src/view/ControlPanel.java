package view;

import java.awt.Dimension;
import java.awt.Point;
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

public class ControlPanel extends JLayeredPane implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private class PlayerPanel extends JPanel {
		
		private static final String NO_PLAYER_IMG = "assets/dice/no_player.png";
		private static final int ROWS = 2;
		private static final int COLS = 4;
		
		private void update(model.Player activePlayer) {
			
		}
		
		private JLabel[][] players;
		private ImageIcon[][] icons;
		
		public PlayerPanel(int x, int y, int width, int height, List<String> playerImgs) {
			super();
			setLayout(null);
			setBounds(x, y, width, height);
			
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
					
					add(players[r][c]);
				}
			}
		}
	}

	private class ButtonPanel extends JPanel {
		
		private void update(model.Player activePlayer) {
			
		}
		
		private final Dimension GAP = new Dimension(10, 10);
		private JButton actBtn;
		private JButton rehBtn;
		private JButton upgBtn;
		private JButton endBtn;
		
		public ButtonPanel(int x, int y, int width, int height) {
			super();
			setLayout(null);
			setBounds(x, y, width, height);
			
			Dimension btnRow1Dim = new Dimension((width/3) - 2 * GAP.width, (2 * height / 3) - 2 * GAP.height);
			Dimension btnRow2Dim = new Dimension(width - 2 * GAP.width, (height / 3) - GAP.height);
			
			actBtn = new JButton("Act");
			actBtn.setLocation(GAP.width, GAP.height);
			actBtn.setSize(btnRow1Dim);
			actBtn.setEnabled(false);
			
			rehBtn = new JButton("Rehearse");
			rehBtn.setLocation(3*GAP.width + btnRow1Dim.width, GAP.height);
			rehBtn.setSize(btnRow1Dim);
			rehBtn.setEnabled(false);
			
			upgBtn = new JButton("Upgrade");
			upgBtn.setLocation(5*GAP.width + 2*btnRow1Dim.width, GAP.height);
			upgBtn.setSize(btnRow1Dim);
			upgBtn.setEnabled(false);
			
			endBtn = new JButton("End");
			endBtn.setLocation(GAP.width, 3*GAP.height + btnRow1Dim.height);
			endBtn.setSize(btnRow2Dim);
			
			add(actBtn);
			add(rehBtn);
			add(upgBtn);
			add(endBtn);
		}
	}
	private JLabel playersLabel;
	private JPanel playersPanel;
	private JPanel buttonsPanel;
	private JScrollPane scrollPane;
	private JTextArea outputArea;
	
	public ControlPanel(int x, int y, int width, int height, model.Board b) {
		setLayout(null);
		setBounds(x, y, width, height);
		
		Point p;
		Dimension d;
		Dimension gap = new Dimension(width, 20);
		
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
		buttonsPanel = new ButtonPanel(p.x, p.y, d.width, d.height);
		add(buttonsPanel, new Integer(0));
		p.y += d.height + gap.height;
		
		System.out.println(p);
		d = new Dimension(width-40, 400);
		outputArea = new JTextArea(5, 20);
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		scrollPane = new JScrollPane(outputArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(d);
		scrollPane.setLocation(p);
		add(scrollPane, new Integer(0));
	}
	
}
