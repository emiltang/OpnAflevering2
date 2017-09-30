package assignment2.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    /**
     * Start server
     *
     * @param args Cmd arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            Registry registry = LocateRegistry.createRegistry(RMI_Config.REGISTRY_PORT);
            registry.bind(RMI_Config.CATALOG_NAME, new CatalogImpl());
            registry.bind(RMI_Config.SUBJECT_NAME, new SubjectImpl());
        } catch (RemoteException | AlreadyBoundException e) {
            throw new Error("Could not start server " + e);
        }
        System.out.println("Server running...");
    }
}
