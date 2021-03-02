package moteur;

import data.Carte; 
import data.Element;
import data.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class build extends Thread{
    private Scenario scenario;
    private int x;
    private int y;
    @SuppressWarnings("rawtypes")
	private DefaultListModel list;
    private ImageIcon personne = new ImageIcon(ClassLoader.getSystemResource("personne.png"));
    private ImageIcon intrusion = new ImageIcon(ClassLoader.getSystemResource("intrusion.png"));
    private ImageIcon maladie = new ImageIcon(ClassLoader.getSystemResource("virus-2.jpg"));
    private ImageIcon feu = new ImageIcon(ClassLoader.getSystemResource("feu.png"));
    private ImageIcon inconnue = new ImageIcon(ClassLoader.getSystemResource("inconnue.png"));
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
    private ImageIcon ble = new ImageIcon(ClassLoader.getSystemResource("champ.jpg"));
    private boolean otage;
    private JLabel text;
    public build(Scenario scenario, int x, int y, @SuppressWarnings("rawtypes") DefaultListModel list, boolean otage, JLabel text){
        this.scenario = scenario;
        this.x = x;
        this.y = y;
        this.list = list;
        this.otage = otage;
        this.text = text;
    }
    public void run(){
        try {
            build_map(list);
            entourer(list, otage, text);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
	public void build_map(@SuppressWarnings("rawtypes") DefaultListModel list) throws InterruptedException {
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
                switch(tab[i][j].getDesc()){
                    case ".":
                        img = ble;
                        break;
                    case "p":
                        img = personne;
                        break;
                    case "i":
                        img = intrusion;
                        break;
                    case "m":
                        img = maladie;
                        break;
                    case "f":
                        img = feu;
                        break;
                    case "x":
                        img = inconnue;
                        break;
                }
                if(img == ble){
                    list.addElement(new ImageIcon(img.getImage().getScaledInstance(950/y, 600/x, Image.SCALE_DEFAULT))); //ajout à la liste
                }
                else{
                    list.addElement(new ImageIcon(img.getImage().getScaledInstance(600/x/2, 600/x/2, Image.SCALE_DEFAULT))); //ajout à la liste

                }
            }
            //sleep(100);
        }
    }
    @SuppressWarnings("unchecked")
	public void entourer(@SuppressWarnings("rawtypes") DefaultListModel list, boolean otage, JLabel text) throws InterruptedException {
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                if(tab[i][j].getDesc() != "."){
                    list.set(i*x+j, merge((ImageIcon) list.getElementAt(i*x+j), new ImageIcon(cercle.getImage().getScaledInstance(600/x, 600/x, Image.SCALE_DEFAULT)))); //entourer
                    majGUI(otage,text);
                }
                sleep(10);
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
    
    public ImageIcon merge(ImageIcon a, ImageIcon b){
        Image imga = a.getImage();
        Image imgb = b.getImage();

        BufferedImage bi = new BufferedImage(imgb.getWidth(null), imgb.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(imga, imgb.getWidth(null)/2- imga.getWidth(null)/2, imgb.getHeight(null)/2-imga.getHeight(null)/2, null);
        g.drawImage(imgb, 0, 0, null);
        g.dispose();
        return new ImageIcon(bi);
    }
}
