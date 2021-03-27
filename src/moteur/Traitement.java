package moteur;

import data.*;

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
    public int scan(){
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
                    return 0;
                }
            }
        }
        return 1;
    }

    public void move(){
        Element[][] tab = carte.getTab(); //recup la carte
        int x = (int) (Math.random()* taille.getX());
        int y = (int) (Math.random()*taille.getY());
        if(!tab[y][x].getDesc().equals(".") && (int) (Math.random()*20) == 0){
            int random = (int) (Math.random()*4);
            switch (3){
                case 0:
                    up(x, y);
                    System.out.println("up");
                    break;
                case 1:
                    down(x, y);
                    System.out.println("down");

                    break;
                case 2:
                    left(x, y);
                    System.out.println("left");

                    break;
                case 3:
                    right(x, y);
                    System.out.println("right");

                    break;
                default :
                    break;
            }
        }
    }

    public void up(int x, int y){ // i=x, j=y
        Element[][] tab = carte.getTab(); //recup la carte
        if(y-1 >= 0 && tab[x][y-1].getDesc().equals(".")){ //si y a la place pour bouger
            Element cache = tab[x][y];
            cache.setCoordonnees(new Coordonnees(x, y-1));
            tab[x][y] = new Element(new Coordonnees(x, y));
            tab[x][y-1] = cache;
            entoure.add(cache);
        }
    }
    public void down(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(y+1 < taille.getY()-1 && tab[x][y+1].getDesc().equals(".")){ //si y a la place pour bouger
            Element cache = tab[x][y];
            cache.setCoordonnees(new Coordonnees(x, y+1));
            tab[x][y] = new Element(new Coordonnees(x, y));
            tab[x][y+1] = cache;
            entoure.add(cache);

        }
    }
    public void left(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x-1 >= 0 && tab[x-1][y].getDesc().equals(".")){ //si y a la place pour bouger
            Element cache = tab[x][y];
            cache.setCoordonnees(new Coordonnees(x-1, y));
            tab[x][y] = new Element(new Coordonnees(x, y));
            tab[x-1][y] = cache;
            entoure.add(cache);

        }
    }
    public void right(int x, int y){
        System.out.println(y + " " + x);

        Element[][] tab = carte.getTab(); //recup la carte
        entoure.remove(0);

        if(x+1 < taille.getX()-1 && tab[x+1][y].getDesc().equals(".")){ //si y a la place pour bouger
            for(Element element : entoure){
                if(element.getCoordonnees().getX() == x && element.getCoordonnees().getY() == y){
                    entoure.remove(element);
                }
            }
            Element cache = tab[x][y];
            cache.setCoordonnees(new Coordonnees(x+1, y));
            entoure.remove(cache);
            tab[x][y] = new Element(new Coordonnees(x, y));
            tab[x+1][y] = cache;
            entoure.add(cache);

        }


    }

    public void next(){
        if(selected == null){
            selectedIndex = 0;
        }
        else{
            selectedIndex++;
            if(selectedIndex>entoure.size()-1){
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
