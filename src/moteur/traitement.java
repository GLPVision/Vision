package moteur;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

public class traitement extends Thread {
    private int nbOtage, nbAssaillant, nbTotal, intrusion, feu, maladie, inconnue;
    private Carte carte;
    private Scenario scenario;
    private JLabel jl1, jl2, jl3;
	private String word;
    private boolean otage;
    private DefaultListModel<ImageIcon> list;
    private JList content;
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    private String Newligne=System.getProperty("line.separator");
    private Coordonnees taille;
    private Coordonnees debut;
    
    public traitement(boolean otage, Coordonnees taille, Coordonnees debut, int nbOtage, JLabel jl1, JLabel jl2, JLabel jl3, DefaultListModel list, JList content, String word){
        if(otage){
            scenario = new Otage(taille, nbOtage);
        }
        else {
            scenario = new Agriculture(taille);
        }
        carte = scenario.getCarte();
        this.otage = otage;
        this.taille = taille;
        this.debut = debut;
        this.nbOtage = nbOtage;
        this.jl1 = jl1;
        this.jl2 = jl2;
        this.jl3 = jl3;
        this.list = list;
        this.content = content;
        this.word = word;
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
        for(int i = 0 ; i<taille.getY() ; i++){
            for(int j = 0 ; j<taille.getX() ; j++){
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
                    content.setSelectedIndex(i*taille.getX()+j);
                }
                majGUI();
                sleep(30);
            }
        }
        if(otage) { //si derniere iteration otage
            nbAssaillant = nbTotal - nbOtage;
            majGUI();
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
        this.interrupt();
    }

    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){
            jl1.setText("   Nombre total d'individus : " + nbTotal);
            jl2.setText("   Nombre total d'assaillants : " + nbAssaillant);
            jl3.setText("   Nombre d'otages : " + nbOtage);


        }
        else{
            jl1.setText("   Total : " + (inconnue+feu+maladie+intrusion));
            jl3.setText("<html> &nbsp &#160 Nombre de feux : " + feu + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + intrusion + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + maladie + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + inconnue + "</html>");
        }
    }

    public void entourer(int x, int y){
        list.set(x*taille.getX()+y, build.merge((ImageIcon) list.getElementAt(x*taille.getX()+y), new ImageIcon(cercle.getImage().getScaledInstance(Math.min(950/taille.getX(), 600/taille.getY()), Math.min(950/taille.getX(), 600/taille.getY()), Image.SCALE_DEFAULT)))); //entourer
    }

    public void current(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX();
        int c2 = selected/taille.getX();
        String type = null;
        switch(carte.getTab()[c2][c1].getDesc()){
            case ".":
                type = "Aucune";
                break;
            case "p":
                type = "Individu";
                break;
            case "i":
                type = "Intrusion";
                break;
            case "m":
                type = "Maladie";
                break;
            case "f":
                type = "Feu";
                break;
            case "x":
                type = "Inconnue";
                break;
            default:
                break;
        }
        if(otage){
            System.out.println(c1 + " " + c2);
        }

        else {
            int x = debut.getX()+c1;
            int y = debut.getY()+c2;
            jl2.setText("<html> &nbsp &#160 Type d'anomalie : " + type + "<br/>" +
                    " &nbsp &#160 Coordonnées : x=" + x + ", y=" + y + "</html>");
            word = "   " + type  + " en : " + x + "," + y ;
        }
        

    }
    
    public void next(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX();
        int c2 = selected/taille.getX();
        Element[][] elt = carte.getTab();
        int j;
        for(int i = c2 ; i<taille.getY() ; i++) {
            if(i == c2)
                j = c1;
            else
                j=0;
            for (; j<taille.getX(); j++) {
                if(elt[i][j].getDesc()!="." && i* taille.getY()+j>c2* taille.getY()+c1){
                    content.setSelectedIndex(i*taille.getY()+j);
                    i = taille.getX();
                    break;
                }
            }
        }
    }

    public void previous(){
        int selected = content.getSelectedIndex();
        int c1 = selected%taille.getX();
        int c2 = selected/taille.getX();
        Element[][] elt = carte.getTab();
        int j;
        for(int i = c2 ; i>=0 ; i--) {
            if(i == c2)
                j = c1;
            else
                j = taille.getX()-1;
            for (; j>=0 ; j--) {
                if(elt[i][j].getDesc()!="." && i* taille.getY()+j<c2* taille.getY()+c1){
                    content.setSelectedIndex(i*taille.getY()+j);
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



    public boolean getOtage() {
        return otage;
    }

    public DefaultListModel getList() {
        return list;
    }

    public Carte getCarte() {
        return carte;
    }
    public String getWord() {
    	return this.word;
    }
    
    String makeString (JLabel jl) {//This is the method which converts the Jlabel into a String
		   ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		   XMLEncoder e = new XMLEncoder (baos);
		   e.writeObject (jl);
		   e.close ();
		   return new String (baos.toByteArray ());
    }

    public Coordonnees getTaille() {
        return taille;
    }
}
