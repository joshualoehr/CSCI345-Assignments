package controller;

import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {
	
	private model.Board board;
	
	public Board(model.Board board) {
		this.board = board;
		setBounds(0,0,1200,900);
		
		HashMap<String, Rectangle> roomData = model.InfoParser.getRoomPositions();
        roomData.forEach((name, bounds) -> initRoom(name, bounds));
	}
	
	private void initRoom(String name, Rectangle bounds) {
    	Room room = new Room(bounds.x, bounds.y, bounds.width, bounds.height, model.Room.getRoom(name), board);
    	add(room, 0);
    }
}
