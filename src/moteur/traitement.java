package moteur;

import data.*;

import javax.swing.*;

public class traitement {
    private int nbOtage;
    private int nbAssaillant;
    private int nbTotal;
    private int intrusion;
    private int feu;
    private int maladie;
    private int inconnue;
    private Carte carte;
    private static Scenario scenario;
    public void creer(boolean otage, int x, int y, int nbOtage){
        if(otage){
            scenario = new Otage(x, y, nbOtage);
        }
        else {
            scenario = new Agriculture(x, y);
        }
        carte = scenario.getCarte();
    }

    public void scan(){
        Element[][] tab = carte.getTab();
        for(int i = 0 ; i<carte.getX() ; i++){
            for(int j = 0 ; j<carte.getY() ; j++){
                if(tab[i][j].getDesc() == "p")
                    nbTotal++;
                else if(tab[i][j].getDesc() == "i")
                    intrusion++;
                else if(tab[i][j].getDesc() == "f")
                    feu++;
                else if(tab[i][j].getDesc() == "m")
                    maladie++;
                else if(tab[i][j].getDesc() == "x")
                    inconnue++;

            }
        }
    }

    public void majGUI(boolean otage, JLabel text){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){
            text.setText("nb assaillants :" +
                    "\nnb otages :" +
                    "\nnb total :");
        }
        else{
            text.setText(("nb anomalies :" +
                    "\nnb intrusions :" +
                    "\netc"));
        }

    }
    public void supp() {
        this.nbOtage = 0;
        this.nbAssaillant = 0;
        this.nbTotal = 0;
        this.inconnue = 0;
        this.feu = 0;
        this.maladie = 0;
        this. inconnue = 0;
        this.carte = null;
        scenario = null;
    }
    public Scenario getScenario(){
        return scenario;
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

    public int getIntrusion() {
        return intrusion;
    }

    public int getFeu() {
        return feu;
    }

    public int getMaladie() {
        return maladie;
    }

    public int getInconnue() {
        return inconnue;
    }

    public Carte getCarte() {
        return carte;
    }
}
