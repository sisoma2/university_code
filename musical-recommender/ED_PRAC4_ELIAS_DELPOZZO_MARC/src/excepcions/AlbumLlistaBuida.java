package excepcions;

public class AlbumLlistaBuida extends Exception{
	private static final long serialVersionUID = 1L;
	
	public AlbumLlistaBuida(){
		super("La llista que intentes consultar está buida!!\n");
	}
}
