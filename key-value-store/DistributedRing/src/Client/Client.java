package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import Excepcions.QuorumEscriptura;
import Excepcions.QuorumLectura;
import Servidor.Manager;

public class Client {
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	
	public static void menu(){
		System.out.println("Que vols fer?");
		System.out.println("p - Afegir.");
		System.out.println("g - Consultar.");
		System.out.println("s - Aturar/Encendre servidor.");
		System.out.println("e - Sortir.");
	}
	
	public static void kill() throws NumberFormatException, IOException{
		System.out.println("Numero del servidor:");
		int serv=Integer.parseInt(inFromUser.readLine());
		Manager m;
		try {
			m = (Manager) Naming.lookup( "rmi://localhost:1099/"+serv );
			m.stopServer();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws IOException {
		String[] servers;
		char op='o';
							
		servers = Naming.list("rmi://localhost:1099");
		N=servers.length;
		
		while(op!='e'){
			menu();
			op=(inFromUser.readLine()).charAt(0);
			
			int op2=op;
			
			switch(op2){
				//get
				case 103:
					consultar();
					break;
					
				//put
				case 112:
					afegir();
					break;
				
				//stop
				case 115:
					kill();
			}
		}
		
		System.out.println("Sortint del programa.");
	}
	
	public static void afegir() throws IOException{
		System.out.println("Introdueix la clau:");
		String clau=inFromUser.readLine();
		
		System.out.println("Introdueix el valor a guardar:");
		String valor=inFromUser.readLine();
		
		long serv=adler(clau);
		System.out.println("Servidor: "+serv);
		
		try {
			Manager m = (Manager) Naming.lookup( "rmi://localhost:1099/"+serv );
			String valorAntic;
			try {
				try {
					valorAntic = m.put(clau,valor);
					System.out.println("El valor antic de la clau es: "+valorAntic);
					System.out.println("S'ha afegit correctament.");
				} catch (RemoteException e){
					System.out.println("El coordinador ha fallat, enviant la peticio al següent servidor.");
					m = (Manager) Naming.lookup( "rmi://localhost:1099/"+((serv+1)%N) );
					valorAntic = m.put(clau,valor);
					System.out.println("El valor antic de la clau es: "+valorAntic);
					System.out.println("S'ha afegit correctament.");
				}
			} catch (QuorumLectura e) {
				System.out.println(e);
			} catch (QuorumEscriptura e) {
				System.out.println(e);
			}
			
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void consultar() throws IOException{
		String valor;
		System.out.println("Introdueix la clau:");
		String clau=inFromUser.readLine();
		
		long serv=adler(clau);
		
		try {
			Manager m = (Manager) Naming.lookup( "rmi://localhost:1099/"+serv );
			try {
				try {
					valor = m.get(clau);
					System.out.println("El valor es: "+valor);
				} catch (RemoteException e){
					System.out.println("El coordinador ha fallat, enviant la peticio al següent servidor.");
					m = (Manager) Naming.lookup( "rmi://localhost:1099/"+((serv+1)%N) );
					valor = m.get(clau);
					System.out.println("El valor es: "+valor);
				}
			} catch (QuorumLectura e) {
				System.out.println(e);
			}

		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
}
