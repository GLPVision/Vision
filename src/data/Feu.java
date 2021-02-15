package data;

/**
 * Classe feu
 * @author QIU Antoine
 */
public class Feu extends Anomalie{
    /**
     * taille du feu
     */
    private String feu;

    /**
     * Constructeur, initialse les variables
     * @param c Coordonnées
     */
    public Feu(Coordonnees c) {
        super(c);
        feu = "f";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return feu;
    }

}
