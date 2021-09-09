package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Product Controller class.
 *
 * @author Gentillo Curescu
 */
public class ModifyProductController implements Initializable {

    Product selectedProduct;

    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> viewAssocPartTable;
    @FXML
    private TableColumn<Part, Integer> columnAssocPartId;
    @FXML
    private TableColumn<Part, String> columnAssocPartName;
    @FXML
    private TableColumn<Part, Integer> columnAssocPartInventory;
    @FXML
    private TableColumn<Part, Double> columnAssocPartPrice;
    @FXML
    private TableView<Part> viewPartTable;
    @FXML
    private TableColumn<Part, Integer> columnPartId;
    @FXML
    private TableColumn<Part, String> columnPartName;
    @FXML
    private TableColumn<Part, Integer> columnPartInventory;
    @FXML
    private TableColumn<Part, Double> columnPartPrice;
    @FXML
    private TextField textPartSearch;
    @FXML
    private TextField textProductId;
    @FXML
    private TextField textProductName;
    @FXML
    private TextField textProductInventory;
    @FXML
    private TextField textProductPrice;
    @FXML
    private TextField textProductMax;
    @FXML
    private TextField textProductMin;


    @FXML
    void actionAddButton(ActionEvent event) {

        Part selectedPart = viewPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplay(5);
        } else {
            assocParts.add(selectedPart);
            viewAssocPartTable.setItems(assocParts);
        }
    }


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
    void actionRemoveButton(ActionEvent event) {

        Part selectedPart = viewAssocPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplay(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                viewAssocPartTable.setItems(assocParts);
            }
        }
    }


    @FXML
    void actionSaveButton(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = textProductName.getText();
            Double price = Double.parseDouble(textProductPrice.getText());
            int stock = Integer.parseInt(textProductInventory.getText());
            int min = Integer.parseInt(textProductMin.getText());
            int max = Integer.parseInt(textProductMax.getText());

            if (name.isEmpty()) {
                alertDisplay(7);
            } else {
                if (validMin(min, max) && validInventory(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e){
            alertDisplay(1);
        }
    }


    @FXML
    void actionSearchBttn(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = textPartSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        viewPartTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            alertDisplay(1);
        }
    }


    @FXML
    void pressedSearchKey(KeyEvent event) {

        if (textPartSearch.getText().isEmpty()) {
            viewPartTable.setItems(Inventory.getAllParts());
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
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
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
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedProduct = MainScreenController.getProductModified();
        assocParts = selectedProduct.getAllParts();

        columnPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        viewPartTable.setItems(Inventory.getAllParts());

        columnAssocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAssocPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnAssocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        viewAssocPartTable.setItems(assocParts);

        textProductId.setText(String.valueOf(selectedProduct.getId()));
        textProductName.setText(selectedProduct.getName());
        textProductInventory.setText(String.valueOf(selectedProduct.getStock()));
        textProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        textProductMax.setText(String.valueOf(selectedProduct.getMax()));
        textProductMin.setText(String.valueOf(selectedProduct.getMin()));
    }
}