package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Add Part Controller class.
 *
 * @author Gentillo Curescu
 */
public class AddPartController implements Initializable {

    @FXML
    private Label labelPartIdName;
    @FXML
    private RadioButton buttonInHouseRadio;
    @FXML
    private ToggleGroup typeTGPart;
    @FXML
    private RadioButton buttonOutsourcedRadio;
    @FXML
    private TextField textPartId;
    @FXML
    private TextField textPartName;
    @FXML
    private TextField textPartInventory;
    @FXML
    private TextField textPartPrice;
    @FXML
    private TextField textPartMax;
    @FXML
    private TextField textPartIdName;
    @FXML
    private TextField textPartMin;

    /**
     * This Method will display a confirmation box and load the main screen upon action.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void actionCancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to cancel your changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * This Radio Button is used to set labels for InHouse parts.
     *
     * @param event In-house radio button action.
     */
    @FXML
    void actionInHouseRadioButton(ActionEvent event) {

        labelPartIdName.setText("Machine ID");
    }

    /**
     * This Radio Button is used to set labels for Outsourced parts.
     *
     * @param event Outsourced radio button.
     */
    @FXML
    void actionOutsourcedRadioButton(ActionEvent event) {

        labelPartIdName.setText("Company Name");
    }

    /**
     * This method saves a new part to inventory then loads the Main Screen.
     *
     * Error messages are displayed when empty or/and invalid values are entered in the fields.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void actionSaveButton(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = textPartName.getText();
            Double price = Double.parseDouble(textPartPrice.getText());
            int stock = Integer.parseInt(textPartInventory.getText());
            int min = Integer.parseInt(textPartMin.getText());
            int max = Integer.parseInt(textPartMax.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                alertDisplay(5);
            } else {
                if (validMin(min, max) && validInventory(min, max, stock)) {

                    if (buttonInHouseRadio.isSelected()) {
                        try {
                            machineId = Integer.parseInt(textPartIdName.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (Exception e) {
                            alertDisplay(2);
                        }
                    }

                    if (buttonOutsourcedRadio.isSelected()) {
                        companyName = textPartIdName.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        returnToMainScreen(event);
                    }
                }
            }
        } catch(Exception e) {
            alertDisplay(1);
        }
    }

    /**
     * This method loads the Main Screen.
     *
     * @param event Passed from parent method.
     * @throws IOException From FXMLLoader.
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to validate that the min value entered is greater than 0
     * and less than the max value entered
     *
     * @param min
     * @param max
     * @return Boolean indicating if min is valid.
     */
    private boolean validMin(int min, int max) {

        boolean isTrue = true;

        if (min <= 0 || min >= max) {
            isTrue = false;
            alertDisplay(3);
        }

        return isTrue;
    }

    /**
     * This method is used to validate that the inventory level is equal too  
     * or between the min and max.
     *
     * @param min
     * @param max
     * @param stock
     * @return Boolean indicating if inventory is valid.
     */
    private boolean validInventory(int min, int max, int stock) {

        boolean isTrue = true;

        if (stock < min || stock > max) {
            isTrue = false;
            alertDisplay(4);
        }

        return isTrue;
    }

    /**
     * Displays various alert messages.
     *
     * @param alertType Alert message selector.
     */
    private void alertDisplay(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be a number equal to or between Min and Max.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * The controller is initialized and sets the radio button for InHouse to true
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonInHouseRadio.setSelected(true);
    }
}
