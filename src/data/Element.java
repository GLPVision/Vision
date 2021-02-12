package data;

public class Element {
    private Coordonnees coordonnees;
    private String desc;
    public Element(Coordonnees c){
        coordonnees = c;
        this.desc = ".";
    }
    public String getDesc(){
        return desc;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }
}
