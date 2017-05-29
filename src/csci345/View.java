import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import javax.imageio.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

//package deadWood;

public class View {

    public static class Closer extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }

    public static class Board extends JLayeredPane {
        private JLabel boardLabel;

        Class cls = getClass();

        ImageIcon icon =
            new ImageIcon(ImageIO.read(cls.getResourceAsStream("board.jpg")));

        public Board() throws Exception{

            boardLabel = new JLabel();

            boardLabel.setIcon(icon);
            add(boardLabel, new Integer(0));
            boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        }

        public ImageIcon getIcon(){
            return this.icon;
        }

    }

    public static void main(String [] args) throws Exception{
        JFrame frame = new JFrame();
        Board board = new Board();
        //Mouse myMouse = new Mouse();


        ImageIcon ourIcon = board.getIcon();

        frame.setTitle("Deadwood");
        frame.setPreferredSize(new Dimension(ourIcon.getIconWidth(),ourIcon.getIconHeight()));
        frame.setResizable(false);
        frame.addWindowListener(new Closer());

        frame.add(board);

        frame.pack();
        frame.setVisible(true);
    }
    
    /*public static JFrame getFrame(){
    	return this.frame;
    }
    public static Board getBoard(){
    	return this.board;
    }*/
}