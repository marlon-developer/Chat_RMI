
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface IChat extends Remote {

    public void responseMessage(String frase) throws RemoteException;

    public LinkedList readMessage() throws RemoteException;
}
