package affichage;

import data.Coordonnees;
import data.Element;
import logs.LoggerUtility;
import moteur.Traitement;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Mise en place de l'interface graphique du scénario Otage
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * 
 * @version 12
 */

public class OtageGUI extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerUtility.getLogger(OtageGUI.class);
	private Info infoPanel;
	private Display carte;
	private JPanel contentPane;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem recherche, quitter, sombre, clair, Aide;
	private JTextField nomcarte, info;
	private Traitement traitement;
	private Coordonnees taille, debut;
	private int nbOtage, diffy, diffx, casex, casey;
	private boolean running = true;

	/**
	 * 
	 * @param debut
	 * @param taille
	 * @param nbOtage
	 * @throws IOException
	 */
	public OtageGUI(Coordonnees debut, Coordonnees taille, int nbOtage) throws IOException {
		/**
		 * Définition du nom de la fenêtre
		 */
		super("Vision Détection : Prise d'otages");
		this.debut = debut;
		this.taille = taille;
		this.nbOtage = nbOtage;
		logger.info("Affichage de la fenêtre d'Otage");
	}

	public void init(Coordonnees debut, Coordonnees taille) throws IOException {
		ActionBar actionListener = new ActionBar();
		Click click = new Click();
		/**
		 * Définition de la fenêtre		
		 */
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		
		/**
		 * Définition de la barre de menu		
		 */
		menu = new JMenuBar();
		setJMenuBar(menu);
		menu.setBackground(Color.lightGray);
		
		/**
		 * Mise en place de l'onglet "Fichier"		
		 */
		Fichier = new JMenu("Fichier");
		menu.add(Fichier);

		/**
		 * Mise en place de l'onglet "Apparence"
		 */
		Apparence = new JMenu("Apparence");
		menu.add(Apparence);
		
		/**
		 * Mise en place de l'onglet "Aide"		
		 */
		Aide = new JMenuItem("Aide ?") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getMaximumSize() {
				Dimension d1 = super.getPreferredSize();
                Dimension d2 = super.getMaximumSize();
                d2.width = d1.width;
                return d2;
			}
		};
		Aide.setPreferredSize(new Dimension(45,10));
		Aide.setHorizontalAlignment(SwingConstants.CENTER);
		Aide.setBackground(Color.LIGHT_GRAY);
		menu.add(Aide);		
		Aide.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité de recherche
		 */
		recherche = new JMenuItem("Nouvelle Recherche");
		Fichier.add(recherche);
		recherche.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour quitter l'application
		 */
		quitter = new JMenuItem("Quitter/Fermer Vision Détection");
		Fichier.add(quitter);
		quitter.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème sombre
		 */
		sombre = new JMenuItem("Thème Sombre");
		Apparence.add(sombre);
		sombre.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème clair
		 */
		clair = new JMenuItem("Thème Clair");
		Apparence.add(clair);
		clair.addActionListener(actionListener);
		contentPane.setLayout(null);
		
		/**
		 * Mise en place du nom au dessus de la carte
		 */
		nomcarte = new JTextField();
		nomcarte.setHorizontalAlignment(SwingConstants.CENTER);
		nomcarte.setBounds(274, 25, 90, 25);
		contentPane.add(nomcarte);
		nomcarte.setColumns(10);
		nomcarte.setText("Cartographie : ");
		nomcarte.setEditable(false);
		nomcarte.setBackground(SystemColor.activeCaption);
		nomcarte.setBorder(new MatteBorder(3, 3, 0, 3, (Color) Color.BLACK));
		
		/**
		 * Mise en place du nom au dessus du cadre d'informations
		 */
		info = new JTextField();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(10, 25, 90, 25);
		contentPane.add(info);
		info.setColumns(10);
		info.setText("Informations : ");
		info.setEditable(false);
		info.setBackground(SystemColor.activeCaption);
		info.setBorder(new MatteBorder(3, 3, 0, 3, (Color) Color.BLACK));

		traitement = new Traitement(taille, debut, nbOtage);

		casex = 950/traitement.getTaille().getX();
		casey = 600/traitement.getTaille().getY();
		int taillex = casex*traitement.getTaille().getX();
		int tailley = casey*traitement.getTaille().getY();
		diffy = 600-tailley;
		diffx = 950-taillex;

		/**
		 * Mise en place du cadre contenant la carte
		 */
		PaintStrategy paintStrategy = new PaintStrategy();
		carte = new Display(traitement, paintStrategy);
		carte.addMouseListener(click);
		carte.setLayout(new BorderLayout());
		carte.setBackground(new Color(0, 128, 128));
		carte.setBounds(274, 50, taillex, tailley);
		carte.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		contentPane.add(carte);

		infoPanel = new Info(traitement, diffy, carte);
		contentPane.add(infoPanel);

		this.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setSize(1250-diffx, 720-diffy);
		this.setBounds(300, 200, 1250-diffx, 720-diffy);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}

	@Override
	public void run(){
		try {
			init(debut, taille);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int state = 1;
		while (running) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(state == 0){
				state = traitement.scan();
				infoPanel.majGUI();
				carte.repaint();
			}
			else if(traitement.move() == 1){
				infoPanel.resetList();
				infoPanel.majGUI();
				carte.repaint();
			}
		}
		traitement.supp();
	}

	public void stop(){
		running = false;
	}

	private class ActionBar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			/**
			 * Acition pour quitter
			 */
			if(e.getSource()==quitter) {
				System.exit(0);
				logger.info("Fermeture de l'application");
			}

			/**
			 * Action pour passer en thème sombre
			 */
			if(e.getSource()==sombre) {
				contentPane.setBackground(Color.DARK_GRAY);
				logger.info("Passage au thème sombre");
			}

			/**
			 * Action pour passer en thème clair
			 */
			if(e.getSource()==clair) {
				contentPane.setBackground(Color.white);
				logger.info("Passage au thème clair");
			}

			/**
			 * Action affichant l'aide
			 */
			if(e.getSource()==Aide) {
				JOptionPane.showMessageDialog(OtageGUI.this, "Bienvenue sur Vision Détection ! \n\nNotre application vous permet de repérer le nombre d'aissaillants lors d'une prise d'otage.\nVous devrez entrer des coordonnées de départ et des coordonnées d'arrivée pour que le drone puisse s'envoler et survoler l'endroit désiré. \n\nA gauche, de nombreuse informations sont disponibles pour que vous puissiez localiser l'endroit précis de ces malfaiteurs.\nIl est aussi possible de determiner leur nombre précis grâce aux images renvoyées en temps réel.\n\nAidez-nous à sauvez les otages en collaborant avec les forces de l'orde !\n", "Aide", JOptionPane.INFORMATION_MESSAGE);
				logger.info("Affichage de l'aide");
			}

			/**
			 * Action fermant la fenêtre actuelle et renvoyant vers la fenêtre d'accueil
			 */
			if(e.getSource()==recherche) {
				VisionGUI fen = new VisionGUI();
				OtageGUI.this.setVisible(false);
				OtageGUI.this.stop();
				logger.info("Retour à la fenêtre principale");
			}
		}
	}

	private class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX()/casex;
			int y = e.getY()/casey;
			Element selected = traitement.getCarte().getElement(x, y);
			traitement.setSelected(selected);
			infoPanel.majGUI();
			carte.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}
}
