package view;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import model.InfoParser.RoleData;

@SuppressWarnings("serial")
public class Room extends JLayeredPane implements Observer {
	
	private class ShotCounter extends JLabel implements Observer {
		
		private static final String IMG_PATH = "assets/shot.png";
		
		private Integer index;
		
		public ShotCounter(int x, int y, int w, int h, int i, model.Room r) {
			super(new ImageIcon(IMG_PATH));
			index = i;
			setBounds(x, y, w, h);
			setVisible(false);
			r.addObserver(this);
		}
		
		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof Integer) {
				arg = (Integer) arg;
				if (arg.equals(0)) {
					setVisible(false);
				} else if (index.equals((Integer) arg)) {
					setVisible(true);
				}
			}
		}
	}
	
	private Rectangle cardBounds;
	
	public Room(int x, int y, int w, int h, model.Room r) {
		cardBounds = new Rectangle(x, y, w, h);
		setBounds(0, 0, 1200, 900);
		
		ArrayList<Rectangle> shotCounterData = 
				model.InfoParser.getTakesPositions(r.getName());
		for (int i = 0; i < shotCounterData.size(); i++) {
			Rectangle b = shotCounterData.get(i);
			initShotCounter(b, i, r);
		}
		
		ArrayList<RoleData> extrasData =
				model.InfoParser.getExtraPartsPositions(r.getName());
		extrasData.forEach(this::initExtraRole);
		
		r.addObserver(this);
	}
	
	private void initShotCounter(Rectangle b, int index, model.Room r) {
		add(new ShotCounter(b.x, b.y, b.width, b.height, index, r));
	}
	
	private void initExtraRole(RoleData rd) {
		Rectangle b = rd.getBounds();
		add(new Role(b.x, b.y, b.width, b.height, rd.getRole()), new Integer(1));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Scene) {
			model.Scene s = (model.Scene) arg;
			Rectangle b = cardBounds;
			add(new Scene(b.x, b.y, b.width, b.height, s), new Integer(2));
		}
	}

}
