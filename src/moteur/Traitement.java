package moteur;

import config.Configuration;
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
    private int nbOtage, nbAssaillant, nbTotal, nbIndividu, nbIntrusion, nbFeu, nbMaladie, nbInconnue;
    private ArrayList <Personne> individu = new ArrayList<>();
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
        individu = new ArrayList<>();
        intrusion = new ArrayList<>();
        feu = new ArrayList<>();
        maladie = new ArrayList<>();
        inconnue = new ArrayList<>();
        nbTotal = 0;
        nbIndividu = 0;
        nbIntrusion = 0;
        nbFeu = 0;
        nbMaladie = 0;
        nbInconnue = 0;
    }

    /**
     * Fonction qui scanne le nombre de personne/anomalies
     */
    public int scan(){
        Element[][] tab = carte.getTab(); //recup la carte
        Element element = tab[posX][posY];
        Coordonnees c = element.getCoordonnees();

        if(!circle){
            if(element.getDesc().equals("Personne")) { //si personne
                individu.add(new Personne(c));
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
            if(!element.getDesc().equals(".")){
                entoure.add(element);
            }
            if(element.getDesc().equals("Personne")) { //si personne
                nbIndividu++;
            }
            else if(element.getDesc().equals("Intrusion")) { //si intrusion
                nbIntrusion++;
            }
            else if(element.getDesc().equals("Feu")) { //si feu
                nbFeu++;
            }
            else if(element.getDesc().equals("Maladie")) { //si maladie
                nbMaladie++;
            }
            else if(element.getDesc().equals("Inconnue")){ //si inconnue
                nbInconnue++;
            }
            posX++;
            if(posX>=taille.getX()){
                posX = 0;
                posY++;
                if(posY >= taille.getY()){
                    if(otage) {
                        nbAssaillant = nbIndividu - nbOtage;
                    }
                    posY = 0;
                    return 1;
                }
            }
        }
        return 0;
    }

    public int move(){
        if((int) (Math.random()* Configuration.PROBA_MOVE) == 0){
            Element e = entoure.get((int) (Math.random() * entoure.size()));
            int x = e.getCoordonnees().getX();
            int y = e.getCoordonnees().getY();
            switch ((int) (Math.random()*4)){
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
            change(tab[x][y], x, y, 0, -1);
        }
    }
    public void down(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(y+1 < taille.getY() && tab[x][y+1].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, 0, 1);
        }
    }
    public void left(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x-1 >= 0 && tab[x-1][y].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, -1, 0);
        }
    }

    public void right(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x+1 < taille.getX() && tab[x+1][y].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, 1, 0);
        }
    }

    public void change(Element element, int x, int y, int dx, int dy){
        for(int i=0 ; i<entoure.size() ; i++){
            if (entoure.get(i).getCoordonnees().getX() == x && entoure.get(i).getCoordonnees().getY() == y) {
                entoure.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                break;
            }
        }
        switch(element.getDesc()){
            case "Personne":
                for(int i=0 ; i<individu.size() ; i++){
                    if (individu.get(i).getCoordonnees().getX() == x && individu.get(i).getCoordonnees().getY() == y) {
                        individu.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            case "Intrusion":
                for(int i=0 ; i<intrusion.size() ; i++){
                    if (intrusion.get(i).getCoordonnees().getX() == x && intrusion.get(i).getCoordonnees().getY() == y) {
                        intrusion.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            case "Feu":
                for(int i=0 ; i<feu.size() ; i++){
                    if (feu.get(i).getCoordonnees().getX() == x && feu.get(i).getCoordonnees().getY() == y) {
                        feu.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            case "Maladie":
                for(int i=0 ; i<maladie.size() ; i++){
                    if (maladie.get(i).getCoordonnees().getX() == x && maladie.get(i).getCoordonnees().getY() == y) {
                        maladie.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            case "Inconnue":
                for(int i=0 ; i<inconnue.size() ; i++){
                    if (inconnue.get(i).getCoordonnees().getX() == x && inconnue.get(i).getCoordonnees().getY() == y) {
                        inconnue.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            default:
                break;
        }
        Element[][] tab = carte.getTab(); //recup la carte
        Element cache = tab[x][y];
        element.setCoordonnees(new Coordonnees(x+dx, y+dy));
        tab[x][y] = new Element(new Coordonnees(x, y));
        tab[x+dx][y+dy] = cache;
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
        nbOtage = 0;
        nbAssaillant = 0;
        nbTotal = 0;
        nbTotal = 0;
        nbIndividu = 0;
        nbIntrusion = 0;
        nbFeu = 0;
        nbMaladie = 0;
        nbInconnue = 0;
        inconnue = null;
        feu = null;
        maladie = null;
        intrusion = null;
        carte = null;
        scenario = null;
    }


    /**
     * Fonction qui retourne le Scénario
     * @return Scénario
     */
    public Scenario getScenario() {
        return scenario;
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

    public Coordonnees getDebut() {
        return debut;
    }

    /**
     * Fonction qui retourne si Otage ou Agricole
     * @return Otage ou Agricole
     */
    public boolean isOtage() {
        return otage;
    }

    public int getNbOtage() {
        return nbOtage;
    }

    public int getNbAssaillant() {
        return nbAssaillant;
    }

    public int getNbTotal() {
        return nbTotal;
    }

    public int getNbIndividu() {
        return nbIndividu;
    }

    public int getNbIntrusion() {
        return nbIntrusion;
    }

    public int getNbFeu() {
        return nbFeu;
    }

    public int getNbMaladie() {
        return nbMaladie;
    }

    public int getNbInconnue() {
        return nbInconnue;
    }

    public ArrayList <Personne> getIndividu() {
        return individu;
    }

    public ArrayList <Intrusion> getIntrusion() {
        return intrusion;
    }

    public ArrayList <Feu> getFeu() {
        return feu;
    }

    public ArrayList <Maladie> getMaladie() {
        return maladie;
    }

    public ArrayList <Anomalie> getInconnue() {
        return inconnue;
    }

    public ArrayList <Element> getEntoure() {
        return entoure;
    }

    public Element getSelected() {
        return selected;
    }

    public void setSelected(Element selected) {
        this.selected = selected;
    }
}
