package InventoryApplication.Controller;


import InventoryApplication.Model.InHouse;
import InventoryApplication.Model.Outsourced;
import InventoryApplication.Model.Inventory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class acts as a Controller for the Add Part screen.  You can enter in all fields and then save to generate a new part for Inventory */


public class AddPart implements Initializable {
    public RadioButton inhouse;
    public ToggleGroup addPartGroup;
    public RadioButton outsourced;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public Label addPartChangeLabel;
    public TextField machineIdTextField;
    public Button saveButton;
    public Button cancelButton;

    public void toInhouse(ActionEvent actionEvent) {
        addPartChangeLabel.setText("Machine ID");
    }

    public void toOutsourced(ActionEvent actionEvent) {
        addPartChangeLabel.setText("Company Name");
    }

    public void saveAddPart(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int stock = Integer.parseInt(inventoryTextField.getText());
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
        }

        if (inhouse.isSelected()) {
            int machineId = Integer.parseInt(machineIdTextField.getText());
            InHouse part = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.addPart(part);
        } else if (outsourced.isSelected()) {
            String companyName = machineIdTextField.getText();
            Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.addPart(part);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        Stage stage = (Stage) saveButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 320);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 320);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setText(String.valueOf(Inventory.getRandomId()));
    }
}
