package moteur;

import data.Agriculture;
import data.Carte;
import data.Element;
import data.Scenario;

import javax.swing.*;
import java.awt.*;

public class build extends Thread{
    private Scenario scenario;
    private int x;
    private int y;
    private DefaultListModel list;
    public build(Scenario scenario, int x, int y, DefaultListModel list){
        this.scenario = scenario;
        this.x = x;
        this.y = y;
        this.list = list;
    }
    public void run(){
        try {
            build_map(list);
            entourer(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void build_map(DefaultListModel list) throws InterruptedException {
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                list.addElement(tab[i][j].getDesc()); //ajout à la liste
                sleep(10);
            }
        }
    }
    public void entourer(DefaultListModel list) throws InterruptedException {
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                //selectionner
                String pwd = System.getProperty("user.dir");
                ImageIcon img = new ImageIcon(pwd + "/src/res/rouge.jpg");
                if(tab[i][j].getDesc() != "."){
                    list.set(i*x+j, img); //entourer
                }
                sleep(10);
            }
        }
    }
}
