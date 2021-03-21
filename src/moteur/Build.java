package moteur;

import data.Carte;
import data.Coordonnees;
import data.Element;
import data.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe Build
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 10
 */
public class Build {
    /**
     * Scénario
     */
    private Scenario scenario;
    /**
     * Taille de la matrice/carte
     */
    private Coordonnees taille;
    /**
     * Liste contenant des ImageIcon lié à la JList du GUI
     */
	private DefaultListModel<ImageIcon> list;
    /**
     * Image de personne
     */
    private ImageIcon personne = new ImageIcon(ClassLoader.getSystemResource("personne.png"));
    /**
     * Image d'intrusion
     */
    private ImageIcon intrusion = new ImageIcon(ClassLoader.getSystemResource("intrusion.png"));
    /**
     * Image de maladie
     */
    private ImageIcon maladie = new ImageIcon(ClassLoader.getSystemResource("virus.png"));
    /**
     * Image de feu
     */
    private ImageIcon feu = new ImageIcon(ClassLoader.getSystemResource("feu.png"));
    /**
     * Image d'inconnu
     */
    private ImageIcon inconnue = new ImageIcon(ClassLoader.getSystemResource("inconnue.png"));
    /**
     * Image de blé
     */
    private ImageIcon ble = new ImageIcon(ClassLoader.getSystemResource("champ.jpg"));
    /**
     * Image de bâtiment
     */
    private ImageIcon bat = new ImageIcon(ClassLoader.getSystemResource("fondotage.png"));
    /**
     * Otage ou Agricole
     */
    private boolean otage;
    /**
     * Constructeur, initialise les variables
     * @param t Toutes les données
     */
	public Build(Traitement t){
        this.scenario = t.getScenario();
        this.taille = t.getTaille();
        //this.list = t.getList();
        this.otage = t.getOtage();
    }
    /**
     * Fonction qui construit la carte dans le GUI
     */
	public void build_map(){
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i< taille.getY() ; i++){ //parcours x
            for (int j=0 ; j<taille.getX() ; j++){ //parcours y
                ImageIcon img = null; //initialisation
                switch(tab[i][j].getDesc()){
                    case ".":
                        if(otage)
                            img = bat; //fond
                        else
                            img = ble; //fond
                        break;
                    case "p":
                        img = personne;
                        break;
                    case "i":
                        img = intrusion;
                        break;
                    case "m":
                        img = maladie;
                        break;
                    case "f":
                        img = feu;
                        break;
                    case "x":
                        img = inconnue;
                        break;
                }
                if(img == ble || img == bat){ //si fond
                    list.addElement(new ImageIcon(img.getImage().getScaledInstance(950/taille.getX(), 600/taille.getY(), Image.SCALE_DEFAULT))); //ajout à la liste
                }
                else{ //sinon applique fond à l'image
                    if(otage)
                        list.addElement(merge(new ImageIcon(bat.getImage().getScaledInstance(950/taille.getX(), 600/taille.getY(), Image.SCALE_DEFAULT)), new ImageIcon(img.getImage().getScaledInstance(Math.min(950/taille.getX()/2, 600/taille.getY()/2), Math.min(950/taille.getX()/2, 600/taille.getY()/2), Image.SCALE_DEFAULT)))); //ajout à la liste
                    else
                        list.addElement(merge(new ImageIcon(ble.getImage().getScaledInstance(950/taille.getX(), 600/taille.getY(), Image.SCALE_DEFAULT)), new ImageIcon(img.getImage().getScaledInstance(Math.min(950/taille.getX()/2, 600/taille.getY()/2), Math.min(950/taille.getX()/2, 600/taille.getY()/2), Image.SCALE_DEFAULT)))); //ajout à la liste
                }
            }
        }
    }
    /**
     * Fonction qui fusionne deux ImageIcon
     * @param a ImageIcon d'arrière plan
     * @param b ImageIcon de premier plan
     * @return ImageIcon fusionné
     */
    public static ImageIcon merge(ImageIcon a, ImageIcon b){ //a dessous et b dessus, a plus grand
        Image imga = a.getImage();
        Image imgb = b.getImage();

        BufferedImage bi = new BufferedImage(imga.getWidth(null), imga.getHeight(null), BufferedImage.TYPE_INT_ARGB); //image vide de bonne taille
        Graphics2D g = bi.createGraphics(); //initialisation
        g.drawImage(imga, 0, 0, null); //dessine ImageIcon a
        g.drawImage(imgb, imga.getWidth(null)/2- imgb.getWidth(null)/2, imga.getHeight(null)/2- imgb.getHeight(null)/2, null); //dessine ImageIcon b avec bonne position car taille différente
        g.dispose(); //fermer
        return new ImageIcon(bi);
    }

    /**
     * Fonction qui retourne le Scénario
     * @return Scénario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Fonction qui retourne la taille de la carte
     * @return Taille
     */
    public Coordonnees getTaille() {
        return taille;
    }

    /**
     * Fonction qui retourne la liste des ImageIcon lié à la JList
     * @return Liste
     */
    public DefaultListModel<ImageIcon> getList() {
        return list;
    }

    /**
     * Fonction qui retourne si c'est un scénario d'otage ou agricole
     * @return Otage
     */
    public boolean isOtage() {
        return otage;
    }

    /**
     * Fonction qui retourne l'image de personne
     * @return Personne
     */
    public ImageIcon getPersonne() {
        return personne;
    }

    /**
     * Fonction qui retourne l'image d'intrusion
     * @return Intrusion
     */
    public ImageIcon getIntrusion() {
        return intrusion;
    }

    /**
     * Fonction qui retourne l'image de maladie
     * @return Maladie
     */
    public ImageIcon getMaladie() {
        return maladie;
    }

    /**
     * Fonction qui retourne l'image de feu
     * @return Feu
     */
    public ImageIcon getFeu() {
        return feu;
    }

    /**
     * Fonction qui retourne l'image d'inconnu
     * @return Inconnue
     */
    public ImageIcon getInconnue() {
        return inconnue;
    }

    /**
     * Fonction qui retourne l'image de blé
     * @return Blé
     */
    public ImageIcon getBle() {
        return ble;
    }

    /**
     * Fonction qui retourne l'image de bâtiment
     * @return Bâtiment
     */
    public ImageIcon getBat() {
        return bat;
    }
}
