package controller;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import model.InfoParser.RoleData;

@SuppressWarnings("serial")
public class Room extends JLayeredPane {
	
	private static HashMap<String, Rectangle> roomBounds;
	static {
		roomBounds = new HashMap<String, Rectangle>();
		roomBounds.put("Train Station", new Rectangle(0, 0, 240, 440));
		roomBounds.put("Jail", new Rectangle(260, 10, 340, 220));
		roomBounds.put("General Store", new Rectangle(220, 260, 380, 180));
		roomBounds.put("Main Street", new Rectangle(610, 10, 580, 225));
		roomBounds.put("Trailers", new Rectangle(990, 260, 200, 180));
		roomBounds.put("Saloon", new Rectangle(610, 210, 370, 230));
		roomBounds.put("Bank", new Rectangle(610, 460, 380, 180));
		roomBounds.put("Church", new Rectangle(610, 660, 320, 230));
		roomBounds.put("Hotel", new Rectangle(940, 460, 250, 250));
		roomBounds.put("Ranch", new Rectangle(230, 460, 370, 220));
		roomBounds.put("Casting Office", new Rectangle(10, 460, 205, 200));
		roomBounds.put("Secret Hideout", new Rectangle(10, 685, 590, 205));
	}
	
	private static HashMap<String, Point> playerHolding;
	static {
		playerHolding = new HashMap<String,Point>();
		playerHolding.put("Train Station", new Point(20,230));
		playerHolding.put("Jail", new Point(400,210));
		playerHolding.put("General Store", new Point(290,400));
		playerHolding.put("Main Street", new Point(800,100));
		playerHolding.put("Trailers", new Point(1000,300));
		playerHolding.put("Saloon", new Point(780,400));
		playerHolding.put("Bank", new Point(760,600));
		playerHolding.put("Church", new Point(750,700));
		playerHolding.put("Hotel", new Point(1000,475));
		playerHolding.put("Ranch", new Point(280,650));
		playerHolding.put("Casting Office", new Point(20,625));
		playerHolding.put("Secret Hideout", new Point(260,850));
		
	}
	
	private JLabel clickArea;
	
	public Room(int x, int y, int w, int h, model.Room r) {
		setBounds(0, 0, 1200, 900);
		setOpaque(false);
		
		clickArea = new JLabel();
		clickArea.setBounds(roomBounds.get(r.toString()));
		clickArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO: handle room click
				System.out.println("Click room " + r);
			}
		});
		add(clickArea, new Integer(0));
		
		ArrayList<RoleData> extrasData =
				model.InfoParser.getExtraPartsPositions(r.getName());
		extrasData.forEach(this::initExtraRole);
		
		add(new Scene(x, y, w, h, r), new Integer(2));
	}
	
	private void initExtraRole(RoleData rd) {
		Rectangle b = rd.getBounds();
		add(new Role(b.x, b.y, b.width, b.height, rd.getRole()), new Integer(1));
	}
}
