package data;

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
     */
    public Otage(int x, int y, int nbOtage){
        carte = new Carte(x, y, true, nbOtage); //otage + otage/4 < x*y/2
    }
}
