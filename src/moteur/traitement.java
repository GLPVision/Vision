package moteur;

import data.*;

import javax.swing.*;
import java.awt.*;

public class traitement extends Thread {
    private int nbOtage, nbAssaillant, nbTotal, intrusion, feu, maladie, inconnue, x, y;
    private Carte carte;
    private Scenario scenario;
    private JLabel total, text, types;
    private boolean otage;
    private DefaultListModel list;
    private JList content;
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    public traitement(boolean otage, int x, int y, int nbOtage, JLabel total, JLabel text, JLabel types, DefaultListModel list, JList content){
        if(otage){
            scenario = new Otage(x, y, nbOtage);
        }
        else {
            scenario = new Agriculture(x, y);
        }
        carte = scenario.getCarte();
        this.otage = otage;
        this.x = x;
        this.y = y;
        this.nbOtage = nbOtage;
        this.total = total;
        this.text = text;
        this.types = types;
        this.list = list;
        this.content = content;
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
        for(int i = 0 ; i<carte.getY() ; i++){
            for(int j = 0 ; j<carte.getX() ; j++){
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
                    content.setSelectedIndex(i*x+j);
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
        this.stop();
    }

    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){

        }
        else{
            total.setText("   Total : " + (inconnue+feu+maladie+intrusion));
            //text.setText();
            types.setText("<html>Nombre de feux : " + feu +"<br/>" +
                    "Nombre d'anomalies inconnues : " + inconnue +"<br/>" +
                    "Nombre d'intrusions : " + intrusion +"<br/>" +
                    "Nombre de maladies : " + maladie +"</html>");
        }
    }

    public void entourer(int x, int y){
        list.set(x*carte.getX()+y, build.merge((ImageIcon) list.getElementAt(x*carte.getX()+y), new ImageIcon(cercle.getImage().getScaledInstance(Math.min(950/carte.getX(), 600/carte.getY()), Math.min(950/carte.getX(), 600/carte.getY()), Image.SCALE_DEFAULT)))); //entourer
    }

    public void current(){
        int selected = content.getSelectedIndex();
        int c1 = selected%x;
        int c2 = selected/x;
        text.setText("   x: " + c1 + " y: " + c2);
    }
    public void next(){
        int selected = content.getSelectedIndex();
        int c1 = selected%x;
        int c2 = selected/x;
        Element[][] elt = carte.getTab();
        int j;
        for(int i = c2 ; i<y ; i++) {
            if(i == c2)
                j = c1;
            else
                j=0;
            for (; j<x; j++) {
                if(elt[i][j].getDesc()!="." && i*y+j>c2*y+c1){
                    content.setSelectedIndex(i*y+j);
                    i = x;
                    break;
                }
            }
        }
    }

    public void previous(){
        int selected = content.getSelectedIndex();
        int c1 = selected%x;
        int c2 = selected/x;
        Element[][] elt = carte.getTab();
        int j;
        for(int i = c2 ; i>=0 ; i--) {
            if(i == c2)
                j = c1;
            else
                j = x-1;
            for (; j>=0 ; j--) {
                if(elt[i][j].getDesc()!="." && i*y+j<c2*y+c1){
                    content.setSelectedIndex(i*y+j);
                    content.revalidate();
                    content.repaint();
                    i = -1;
                    break;
                }
            }
        }
    }
    public Scenario getScenario() {
        return scenario;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getOtage() {
        return otage;
    }

    public DefaultListModel getList() {
        return list;
    }

    public Carte getCarte() {
        return carte;
    }
}
