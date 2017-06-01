package view;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Player extends JPanel implements Observer {

	private static HashMap<String, Point> playerHolding;
	static {
		playerHolding = new HashMap<String,Point>();
		playerHolding.put("Train Station", new Point(12,218));
		playerHolding.put("Jail", new Point(407,205));
		playerHolding.put("General Store", new Point(290,385));
		playerHolding.put("Main Street", new Point(790,75));
		playerHolding.put("Trailers", new Point(1025,360));
		playerHolding.put("Saloon", new Point(725,227));
		playerHolding.put("Bank", new Point(615,595));
		playerHolding.put("Church", new Point(730,680));
		playerHolding.put("Hotel", new Point(1015,460));
		playerHolding.put("Ranch", new Point(280,630));
		playerHolding.put("Casting Office", new Point(20,625));
		playerHolding.put("Secret Hideout", new Point(250,840));
	}
	private Point getRoomLoc(String roomName) {
		Point p = playerHolding.get(roomName);
		return new Point(p.x + player.getPlayerNum() * 10, p.y);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Room) {
			setLocation(getRoomLoc(((model.Room) arg).getName()));
		}
	}
	
	private model.Player player;
	private ImageIcon icon;
	private JLabel label;
	
	public Player(model.Player p) {
		player = p;
		
		icon = new ImageIcon(p.getImgName());
		label = new JLabel(icon);
		label.setBounds(0, 0, 46, 46);
		add(label);
		
		Point start = getRoomLoc("Trailers");
		setBounds(start.x, start.y, label.getWidth(), label.getHeight());
		
		p.addObserver(this);
	}

}
