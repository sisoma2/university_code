import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LlistaClients implements Iterable<Registre> {
private List<Registre> participants;
	
	// constructor
	public LlistaClients() {
		participants= new ArrayList<Registre>();
	}
	
	public boolean afegirServer (String nom, InetAddress adresa, int portRemot) {
		Registre server= new Registre(nom,adresa,portRemot); // crear la estructura
		// buscar-lo a la llista i si no hi es afegirlo
		if (!participants.contains(server)) {
			participants.add(server);
			return(true);
		}
		return(false);
	}
	
	public int indexOf(Registre r) {
		return participants.indexOf(r);
	}
	
	public boolean afegirServer (InetAddress adresa, int portRemot) {
		Registre server= new Registre(adresa,portRemot); // crear la estructura
		// buscar-lo a la llista i si no hi es afegirlo
		if (!participants.contains(server)) {
			participants.add(server);
			return(true);
		}
		return(false);
	}
	
	public Registre get(int index) {
		return participants.get(index);
	}
	
	public boolean treuServer (String nom, InetAddress adresa, int portRemot) {
		Registre server= new Registre(nom,adresa,portRemot); // crear la estructura
		// buscar-lo a la llista i si hi es treure'l
		if (participants.contains(server)) {
			participants.remove(server);
			return(true);
		}
		return(false);
	}
	
	public int quants () {
		return participants.size();
	}
	
    @Override
    public Iterator<Registre> iterator() {
    	return (participants.iterator());
    }
}
