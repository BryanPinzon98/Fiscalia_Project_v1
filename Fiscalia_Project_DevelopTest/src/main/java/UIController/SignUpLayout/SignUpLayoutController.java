package UIController.SignUpLayout;


import com.digitalpersona.onetouch.DPFPTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataManager.Connection;
import enrollment.Enrollment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.User;
import resources.ManageLayout;
import resources.ValidateSignUpForm;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SignUpLayoutController implements Initializable {

    private SignUpLayoutController signUpReferenceClass = null;
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private Connection DDBBConnectionClass = Connection.getInstance();
    private ValidateSignUpForm validateSignUpFormClass = ValidateSignUpForm.getInstance();

    private ArrayList<TextField> textFieldArray = new ArrayList<>();
    private ArrayList<ChoiceBox> choiceBoxArray = new ArrayList<>();
    private Parent signUpParent = null;
    private DPFPTemplate fingerprintTemplate = null;
    private Image templateFingerprintImage = null;


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

    @FXML
    private Label genreLabel;

    @FXML
    private Label maritalStatusLabel;

    @FXML
    private Label typeUserLabel;

    @FXML
    private Label fingerprintLabel;


    //Administra los clics de cada uno de los botones.
    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        if (buttonPressed.getText().equals("Aceptar")) {
            System.out.println("Se cambió al modo de edición");
        } else {

            switch (buttonPressed.getId()) {

                case "txtFieldFormSubmit":

                    if (fingerprintTemplate != null) {
                        System.out.println("El archivo .fpt no está vacío y está listo para enviar.");
                    }

                    if (templateFingerprintImage != null) {
                        System.out.println("La referencia a la imágen no está vacía y está lista para enviar.");
                        System.out.println("Referencia final de la imagen template: " + templateFingerprintImage);
                    }

                    System.out.println("Submit button pressed");

                    lastValidation();
                    boolean formIsCorrect = formIsCorrect(validateSignUpFormClass.getFieldsStatus());

                    if (!formIsCorrect) {
                        Alert incorrectFormAlert = new Alert(Alert.AlertType.ERROR);
                        incorrectFormAlert.setTitle("¡Alerta!");
                        incorrectFormAlert.setHeaderText(null);
                        incorrectFormAlert.setContentText("Realice las acciones pertinentes con los campos en rojo.");
                        incorrectFormAlert.showAndWait();

                    } else {

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

                    }
                    break;

                case "txtFieldFormCancel":
                    System.out.println("Cancel button pressed");
                    manageLayoutClass.closeLayout((Stage) buttonPressed.getScene().getWindow());
                    break;

                case "btnEnrollFingerprint":
                    Enrollment enrollmentProcess = new Enrollment();
                    enrollmentProcess.setSignUpLayoutControllerClass(this.signUpReferenceClass);
                    enrollmentProcess.setVisible(true);

                    break;
            }
        }
    }

    public void lastValidation() {

        for (TextField textField : textFieldArray) {
            validateSignUpFormClass.validateEmptyForm(textField);
        }

        for (ChoiceBox choiceBox : choiceBoxArray) {
            validateSignUpFormClass.validateEmptyChoiceBox(choiceBox);
        }

        validateSignUpFormClass.validateRFC(txtFieldFormRFC);

        validateSignUpFormClass.validateFingerprintTemplate(fingerprintTemplate);

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

        //Fill TextField Array
        textFieldArray.add(txtFieldFormNames);
        textFieldArray.add(txtFieldFormLastNames);
        textFieldArray.add(txtFieldFormRFC);
        textFieldArray.add(txtFieldFormAddress);
        textFieldArray.add(txtFieldFormEmail);

        validateSignUpFormClass.setTxtFieldArray(textFieldArray);

        //Fill ChoiceBox Array
        choiceBoxArray.add(choiceBoxGenre);
        choiceBoxArray.add(choiceBoxMaritalStatus);
        choiceBoxArray.add(choiceBoxTypeUser);

        validateSignUpFormClass.setChoiceBoxArrayList(choiceBoxArray);

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

    public void setTemplateFingerprintImage(Image templateFingerprintImage) {
        this.templateFingerprintImage = templateFingerprintImage;
    }

    public Label getFingerprintLabel() {
        return fingerprintLabel;
    }

    public void setSignUpReferenceClass(SignUpLayoutController signUpReferenceClass) {

        if (signUpReferenceClass != null) {
            //System.out.println("Referencia guardada correctamente");
            this.signUpReferenceClass = signUpReferenceClass;
        } else {
            //System.out.println("La referencia a Sign Up está nula");
        }

    }

    public void setFingerprintTemplate(DPFPTemplate fingerprintTemplate) {
        this.fingerprintTemplate = fingerprintTemplate;
    }

    //Validaciones

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

    public void manageEmailLabelWarning(String warningMessage, String warningStatus) {
        if (warningStatus.equals("show")) {
            emailLabel.setText(warningMessage);
            emailLabel.setVisible(true);
        } else {
            emailLabel.setText(warningMessage);
            emailLabel.setVisible(false);
        }
    }

    public void manageGeneralLabelWarning(String fieldID, String warningMessage, String warningStatus) {

        if (warningStatus.equals("show")) {

            switch (fieldID) {
                case "txtFieldFormNames":
                    nameLabel.setText(warningMessage);
                    nameLabel.setVisible(true);
                    break;
                case "txtFieldFormLastNames":
                    lastNameLabel.setText(warningMessage);
                    lastNameLabel.setVisible(true);
                    break;
                case "choiceBoxGenre":
                    genreLabel.setText(warningMessage);
                    genreLabel.setVisible(true);
                    break;
                case "choiceBoxMaritalStatus":
                    maritalStatusLabel.setText(warningMessage);
                    maritalStatusLabel.setVisible(true);
                    break;
                case "txtFieldFormAddress":
                    addressLabel.setText(warningMessage);
                    addressLabel.setVisible(true);
                    break;
                case "choiceBoxTypeUser":
                    typeUserLabel.setText(warningMessage);
                    typeUserLabel.setVisible(true);
                    break;
            }

        } else {

            switch (fieldID) {
                case "txtFieldFormNames":
                    nameLabel.setText(warningMessage);
                    nameLabel.setVisible(false);
                    break;
                case "txtFieldFormLastNames":
                    lastNameLabel.setText(warningMessage);
                    lastNameLabel.setVisible(false);
                    break;
                case "choiceBoxGenre":
                    genreLabel.setText(warningMessage);
                    genreLabel.setVisible(false);
                    break;
                case "choiceBoxMaritalStatus":
                    maritalStatusLabel.setText(warningMessage);
                    maritalStatusLabel.setVisible(false);
                    break;
                case "txtFieldFormAddress":
                    addressLabel.setText(warningMessage);
                    addressLabel.setVisible(false);
                    break;
                case "choiceBoxTypeUser":
                    typeUserLabel.setText(warningMessage);
                    typeUserLabel.setVisible(false);
                    break;
            }
        }
    }

    public boolean formIsCorrect(HashMap<String, Boolean> fieldsStatusHashMap) {

        boolean formIsCorrect = false;

        boolean isEmptyNameField = false;
        boolean isEmptyLastNameField = false;
        boolean isCorrectRFCField = false;
        boolean isEmptyGenreField = false;
        boolean isEmptyMaritalStatus = false;
        boolean isEmptyAddress = false;
        boolean isCorrectEmail = false;
        boolean isEmptyTypeUser = false;
        boolean fingerprintTemplateIsReady = false;
        boolean fingerprintTemplateImageIsReady = false;

        for (HashMap.Entry<String, Boolean> field : fieldsStatusHashMap.entrySet()) {

            switch (field.getKey()) {
                case "txtFieldFormNames":
                    isEmptyNameField = field.getValue();
                    break;
                case "txtFieldFormLastNames":
                    isEmptyLastNameField = field.getValue();
                    break;
                case "txtFieldFormRFC":
                    isCorrectRFCField = field.getValue();
                    break;
                case "choiceBoxGenre":
                    isEmptyGenreField = field.getValue();
                    break;
                case "choiceBoxMaritalStatus":
                    isEmptyMaritalStatus = field.getValue();
                    break;
                case "txtFieldFormAddress":
                    isEmptyAddress = field.getValue();
                    break;
                case "txtFieldFormEmail":
                    isCorrectEmail = field.getValue();
                    break;
                case "choiceBoxTypeUser":
                    isEmptyTypeUser = field.getValue();
                    break;
            }
        }

        if (fingerprintTemplate != null) {
            fingerprintTemplateIsReady = true;
            //fingerprintLabel.setVisible(false);
        }

        if (templateFingerprintImage != null) {
            fingerprintTemplateImageIsReady = true;
        }

        if (!isEmptyNameField && !isEmptyLastNameField && isCorrectRFCField && !isEmptyGenreField && !isEmptyMaritalStatus && !isEmptyAddress && isCorrectEmail && !isEmptyTypeUser && fingerprintTemplateIsReady && fingerprintTemplateImageIsReady) {
            formIsCorrect = true;
        } else {
            formIsCorrect = false;
        }

        return formIsCorrect;
    }

    public void setSignUpParent(Parent parent) {
        this.signUpParent = parent;
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
