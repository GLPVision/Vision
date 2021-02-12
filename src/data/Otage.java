package data;

import java.io.IOException;

public class Otage extends Scenario{

    public Otage(int x, int y, int nbOtage) throws IOException {
        carte = new Carte(x, y, true, nbOtage); //otage + otage/4 < x*y/2
    }
}
