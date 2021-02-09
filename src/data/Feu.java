package data;

public class Feu extends Anomalie{
    private String feu;
    public Feu(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*3)){
            case 0:
                feu = "petit feu";
                break;
            case 1:
                feu = "moyen feu";
                break;
            case 2:
                feu = "grand feu";
                break;
        }
    }
    public String getDesc(){
        return feu;
    }

}
