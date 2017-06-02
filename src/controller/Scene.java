package controller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;

import model.InfoParser.RoleData;

@SuppressWarnings("serial")
public class Scene extends JLayeredPane implements Observer {
	
	private model.Scene scene;
	private model.Board board;
	private ArrayList<Role> roles;

	public Scene(int x, int y, int w, int h, model.Room r, model.Board board) {
		this.board = board;
		setBounds(x, y, w, h);
		setOpaque(false);
		
		roles = new ArrayList<Role>();
		
		r.addObserver(this);
	}
	
	private void initStarringRoles(model.Scene s) {
		Role role;
		ArrayList<RoleData> starringRoleData = 
				model.InfoParser.getCardPartsPositions(s.getName());
		for (RoleData rd : starringRoleData) {
			Rectangle b = rd.getBounds();
			role = new Role(b.x, b.y, b.width, b.height, rd.getRole(), board);
			roles.add(role);
			add(role, new Integer(1));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Scene) {
			scene = (model.Scene) arg;
		} else if (arg instanceof model.Player) { 
			initStarringRoles(scene);
		} else if (arg == null) {
			roles.forEach(this::remove);
			roles = new ArrayList<Role>();
		}
	}
}
