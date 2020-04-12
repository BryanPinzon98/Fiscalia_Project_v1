package resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageLayout {

    private static ManageLayout loadLayout = null;
    private Stage stage = null;
    private Parent pane = null;
    private FXMLLoader fxmlLoader = null;

    private ManageLayout() {
    }

    public static ManageLayout getInstance() {
        if (loadLayout == null) {
            loadLayout = new ManageLayout();
        }
        return loadLayout;
    }

    public FXMLLoader loadLayout(String fxmlPath, String layoutTitle, boolean show) {

        try {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            stage = new Stage();
            pane = fxmlLoader.load();
            stage.setScene(new Scene(pane));


            if (show) {
                stage.setTitle(layoutTitle);
                stage.setResizable(false);
                stage.getIcons().add(new Image("images/Fiscalia_Web_Logo.jpg"));
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader;
    }

    public void closeLayout(Stage stage) {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public Parent getParent() {
        return pane;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }
}
