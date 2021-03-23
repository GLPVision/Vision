package affichage;

import data.Coordonnees;
import logs.LoggerUtility;
import moteur.Traitement;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel contentPane, carte;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem recherche, quitter, sombre, clair, Aide;
	private JTextField nomcarte, info;
	private JButton prec, next;
	private Traitement t;
	
	/**
	 * Nombre d'otages
	 */
	@SuppressWarnings("unused")
	private int nbOtage, diffy, diffx;
	@SuppressWarnings("unused")
	private Coordonnees debut;
	@SuppressWarnings("unused")
	private Coordonnees taille;

	
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
		init(debut, taille, nbOtage);
		logger.info("Affichage de la fenêtre d'Otage");
	}

	public void init(Coordonnees debut, Coordonnees taille, int nbOtage) throws IOException {
		/**
		 * Définitioon des paramètres de la carte
		 */
		this.debut = debut;
		this.taille = taille;
		this.nbOtage = nbOtage;
		
		/**
		 * Dimensions de la fenêtre		
		 */
		//setMinimumSize(new Dimension(1250, 720));
		//setPreferredSize(new Dimension(2000, 800));
		
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
		
		/**
		 * Mise en place de la grille affichant toutes les informations nécessaires et attendues de la carte
		 */



		
		/**
		 * Définition de l'affichage d'informations dans la liste
		 */
		//word = new String("");
		
		/**
		 * Appel de la fonction permettant la création de la carte
		 */

		t = new Traitement(true, taille, debut, nbOtage);

		int taillex = 950/t.getTaille().getX();
		int tailley = 600/t.getTaille().getY();
		taillex = taillex*t.getTaille().getX();
		tailley = tailley*t.getTaille().getY();
		diffy = 600-tailley;
		diffx = 950-taillex;


		Info info = new Info(t, diffy);
		contentPane.add(info);

		t.run();
		info.majGUI();


		Draw draw = new Draw();
		/**
		 * Mise en place du cadre contenant la carte
		 */
		carte = new Display(t, draw);

		/**
		 * layout pour remplir le panel
		 */
		carte.setLayout(new BorderLayout());
		carte.setBackground(new Color(0, 128, 128));
		carte.setBounds(274, 50, taillex, tailley);
		carte.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		contentPane.add(carte);

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

	}

	ActionListener actionListener = new ActionListener() {
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
				JOptionPane.showMessageDialog(OtageGUI.this, "Bienvenue sur Vision Détection ! \nNotre application vous permet de repérer le nombre d'aissaillants lors d'une prise d'otage.\nVous devrez entrer des coordonnées de départ et des coordonnées d'arrivées pour que le drone puisse s'envoler et survoler l'endroit désiré. \nDe nombreuse informations sont disponibles à gauche de la cartographie pour que vous puissiez localiser l'endroit précis de ces malfaiteurs,\nou bien, il est aussi possible de determiner leur nombre précis grâce aux images renvoyées en temps réel par notre drone.\nAidez-nous à sauvez les otages en collaborant avec les forces de l'orde !", "Aide", JOptionPane.INFORMATION_MESSAGE);
				logger.info("Affichage de l'aide");
			}

			/**
			 * Action fermant la fenêtre actuelle et renvoyant vers la fenêtre d'accueil
			 */
			if(e.getSource()==recherche) {
				@SuppressWarnings("unused")
				VisionGUI fen = new VisionGUI();
				OtageGUI.this.setVisible(false);
				logger.info("Retour à la fenêtre principale");
			}
			if(e.getSource()==next) {
				//t.next();
				logger.info("Passage à l'anomalie suivante");
			}
			if(e.getSource()==prec) {
				//t.previous();
				logger.info("Passage à l'anomalie précédente");
			}
		}
	};
}
