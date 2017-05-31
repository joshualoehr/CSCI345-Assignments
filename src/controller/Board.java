package controller;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {

	private void tempInit() {
		System.out.println("Init Train Station controller");
		Room room = new Room(0, 0, 1200, 900, model.Room.getRoom("Train Station"));
		add(room, 0);
	}
	
	public Board(model.Board model) {
		setBounds(0,0,1200,900);
		tempInit();
	}
}
