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
import java.util.ResourceBundle;

public class SignUpLayoutController implements Initializable {

    private SignUpLayoutController signUpReferenceClass = null;
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private Connection DDBBConnectionClass = Connection.getInstance();
    private ValidateSignUpForm validateSignUpFormClass = ValidateSignUpForm.getInstance();

    private ArrayList<TextField> textFieldArray = new ArrayList<>();
    private ArrayList<ChoiceBox> choiceBoxArray = new ArrayList<>();
    private ArrayList<Label> labelsArray = new ArrayList<>();

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
    private Label txtFieldFormNamesLabel;

    @FXML
    private Label txtFieldFormLastNamesLabel;

    @FXML
    private Label txtFieldFormRFCLabel;

    @FXML
    private Label txtFieldFormAddressLabel;

    @FXML
    private Label txtFieldFormEmailLabel;

    @FXML
    private Label choiceBoxGenreLabel;

    @FXML
    private Label choiceBoxMaritalStatusLabel;

    @FXML
    private Label choiceBoxTypeUserLabel;

    @FXML
    private Label btnEnrollFingerprintLabel;


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

                    if (!validateSignUpFormClass.formIsCorrect()) {
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
        validateSignUpFormClass.validateFingerprintTemplate(fingerprintTemplate, templateFingerprintImage);
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

        //Fill Labels Array
        labelsArray.add(txtFieldFormNamesLabel);
        labelsArray.add(txtFieldFormLastNamesLabel);
        labelsArray.add(txtFieldFormRFCLabel);
        labelsArray.add(choiceBoxGenreLabel);
        labelsArray.add(choiceBoxMaritalStatusLabel);
        labelsArray.add(txtFieldFormAddressLabel);
        labelsArray.add(txtFieldFormEmailLabel);
        labelsArray.add(choiceBoxTypeUserLabel);
        labelsArray.add(btnEnrollFingerprintLabel);

        validateSignUpFormClass.setLabelsArrayList(labelsArray);

        validateSignUpFormClass.validateForm();
    }

    // ----- Cargar la vista de Editar Usuario (?)

    public void setUserGenre(String userGenre) {
        choiceBoxGenre.setItems(FXCollections.observableArrayList(userGenre));
        choiceBoxGenre.getSelectionModel().select(0);
    }

    public void setUserMaritalStatus(String userMaritalStatus) {
        choiceBoxMaritalStatus.setItems(FXCollections.observableArrayList(userMaritalStatus));
        choiceBoxMaritalStatus.getSelectionModel().select(0);
    }

    public void setTypeUser(String typeUser) {
        choiceBoxTypeUser.setItems(FXCollections.observableArrayList(typeUser));
        choiceBoxTypeUser.getSelectionModel().select(0);
    }

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

    public void setUserAddress(String userAddress) {
        this.txtFieldFormAddress.setText(userAddress);
    }

    public void setUserEmail(String userEmail) {
        this.txtFieldFormEmail.setText(userEmail);
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

    //Getters y Setters

    public void setFingerprintTemplate(DPFPTemplate fingerprintTemplate) {
        this.fingerprintTemplate = fingerprintTemplate;
    }

    public void setSignUpParent(Parent parent) {
        this.signUpParent = parent;
    }

    public void setSignUpReferenceClass(SignUpLayoutController signUpReferenceClass) {

        if (signUpReferenceClass != null) {
            //System.out.println("Referencia guardada correctamente");
            this.signUpReferenceClass = signUpReferenceClass;
        } else {
            //System.out.println("La referencia a Sign Up está nula");
        }
    }

    public void hideFingerprintLabelWarning(){
        btnEnrollFingerprintLabel.setVisible(false);
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
