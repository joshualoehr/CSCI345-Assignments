package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;

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
	
	private model.Scene scene;
	private SceneMouseListener listener;

	public Scene(int x, int y, int w, int h, model.Room r) {
		setBounds(x, y, w, h);
		setOpaque(false);
		
		r.addObserver(this);
	}
	
	public void addScene(model.Scene s) {
		this.scene = s;
		listener = new SceneMouseListener(s);
		addMouseListener(listener);
		initStarringRoles(s);
	}
	
	public void removeScene() {
		removeMouseListener(listener);
		listener = null;
		scene = null;
	}
	
	private void initStarringRoles(model.Scene s) {
		Role role;
		for (model.Role r : s.getStarringRoles()) {
			System.out.println("Init controller: " + r);
			switch (r.getName()) {
			case "Defrocked Priest":
				role = new Role(20, 47, 40, 40, r);
				add(role, new Integer(1));
				break;
			case "Marshal Canfield":
				role = new Role(83, 47, 40, 40, r);
				add(role, new Integer(1));
				break;
			case "One-Eyed Man":
				role = new Role(145, 47, 40, 40, r);
				add(role, new Integer(1));
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Update scene controller w/ " + arg);
		
		if (arg instanceof model.Scene) {
			System.out.println("Add scene mouse listener");
			addScene((model.Scene) arg);
		} else if (arg == null) {
			removeScene();
		}
	}
}
