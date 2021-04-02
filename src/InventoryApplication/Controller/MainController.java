package InventoryApplication.Controller;

import InventoryApplication.Model.*;
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

    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/AddPart.fxml"));
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        Scene scene = new Scene(root, 450, 350);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

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

    public void searchParts(ActionEvent actionEvent) {
        partsTable.setItems(Inventory.lookupPart(partSearch.getText()));
    }

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/AddProduct.fxml"));
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

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

    public void searchProducts(ActionEvent actionEvent) {
        productsTable.setItems(Inventory.lookupProduct(productSearch.getText()));
    }

    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
