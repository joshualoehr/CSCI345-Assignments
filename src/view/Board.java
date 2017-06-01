package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {
	
	
	
	private JLabel boardLabel;
    private ImageIcon icon;

    public Board(model.Board board, String boardFile) {
    	icon = new ImageIcon(boardFile);
    	
        boardLabel = new JLabel(icon);
        add(boardLabel, 0);
        boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        setBounds(boardLabel.getBounds());
        
        HashMap<String, Rectangle> roomData = model.InfoParser.getRoomPositions();
        roomData.forEach((name, bounds) -> initRoom(name, bounds));
        
        board.getPlayers().forEach(this::initPlayer);
    }
    
    private void initPlayer(model.Player p) {
    	System.out.println("Init player " + p);
    	Player player = new Player(p);
    	add(player, new Integer(2));
    }
    
    private void initRoom(String name, Rectangle bounds) {
    	Room room = new Room(bounds.x, bounds.y, bounds.width, bounds.height, model.Room.getRoom(name));
    	add(room, 0);
    }
    
    public Dimension getIconDimension() {
    	return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
}
