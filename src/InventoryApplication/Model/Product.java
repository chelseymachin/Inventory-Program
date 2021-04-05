package InventoryApplication.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product class stores data about each product, gets/sets all info for products, and maintains observable list of associated parts */



/**
 *
 * @author Chelsey Machin
 */

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return id of product
     */
    public int getId() {
        return id;
    }

    /**
     * @param id set the ID of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price of product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price set the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock level of product
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock set the inventory level of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return minimum inventory amount of product
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min set the minimum inventory level of the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return maximum inventory amount of product
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max set the maximum inventory level of the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part add a certain part to the Associated parts list for the product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @return true if part is able to be removed from associated parts observable list
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return the list of associated parts for the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
