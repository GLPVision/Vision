package affichage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VisionGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton agricole, otage;
	private JTextField depart, fin, choix, xinit, yinit, xfin, yfin;
	private JLabel droneimg;
	private ImageIcon img;
	private Image drone;
	
	public VisionGUI(){
		super("Vision Détection");
		
		setMinimumSize(new Dimension(560, 260));
		setPreferredSize(new Dimension(2000, 800));
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		droneimg = new JLabel("");
		droneimg.setBounds(440, 11, 50, 50);
		String pwd = System.getProperty("user.dir");//Permet d'afficher le logo de vision
		img = new ImageIcon(pwd + "/src/affichage/drone.png");
		drone = img.getImage();
		drone = drone.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		droneimg.setIcon(new ImageIcon(drone));
		contentPane.add(droneimg);
		
		agricole = new JButton("Agriculture");
		agricole.setBackground(new Color(204, 190, 121));
		getContentPane().add(agricole);
		agricole.addActionListener(this);
		agricole.setBounds(400, 109, 130, 30);
		
		otage = new JButton("Prise d'otage");
		otage.setBackground(new Color(204, 190, 121));
		getContentPane().add(otage);
		otage.addActionListener(this);
		otage.setBounds(400, 150, 130, 30);
		
		depart = new JTextField();
		depart.setBounds(47, 27, 283, 30);
		contentPane.add(depart);
		depart.setColumns(10);
		depart.setText("Coordonées de départ : ");//Permet de rentr� les coordonn�es de d�part
		depart.setEditable(false);
		depart.setBackground(new Color(204, 190, 121));
		depart.setBorder(null);
		
		fin = new JTextField();
		fin.setBounds(47, 109, 283, 30);
		contentPane.add(fin);
		fin.setColumns(10);
		fin.setText("Coordonées d'arrivée : ");//Permet de rentr� les coordonn�es de d�part
		depart.setEditable(false);
		fin.setEditable(false);
		fin.setBackground(new Color(204, 190, 121));
		fin.setBorder(null);
		
		choix = new JTextField();
		choix.setBounds(400, 68, 130, 30);
		contentPane.add(choix);
		choix.setColumns(10);
		choix.setText("Votre domaine : ");
		choix.setEditable(false);
		choix.setBackground(new Color(204, 190, 121));
		choix.setBorder(null);
		
		xinit = new JTextField(); //x d�but
		xinit.setBounds(47, 68, 130, 30);
		contentPane.add(xinit);
		xinit.setColumns(10);
		xinit.setBackground(new Color(204, 190, 121));
		xinit.setBorder(null);
		
		yinit = new JTextField();//y d�but
		yinit.setBounds(200, 68, 130, 30);
		contentPane.add(yinit);
		yinit.setColumns(10);
		yinit.setBackground(new Color(204, 190, 121));
		yinit.setBorder(null);
		
		xfin = new JTextField();//x d'arriver
		xfin.setBounds(47, 150, 130, 30);
		contentPane.add(xfin);
		xfin.setColumns(10);
		xfin.setBackground(new Color(204, 190, 121));
		xfin.setBorder(null);
		
		yfin = new JTextField();//y d'arriver
		yfin.setBounds(200, 150, 130, 30);
		contentPane.add(yfin);
		yfin.setColumns(10);
		yfin.setBackground(new Color(204, 190, 121));
		yfin.setBorder(null);
	}
	/**
	 * permet d'interagir avec les boutons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==agricole) {
			if(xinit.getText().isEmpty() || yinit.getText().isEmpty() || xfin.getText().isEmpty() || yfin.getText().isEmpty()){ //a ajouter verif si nombre
				JOptionPane.showMessageDialog(null ,"Veuillez vérifier les coordonnées", "Erreur", JOptionPane.ERROR_MESSAGE); //Affiche un message d'erreur si les coordonn�es saisie ne sont pas valide
			}
			else{
				int x = Integer.parseInt(xfin.getText()) - Integer.parseInt(xinit.getText());
				int y = Integer.parseInt(yfin.getText()) - Integer.parseInt(yinit.getText());
				JFrame fen = null;
				try {
					fen = new AgricoleGUI(x, y);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				fen.getContentPane().setBackground(Color.DARK_GRAY);
				String pwd = System.getProperty("user.dir");
				Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/drone.png");
				fen.setIconImage(icon);
				fen.setResizable(false);
				fen.setSize(1250, 720);
				fen.setBounds(300, 200, 1250, 720);
				fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fen.setVisible(true);
				this.setVisible(false);
			}
		}
		
		if(e.getSource()==otage) {
			if(xinit.getText().isEmpty() || yinit.getText().isEmpty() || xfin.getText().isEmpty() || yfin.getText().isEmpty()){ //a ajouter verif si nombre
				JOptionPane.showMessageDialog(null ,"Veuillez vérifier les coordonnées", "Erreur", JOptionPane.ERROR_MESSAGE);//Affiche un message d'erreur si les coordonn�es saisie ne sont pas valide
			}
			else {
				int x = Integer.parseInt(xfin.getText()) - Integer.parseInt(xinit.getText());
				int y = Integer.parseInt(yfin.getText()) - Integer.parseInt(yinit.getText());
				String txt = JOptionPane.showInputDialog(null ,"Nombre d'otages", "Prise d'otages", JOptionPane.INFORMATION_MESSAGE);
				while (txt.isEmpty() || Integer.parseInt(txt) > (x*y)/4){
					JOptionPane.showMessageDialog(null ,"Veuillez vérifier le nombre d'otages (peut être trop grand nombre)", "Erreur", JOptionPane.ERROR_MESSAGE); //Affiche un message d'erreur si le nombre d'otage saisie n'est pas valide
					txt = JOptionPane.showInputDialog(null ,"Nombre d'otages", "Prise d'otages", JOptionPane.INFORMATION_MESSAGE);
				}
				JFrame fen = null;
				try {
					fen = new OtageGUI(x, y, Integer.parseInt(txt));
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				fen.getContentPane().setBackground(Color.DARK_GRAY);
				String pwd = System.getProperty("user.dir");
				Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/drone.png");
				fen.setIconImage(icon);
				fen.setResizable(false);
				fen.setSize(1000, 500);
				fen.setBounds(300, 200, 1000, 500);
				fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fen.setVisible(true);
				this.setVisible(false);
			}
		}
	}
}
