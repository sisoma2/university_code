package excepcions;

public class TrackJaExisteix extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TrackJaExisteix(int id,int id2,String nom){
		super("La can�� amb id: " +id+ " ja est� a la l'Album amb id "+id2+" i nom "+nom+"!!\n");
	}
}
