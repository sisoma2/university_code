package Excepcions;

public class QuorumEscriptura extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	String val;
	
	public QuorumEscriptura(String val){
		this.val=val;
	}
	
	public String toString(){
		return "S'ha pogut llegir, pero no escriure. El valor mes recent era: "+val;
	}

}
