package moteur;

import data.Carte;
import data.Element;
import data.Scenario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class build extends Thread{
    private Scenario scenario;
    private int x;
    private int y;
    private DefaultListModel list;
    private ImageIcon personne = new ImageIcon(ClassLoader.getSystemResource("personne.png"));
    private ImageIcon intrusion = new ImageIcon(ClassLoader.getSystemResource("intrusion.png"));
    private ImageIcon maladie = new ImageIcon(ClassLoader.getSystemResource("maladie.png"));
    private ImageIcon feu = new ImageIcon(ClassLoader.getSystemResource("feu.png"));
    private ImageIcon inconnue = new ImageIcon(ClassLoader.getSystemResource("inconnue.png"));
    private ImageIcon cercle = new ImageIcon(ClassLoader.getSystemResource("cercle.png"));
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
                ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
                switch(tab[i][j].getDesc()){
                    case ".":
                        list.addElement(".");
                        continue;
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
                list.addElement(new ImageIcon(img.getImage().getScaledInstance(600/y/2, 600/y/2, Image.SCALE_DEFAULT))); //ajout à la liste
            }
            //sleep(100);
        }
    }
    public void entourer(DefaultListModel list) throws InterruptedException {
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                if(tab[i][j].getDesc() != "."){
                    list.set(i*x+j, merge((ImageIcon) list.getElementAt(i*x+j), new ImageIcon(cercle.getImage().getScaledInstance(600/y, 600/y, Image.SCALE_DEFAULT)))); //entourer
                }
                sleep(10);
            }
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
