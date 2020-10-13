package excepcions;

public class TrackLlistaPlena extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TrackLlistaPlena(int num,int id2,String nom){
		super("L'Album amb id "+id2+" i nom "+nom+" ha arribat al límit d'elements!! S'han llegit "+num+" de cançons!!\n");
	}

}
