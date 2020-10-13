package excepcions;

public class TaulaHashArtistaNoTrobat extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TaulaHashArtistaNoTrobat(String nom){
		super("L'artista amb nom: "+nom+" no está contingut a la llista!!\n");
	}
}