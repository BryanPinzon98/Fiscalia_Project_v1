package gov.fged.UI.HomePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        primaryStage.setTitle("Registro Fiscalia");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
