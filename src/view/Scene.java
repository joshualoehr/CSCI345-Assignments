package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Scene extends JLayeredPane implements Observer {
	
	private static final String IMG_PATH = "assets/cards/";
	
	private JLabel sceneCard;
	private ImageIcon cardIcon;

	public Scene(int x, int y, int w, int h, model.Scene s) {
		setBounds(x, y, w, h);
		setVisible(true);
		
		cardIcon = new ImageIcon(IMG_PATH + "01.png");
		sceneCard = new JLabel(cardIcon);
		sceneCard.setBounds(0, 0, cardIcon.getIconWidth(), cardIcon.getIconHeight());
		add(sceneCard);
		
		initStarringRoles(s);
		
		s.addObserver(this);
	}
	
	private void initStarringRoles(model.Scene s) {
		Role role;
		for (model.Role r : s.getStarringRoles()) {
			System.out.println("Init view: " + r);
			switch (r.getName()) {
			case "Defrocked Priest":
				role = new Role(20, 47, 42, 42, r);
				add(role, new Integer(1));
				break;
			case "Marshal Canfield":
				role = new Role(83, 47, 42, 42, r);
				add(role, new Integer(1));
				break;
			case "One-Eyed Man":
				role = new Role(145, 47, 42, 42, r);
				add(role, new Integer(1));
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO update scene view
		setVisible(!isVisible());
		System.out.println("Update scene");
	}
}
