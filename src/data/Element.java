package data;

/**
 * Classe element / case de la matrice
 */
public class Element {
    /**
     * Coordonnées
     */
    private Coordonnees coordonnees;
    /**
     * Description
     */
    private String desc;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Element(Coordonnees c){
        coordonnees = c;
        this.desc = ".";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return desc;
    }

    /**
     * Fonction qui retourne les coordonnées
     * @return Coordonnées
     */
    public Coordonnees getCoordonnees() {
        return coordonnees;
    }
}
