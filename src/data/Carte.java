package data;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Carte {
    private int x;
    private int y;
    private Element[][] tab;
    private Date date;
    private boolean otage = false;
    private int nb_anomalie;
    private int nb_otage;
    private int nb_assaillant;
    private final float prob = 1/10;//proba de 1/n
    public Carte(int x, int y, boolean mode, int nb_otage) throws IOException {
        this.x = x;
        this.y = y;
        this.otage = mode;
        this.nb_otage = nb_otage;
        tab = new Element[this.x][this.y];
        init();
    }

    public void init() throws IOException {
        setDate();
        generer();
        scan();
        if(otage){
            while(nb_assaillant < nb_otage/4){  //au moins 1 assaillant pour 4 otages
                generer();
                scan();
            }
        }
        export();
    }

    public void setDate(){
        String date = java.time.LocalDate.now().toString();
        String heure = java.time.LocalTime.now().toString();
        this.date = new Date(date.substring(8, 10), date.substring(5, 7), date.substring(0, 4), new Heure(heure.substring(0, 2), heure.substring(3, 5), heure.substring(6, 8)));
    }

    public void generer(){
        for (int i=0 ; i<x ; i++){
            for (int j=0 ; j<y ; j++){
                String desc;
                if(otage){
                    int random = (int) (Math.random()*(x*y/10));
                    switch(random){
                        case 1:
                            tab[i][j] = new Personne(new Coordonnees(i, j));
                            break;
                        default:
                            tab[i][j] = new Element(new Coordonnees(i, j));
                            break;
                    }
                }
                else{ //agri
                    int random = (int) (Math.random()*(x*y));
                    switch (random) {
                        case 1:
                            tab[i][j] = new Maladie(new Coordonnees(i, j));
                            break;
                        case 2:
                            tab[i][j] = new Intrusion(new Coordonnees(i, j));
                            break;
                        case 3:
                            tab[i][j] = new Feu(new Coordonnees(i, j));
                            break;
                        case 4:
                            tab[i][j] = new Anomalie(new Coordonnees(i, j));
                            break;
                        default:
                            tab[i][j] = new Element(new Coordonnees(i, j));
                            break;
                    }
                }
            }
        }
    }
    public void afficher(){
        for (int  i=0 ; i<x ; i++){
            for (int j=0 ; j<y ; j++){
                System.out.print(tab[i][j].getDesc() + "\t");
            }
            System.out.print("\n");
        }
    }

    public void export() throws IOException {
        StringBuffer out = new StringBuffer();
        out.append(date.getJour() + ";" + date.getMois() + ";" + date.getAnnee() + ";" + date.getHeure().getHeure() + ";" + date.getHeure().getMinute() + ";" + date.getHeure().getSeconde() + "\n");
        out.append(x + ";" + y + ";\n");
        if (otage){
            out.append(nb_assaillant + ";" + nb_otage + ";\n");
        }
        else{
            out.append(nb_anomalie + ";\n");
        }
        for (int i=0 ; i<x ; i++){
            String line = "";
            for (int j=0 ; j<y ; j++){
                line = line + tab[i][j].getDesc() +  ";";
            }
            line = line + "\n";
            out.append(line);
        }
        BufferedWriter bw = null;
        if (otage) {
            bw = new BufferedWriter(new FileWriter(".\\" + "last_otage.csv"));
        }
        else{
            bw = new BufferedWriter(new FileWriter(".\\" + "last_agricole.csv"));
        }
        bw.write(out.toString()); //ecriture du fichier
        bw.close(); //fermer le fichier
    }
    public void scan(){
        int nb =0;
        for (int i=0 ; i<x ; i++){
            for (int j=0 ; j<y ; j++){
                if (tab[i][j].getDesc() != "."){
                    nb++;
                }
            }
        }
        if(otage){
            nb_assaillant = nb - nb_otage;
        }
        else {
            nb_anomalie = nb;
        }
    }
}

