package data;

public class Intrusion extends Anomalie{
    private String intrusion;
    public Intrusion(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*6)){
            case 0:
                intrusion = "ho"; //homme
                break;

            case 1:
                intrusion = "la"; //lapin
                break;

            case 2:
                intrusion = "mo"; //mouton
                break;

            case 3:
                intrusion = "va"; //vache
                break;

            case 4:
                intrusion = "co"; //cochon
                break;
            case 5:
                intrusion = "oi"; //oiseaux
                break;
        }
    }
    public String getDesc(){
        return intrusion;
    }
}
