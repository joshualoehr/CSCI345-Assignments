package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Room extends JLayeredPane {

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
	
	public Room(int x, int y, int w, int h, model.Room r) {
		setBounds(x, y, w, h);
		setOpaque(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO: handle room click
				System.out.println("Click room " + r);
			}
		});
		
		tempInit(r);
	}
}
