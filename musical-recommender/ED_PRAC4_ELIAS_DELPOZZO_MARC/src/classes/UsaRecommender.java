package classes;

import interfaces.AlbumListInterface;

import java.io.*;
import java.util.Vector;

import javax.xml.parsers.*;

import org.apache.commons.cli.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import excepcions.*;

/**
 * La Classe UsaAlbumList.
 */
public class UsaRecommender {
	//Creem l'objecte in per llegir de in
	/** The in. */
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	        
	/**
	 * Crea album.
	 *
	 * @return Referencia de tipus AlbumListInterface
	 * @throws ParseException the parse exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 */
	public static Recommender creaRecommender() throws ParseException, IOException, ParserConfigurationException, SAXException {
        Album album=null;													//Album=Album per a afegir a la rec d'Albums
        Recommender rec=null;										//AlbumLibrary=Llibreria d'Albums
    	Track tr = null;													//tr=Track per a afegir a l'album
    	int max=0;
    	boolean errorLim=false;
    	
    	System.out.println("Indica el limit d'artistes a llegir: ");				//Preguntem el nombre máxim d'Albums a llegir
        try{																//Controlem errors
        	max = Integer.parseInt(in.readLine());
        } catch(NumberFormatException e) {
        	errorLim=true;
        }
        
        while(max <= 0 || errorLim){										//Controlem errors
        	System.out.println("Intrdueix un numero!!");
        try{
        	max = Integer.parseInt(in.readLine());
        	errorLim=false;
        } catch(NumberFormatException e) {
        	errorLim=true;
        }
    	}
        
    	rec = new Recommender(max);												//Creem la rec d'Albums

        String fileName = "xml/practica2.xml";								//Llegim el fitxer XML amb tots els seus atributs
		/**
		 * Read XML file
		 */ 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File file = new File(fileName);
		Document document = builder.parse(file);
		
		// Return ed element of XML file.
		Element ed = document.getDocumentElement();
		
		// Return lecturer elements list
		NodeList AlbumList = ed.getElementsByTagName("Album");
		
		if(AlbumList != null && AlbumList.getLength() > 0) {
			int i=0;
			while(i<AlbumList.getLength()){
			//Get a lecturer
			Element alb = (Element) AlbumList.item(i);
			
			String autor = alb.getAttribute("Artist"); 
			String genere = alb.getAttribute("Genre");
			String id_aux = alb.getAttribute("Id"); 
			int id=Integer.parseInt(id_aux); 
			String titol = alb.getAttribute("Title");
			String any_aux = alb.getAttribute("Year");
			int any=Integer.parseInt(any_aux);
			
			//Creem l'album
			album = new Album(id,titol,autor,genere,any,50);

			//Afegim els tracks!!
			NodeList trackList = alb.getElementsByTagName("Track");
			if(trackList != null && trackList.getLength()>0){
				for(int j=0;j<trackList.getLength();j++){
					Element track = (Element)trackList.item(j);
					String bitrate = track.getAttribute("Bitrate");
					int bitrate2=Integer.parseInt(bitrate);
					String trackId = track.getAttribute("Id");
					int trackId2=Integer.parseInt(trackId);
					String duracio = track.getAttribute("Length");
					int duracio2=Integer.parseInt(duracio);
					String numero = track.getAttribute("Number");
					int numero2=Integer.parseInt(numero);
					String reproduit = track.getAttribute("Played");
					boolean reproduit2 = Boolean.parseBoolean(reproduit);
					String titolTrack = track.getAttribute("Title");
					tr=new Track(trackId2, numero2, titolTrack, duracio2, bitrate2, reproduit2, autor);
					try{
						album.addTrack(tr);																//Afegim els tracks a la rec
					} catch(TrackJaExisteix e){
						System.out.println(e.toString());
					} catch(TrackLlistaPlena e){
						System.out.println(e.toString());
						break;
					}
				}
			}
			try{
				rec.addAlbum(album); 																//Afegim els albums a la rec
			} catch(AlbumJaExisteix e){
				System.out.println(e.toString());
			} catch(AlbumLlistaPlena e){
				System.out.println(e.toString());
				break;
			} catch (TaulaHashPlena e) {
				System.out.println(e.toString());
				break;
			}
			i++;
			}
		}
		
		//Llegir l'arxiu practica4.xml
		
		fileName = "xml/practica4.xml";								//Llegim el fitxer XML amb tots els seus atributs
			/**
			 * Read XML file
			 */ 
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		file = new File(fileName);
		document = builder.parse(file);
		
		// Return ed element of XML file.
		ed = document.getDocumentElement();
		
		// Return lecturer elements list
		NodeList RelationList = ed.getElementsByTagName("Relation");
		
		if(RelationList != null && RelationList.getLength() > 0) {
			for(int i=0;i<RelationList.getLength();i++){
				Element relation = (Element) RelationList.item(i);
				
				String autor1 = relation.getAttribute("Artist1"); 
				String autor2 = relation.getAttribute("Artist2");
				String sim = relation.getAttribute("Similarity");
				int valorLink=Integer.parseInt(sim);
				
				try {
					rec.addRelation(autor1, autor2, valorLink);
				} catch (TaulaHashArtistaNoTrobat e) {
					System.out.println(e.toString());
				}
			}
		}
		return rec;																				//Retornem la rec d'Albums ja construida
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ParseException the parse exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws TaulaHashBuida the taula hash buida
	 */
	public static void main(String[] args) throws ParseException, IOException, ParserConfigurationException, SAXException, TaulaHashBuida  { //Programa Principal amb menú
        Recommender rec= null;
        Track tr=null;
        int opcio=0;
        boolean error=false;
        
        rec = creaRecommender();
        
        do{
        System.out.println("1 : Tria canço que vols escoltar.");
        System.out.println("2 : Escolta la seguent canço.");
        System.out.println("3 : Consulta tots els artistes.");
        System.out.println("4 : Consultar tots els Albums d'un artista.");
        System.out.println("5 : Consulta la relacio entre dos artistes.");
        System.out.println("6 : Consulta les relacions d'un artista.");
        System.out.println("7 : Esborra un artista.");
        System.out.println("8 : Esborra relació.");
        System.out.println("9 : Esborra un Album d'un artista.");
        System.out.println("10 : Sortir.");
        // recuperacio del choice de l'usuari
        System.out.println("Introdueix la opcio desitjada:");
        try{
        opcio = Integer.parseInt(in.readLine());
        error = false;
        switch(opcio){
        	case 1: tr=triarCanço(rec);
    		break;
        	case 2: tr=seguentCanço(rec, tr);
        	break;
        	case 3: consultaArtistes(rec);
        	break;
        	case 4: consultaAlbumsArtista(rec);
        	break;
        	case 5: consultaRelacions2Artistes(rec);
        	break;
        	case 6: consultaRelacions(rec);
        	break;
        	case 7: esborraArtista(rec);
        	break;
        	case 8: esborraRelacio(rec);
        	break;
        	case 9: esborraAlbum(rec);
        	break;
        }
        } catch(NumberFormatException e){
        	error = true;
        }
        }while(opcio != 10 || error);
	}

	/**
	 * Consulta relacions2 artistes.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void consultaRelacions2Artistes(Recommender rec) throws IOException {
		System.out.println("\nIndica el primer artista a consultar relacions:\n");
		String autor1=in.readLine();
		 while(autor1.length() == 0 || autor1 == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor1=in.readLine();
		}
		 
		System.out.println("\nIndica el segon artista a consultar relacions:\n");
		String autor2=in.readLine();
		 while(autor2.length() == 0 || autor2 == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor2=in.readLine();
		}
		
		try {
			System.out.println("\nEl grau de similitud entre "+ autor1 +" i "+ autor2 +" es: " + rec.getRelationOfArtists(autor1, autor2) + "\n");
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} catch (LinkNoExisteix e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Esborra album.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void esborraAlbum(Recommender rec) throws IOException {
		int id=0;
		boolean errorId=false;
		
		System.out.println("\nIntrodueix l'artista de l'Album a esborrar:\n");					//Preguntem l'autor
		String autor=in.readLine();
		 while(autor.length() == 0 || autor == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor=in.readLine();
		}
		
		 System.out.println("Indica la ID de l'Album a esborrar: ");							//Preguntem la id de l'Album a esborrar
	        try{
	        	id = Integer.parseInt(in.readLine());
	        } catch(NumberFormatException e) {
	        	errorId=true;
	        }
	        
	        while(errorId){																		//Controlem errors
	        	System.out.println("Intrdueix un numero!!\n");
	        try{
	        	id = Integer.parseInt(in.readLine());
	        	errorId=false;
	        } catch(NumberFormatException e) {
	        	errorId=true;
	        }
	    	}
	        
	      try {
	    	rec.removeAlbum(autor,id);															//Esborrem l'Album
			System.out.println("\nAlbum esborrat correctament!!\n");
		} catch (AlbumNoExisteix e) {
			System.out.println(e.toString());
	    } catch (AlbumLlistaBuida e) {
	    	System.out.println(e.toString());
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} catch (TaulaHashPlena e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Esborra relacio.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void esborraRelacio(Recommender rec) throws IOException {
		System.out.println("\nIndica l'autor a esborrar la relació:\n");
		String autor1=in.readLine();
		 while(autor1.length() == 0 || autor1 == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor1=in.readLine();
		}
		 
		System.out.println("\nIndica el segon autor a esborrar la relació:\n");
		String autor2=in.readLine();
		 while(autor2.length() == 0 || autor2 == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor2=in.readLine();
		}
		
		try {
			rec.removeRelation(autor1, autor2);
			System.out.println("\nRelació esborrada correctament!!\n");
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} catch (LinkNoExisteix e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Esborra artista.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void esborraArtista(Recommender rec) throws IOException {
		System.out.println("\nIntrodueix l'artista a esborrar:\n");					//Preguntem l'autor
		String autor=in.readLine();
		 while(autor.length() == 0 || autor == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor=in.readLine();
		}
		
		try {
			rec.removeArtist(autor);
			System.out.print("\nArtista esborrat correctament.\n\n");
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Consulta relacions.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void consultaRelacions(Recommender rec) throws IOException {
		System.out.println("\nIndica l'artista a consultar relacions:\n");
		String autor=in.readLine();
		Vector<NodeLink<String, Integer>> llistat=null;
		try {
			llistat = rec.getRelationsOfArtist(autor);
		} catch (TaulaHashArtistaNoTrobat e) {
			e.printStackTrace();
		}
		for(int i=1;i<llistat.size();i++){
			String clau=llistat.elementAt(i).autor;
			int val=llistat.elementAt(i).grau;
			System.out.println(clau+"----->"+val);
			
		}
	}

	/**
	 * Consulta albums artista.
	 *
	 * @param rec the rec
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void consultaAlbumsArtista(Recommender rec) throws IOException {
		System.out.println("\nIntrodueix l'artista a consultar:\n");					//Preguntem l'autor
		String autor=in.readLine();
		while(autor.length() == 0 || autor == null){												//Controlem errors
				System.out.print("\nIntrodueix autor correcte.\n");
				autor=in.readLine();
		}
		
		try {
			AlbumList albumList= rec.getAlbumsOfArtist(autor);
			System.out.println("\nS'ha retornat una llista d'Albums, a continuació apareix el Menú per a treballar amb ella!!\n");
			consultaIDs(albumList);
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} 
	}

	/**
	 * Consulta artistes.
	 *
	 * @param rec the rec
	 */
	private static void consultaArtistes(Recommender rec) {
		try {
			Vector<String> artistes=rec.getArtists();
			System.out.println("\nAquests són els artistes que hi ha emmagatzemats a la estructura de dades: \n");
			for(int i=0;i<artistes.size();i++){
				System.out.println(artistes.get(i));
			}
			System.out.println("\n");
		} catch (TaulaHashBuida e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Seguent canço.
	 *
	 * @param rec the rec
	 * @param track the track
	 * @return the track
	 */
	private static Track seguentCanço(Recommender rec, Track track) {
		Track nouTrack=null;

		System.out.println("\nActualment estas reproduint: "+ track.toString() +"\n");
		track.setPlayed(true);
		
		try {
			nouTrack=rec.recommendSong(track);
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} catch (AlbumLlistaBuida e) {
			System.out.println(e.toString());
		} catch (AlbumNoExisteix e) {
			System.out.println(e.toString());
		} catch (TrackNoExisteix e) {
			System.out.println(e.toString());
		}
	
		System.out.println("\n La seguent cançó a reproduir serà:\n");
		System.out.println(nouTrack.toString()+"Autor:"+nouTrack.getArtista()+"\n");
		
		return nouTrack;
	}

	/**
	 * Triar canço.
	 *
	 * @param rec the rec
	 * @return the track
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static Track triarCanço(Recommender rec) throws IOException {
		consultaArtistes(rec);
		Album Album = null;
		Track track = null;
		Track nouTrack = null;
		System.out.println("\nDe la llista anterior quin artista vols escoltar??\n");
		String autor=in.readLine();
		AlbumList albumList = null;
		try {
			albumList = rec.getAlbumsOfArtist(autor);
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		}
		System.out.println("Id de albums:");
		consultaIDs(albumList);
		System.out.println("Selecciona un album:");
		int id=0;
		id=Integer.parseInt(in.readLine());
		try {
			System.out.println(albumList.getAlbum(id).getTitol());
		} catch (AlbumNoExisteix e) {
			System.out.println(e.toString());
		}
		try {
			Album=albumList.getAlbum(id);
		} catch (AlbumNoExisteix e) {
			System.out.println(e.toString());
		}
		System.out.println("Llista de Tracks");
		consultaIDsTrack(Album);
		System.out.println("Selecciona un track:");
		int idtrack=Integer.parseInt(in.readLine());
		try {
			track= Album.getTrack(idtrack);
		} catch (TrackNoExisteix e) {
			System.out.println(e.toString());
		}
		System.out.println("\nEstas reproduint: "+ track.toString() +"\n");
		track.setPlayed(true);
		
		try {
			nouTrack=rec.recommendSong(track);
		} catch (TaulaHashArtistaNoTrobat e) {
			System.out.println(e.toString());
		} catch (AlbumLlistaBuida e) {
			System.out.println(e.toString());
		} catch (AlbumNoExisteix e) {
			System.out.println(e.toString());
		} catch (TrackNoExisteix e) {
			System.out.println(e.toString());
		}
	
		System.out.println("\n La seguent cançó a reproduir serà:\n");
		System.out.println(nouTrack.toString()+"Autor:"+nouTrack.getArtista()+"\n");
		
		return nouTrack;
	}
	
	/**
	 * Consulta i ds track.
	 *
	 * @param album the album
	 */
	public static void consultaIDsTrack(Album album){								//Mostra per pantalla totes les IDs dels tracks
		int[] tracks=album.getTrackCodes();
		System.out.println("Actualment a l'Album hi ha les seguents IDs: \n");
		for(int i=0;i<tracks.length;i++){
			System.out.println(tracks[i]);
		}
		System.out.println("\n");
	}
	
	/**
	 * Consulta i ds.
	 *
	 * @param albumList the album list
	 */
	public static void consultaIDs(AlbumListInterface albumList){						//Rep un vector amb les IDs i les mostra per pantalla
		int[] albums=null;
		try {
			albums = albumList.getAlbumCodes();
			System.out.println("Actualment a l'Album hi ha les seguents IDs: \n");
			for(int i=0;i<albums.length;i++){
				System.out.println(albums[i]);
			}
			System.out.println("\n");
		} catch (AlbumLlistaBuida e) {
			System.out.println(e.toString());
		}
	}
}
