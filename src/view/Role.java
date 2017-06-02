package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Role extends JPanel implements Observer {

	private static final String IMG_PATH = "assets/dice/";
	private ImageIcon diceIcon;
	private JLabel dice;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof model.Player) {
			diceIcon = new ImageIcon(((model.Player) arg).getImgName());
			dice.setIcon(diceIcon);
			dice.setBounds(0, 0, diceIcon.getIconWidth(), diceIcon.getIconHeight());
			dice.setVisible(true);
			add(dice);
			setVisible(true);
		} else if (arg == null) {
			remove(dice);
			setVisible(false);
		}
	}
	
	public Role(int x, int y, int w, int h, model.Role r) {
		setBounds(x,y,w,h);
		setVisible(false);
		
		dice = new JLabel();
		dice.setVisible(false);
		
		r.addObserver(this);
	}

}
