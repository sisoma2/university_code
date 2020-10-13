package classes;

import interfaces.AlbumInterface;
import excepcions.TrackJaExisteix;
import excepcions.TrackLlistaPlena;
import excepcions.TrackNoExisteix;

/**
 * The Class Album.
 */
public class Album implements AlbumInterface{
	
	/** The id. */
	private int id;
	
	/** The titol. */
	private String titol;
	
	/** The autor. */
	private String autor;
	
	/** The genere. */
	private String genere;
	
	/** The any. */
	private int any;
	
	/** The max. */
	private int max;
	
	/** The tracks. */
	private Track[] tracks;
	
	/** The num tracks. */
	private int numTracks;

	/**
	 * Instantiates a new album.
	 *
	 * @param id the id
	 * @param titol the titol
	 * @param autor the autor
	 * @param genere the genere
	 * @param any the any
	 * @param max the max
	 */
	public Album (int id, String titol, String autor, String genere, int any, int max) {			//Constructor de la classe
		this.id=id;
		this.titol = titol;
		this.autor = autor;
		this.genere = genere;
		this.any=any;
		this.max=max;
		tracks=new Track[max];
		numTracks = 0;
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getId()
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getTitol()
	 */
	public String getTitol() {
		return titol;
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getAutor()
	 */
	public String getAutor() {
		return autor;
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getGenere()
	 */
	public String getGenere() {
		return genere;
	}

	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getAny()
	 */
	public int getAny() {
		return any;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getMax()
	 */
	public int getMax() {
		return max;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getNumTracks()
	 */
	public int getNumTracks(){
		return numTracks;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#addTrack(classes.Track)
	 */
	public void addTrack(Track tr)throws TrackJaExisteix, TrackLlistaPlena{
		int i=0;
		boolean trobat = false;
		
		if(numTracks>=max){
			throw new TrackLlistaPlena(numTracks,id,titol);
		}
		
		while(i < numTracks && !trobat){											//Comprova que no estigui ja a la llista
			if(tracks[i].getID() == id){
				trobat = true;
			} else {
				i++;
			}
		}
	
		if(!trobat){																//Si no existeix el guardem a la llista
			tracks[numTracks] = tr;
			numTracks++;
			ordenarOrdre();
		} else {																	//Si ja esta a la llista llancem excepcio
			throw new TrackJaExisteix(tr.getID(),this.id,titol);
		}
	}
	
	/**
	 * Ordenar ordre.
	 */
	public void ordenarOrdre(){													//Funció d'ordenar els tracks per l'ordre de la cançó
		int i,j;
		boolean ord;
		Track aux;

		i=1;
		ord=false;
		while (!ord) {
		  ord=true;
		  for (j=0; j<numTracks-i; j++) {
		    if (tracks[j].getNumber()>tracks[j+1].getNumber()) {
		    	ord=false;
		    	aux = tracks[j];
			tracks[j]=tracks[j+1];
			tracks[j+1]=aux;
		    }
		  }
		  i++;
		}
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getTrackCodes()
	 */
	public int[] getTrackCodes() {
		int[] listId;
		listId=new int[numTracks];												//Creem un vector del numero d'Albums
		int i;
		
		for(i=0; i<numTracks; i++){												//Guardem les IDs de tots els albums
			listId[i] = tracks[i].getID();
		}
		return listId;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#getTrack(int)
	 */
	public Track getTrack(int id) throws TrackNoExisteix {
		int i=0;
		boolean trobat=false;
		Track tr=null;
		
		while(i < numTracks && !trobat){										//Comprova que no estigui ja a la llista
			if(tracks[i].getID() == id){
				trobat = true;
			} else {
				i++;
			}
		}
		
		if(trobat){																//Si existeix retornem el track
			tr=tracks[i];
		} else {																//Si no existeix llancem excepcio
			throw new TrackNoExisteix(id,this.id,titol);
		}
		
		return tr;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.AlbumInterface#removeTrack(int)
	 */
	public void removeTrack(int id) throws TrackNoExisteix{
		int i=0,j;
		boolean trobat = false;
		
		while(i < numTracks && !trobat){										//Comprova que no estigui ja a la llista
			if(tracks[i].getID() == id){
				trobat = true;
			} else {
				i++;
			}
		}
		
		if(trobat){																//Si existeix el borra
			for(j=i+1;j<numTracks;j++){
				tracks[i]=tracks[j];
			}
			if(j==numTracks-1){
				tracks[i] = null;
			}
			numTracks--;
		} else {
			throw new TrackNoExisteix(id,this.id,titol);										//Si no existeix llencem excepcio
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String informacio = "L'album amb nom "+titol+" i id "+id+" té com a autor a "+autor+" del genere "+genere+" creat a l'any "+any+" amb "+numTracks+" cançons.\nTracks inclosos a l'Album: \n";
		for(int i=0;i<numTracks;i++){
    		informacio=informacio+"\t "+tracks[i].toString();
    	}
		return informacio;
	}
}
