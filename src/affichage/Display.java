package affichage;

import data.*;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Display extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Carte carte;
    private Traitement traitement;
    private PaintStrategy paintStrategy;

    Display(Traitement traitement, PaintStrategy paintStrategy){
        this.carte = traitement.getCarte();
        this.paintStrategy = paintStrategy;
        this.traitement = traitement;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            paintStrategy.draw(carte, g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(traitement.isOtage()){
            ArrayList <Personne> personne = traitement.getIndividu();
            for(Personne p : personne){
                paintStrategy.draw(p, g);
            }
        }
        else{
            ArrayList <Anomalie> anomalie = traitement.getInconnue();
            ArrayList <Feu> feu = traitement.getFeu();
            ArrayList <Intrusion> intrusion = traitement.getIntrusion();
            ArrayList <Maladie> maladie = traitement.getMaladie();
            for(int i=0 ; i<anomalie.size() ; i++){
                paintStrategy.draw(anomalie.get(i), g);
            }
            for(int i=0 ; i<feu.size() ; i++){
                paintStrategy.draw(feu.get(i), g);
            }
            for(int i=0 ; i<intrusion.size() ; i++){
                paintStrategy.draw(intrusion.get(i), g);
            }
            for(int i=0 ; i<maladie.size() ; i++){
                paintStrategy.draw(maladie.get(i), g);
            }
        }
        ArrayList <Element> entoure = traitement.getEntoure();
        for(Element e : entoure){
            paintStrategy.draw(e, g);
        }
        Element selected = traitement.getSelected();
        paintStrategy.draw_selected(selected, g);
    }
}
