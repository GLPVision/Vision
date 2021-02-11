package data;

import java.io.IOException;

public class Agriculture extends Scenario{
    private Carte carte;
    public Agriculture(int x, int y) throws IOException {
        carte = new Carte(x, y, new Date("01", "01", "2000", new Heure("12", "00", "00")), false); //idee mettre date et heure actuelle
        carte.generer();
        carte.export();
        carte.afficher();
    }
    public static void main(String args []) throws IOException {
        new Agriculture(10, 10);
    }
}
