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
		setVisible(((model.Role) o).isOccupied());
	}
	
	public Role(int x, int y, int w, int h, model.Role r) {
		System.out.println(String.format("view.Role at %d,%d", x, y));
		
		setBounds(x,y,w,h);
		setVisible(true);
		
		diceIcon = new ImageIcon(IMG_PATH + "b1.png");
		dice = new JLabel(diceIcon);
		dice.setBounds(0, 0, diceIcon.getIconWidth(), diceIcon.getIconHeight());
		add(dice);
		
		r.addObserver(this);
		this.update(r, null);
	}

}
