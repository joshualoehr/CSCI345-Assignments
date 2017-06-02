package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Role extends JPanel {
	
	public Role(int x, int y, int w, int h, model.Role r, model.Board board) {
		setBounds(x, y, w, h);
		setOpaque(false);
		Role role = this;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int n = JOptionPane.showConfirmDialog(
						role,
						"Take role: " + r.getName() + "?",
						"Take Role Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					board.processInput("work " + r.getName());
				}
			}
		});
	}
	
	
}
