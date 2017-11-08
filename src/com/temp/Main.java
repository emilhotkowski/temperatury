package com.temp;

import com.temp.model.Temperature;
import com.temp.view.TemperatureController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private ObservableList<Temperature> temperatures = FXCollections.observableArrayList();

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/temperature.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Temperatury");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        TemperatureController controller = loader.getController();
        controller.setMain(this);

    }


    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Temperature> getTemperatures() {
        return temperatures;
    }

}
