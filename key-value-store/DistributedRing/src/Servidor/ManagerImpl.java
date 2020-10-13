package Servidor;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import Excepcions.*;

public class ManagerImpl extends UnicastRemoteObject implements Manager {
	private static final long serialVersionUID = 1L;
	private static HashMap<String,Valor> tHash = new HashMap<String,Valor>();
	private static ArrayList<Manager> preferenceList = new ArrayList<Manager>();
	private static Timer timer;
	private static TimerTask timerTask;
	private static int N;
	private static ArrayList<Boolean> llistaAlive = new ArrayList<Boolean>(N);
	private static int id;
	private static boolean servidorCaigut=false;
	
	public ManagerImpl(int n, int id2) throws RemoteException {
		super();
		N=n;
		id=id2;
	}
	
	public int getId() throws RemoteException{
		if(servidorCaigut) throw new RemoteException();
		return id;
	}
	
	@Override
	public void replicate(String k, Valor v) throws RemoteException {
		if(servidorCaigut) throw new RemoteException();
		tHash.put(k, v);
	}
	
	public String put(String k, String v) throws QuorumLectura, QuorumEscriptura, RemoteException {
		if(servidorCaigut) throw new RemoteException();
		ArrayList<Manager> quorum = new ArrayList<Manager>();
		ArrayList<Integer> versions = new ArrayList<Integer>();
		String valorAntic=null;
		int versio,max=0;
		Valor val;
		
		for(Manager m: preferenceList){
			try {
				if(llistaAlive.get(m.getId())) quorum.add(m);
				versio = m.getVersion(k);
				if(versio != 0) versions.add(versio);
			} catch (RemoteException e) {}
		}
		
		if(tHash.containsKey(k)){
			
			for(int i=0;i<versions.size();i++){
				if(versions.get(i)>=max){
					versio=versions.get(i);
					max=versions.get(i);
				}
			}
			
			valorAntic=this.get(k);
			val=new Valor(v,max+1);
			
		} else {
			val=new Valor(v,0);		
		}
		
		switch(quorum.size()){
		case 0:
			throw new QuorumLectura();
		case 1:
			throw new QuorumEscriptura(valorAntic);
		case 2:
			tHash.put(k, val);
			try {
				quorum.get(0).replicate(k, val);
				quorum.get(1).replicate(k, val);
			} catch (RemoteException e) {}
			break;
		case 3:
			tHash.put(k, val);
			try {
				quorum.get(0).replicate(k, val);
				quorum.get(1).replicate(k, val);
				ReplicateThread t1 = new ReplicateThread(quorum.get(2), k, val);
				t1.start();
			} catch (RemoteException e) {}
			break;
		}
		
		return valorAntic;
	}
	
	public int getVersion(String k) throws RemoteException{
		if(servidorCaigut) throw new RemoteException();
		if(tHash.containsKey(k)){
			return tHash.get(k).getVersio();
		} else return 0;
	}
	
	public Valor getValor(String k) throws RemoteException{
		if(servidorCaigut) throw new RemoteException();
		if(tHash.containsKey(k)){
			return tHash.get(k);
		} else {
			return null;
		}
	}
	
	public String get(String k) throws QuorumLectura, RemoteException{
		if(servidorCaigut) throw new RemoteException();
		
		ArrayList<Manager> quorum = new ArrayList<Manager>();
		ArrayList<Valor> valors = new ArrayList<Valor>();
		String valorMax;
		Valor valor,valorCoord;
		int max=0;
		
		for(Manager m: preferenceList){
			try {
				if(llistaAlive.get(m.getId())) quorum.add(m);
				valor = m.getValor(k);
				if(valor != null) valors.add(valor);
			} catch (RemoteException e) {}
		}
		
		if(quorum.size() < 1){
			throw new QuorumLectura();
		}
		
		for(int i=0;i<valors.size();i++){
			if(valors.get(i).getVersio()>max){
				max=valors.get(i).getVersio();
				valorMax=valors.get(i).getV();
			}
		}
		
		if(tHash.containsKey(k)){
			valorCoord=tHash.get(k);
			valorMax=valorCoord.getV();
		} else valorMax="0";
		
		return valorMax;
	}
	
	@Override
	public void alive() throws RemoteException {
		if(servidorCaigut) throw new RemoteException();
	}
	
	public static int adler(String clau){
		long max=(long) (Math.pow(2, 32))/N;
		int i=0;
		byte bytes[] = clau.getBytes(); 
		Checksum Adler = new Adler32();
		Adler.update(bytes, 0, bytes.length);
		long hash = Adler.getValue();
		
		while(hash>(i*max) && i<5) i++;
		
		if(i==5) i=0;
		
		return i;
	}
	
	@Override
	public void antiEntropy(HashMap<String, Valor> tHashAux) throws RemoteException {
		if(servidorCaigut) throw new RemoteException();
		
		int servidor;
		
		for (String key : tHashAux.keySet()) {
			servidor=adler(key);
			if(servidor == id || (servidor+1)%N == id || (servidor+2)%N == id || (servidor+3)%N == id){
				if(tHash.containsKey(key)){
					if(tHashAux.get(key).getVersio() > tHash.get(key).getVersio()){
						System.out.println("Actualitzem la versio de la clau: " + key + " al servidor " + id);
						tHash.put(key, tHashAux.get(key));
					}
				} else {
					tHash.put(key, tHashAux.get(key));
					System.out.println("Creem entrada de la clau "+key+" que no existia al servidor " + id);
				}
			}
		}
	}
	
	@Override
	public void stopServer() throws RemoteException {
		if(servidorCaigut){
			servidorCaigut=false;
			System.out.println("Despertem al servidor: "+id);
			timerTask = new PeriodicTask(tHash, llistaAlive); //running timer task as daemon thread
		    timer = new Timer(true);
		    timer.scheduleAtFixedRate(timerTask, 0, 3000);
		} else {
			servidorCaigut=true;
			System.out.println("Fem caure al servidor: "+id);
			timerTask.cancel();
		    timer.cancel();
		}
	}
	
	public static void main(String[] args) throws Exception {
		 System.setProperty("java.security.policy", "server.policy");
	     if ( System.getSecurityManager() == null ) {
	            System.setSecurityManager(new RMISecurityManager( ) );
		 }
	     
	     int i;
	     int id = Integer.parseInt( args[0] ); 	// Identifies itself
	     String serveraddr = args[1];  // Identifies server (IP or host name)
	     int n = Integer.parseInt( args[2] );  // Number of manager in the ring
	     
		 ManagerImpl manager = new ManagerImpl (n,id);
		 
		 for(i=0;i<5;i++){
		    	llistaAlive.add(true); 
		 }
		 
		 Naming.rebind("rmi://"+serveraddr+"/"+id, manager);
	     
	     // loop needed because initially there's no manager in the ring:
	     Manager neigh = null;
		 System.out.println("Setting neighbors ...");
		 i=1;
	     while(i<4) { 
	    	 try {
	    		 neigh =  (Manager) Naming.lookup("rmi://"+serveraddr+"/"+(id+i)%n);
	    		 preferenceList.add(neigh);
	    		 i++;
		     } catch (Exception ie){}
		  }
	     
	     timerTask = new PeriodicTask(tHash, llistaAlive); //running timer task as daemon thread
	     timer = new Timer(true);
	     timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}  // main
}
