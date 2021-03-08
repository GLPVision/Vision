package data;

import java.io.IOException;

public class Test {
    public static void main(String args []) throws IOException {
        new Otage(new Coordonnees(10, 10), 10).getCarte().afficher();
        System.out.println("\n");
        new Agriculture(new Coordonnees(10, 10)).getCarte().afficher();
    }
}
