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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUserFormData();
    }

    //Manage clicks from main layout.
    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (buttonPressed.getId()) {

            //New User button implementation.
            case "btnNewUser":
                FXMLLoader signUpLoader = loadLayoutClass.loadLayout("layout/SignUpLayout.fxml", "Registro Nuevo Usuario", true);

                SignUpLayoutController signUpController = signUpLoader.getController();
                signUpController.setSignUpParent(loadLayoutClass.getParent());
                signUpController.setSignUpReferenceClass(signUpController);
                signUpController.setActualStage(loadLayoutClass.getStage());
                break;

            //Search User button implementation.
            case "btnSearchUser":
                FXMLLoader searchUserLoader = loadLayoutClass.loadLayout("layout/UserSearchInputLayout.fxml", "Buscar Usuario", true);

                UserSearchInputController searchInputController = searchUserLoader.getController();
                searchInputController.setActualStage(loadLayoutClass.getStage());
                break;
        }
    }

    private void getUserFormData() {
        DDBBConectionClass.loadGenreChoiceBoxOptions();
        DDBBConectionClass.loadMaritalStatusChoiceBoxOptions();
        DDBBConectionClass.loadTypeUserChoiceBoxOptions();
    }
}
