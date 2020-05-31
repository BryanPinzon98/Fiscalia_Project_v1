package UIController.HomeLayout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class HomeLayout extends Application {


    //Show the application main layout.
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("layout/HomeLayout.fxml")));
        primaryStage.setTitle("Fiscalia General del Estado de Durango");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
        primaryStage.show();
    }
}
