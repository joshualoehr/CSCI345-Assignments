package view;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import model.InfoParser.RoleData;

@SuppressWarnings("serial")
public class Scene extends JLayeredPane {
	
	private static final String IMG_PATH = "assets/cards/";
	
	private JLabel sceneCard;
	private ImageIcon cardIcon;

	public Scene(int x, int y, int w, int h, model.Scene s) {
		setBounds(x, y, w, h);
		setVisible(true);
		
		String imgName = model.InfoParser.getSceneImg(s.getName());
		cardIcon = new ImageIcon(IMG_PATH + imgName);
		sceneCard = new JLabel(cardIcon);
		sceneCard.setBounds(0, 0, cardIcon.getIconWidth(), cardIcon.getIconHeight());
		add(sceneCard);
		
		initStarringRoles(s);
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
}
