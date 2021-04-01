package InventoryApplication.Controller;

import InventoryApplication.Model.InHouse;
import InventoryApplication.Model.Inventory;
import InventoryApplication.Model.Outsourced;
import InventoryApplication.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {
    public RadioButton inhouse;
    public ToggleGroup sourceGroup;
    public RadioButton outsourced;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public Label modifyPartChangeLabel;
    public TextField machineIdTextField;
    public Button saveButton;
    public Button cancelButton;
    private static Part selection;
    private static int selectionIndex;
    private Boolean isInHouse() {
        return inhouse.isSelected();
    }
    private Boolean isOutsourced() {
        return outsourced.isSelected();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void toInhouse(ActionEvent actionEvent) {
        modifyPartChangeLabel.setText("Machine ID");

    }

    public void toOutsourced(ActionEvent actionEvent) {
        modifyPartChangeLabel.setText("Company Name");
    }

    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int stock = Integer.parseInt(inventoryTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        if (isInHouse()) {
            int machineId = Integer.parseInt(machineIdTextField.getText());
            InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.updatePart(selectionIndex, updatedPart);
        } else if (isOutsourced()) {
            String companyName = machineIdTextField.getText();
            Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.updatePart(selectionIndex, updatedPart);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
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
    public static void passData(Part part, int index) {
        selection = part;
        selectionIndex = index;

    }
    private void loadData() {
        idTextField.setText(Integer.toString(selection.getId()));
        nameTextField.setText(selection.getName());
        inventoryTextField.setText(Integer.toString(selection.getStock()));
        priceTextField.setText(Double.toString(selection.getPrice()));
        maxTextField.setText(Integer.toString(selection.getMax()));
        minTextField.setText(Integer.toString(selection.getMin()));

        if (selection instanceof InHouse) {
            sourceGroup.selectToggle(inhouse);
            machineIdTextField.setText(String.valueOf(((InHouse) selection).getMachineId()));
        }

        if (selection instanceof Outsourced) {
            sourceGroup.selectToggle(outsourced);
            machineIdTextField.setText(((Outsourced) selection).getCompanyName());
        }
    }
}
