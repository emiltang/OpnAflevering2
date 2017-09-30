package assignment2.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Generic interface to a catalog of entries
 * (part of the model layer of the application)
 *
 * @author ups
 */
public interface ICatalog extends Remote {
    /**
     * Get all names of entries
     */
    Set<String> getEntryNames() throws RemoteException;

    /**
     * Lookup entry by name
     */
    IEntry getEntry(String name) throws RemoteException;

    /**
     * Search catalog and return all products that match the given prefix
     */
    List<Product> search(String prefix) throws RemoteException;

}
