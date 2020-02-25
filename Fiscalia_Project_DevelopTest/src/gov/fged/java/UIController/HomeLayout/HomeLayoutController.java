package gov.fged.java.UIController.HomeLayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeLayoutController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnSearchUser;

    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (buttonPressed.getId()) {

            case "btnNewUser":
                System.out.println("New User Button Pressed");
                loadLayout("../../../res/layout/SignUpLayout.fxml", "SignUp");
                break;

            case "btnSearchUser":
                System.out.println("Search User Button Pressed");
                loadLayout("../../../res/layout/ProfileLayout.fxml", "Profile");
                break;
        }
    }

    private void loadLayout(String fxmlPath, String layoutTitle) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(layoutTitle);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
