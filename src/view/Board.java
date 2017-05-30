package view;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {
	
	private static int a = 1;
	
	private void tempInit() {
		System.out.println("Init Train Station view");
		Room room = new Room(5, 5, 300, 500, model.Room.getRoom("Train Station"));
		add(room, 0);
	}
	
	private JLabel boardLabel;
    private ImageIcon icon;

    public Board(model.Board model, String boardFile) throws IOException {
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
