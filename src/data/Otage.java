package data;

import java.io.IOException;

public class Otage extends Scenario{

    public Otage(int x, int y) throws IOException {
        carte = new Carte(x, y, true, 12); //otage + otage/4 < x*y/2
    }
}
