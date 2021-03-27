package moteur;

import data.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    private HashMap <Coordonnees, Personne> nbTotal = new HashMap<>();
    private HashMap <Coordonnees, Intrusion> intrusion = new HashMap<>();
    private HashMap <Coordonnees, Feu> feu = new HashMap<>();
    private HashMap <Coordonnees, Maladie> maladie = new HashMap<>();
    private HashMap <Coordonnees, Anomalie> inconnue = new HashMap<>();
    private HashMap <Coordonnees, Element> entoure = new HashMap<>();
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
        entoure = new HashMap<>();
        nbTotal = new HashMap<>();
        intrusion = new HashMap<>();
        feu = new HashMap<>();
        maladie = new HashMap<>();
        inconnue = new HashMap<>();
    }

    /**
     * Fonction qui scanne le nombre de personne/anomalies
     * @throws InterruptedException Erreur de sleep
     */
    public int scan(){
        Element[][] tab = carte.getTab(); //recup la carte
        Element element = tab[posX][posY];
        Coordonnees c = element.getCoordonnees();

        if(!circle){
            if(element.getDesc().equals("Personne")) { //si personne
                nbTotal.put(c, new Personne(c));
            }
            else if(element.getDesc().equals("Intrusion")) { //si intrusion
                intrusion.put(c, new Intrusion(c));
            }
            else if(element.getDesc().equals("Feu")) { //si feu
                feu.put(c, new Feu(c));
            }
            else if(element.getDesc().equals("Maladie")) { //si maladie
                maladie.put(c, new Maladie(c));
            }
            else if(element.getDesc().equals("Inconnue")){ //si inconnue
                inconnue.put(c, new Anomalie(c));
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
            if(!element.getDesc().equals(".")){
                entoure.put(c, element);
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
                    return 1;
                }
            }
        }
        return 0;
    }

    public int move(){
        Element[][] tab = carte.getTab(); //recup la carte
        int x = (int) (Math.random()* taille.getX());
        int y = (int) (Math.random()*taille.getY());
        if(!tab[x][y].getDesc().equals(".") && (int) (Math.random()*10) == 0){
            int random = (int) (Math.random()*4);
            switch (random){
                case 0:
                    up(x, y);
                    break;
                case 1:
                    down(x, y);
                    break;
                case 2:
                    left(x, y);
                    break;
                case 3:
                    right(x, y);
                    break;
                default :
                    break;
            }
            return 1;
        }
        return 0;
    }

    public void up(int x, int y){ // i=x, j=y
        Element[][] tab = carte.getTab(); //recup la carte
        if(y-1 >= 0 && tab[x][y-1].getDesc().equals(".")){ //si y a la place pour bouger
            for(Coordonnees c : entoure.keySet()) { //elever cercle rouge et element
                if (entoure.get(c).getCoordonnees().getX() == x && entoure.get(c).getCoordonnees().getY() == y) {
                    remove(c);
                    break;
                }
            }
            Element element = tab[x][y];
            swap(element, x, y, 0, -1);
            add(element);
        }
    }
    public void down(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(y+1 < taille.getY() && tab[x][y+1].getDesc().equals(".")){ //si y a la place pour bouger
            for(Coordonnees c : entoure.keySet()) { //elever cercle rouge et element
                if (entoure.get(c).getCoordonnees().getX() == x && entoure.get(c).getCoordonnees().getY() == y) {
                    remove(c);
                    break;
                }
            }
            Element element = tab[x][y];
            swap(element, x, y, 0, 1);
            add(element);
        }
    }
    public void left(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x-1 >= 0 && tab[x-1][y].getDesc().equals(".")){ //si y a la place pour bouger
            for(Coordonnees c : entoure.keySet()) { //elever cercle rouge et element
                if (entoure.get(c).getCoordonnees().getX() == x && entoure.get(c).getCoordonnees().getY() == y) {
                    remove(c);
                    break;
                }
            }
            Element element = tab[x][y];
            swap(element, x, y, -1, 0);
            add(element);
        }
    }

    public void right(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x+1 < taille.getX() && tab[x+1][y].getDesc().equals(".")){ //si y a la place pour bouger
            for(Coordonnees c : entoure.keySet()) { //elever cercle rouge et element
                if (entoure.get(c).getCoordonnees().getX() == x && entoure.get(c).getCoordonnees().getY() == y) {
                    remove(c);
                    break;
                }
            }
            Element element = tab[x][y];
            swap(element, x, y, 1, 0);
            add(element);
        }
    }
    public void swap(Element element, int x, int y, int dx, int dy){
        Element[][] tab = carte.getTab(); //recup la carte
        Element cache = tab[x][y];
        element.setCoordonnees(new Coordonnees(x+dx, y+dy));
        tab[x][y] = new Element(new Coordonnees(x, y));
        tab[x+dx][y+dy] = cache;
    }

    public void remove(Coordonnees coordonnees){
        entoure.remove(coordonnees);
        nbTotal.remove(coordonnees);
        intrusion.remove(coordonnees);
        feu.remove(coordonnees);
        maladie.remove(coordonnees);
        inconnue.remove(coordonnees);
    }

    public void add(Element element){
        entoure.put(element.getCoordonnees(), element);
        switch(element.getDesc()){
            case "Personne":
                nbTotal.put(element.getCoordonnees(), (Personne) element);
                break;
            case "Intrusion":
                intrusion.put(element.getCoordonnees(), (Intrusion) element);
                break;
            case "Feu":
                feu.put(element.getCoordonnees(), (Feu) element);
                break;
            case "Maladie":
                maladie.put(element.getCoordonnees(), (Maladie) element);
                break;
            case "Inconnue":
                inconnue.put(element.getCoordonnees(), (Anomalie) element);
            default:
                break;
        }
    }

    public void next(){
        Collection<Coordonnees> values = entoure.keySet();
        ArrayList<Coordonnees> elements = new ArrayList<>(values);
        if(selected == null){
            selectedIndex = 0;
        }
        else{
            selectedIndex++;
            if(selectedIndex>elements.size()-1){
                selectedIndex = 0;
            }
        }
        selected = entoure.get(elements.get(selectedIndex));
    }

    public void previous(){
        Collection<Coordonnees> values = entoure.keySet();
        ArrayList<Coordonnees> elements = new ArrayList<>(values);
        if(selected == null){
            selectedIndex = elements.size()-1;
        }
        else{
            selectedIndex--;
            if(selectedIndex<0){
                selectedIndex = entoure.size()-1;
            }
        }
        selected = entoure.get(elements.get(selectedIndex));
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

    public HashMap<Coordonnees, Personne> getNbTotal() {
        return nbTotal;
    }

    public HashMap<Coordonnees, Intrusion> getIntrusion() {
        return intrusion;
    }

    public HashMap<Coordonnees, Feu> getFeu() {
        return feu;
    }

    public HashMap<Coordonnees, Maladie> getMaladie() {
        return maladie;
    }

    public HashMap<Coordonnees, Anomalie> getInconnue() {
        return inconnue;
    }

    public HashMap<Coordonnees, Element> getEntoure() {
        return entoure;
    }

    public Element getSelected() {
        return selected;
    }

    public void setSelected(Element selected) {
        this.selected = selected;
    }
}
