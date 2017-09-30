package assignment2.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Generic interface for an entry containing a product and its corresponding quantity
 * (part of the model layer of the application)
 *
 * @author ups
 */
public interface IEntry extends Remote {
    /**
     * Get the quantity
     */
    int getQuantity() throws RemoteException;

    /**
     * Change the quantity (positive number implies increase, negative number implies reduction)
     */
    boolean updateQuantity(int change) throws RemoteException;

    /**
     * Get the product
     */
    Product getProduct() throws RemoteException;
}
