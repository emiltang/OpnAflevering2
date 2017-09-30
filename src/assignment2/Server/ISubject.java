package assignment2.Server;

import assignment2.Client.IObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISubject extends Remote {
    void addObserver(IObserver observer) throws RemoteException;

    void notifyChanged() throws RemoteException;
}
