package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {

	private void tempInit() {
		System.out.println("Init Train Station controller");
		Room room = new Room(0, 0, this.getWidth(), this.getHeight(), model.Room.getRoom("Train Station"));
		add(room, 0);
	}
	
	public Board(model.Board model) {
		setBounds(0,0,1200,900);
		
//		addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println(e.getPoint());
//			}
//		});
		
		tempInit();
	}
}
