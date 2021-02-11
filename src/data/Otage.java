package data;

import java.io.IOException;

public class Otage extends Scenario{
    private Carte carte;
    public Otage(int x, int y) throws IOException {
        carte = new Carte(x, y, new Date("01", "01", "2000", new Heure("12", "00", "00")), true); //idee mettre date et heure actuelle
        carte.generer();
        carte.export();
        carte.afficher();
    }
    public static void main(String args []) throws IOException {
        new Otage(10, 10);
    }
}
