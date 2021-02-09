package affichage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class VisionGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton agricole, otage;
	
	public VisionGUI(){
		super("Vision Détection");
		
		setMinimumSize(new Dimension(600, 400));
		setPreferredSize(new Dimension(2000, 800));
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		
		
		agricole = new JButton("Agriculture");
		agricole.setBackground(new Color(204, 190, 121));
		getContentPane().add(agricole);
		agricole.addActionListener(this);
		agricole.setBounds(10, 10, 150, 25);
		
		otage = new JButton("Prise d'otage");
		otage.setBackground(new Color(204, 190, 121));
		getContentPane().add(otage);
		otage.addActionListener(this);
		otage.setBounds(10, 10, 150, 25);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==agricole) {
			JFrame fen = new AgricoleGUI();
			fen.getContentPane().setBackground(Color.DARK_GRAY);
			String pwd = System.getProperty("user.dir");
	       	Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/drone.png"); 
	        fen.setIconImage(icon); 
			fen.setSize(1000, 500);
			fen.setBounds(300, 200, 1000, 500);
			fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fen.setVisible(true);
		}
		
		if(e.getSource()==otage) {
			JFrame fen = new OtageGUI();
			fen.getContentPane().setBackground(Color.DARK_GRAY);
			String pwd = System.getProperty("user.dir");
	       	Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/drone.png"); 
	        fen.setIconImage(icon); 
			fen.setSize(1000, 500);
			fen.setBounds(300, 200, 1000, 500);
			fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fen.setVisible(true);
		}
	}
	
}
