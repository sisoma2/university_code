package excepcions;

public class TrackNoExisteix extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TrackNoExisteix(int id,int id2,String nom){
		super("La can�� amb id "+id+" no est� continguda a l'Album amb id "+id2+" i nom "+nom+"!!\n");
	}
}
