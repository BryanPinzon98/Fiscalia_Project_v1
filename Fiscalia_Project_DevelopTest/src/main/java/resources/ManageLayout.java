package gov.fged.java.resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ManageLayout {

    private static ManageLayout loadLayout = null;

    private ManageLayout() {
    }

    public static ManageLayout getInstance() {
        if (loadLayout == null) {
            loadLayout = new ManageLayout();
        }
        return loadLayout;
    }

    public void loadLayout(String fxmlPath, String layoutTitle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlPath)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(layoutTitle);
            stage.setResizable(false);
            stage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeLayout(Stage stage) {
        stage.close();
    }
}
