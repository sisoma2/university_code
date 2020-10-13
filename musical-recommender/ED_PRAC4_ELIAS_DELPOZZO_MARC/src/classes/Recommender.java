package classes;

import java.util.Vector;

import excepcions.*;

/**
 * The Class Recommender.
 */
public class Recommender {
	
	/** The graf. */
	private Graf<String,AlbumList,Integer> graf;
	
	/** The num elements. */
	private int max, numElements;
	
	/**
	 * Instantiates a new recommender.
	 *
	 * @param maxim the maxim
	 */
	public Recommender(int maxim){
		max=maxim;
		numElements=0;
		graf=new Graf<String,AlbumList,Integer>(maxim);
	}
	
	/**
	 * Adds the album.
	 *
	 * @param album the album
	 * @throws AlbumJaExisteix the album ja existeix
	 * @throws AlbumLlistaPlena the album llista plena
	 * @throws TaulaHashPlena the taula hash plena
	 */
	public void addAlbum(Album album)throws AlbumJaExisteix, AlbumLlistaPlena, TaulaHashPlena{
		String autor = album.getAutor();
		
		if(numElements>=max) throw new TaulaHashPlena(numElements);
		
		try {
			AlbumList llistaAlbums = graf.getVertex(autor);
			llistaAlbums.addAlbum(album);
			graf.addVertex(autor, llistaAlbums);
		} catch (TaulaHashArtistaNoTrobat e) {
			AlbumList llistaAlbums = new AlbumList(100);
			llistaAlbums.addAlbum(album);
			graf.addVertex(autor, llistaAlbums);
		}
		numElements++;
	}
	
	/**
	 * Adds the relation.
	 *
	 * @param autor1 the autor1
	 * @param autor2 the autor2
	 * @param valorLink the valor link
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void addRelation(String autor1, String autor2, int valorLink) throws TaulaHashArtistaNoTrobat{
		graf.addLink(autor1, autor2, valorLink);
	}
	
	/**
	 * Gets the artists.
	 *
	 * @return the artists
	 * @throws TaulaHashBuida the taula hash buida
	 */
	public Vector<String> getArtists() throws TaulaHashBuida{
		return graf.getAllVertexs();
	}
	
	/**
	 * Gets the albums of artist.
	 *
	 * @param autor the autor
	 * @return the albums of artist
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public AlbumList getAlbumsOfArtist(String autor) throws TaulaHashArtistaNoTrobat{
		return graf.getVertex(autor);
	}
	
	/**
	 * Gets the relations of artist.
	 *
	 * @param autor the autor
	 * @return the relations of artist
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public Vector<NodeLink<String,Integer>> getRelationsOfArtist(String autor) throws TaulaHashArtistaNoTrobat{
		return graf.getAllLinks(autor);
	}
	
	/**
	 * Gets the relation of artists.
	 *
	 * @param autor1 the autor1
	 * @param autor2 the autor2
	 * @return the relation of artists
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 * @throws LinkNoExisteix the link no existeix
	 */
	public int getRelationOfArtists(String autor1, String autor2) throws TaulaHashArtistaNoTrobat, LinkNoExisteix{
		return graf.getLink(autor1, autor2);
	}
	
	/**
	 * Removes the artist.
	 *
	 * @param autor the autor
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void removeArtist(String autor) throws TaulaHashArtistaNoTrobat{
		graf.removeVertex(autor);
	}
	
	/**
	 * Removes the album.
	 *
	 * @param autor the autor
	 * @param id the id
	 * @throws TaulaHashPlena the taula hash plena
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 * @throws AlbumNoExisteix the album no existeix
	 * @throws AlbumLlistaBuida the album llista buida
	 */
	public void removeAlbum(String autor, int id) throws TaulaHashPlena, TaulaHashArtistaNoTrobat, AlbumNoExisteix, AlbumLlistaBuida{
		AlbumList llistaAlbums = graf.getVertex(autor);
		llistaAlbums.removeAlbum(id);
		graf.addVertex(autor, llistaAlbums);
	}
	
	/**
	 * Removes the relation.
	 *
	 * @param autor1 the autor1
	 * @param autor2 the autor2
	 * @throws LinkNoExisteix the link no existeix
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 */
	public void removeRelation(String autor1, String autor2) throws LinkNoExisteix, TaulaHashArtistaNoTrobat{
		graf.removeLink(autor1, autor2);
	}
	
	/**
	 * Recommend song.
	 *
	 * @param tr the tr
	 * @return the track
	 * @throws TaulaHashArtistaNoTrobat the taula hash artista no trobat
	 * @throws AlbumLlistaBuida the album llista buida
	 * @throws AlbumNoExisteix the album no existeix
	 * @throws TrackNoExisteix the track no existeix
	 */
	public Track recommendSong(Track tr) throws TaulaHashArtistaNoTrobat, AlbumLlistaBuida, AlbumNoExisteix, TrackNoExisteix{
		String autor=tr.getArtista();
		int[] albumCodes=null;
		int[] trackCodes=null;
		String a1=null,a2=null;
		AlbumList albumList;
		Album album;
		Track track=null;
		int val1=0,val2=0;
		boolean trobat=false;
		Vector<NodeLink<String, Integer>> llistat = graf.getAllLinks(autor);
		if(llistat == null){
			albumList=graf.getVertex(autor);
		} else {
			for(int i=0;i<llistat.size();i++){
				a1=llistat.get(i).autor;
				val1=llistat.get(i).grau;
				if(val1>val2){
					val2=val1;
					a2=a1;
				}
			}
			
			albumList=graf.getVertex(a2);
		}
		
		albumCodes=albumList.getAlbumCodes();
		int i=0;
		while(i<albumList.getNumElements() && !trobat){
			album=albumList.getAlbum(albumCodes[i]);
			int j=0;
			while(j<album.getNumTracks() && !trobat){
				trackCodes=album.getTrackCodes();
				track=album.getTrack(trackCodes[j]);
				if(track.getPlayed() == false){
					trobat=true;
				} else {
					j++;
				}
			}
			i++;
		}
		return track;
	}
}
