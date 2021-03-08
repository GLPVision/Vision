package data;

/**
 * Classe scénario otage
 * @author QIU Antoine
 */
public class Otage extends Scenario{
    /**
     * Constructeur, initialise les variables
     * @param taille Taille de la carte
     * @param nbOtage Nombre d'otages
     */
    public Otage(Coordonnees taille, int nbOtage){
        carte = new Carte(taille, true, nbOtage); //otage + otage/4 < x*y/2
    }
}
