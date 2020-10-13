package excepcions;

public class TrackNoExisteix extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TrackNoExisteix(int id,int id2,String nom){
		super("La cançó amb id "+id+" no está continguda a l'Album amb id "+id2+" i nom "+nom+"!!\n");
	}
}
