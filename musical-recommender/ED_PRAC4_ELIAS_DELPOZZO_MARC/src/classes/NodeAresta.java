package classes;

/**
 * The Class NodeAresta.
 *
 * @param <K> the key type
 * @param <F> the generic type
 */
public class NodeAresta <K extends Comparable<K>, F>{
	
	/** The autor2. */
	K a1,a2;
	
	/** The grau. */
	F grau; 
	
	/** The seguentcl1. */
	NodeAresta<K, F> seg1;
	
	/** The seguentcl2. */
	NodeAresta<K, F> seg2;
}
