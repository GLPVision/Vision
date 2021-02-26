package affichage;

import moteur.build;
import moteur.traitement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OtageGUI extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, carte, grille;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem recherche, quitter, sombre, clair, Aide;
	private JTextField nomcarte, info;
	/**
	 * JList contient une liste
	 */
	private JList content;
	/**
	 * Liste contient la carte
	 */
	private DefaultListModel list;
	/**
	 * Nombre d'otages
	 */
	private int nbOtage;
	/**
	 * taille x de la matrice
	 */
	private int x;
	/**
	 * taille y de la matrice
	 */
	private int y;
	
	public OtageGUI(int x, int y, int nbOtage) throws IOException{
		super("Vision Détection : Prise d'otages");
		
		setMinimumSize(new Dimension(1250, 720));
		setPreferredSize(new Dimension(2000, 800));
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		
		menu = new JMenuBar();
		setJMenuBar(menu);
		menu.setBackground(Color.lightGray);
		
		Fichier = new JMenu("Fichier");
		menu.add(Fichier);
		
		Apparence = new JMenu("Apparence");
		menu.add(Apparence);
		
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
		
		recherche = new JMenuItem("Nouvelle Recherche");
		Fichier.add(recherche);
		recherche.addActionListener(this);
		
		quitter = new JMenuItem("Quitter/Fermer Vision Détection");
		Fichier.add(quitter);
		quitter.addActionListener(this);
		
		sombre = new JMenuItem("Thème Sombre");
		Apparence.add(sombre);
		sombre.addActionListener(this);
		
		clair = new JMenuItem("Thème Clair");
		Apparence.add(clair);
		clair.addActionListener(this);
		contentPane.setLayout(null);
		
		carte = new JPanel();
		carte.setLayout(new BorderLayout()); //layout pour remplir le panel
		carte.setBackground(new Color(0, 128, 128));
		carte.setBounds(274, 50, 950, 600);
		list = new DefaultListModel(); //initialisation
		content = new JList(list); //list dans JList
		content.setLayoutOrientation(JList.HORIZONTAL_WRAP); //liste horizontale
		//centre text de chaque cas
		DefaultListCellRenderer cellRenderer = new DefaultListCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		content.setCellRenderer(cellRenderer);
		//taille des cases en fonction du nombre de cases
		content.setFixedCellWidth(carte.getWidth()/x);
		content.setFixedCellHeight(carte.getHeight()/y);
		content.setVisibleRowCount(x); //largeur de x cases
		content.setBackground(new Color(0, 128, 128));
		content.setBorder(null);
		carte.add(content, BorderLayout.CENTER);
		contentPane.add(carte);
		
		nomcarte = new JTextField();
		nomcarte.setBounds(274, 25, 90, 25);
		contentPane.add(nomcarte);
		nomcarte.setColumns(10);
		nomcarte.setText("Cartographie : ");
		nomcarte.setEditable(false);
		nomcarte.setBackground(new Color(204, 190, 121));
		nomcarte.setBorder(null);
		
		info = new JTextField();
		info.setBounds(10, 25, 90, 25);
		contentPane.add(info);
		info.setColumns(10);
		info.setText("Informations : ");
		info.setEditable(false);
		info.setBackground(new Color(204, 190, 121));
		info.setBorder(null);
		
		grille = new JPanel();
		JLabel text = new JLabel("texte");//ajouter un element texte a grille (jtextfield, jtextarea ou jlabel)
		grille.setBounds(10, 50, 255, 600);
		contentPane.add(grille);
		grille.setLayout(new FlowLayout());
		grille.add(text);
		grille.setBackground(new Color(204, 190, 121));

		this.x = x;
		this.y = y;
		this.nbOtage = nbOtage;

		traitement t = new traitement();
		t.creer(true, x, y, nbOtage);
		t.scan();
		t.majGUI(true, text);
		new build(t.getScenario(), x, y, list).start(); //construit la carte dans le gui
		t.supp();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==quitter) {
			System.exit(0);
		}
		
		if(e.getSource()==sombre) {
			contentPane.setBackground(Color.DARK_GRAY);
		}
		
		if(e.getSource()==clair) {
			contentPane.setBackground(Color.white);
		}
		
		if(e.getSource()==Aide) {
			JOptionPane.showMessageDialog(this, "Bienvenue sur Vision Détection ! \nL'application qui vous permet d'identifier une anomalie dans un espace défini ou d'identifier le nombre de personnes présentes lors d'une prise d'otage.", "Aide", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource()==recherche) {
			JFrame fen = new VisionGUI();
			fen.getContentPane().setBackground(Color.DARK_GRAY);
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
			fen.setIconImage(icon.getImage());
	        fen.setResizable(false);
			fen.setSize(560, 260);
			fen.setBounds(300, 200, 560, 260);
			fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fen.setVisible(true);
			this.setVisible(false);
		}
	}
}