package affichage;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Mise en place du lancement de l'application
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * 
 * @version 1
 */

public class TestVisionGUI {

	/**
	 * Fonction principale
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame fen = new VisionGUI();
		fen.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
        fen.setIconImage(icon.getImage());
        fen.setResizable(false);
		fen.setSize(560, 260);
		fen.setBounds(550, 350, 560, 260);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fen.setLocation(dim.width/2-fen.getSize().width/2, dim.height/2-fen.getSize().height/2);
		fen.setVisible(true);
	}
}
