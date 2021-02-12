package data;

public class Feu extends Anomalie{
    private String feu;
    public Feu(Coordonnees c) {
        super(c);
        switch ((int) (Math.random()*3)){
            case 0:
                feu = "fs"; //feu small
                break;
            case 1:
                feu = "fm"; //feu medium
                break;
            case 2:
                feu = "fl"; //feu large
                break;
        }
    }
    public String getDesc(){
        return feu;
    }

}
