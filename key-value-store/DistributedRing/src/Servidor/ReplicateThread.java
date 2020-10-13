package Servidor;
import java.rmi.RemoteException;


public class ReplicateThread extends Thread{
		private Manager server;
		private String k;
		private Valor v;
		
		public ReplicateThread(Manager server, String k, Valor v){
			this.server = server;
			this.k=k;
			this.v=v;
		}
		
		public void run(){
			try {
				server.replicate(k,v);
				
			} catch (RemoteException e) {
				System.out.println(e.toString());
			}
		}
}
