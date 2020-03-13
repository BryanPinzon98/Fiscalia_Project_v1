package UIController.SignUpLayout;


import com.fasterxml.jackson.databind.ObjectMapper;
import enrollment.Enrollment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();

    @FXML
    private Text signUpTitle;

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
    private Button btnEnrollFingerprint;


    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        if (buttonPressed.getText().equals("Aceptar")) {
            System.out.println("Se cambió al modo de edición");
        } else {

            switch (buttonPressed.getId()) {

                case "txtFieldFormSubmit":

                    System.out.println("Submit button pressed");

                    User newUser = new User(
                            0,
                            txtFieldFormRFC.getText(),
                            txtFieldFormNames.getText(),
                            txtFieldFormLastNames.getText(),
                            txtFieldFormAddress.getText(),
                            txtFieldFormEmail.getText(),
                            "",
                            txtFieldFormGenre.getText(),
                            txtFieldFormMaritalStatus.getText(),
                            txtFieldFormTypeUser.getText());

                    serializeUserObject(newUser);
                    break;

                case "txtFieldFormCancel":
                    System.out.println("Cancel button pressed");
                    manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                    break;

                case "btnEnrollFingerprint":
                    Enrollment enrollmentProcess = new Enrollment();
                    enrollmentProcess.setVisible(true);
                    break;
            }
        }

    }

    private void serializeUserObject(User userCreated) {

        ObjectMapper objectMapper = new ObjectMapper();
        String userParseJSON = null;

        try {
            objectMapper.writeValue(new File("target/user.json"), userCreated);
            userParseJSON = objectMapper.writeValueAsString(userCreated);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(userParseJSON);
        //Conectarse a la BBDD y enviar el JSON
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setUserNames(String userName) {
        this.txtFieldFormNames.setText(userName);
    }

    public void setUserLastNames(String userLastNames) {
        this.txtFieldFormLastNames.setText(userLastNames);
    }

    public void setUserRFC(String userRFC) {
        this.txtFieldFormRFC.setText(userRFC);
    }

    public void setUserGenre(String userGenre) {
        this.txtFieldFormGenre.setText(userGenre);
    }

    public void setUserMaritalStatus(String userMaritalStatus) {
        this.txtFieldFormMaritalStatus.setText(userMaritalStatus);
    }

    public void setUserAddress(String userAddress) {
        this.txtFieldFormAddress.setText(userAddress);
    }

    public void setUserEmail(String userEmail) {
        this.txtFieldFormEmail.setText(userEmail);
    }

    public void setTypeUser(String typeUser) {
        this.txtFieldFormTypeUser.setText(typeUser);
    }

    public void setSignUpTitle(String signUpTitle) {
        this.signUpTitle.setText(signUpTitle);
    }

    public void setSubmitButtonTitle(String txtFieldFormSubmit) {
        this.txtFieldFormSubmit.setText(txtFieldFormSubmit);
    }

    public void setVisibleEnrollFingerprint(Boolean choiceVisible) {
        btnEnrollFingerprint.setVisible(choiceVisible);
    }
}
