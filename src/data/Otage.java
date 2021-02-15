package data;

import java.io.IOException;

/**
 * Classe scénario otage
 * @author QIU Antoine
 */
public class Otage extends Scenario{
    /**
     * Constructeur, initialise les variables
     * @param x Coordonnées x
     * @param y Coordonnées y
     * @param nbOtage Nombre d'otages
     * @throws IOException Erreur d'écriture lors de l'exportation
     */
    public Otage(int x, int y, int nbOtage) throws IOException {
        carte = new Carte(x, y, true, nbOtage); //otage + otage/4 < x*y/2
    }
}
