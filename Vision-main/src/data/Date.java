package data;

/**
 * Classe date
 */
public class Date {
    /**
     * Jour
     */
    private String jour;
    /**
     * Mois
     */
    private String mois;
    /**
     * Année
     */
    private String annee;
    /**
     * Heure
     */
    private Heure heure;

    /**
     * Constructeur, initialise les variables
     * @param jour Jour
     * @param mois Mois
     * @param annee Année
     * @param heure Heure
     */
    public Date(String jour, String mois, String annee, Heure heure){
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
        this.heure = heure;
    }

    /**
     * Fonction qui retourne le jour
     * @return Jour
     */
    public String getJour(){
        return jour;
    }

    /**
     * Fonction qui retourne le mois
     * @return Mois
     */
    public String getMois(){
        return mois;
    }

    /**
     * Fonction qui retourn l'année
     * @return Année
     */
    public String getAnnee(){
        return annee;
    }

    /**
     * Fonction qui retourn l'heure
     * @return Heure
     */
    public Heure getHeure(){
        return heure;
    }
}