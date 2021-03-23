package moteur;

import data.*;

import javax.swing.*;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Classe Traitement
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 10
 */
public class Traitement extends Thread {
    /**
     * Nombre d'otages, nombre d'assaillants, nombre total de personnes, nombre d'intrusions, nombre de feux, nombre de maladies, nombre d'inconnues
     */
    private int nbOtage, nbAssaillant;
    private ArrayList <Personne> nbTotal = new ArrayList<>();
    private ArrayList <Intrusion> intrusion = new ArrayList<>();
    private ArrayList <Feu> feu = new ArrayList<>();
    private ArrayList <Maladie> maladie = new ArrayList<>();
    private ArrayList <Anomalie> inconnue = new ArrayList<>();
    private ArrayList <Element> entoure = new ArrayList<>();
    private Element selected;
    /**
     * Carte
     */
    private Carte carte;
    /**
     * Scénario
     */
    private Scenario scenario;
    /**
     * Contenu d'une JLabel
     */
	private String word;
    /**
     * Otage ou Agricole
     */
    private boolean otage;
    /**
     * Nouvelle ligne
     */
	@SuppressWarnings("unused")
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
     * @param otage
     * @param taille
     * @param debut
     * @param nbOtage
     */
    public Traitement(boolean otage, Coordonnees taille, Coordonnees debut, int nbOtage){
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
        entoure = new ArrayList<>();
        for(int i = 0 ; i<taille.getY() ; i++){ //parcours y
            for(int j = 0 ; j<taille.getX() ; j++){ //parcours x
                Coordonnees c = new Coordonnees(j, i);
                if(tab[i][j].getDesc() != "."){ //si pas rien, entourer
                    selected = new Element(c);
                }
                if(tab[i][j].getDesc() == "Personne") { //si personne
                    nbTotal.add(new Personne(c));
                    entoure.add(new Personne(c));
                    selected.setDesc(tab[i][j].getDesc());
                }
                else if(tab[i][j].getDesc() == "Intrusion") { //si intrusion
                    intrusion.add(new Intrusion(c));
                    entoure.add(new Intrusion(c));
                    selected.setDesc(tab[i][j].getDesc());
                }
                else if(tab[i][j].getDesc() == "Feu") { //si feu
                    feu.add(new Feu(c));
                    entoure.add(new Feu(c));
                    selected.setDesc(tab[i][j].getDesc());
                }
                else if(tab[i][j].getDesc() == "Maladie") { //si maladie
                    maladie.add(new Maladie(c));
                    entoure.add(new Maladie(c));
                    selected.setDesc(tab[i][j].getDesc());
                }
                else if(tab[i][j].getDesc() == "Inconnue"){ //si inconnue
                    inconnue.add(new Anomalie(c));
                    entoure.add(new Anomalie(c));
                    selected.setDesc(tab[i][j].getDesc());
                }

                //majGUI(); //mise à jour progressive du GUI
                //sleep(30); //latence
            }
        }
        if(otage) { //si derniere iteration otage
            nbAssaillant = nbTotal.size() - nbOtage;
        }
    }

    /**
     * Fonction qui remet tout à zéro
     */
        /*
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
    */

    /**
     * Fonction qui modifie le GUI
     */
    /*
    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){
            jl1.setText("    Nombre total d'individus : " + nbTotal.size());
            jl3.setText("    Nombre d'otages : " + nbOtage);
            jl4.setText("    Nombre total d'assaillants : " + nbAssaillant);


        }
        else{
            jl1.setText("   Total : " + (inconnue.size()+feu.size()+maladie.size()+intrusion.size()));
            jl3.setText("<html> &nbsp &#160 Nombre de feux : " + feu.size() + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + intrusion.size() + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + maladie.size() + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + inconnue.size() + "</html>");
        }
    }

     */

    /**
     * Fonction qui entoure une image
     * @param x Coordonnées X
     * @param y Coordonnées Y
     */
    /*
    public void entourer(int x, int y){
        list.set(x*taille.getX()+y, Build.merge((ImageIcon) list.getElementAt(x*taille.getX()+y), new ImageIcon(cercle.getImage().getScaledInstance(Math.min(950/taille.getX(), 600/taille.getY()), Math.min(950/taille.getX(), 600/taille.getY()), Image.SCALE_DEFAULT)))); //entourer
    }

     */

    /**
     * Fonction qui analyse la case séléctionnée dans le GUI
     */
    /*
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

     */

    /**
     * Fonction qui cherche la prochaine personne/anomalie dans le GUI
     */
    /*
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

     */

    /**
     * Fonction qui cherche la personne/anomalie précédente dans le GUI
     */
    /*
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

     */

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
     * Fonction qui retourne si Otage ou Agricole
     * @return Otage ou Agricole
     */
    public boolean isOtage() {
        return otage;
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

	public ArrayList<Personne> getNbTotal() {
        return nbTotal;
    }

	public ArrayList<Intrusion> getIntrusion() {
        return intrusion;
    }

	public ArrayList<Feu> getFeu() {
        return feu;
    }

	public ArrayList<Maladie> getMaladie() {
        return maladie;
    }

	public ArrayList<Anomalie> getInconnue() {
        return inconnue;
    }

    public ArrayList<Element> getEntoure() {
        return entoure;
    }

    public Element getSelected() {
        return selected;
    }
}
