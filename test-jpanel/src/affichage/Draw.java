package affichage;

import data.*;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Draw {
    private Float width, height;
    private Float size;
    private Image champ = ImageIO.read(ClassLoader.getSystemResource("champ.jpg"));
    private Image bat = ImageIO.read(ClassLoader.getSystemResource("fondotage.png"));
    private Image personne = ImageIO.read(ClassLoader.getSystemResource("personne.png"));
    private Image inconnue = ImageIO.read(ClassLoader.getSystemResource("inconnue.png"));
    private Image feu = ImageIO.read(ClassLoader.getSystemResource("feu.png"));
    private Image intrusion = ImageIO.read(ClassLoader.getSystemResource("intrusion.png"));
    private Image maladie = ImageIO.read(ClassLoader.getSystemResource("virus.png"));

    public Draw() throws IOException {

    }
    public void draw(Carte carte, Graphics graphics) throws IOException {
        Graphics2D g = (Graphics2D) graphics;
        Element[][] elements = carte.getTab();
        Coordonnees taille = carte.getTaille();
        height = (float)600/(float)taille.getY();
        width = (float)950/(float)taille.getX();
        if(height<width)
            size = height/2;
        else
            size = width/2;
        //afficher les cases
        Image fond = null;
        if(carte.isOtage())
            fond = bat.getScaledInstance(width.intValue()+10, height.intValue()+10, Image.SCALE_DEFAULT);
        else
            fond = champ.getScaledInstance(width.intValue()+10, height.intValue()+10, Image.SCALE_DEFAULT);
        for(int i=0 ; i< taille.getY() ; i++){
            for(int j=0 ; j< taille.getX() ; j++){
                g.drawImage(fond, (int)(j*width), (int)(i*height), null, null);
            }
        }
/*
        for(int i=0 ; i<taille.getY() ; i++){
            g.draw(new Line2D.Double(0, i * height, 950, i * height));
        }
        for(int i=0 ; i<taille.getX() ; i++){
            g.draw(new Line2D.Double(i*width, 0, i*width, 600));
        }
        
 */






    }

    public void draw(Anomalie a, Graphics graphics){ //dessine liste
        inconnue = inconnue.getScaledInstance(size.intValue(), size.intValue(), Image.SCALE_DEFAULT);
        Coordonnees pos = a.getCoordonnees();
        graphics.drawImage(inconnue, pos.getX()*width.intValue()+(width.intValue()/2-size.intValue()/2), pos.getY()*height.intValue()+(height.intValue()/2-size.intValue()/2), null, null);

    }
    public void draw(Feu f, Graphics graphics){
        feu = feu.getScaledInstance(size.intValue(), size.intValue(), Image.SCALE_DEFAULT);
        Coordonnees pos = f.getCoordonnees();
        graphics.drawImage(feu, pos.getX()*width.intValue()+(width.intValue()/2-size.intValue()/2), pos.getY()*height.intValue()+(height.intValue()/2-size.intValue()/2), null, null);

    }
    public void draw(Intrusion i, Graphics graphics){
        intrusion = intrusion.getScaledInstance(size.intValue(), size.intValue(), Image.SCALE_DEFAULT);
        Coordonnees pos = i.getCoordonnees();
        graphics.drawImage(intrusion, pos.getX()*width.intValue()+(width.intValue()/2-size.intValue()/2), pos.getY()*height.intValue()+(height.intValue()/2-size.intValue()/2), null, null);

    }
    public void draw(Maladie m, Graphics graphics){
        maladie = maladie.getScaledInstance(size.intValue(), size.intValue(), Image.SCALE_DEFAULT);
        Coordonnees pos = m.getCoordonnees();
        graphics.drawImage(maladie, pos.getX()*width.intValue()+(width.intValue()/2-size.intValue()/2), pos.getY()*height.intValue()+(height.intValue()/2-size.intValue()/2), null, null);

    }
    public void draw(Personne p, Graphics graphics){
        personne = personne.getScaledInstance(size.intValue(), size.intValue(), Image.SCALE_DEFAULT);
        Coordonnees pos = p.getCoordonnees();
        graphics.drawImage(personne, pos.getX()*width.intValue()+(width.intValue()/2-size.intValue()/2), pos.getY()*height.intValue()+(height.intValue()/2-size.intValue()/2), null, null);
    }

    public void draw(Element e, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Coordonnees pos = e.getCoordonnees();
        g.setColor(new Color(255, 0, 0));
        g.draw(new Ellipse2D.Double(pos.getX()*width+(width/2-size), pos.getY()*height+(height/2-size), size*2, size*2));
        g.setColor(Color.black);
    }
}
