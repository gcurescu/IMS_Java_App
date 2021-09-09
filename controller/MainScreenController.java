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
 * Controller class that provides control logic for the main screen of the application.
 *
 * A runtime error occurs if no part is selected when the user clicks the modify button.
 * This runtime error occurs due to a null value being passed to the initialize method of the
 * ModifyPartController.

 *
 * @author Gentillo Curescu
 */
public class MainScreenController implements Initializable {


    private static Part partModified;

    private static Product productModified;

    @FXML
    private TextField textPartSearch;
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
    private TextField textProductSearch;
    @FXML
    private TableView<Product> viewProductTable;
    @FXML
    private TableColumn<Product, Integer> columnProductId;
    @FXML
    private TableColumn<Product, String> columnProductName;
    @FXML
    private TableColumn<Product, Integer> columnProductInventory;
    @FXML
    private TableColumn<Product, Double> columnProductPrice;


    public static Part getPartModified() {
        return partModified;
    }

    public static Product getProductModified() {
        return productModified;
    }

    @FXML
    void actionExitButton(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void actionPartAdd(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPartScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void actionPartDelete(ActionEvent event) {

        Part selectedPart = viewPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplay(3);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }


    @FXML
    void actionPartModify(ActionEvent event) throws IOException {

        partModified = viewPartTable.getSelectionModel().getSelectedItem();

        //Example of correcting a runtime error by preventing null from being passed
        // to the ModifyPartController.
        if (partModified == null) {
            alertDisplay(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPartScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void actionPartSearchBttn(ActionEvent event) {

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
    void pressedPartSearchTextKey(KeyEvent event) {

        if (textPartSearch.getText().isEmpty()) {
            viewPartTable.setItems(Inventory.getAllParts());
        }

    }


    @FXML
    void actionProductAdd(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProductScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void actionProductDelete(ActionEvent event) {

        Product selectedProduct = viewProductTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            alertDisplay(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllParts();

                if (assocParts.size() >= 1) {
                    alertDisplay(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }


    @FXML
    void actionProductModify(ActionEvent event) throws IOException {

        productModified = viewProductTable.getSelectionModel().getSelectedItem();

        if (productModified == null) {
            alertDisplay(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProductScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void actionProductSearchBttn(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = textProductSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        viewProductTable.setItems(productsFound);

        if (productsFound.size() == 0) {
            alertDisplay(2);
        }
    }

    @FXML
    void pressedProductSearchTextKey(KeyEvent event) {

        if (textProductSearch.getText().isEmpty()) {
            viewProductTable.setItems(Inventory.getAllProducts());
        }
    }

    private void alertDisplay(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Populate parts table view
        viewPartTable.setItems(Inventory.getAllParts());
        columnPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate products table view
        viewProductTable.setItems(Inventory.getAllProducts());
        columnProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
