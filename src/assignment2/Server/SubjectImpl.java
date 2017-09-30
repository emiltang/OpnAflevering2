package assignment2.Server;

import assignment2.Client.IObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

/* Sources:
 * https://en.wikipedia.org/wiki/Observer_pattern
 * https://www.tutorialspoint.com/design_pattern/observer_pattern
 */

@SuppressWarnings("WeakerAccess")
public class SubjectImpl extends UnicastRemoteObject implements ISubject {

    private final Set<IObserver> observers = new HashSet<>();

    public SubjectImpl() throws RemoteException {
        super();
    }

    @Override
    public void addObserver(IObserver observer) throws RemoteException {
        observers.add(observer);
    }

    @Override
    public void notifyChanged() throws RemoteException {
        for (IObserver observer : observers) {
            observer.update();
        }
    }
}
