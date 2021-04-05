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
import java.util.Optional;
import java.util.ResourceBundle;

/** This class acts as a Controller for the Add Product screen.  You can enter in all fields and then save to generate a new product for Inventory */


public class AddProduct implements Initializable {
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
    public Button addButton;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> associatedPartsIdCol;
    public TableColumn<Part, String> associatedPartsNameCol;
    public TableColumn<Part, Integer> associatedPartsInvCol;
    public TableColumn<Part, Double> associatedPartsPriceCol;
    public Button removeButton;
    public Button saveButton;
    public Button cancelButton;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedParts);

        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        idTextField.setText(String.valueOf(Inventory.getRandomId()));
    }

    /**
     *
     * @param actionEvent uses lookupPart to compare search string in parts table with list of allParts in inventory, returns results and sets them to the table
     */
    public void searchParts(ActionEvent actionEvent) {
        String search = partsSearch.getText();
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

        allPartsTable.setItems(parts);
    }

    /**
     *
     * @param actionEvent adds selected part if selection is not null
     */
    public void addPart(ActionEvent actionEvent) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartsTable.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No value selected");
            alert.setContentText("Please select a part to add");
            alert.showAndWait();
        }

    }

    /**
     *
     * @param actionEvent checks that selection is not null, confirms deletion with user, then removes selected part from associated parts table (not inventory itself)
     */
    public void removePart(ActionEvent actionEvent) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Remove Part");
            alert.setContentText("Are you sure you want to remove this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartsTable.setItems(associatedParts);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No value selected");
            alert.setContentText("Please select a part to remove");
            alert.showAndWait();
        }

    }

    /**
     *
     * @param actionEvent checks for blanks in screen fields, if no blanks, sets data values with input from fields, adds product to inventory, returns to main screen
     * @throws IOException
     */
    public void saveAddProduct(ActionEvent actionEvent) throws IOException {
        if (idTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty() || invTextField.getText().isEmpty() || minTextField.getText().isEmpty() || maxTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Value");
            alert.setContentText("Please enter an appropriate value for each blank field before saving.");
            alert.showAndWait();
        } else {
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
                Product newProduct = new Product(id, name, price, stock, min, max);
                for (Part p : associatedParts) {
                    newProduct.addAssociatedPart(p);
                }

                Inventory.addProduct(newProduct);

                Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/view/MainScreen.fxml"));
                Stage stage = (Stage) saveButton.getScene().getWindow();
                Scene scene = new Scene(root, 800, 320);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     *
     * @param actionEvent when cancel button pressed, returns user to main screen without saving
     * @throws IOException
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 320);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
