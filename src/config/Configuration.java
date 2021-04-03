package config;

/**
 * Configuration du programme
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 2
 */
public class Configuration {
    public static final int MAX_OTAGES = 10;
    public static final int MIN_OTAGES = 1;
    public static final int SCAN_SPEED = 10;
    public static final int BASE_SPEED = 500;
    //plus c'est élevé, moins il y a de chance
    public static final int PROBA_ANOMALIE = 50;
    public static final int PROBA_INDIVIDU = 50;
    public static final int PROBA_MOVE = 4; //1/4
    public static final int PROBA_FEU = 4;
    public static final int PROBA_INFECT = 8; //1/2
}
