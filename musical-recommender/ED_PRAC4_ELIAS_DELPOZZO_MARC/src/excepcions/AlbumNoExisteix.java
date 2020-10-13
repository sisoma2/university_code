package excepcions;

public class AlbumNoExisteix extends Exception{
	private static final long serialVersionUID = 1L;
	
	public AlbumNoExisteix(int id){
		super("L'album amb id: "+id+" no está contingut a la llista!!\n");
	}
	
	public AlbumNoExisteix(){
		super("L'album que intentes consultar no está contingut a la llista!!\n");
	}
}
