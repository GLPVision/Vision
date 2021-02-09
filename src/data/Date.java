package data;

public class Date {
    private String jour;
    private String mois;
    private String annee;
    private Heure heure;
    public Date(String jour, String mois, String annee, Heure heure){
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
        this.heure = heure;
    }
    public void setJour(String jour){
        this.jour = jour;
    }
    public void setMois(String mois){
        this.mois = mois;
    }
    public void setAnnee(String annee){
        this.annee = annee;
    }
    public void setHeure(Heure heure){
        this.heure = heure;
    }
    public String getJour(){
        return jour;
    }
    public String getMois(){
        return mois;
    }
    public String getAnnee(){
        return annee;
    }
    public Heure getHeure(){
        return heure;
    }
}
