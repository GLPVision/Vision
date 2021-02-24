package moteur;

import data.Agriculture;
import data.Carte;
import data.Otage;
import data.Scenario;

public class traitement {
    private int nbOtage;
    private int nbAssaillant;
    private int nbTotal;
    private int individu;
    private int feu;
    private int maladie;
    private int inconnue;
    private Carte carte;
    private static Scenario scenario;
    public traitement(int nbOtage, int nbAssaillant, int nbTotal, int individu, int feu, int maladie, int inconnue, Carte carte){
        this.nbOtage = nbOtage;
        this.nbAssaillant = nbAssaillant;
        this.nbTotal = nbTotal;
        this.individu = individu;
        this.feu = feu;
        this.maladie = maladie;
        this. inconnue = inconnue;
        this.carte = carte;
    }
    public static Scenario creer(boolean otage, int x, int y, int nbOtage){
        if(otage){
            scenario = new Otage(x, y, nbOtage);
        }
        else {
            scenario = new Agriculture(x, y);
        }
        return scenario;
    }
    public void supp() {
        this.nbOtage = 0;
        this.nbAssaillant = 0;
        this.nbTotal = 0;
        this.individu = 0;
        this.feu = 0;
        this.maladie = 0;
        this. inconnue = 0;
        this.carte = null;
        scenario = null;
    }
}
