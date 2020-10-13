package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LlistaActivitats {
	private List<Activitat> activitats = new ArrayList<Activitat>();
	
	public LlistaActivitats(String fit) throws IOException{

		int capacitatSala;
        int[] dies;
        double preu;
        String nomActivitat, publicDestinatari,HoraIniciActivitat, lloc;
        String[] franjaHorari;
        
		BufferedReader arxiu;
		 arxiu=new BufferedReader(new FileReader(fit+".txt")); 
		 String linia=arxiu.readLine();
		 
		 while(linia != null){
			 StringTokenizer st = new StringTokenizer(linia,"#");
			 String tipusActivitat = st.nextToken();
			 
			 StringTokenizer diess = new StringTokenizer(st.nextToken(),",");
			 int numDies = diess.countTokens();
			 dies = new int [numDies];
			 int i = 0;
			 while (diess.hasMoreTokens()) {
				dies[i]=Integer.parseInt(diess.nextToken());
				i++;
		  	  }
			 
			 nomActivitat = st.nextToken();
			 publicDestinatari = st.nextToken();
			 
			 if(tipusActivitat.equalsIgnoreCase("concert")){
				 lloc = st.nextToken();
				 capacitatSala = Integer.parseInt(st.nextToken());
				 HoraIniciActivitat = st.nextToken();
				 Concert c = new Concert(tipusActivitat,dies,nomActivitat,publicDestinatari,lloc, capacitatSala,HoraIniciActivitat);
				 activitats.add(c);
			 }
			 else if(tipusActivitat.equalsIgnoreCase("teatre")){
				capacitatSala = Integer.parseInt(st.nextToken());
				preu = Double.parseDouble(st.nextToken());
				HoraIniciActivitat = st.nextToken();
				Teatre t = new Teatre(tipusActivitat, dies,nomActivitat,publicDestinatari, capacitatSala, HoraIniciActivitat, preu);
				activitats.add(t);
			 }
			 else if(tipusActivitat.equalsIgnoreCase("exposicio")){
				 lloc = st.nextToken();
				 franjaHorari = new String[2];
				 franjaHorari[0] = st.nextToken();
				 franjaHorari[1] = st.nextToken();
				 Exposicio ex = new Exposicio(tipusActivitat, dies,nomActivitat,lloc,publicDestinatari,franjaHorari);
				 activitats.add(ex);
			 } 
			 else {
            	System.out.print("\n Error tipus d'activitat desconegut!! \n");
            }
			 linia=arxiu.readLine();
		 }
		 //Tanquem l'arxiu de text
		 arxiu.close();
	}
	
	public List<Activitat> consultaDia(int dt){
		List<Activitat> v = new ArrayList<Activitat>();
		for(int i = 0; i < activitats.size(); i++){ // recorrer les dades de cada activitat
          Activitat a = activitats.get(i);
          for(int k = 0; k < a.getDies().length; k++){
             if(dt == a.getDies()[k]){
      	   		 v.add(a);  
             } 
          }
      }
		return v;
	}
	
	public List<Activitat> consultaNom(String nomActivitat){
		List<Activitat> v = new ArrayList<Activitat>();
		for(int i = 0; i < activitats.size(); i++){
        	Activitat a = activitats.get(i);
            if(a.getTipusActivitat().equalsIgnoreCase(nomActivitat)){
                	v.add(a);
            }
		}
		return v;
	}
	
	public List<Activitat> consultaDataNom(int dt, String nomActivitat){
		List<Activitat> v = new ArrayList<Activitat>();
		 for(int i = 0; i < activitats.size(); i++){
			Activitat a = activitats.get(i);
           for(int k = 0; k < a.getDies().length; k++){
              if(dt == a.getDies()[k] && a.getTipusActivitat().equalsIgnoreCase(nomActivitat)){
            	  v.add(a);
              }
           }
		 }
		return v;
	}
	
	public int compraEntrades(int dia, String nomActivitat, int numEntrades){
		for(int i = 0; i < activitats.size(); i++){
			Activitat a = activitats.get(i);
           for(int k = 0; k < a.getDies().length; k++){
              if(dia == a.getDies()[k] && a.getNomActivitat().equalsIgnoreCase(nomActivitat)){
            	  if(a.getTipusActivitat().equalsIgnoreCase("teatre")){
            		  Teatre teatre = (Teatre)a;
            		  if((teatre.getCapacitatSala()-numEntrades) >= 0){
            			  teatre.setCapacitatSala(teatre.getCapacitatSala()-numEntrades);
            			  return 1;
            		  } else {
            			  return 2;
            		  }
            	  } else if (a.getTipusActivitat().equalsIgnoreCase("concert")){
            		  Concert oc = (Concert)a;
            		  if((oc.getCapacitatSala()-numEntrades) >= 0){
            			  oc.setCapacitatSala(oc.getCapacitatSala()-numEntrades);
            			  return 1;
            		  } else {
            			  return 2;
            		  }
            	  } else {
            		  return 0;
            	  }
              }
           }
		 }
		return 0;
	}
}
