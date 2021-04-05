package InventoryApplication;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class creates an application that stores and manipulates parts and products as an inventory management system
 *
 * RUNTIME ERROR:
 * During production, encountered an error where each time a user clicked the "Add part" button in the add/modify products screen,
 * a blank result would be added to the observable list and the table data for associated parts would fill with blank results of set items.
 * Fixed this by checking for null value before adding the part to the associated parts list for the add/modify product selected or being worked on.
 * This prevented blank data being added to the table results and the associated parts list.
 *
 * FUTURE ENHANCEMENT:
 * A future enhancement that I would add to this program is a selection system for the outsourced parts company names.  I would create another screen
 * where you can add Company profiles to another data set (part companies data set), and then select a company from that data set when you go to add
 * an outsourced part to inventory. This could be done using a dropdown menu with a list of company names from that data set when you go to add a part
 * in the add part screen.
 * */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/InventoryApplication/View/MainScreen.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root, 800, 320));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
