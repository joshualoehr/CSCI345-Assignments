package controller;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Board extends JLayeredPane {

	public Board(model.Board model) {
		setBounds(0,0,1200,900);
		
		initRoles();
	}
	
	 private void initRoles() {
    	Role r;
    	r = new Role(114, 227, 46, 46, model.Role.getRole("Crusty Prospector"));
    	add(r, 0);
    	
    	r = new Role(51, 268, 46, 46, model.Role.getRole("Dragged by Train"));
    	add(r, 0);
    }
}
