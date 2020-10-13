package excepcions;

public class TaulaHashAnyNoTrobat extends Exception {
	private static final long serialVersionUID = 429512664379111823L;
	
	public TaulaHashAnyNoTrobat(int any){
		super("No s'han trobat Albums per a l'any "+any+"!!\n");
	}
}
