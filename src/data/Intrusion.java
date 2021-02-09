package data;

public class Intrusion extends Anomalie{
    private String intrusion;
    public Intrusion(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*5)){
            case 0:
                intrusion = "homme";
                break;

            case 1:
                intrusion = "lapin";
                break;

            case 2:
                intrusion = "chèvre";
                break;

            case 3:
                intrusion = "vache";
                break;

            case 4:
                intrusion = "cochon";
                break;
        }
    }
    public String getDesc(){
        return intrusion;
    }
}
