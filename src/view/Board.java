package view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {
	
	private void tempInit() {
		System.out.println("Init Train Station view");
		Room room = new Room(0, 0, 300, 500, model.Room.getRoom("Train Station"));
		add(room, 0);
	}
	
	private JLabel boardLabel;
    private ImageIcon icon;

    public Board(model.Board model, String boardFile) {
    	icon = new ImageIcon(boardFile);
    	
        boardLabel = new JLabel(icon);
        add(boardLabel, 0);
        boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        setBounds(boardLabel.getBounds());
        
        tempInit();
    }
    
    public Dimension getIconDimension() {
    	return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
}
