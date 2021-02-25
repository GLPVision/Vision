package affichage;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import java.awt.Color;

public class TestVisionGUI {

	public static void main(String[] args) {
		JFrame fen = new VisionGUI();
		fen.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
        fen.setIconImage(icon.getImage());
        fen.setResizable(false);
		fen.setSize(560, 260);
		fen.setBounds(550, 350, 560, 260);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setVisible(true);
	}
}
