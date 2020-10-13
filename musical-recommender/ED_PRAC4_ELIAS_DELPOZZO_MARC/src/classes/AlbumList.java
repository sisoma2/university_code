package classes;

import interfaces.AlbumListInterface;
import excepcions.*;

/**
 * La clase AlbumListPunters.
 */
public class AlbumList implements AlbumListInterface{
	
	/** El primer node. */
	private NodeListDinamica primer;
	
	/** L'ultim node. */
	private NodeListDinamica ultim;
	
	/** El maxim. */
	private int numAlbums,max;

	/**
	 * Instancia una nova Llista d'albums de punters.
	 * 
	 * @param max
	 *            el maxim d'albums
	 */
	public AlbumList(int max){											//Constructor de la classe
		this.max=max;
		numAlbums=0;
		primer = new NodeListDinamica();
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#addAlbum(classes.Album)
	 */
	public void addAlbum(Album album) throws AlbumJaExisteix, AlbumLlistaPlena {
		boolean trobat=false;
		
		if(numAlbums>=max){ 													//Llista plena
			throw new AlbumLlistaPlena(max);
		}
		
		NodeListDinamica node = primer; 										//Cerca del Album
		while(node.seguent != null && !trobat) {
			if(node.seguent.informacio.getId() == album.getId()){
				trobat = true;
			} else {
				node=node.seguent;
			}
		}
		
		if(trobat){ 															//Album Ja existent
			throw new AlbumJaExisteix(album.getId());
		}
		
		
		NodeListDinamica nouElement = new NodeListDinamica(); 				//Creem el node a la llista
		nouElement.informacio=album;
		if(numAlbums == 0){ 												//Cas de que la llista estigui buida
			nouElement.anterior=primer;
			nouElement.seguent=null;
			primer.seguent=nouElement; 		 								//Actualitzem punters
		} else { 															//Cas de que el primer no sigui null
			node = primer;
			while(node.seguent !=null && node.seguent.informacio.getId() < album.getId()) {
				node=node.seguent;
			}
			if(node.seguent!=null){											//Introduir entremig de 2 albums
				NodeListDinamica aux=node.seguent;
				node.seguent=nouElement;
				nouElement.anterior=node;
				nouElement.seguent=aux;
				aux.anterior=nouElement;
			}else{															//Introduir al final de la llista
				node.seguent=nouElement;
				nouElement.anterior=node;
				nouElement.seguent=null;
			}
		}
		ultim = nouElement;
		numAlbums++;
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#removeAlbum(int)
	 */
	public void removeAlbum(int id) throws AlbumNoExisteix,AlbumLlistaBuida {
		boolean trobat=false;
		NodeListDinamica actual=null;
		if(numAlbums == 0) throw new AlbumLlistaBuida();					//Si no hi ha albums llavors llista esta buida
		
		NodeListDinamica node = primer;										//Trobem els nodes a modificar
		while(node.seguent != null && !trobat){
			if(node.seguent.informacio.getId() == id) {
				actual = node.seguent;
				trobat=true;
			} else {
				node=node.seguent;
			}
		}
		
		if(!trobat) {
			throw new AlbumNoExisteix(id);									//Si no s'ha trobat l'Album per la id llavors l'Album no existeix
		} else {
			actual.anterior.seguent=actual.seguent;							//Actualitzem punters
			if(actual.seguent != null){
				actual.seguent.anterior=actual.anterior;
			}
			if(ultim.informacio.getId() == id) ultim=null;
			actual=null;
			numAlbums--;													//Decrementem el contador d'albums
		}
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getAlbumCodes()
	 */
	public int[] getAlbumCodes() throws AlbumLlistaBuida{
		if(numAlbums == 0) throw new AlbumLlistaBuida();
		
		int[] listId;
		listId=new int[numAlbums];												//Creem un vector del numero d'Albums a la llista
		int i=0;

		NodeListDinamica node = primer; 										//Guardem les Id's dels Albums a la llista
		while(node.seguent!=null) {
			listId[i] = node.seguent.informacio.getId();
			i++;
			node=node.seguent;
		}
		
		return listId;
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getAlbum(int)
	 */
	public Album getAlbum(int id) throws AlbumNoExisteix {
		NodeListDinamica node = primer;
		boolean trobat = false;
		Album al=null;
		
		while(node.seguent!=null && !trobat) {								//Comprovem que l'album existeixi
			if(node.seguent.informacio.getId() == id){
				al=node.seguent.informacio;
				trobat = true;
			} else {
				node=node.seguent;
			}
		}
		if(!trobat){														//Si existeix el retornem
			throw new AlbumNoExisteix(id);									//Sino llancem la excepció
		} else {
			return al;
		}
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getLast()
	 */
	public Album getLast() throws AlbumNoExisteix{
		Album al=null;
		if(ultim !=null) al = ultim.informacio;						//Si existeix el retornem
		else throw new AlbumNoExisteix();				//Si no existeix llancem excepcio
		return al;
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getAlbumsAuthor(java.lang.String)
	 */
	public Album[] getAlbumsAuthor(String autor) throws AlbumVectorBuit{
		Album[] llista;
		int i=0;
		
		NodeListDinamica node = primer; 							//Cerca el nombre d'Albums que concorden amb l'autor
		while(node.seguent!=null) {
			if(node.seguent.informacio.getAutor().equals(autor)){
				i++;
			}
			node=node.seguent;
		}
		if(i == 0) throw new AlbumVectorBuit();
		
		llista=new Album[i];
		i=0;
		node = primer; 												//Guarda els albums que coincideixen a la llista
		while(node.seguent!=null) {
			if(node.seguent.informacio.getAutor().equals(autor)){
				llista[i]=node.seguent.informacio;
				i++;
			}
			node=node.seguent;
		}
		
		return llista;
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getAlbumsYear(int)
	 */
	public Album[] getAlbumsYear(int any) throws AlbumVectorBuit{
		Album[] llista;
		int i=0;
		
		NodeListDinamica node = primer; 					//Cerca el nombre d'Albums que concorden amb l'any
		while(node.seguent!=null) {
			if(node.seguent.informacio.getAny() == any){
				i++;
			}
			node=node.seguent;
		}
		
		if(i == 0) throw new AlbumVectorBuit();
		
		llista=new Album[i];
		i=0;
		node = primer; 										//Guarda els albums que coincideixen a la llista
		while(node.seguent!=null) {
			if(node.seguent.informacio.getAny() == any){
				llista[i]=node.seguent.informacio;
				i++;
			}
			node=node.seguent;
		}
		
		return llista;
	}

	
	/* (non-Javadoc)
	 * @see interfaces.AlbumListInterface#getNumElements()
	 */
	public int getNumElements() { //Retorna el numero d'albums que hi ha a la llista
		return numAlbums;
	}

}
