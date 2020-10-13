package classes;

public class Concert extends Activitat{
	private int capacitatSala;
    private double preuAactivitat;
    private String HoraIniciActivitat;
    
    public Concert(String tipusActivitat, int[] dies, String nomActivitat, String publicDestinatari, String lloc, int capacitatSala, String HoraIniciActivitat){
        super(tipusActivitat,dies, nomActivitat, publicDestinatari, lloc);
        this.capacitatSala = capacitatSala;
        this.HoraIniciActivitat = HoraIniciActivitat;
        this.setPreuAactivitat(10.0);
    }
	public int getCapacitatSala() {
		return capacitatSala;
	}
	public void setCapacitatSala(int capacitatSala) {
		this.capacitatSala = capacitatSala;
	}
	public double getPreuAactivitat() {
		return preuAactivitat;
	}
	public void setPreuAactivitat(double preuAactivitat) {
		this.preuAactivitat = preuAactivitat;
	}
	public String getHoraIniciActivitat() {
		return HoraIniciActivitat;
	}
	public void setHoraIniciActivitat(String horaIniciActivitat) {
		HoraIniciActivitat = horaIniciActivitat;
	}
}
