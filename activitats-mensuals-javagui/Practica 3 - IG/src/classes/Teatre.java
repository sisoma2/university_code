package classes;

public class Teatre extends Activitat {
	private int capacitatSala;
    private double preuAactivitat;
    private String HoraIniciActivitat;
    
    public Teatre(String tipusActivitat, int[] dies, String nomActivitat, String publicDestinatari, int capacitatSala, String HoraIniciActivitat, double preu){
        super(tipusActivitat, dies, nomActivitat, publicDestinatari, "Teatre Metropol" );
        this.setHoraIniciActivitat(HoraIniciActivitat);
        this.setCapacitatSala(capacitatSala);
        this.setPreuAactivitat(preu);
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
