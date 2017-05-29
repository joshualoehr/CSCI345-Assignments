package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Role extends JLayeredPane implements Observer {

	private JLabel dice;
	
	@Override
	public void update(Observable o, Object arg) {
		setVisible(((model.Role) o).isOccupied());
	}
	
	public Role(int x, int y, int h, int w, model.Role r) {
		setBounds(x,y,h,w);
		
		dice = new JLabel();
		dice.setVisible(false);
		add(dice, new Integer(0));
		dice.setBounds(0,0,h,w);
		
		r.addObserver(this);
		this.update(r, null);
	}

}
