package assignment2.Client;

import assignment2.Server.*;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Controller for the application
 *
 * @author ups
 */
@SuppressWarnings("WeakerAccess")
public class ClientController extends UnicastRemoteObject implements IObserver {

    /**
     * Singleton pattern instance
     */
    private static ClientController instance;
    /**
     * The area in which text can be displayed
     */
    private JTextPane displayArea;
    /**
     * The label in which the current status can be displayed
     */
    private JLabel statusLabel;
    /**
     * The catalogue being manipulated
     */
    private ICatalog catalogue;
    private ISubject subject;

    /**
     * Initialize, including connecting to a specific catalogue
     */
    private ClientController() throws RemoteException {
        super();
        try {
            String serverName = JOptionPane.showInputDialog("Server name?", "localhost");
            Registry registry = LocateRegistry.getRegistry(serverName, RMI_Config.REGISTRY_PORT);
            catalogue = (ICatalog) registry.lookup(RMI_Config.CATALOG_NAME);
            subject = (ISubject) registry.lookup(RMI_Config.SUBJECT_NAME);
            subject.addObserver(this);
        } catch (RemoteException | NotBoundException e) {
            throw new Error("Could not connect to server " + e);
        }
        System.out.println("Client successfully connected to server");
    }

    /**
     * Singleton pattern access method
     */
    public static synchronized ClientController get() {
        try {
            if (instance == null)
                instance = new ClientController();
            return instance;
        } catch (RemoteException e) {
            throw new Error("Error connection to controller " + e);
        }
    }

    /**
     * Increase button clicked in GUI
     */
    public void increaseAction(String productName, String amountText) {
        changeEntry(productName, amountText, true);
    }

    /**
     * Decrease button clicked in GUI
     */
    public void decreaseAction(String productName, String amountText) {
        changeEntry(productName, amountText, false);
    }

    /**
     * Helper: generic implementation of the increase/decrease action
     */
    private void changeEntry(String productName, String amountText, boolean increase) {
        try {
            int amount = Integer.parseInt(amountText);
            IEntry entry = catalogue.getEntry(productName);
            if (entry == null) {
                setStatus("No such entry: " + productName);
                return;
            }
            if (entry.updateQuantity(increase ? amount : -amount)) // Returns true if success
                updateDisplay();
            else
                setStatus("Negative stock not allowed");
            subject.notifyChanged();
        } catch (RemoteException e) {
            String s = "Error connecting to server " + e;
            System.err.println(s);
            setStatus(s);
        }
    }

    /**
     * Show the status to the user
     */
    private void setStatus(String status) {
        if (statusLabel == null) throw new Error("Internal error: unable to display status");
        statusLabel.setText(status);
    }

    /**
     * Update display, showing the current entries and their total value
     */
    private void updateDisplay() {
        try {
            // Check that controller is properly initialized
            if (catalogue == null) throw new Error("Internal error: catalogue not set");
            if (displayArea == null) throw new Error("Internal error: display area not set");
            // Then do the display
            StringBuilder result = new StringBuilder();
            float value = 0;
            // For each item in the stock, add it to the description list and the total value
            for (String name : catalogue.getEntryNames()) {
                IEntry entry = catalogue.getEntry(name);
                Product product = entry.getProduct();
                result.append(product.getName())
                        .append("@")
                        .append(product.getPrice())
                        .append(": ")
                        .append(entry.getQuantity())
                        .append("\n");
                value += entry.getQuantity() * product.getPrice();
            }
            result.append("Total value: ").append(value);
            displayArea.setText(result.toString());
        } catch (RemoteException e) {
            String s = "Error connecting to server " + e;
            System.err.println(s);
            displayArea.setText(s);
        }
    }

    /**
     * Search button clicked in GUI: use text area to show matching products
     */
    public void searchAction(String prefix) {
        try {
            StringBuilder result = new StringBuilder();
            for (Product product : catalogue.search(prefix)) {
                result.append(product.getName()).append("\n");
            }
            displayArea.setText(result.toString());
        } catch (RemoteException e) {
            String s = "Error connecting to server " + e;
            System.err.println(s);
            displayArea.setText(s);
        }
    }

    /**
     * Clear button clicked in GUI: go back to inventory display
     */
    public void clearAction() {
        updateDisplay();
    }

    /**
     * Set the inventory display object
     * (Note: high coupling, ideally use observer instead)
     */
    public void setInventoryDisplay(JTextPane entriesDisplay) {
        this.displayArea = entriesDisplay;
        updateDisplay();
    }

    /**
     * Set the status display object
     * (Note: high coupling, ideally use observer instead)
     */
    public void setStatusArea(JLabel label) {
        this.statusLabel = label;
        setStatus("Ready");
    }

    @Override
    public void update() throws RemoteException {
        updateDisplay();
    }
}
