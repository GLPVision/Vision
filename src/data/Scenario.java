package data;

/**
 * Classe abstraite scénario
 */
public abstract class Scenario {
    /**
     * Carte
     */
    protected Carte carte;

    /**
     * Fonction qui retourne la carte
     * @return Carte
     */
    public Carte getCarte() {
        return carte;
    }
}
