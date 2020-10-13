package Excepcions;

public class QuorumLectura extends Exception{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return "No s'ha pogut llegir.";
	}
}
