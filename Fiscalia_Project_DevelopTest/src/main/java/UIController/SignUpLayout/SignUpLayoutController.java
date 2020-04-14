package UIController.SignUpLayout;


import com.fasterxml.jackson.databind.ObjectMapper;
import dataManager.Connection;
import enrollment.Enrollment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;
import resources.ValidateSignUpForm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignUpLayoutController implements Initializable {

    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private Connection DDBBConnectionClass = Connection.getInstance();
    private ValidateSignUpForm validateSignUpFormClass = ValidateSignUpForm.getInstance();


    @FXML
    private Text signUpTitle;

    @FXML
    private TextField txtFieldFormNames;

    @FXML
    private TextField txtFieldFormLastNames;

    @FXML
    private TextField txtFieldFormRFC;

    @FXML
    private ChoiceBox<String> choiceBoxGenre;

    @FXML
    private ChoiceBox<String> choiceBoxMaritalStatus;

    @FXML
    private TextField txtFieldFormAddress;

    @FXML
    private TextField txtFieldFormEmail;

    @FXML
    private ChoiceBox<String> choiceBoxTypeUser;

    @FXML
    private Button btnEnrollFingerprint;

    @FXML
    private Button txtFieldFormSubmit;

    @FXML
    private Button txtFieldFormCancel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label RFCLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label emailLabel;


    //Administra los clics de cada uno de los botones.
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
                            choiceBoxGenre.getValue(),
                            choiceBoxMaritalStatus.getValue(),
                            choiceBoxTypeUser.getValue());

                    serializeUserObject(newUser);
                    //manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());

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

    //Serializa el objeto java en un JSON
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

    //Validate Form
    public void validateForm() {

        ArrayList<TextField> textFieldArray = new ArrayList<>();
        textFieldArray.add(txtFieldFormNames);
        textFieldArray.add(txtFieldFormLastNames);
        textFieldArray.add(txtFieldFormRFC);
        textFieldArray.add(txtFieldFormAddress);
        textFieldArray.add(txtFieldFormEmail);

        validateSignUpFormClass.setTxtFieldArray(textFieldArray);
        validateSignUpFormClass.validateForm();

    }

    //Getters y Setters
    public void setUserNames(String userName) {
        this.txtFieldFormNames.setText(userName);
    }

    public void setUserLastNames(String userLastNames) {
        this.txtFieldFormLastNames.setText(userLastNames);
    }

    public void setUserRFC(String userRFC) {
        this.txtFieldFormRFC.setText(userRFC);
        txtFieldFormRFC.setEditable(false);
    }

    public void setUserGenre(String userGenre) {
        //this.txtFieldFormGenre.setText(userGenre);
        choiceBoxGenre.setItems(FXCollections.observableArrayList(userGenre));
        choiceBoxGenre.getSelectionModel().select(0);
    }

    public void setUserMaritalStatus(String userMaritalStatus) {
        //this.txtFieldFormMaritalStatus.setText(userMaritalStatus);
        choiceBoxMaritalStatus.setItems(FXCollections.observableArrayList(userMaritalStatus));
        choiceBoxMaritalStatus.getSelectionModel().select(0);
    }

    public void setUserAddress(String userAddress) {
        this.txtFieldFormAddress.setText(userAddress);
    }

    public void setUserEmail(String userEmail) {
        this.txtFieldFormEmail.setText(userEmail);
    }

    public void setTypeUser(String typeUser) {
        //this.txtFieldFormTypeUser.setText(typeUser);
        choiceBoxTypeUser.setItems(FXCollections.observableArrayList(typeUser));
        choiceBoxTypeUser.getSelectionModel().select(0);
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

    //Validaciones
    public void manageNameLabelWarning(String warningMessage, String warningStatus) {
        if (warningStatus.equals("show")) {
            nameLabel.setText(warningMessage);
            nameLabel.setVisible(true);
        } else {
            nameLabel.setText(warningMessage);
            nameLabel.setVisible(false);
        }
    }

    public void manageLastNameLabelWarning(String warningMessage, String warningStatus) {
        if (warningStatus.equals("show")) {
            lastNameLabel.setText(warningMessage);
            lastNameLabel.setVisible(true);
        } else {
            lastNameLabel.setText(warningMessage);
            lastNameLabel.setVisible(false);
        }
    }

    public void manageRFCLabelWarning(String validationCase) {

        switch (validationCase) {
            case "emptyTextField":
                RFCLabel.setText("El campo RFC no puede estar vacío.");
                break;
            case "RFCLength":
                RFCLabel.setText("El campo no cumple con 12 caracteres.");
                break;
        }
        RFCLabel.setVisible(true);
    }

    public void hideRFCWarningMessage() {
        RFCLabel.setVisible(false);
    }

    public void manageAddressLabelWarning(String warningMessage, String warningStatus) {
        if (warningStatus.equals("show")) {
            addressLabel.setText(warningMessage);
            addressLabel.setVisible(true);
        } else {
            addressLabel.setText(warningMessage);
            addressLabel.setVisible(false);
        }
    }

    public void manageEmailLabelWarning(String warningMessage, String warningStatus) {
        if (warningStatus.equals("show")) {
            emailLabel.setText(warningMessage);
            emailLabel.setVisible(true);
        } else {
            emailLabel.setText(warningMessage);
            emailLabel.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*
        choiceBoxGenre.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        choiceBoxTypeUser.setItems(FXCollections.observableArrayList("Empleado", "Visitante"));
        choiceBoxMaritalStatus.setItems(FXCollections.observableArrayList("Casado", "Soltero"));
 */

        choiceBoxGenre.setItems(FXCollections.observableArrayList(DDBBConnectionClass.getGenres()));
        choiceBoxTypeUser.setItems(FXCollections.observableArrayList(DDBBConnectionClass.getTypeUsers()));
        choiceBoxMaritalStatus.setItems(FXCollections.observableArrayList(DDBBConnectionClass.getMaritalStatusOptions()));

        validateForm();
    }
}
