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
public class Traitement{
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
    private int selectedIndex;
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
     * Otage ou Agricole
     */
    private boolean otage;
    /**
     * Taille
     */
    private Coordonnees taille, debut;
    private int posX = 0;
    private int posY = 0;
    private boolean circle = false;

    /**
     * Constructeur, initialise les variables
     * @param taille
     */
    public Traitement(Coordonnees taille, Coordonnees debut){ //agricole
        this.scenario = new Agriculture(taille);
        this.carte = scenario.getCarte();
        this.otage = false;
        this.taille = taille;
        this.debut = debut;
        init();
    }

    public Traitement(Coordonnees taille, Coordonnees debut, int nbOtage){ //otage
        this.scenario = new Otage(taille, nbOtage);
        this.carte = scenario.getCarte();
        this.otage = true;
        this.taille = taille;
        this.debut = debut;
        this.nbOtage = nbOtage;
        init();
    }

    public void init(){
        entoure = new ArrayList<>();
        nbTotal = new ArrayList<>();
        intrusion = new ArrayList<>();
        feu = new ArrayList<>();
        maladie = new ArrayList<>();
        inconnue = new ArrayList<>();
    }

    /**
     * Fonction qui scanne le nombre de personne/anomalies
     * @throws InterruptedException Erreur de sleep
     */
    public void scan(){
        Element[][] tab = carte.getTab(); //recup la carte
        Element element = tab[posX][posY];
        Coordonnees c = element.getCoordonnees();

        if(!circle){
            if(element.getDesc().equals("Personne")) { //si personne
                nbTotal.add(new Personne(c));
            }
            else if(element.getDesc().equals("Intrusion")) { //si intrusion
                intrusion.add(new Intrusion(c));
            }
            else if(element.getDesc().equals("Feu")) { //si feu
                feu.add(new Feu(c));
            }
            else if(element.getDesc().equals("Maladie")) { //si maladie
                maladie.add(new Maladie(c));
            }
            else if(element.getDesc().equals("Inconnue")){ //si inconnue
                inconnue.add(new Anomalie(c));
            }
            posX++;
            if(posX>=taille.getX()){
                posX = 0;
                posY++;
                if(posY >= taille.getY()){
                    posY = 0;
                    circle = true;
                }
            }
        }
        else{ //entourer
            if(element.getDesc().equals("Personne")) { //si personne
                entoure.add(new Personne(c));
            }
            else if(element.getDesc().equals("Intrusion")) { //si intrusion
                entoure.add(new Intrusion(c));
            }
            else if(element.getDesc().equals("Feu")) { //si feu
                entoure.add(new Feu(c));
            }
            else if(element.getDesc().equals("Maladie")) { //si maladie
                entoure.add(new Maladie(c));
            }
            else if(element.getDesc().equals("Inconnue")){ //si inconnue
                entoure.add(new Anomalie(c));
            }

            if(otage) { //si derniere iteration otage
                nbAssaillant = nbTotal.size() - nbOtage;
            }
            posX++;
            if(posX>=taille.getX()){
                posX = 0;
                posY++;
                if(posY >= taille.getY()){
                    posY = 0;
                    //circle = false;
                    //init();
                }
            }
        }
    }

    public void next(){
        if(selected == null){
            selectedIndex = 0;
        }
        else{
            selectedIndex++;
            if(selectedIndex>entoure.size()){
                selectedIndex = 0;
            }
        }
        selected = entoure.get(selectedIndex);
    }

    public void previous(){
        if(selected == null){
            selectedIndex = entoure.size()-1;
        }
        else{
            selectedIndex--;
            if(selectedIndex<0){
                selectedIndex = entoure.size()-1;
            }
        }
        selected = entoure.get(selectedIndex);
    }







    /**
     * Fonction qui remet tout à zéro
     */
    public void supp() {
        this.nbOtage = 0;
        this.nbAssaillant = 0;
        this.nbTotal = null;
        this.inconnue = null;
        this.feu = null;
        this.maladie = null;
        this. intrusion = null;
        this.carte = null;
        this.scenario = null;
    }

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
     * Fonction qui retourne le nombre d'otages
     * @return Otage
     */
    public int getNbOtage() {
        return nbOtage;
    }

    public Coordonnees getDebut() {
        return debut;
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

    public void setSelected(Element selected) {
        this.selected = selected;
    }
}
