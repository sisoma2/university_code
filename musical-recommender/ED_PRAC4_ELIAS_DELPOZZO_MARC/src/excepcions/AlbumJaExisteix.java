package excepcions;

public class AlbumJaExisteix extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AlbumJaExisteix(int id){
		super("L'album amb id: " +id+ " ja est� a la llista!!\n");
	}
}
