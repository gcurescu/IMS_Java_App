package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * This is the Main class which runs the Inventory Management application
 *
 * A feature that I would like to add for the future version would be to control pricing based on
 * the sales made for each product and how much there is in stock.
 *
 * @author Gentillo Curescu
 *
 */
public class Main extends Application {

    /**
     * In this start method an FXML stage is created and the initial scene is loaded.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * In this main method sample data is created upon the launch of the app.
     *
     * @param args
     */
    public static void main(String[] args) {

        //Add sample parts
        int partId = Inventory.getNewPartId();
        InHouse part1 = new InHouse(partId,"Part 1", 200.00, 20, 1, 20,
                101);
        partId = Inventory.getNewPartId();
        InHouse part2 = new InHouse(partId,"Part 2", 120.00, 20, 1, 20,
                101);
        partId = Inventory.getNewPartId();
        InHouse part3 = new InHouse(partId,"Part 3", 5.99, 20, 1, 20,
                101);
        partId = Inventory.getNewPartId();
        Outsourced part4 = new Outsourced(partId, "Part 4",49.99, 50, 30,
                100, "Company 1");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        //Add sample product
        int productId = Inventory.addNewProductId();
        Product product1 = new Product(productId, "Product 1", 550.00, 20, 20,
                100);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part4);
        Inventory.addProduct(product1);

        launch(args);
    }
}
