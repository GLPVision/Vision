package data;


import config.Configuration;

/**
 * Classe carte
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 4
 */
public class Carte {
    private Coordonnees taille;
    /**
     * Matrice de dimension 2 / carte
     */
    private Element[][] tab;
    /**
     * Date
     */
    private Date date;
    /**
     * Booléen true=otage, false=agricole
     */
    private boolean otage = false;
    /**
     * Nombre d'anomalies
     */
    private int nb_anomalie;
    /**
     * Nombre d'otages
     */
    private int nb_otage;
    /**
     * Nombre d'asaillants
     */
    private int nb_assaillant;
    /**
     * Constructeur, initialise les variables
     * @param taille Taille de la carte
     * @param mode Otage ou agricole
     * @param nb_otage Nombre d'otages
     */
    public Carte(Coordonnees taille, boolean mode, int nb_otage){
        this.taille = taille;
        this.otage = mode;
        this.nb_otage = nb_otage;
        tab = new Element[taille.getY()][taille.getX()];
        init();
    }

    /**
     * Fonction initialisation
     */
    public void init(){
        setDate();
        generer();
        scan();
        if(otage){
            while(nb_assaillant <= nb_otage/4){  //au moins 1 assaillant pour 4 otages
                generer();
                scan();
            }
        }
    }

    /**
     * Fonction pour régler la date
     */
    public void setDate(){
        String date = java.time.LocalDate.now().toString();
        String heure = java.time.LocalTime.now().toString();
        this.date = new Date(date.substring(8, 10), date.substring(5, 7), date.substring(0, 4), new Heure(heure.substring(0, 2), heure.substring(3, 5), heure.substring(6, 8)));
    }
    /**
     * Fonction pour générer la carte aléatoirement
     */
    public void generer(){
        for (int i=0 ; i< taille.getY() ; i++){ //parcours x
            for (int j=0 ; j< taille.getX() ; j++){ //parcours y
                if(otage){
                    int random = (int) (Math.random()* Configuration.PROBA_INDIVIDU); //aléatoire
                    switch(random){
                        case 1:
                            tab[i][j] = new Personne(new Coordonnees(i, j)); //personne
                            break;
                        default:
                            tab[i][j] = new Element(new Coordonnees(i, j)); //rien
                            break;
                    }
                }
                else{ //agricole
                    int random = (int) (Math.random()*Configuration.PROBA_ANOMALIE); //aléatoire
                    switch (random) {
                        case 1:
                            tab[i][j] = new Maladie(new Coordonnees(i, j)); //maladie
                            break;
                        case 2:
                            tab[i][j] = new Intrusion(new Coordonnees(i, j)); //intrusion
                            break;
                        case 3:
                            tab[i][j] = new Feu(new Coordonnees(i, j)); //feu
                            break;
                        case 4:
                            tab[i][j] = new Anomalie(new Coordonnees(i, j)); //anomalie
                            break;
                        default:
                            tab[i][j] = new Element(new Coordonnees(i, j)); //rien
                            break;
                    }
                }
            }
        }
    }
    /**
     * Fonction pour afficher la carte dans le terminal (pour tester)
     */
    public void afficher(){
        for (int  i=0 ; i< taille.getX() ; i++){
            for (int j=0 ; j< taille.getY() ; j++){
                System.out.print(tab[i][j].getDesc() + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Fonction pour détecter le nombre de personnes/anomalies
     */
    public void scan(){
        int nb =0;
        for (int i=0 ; i< taille.getY() ; i++){ //parcours x
            for (int j=0 ; j< taille.getX() ; j++){ //parcours y
                if (tab[i][j].getDesc() != "."){ //si pas rien
                    nb++;
                }
            }
        }
        if(otage){
            nb_assaillant = nb - nb_otage; //calcul nombre d'otages
        }
        else {
            nb_anomalie = nb; //calcul nombre d'anomalies
        }
    }

    /**
     * Fonction qui retourne la matrice
     * @return Matrice
     */
    public Element[][] getTab(){
        return tab;
    }

    public Coordonnees getTaille() {
        return taille;
    }

    public boolean isOtage() {
        return otage;
    }

    public Element getElement(int x, int y) {
        return tab[x][y];
    }
}

