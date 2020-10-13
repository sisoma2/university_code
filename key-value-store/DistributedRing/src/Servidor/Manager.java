package Servidor;
import java.rmi.*;
import java.util.HashMap;

import Excepcions.*;

public interface Manager extends Remote {
	void replicate(String K, Valor V) throws RemoteException;
	String get(String k) throws RemoteException, QuorumLectura;
	String put(String k, String v) throws RemoteException, QuorumLectura, QuorumEscriptura;
	Valor getValor(String k) throws RemoteException;
	int getVersion(String k) throws RemoteException;
	void alive() throws RemoteException;
	void antiEntropy(HashMap<String, Valor> tHash) throws RemoteException;
	void stopServer() throws RemoteException;
	int getId() throws RemoteException;
}
