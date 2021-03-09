package data;

/**
 * Classe Personne
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Personne extends Element {
    /**
     * Description
     */
    private String personne;
    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Personne(Coordonnees c) {
        super(c);
        this.personne = "p";
    }
    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return personne;
    }
}
