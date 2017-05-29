package view;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {
	
	private JLabel boardLabel;
    private ImageIcon icon;

    public Board(model.Board model, String boardFile) throws IOException {
    	icon = new ImageIcon(boardFile);
    	
        boardLabel = new JLabel(icon);
        add(boardLabel, 0);
        boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        setBounds(boardLabel.getBounds());
        
        initRoles();
    }
    
    public Dimension getIconDimension() {
    	return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
    
    private void initRoles() {
    	Role r;
    	r = new Role(114, 227, 46, 46, model.Role.getRole("Crusty Prospector"));
    	add(r, 0);
    	
    	r = new Role(51, 268, 46, 46, model.Role.getRole("Dragged by Train"));
    	add(r, 0);
    }
}
