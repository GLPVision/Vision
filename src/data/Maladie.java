package data;

public class Maladie extends Anomalie {
    private String maladie;
    public Maladie(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*4)){
            case 0:
                maladie = "mort";
                break;
            case 1:
                maladie = "virus";
                break;
            case 2:
                maladie = "bactérie";
                break;
            case 3:
                maladie = "vieux";
                break;
        }
    }
    public String getDesc(){
        return maladie;
    }
}
