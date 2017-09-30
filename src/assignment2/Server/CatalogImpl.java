package assignment2.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Implementation of a catalog
 * (part of the model layer of the application)
 *
 * @author ups
 */
@SuppressWarnings("WeakerAccess")
public class CatalogImpl extends UnicastRemoteObject implements ICatalog {


    private static final String[] PRODUCT_NAMES = {
            "cucumber", "carrot", "potato", "pear", "apple", "zucchini", "beet", "onion", "tomato", "orange", "banana", "grapes", "sweet potato",
            "garlic", "lemon", "lime", "cabbage", "corn"
    };
    /**
     * Contents of the catalog
     */
    private Map<String, IEntry> stock = new HashMap<>();

    /**
     * Create and initialize the stock
     */
    public CatalogImpl() throws RemoteException {
        initializeStock();
    }

    /**
     * Lookup entry by name
     */
    @Override
    public IEntry getEntry(String name) {
        return stock.get(name);
    }

    /**
     * Search catalog and return all products that match the given prefix
     */
    @Override
    public List<Product> search(String pattern) {
        ArrayList<Product> result = new ArrayList<>();
        try {
            for (Map.Entry<String, IEntry> entry : stock.entrySet()) {
                if (entry.getKey().startsWith(pattern))
                    result.add(entry.getValue().getProduct());
            }
        } catch (RemoteException e) {
            System.err.println("Error connection to UnicastRemoteObject " + this + " " + e);
        }
        return result;
        /*
        return stock.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(pattern))
                .map(entry -> entry.getValue().getProduct())
                .collect(Collectors.toList());
        */
    }

    /**
     * Get all names of entries
     */
    @Override
    public Set<String> getEntryNames() {
        return new HashSet<>(stock.keySet());
    }

    /**
     * Initialize stock with dummy products
     */
    private void initializeStock() {
        Random random = new Random();
        try {
            for (String PRODUCT_NAME : PRODUCT_NAMES)
                stock.put(PRODUCT_NAME, new EntryImpl(new Product(PRODUCT_NAME, random.nextInt(1000) / 100.0f), random.nextInt(10)));
        } catch (RemoteException e) {
            System.err.println("Error connection to RemoteObject " + e);
        }
    }
}
