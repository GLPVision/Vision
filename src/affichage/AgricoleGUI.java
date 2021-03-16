package affichage;

import data.Coordonnees;
import data.Element;
import data.Intrusion;
import moteur.Build;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Mise en place de l'interface graphique du scénario Agricole
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 16
 */

public class AgricoleGUI extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, carte, grille, buttonPanel;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem recherche, quitter, sombre, clair, Aide;
	private JTextField nomcarte, info;
	private JLabel repere, nombre, total, text, types, Anomalies;
	private JList<?> liste;
	private JScrollPane scrollPane;
	private JButton prec, next;
	private String word;
	private Traitement t;
	private DefaultListModel model;
	private DefaultListCellRenderer cellRenderer;
	private ArrayList ano;
	
	/**
	 * JList contient une liste
	 */
	private JList<?> content;
	
	/**
	 * Liste contient la carte
	 */
	private DefaultListModel<?> list;
	private Coordonnees debut;
	private Coordonnees taille;
	private int diffx, diffy;

	/**
	 * 
	 * @param debut
	 * @param taille
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AgricoleGUI(Coordonnees debut, Coordonnees taille) throws IOException {
		/**
		 * Définition du nom de la fenêtre
		 */
		super("Vision Détection : Agricole");
		init(debut, taille);
		//run();
	}


	public void init(Coordonnees debut, Coordonnees taille) throws IOException {
		/**
		 * Définitioon des paramètres de la carte
		 */
		this.debut = debut;
		this.taille = taille;
		
		/**
		 * Dimensions de la fenêtre		
		 */
		//setMinimumSize(new Dimension(1250, 720));//Taille minimum de notre fen�tre
		setPreferredSize(new Dimension(2000, 800));//Dimension de notre fen�tre 
		
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
		menu = new JMenuBar();//Barre de menu
		setJMenuBar(menu);
		menu.setBackground(Color.lightGray);//Couleur de l'arri�re plan
		
		/**
		 * Mise en place de l'onglet "Fichier"		
		 */
		Fichier = new JMenu("Fichier");
		menu.add(Fichier);
		
		/**
		 * Mise en place de l'onglet "Apparence"
		 */
		Apparence = new JMenu("Apparence");//Permet � l'utuilisateur de choisir entre le th�me sombre et le th�me clair
		menu.add(Apparence);
		
		/**
		 * Mise en place de l'onglet "Aide"		
		 */
		Aide = new JMenuItem("Aide ?") { //Affiche un message d'aide
			
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
		recherche = new JMenuItem("Nouvelle Recherche");//Permet de changer de map
		Fichier.add(recherche);
		recherche.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour quitter l'application
		 */
		quitter = new JMenuItem("Quitter/Fermer Vision Détection");//Permet � l'utilisateur de quitter l'application
		Fichier.add(quitter);
		quitter.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème sombre
		 */
		sombre = new JMenuItem("Thème Sombre");//Affiche le theme sombre de notre application
		Apparence.add(sombre);
		sombre.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème clair
		 */
		clair = new JMenuItem("Thème Clair");//Affiche le theme clair de notre application
		Apparence.add(clair);
		clair.addActionListener(actionListener);
		contentPane.setLayout(null);


		
		/**
		 * initialisation
		 */
		//list = new DefaultListModel();
		//content = new JList(list);
		//content.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		/**
		 * centre text de chaque cas
		 */
		//cellRenderer = new DefaultListCellRenderer();
		//cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		//content.setCellRenderer(cellRenderer);
		
		/**
		 * Définition de la taille des cases en fonction du nombre de cases
		 */
		/*
		content.setFixedCellWidth(carte.getWidth()/taille.getX());
		content.setFixedCellHeight(carte.getHeight()/taille.getY());
		content.setVisibleRowCount(taille.getY());
		content.setBackground(new Color(0, 102, 51));
		content.setBorder(new LineBorder(new Color(0, 0, 0), 3));

		 */
		//carte.add(content, BorderLayout.CENTER);

		/**
		 * Mise en place du model et de la liste pour énumérer les anomalies présentes sur la map
		 */

		model = new DefaultListModel();
		/*
		ano = new ArrayList();
		content.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				t.current();
				if (! ano.contains(t.getWord())) {
					model.addElement(t.getWord());
					ano.add(t.getWord());
				}
			}
		});

		 */


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
		grille = new JPanel();
		grille.setBorder(new MatteBorder(1, 3, 3, 3, (Color) Color.BLACK));
		grille.setBounds(10, 50, 255, 600);
		contentPane.add(grille);
		grille.setLayout(null);
		grille.setBackground(new Color(204, 190, 121));
		
		/**
		 * Mise en place de l'encadré de texte affichant l'anomalie sélectionnée sur la carte
		 */
		repere = new JLabel("    Anomalie sélectionnée");
		repere.setHorizontalAlignment(SwingConstants.LEFT);
		repere.setBounds(0, 0, 255, 30);
		repere.setBorder(new MatteBorder(3, 0, 3, 0, (Color) new Color(0, 0, 0)));
		grille.add(repere);
		
		/**
		 * Mise en place de l'encadré de texte affichant le nombre d'anomalies de chaque catégorie présentes sur la carte
		 */
		nombre = new JLabel("    Nombre d'anomalies");
		nombre.setHorizontalAlignment(SwingConstants.LEFT);
		nombre.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		nombre.setBounds(0, 340, 255, 30);
		grille.add(nombre);
		
		/**
		 * Mise en place de l'encadré de texte affichant le nombre total d'anomalies présentes sur la carte
		 */
		total = new JLabel("    Total : "); //
		total.setHorizontalAlignment(SwingConstants.LEFT);
		total.setBorder(new MatteBorder(3, 0, 3, 0, (Color) Color.BLACK));
		total.setBounds(0, 450, 255, 30);
		grille.add(total);
		
		/**
		 * Mise en place du bouton permettant de sélectionner l'anomalie précédente sur la carte
		 */
		
		/**
		 * Mise en place du bouton permettant de sélectionner l'anomalie suivante sur la carte
		 */
		
		/**
		 * Mise en place des informations de coordonnées affichées en fonction de la case sélectionnée
		 */
		text = new JLabel(""); //lister les coordonnées
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setHorizontalAlignment(SwingConstants.LEFT);
		text.setBounds(0, 30, 255, 50);
		text.setBorder(null);
		grille.add(text);

		/**
		 * Mise en place des informations de types d'anomalie affichées en fonction de la case sélectionnée
		 */
		types = new JLabel(); //
		types.setVerticalAlignment(SwingConstants.CENTER);
		types.setHorizontalAlignment(SwingConstants.LEFT);
		types.setBorder(null);
		types.setBounds(0, 370, 255, 80);
		grille.add(types);		
		
		/**
		 * Mise en place du texte au dessus de la liste
		 */
		Anomalies = new JLabel("    Liste des anomalies présentes");
		Anomalies.setHorizontalAlignment(SwingConstants.LEFT);
		Anomalies.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
		Anomalies.setBounds(0, 80, 255, 30);
		grille.add(Anomalies);
		
		/**
		 * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
		 */
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		scrollPane.setBounds(0, 108, 255, 235);
		grille.add(scrollPane);
		
		/**
		 * Mise en place de la liste contenant les informations sur les anomalies de la carte
		 */
		liste = new JList(model);
		scrollPane.setViewportView(liste);
		liste.setBorder(null);
		liste.setBackground(new Color(204, 190, 121));


		
		/**
		 * Définition de l'affichage d'informations dans la liste
		 */
		word = new String("");
		
		/**
		 * Appel de la fonction permettant la création de la carte
		 */


		t = new Traitement(false, taille, debut, 0, total, text, types, null, list, content, word);

		int taillex = 950/t.getTaille().getX();
		int tailley = 600/t.getTaille().getY();
		taillex = taillex*t.getTaille().getX();
		tailley = tailley*t.getTaille().getY();
		diffy = 600-tailley;
		diffx = 950-taillex;


		buttonPanel = new JPanel();
		buttonPanel.setBorder(new MatteBorder(0, 3, 3, 3, (Color) new Color(0, 0, 0)));
		buttonPanel.setBounds(0, 480, 255, 120-diffy);
		buttonPanel.setBackground(new Color (204, 190, 121));
		SpringLayout sl_panel = new SpringLayout();
		buttonPanel.setLayout(sl_panel);
		prec = new JButton("Anomalie précédente");
		prec.setBorder(null);
		prec.setBackground(SystemColor.activeCaption);
		next = new JButton("Anomalie suivante");
		next.setBorder(null);
		next.setBackground(SystemColor.activeCaption);
		sl_panel.putConstraint(SpringLayout.SOUTH, next, -10, SpringLayout.SOUTH, buttonPanel);
		sl_panel.putConstraint(SpringLayout.NORTH, next, 5, SpringLayout.VERTICAL_CENTER, buttonPanel);
		sl_panel.putConstraint(SpringLayout.SOUTH, prec, -5, SpringLayout.VERTICAL_CENTER, buttonPanel);
		sl_panel.putConstraint(SpringLayout.NORTH, prec, 10, SpringLayout.NORTH, buttonPanel);
		sl_panel.putConstraint(SpringLayout.WEST, prec, 10, SpringLayout.WEST, buttonPanel);
		sl_panel.putConstraint(SpringLayout.EAST, prec, -10, SpringLayout.EAST, buttonPanel);
		sl_panel.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, buttonPanel);
		sl_panel.putConstraint(SpringLayout.EAST, next, -10, SpringLayout.EAST, buttonPanel);
		buttonPanel.add(next);
		buttonPanel.add(prec);
		grille.add(buttonPanel);

		grille.setBounds(10, 50, 255, 600-diffy);

		t.run();
		for(int i=0 ; i<t.getEntoure().size() ; i++){
			Element e = t.getEntoure().get(i);
			model.addElement("   " + e.getDesc() + " en : " + e.getCoordonnees().getX() + ", " + e.getCoordonnees().getY());
		}

		Draw draw = new Draw();
		/**
		 * Mise en place du cadre contenant la carte
		 */
		carte = new Display(t, draw);


		carte.setLayout(new BorderLayout());
		carte.setBackground(new Color(0, 128, 128));
		//carte.setBounds(274, 50, 950, 600);
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
	public void run() {
		while (true){
			try {
				Thread.sleep(100);
				t.scan();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			carte.repaint();
		}
	}

	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			/**
			 * Action pour quitter
			 */
			if(e.getSource()==quitter) {
				System.exit(0);
			}

			/**
			 * Action pour passer en thème sombre
			 */
			if(e.getSource()==sombre) {
				contentPane.setBackground(Color.DARK_GRAY);
			}

			/**
			 * Action pour passer en thème clair
			 */
			if(e.getSource()==clair) {
				contentPane.setBackground(Color.white);
			}

			/**
			 * Action affichant l'aide
			 */
			if(e.getSource()==Aide) {
				JOptionPane.showMessageDialog(AgricoleGUI.this, "Bienvenue sur Vision Détection ! \nL'application qui vous permet d'identifier une anomalie dans un espace défini ou d'identifier le nombre de personnes présentes lors d'une prise d'otage.", "Aide", JOptionPane.INFORMATION_MESSAGE);
			}

			/**
			 * Action fermant la fenêtre actuelle et renvoyant vers la fenêtre d'accueil
			 */
			if(e.getSource()==recherche) {
				JFrame fen = new VisionGUI();
				AgricoleGUI.this.setVisible(false);
			}
			if(e.getSource()==next) {
				t.next();
			}
			if(e.getSource()==prec) {
				t.previous();
			}
		}
	};

}
