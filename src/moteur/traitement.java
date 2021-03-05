package moteur;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

public class traitement extends Thread {
    private int nbOtage, nbAssaillant, nbTotal, intrusion, feu, maladie, inconnue, x, y;
    private Carte carte;
    private Scenario scenario;
    private JLabel total;
	private JLabel text;
	private JLabel types;
	private String word;
    private boolean otage;
    private DefaultListModel<ImageIcon> list;
    private JList content;
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    private String Newligne=System.getProperty("line.separator");
    
    public traitement(boolean otage, int x, int y, int nbOtage, JLabel total, JLabel text, JLabel types, DefaultListModel list, JList content, String word){
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
        this.interrupt();
    }

    public void majGUI(){ //fonction a appeler avec un action listener lié a la selection d'un element
        if(otage){

        }
        else{
            total.setText("   Total : " + (inconnue+feu+maladie+intrusion));
            //text.setText();
            types.setText("<html> &nbsp &#160 Nombre de feux : " + feu + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + intrusion + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + maladie + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + inconnue + "</html>");
        }
    }

    public void entourer(int x, int y){
        list.set(x*carte.getX()+y, build.merge((ImageIcon) list.getElementAt(x*carte.getX()+y), new ImageIcon(cercle.getImage().getScaledInstance(Math.min(950/carte.getX(), 600/carte.getY()), Math.min(950/carte.getX(), 600/carte.getY()), Image.SCALE_DEFAULT)))); //entourer
    }

    public void current(){
        int selected = content.getSelectedIndex();
        int c1 = selected%x;
        int c2 = selected/x;
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
        text.setText("<html> &nbsp &#160 Type d'anomalie : " + type + "<br/>" +
                " &nbsp &#160 Coordonnées : x=" + c1 + ", y=" + c2 + "</html>");
        
        word = "   " + type  + " en : " + c1 + "," + c2 ;        
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
}
