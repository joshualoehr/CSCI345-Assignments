package controller;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;

import model.InfoParser.RoleData;

@SuppressWarnings("serial")
public class Scene extends JLayeredPane implements Observer {
	
	private class SceneMouseListener extends MouseAdapter {
		
		private model.Scene scene;
		
		public SceneMouseListener(model.Scene scene) {
			super();
			this.scene = scene;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("Clicked scene: " + scene);
		}
	}
	
	private SceneMouseListener listener;

	public Scene(int x, int y, int w, int h, model.Room r) {
		setBounds(x, y, w, h);
		setOpaque(false);
		
		r.addObserver(this);
	}
	
	public void addScene(model.Scene s) {
		listener = new SceneMouseListener(s);
		addMouseListener(listener);
		initStarringRoles(s);
	}
	
	public void removeScene() {
		removeMouseListener(listener);
		listener = null;
	}
	
	private void initStarringRoles(model.Scene s) {
		Role role;
		ArrayList<RoleData> starringRoleData = 
				model.InfoParser.getCardPartsPositions(s.getName());
		for (RoleData rd : starringRoleData) {
			Rectangle b = rd.getBounds();
			role = new Role(b.x, b.y, b.width, b.height, rd.getRole());
			add(role, new Integer(1));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Scene) {
			addScene((model.Scene) arg);
		} else if (arg == null) {
			removeScene();
		}
	}
}
