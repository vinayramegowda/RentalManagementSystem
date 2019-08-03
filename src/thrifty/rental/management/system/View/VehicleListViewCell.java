/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import thrifty.rental.management.system.Model.Vehicle;
import thrifty.rental.management.system.Model.VehicleType;

/**
 *
 * @author Vinay
 */
public class VehicleListViewCell extends ListCell<Vehicle> {

    @FXML
    private Label make;
    @FXML
    private Label year;
    @FXML
    private Label model;
    @FXML
    private Label noOfSeats;
    @FXML
    private Label noOfDoors;
    @FXML
    private Label rentStatus;
    @FXML
    private static ImageView vehicleImage;

    private HBox content;
    private Text name;
    private Text price;

    public VehicleListViewCell() {
        super();
        vehicleImage=new ImageView();
        name = new Text();
        price = new Text();
        VBox vBox = new VBox(name, price);
        content = new HBox(new Label("[Graphic]"), vBox);
        content.setSpacing(10);

    }
    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    public static void updateImage() throws FileNotFoundException {
        FileInputStream input = new FileInputStream("Images/02.png");
        //vehicleImage.setImage(image);
        vehicleImage.setImage(new Image(input));

    }

    @Override
    protected void updateItem(Vehicle vehicle, boolean empty) {
        super.updateItem(vehicle, empty);

        if (empty || vehicle == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("ListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
           
            make.setText(String.valueOf(vehicle.getMake()));
            model.setText(vehicle.getModel());
            year.setText("" + vehicle.getYear());
            VehicleType type = vehicle.getVehicleType();
            if (vehicle.getVehicleClassification().equalsIgnoreCase("car")) {
                noOfSeats.setText("" + type.getCarSeats());
                noOfDoors.setText("4");
            } else {
                noOfSeats.setText("15");
                noOfDoors.setText("5");
            }
            switch (vehicle.getStatus()) {
                case 0:
                    rentStatus.setText("Available");
                    break;
                case 1:
                    rentStatus.setText("Rented");
                    break;
                case 2:
                    rentStatus.setText("UnderMaintainance");
                    break;
            }
            setText(null);
            setGraphic(gridPane);
        }

    }
}
