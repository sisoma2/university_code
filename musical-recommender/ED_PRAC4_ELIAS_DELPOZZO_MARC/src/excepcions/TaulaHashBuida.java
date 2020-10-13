package excepcions;

public class TaulaHashBuida extends Exception{
	private static final long serialVersionUID = 1L;

	public TaulaHashBuida(){
		super("La llista que intentes consultar está buida!!\n");
	}
}
