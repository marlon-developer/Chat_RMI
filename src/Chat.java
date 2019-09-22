
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class Chat extends UnicastRemoteObject implements IChat {

    public LinkedList<String> listMessage;

    public Chat() throws RemoteException {
        listMessage = new LinkedList<>();
    }

    @Override
    public void responseMessage(String str) throws RemoteException {
        listMessage.add(str);
        System.out.println(str);
    }

    @Override
    public LinkedList<String> readMessage() throws RemoteException {
        return listMessage;
    }
}
