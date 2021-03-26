package affichage;

import data.*;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
        if(traitement.getOtage()){
			ArrayList <Personne> personne = traitement.getNbTotal();
            for(int i=0 ; i<personne.size() ; i++){
                paintStrategy.draw(personne.get(i), g);
            }
            ArrayList <Element> entoure = traitement.getEntoure();
            for(int i=0 ; i<entoure.size() ; i++){
                paintStrategy.draw((Element) entoure.get(i), g);
            }
        }
        else{
			ArrayList <Anomalie> anomalie = traitement.getInconnue();
            for(int i=0 ; i<anomalie.size() ; i++){
                paintStrategy.draw((Anomalie) anomalie.get(i), g);
            }

			ArrayList <Feu> feu = traitement.getFeu();
            for(int i=0 ; i<feu.size() ; i++){
                paintStrategy.draw((Feu) feu.get(i), g);
            }

			ArrayList <Intrusion> intrusion = traitement.getIntrusion();
            for(int i=0 ; i<intrusion.size() ; i++){
                paintStrategy.draw((Intrusion) intrusion.get(i), g);
            }

			ArrayList <Maladie> maladie = traitement.getMaladie();
            for(int i=0 ; i<maladie.size() ; i++){
                paintStrategy.draw((Maladie) maladie.get(i), g);
            }

            ArrayList <Element> entoure = traitement.getEntoure();
            for(int i=0 ; i<entoure.size() ; i++){
                paintStrategy.draw((Element) entoure.get(i), g);
            }
        }
        Element selected = traitement.getSelected();
        paintStrategy.draw_selected(selected, g);
    }
}
