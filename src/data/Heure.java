package data;

public class Heure {
    private String heure;
    private String minute;
    private String seconde;
    public Heure(String heure, String minute, String seconde){
        this.heure = heure;
        this.minute = minute;
        this.seconde = seconde;
    }
    public void setHeure(String heure){
        this.heure = heure;
    }
    public void setMinute(String minute){
        this.minute = minute;
    }
    public void setSeconde(String seconde){
        this.seconde = seconde;
    }
    public String getHeure(){
        return heure;
    }
    public String getMinute(){
        return minute;
    }
    public String getSeconde(){
        return seconde;
    }
}
