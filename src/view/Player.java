package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private ImageIcon icon;
	private JLabel label;
	
	public Player(int x, int y, model.Player p) {
		icon = new ImageIcon(p.getImgName());
		label = new JLabel(icon);
		label.setBounds(0, 0, 46, 46);
		add(label);
		
		setBounds(x, y, label.getWidth(), label.getHeight());
	}

}
