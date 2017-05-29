package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Role extends JPanel {
	
	public Role(int x, int y, int h, int w, model.Role r) {
		setBounds(x, y, h, w);
		setOpaque(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				r.setOccupied(!r.isOccupied());
			}
		});
	}
	
	
}
