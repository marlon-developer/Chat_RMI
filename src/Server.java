
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    String url = "rmi://localhost/Chat";

    public Server() {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Chat objetoRemoto = new Chat();
            Naming.bind(url, objetoRemoto);
            System.out.println("Servidor Conectado!");
        } catch (MalformedURLException | AlreadyBoundException | RemoteException ex) {

        }
    }

    public static void main(String args[]) {
        new Server();
    }
}
