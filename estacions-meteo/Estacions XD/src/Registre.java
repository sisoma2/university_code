import java.net.InetAddress;

public class Registre {
	private String nickname; // nom del servidor o nickname de l'usuari
	private InetAddress IPadd;
	private int port;
	private String viu;

	public Registre (InetAddress adresa, int portRemot) {
		nickname= "";
		port=portRemot;
		IPadd= adresa;
		viu="*";
	}

	// constructor
	public Registre (String nom, InetAddress adresa, int portRemot) {
		nickname= nom;
		port=portRemot;
		IPadd= adresa;
		viu="*";
	}

	// comparació: imprescindible per a que funcioni .contains
	public boolean equals(Object obj){
		if(obj == this){  // es ell mateix?
			return true;
		}
		// comprovem que ES un objecte
		if(obj == null || obj.getClass() != this.getClass()){
			return false;
		}
		// cast de tipus per a l'acccés
		Registre server2 = (Registre) obj;
		return(	IPadd.equals(server2.IPadd) && port==server2.port);
	}

	public void setNom(String nom) {
		this.nickname=nom;
	}
	public String getNom() {
		return nickname;
	}
	public void setIPadd(InetAddress ip) {
		this.IPadd=ip;
	}
	public InetAddress getIPadd() {
		return IPadd;
	}	
	public String getIP() {
		return IPadd.getHostAddress();
	}
	public void setPort(int port) {
		this.port=port;
	}
	public int getPort() {
		return port;
	}
	public String getViu() {
		return viu;
	}
	public void marcaViu() {
		viu="V";
	}
	public void ellMateix() {
		viu="=";
	}
	public void marcaMort(){
		viu="*";
	}

	public String toString(){
		if (nickname.isEmpty())
			return IPadd.getHostAddress()+":"+String.valueOf(port);
		else 
			return nickname+":"+IPadd.getHostAddress()+":"+String.valueOf(port);

	}
}
