package InventoryApplication.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.Random;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public Part lookupPart(int partId) {
        for (Part x : allParts) {
            if (x.getId() == partId) {
                return x;
            }
        }
        return null;
    }

    public Product lookupProduct(int productId) {
        for (Product x : allProducts) {
            if (x.getId() == productId) {
                return x;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> sortedParts = FXCollections.observableArrayList();
        for (Part x : allParts) {
            if (x.getName().toLowerCase().contains(partName.toLowerCase().trim())) {
                sortedParts.add(x);
            }
        }
        return sortedParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> sortedProducts = FXCollections.observableArrayList();
        for (Product x : allProducts) {
            if (x.getName().toLowerCase().contains(productName.toLowerCase().trim())) {
                sortedProducts.add(x);
            }
        }
        return sortedProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static int getRandomId() {
        Random random = new Random();
        int max = 1000000;
        int id = random.nextInt(max);
        return id;
    }

}
