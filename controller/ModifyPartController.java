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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Part Controller class.
 *
 * @author Gentillo Curescu
 */
public class ModifyPartController implements Initializable {

    private Part partSelected;

    @FXML
    private Label labelPartIdName;
    @FXML
    private RadioButton radioButtonInHouse;
    @FXML
    private ToggleGroup typeTGPart;
    @FXML
    private RadioButton radioButtonOutsourced;
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


    @FXML
    void actionCancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }


    @FXML
    void actionInHouseRadioButton(ActionEvent event) {

        labelPartIdName.setText("Machine ID");
    }

    @FXML
    void actionOutsourcedRadioButton(ActionEvent event) {

        labelPartIdName.setText("Company Name");
    }


    @FXML
    void actionSaveButton(ActionEvent event) throws IOException {

        try {
            int id = partSelected.getId();
            String name = textPartName.getText();
            Double price = Double.parseDouble(textPartPrice.getText());
            int stock = Integer.parseInt(textPartInventory.getText());
            int min = Integer.parseInt(textPartMin.getText());
            int max = Integer.parseInt(textPartMax.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (validMin(min, max) && validInventory(min, max, stock)) {

                if (radioButtonInHouse.isSelected()) {
                    try {
                        machineId = Integer.parseInt(textPartIdName.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddSuccessful = true;
                    } catch (Exception e) {
                        alertDisplay(2);
                    }
                }

                if (radioButtonOutsourced.isSelected()) {
                    companyName = textPartIdName.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if (partAddSuccessful) {
                    Inventory.deletePart(partSelected);
                    returnToMainScreen(event);
                }
            }
        } catch(Exception e) {
            alertDisplay(1);
        }
    }


    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    private boolean validMin(int min, int max) {

        boolean isTrue = true;

        if (min <= 0 || min >= max) {
            isTrue = false;
            alertDisplay(3);
        }

        return isTrue;
    }
    
    private boolean validInventory(int min, int max, int stock) {

        boolean isTrue = true;

        if (stock < min || stock > max) {
            isTrue = false;
            alertDisplay(4);
        }

        return isTrue;
    }


    private void alertDisplay(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
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
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partSelected = MainScreenController.getPartModified();

        if (partSelected instanceof InHouse) {
            radioButtonInHouse.setSelected(true);
            labelPartIdName.setText("Machine ID");
            textPartIdName.setText(String.valueOf(((InHouse) partSelected).getMachineId()));
        }

        if (partSelected instanceof Outsourced){
            radioButtonOutsourced.setSelected(true);
            labelPartIdName.setText("Company Name");
            textPartIdName.setText(((Outsourced) partSelected).getCompanyName());
        }

        textPartId.setText(String.valueOf(partSelected.getId()));
        textPartName.setText(partSelected.getName());
        textPartInventory.setText(String.valueOf(partSelected.getStock()));
        textPartPrice.setText(String.valueOf(partSelected.getPrice()));
        textPartMax.setText(String.valueOf(partSelected.getMax()));
        textPartMin.setText(String.valueOf(partSelected.getMin()));
    }
}
