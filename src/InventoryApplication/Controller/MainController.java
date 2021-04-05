package InventoryApplication.Controller;

import InventoryApplication.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class acts as a Controller for the Main screen.
 * You can search through all parts and products here, select one to modify, or go to the add screen.*/
public class MainController implements Initializable {
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partsIdCol;
    public TableColumn<Part, String> partsNameCol;
    public TableColumn<Part, Integer> partsInventoryLevelCol;
    public TableColumn<Part, Double> partsPricePerUnitCol;
    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public TextField partSearch;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productsIdCol;
    public TableColumn<Product, String> productsNameCol;
    public TableColumn<Product, Integer> productsInventoryLevelCol;
    public TableColumn<Product, Double> productsPricePerUnitCol;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public TextField productSearch;
    public Button exitButton;
    private Part selectedPart;
    private Product selectedProduct;
    private int selectionIndex;
    public static boolean firstTime = true;

    /**
     * Adds initial test data into program as inventory
     */
    public void addTestData() {
        if (!firstTime) {
            return;
        }
        firstTime = false;
        InHouse a = new InHouse(Inventory.getRandomId(), "Screw", 0.33, 555, 0, 999999, 545455);
        Inventory.addPart(a);
        InHouse b = new InHouse(Inventory.getRandomId(), "Washer", 0.25, 999, 0, 999, 155532);
        Inventory.addPart(b);
        Outsourced c = new Outsourced(Inventory.getRandomId(), "Nail", 0.10, 999, 0, 999999, "ABQ Fabrications");
        Inventory.addPart(c);
        Product d = new Product(Inventory.getRandomId(), "Gasket Frame", 15.99, 15, 0, 555);
        Inventory.addProduct(d);
        Product e = new Product(Inventory.getRandomId(), "Loopy Hoop", 29.99, 110, 0, 9999);
        Inventory.addProduct(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();

        partsTable.setItems(Inventory.getAllParts());

        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     *
     * @param actionEvent when add part button is clicked, takes you to add part screen with blanks
     * @throws IOException
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/AddPart.fxml"));
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        Scene scene = new Scene(root, 450, 350);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent checks to make sure a part is selected from parts table, then takes you to parts screen with that part's data loaded into it
     * @throws IOException
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        selectionIndex = Inventory.getAllParts().indexOf(selectedPart);
        ModifyPart.passData(selectedPart, selectionIndex);
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryApplication/View/ModifyPart.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) modifyPartButton.getScene().getWindow();
            Scene scene = new Scene(root, 450, 350);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Input");
            alert.setContentText("Please select a valid part to modify");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent checks that part is selected, confirms deletion action with user, then deletes selected part from allParts inventory
     */
    public void deletePart(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Delete Part");
            alert.setContentText("Are you sure you want to remove this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        } else {
            Alert otherAlert = new Alert(Alert.AlertType.ERROR);
            otherAlert.setHeaderText("No Input");
            otherAlert.setContentText("Please select a valid product to delete");
            otherAlert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent when user enters text into search box above parts table, checks whether int or string, then compares either to list of inventory
     *                    utilizing lookupPart function and sets table items with results
     */
    public void searchParts(ActionEvent actionEvent) {
        String search = partSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(search);

        if (parts.isEmpty()) {
            try {
                int searchId = Integer.parseInt(search);
                Part part = Inventory.lookupPart(searchId);
                if (part != null) {
                    parts.add(part);
                }

            } catch (NumberFormatException e) {
                // Ignore exception catch
            }

        }

        partsTable.setItems(parts);
    }

    /**
     *
     * @param actionEvent takes user to add product screen with blanks
     * @throws IOException
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/AddProduct.fxml"));
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent takes user to modify product screen and passes data about selected product and its index to that controller
     * @throws IOException
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        selectionIndex = Inventory.getAllProducts().indexOf(selectedProduct);

        if (selectedProduct != null) {
            ModifyProduct.passData(selectedProduct, selectionIndex);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryApplication/View/ModifyProduct.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) modifyProductButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Input");
            alert.setContentText("Please select a valid product to modify");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent checks to make sure a product is selected, then deletes that product from allProducts inventory after confirming action with user
     */
    public void deleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Delete Product");
            alert.setContentText("Are you sure you want to remove this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        } else {
            Alert otherAlert = new Alert(Alert.AlertType.ERROR);
            otherAlert.setHeaderText("No Input");
            otherAlert.setContentText("Please select a valid product to delete");
            otherAlert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent uses lookupProduct to compare given search box text to list of allProducts in inventory, returns results and sets table with resulting list
     */
    public void searchProducts(ActionEvent actionEvent) {
        String search = productSearch.getText();
        ObservableList<Product> products = Inventory.lookupProduct(search);

        if (products.isEmpty()) {
            try {
                int searchId = Integer.parseInt(search);
                Product product = Inventory.lookupProduct(searchId);
                if (product != null) {
                    products.add(product);
                }

            } catch (NumberFormatException e) {
                // Ignore exception catch
            }

        }

        productsTable.setItems(products);
    }

    /**
     *
     * @param actionEvent upon pressing exit button, closes program fully
     */
    public void exitProgram(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Program Exit");
            alert.setContentText("Are you sure you want to leave the program?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }


    }
}
