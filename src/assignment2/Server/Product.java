package assignment2.Server;

import java.io.Serializable;

/**
 * Product that has a name and a price
 * (part of the model layer of the application)
 *
 * @author ups
 */
@SuppressWarnings("SimplifiableIfStatement")
public class Product implements Serializable {

    /**
     * Name of the product
     */
    private String name;

    /**
     * Price of the product
     */
    private float price;

    /**
     * Create product with the given name and price
     */
    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Get name of product
     */
    public String getName() {
        return name;
    }

    /**
     * Get price of product
     */
    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Float.compare(product.price, price) != 0) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
