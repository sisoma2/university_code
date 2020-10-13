package classes;

import java.util.Vector;

import excepcions.*;

/**
 * The Class Graf.
 *
 * @param <K> the key type
 * @param <E> the element type
 * @param <F> the generic type
 */
public class Graf <K extends Comparable<K>, E , F>{
	
	/** The fi llista. */
	static int FI_LLISTA=-1;
	
	/** The pos buida. */
	static int POS_BUIDA=-2;
	
	/** The t hash. */
	private Vector<NodeGraf<K,E,F>> tHash;
	
	/** The num elements. */
	private int segLliure,max,numElements;
	
	/**
	 * Instantiates a new graf.
	 *
	 * @param max the max
	 */
	public Graf(int max){
		numElements=0;
		double temp = (int) max*1.3;
		int mida = (int) temp;
		this.max = max;
		tHash = new Vector<NodeGraf<K,E,F>>(mida);
		
		//Zona Principal
		for(int i=0;i<max;i++){
			tHash.add(new NodeGraf<K,E,F>());
			tHash.get(i).seg=POS_BUIDA;
			tHash.get(i).primeraAresta=null;
		}
		segLliure=max;
		
		//Zona Excedents
		for(int j=max;j<mida-1;j++){
			tHash.add(new NodeGraf<K,E,F>());
			tHash.get(j).seg=j+1;
			tHash.get(j).primeraAresta=null;
		}
		
		tHash.add(new NodeGraf<K,E,F>());
		tHash.get(mida-1).seg=FI_LLISTA;
		tHash.get(mida-1).primeraAresta=null;
	}
	
	/**
	 * Adds the vertex.
	 *
	 * @param k the k
	 * @param v the v
	 * @throws TaulaHashPlena the taula hash plena
	 */
	public void addVertex(K k, E v) throws TaulaHashPlena{
		int i=0;
		
		i = funcHash(k);
		int iant=i;
		while(i>=0 && !k.equals(tHash.get(i).clau)){
			iant=i;
			i=tHash.get(i).seg;
		}
		
		if(i==POS_BUIDA){
			//La clau no hi era, inserim a la zona principal
			tHash.get(iant).clau=k;
			tHash.get(iant).valor=v;
			tHash.get(iant).primeraAresta=null;
			tHash.get(iant).seg=FI_LLISTA;
			
			//Sumem 1 al numero d'elements de la taula de hash
			numElements++;
		} else if(i == FI_LLISTA){
			//La clau no hi era, inserim a la zona d'excedents
			int nou=segLliure;
			if(nou==FI_LLISTA) throw new TaulaHashPlena(numElements);
			segLliure=tHash.get(nou).seg;
			
			//Inicialitzem bloc
			tHash.get(nou).clau=k;
			tHash.get(nou).valor=v;
			tHash.get(nou).primeraAresta=null;
			tHash.get(nou).seg=FI_LLISTA;
			
			//Encadenem a la llista de sinonims
			tHash.get(iant).seg=nou;
			
			//Sumem 1 al numero d'elements de la taula de hash
			numElements++;
		} else {
			//La Clau existeix i llancem excepció
			//Excepció Artista ja existeix
			tHash.get(i).valor=v;
		}
	}
	
	/**
	 * Adds the link.
	 *
	 * @param a1 the a1
	 * @param a2 the a2
	 * @param valorLink the valor link
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void addLink(K a1, K a2, F valorLink) throws TaulaHashArtistaNoTrobat{
		
		int posicio1=funcHash(a1);
		int posicio2=funcHash(a2);
		boolean trobat=false;
		
		while(posicio1>=0 && !a1.equals(tHash.get(posicio1).clau)){
			posicio1=tHash.get(posicio1).seg;
		} 
		
		if(posicio1<0){
			throw new TaulaHashArtistaNoTrobat(a1.toString());
		}
		
		while(posicio2>=0 && !a2.equals(tHash.get(posicio2).clau)){
			posicio2=tHash.get(posicio2).seg;
		} 
		
		if(posicio2<0){
			throw new TaulaHashArtistaNoTrobat(a2.toString());
		}
		
		trobat=false;
		NodeAresta<K, F> nodeA=tHash.get(posicio1).primeraAresta;
		
		while(trobat==false && nodeA!=null){
			if(nodeA.a1.equals(a2) || nodeA.a2.equals(a2)){
				nodeA.grau=valorLink;
				trobat=true;
			}
			if(nodeA.a1.equals(a1)){
				nodeA=nodeA.seg1;
			} else {
				nodeA=nodeA.seg2;
			}
		}
		
		if(trobat==false){
			NodeAresta<K, F> nodeB=new NodeAresta<K,F>();
			nodeB.a1=a1;
			nodeB.a2=a2;
			nodeB.grau=valorLink;
			nodeB.seg1=tHash.get(posicio1).primeraAresta;
			nodeB.seg2=tHash.get(posicio2).primeraAresta;
			tHash.get(posicio1).primeraAresta=nodeB;
			tHash.get(posicio2).primeraAresta=nodeB;
			
		}
	}
	
	/**
	 * Removes the vertex.
	 *
	 * @param k the k
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void removeVertex(K k) throws TaulaHashArtistaNoTrobat{
		int i = funcHash(k);
		
		int iant=i;
		while(i>=0 && !k.equals(tHash.get(i).clau)){
			iant=i;
			i=tHash.get(i).seg;
		} 
		
		if(i<0){
			throw new TaulaHashArtistaNoTrobat(k.toString());
		}
		
		if(tHash.get(i).seg == FI_LLISTA && i<max){
			//Sense colisions, marquem posició lliure
			tHash.get(i).seg=POS_BUIDA;
			tHash.get(i).clau=null;
			tHash.get(i).valor=null;
		} else if (tHash.get(i).seg != FI_LLISTA && i<max){
			//Amb colisions i el que volem eliminar está a zona principal l'eliminen
				tHash.get(i).clau=null;
				tHash.get(i).valor=null;
		} else {
			tHash.get(iant).seg=tHash.get(i).seg;
			//Eliminen la posició i actualitzem segLliure
			tHash.get(i).clau=null;
			tHash.get(i).valor=null;
			tHash.get(i).seg=segLliure;
			segLliure=i;
		}
		//Restem 1 al numero d'elements de la taula de hash
		numElements--;
	}
	
	/**
	 * Removes the link.
	 *
	 * @param a1 the a1
	 * @param a2 the a2
	 * @throws LinkNoExisteix the link no existeix
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void removeLink(K a1, K a2) throws LinkNoExisteix, TaulaHashArtistaNoTrobat{
		int posicio1=funcHash(a1);
		int posicio2=funcHash(a2);
		boolean trobat=false;
		
		while(posicio1>=0 && !a1.equals(tHash.get(posicio1).clau)){
			posicio1=tHash.get(posicio1).seg;
		} 
		
		if(posicio1<0){
			throw new TaulaHashArtistaNoTrobat(a1.toString());
		}
		
		while(posicio2>=0 && !a2.equals(tHash.get(posicio2).clau)){
			posicio2=tHash.get(posicio2).seg;
		} 
		
		if(posicio2<0){
			throw new TaulaHashArtistaNoTrobat(a2.toString());
		}
		
		NodeAresta <K, F> aux1=tHash.get(posicio2).primeraAresta;
		NodeAresta <K, F> aux2=aux1;
		
		while(aux1 != null && !trobat){
			if((aux1.a1.equals(a1) && aux1.a2.equals(a2))||(aux1.a2.equals(a1) && aux1.a1.equals(a2))){
				if(aux1.a1.equals(tHash.get(posicio2).primeraAresta.a1)){
					if(aux1.a1.equals(a2)){									
						tHash.get(posicio2).primeraAresta=aux1.seg1;
					} else {
						tHash.get(posicio2).primeraAresta=aux1.seg2;
					}
				} else {
					if(aux1.a2.equals(a1) && aux2.a2.equals(a1)){
						aux2.seg2=aux1.seg2;
					} else if(aux1.a1.equals(a1) && aux2.a1.equals(a1)){
						aux2.seg1=aux1.seg1;
					} else if(aux1.a1.equals(a1) && aux2.a2.equals(a1)){
						aux2.a2=aux1.a1;
					} else {
						aux2.a1=aux1.a2;
					}
				}
				trobat=true;
			}
			aux2=aux1;
			if(aux1.a1.equals(a1)){
				aux1=aux1.seg1;
			}else{
				aux1=aux1.seg2;
			}
		}
		
		if(trobat=true){
			trobat=false;
			aux1=tHash.get(posicio1).primeraAresta;
			aux2=aux1;
			while(aux1 != null && trobat==false){
				if((aux1.a1.equals(a1) && aux1.a2.equals(a2))||(aux1.a2.equals(a1) && aux1.a1.equals(a2))){
					if(aux1.a1.equals(tHash.get(posicio1).primeraAresta.a1)){ 
						if(aux1.a1.equals(a1)){
							tHash.get(posicio1).primeraAresta=aux1.seg1;
						} else {
							tHash.get(posicio1).primeraAresta=aux1.seg2;
						}
					} else {
						if(aux1.a2.equals(a2) && aux2.a2.equals(a2)){
							aux2.seg2=aux1.seg2;
						} else if(aux1.a1.equals(a2) && aux2.a1.equals(a2)){
							aux2.seg1=aux1.seg1;
						} else if(aux1.a1.equals(a2) && aux2.a2.equals(a2)){
							aux2.a2=aux1.a1;
						} else{
							aux2.a1=aux1.a2;
						}
					}
					trobat=true;
					aux1=null;
				}
				if(aux1!=null){
				aux2=aux1;
					if(aux1.a1.equals(a2)){
						aux1=aux1.seg1;
					}else{
						aux1=aux1.seg2;
					}
				}
			}
		}
		if(trobat==false){
			throw new LinkNoExisteix(a1.toString(),a2.toString());
		}
	}
	
	/**
	 * Gets the link.
	 *
	 * @param a1 the a1
	 * @param a2 the a2
	 * @return the link
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 * @throws LinkNoExisteix the link no existeix
	 */
	public F getLink(K a1, K a2) throws TaulaHashArtistaNoTrobat, LinkNoExisteix{
		
		int i=funcHash(a1);
		
		while(i>=0 && !a1.equals(tHash.get(i).clau)){
			i=tHash.get(i).seg;
		} 
		
		if(i<0){
			throw new TaulaHashArtistaNoTrobat(a1.toString());
		}
		
		F link= null;
		boolean trobat=false;
		NodeAresta<K, F> nodeA=tHash.get(i).primeraAresta;
		
		while(nodeA!=null && !trobat){
			if(nodeA.a1.equals(a2) || nodeA.a2.equals(a2)){
				link= nodeA.grau;
				trobat=true;
			}
			if(nodeA.a1.equals(a1)){
				nodeA=nodeA.seg1;
			}else{
				nodeA=nodeA.seg2;
			}
		}
		
		if(link != null){
			return link;
		} else {
			throw new LinkNoExisteix(a1.toString(), a2.toString());
		}
	}
	
	/**
	 * Gets the all links.
	 *
	 * @param autor the autor
	 * @return the all links
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public Vector<NodeLink<K,F>> getAllLinks(K autor) throws TaulaHashArtistaNoTrobat{
		int i=funcHash(autor);
		
		while(i>=0 && !autor.equals(tHash.get(i).clau)){
			i=tHash.get(i).seg;
		} 
		
		if(i<0){
			throw new TaulaHashArtistaNoTrobat(autor.toString());
		}
		
		NodeAresta<K, F> node = tHash.get(i).primeraAresta;
		Vector<NodeLink<K,F>> llista = new Vector<NodeLink<K,F>>();
		
		int j=0;
		while(node!=null && i>=0){
			K a2=null;
			if(node.a1.equals(autor)){
				a2 = node.a2;
			} else {
				a2 = node.a1;
			}
			NodeLink<K,F> aux = new NodeLink<K,F>();
			aux.autor=a2;
			aux.grau=node.grau;
			llista.add(aux);
			System.out.println(llista.get(j).autor+"-->"+llista.get(j).grau);
			j++;
			if(autor.equals(node.a1)){
				node=node.seg1;
			} else {
				node=node.seg2;
			}
		}
		return llista;
	}
	
	/**
	 * Gets the vertex.
	 *
	 * @param k the k
	 * @return the vertex
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public E getVertex(K k) throws TaulaHashArtistaNoTrobat{
		int i=funcHash(k);
		
		while(i>=0 && !k.equals(tHash.get(i).clau)){
			i=tHash.get(i).seg;
		} 
		
		if(i<0){
			throw new TaulaHashArtistaNoTrobat(k.toString());
		} else {
			return tHash.get(i).valor;
		}
	}
	
	/**
	 * Gets the all vertexs.
	 *
	 * @return the all vertexs
	 * @throws TaulaHashBuida the taula hash buida
	 */
	public Vector<K> getAllVertexs() throws TaulaHashBuida{
		if(isEmpty()) throw new TaulaHashBuida();
		Vector<K> llista = new Vector<K>();
		
		for(int i=0; i<tHash.size() ; i++){
			if(tHash.get(i).valor != null){
				llista.add(tHash.get(i).clau);
			}
		}
		
		return llista;
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		if(numElements==0) return true;
		else return false;
	}
	
	/**
	 * Func hash.
	 *
	 * @param clau the clau
	 * @return the int
	 */
	public int funcHash(K clau){
		  String semilla = "EsTruCtuRa De dAdEs";
		  int semCode = semilla.hashCode();
	      int ret=(clau.hashCode()^semCode)%max;
	      if(ret<0) ret=-ret;
	      return ret;
	}
	
	/**
	 * Gets the load factor.
	 *
	 * @return the load factor
	 */
	public double getLoadFactor(){
		double load = 1.0*numElements/max;
		return load;
	}
	
	/**
	 * Gets the num elements.
	 *
	 * @return the num elements
	 */
	public int getNumElements(){
		return numElements;
	}
}
