package InventoryApplication.Model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Inventory class stores all parts and all products in observable lists backed by observable array lists;
 * Also stores all lookup functions for searches, add/update/delete part and product functions;
 * and a function to generate an ID for the item randomly
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart adds the indicated part to the inventory list of all parts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct adds the indicated product to the inventory list of all products
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId uses entered partID to compare against list of all part IDs and return the match
     */
    public static Part lookupPart(int partId) {
        for (Part x : allParts) {
            if (x.getId() == partId) {
                return x;
            }
        }
        return null;
    }

    /**
     * @param productId uses entered productID to compare against list of all product IDs and return the match
     */
    public static Product lookupProduct(int productId) {
        for (Product x : allProducts) {
            if (x.getId() == productId) {
                return x;
            }
        }
        return null;
    }

    /**
     * @param partName uses full or partially entered name of part to compare against list of all part names and return matches
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> sortedParts = FXCollections.observableArrayList();
        for (Part x : allParts) {
            if (x.getName().toLowerCase().contains(partName.toLowerCase().trim())) {
                sortedParts.add(x);
            }
        }
        return sortedParts;
    }

    /**
     * @param productName uses full or partially entered name of product to compare against list of all product names and return matches
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> sortedProducts = FXCollections.observableArrayList();
        for (Product x : allProducts) {
            if (x.getName().toLowerCase().contains(productName.toLowerCase().trim())) {
                sortedProducts.add(x);
            }
        }
        return sortedProducts;
    }


    /**
     * @param index indicates where in part list selected part is
     * @param selectedPart updates part at allocated index in allParts list to be replaced by selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index indicates where in product list selected product is
     * @param selectedProduct updates product at allocated index in allProducts list to be replaced by selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart indicates what part to delete
     * @return returns true if selected part is removed from allParts list
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     *
     * @param selectedProduct indicates what product to delete
     * @return returns true if selected product is removed from allProducts list
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     *
     * @return a list of all parts currently in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return a list of all products currently in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     *
     * @return random number for generated new part or product ID
     */
    public static int getRandomId() {
        Random random = new Random();
        int max = 1000000;
        int id = random.nextInt(max);
        return id;
    }

}
