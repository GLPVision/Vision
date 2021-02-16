package data;

import java.io.IOException;

public class test {
    public static void main(String args []) throws IOException {
        new Otage(10, 10, 10).getCarte().afficher();
        System.out.println("\n");
        new Agriculture(10, 10).getCarte().afficher();
    }
}
