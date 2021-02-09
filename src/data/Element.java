package data;

public class Element {
    private Coordonnees coordonnees;
    private String desc;
    public Element(Coordonnees c){
        coordonnees = c;
        this.desc = "rien";
    }
    public String getDesc(){
        return desc;
    }
}
