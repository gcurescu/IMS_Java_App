package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models a product that will contain the associated parts.
 *
 * @author Gentillo Curescu
 *
 */
public class Product {
    /**
     *
     *The ID for the product
     *The name of the product
     *The price of the product
     *The inventory level of the product
     *The minimum level for the product
     *The maximum level for the product
     *A list of associated parts for the product
     */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> partsAssociated = FXCollections.observableArrayList();

    /**
     * This is a Constructor for each new instance of the product
     *
     * @param id the ID for the product, name the name of the product, price the price of the product, stock the inventory level of the product, min the minimum level for the product, max the maximum level for the product
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The getters and setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds part to parts list for the product.
     *
     * @param part Adds part
     */

    public void  addAssociatedPart(Part part) {
        partsAssociated.add(part);
    }

    /**
     * Deletes part from the parts list.
     *
     * @param selectedAssociatedPart The part to delete
     * @return a boolean indicating status of the part deletion
     */

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (partsAssociated.contains(selectedAssociatedPart)) {
            partsAssociated.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    public ObservableList<Part> getAllParts() {return partsAssociated;}
}
