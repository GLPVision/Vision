package data;

import java.io.IOException;

/**
 * Classe scénario agriculture
 * @author QIU Antoine
 */
public class Agriculture extends Scenario {
    /**
     * Constructeur, initialise les variables
     * @param taille Taille de la carte
     * @throws IOException Erreur d'écriture lors de l'exportation
     */
    public Agriculture(Coordonnees taille){
        carte = new Carte(taille, false, 0); //nouvelle carte
    }
}
