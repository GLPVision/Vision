package affichage;

import java.awt.Image; 
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.Color;

public class TestVisionGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fen = new VisionGUI();
		fen.getContentPane().setBackground(Color.DARK_GRAY);
		String pwd = System.getProperty("user.dir");
       	Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/drone.png"); 
        fen.setIconImage(icon);
        fen.setResizable(false);
		fen.setSize(560, 260);
		fen.setBounds(550, 350, 560, 260);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setVisible(true);
	}
}
