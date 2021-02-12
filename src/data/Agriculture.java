package data;

import java.io.IOException;

public class Agriculture extends Scenario {
    public Agriculture(int x, int y) throws IOException {
        carte = new Carte(x, y, false, 0);
    }

}
