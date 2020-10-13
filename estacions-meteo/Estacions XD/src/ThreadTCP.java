import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ThreadTCP extends Thread{

	public Socket socket;
	public LlistaClients llista;

	public ThreadTCP(Socket socket, LlistaClients llista){
		this.socket = socket;
		this.llista=llista;
	}

	public void run (){
		try{
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String clientSentence = inFromClient.readLine().trim();

			if(clientSentence.equals("llista")){
				DataOutputStream  outToClient = new DataOutputStream(socket.getOutputStream()); 
				String respuesta = new String(); 
				for(Registre r : llista){
					respuesta=respuesta+"-"+r.toString();
				}
				outToClient.writeBytes(respuesta+"\n");
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
