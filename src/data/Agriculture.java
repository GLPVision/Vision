package data;

import java.io.IOException;

/**
 * Classe scénario agriculture
 * @author QIU Antoine
 */
public class Agriculture extends Scenario {
    /**
     * Constructeur, initialise les variables
     * @param x Coordonnées x
     * @param y Coordonnées y
     * @throws IOException Erreur d'écriture lors de l'exportation
     */
    public Agriculture(int x, int y) throws IOException {
        carte = new Carte(x, y, false, 0); //nouvelle carte
    }
}
