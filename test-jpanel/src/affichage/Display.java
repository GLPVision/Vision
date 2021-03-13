package affichage;

import data.*;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Display extends JPanel{
    private Carte carte;
    private Traitement traitement;
    private Draw draw;

    Display(Traitement traitement, Draw draw){
        this.carte = traitement.getCarte();
        this.draw = draw;
        this.traitement = traitement;
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            draw.draw(carte, g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(traitement.getOtage()){
            ArrayList <Personne> personne = traitement.getNbTotal();
            for(int i=0 ; i<personne.size() ; i++){
                draw.draw(personne.get(i), g);
            }
            ArrayList <Element> entoure = traitement.getEntoure();
            for(int i=0 ; i<entoure.size() ; i++){
                draw.draw((Element) entoure.get(i), g);
            }
        }
        else{
            ArrayList anomalie = traitement.getInconnue();
            for(int i=0 ; i<anomalie.size() ; i++){
                draw.draw((Anomalie) anomalie.get(i), g);
            }
            ArrayList feu = traitement.getFeu();
            for(int i=0 ; i<feu.size() ; i++){
                draw.draw((Feu) feu.get(i), g);
            }
            ArrayList intrusion = traitement.getIntrusion();
            for(int i=0 ; i<intrusion.size() ; i++){
                draw.draw((Intrusion) intrusion.get(i), g);
            }
            ArrayList maladie = traitement.getMaladie();
            for(int i=0 ; i<maladie.size() ; i++){
                draw.draw((Maladie) maladie.get(i), g);
            }
            ArrayList <Element> entoure = traitement.getEntoure();
            for(int i=0 ; i<entoure.size() ; i++){
                draw.draw((Element) entoure.get(i), g);
            }
        }
    }
}
