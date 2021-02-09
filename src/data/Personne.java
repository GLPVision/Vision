package data;

public class Personne extends Element{
    private String personne;


    public Personne(Coordonnees c) {
        super(c);
        this.personne = "personne";
    }
    public String getDesc(){
        return personne;
    }
}
