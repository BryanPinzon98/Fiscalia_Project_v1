package gov.fged.java.UIController.HomeLayout;

import gov.fged.java.resources.ManageLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeLayoutController implements Initializable {

    private ManageLayout loadLayoutClass = ManageLayout.getInstance();

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
                loadLayoutClass.loadLayout("gov/fged/res/layout/SignUpLayout.fxml", "Registro");
                break;

            case "btnSearchUser":
                System.out.println("Search User Button Pressed");
                loadLayoutClass.loadLayout("gov/fged/res/layout/UserSearchInput.fxml", "Buscar");
                break;
        }
    }

}
