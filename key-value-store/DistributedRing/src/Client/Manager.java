package Client;
import java.rmi.*;

public interface Manager extends Remote {
	String get(String k) throws RemoteException;
	String put(String k, String v) throws RemoteException;
	void stopServer() throws RemoteException;
}
