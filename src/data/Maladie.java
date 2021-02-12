package data;

public class Maladie extends Anomalie {
    private String maladie;
    public Maladie(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*4)){
            case 0:
                maladie = "m"; //mort
                break;
            case 1:
                maladie = "v"; //virus
                break;
            case 2:
                maladie = "b"; //bacterie
                break;
            case 3:
                maladie = "a"; //age
                break;
        }
    }
    public String getDesc(){
        return maladie;
    }
}
