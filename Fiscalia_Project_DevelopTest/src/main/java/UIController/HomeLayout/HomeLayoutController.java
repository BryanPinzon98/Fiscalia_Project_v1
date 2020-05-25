package UIController.HomeLayout;

import UIController.ProfileLayout.UserSearchInputController;
import UIController.SignUpLayout.SignUpLayoutController;
import dataManager.Connection;
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
    private Connection DDBBConectionClass = Connection.getInstance();

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
                signUpControllerClass.setSignUpReferenceClass(signUpControllerClass);
                break;

            case "btnSearchUser":
                System.out.println("Search User Button Pressed");
                FXMLLoader searchUserLoader = loadLayoutClass.loadLayout("layout/UserSearchInputLayout.fxml", "Buscar", true);
                UserSearchInputController searchInputController = searchUserLoader.getController();

                searchInputController.setActualStage(loadLayoutClass.getStage());
                break;
        }
    }

    private void getData() {
        DDBBConectionClass.loadGenreChoiceBoxOptions();
        DDBBConectionClass.loadMaritalStatusChoiceBoxOptions();
        DDBBConectionClass.loadTypeUserChoiceBoxOptions();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
