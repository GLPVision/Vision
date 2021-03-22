package affichage;

import data.Coordonnees;
import logs.LoggerUtility;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Mise en place de l'interface graphique de la fenêtre d'ouverture de l'application
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 5
 */
public class VisionGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerUtility.getLogger(AgricoleGUI.class);
	private JPanel contentPane;
	private JButton agricole, otage;
	private JTextField depart, fin, choix, xinit, yinit, xfin, yfin;
	private JLabel droneimg;
	private ImageIcon img;
	private Image drone;
	
	public VisionGUI(){
		/**
		 * Définition du nom de la fenêtre
		 */
		super("Vision Détection");
		logger.info("Construction de la fenêtre principale");
		
		/**
		 * Définitioon des paramètres de la carte
		 */
		setMinimumSize(new Dimension(560, 260));
		setPreferredSize(new Dimension(2000, 800));
		
		/**
		 * Définition de la fenêtre		
		 */
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Affichage du logo de l'application
		 */
		droneimg = new JLabel("");
		droneimg.setBounds(440, 11, 50, 50);
		img = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
		drone = img.getImage();
		drone = drone.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		droneimg.setIcon(new ImageIcon(drone));
		contentPane.add(droneimg);
		
		/**
		 * Mise en place du boutton pour accéder au scénario d'agriculture
		 */
		agricole = new JButton("Agriculture");
		agricole.setBackground(new Color(204, 190, 121));
		getContentPane().add(agricole);
		agricole.addActionListener(this);
		agricole.setBounds(400, 109, 130, 30);
		
		/**
		 * Mise en place du boutton pour accéder au scénario d'otage
		 */
		otage = new JButton("Prise d'otage");
		otage.setBackground(new Color(204, 190, 121));
		getContentPane().add(otage);
		otage.addActionListener(this);
		otage.setBounds(400, 150, 130, 30);
		
		/**
		 * Mise en place du renseignement des coordonnées de départ du drone
		 */
		depart = new JTextField();
		depart.setBounds(47, 27, 283, 30);
		contentPane.add(depart);
		depart.setColumns(10);
		depart.setText("Coordonées de départ : ");
		depart.setEditable(false);
		depart.setBackground(new Color(204, 190, 121));
		depart.setBorder(null);
		
		
		/**
		 * Mise en place du renseignement des coordonnées d'arrivée du drone
		 */
		fin = new JTextField();
		fin.setBounds(47, 109, 283, 30);
		contentPane.add(fin);
		fin.setColumns(10);
		fin.setText("Coordonées d'arrivée : ");
		depart.setEditable(false);
		fin.setEditable(false);
		fin.setBackground(new Color(204, 190, 121));
		fin.setBorder(null);
		
		/**
		 * Mise en place de l'affichage du texte demandant de choisir le scénario
		 */
		choix = new JTextField();
		choix.setHorizontalAlignment(SwingConstants.CENTER);
		choix.setBounds(400, 68, 130, 30);
		contentPane.add(choix);
		choix.setColumns(10);
		choix.setText("Votre domaine : ");
		choix.setEditable(false);
		choix.setBackground(new Color(204, 190, 121));
		choix.setBorder(null);
		
		/**
		 * Définition des coordonnées de départ en x
		 */
		xinit = new JTextField();
		xinit.setBounds(47, 68, 130, 30);
		contentPane.add(xinit);
		xinit.setColumns(10);
		xinit.setBackground(new Color(204, 190, 121));
		xinit.setBorder(null);
		
		/**
		 * Définition des coordonnées de départ en y
		 */
		yinit = new JTextField();
		yinit.setBounds(200, 68, 130, 30);
		contentPane.add(yinit);
		yinit.setColumns(10);
		yinit.setBackground(new Color(204, 190, 121));
		yinit.setBorder(null);
		
		/**
		 * Définition des coordonnées d'arrivée en x
		 */
		xfin = new JTextField();//x d'arriver
		xfin.setBounds(47, 150, 130, 30);
		contentPane.add(xfin);
		xfin.setColumns(10);
		xfin.setBackground(new Color(204, 190, 121));
		xfin.setBorder(null);
		
		/**
		 * Définition des coordonnées d'arrivée en y
		 */
		yfin = new JTextField();//y d'arriver
		yfin.setBounds(200, 150, 130, 30);
		contentPane.add(yfin);
		yfin.setColumns(10);
		yfin.setBackground(new Color(204, 190, 121));
		yfin.setBorder(null);

		this.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setSize(560, 260);
		this.setBounds(550, 350, 560, 260);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
		logger.info("Fenêtre principale construite");
	}
	/**
	 * permet d'interagir avec les boutons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		/**
		 * Appellation du scénario agricole et mise en place de la fenêtre
		 */
		if(e.getSource()==agricole) {
			logger.info("Scénario : agriculure");
			if(xinit.getText().isEmpty() || yinit.getText().isEmpty() || xfin.getText().isEmpty() || yfin.getText().isEmpty()){ //a ajouter verif si nombre
				JOptionPane.showMessageDialog(null ,"Veuillez vérifier les coordonnées", "Erreur", JOptionPane.ERROR_MESSAGE); //Affiche un message d'erreur si les coordonn�es saisie ne sont pas valide
				logger.error("Coordonnées invalides");
			}
			else{
				int x = Math.abs(Integer.parseInt(xfin.getText()) - Integer.parseInt(xinit.getText()));
				int y = Math.abs(Integer.parseInt(yfin.getText()) - Integer.parseInt(yinit.getText()));
				logger.info("Création d'une fenêtre Agricole");
				AgricoleGUI fen = null;
				try {
					fen = new AgricoleGUI(new Coordonnees(Integer.parseInt(xinit.getText()), Integer.parseInt(yinit.getText())), new Coordonnees(x, y));
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				this.setVisible(false);
				logger.info("Fin de création de la fenêtre Agricole");
			}
		}
		
		/**
		 * Appellation du scénario otage et mise en place de la fenêtre
		 */
		if(e.getSource()==otage) {
			logger.info("Scénario : otage");
			if(xinit.getText().isEmpty() || yinit.getText().isEmpty() || xfin.getText().isEmpty() || yfin.getText().isEmpty()){ //a ajouter verif si nombre
				JOptionPane.showMessageDialog(null ,"Veuillez vérifier les coordonnées", "Erreur", JOptionPane.ERROR_MESSAGE);//Affiche un message d'erreur si les coordonn�es saisie ne sont pas valide
				logger.error("Coordonnées invalides");
			}
			else {
				int x = Math.abs(Integer.parseInt(xfin.getText()) - Integer.parseInt(xinit.getText()));
				int y = Math.abs(Integer.parseInt(yfin.getText()) - Integer.parseInt(yinit.getText()));
				String txt = JOptionPane.showInputDialog(null ,"Nombre d'otages", "Prise d'otages", JOptionPane.INFORMATION_MESSAGE);
				while (txt.isEmpty() || Integer.parseInt(txt) > (x*y)/4){
					logger.error("Nombre d'otages invalide");
					JOptionPane.showMessageDialog(null ,"Veuillez vérifier le nombre d'otages (peut être trop grand nombre)", "Erreur", JOptionPane.ERROR_MESSAGE); //Affiche un message d'erreur si le nombre d'otage saisie n'est pas valide
					txt = JOptionPane.showInputDialog(null ,"Nombre d'otages", "Prise d'otages", JOptionPane.INFORMATION_MESSAGE);
				}
				logger.info("Création d'une fenêtre Otage");
				OtageGUI fen = null;
				try {
					fen = new OtageGUI(new Coordonnees(Integer.parseInt(xinit.getText()), Integer.parseInt(yinit.getText())), new Coordonnees(x, y), Integer.parseInt(txt));
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				this.setVisible(false);
				logger.info("Fin de création de la fenêtre Otage");
			}
		}
	}
}
