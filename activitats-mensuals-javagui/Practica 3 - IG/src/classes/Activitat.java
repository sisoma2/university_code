package classes;

public abstract class Activitat {
	protected int[] dies;
	protected String nomActivitat;
	protected String publicDestinatari;
	protected String lloc;
	protected String tipusActivitat;
    
    public Activitat(String tipusActivitat,int[] dies, String nomActivitat, String publicDestinatari, String lloc){
        this.dies = dies;
        this.nomActivitat= nomActivitat;
        this.publicDestinatari = publicDestinatari;
        this.lloc = lloc;
        this.tipusActivitat = tipusActivitat;
    }
    
    public String getLloc(){
        return lloc;
    }
    public int[] getDies() {
        return dies;
    }
    public void setDies(int[] dies) {
        this.setDies(dies);
    }
    public String getNomActivitat() {
        return nomActivitat;
    }
    public void setNomActivitat(String nomActivitat) {
        this.nomActivitat = nomActivitat;
    }
    public String getPublicDestinatari() {
        return publicDestinatari;
    }  
    public void setPublicDestinatari(String publicDestinatari) {
        this.publicDestinatari = publicDestinatari;
    }     
    public void setLlloc(String lloc) {
        this.lloc = lloc;
    }
    public String getTipusActivitat() {
        return tipusActivitat;
    }
    public void setTipusActivitat(String tipusActivitat) {
        this.tipusActivitat = tipusActivitat;
    }
    
}


