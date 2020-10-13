package excepcions;

public class LinkNoExisteix extends Exception{
	private static final long serialVersionUID = 1L;
	
	public LinkNoExisteix(String a1, String a2){
		super("No s'ha trobat cap relació entre l'artista "+ a1+ " i " + a2 +"!!\n");
	}
}
