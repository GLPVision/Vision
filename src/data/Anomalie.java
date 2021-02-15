package data;

/**
 * Classe anomalie
 * @author QIU Antoine
 */
public class Anomalie extends Element{
    /**
     * Description
     */
    private String anomalie;
    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Anomalie(Coordonnees c) {
        super(c);
        anomalie = "x";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return anomalie;
    }
}
