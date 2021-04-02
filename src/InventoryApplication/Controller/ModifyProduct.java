package InventoryApplication.Controller;

import InventoryApplication.Model.Inventory;
import InventoryApplication.Model.Part;
import InventoryApplication.Model.Product;
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
import java.util.ResourceBundle;

public class ModifyProduct implements Initializable {
    public TextField idTextField;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> allPartsIdCol;
    public TableColumn<Part, String> allPartsNameCol;
    public TableColumn<Part, Integer> allPartsInvCol;
    public TableColumn<Part, Double> allPartsPriceCol;
    public TextField partsSearch;
    public Button addPartButton;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> associatedPartsIdCol;
    public TableColumn<Part, String> associatedPartsNameCol;
    public TableColumn<Part, Integer> associatedPartsInvCol;
    public TableColumn<Part, Double> associatedPartsPriceCol;
    public Button removePartButton;
    public Button saveButton;
    public Button cancelButton;
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static Product selection;
    private static int selectionIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(selection.getAllAssociatedParts());

        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void searchParts(ActionEvent actionEvent) {
        allPartsTable.setItems(Inventory.lookupPart(partsSearch.getText()));
    }

    public void addPart(ActionEvent actionEvent) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
        associatedPartsTable.setItems(associatedParts);
    }

    public void removePart(ActionEvent actionEvent) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        associatedParts.remove(selectedPart);
        associatedPartsTable.setItems(associatedParts);
    }

    public void saveModifyProduct(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int stock = Integer.parseInt(invTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Value");
            alert.setContentText("Please enter a minimum value that's less than the maximum value.");
            alert.showAndWait();
        } else if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Value");
            alert.setContentText("Please enter a stock value that's in between the minimum and maximum allowable stock.");
            alert.showAndWait();
        } else {
            Product updatedProduct = new Product(id, name, price, stock, min, max);
            for (Part p : associatedParts) {
                updatedProduct.addAssociatedPart(p);
            }
            Inventory.updateProduct(selectionIndex, updatedProduct);

            Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/view/MainScreen.fxml"));
            Stage stage = (Stage) saveButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 320);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 320);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public static void passData(Product product, int index) {
        selection = product;
        selectionIndex = index;
        associatedParts = selection.getAllAssociatedParts();
    }

    public void loadData() {
        idTextField.setText(Integer.toString(selection.getId()));
        nameTextField.setText(selection.getName());
        invTextField.setText(Integer.toString(selection.getStock()));
        priceTextField.setText(Double.toString(selection.getPrice()));
        maxTextField.setText(Integer.toString(selection.getMax()));
        minTextField.setText(Integer.toString(selection.getMin()));


    }
}
