package UIController.HomeLayout;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import resources.ManageLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeLayoutController implements Initializable {

    private ManageLayout loadLayoutClass = ManageLayout.getInstance();

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnSearchUser;

    //Administra los clics de los dos botones de la ventana principal del programa.
    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (buttonPressed.getId()) {

            case "btnNewUser":
                System.out.println("New User Button Pressed");
                FXMLLoader signUpController = loadLayoutClass.loadLayout("layout/SignUpLayout.fxml", "Registro", true);
                SignUpLayoutController signUpControllerClass = signUpController.getController();
                signUpControllerClass.setSignUpParent(loadLayoutClass.getParent());
                break;

            case "btnSearchUser":
                System.out.println("Search User Button Pressed");
                loadLayoutClass.loadLayout("layout/UserSearchInputLayout.fxml", "Buscar", true);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
