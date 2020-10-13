package classes;

public class Exposicio extends Activitat {
	private String[] franjaHorari;

    public Exposicio(String tipusActivitat,int[] dies, String nomActivitat, String lloc, String publicDestinatari, String[] franjaHorari){
        super(tipusActivitat,dies, nomActivitat, publicDestinatari, lloc);
        this.franjaHorari = franjaHorari;
    }
    
    public String[] getFranjaHorari() {
        return franjaHorari;
    }  
     
    public void setFranjaHorari(String[] franjaHorari) {
        this.franjaHorari = franjaHorari;
    }
}
