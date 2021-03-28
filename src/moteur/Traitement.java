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
    private int nbMoving = 0;

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
            if(element.getDesc().equals("Individu")) { //si personne
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
            if(!otage && !element.getDesc().equals(".")){
                entoure.add(element);
            }
            if(element.getDesc().equals("Individu")) { //si personne
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
            if(otage){
                Personne p;
                p = individu.get((int) (Math.random() * individu.size()));
                int x = p.getCoordonnees().getX();
                int y = p.getCoordonnees().getY();
                if(nbMoving<nbAssaillant){
                    p.setDesc("Assaillant");
                    entoure.add(p);
                    carte.getTab()[x][y] = p;
                    nbMoving++;
                    if(p.getDesc().equals("Individu")) {
                        switch ((int) (Math.random() * 4)) {
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
                            default:
                                break;
                        }
                        return 1;
                    }
                }
                else if(p.getDesc().equals("Assaillant")){
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
            }
            else{
                Element e;
                e = entoure.get((int) (Math.random() * entoure.size()));
                int x = e.getCoordonnees().getX();
                int y = e.getCoordonnees().getY();
                if(e.getDesc().equals("Intrusion")){
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
                else if(e.getDesc().equals("Feu")){
                    burn(x, y);
                    return 1;
                }
                else if(e.getDesc().equals("Inconnue")){
                    return 1;
                }
                else if(e.getDesc().equals("Maladie")){
                    infect(x, y);
                    return 1;
                }
            }
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

    public void burn(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        switch((int) (Math.random() *Configuration.PROBA_FEU)){
            case 0:
                if(y-1 >= 0 && !tab[x][y-1].getDesc().equals("Feu")){
                    remove(x, y-1);
                    feu.add(new Feu(new Coordonnees(x, y-1)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x, y-1)));
                    tab[x][y-1] = new Feu(new Coordonnees(x, y-1));
                }
                break;
            case 1:
                if(y+1 < taille.getY() && !tab[x][y+1].getDesc().equals("Feu")){
                    remove(x, y+1);
                    feu.add(new Feu(new Coordonnees(x, y+1)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x, y+1)));
                    tab[x][y+1] = new Feu(new Coordonnees(x, y+1));
                }
                break;
            case 2:
                if(x+1 < taille.getX() && !tab[x+1][y].getDesc().equals("Feu")){
                    remove(x+1, y);
                    feu.add(new Feu(new Coordonnees(x+1, y)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x+1, y)));
                    tab[x+1][y] = new Feu(new Coordonnees(x+1, y));
                }
                break;
            case 3:
                if(x-1 >= 0 && !tab[x-1][y].getDesc().equals("Feu")){
                    remove(x-1, y);
                    feu.add(new Feu(new Coordonnees(x-1, y)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x-1, y)));
                    tab[x-1][y] = new Feu(new Coordonnees(x-1, y));
                }
                break;
        }
    }
    public void infect(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        switch((int) (Math.random() *Configuration.PROBA_INFECT)){
            case 0:
                if(y-1 >= 0 && tab[x][y-1].getDesc().equals(".")){
                    remove(x, y-1);
                    maladie.add(new Maladie(new Coordonnees(x, y-1)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x, y-1)));
                    tab[x][y-1] = new Maladie(new Coordonnees(x, y-1));
                }
                break;
            case 1:
                if(y+1 < taille.getY() && tab[x][y+1].getDesc().equals(".")){
                    remove(x, y+1);
                    maladie.add(new Maladie(new Coordonnees(x, y+1)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x, y+1)));
                    tab[x][y+1] = new Maladie(new Coordonnees(x, y+1));
                }
                break;
            case 2:
                if(x+1 < taille.getX() && tab[x+1][y].getDesc().equals(".")){
                    remove(x+1, y);
                    maladie.add(new Maladie(new Coordonnees(x+1, y)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x+1, y)));
                    tab[x+1][y] = new Maladie(new Coordonnees(x+1, y));
                }
                break;
            case 3:
                if(x-1 >= 0 && tab[x-1][y].getDesc().equals("")){
                    remove(x-1, y);
                    maladie.add(new Maladie(new Coordonnees(x-1, y)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x-1, y)));
                    tab[x-1][y] = new Maladie(new Coordonnees(x-1, y));
                }
                break;
        }
    }

    public void remove(int x, int y){
        for(int i=0 ; i<intrusion.size() ; i++){
            if(intrusion.get(i).getCoordonnees().getX()==x && intrusion.get(i).getCoordonnees().getY()==y){
                intrusion.remove(i);
                nbIntrusion--;
                break;
            }
        }
        for(int i=0 ; i<feu.size() ; i++){
            if(feu.get(i).getCoordonnees().getX()==x && feu.get(i).getCoordonnees().getY()==y){
                feu.remove(i);
                nbFeu--;
                break;
            }
        }
        for(int i=0 ; i<maladie.size() ; i++){
            if(maladie.get(i).getCoordonnees().getX()==x && maladie.get(i).getCoordonnees().getY()==y){
                maladie.remove(i);
                nbMaladie--;
                break;
            }
        }
        for(int i=0 ; i<inconnue.size() ; i++){
            if(inconnue.get(i).getCoordonnees().getX()==x && inconnue.get(i).getCoordonnees().getY()==y){
                inconnue.remove(i);
                nbInconnue--;
                break;
            }
        }
        for(int i=0 ; i<individu.size() ; i++){
            if(individu.get(i).getCoordonnees().getX()==x && individu.get(i).getCoordonnees().getY()==y){
                individu.remove(i);
                nbTotal--;
                break;
            }
        }
        for(int i=0 ; i<entoure.size() ; i++){
            if(entoure.get(i).getCoordonnees().getX()==x && entoure.get(i).getCoordonnees().getY()==y){
                entoure.remove(i);
                break;
            }
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
