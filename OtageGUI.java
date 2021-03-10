package affichage;

import data.Coordonnees;
import moteur.Build;
import moteur.Traitement;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Mise en place de l'interface graphique du scénario Otage
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * 
 * @version 12
 */

public class OtageGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, carte, grille;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem recherche, quitter, sombre, clair, Aide;
	private JTextField nomcarte, info;
	private JLabel otage, repere, nombre, total, Individus, text;
	private JButton prec, next;
	private Traitement t;
	private DefaultListCellRenderer cellRenderer;
	private JScrollPane scrollPane;
	private JList liste;
	private DefaultListModel model;
	private String word;
	private ArrayList people;
	
	/**
	 * JList contient une liste
	 */
	@SuppressWarnings("rawtypes")
	private JList content;
	/**
	 * Liste contient la carte
	 */
	@SuppressWarnings("rawtypes")
	private DefaultListModel list;
	/**
	 * Nombre d'otages
	 */
	@SuppressWarnings("unused")
	private int nbOtage;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OtageGUI(Coordonnees debut, Coordonnees taille, int nbOtage) throws IOException{
		/**
		 * Définition du nom de la fenêtre
		 */
		super("Vision Détection : Prise d'otages");
		
		/**
		 * Définitioon des paramètres de la carte
		 */
		this.debut = debut;
		this.taille = taille;
		this.nbOtage = nbOtage;
		
		/**
		 * Dimensions de la fenêtre		
		 */
		setMinimumSize(new Dimension(1250, 720));
		setPreferredSize(new Dimension(2000, 800));
		
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
		Aide.addActionListener(this);
		
		/**
		 * Mise en place de la fonctionnalité de recherche
		 */
		recherche = new JMenuItem("Nouvelle Recherche");
		Fichier.add(recherche);
		recherche.addActionListener(this);
		
		/**
		 * Mise en place de la fonctionnalité pour quitter l'application
		 */
		quitter = new JMenuItem("Quitter/Fermer Vision Détection");
		Fichier.add(quitter);
		quitter.addActionListener(this);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème sombre
		 */
		sombre = new JMenuItem("Thème Sombre");
		Apparence.add(sombre);
		sombre.addActionListener(this);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème clair
		 */
		clair = new JMenuItem("Thème Clair");
		Apparence.add(clair);
		clair.addActionListener(this);
		contentPane.setLayout(null);
		
		/**
		 * Mise en place du cadre contenant la carte
		 */
		carte = new JPanel();
		
		/**
		 * layout pour remplir le panel
		 */
		carte.setLayout(new BorderLayout());
		carte.setBackground(new Color(0, 128, 128));
		carte.setBounds(274, 50, 950, 600);
		
		/**
		 * initialisation
		 */
		list = new DefaultListModel();
		content = new JList(list);
		content.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		/**
		 * centre text de chaque cas
		 */
		cellRenderer = new DefaultListCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		content.setCellRenderer(cellRenderer);
		
		/**
		 * Définition de la taille des cases en fonction du nombre de cases
		 */
		content.setFixedCellWidth(carte.getWidth()/taille.getX());
		content.setFixedCellHeight(carte.getHeight()/taille.getY());
		content.setVisibleRowCount(taille.getY());
		content.setBackground(new Color(0, 128, 128));
		content.setBorder(new LineBorder(Color.BLACK, 3));
		carte.add(content, BorderLayout.CENTER);
		contentPane.add(carte);

		content.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				t.current();
			}
		});
		
		/**
		 * Mise en place du model et de la liste pour énumérer les individu présent sur la map
		 */
		model = new DefaultListModel();
		people = new ArrayList();
		content.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				t.current();
				if (! people.contains(t.getWord())) {
					model.addElement(t.getWord());
					people.add(t.getWord());
				}
			}
		});
		
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
		grille.setBorder(new LineBorder(Color.BLACK, 3));
		grille.setBounds(10, 50, 255, 600);
		contentPane.add(grille);
		grille.setLayout(null);
		grille.setBackground(new Color(204, 190, 121));
		
		/**
		 * Mise en place de l'encadré de texte affichant le nombre d'otages rempli par l'utilisateur au préalable
		 */
		otage = new JLabel("    Nombre d'otages :");
		otage.setHorizontalAlignment(SwingConstants.LEFT);
		otage.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
		otage.setBounds(0, 0, 255, 30);
		grille.add(otage);
		
		/**
		 * Mise en place de l'encadré de texte affichant les coordonnées de l'individu que l'on a sélectionner sur la carte
		 */
		repere = new JLabel("    Coordonnées de l'individu actuel");
		repere.setBackground(SystemColor.activeCaption);
		repere.setHorizontalAlignment(SwingConstants.LEFT);
		repere.setBounds(0, 30, 255, 30);
		repere.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
		grille.add(repere);
		
		/**
		 * Mise en place de l'encadré de texte affichant le nombre total d'individu présents sur la carte
		 */
		nombre = new JLabel("    Nombre total d'individus :");
		nombre.setHorizontalAlignment(SwingConstants.LEFT);
		nombre.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		nombre.setBounds(0, 420, 255, 30);
		grille.add(nombre);
		
		/**
		 * Mise en place de l'encadré de texte affichant le nombre total d'assaillants présents sur la carte
		 */
		total = new JLabel("    Nombre total d'assaillants : ");
		total.setHorizontalAlignment(SwingConstants.LEFT);
		total.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
		total.setBounds(0, 450, 255, 30);
		grille.add(total);
				
		/**
		 * Mise en place du bouton permettant de sélectionner l'individu précédent sur la carte
		 */
		prec = new JButton("Individu précédent");
		prec.setBorder(null);
		prec.setBackground(SystemColor.activeCaption);
		prec.setBounds(10, 491, 235, 42);
		grille.add(prec);
		prec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.previous();
			}
		});
		
		/**
		 * Mise en place du bouton permettant de sélectionner l'individu suivant sur la carte
		 */
		next = new JButton("Individu suivant");
		next.setBorder(null);
		next.setBackground(SystemColor.activeCaption);
		next.setBounds(10, 547, 235, 42);
		grille.add(next);
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.next();
			}
		});
		
		/**
		 * Mise en place du texte au dessus de la liste
		 */
		Individus = new JLabel("    Coordonnées des individus repérés");
		Individus.setHorizontalAlignment(SwingConstants.LEFT);
		Individus.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
		Individus.setBackground(SystemColor.activeCaption);
		Individus.setBounds(0, 110, 255, 30);
		grille.add(Individus);
		
		/**
		 * Mise en place des informations affichées en fonction de la case sélectionnée
		 */
		text = new JLabel("");
		text.setBounds(0, 60, 255, 50);
		grille.add(text);
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setHorizontalAlignment(SwingConstants.LEFT);
		text.setBorder(null);
		
		/**
		 * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
		 */
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		scrollPane.setBounds(0, 140, 255, 280);
		grille.add(scrollPane);
		
		/**
		 * Mise en place de la liste contenant les informations sur les individus de la carte
		 */
		liste = new JList(model);
		scrollPane.setViewportView(liste);
		liste.setBorder(null);
		liste.setBackground(new Color(204, 190, 121));

		this.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setSize(1000, 500);
		this.setBounds(300, 200, 1000, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
		
		/**
		 * Définition de l'affichage d'informations dans la liste
		 */
		word = new String("");
		
		/**
		 * Appel de la fonction permettant la création de la carte
		 */
		Traitement t = new Traitement(true, taille, debut, nbOtage, nombre, text, otage, total, list, content, word);
		Build b = new Build(t);
		b.build_map();
		t.start();
		this.t = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/**
		 * Acition pour quitter
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
			JOptionPane.showMessageDialog(this, "Bienvenue sur Vision Détection ! \nL'application qui vous permet d'identifier une anomalie dans un espace défini ou d'identifier le nombre de personnes présentes lors d'une prise d'otage.", "Aide", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * Action fermant la fenêtre actuelle et renvoyant vers la fenêtre d'accueil
		 */
		if(e.getSource()==recherche) {
			JFrame fen = new VisionGUI();
			this.setVisible(false);
		}
	}
}