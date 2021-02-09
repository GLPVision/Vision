package affichage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OtageGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem ouvrir, quitter, sombre, clair, Aide;
	private JButton recherche;
	
	public OtageGUI(){
		super("Vision Détection : Agricole");
		
		setMinimumSize(new Dimension(800, 650));
		setPreferredSize(new Dimension(2000, 800));
		
		contentPane = new JPanel();
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
		
		ouvrir = new JMenuItem("Ouvrir");
		Fichier.add(ouvrir);
		ouvrir.addActionListener(this);
		
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
		
		recherche =  new JButton ("Nouvelle recherche");
		recherche.setBackground(new Color(204, 190, 121));
		getContentPane().add(recherche);
		recherche.addActionListener(this);
		recherche.setBounds(10, 10, 150, 25);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
			JOptionPane.showMessageDialog(this, "Bienvenue sur Vision Détection ! \nL'application qui vous permet d'identifier une anomalie dans un espace définit ou d'identifier le nombre de personnes présentes lors d'une prise d'otage.", "Aide", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource()==recherche) {
			JDialog bd = new JDialog(this, "Option de recherche", true);
			bd.setBackground(Color.lightGray);
			bd.setSize(250, 100);
			bd.setBounds(750, 350, 250, 250);
			bd.setVisible(true);
		}
	}
	
}
