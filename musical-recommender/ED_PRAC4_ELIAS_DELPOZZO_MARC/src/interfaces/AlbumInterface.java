package interfaces;

import classes.Track;
import excepcions.*;

public interface AlbumInterface {
	void addTrack(Track tr) throws TrackJaExisteix, TrackLlistaPlena;
	int[] getTrackCodes();
	Track getTrack(int id) throws TrackNoExisteix;
	void removeTrack(int id) throws TrackNoExisteix;
	int getId();
	String getTitol();
	String getAutor();
	String getGenere();
	int getAny();
	int getMax();
	int getNumTracks();
}
