package excepcions;

public class TaulaHashPlena extends Exception {
	private static final long serialVersionUID = 1L;
	
	public TaulaHashPlena(int max){
		super("La llista ha arribat al l�mit d'elements!! S'han llegit "+max+" artistes!!\n");
	}
}
