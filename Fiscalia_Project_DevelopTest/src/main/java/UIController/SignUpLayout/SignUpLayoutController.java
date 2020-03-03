package UIController.SignUpLayout;


import resources.ManageLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();

    @FXML
    private TextField txtFieldFormNames;

    @FXML
    private TextField txtFieldFormLastNames;

    @FXML
    private TextField txtFieldFormRFC;

    @FXML
    private TextField txtFieldFormGenre;

    @FXML
    private TextField txtFieldFormMaritalStatus;

    @FXML
    private TextField txtFieldFormAddress;

    @FXML
    private TextField txtFieldFormEmail;

    @FXML
    private TextField txtFieldFormTypeUser;

    @FXML
    private Button txtFieldFormSubmit;

    @FXML
    private Button txtFieldFormCancel;


    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (buttonPressed.getId()) {

            case "txtFieldFormSubmit":

                System.out.println("Submit button pressed");

                /*
                User newUser = new User(
                        txtFieldFormNames.getText(),
                        txtFieldFormLastNames.getText(),
                        txtFieldFormRFC.getText(),
                        txtFieldFormGenre.getText(),
                        txtFieldFormMaritalStatus.getText(),
                        txtFieldFormAddress.getText(),
                        txtFieldFormEmail.getText(),
                        txtFieldFormTypeUser.getText());
                 */
                break;

            case "txtFieldFormCancel":
                System.out.println("Cancel button pressed");
                manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                break;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
