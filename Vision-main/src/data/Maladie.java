package data;

/**
 * Classe maladie
 * @author QIU Antoine
 */
public class Maladie extends Anomalie {
    /**
     * Maladie
     */
    private String maladie;
    /**
     * Constructeur, initialise les variables
     * @param c Coodonnées
     */
    public Maladie(Coordonnees c) {
        super(c);
        maladie = "m";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return maladie;
    }
}
