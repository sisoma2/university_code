package excepcions;

public class TrackLlistaBuida extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TrackLlistaBuida(){
		super("L'Album que intentes consultar est� buit!!\n");
	}
}
