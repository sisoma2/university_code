package interfaces;

import classes.Album;
import excepcions.*;

public interface AlbumListInterface {
	void addAlbum(Album album) throws AlbumJaExisteix, AlbumLlistaPlena;
	void removeAlbum(int id) throws AlbumNoExisteix, AlbumLlistaBuida;
	int[] getAlbumCodes() throws AlbumLlistaBuida;
	Album getAlbum(int id) throws AlbumNoExisteix;
	Album getLast() throws AlbumNoExisteix;
	Album[] getAlbumsAuthor(String autor) throws AlbumVectorBuit;
	Album[] getAlbumsYear(int any) throws AlbumVectorBuit;
	int getNumElements();
}
