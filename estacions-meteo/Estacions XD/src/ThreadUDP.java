import java.io.IOException;
import java.net.*;
import java.util.StringTokenizer;

public class ThreadUDP extends Thread {

	public DatagramSocket socket;
	public LlistaClients llista;

	public ThreadUDP(DatagramSocket socket, LlistaClients llista){
		this.socket = socket;
		this.llista=llista;
	}

	public void run(){

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024]; 
		boolean error=false;

		while(true) { 
			// Create space for received datagram
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// Receive datagram
			try{  
				socket.receive(receivePacket);
			} catch(SocketTimeoutException e){
				System.out.println("Timeout!! \n");
			} catch (IOException e) {
				error=true;
			}
			String sentence = new String(receivePacket.getData()).trim(); 
			String nom=null;
			try {
				nom=parseParametres(sentence, llista);
			} catch (NumberFormatException e) {
				error=true;
			} catch (UnknownHostException e) {
				error=true;
			}

			if(!error && nom != null){
				InetAddress IPAddress = receivePacket.getAddress(); 

				int port = receivePacket.getPort();  

				String response = "ACK:"+nom;

				// put sentence bytes into a "buffer"
				sendData = response.getBytes(); 

				// Create UDP datagram to send to client
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

				try {
					socket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			} else {
				InetAddress IPAddress = receivePacket.getAddress(); 

				int port = receivePacket.getPort();  

				String response = "Error: Paquet erroni!! Torna a enviar";

				// put sentence bytes into a "buffer"
				sendData = response.getBytes(); 

				// Create UDP datagram to send to client
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

				try {
					socket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
				} 
				System.out.println("Paquet erroni!! Torna a enviar \n");
			}
			receiveData = new byte[1024]; 
			sendData = new byte[1024]; 
		}
	} 

	public String parseParametres(String parse, LlistaClients llista) throws NumberFormatException, UnknownHostException{

		String item1="", item2="", item3="", item4="";

		StringTokenizer st = new StringTokenizer(parse,":");
		if (st.hasMoreTokens()) 	item1= st.nextToken();
		if (st.hasMoreTokens()) 	item2= st.nextToken();
		if (st.hasMoreTokens()) 	item3= st.nextToken();
		if (st.hasMoreTokens()) 	item4= st.nextToken();

		if(item1.equals("REGISTER")){
			if (item4.isEmpty()){
				llista.afegirServer(InetAddress.getByName(item2), Integer.parseInt(item3));
				return "senseNom";
			} else {
				llista.afegirServer(item4, InetAddress.getByName(item2), Integer.parseInt(item3));
				return item4;
			}
		} else {
			return null;
		}

	}
}
