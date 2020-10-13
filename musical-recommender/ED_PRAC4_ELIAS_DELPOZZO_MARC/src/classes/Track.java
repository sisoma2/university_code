package classes;

/**
 * The Class Track.
 */
public class Track {
	
	/** The id. */
	private int id;
	
	/** The ordre. */
	private int ordre;
	
	/** The titol. */
	private String titol;
	
	/** The duracio. */
	private int duracio;
	
	/** The bitrate. */
	private int bitrate;
	
	/** The reproduit. */
	private boolean reproduit;
	
	/** The artista. */
	private String artista;
	
	/**
	 * Instantiates a new track.
	 *
	 * @param id the id
	 * @param ordre the ordre
	 * @param titol the titol
	 * @param duracio the duracio
	 * @param bitrate the bitrate
	 * @param reproduit the reproduit
	 * @param artista the artista
	 */
	public Track(int id, int ordre, String titol, int duracio, int bitrate, boolean reproduit, String artista){ 	//Constructor de la classe
		this.id = id;
		this.ordre = ordre;
		this.titol = titol;
		this.bitrate=bitrate;
		this.duracio = duracio;
		this.reproduit = reproduit;
		this.artista=artista;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber(){
		return ordre;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle(){
		return titol;
	}
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public String getLength(){					//Retorna el temps en el format mm:ss
		String seg,temps;
		
		if(duracio%60<10) seg="0"+duracio%60;
		else seg=""+duracio%60;
		temps=duracio/60+":"+seg;
		return temps;
	}
	
	/**
	 * Gets the bitrate.
	 *
	 * @return the bitrate
	 */
	public int getBitrate(){
		return bitrate;
	}
	
	/**
	 * Gets the played.
	 *
	 * @return the played
	 */
	public boolean getPlayed(){
		return reproduit;
	}
	
	/**
	 * Sets the played.
	 *
	 * @param b the new played
	 */
	public void setPlayed(boolean b){
		reproduit=b;
	}
	
	/**
	 * Gets the artista.
	 *
	 * @return the artista
	 */
	public String getArtista(){
		return artista;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String informacio;
		if(reproduit){
			informacio="La canço \""+titol+"\" amb id "+id+" numero "+ordre+" a l'album, amb bitrate "+bitrate+" amb una duracio de "+getLength()+" i ja ha estat reproduida\n";
		} else {
			informacio="La canço \""+titol+"\" amb id "+id+" numero "+ordre+" a l'album, amb bitrate "+bitrate+" amb una duracio de "+getLength()+" i encara no s'ha reproduit.\n";
		}
		return informacio;
	}
}
