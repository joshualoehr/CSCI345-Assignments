package view;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Room extends JLayeredPane implements Observer {
	
	private JLabel visual;
	private JLabel createColoredLabel(String text, Color color, Point origin, int w, int h) {
		JLabel label = new JLabel(text);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(color);
		label.setForeground(Color.black);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBounds(origin.x, origin.y, w, h);
		return label;
	}

	private void tempInit(model.Room r) {
		System.out.println("Init extra roles views");
		Role r1 = new Role(114, 227, 46, 46, model.Role.getRole("Crusty Prospector"));
    	add(r1, new Integer(1));
    	Role r2 = new Role(51, 268, 46, 46, model.Role.getRole("Dragged by Train"));
    	add(r2, new Integer(1));
    	
    	System.out.println("Init shot counters");
    	ShotCounter sc1 = new ShotCounter(141, 11, 47, 47, 1, r);
    	add(sc1, new Integer(1));
    	ShotCounter sc2 = new ShotCounter(89, 11, 47, 47, 2, r);
    	add(sc2, new Integer(1));
    	ShotCounter sc3 = new ShotCounter(36, 11, 47, 47, 3, r);
    	add(sc3, new Integer(1));
    	
	}
	
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
	
	
	public Room(int x, int y, int w, int h, model.Room r) {
		setBounds(x, y, w, h);
		
		//visual = createColoredLabel(r.toString(), Color.blue, new Point(x,y), w, h);
		//add(visual, new Integer(0));
		
		tempInit(r);
		
		r.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Update room view w/ " + arg);
		
		if (arg instanceof model.Scene) {
			Scene s = new Scene(21, 69, 205, 115, (model.Scene) arg);
			add(s, new Integer(2));
		}
	}

}
