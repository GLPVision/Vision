package moteur;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class traitement extends Thread {
    private int nbOtage, nbAssaillant, nbTotal, intrusion, feu, maladie, inconnue;
    private Carte carte;
    private static Scenario scenario;
    private JLabel total;
    private JTextArea text, types;
    private boolean otage;
    private DefaultListModel list;
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    public traitement(boolean otage, int x, int y, int nbOtage, JLabel total, JTextArea text, JTextArea types, DefaultListModel list){
        if(otage){
            scenario = new Otage(x, y, nbOtage);
        }
        else {
            scenario = new Agriculture(x, y);
        }
        carte = scenario.getCarte();
        this.otage = otage;
        this.total = total;
        this.text = text;
        this.types = types;
        this.list = list;
    }
    public void run(){
        try {
            scan();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void scan() throws InterruptedException {
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
                if(tab[i][j].getDesc() != "."){
                    entourer(i, j);
                }
                majGUI();
                sleep(30);
            }
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

    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){

        }
        else{
            total.setText("   Total : " + (inconnue+feu+maladie+intrusion));
        }
    }

    public void entourer(int x, int y){
        list.set(x*carte.getY()+y, build.merge((ImageIcon) list.getElementAt(x*carte.getY()+y), new ImageIcon(cercle.getImage().getScaledInstance(600/carte.getX(), 600/carte.getX(), Image.SCALE_DEFAULT)))); //entourer
    }

    public static Scenario getScenario() {
        return scenario;
    }
}
