package data;

/**
 * Classe intrusion
 * @author QIU Antoine
 */
public class Intrusion extends Anomalie{
    /**
     * Description
     */
    private String intrusion;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Intrusion(Coordonnees c) {
        super(c);
        intrusion = "i";
    }

    /**
     * Focntion qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return intrusion;
    }
}
