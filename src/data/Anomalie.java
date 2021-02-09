package data;

public class Anomalie extends Element{
    private String anomalie;


    public Anomalie(Coordonnees c) {
        super(c);
        anomalie = "Inconnu";
    }
    public String getDesc(){
        return anomalie;
    }

}
