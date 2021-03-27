package affichage;

import data.*;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

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
			HashMap<Coordonnees, Personne> personne = traitement.getNbTotal();
            for(Personne p : personne.values()){
                paintStrategy.draw(p, g);
            }
        }
        else{
			HashMap <Coordonnees, Anomalie> anomalie = traitement.getInconnue();
            for(Anomalie a : anomalie.values()){
                paintStrategy.draw(a, g);
            }

			HashMap <Coordonnees, Feu> feu = traitement.getFeu();
            for(Feu f : feu.values()){
                paintStrategy.draw(f, g);
            }

			HashMap <Coordonnees, Intrusion> intrusion = traitement.getIntrusion();
            for(Intrusion i : intrusion.values()){
                paintStrategy.draw(i, g);
            }

			HashMap <Coordonnees, Maladie> maladie = traitement.getMaladie();
            for(Maladie m : maladie.values()){
                paintStrategy.draw(m, g);
            }
        }
        HashMap <Coordonnees, Element> entoure = traitement.getEntoure();
        for(Element e : entoure.values()){
            paintStrategy.draw((Element) e, g);
        }
        Element selected = traitement.getSelected();
        paintStrategy.draw_selected(selected, g);
    }
}
