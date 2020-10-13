import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Estacio {
	public static void main(String args[]) throws IOException {

		String sentence, servidor= new String();
		boolean continua=true;
		boolean registrat=false;
		Registre estacioActual = null;
		LlistaClients llistaEstacions = new LlistaClients();
		DatagramSocket socketUDP=new DatagramSocket();
		DatagramPacket sendPacket,receivePacket;
		byte[] sendData;
		byte[] receiveData;
		String response=null,next=new String(), missatge=new String();
		boolean actiu=true;

		while(continua){
			try{
				// teclat
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("***************************************");
				System.out.println("MENU PRINCIPAL \n");
				System.out.println("1- Registrar-se (r)");
				System.out.println("2- Obtenir la llista d'estacions (l)");
				System.out.println("3- Sortir de l'estació (f)");
				System.out.println("*************************************** \n");
				System.out.println("Introdueix quina opció vols fer: \n");
				// llegir del teclat
				sentence = inFromUser.readLine(); 

				if(sentence.equals("r") && !registrat){
					// Create client socket
					DatagramSocket clientSocket = new DatagramSocket(); 
					// cal substituir hostname pel nom del host del vostre company, ex:
					// d602.labdeim.net
					clientSocket.setSoTimeout(200);

					sendData = new byte[1024]; 
					receiveData = new byte[1024]; 

					System.out.println("Introdueix la IP del servidor: \n");
					servidor=inFromUser.readLine();

					int port = socketUDP.getLocalPort();

					System.out.println("Introdueix el nom de la estacio:\n");
					String nom = inFromUser.readLine();
					while(nom == null){
						System.out.println("Introdueix un nom vàlid \n");
						nom = inFromUser.readLine();
					}

					String ip = InetAddress.getLocalHost().getHostAddress();

					sentence="REGISTER:"+ip+":"+port+":"+nom;

					estacioActual = new Registre(nom,InetAddress.getByName(ip),port);

					sendData = sentence.getBytes();

					// Create datagram with data-to-send, length, IP addr, port
					sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(servidor), 9880); 

					// Send datagram to server
					clientSocket.send(sendPacket); 

					receivePacket = new DatagramPacket(receiveData, receiveData.length); 

					try{
						// Read datagram from server
						clientSocket.receive(receivePacket); 

						response = new String(receivePacket.getData()).trim(); 

						System.out.println("FROM SERVER -> " + response + "\n");

						// close from OS point of view 
						clientSocket.close(); 


						registrat=true; 

					} catch (SocketTimeoutException e) {
						System.out.println("Timeout!! \n");
						clientSocket.send(sendPacket);
					}

				} else if(sentence.equals("l") && registrat) {
					obtindreLlista(servidor,llistaEstacions,estacioActual);
				}

				if(registrat)
					obtindreLlista(servidor,llistaEstacions,estacioActual);
				socketUDP.setSoTimeout(500);

				int i=0;
				while(!next.equals("f")){
					sendData = new byte[1024]; 
					receiveData = new byte[1024];
					if(actiu){
						Registre r=llistaEstacions.get(i);
						if(!r.equals(estacioActual)){
							System.out.println("--------------MODE ACTIU--------------");
							System.out.println("Estació a sondejar -> "+r.getNom()+"\n");
							missatge="ALIVE?";
							sendData = missatge.getBytes();
							sendPacket = new DatagramPacket(sendData, sendData.length, r.getIPadd(), r.getPort()); 
							socketUDP.send(sendPacket); 

							receivePacket = new DatagramPacket(receiveData, receiveData.length); 
							try{
								socketUDP.receive(receivePacket); 
								response = new String(receivePacket.getData()).trim(); 

								if(response.equals("YES")){
									r.marcaViu();
									System.out.println(r.getNom()+" -> està viva!!\n");
								} else if (response.equals("ALIVE?")){
									int portAux = receivePacket.getPort();
									InetAddress IPAux = receivePacket.getAddress();
									Registre regAux=new Registre(IPAux,portAux);
									int index = llistaEstacions.indexOf(regAux);
									if(index >= 0){
										regAux = llistaEstacions.get(index);
										regAux.marcaViu();
									} else {
										obtindreLlista(servidor,llistaEstacions,estacioActual);
										index = llistaEstacions.indexOf(regAux);
										regAux = llistaEstacions.get(index);
										regAux.marcaViu();
										System.out.println(regAux.getNom()+" -> està viva!!\n");
									}
									sendData = new byte[1024]; 
									receiveData = new byte[1024];
									missatge="YES";
									sendData = missatge.getBytes();
									sendPacket = new DatagramPacket(sendData, sendData.length, IPAux, portAux); 
									socketUDP.send(sendPacket);

								} else {
									System.out.println("Missatge no identificat!!\n");
								}

								if(i<llistaEstacions.quants()-1){
									i++;
								} else {
									i=0;
								}
							} catch (SocketTimeoutException e) {
								System.out.println("Timeout!! \n");
								r.marcaMort();
								if(i<llistaEstacions.quants()-1){
									i++;
								} else {
									i=0;
								}
							}

							System.out.println("\n Resultats del sondejos: \n");
							System.out.println("***************************************");
							mostraResultats(llistaEstacions,estacioActual);
							System.out.println("*************************************** \n \n");

						} else {
							System.out.println("Estació actual NO sondejem!!\n");
							if(i<llistaEstacions.quants()-1){
								i++;
							} else {
								i=0;
							}
						}

					} else {
						socketUDP.setSoTimeout(0);
						sendData = new byte[1024]; 
						receiveData = new byte[1024];
						receivePacket = new DatagramPacket(receiveData, receiveData.length); 
						socketUDP.receive(receivePacket); 
						response = new String(receivePacket.getData()).trim(); 
						if(response.equals("ALIVE?")){
							int portAux = receivePacket.getPort();
							InetAddress IPAux = receivePacket.getAddress();
							Registre regAux=new Registre(IPAux,portAux);
							int index = llistaEstacions.indexOf(regAux);
							if(index >= 0){
								regAux = llistaEstacions.get(index);
								regAux.marcaViu();
							} else {
								obtindreLlista(servidor,llistaEstacions,estacioActual);
								index = llistaEstacions.indexOf(regAux);
								regAux = llistaEstacions.get(index);
								regAux.marcaViu();
							}
							missatge="YES";
							sendData = missatge.getBytes();
							sendPacket = new DatagramPacket(sendData, sendData.length, IPAux, portAux); 
							socketUDP.send(sendPacket);
							System.out.println("--------------MODE PASIU--------------");
							System.out.println(regAux.getNom()+" -> està viva!!\n");
							System.out.println("\n Resultats del sondejos: \n");
							System.out.println("***************************************");
							mostraResultats(llistaEstacions,estacioActual);
							System.out.println("*************************************** \n \n");
						}
					}


					System.out.println("Introdueix 'n' per continuar, 'p' per cambiar de mode i 'f' per sortir de l'estació... \n");
					next=inFromUser.readLine();

					if (next.equals("f")){
						System.out.println("Sortim de l'estació.....");
						continua=false;
					} else if (next.equals("p")){
						System.out.println("Cambiem de mode.....");
						if(actiu){
							actiu=false;
						} else {
							actiu=true;
						}
					} 
				}

			} catch(IOException e){
				System.out.println(e.toString());
			}
		}
		socketUDP.close();
	}

	public static void parseParametres(String parse, LlistaClients llista) throws NumberFormatException, UnknownHostException{

		String item1="", item2="", item3="";

		StringTokenizer st2 = new StringTokenizer(parse,"-");
		String cadena1 = "";
		while(st2.hasMoreTokens()){ 	
			cadena1= st2.nextToken();
			if(!cadena1.isEmpty()){
				StringTokenizer st = new StringTokenizer(cadena1,":");
				if (st.hasMoreTokens()) 	item1= st.nextToken();
				if (st.hasMoreTokens()) 	item2= st.nextToken();
				if (st.hasMoreTokens()) 	item3= st.nextToken();

				if (item1.isEmpty()){
					llista.afegirServer(InetAddress.getByName(item2), Integer.parseInt(item3));
				} else {
					llista.afegirServer(item1, InetAddress.getByName(item2), Integer.parseInt(item3));
				}
			}
		}
	}

	public static void mostraResultats(LlistaClients llistaEstacions, Registre estacioActual){
		String valor;
		for(Registre r : llistaEstacions){
			if (r.equals(estacioActual)){
				r.ellMateix();
			}
			valor=r.getNom()+"  "+r.getIP()+":"+r.getPort()+" -> "+r.getViu();
			System.out.println(valor);
		}
	}

	public static void obtindreLlista(String servidor, LlistaClients llistaEstacions, Registre estacioActual) throws UnknownHostException, IOException{
		String modifiedSentence=null;

		Socket clientSocket = new Socket(InetAddress.getByName(servidor), 6789);
		clientSocket.setSoTimeout(100);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 

		outToServer.writeBytes("llista\n");
		try{
			modifiedSentence = inFromServer.readLine(); 
			parseParametres(modifiedSentence, llistaEstacions);
			System.out.println("***************************************");
			mostraResultats(llistaEstacions,estacioActual);
			System.out.println("*************************************** \n \n");
		} catch (SocketTimeoutException e){
			System.out.println("Timeout!! \n");
			System.out.println("No s'ha pogut rebre la llista. Torna a intentar-ho!!\n");
		}
		clientSocket.close(); 
	}

}
