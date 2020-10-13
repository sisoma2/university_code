package classes;

/**
 * The Class NodeGraf.
 *
 * @param <K> the key type
 * @param <E> the element type
 * @param <F> the generic type
 */
public class NodeGraf <K extends Comparable<K>, E, F> {
	/** The seg. */
	int seg;
	
	/** The clau. */
	K clau;
	
	/** The valor. */
	E valor;
	
	/** The primera aresta. */
	NodeAresta<K,F> primeraAresta;
}
