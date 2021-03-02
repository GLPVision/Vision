package moteur;

import data.Carte; 
import data.Element;
import data.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class build{
    private Scenario scenario;
    private int x;
    private int y;
    @SuppressWarnings("rawtypes")
	private DefaultListModel list;
    private ImageIcon personne = new ImageIcon(ClassLoader.getSystemResource("personne.png"));
    private ImageIcon intrusion = new ImageIcon(ClassLoader.getSystemResource("intrusion.png"));
    private ImageIcon maladie = new ImageIcon(ClassLoader.getSystemResource("virus.png"));
    private ImageIcon feu = new ImageIcon(ClassLoader.getSystemResource("feu.png"));
    private ImageIcon inconnue = new ImageIcon(ClassLoader.getSystemResource("inconnue.png"));
    private ImageIcon ble = new ImageIcon(ClassLoader.getSystemResource("champ.jpg"));
    private boolean otage;
    private JLabel text;
    private traitement t;

    public build(Scenario scenario, int x, int y, @SuppressWarnings("rawtypes") DefaultListModel list, boolean otage, traitement t){
        this.scenario = scenario;
        this.x = x;
        this.y = y;
        this.list = list;
        this.otage = otage;
        this.t = t;
        run();
    }
    public void run(){
        build_map(list);
    }
    @SuppressWarnings("unchecked")
	public void build_map(@SuppressWarnings("rawtypes") DefaultListModel list){
        Carte carte = scenario.getCarte(); //récupère la carte
        Element[][] tab = carte.getTab(); //récupère la matrice
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
                switch(tab[i][j].getDesc()){
                    case ".":
                        if(otage)
                            img = null;
                        else
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
                if(img == ble || img == null){
                    list.addElement(new ImageIcon(img.getImage().getScaledInstance(950/y, 600/x, Image.SCALE_DEFAULT))); //ajout à la liste
                }
                else{
                    list.addElement(merge(new ImageIcon(ble.getImage().getScaledInstance(950/y, 600/x, Image.SCALE_DEFAULT)), new ImageIcon(img.getImage().getScaledInstance(600/x/2, 600/x/2, Image.SCALE_DEFAULT)))); //ajout à la liste
                }
            }
        }
    }


    public static ImageIcon merge(ImageIcon a, ImageIcon b){ //a dessous et b dessus, a plus grand
        Image imga = a.getImage();
        Image imgb = b.getImage();

        BufferedImage bi = new BufferedImage(imga.getWidth(null), imga.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(imga, 0, 0, null);
        g.drawImage(imgb, imga.getWidth(null)/2- imgb.getWidth(null)/2, imga.getHeight(null)/2- imgb.getHeight(null)/2, null);
        g.dispose();
        return new ImageIcon(bi);
    }

}
