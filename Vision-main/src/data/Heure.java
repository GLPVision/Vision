package data;

/**
 * Classe heure
 * @author QIU Antoine
 */
public class Heure {
    /**
     * Heure
     */
    private String heure;
    /**
     * Minute
     */
    private String minute;
    /**
     * Seconde
     */
    private String seconde;

    /**
     * Constructeur, initialise les variables
     * @param heure Heure
     * @param minute Minute
     * @param seconde Seconde
     */
    public Heure(String heure, String minute, String seconde){
        this.heure = heure;
        this.minute = minute;
        this.seconde = seconde;
    }

    /**
     * Fonction qui retourne l'heure
     * @return Heure
     */
    public String getHeure(){
        return heure;
    }

    /**
     * Fonction qui retourne les minutes
     * @return Minute
     */
    public String getMinute(){
        return minute;
    }

    /**
     * Focntion qui retourne les secondes
     * @return Seconde
     */
    public String getSeconde(){
        return seconde;
    }
}
