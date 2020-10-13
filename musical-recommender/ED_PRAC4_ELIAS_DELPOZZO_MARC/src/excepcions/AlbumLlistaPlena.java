package excepcions;

public class AlbumLlistaPlena extends Exception{
	private static final long serialVersionUID = 1L;
	
	public AlbumLlistaPlena(int num){
		super("La llista ha arribat al límit d'elements!! S'han llegit "+num+" d'albums!!\n");
	}
}
