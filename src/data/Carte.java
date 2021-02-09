package data;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Carte {
    private int x;
    private int y;
    private Element[][] tab;
    private Date date;
    boolean otage = false;
    final int prob = 10;//proba de 1/n
    public Carte(int x, int y, Date date, boolean mode){
        tab = new Element[x][y];
        this.x = x;
        this.y = y;
        this.date = date;
        this.otage = mode;
    }

    public void generer(){
        for (int i=0 ; i<x ; i++){
            for (int j=0 ; j<y ; j++){
                String desc;
                if(otage){
                    int random = (int) (Math.random()*prob+1);
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
                    int random = (int) (Math.random() * 10);
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
        if(otage){
            out.append("otage;\n");
        }
        else{
            out.append("agricole\n");
        }
        out.append(date.getJour() + ";" + date.getMois() + ";" + date.getAnnee() + ";" + date.getHeure().getHeure() + ";" + date.getHeure().getMinute() + ";" + date.getHeure().getSeconde() + "\n");
        out.append(x + ";" + y + ";\n");
        for (int i=0 ; i<x ; i++){
            String line = "";
            for (int j=0 ; j<y ; j++){
                line = line + tab[i][j].getDesc() +  ";";
            }
            line = line + "\n";
            out.append(line);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(".\\" + "exp.csv"));
        bw.write(out.toString()); //ecriture du fichier
        bw.close(); //fermer le fichier
    }


    public static void main(String[] args) throws IOException {
        Carte c = new Carte(10, 10, new Date("9", "02", "2021", new Heure("14", "30", "00")), false);
        c.generer();
        c.afficher();
        c.export();
    }
}

