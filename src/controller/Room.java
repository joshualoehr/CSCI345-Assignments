package controller;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

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

	private void tempInit(model.Room room) {
		System.out.println("Init extra roles controllers");
    	Role r;
    	r = new Role(114, 227, 46, 46, model.Role.getRole("Crusty Prospector"));
    	add(r, new Integer(1));
    	
    	r = new Role(51, 268, 46, 46, model.Role.getRole("Dragged by Train"));
    	add(r, new Integer(1));
    	
    	System.out.println("Init scene controller");
    	Scene s;
    	s = new Scene(21, 69, 205, 115, room);
    	add(s, new Integer(2));
    }
	
	private JLabel clickArea;
	
	public Room(int x, int y, int w, int h, model.Room r) {
		setBounds(x, y, w, h);
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
		
		tempInit(r);
	}
}
