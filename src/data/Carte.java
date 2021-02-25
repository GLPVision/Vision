package data;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe carte
 * @author QIU Antoine
 */
public class Carte {
    /**
     * Coordonnées x
     */
    private int x;
    /**
     * Coordonnées y
     */
    private int y;
    /**
     * Matrice de dimension2 / carte
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
     * @param x Coordonnées x
     * @param y Coordonnées y
     * @param mode Otage ou agricole
     * @param nb_otage Nombre d'otages
     */
    public Carte(int x, int y, boolean mode, int nb_otage){
        this.x = x;
        this.y = y;
        this.otage = mode;
        this.nb_otage = nb_otage;
        tab = new Element[this.x][this.y];
        init();
    }

    /**
     * Fonction initialisation
     * @throws IOException Erreur d'écriture lors de l'exportation
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
        //export();
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
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                String desc; //initilisation de la variable
                if(otage){
                    int random = (int) (Math.random()*(5*nb_otage)); //aléatoire
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
                    int random = (int) (Math.random()*(x*y)); //aléatoire
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
        for (int  i=0 ; i<x ; i++){
            for (int j=0 ; j<y ; j++){
                System.out.print(tab[i][j].getDesc() + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Fonctione pour exporter la carte dans un fichier csv
     * @throws IOException Erreur d'écriture lors de l'exportation
     */
    public void export() throws IOException {
        StringBuffer out = new StringBuffer(); //initialisation de la variable
        out.append(date.getJour() + ";" + date.getMois() + ";" + date.getAnnee() + ";" + date.getHeure().getHeure() + ";" + date.getHeure().getMinute() + ";" + date.getHeure().getSeconde() + "\n"); //date et heure
        out.append(x + ";" + y + ";\n"); //taille de la carte
        if (otage){
            out.append(nb_assaillant + ";" + nb_otage + ";\n"); //nombre asaillants et otages
        }
        else{
            out.append(nb_anomalie + ";\n"); //nombre anomalies
        }
        for (int i=0 ; i<x ; i++){ //boucle ecrit la carte dans out
            String line = "";
            for (int j=0 ; j<y ; j++){
                line = line + tab[i][j].getDesc() +  ";";
            }
            line = line + "\n";
            out.append(line);
        }
        BufferedWriter bw = null; //initialise la variable
        if (otage) {
            bw = new BufferedWriter(new FileWriter(".\\" + "last_otage.csv")); //creation fichier otage
        }
        else{
            bw = new BufferedWriter(new FileWriter(".\\" + "last_agricole.csv")); //creation fichier agricole
        }
        bw.write(out.toString()); //ecriture du fichier
        bw.close(); //fermer le fichier
    }

    /**
     * Foonction pour détecter le nombre de personnes/anomalies
     */
    public void scan(){
        int nb =0;
        for (int i=0 ; i<x ; i++){ //parcours x
            for (int j=0 ; j<y ; j++){ //parcours y
                if (tab[i][j].getDesc() != "."){ //si pas rien
                    nb++;
                }
            }
        }
        if(otage){
            nb_assaillant = nb - nb_otage; //calcul nombre d'otages
        }
        else {
            nb_anomalie = nb;
        }
    }

    /**
     * Fonction qui retourne la matrice
     * @return Matrice
     */
    public Element[][] getTab(){
        return tab;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

