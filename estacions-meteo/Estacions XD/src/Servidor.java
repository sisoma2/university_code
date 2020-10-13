import java.io.*; 
import java.net.*; 

public class Servidor {
	public static void main(String args[]) throws IOException { 
		 ServerSocket serverSocketTCP = new ServerSocket(6789);
		 DatagramSocket serverSocketUDP = new DatagramSocket(9880);
		 LlistaClients llista = new LlistaClients();
	
		 new ThreadUDP(serverSocketUDP, llista).start();
		 while(true){
			 	Socket socket=serverSocketTCP.accept();
			 	new ThreadTCP(socket, llista).start();
		 }
	 } 
}
