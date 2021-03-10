package moteur;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

/**
 * Classe Traitement
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 10
 */
public class Traitement extends Thread {
    /**
     * Nombre d'otages, nombre d'assaillants, nombre total de personnes, nombre d'intrusions, nombre de feux, nombre de maladies, nombre d'inconnues
     */
    private int nbOtage, nbAssaillant, nbTotal, intrusion, feu, maladie, inconnue;
    /**
     * Carte
     */
    private Carte carte;
    /**
     * Scénario
     */
    private Scenario scenario;
    /**
     * Les différentes zones de textes du GUI
     */
    private JLabel jl1, jl2, jl3, jl4;
    /**
     * Contenu d'une JLabel
     */
	private String word;
    /**
     * Otage ou Agricole
     */
    private boolean otage;
    /**
     * Liste contenant des ImageIcon lié à la JList du GUI
     */
    private DefaultListModel<ImageIcon> list;
    /**
     * JList du GUI
     */
    private JList content;
    /**
     * Image de cercle
     */
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    /**
     * Nouvelle ligne
     */
    private String Newligne=System.getProperty("line.separator");
    /**
     * Taille
     */
    private Coordonnees taille;
    /**
     * Coordonnées de début
     */
    private Coordonnees debut;
    
    /**
     * Constructeur, initialise les variables
     * @param otage Otage ou Agricole
     * @param taille Taille de la carte/matrice
     * @param debut Coordonnées de début
     * @param nbOtage Nombre d'otages
     * @param jl1 JLabel
     * @param jl2 JLabel
     * @param jl3 JLabel
     * @param jl4 JLabel
     * @param list Liste lié à la JList du GUI
     * @param content JList
     * @param word Contenu d'un JLabel
     */
    public Traitement(boolean otage, Coordonnees taille, Coordonnees debut, int nbOtage, JLabel jl1, JLabel jl2, JLabel jl3, JLabel jl4, DefaultListModel list, JList content, String word){
        if(otage){
            scenario = new Otage(taille, nbOtage);
        }
        else {
            scenario = new Agriculture(taille);
        }
        carte = scenario.getCarte();
        this.otage = otage;
        this.taille = taille;
        this.debut = debut;
        this.nbOtage = nbOtage;
        this.jl1 = jl1;
        this.jl2 = jl2;
        this.jl3 = jl3;
        this.jl4 = jl4;
        this.list = list;
        this.content = content;
        this.word = word;
    }

    /**
     * Fonction run pour démarrer le Thread
     */
    public void run(){
        try {
            scan();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui scanne le nombre de personne/anomalies
     * @throws InterruptedException Erreur de sleep
     */
    public void scan() throws InterruptedException {
        Element[][] tab = carte.getTab(); //recup la carte
        for(int i = 0 ; i<taille.getY() ; i++){ //parcours y
            for(int j = 0 ; j<taille.getX() ; j++){ //parcours x
                if(tab[i][j].getDesc() == "p") //si personne
                    nbTotal++;
                else if(tab[i][j].getDesc() == "i") //si intrusion
                    intrusion++;
                else if(tab[i][j].getDesc() == "f") //si feu
                    feu++;
                else if(tab[i][j].getDesc() == "m") //si maladie
                    maladie++;
                else if(tab[i][j].getDesc() == "x") //si inconnue
                    inconnue++;
                if(tab[i][j].getDesc() != "."){ //si pas rien, entourer
                    entourer(i, j); //entourer
                    content.setSelectedIndex(i*taille.getX()+j); //selectionner la case
                }
                majGUI(); //mise à jour progressive du GUI
                sleep(30); //latence
            }
        }
        if(otage) { //si derniere iteration otage
            nbAssaillant = nbTotal - nbOtage;
            majGUI();
        }
    }

    /**
     * Fonction qui remet tout à zéro
     */
    public void supp() {
        this.nbOtage = 0;
        this.nbAssaillant = 0;
        this.nbTotal = 0;
        this.inconnue = 0;
        this.feu = 0;
        this.maladie = 0;
        this. intrusion = 0;
        this.carte = null;
        this.scenario = null;
        this.interrupt();
    }

    /**
     * Fonction qui modifie le GUI
     */
    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){
            jl1.setText("    Nombre total d'individus : " + nbTotal);
            jl3.setText("    Nombre d'otages : " + nbOtage);
            jl4.setText("    Nombre total d'assaillants : " + nbAssaillant);


        }
        else{
            jl1.setText("   Total : " + (inconnue+feu+maladie+intrusion));
            jl3.setText("<html> &nbsp &#160 Nombre de feux : " + feu + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + intrusion + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + maladie + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + inconnue + "</html>");
        }
    }

    /**
     * Fonction qui entoure une image
     * @param x Coordonnées X
     * @param y Coordonnées Y
     */
    public void entourer(int x, int y){
        list.set(x*taille.getX()+y, Build.merge((ImageIcon) list.getElementAt(x*taille.getX()+y), new ImageIcon(cercle.getImage().getScaledInstance(Math.min(950/taille.getX(), 600/taille.getY()), Math.min(950/taille.getX(), 600/taille.getY()), Image.SCALE_DEFAULT)))); //entourer
    }

    /**
     * Fonction qui analyse la case séléctionnée dans le GUI
     */
    public void current(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX(); //X
        int c2 = selected/taille.getX(); //Y
        String type = null; //initialisation
        switch(carte.getTab()[c2][c1].getDesc()){
            case ".":
                type = "Aucune";
                break;
            case "p":
                type = "Individu";
                break;
            case "i":
                type = "Intrusion";
                break;
            case "m":
                type = "Maladie";
                break;
            case "f":
                type = "Feu";
                break;
            case "x":
                type = "Inconnue";
                break;
            default:
                break;
        }
        
        if(otage){
        	if(type != "Aucune") {
        		int x = debut.getX()+c1;
        		int y = debut.getY()+c2;
        		jl2.setText("<html> &nbsp &#160 Coordonnées : x=" + x + ", y=" + y + "<br/>" + 
        		"&nbsp &#160 Individu : OUI </html>");
        		word = "   Individu en : x =" + x + ", y =" + y;
        	}
        	else {
        		int x = debut.getX()+c1;
        		int y = debut.getY()+c2;
        		jl2.setText("<html> &nbsp &#160 Coordonnées : x=" + x + ", y=" + y + "<br/>" + 
                		"&nbsp &#160 Individu : NON </html>");
        	}
        }

        else {
        	if(type != "Aucune") {
        		int x = debut.getX()+c1;
        		int y = debut.getY()+c2;
        		jl2.setText("<html> &nbsp &#160 Type d'anomalie : " + type + "<br/>" +
        				" &nbsp &#160 Coordonnées : x=" + x + ", y=" + y + "</html>");
        		word = "   " + type  + " en : " + x + "," + y ;
        	}
        	else {
        		int x = debut.getX()+c1;
        		int y = debut.getY()+c2;
        		jl2.setText("<html> &nbsp &#160 Type d'anomalie : " + type + "<br/>" +
        				" &nbsp &#160 Coordonnées : x=" + x + ", y=" + y + "</html>");
        	}
        }
        

    }

    /**
     * Fonction qui cherche la prochaine personne/anomalie dans le GUI
     */
    public void next(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX(); //X
        int c2 = selected/taille.getX(); //Y
        Element[][] elt = carte.getTab(); //recup la carte
        int j; //initialisation
        for(int i = c2 ; i<taille.getY() ; i++) { //parcours y à partir de la personne/anomalie actuelle
            if(i == c2)
                j = c1;
            else
                j=0;
            for (; j<taille.getX(); j++) { //parcours x
                if(elt[i][j].getDesc()!="." && i* taille.getY()+j>c2* taille.getY()+c1){
                    content.setSelectedIndex(i*taille.getY()+j);
                    i = taille.getX();
                    break; //sortir quand trouvé
                }
            }
        }
    }

    /**
     * Fonction qui cherche la personne/anomalie précédente dans le GUI
     */
    public void previous(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX(); //X
        int c2 = selected/taille.getX(); //Y
        Element[][] elt = carte.getTab(); //recup la carte
        int j;
        for(int i = c2 ; i>=0 ; i--) { //parcours y
            if(i == c2)
                j = c1;
            else
                j = taille.getX()-1;
            for (; j>=0 ; j--) { //parcours x
                if(elt[i][j].getDesc()!="." && i* taille.getY()+j<c2* taille.getY()+c1){
                    content.setSelectedIndex(i*taille.getY()+j);
                    content.revalidate();
                    content.repaint();
                    i = -1;
                    break; //sortir quand trouvé
                }
            }
        }
    }

    /**
     * Fonction qui retourne le Scénario
     * @return Scénario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Fonction qui retourn si Otage ou Agricole
     * @return Otage ou Agricole
     */
    public boolean getOtage() {
        return otage;
    }

    /**
     * Fonction qui retourne la liste lié à la JList du GUI
     * @return Liste
     */
    public DefaultListModel getList() {
        return list;
    }

    /**
     * Fonction qui retourne la carte/matrice
     * @return Matrice
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Fonction qui retourne le contenu destiné à un JLabel
     * @return Texte
     */
    public String getWord() {
    	return this.word;
    }

    /**
     * Fonction qui transforme un JLabel avec du html en String
     * @param jl JLabel
     * @return String
     */
    public String makeString (JLabel jl) {//This is the method which converts the Jlabel into a String
		   ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		   XMLEncoder e = new XMLEncoder (baos);
		   e.writeObject (jl);
		   e.close ();
		   return new String (baos.toByteArray ());
    }

    /**
     * Fonction qui retourne la taille de la carte/matrice
     * @return Taille
     */
    public Coordonnees getTaille() {
        return taille;
    }

    /**
     * Fonction qui retourne le nombre d'intrusions
     * @return Intrusion
     */
    public int getIntrusion() {
        return intrusion;
    }

    /**
     * Fonction qui retourne le nombre de feux
     * @return Feu
     */
    public int getFeu() {
        return feu;
    }

    /**
     * Fonction qui retourne le nombre de maladies
     * @return Maladie
     */
    public int getMaladie() {
        return maladie;
    }

    /**
     * Fonction qui retourne le nombre d'inconnues
     * @return Inconnue
     */
    public int getInconnue() {
        return inconnue;
    }

    /**
     * Fonction qui retourne si Otage ou Agricole
     * @return Otage ou Agricole
     */
    public boolean isOtage() {
        return otage;
    }

    /**
     * Fonction qui retourne la JList
     * @return JList
     */
    public JList getContent() {
        return content;
    }

    /**
     * Fonction qui retourne l'iamge de cercle
     * @return Cercle
     */
    public ImageIcon getCercle() {
        return cercle;
    }

    /**
     * Fonction qui retourne les coordonnées de début
     * @return Début
     */
    public Coordonnees getDebut() {
        return debut;
    }

    /**
     * Fonction qui retourne le nombre d'otages
     * @return Otage
     */
    public int getNbOtage() {
        return nbOtage;
    }

    /**
     * Fonction qui retourne le nombre d'assaillants
     * @return Assaillant
     */
    public int getNbAssaillant() {
        return nbAssaillant;
    }

    /**
     * Fonction qui retourne le total de personnes
     * @return Personnes
     */
    public int getNbTotal() {
        return nbTotal;
    }
}
