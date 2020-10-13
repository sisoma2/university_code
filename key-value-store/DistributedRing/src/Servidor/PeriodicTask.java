package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class PeriodicTask extends TimerTask{
	private HashMap<String, Valor> tHash;
	private ArrayList<Boolean> llistaAlive;
	
	public PeriodicTask(HashMap<String, Valor> tHash, ArrayList<Boolean> llistaAlive){
		super();
		this.tHash=tHash;
		this.llistaAlive=llistaAlive;
	}

	@Override
	public void run() {
		try {
			String[] llistaServidors = Naming.list("rmi://localhost:1099");
			for(String serv : llistaServidors){
				Manager m = (Manager) Naming.lookup(serv);
				try{
					if(!llistaAlive.get(m.getId())) {
						m.alive();
						m.antiEntropy(tHash);
						llistaAlive.set(m.getId(),true);
					} else {
						m.alive();
						llistaAlive.set(m.getId(),true);
					}
				} catch(RemoteException e){
					llistaAlive.set(m.getId(),false);
				}
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
