package gov.fged.java.UIController.HomeLayout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomeLayout extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../../../res/layout/HomeLayout.fxml"));
        primaryStage.setTitle("Fiscalia General del Estado de Durango");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("gov/fged/res/media/images/Fiscalia_Web_Logo.jpg"));
        primaryStage.show();
    }
}
