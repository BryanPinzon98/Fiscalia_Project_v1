package UIController.ProfileLayout;

import UIController.UserItemLayout.UsersListLogicController;
import dataManager.Connection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserSearchInputController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private Stage actualStage = null;

    @FXML
    private TextField txtSearchUserNames;

    @FXML
    private TextField txtSearchLastNames;

    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (buttonPressed.getId()) {

            case "btnSearchDataUser":
                System.out.println("Botón de búsqueda presionado");

                Connection connectionClass = Connection.getInstance();
                ArrayList<User> foundUsers = connectionClass.getUsers(txtSearchUserNames.getText(), txtSearchLastNames.getText());

                UsersListLogicController usersListLogicController = UsersListLogicController.getInstance();
                usersListLogicController.setFoundUsers(foundUsers);
                usersListLogicController.loadRootContainer();

                actualStage.close();

                break;

            case "btnCancelSearchDataUser":
                System.out.println("Botón de cancelar presionado");
                manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setActualStage(Stage actualStage) {
        this.actualStage = actualStage;
    }
}
