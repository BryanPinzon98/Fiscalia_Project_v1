package UIController.SignUpLayout;


import UIController.ProfileLayout.ProfileLayoutController;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dataManager.Connection;
import enrollment.Enrollment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.Genre;
import objects.MaritalStatus;
import objects.TypeUser;
import objects.User;
import resources.CameraManager;
import resources.Choice;
import resources.ManageLayout;
import resources.ValidateSignUpForm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
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

    private String fingerprintTemplateBase64String = null;
    private String fingerprintImageBase64String = null;
    private String userPhotoBase64String = null;

    private User actualUserEditMode = null;
    private Stage actualStage = null;

    @FXML
    private Text signUpTitle;

    @FXML
    private TextField txtFieldFormNames;

    @FXML
    private TextField txtFieldFormLastNames;

    @FXML
    private TextField txtFieldFormRFC;

    @FXML
    private ChoiceBox<Choice> choiceBoxGenre;

    @FXML
    private ChoiceBox<Choice> choiceBoxMaritalStatus;

    @FXML
    private TextField txtFieldFormAddress;

    @FXML
    private TextField txtFieldFormEmail;

    @FXML
    private ChoiceBox<Choice> choiceBoxTypeUser;

    @FXML
    private Button btnEnrollFingerprint;

    @FXML
    private Button btnTakePhoto;

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

    @FXML
    private Label btnTakePhotoLabel;


    //Administra los clics de cada uno de los botones.
    @FXML
    private void handleButtonClicks(MouseEvent mouseEvent) {

        Button buttonPressed = (Button) mouseEvent.getSource();

        switch (actualStage.getTitle()) {
            case "Registro Nuevo Usuario":
                System.out.println("Modo de registro");

                switch (buttonPressed.getId()) {

                    case "txtFieldFormSubmit":

                        lastValidation("Register");

                        if (!validateSignUpFormClass.formIsCorrect("register")) {
                            showIncorrectFormFilled();
                        } else {
                            createNewUser();
                        }
                        break;

                    case "txtFieldFormCancel":
                        actualStage.close();
                        break;

                    case "btnEnrollFingerprint":
                        Enrollment enrollmentProcess = new Enrollment("SIGN_UP_CLASS");
                        enrollmentProcess.setSignUpLayoutControllerClass(this.signUpReferenceClass);
                        enrollmentProcess.setVisible(true);
                        break;

                    case "btnTakePhoto":
                        cameraManagerInvoker();
                        break;
                }

                break;

            case "Editar Perfil":
                System.out.println("Modo de edición");

                switch (buttonPressed.getId()) {
                    case "txtFieldFormSubmit":
                        lastValidation("update");

                        if (!validateSignUpFormClass.formIsCorrect("update")) {
                            showIncorrectFormFilled();
                        } else {
                            updateUser();
                        }
                        break;

                    case "btnTakePhoto":
                        cameraManagerInvoker();
                        break;

                    case "txtFieldFormCancel":
                        actualStage.close();
                        loadProfileLayoutAgain();
                        break;
                }
                break;
        }
    }

    //Camera functionality.
    private void cameraManagerInvoker() {
        FXMLLoader FXMLCameraLayoutController = manageLayoutClass.loadLayout("layout/CameraLayout.fxml", "Camera", true);

        CameraManager cameraManagerController = FXMLCameraLayoutController.getController();
        cameraManagerController.setCameraManagerStage(manageLayoutClass.getStage());
        cameraManagerController.setSignUpLayoutController(signUpReferenceClass);
        cameraManagerController.exitStageHandler();

        selectCameraAlertMessage();
    }

    private void selectCameraAlertMessage() {
        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert userCreatedAlert = new Alert(Alert.AlertType.CONFIRMATION, "Seleccione una cámara de la lista.", ACCEPT_BUTTON);
        userCreatedAlert.setTitle("¡Atención!");
        userCreatedAlert.setHeaderText(null);
        userCreatedAlert.showAndWait();
    }

    private void createNewUser() {
        User newUser = new User(
                0,
                txtFieldFormRFC.getText(),
                txtFieldFormNames.getText(),
                txtFieldFormLastNames.getText(),
                txtFieldFormAddress.getText(),
                txtFieldFormEmail.getText(),
                null,
                choiceBoxGenre.getValue().getId(),
                choiceBoxMaritalStatus.getValue().getId(),
                choiceBoxTypeUser.getValue().getId(),
                fingerprintTemplateBase64String,
                userPhotoBase64String);

        String JSONUserCreated = serializeUserObject(newUser, "CREATE");
        DDBBConnectionClass.POST_REQUEST(JSONUserCreated);

        userCreatedSuccessfully();
    }

    private void userCreatedSuccessfully() {
        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert userCreatedAlert = new Alert(Alert.AlertType.CONFIRMATION, "¡Usuario creado con éxito!", ACCEPT_BUTTON);
        userCreatedAlert.setTitle("Atención");
        userCreatedAlert.setHeaderText(null);
        userCreatedAlert.showAndWait();

        switch (userCreatedAlert.getResult().getText()) {
            case "Aceptar":
                actualStage.close();
                break;
        }
    }

    private void showIncorrectFormFilled() {
        Alert incorrectFormAlert = new Alert(Alert.AlertType.ERROR);
        incorrectFormAlert.setTitle("¡Alerta!");
        incorrectFormAlert.setHeaderText(null);
        incorrectFormAlert.setContentText("Realice las acciones pertinentes con los campos en color rojo.");
        incorrectFormAlert.showAndWait();
    }

    private void updateUser() {

        //Get new Data
        User userUpdated = new User(
                actualUserEditMode.getId_usuario(),
                actualUserEditMode.getRfc_usuario(),
                txtFieldFormNames.getText(),
                txtFieldFormLastNames.getText(),
                txtFieldFormAddress.getText(),
                txtFieldFormEmail.getText(),
                null,
                choiceBoxGenre.getValue().getId(),
                choiceBoxMaritalStatus.getValue().getId(),
                choiceBoxTypeUser.getValue().getId());

        String JSONUserUpdated = serializeUserObject(userUpdated, "UPDATE");
        //System.out.println(JSONUserUpdated);
        DDBBConnectionClass.UPDATE_REQUEST(JSONUserUpdated, actualUserEditMode.getId_usuario());

        if (userPhotoBase64String != null) {
            DDBBConnectionClass.POST_PHOTO_USER(serializePhotoUser(userPhotoBase64String, actualUserEditMode.getId_usuario()));
        }

        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert verifiedResponseAlert = new Alert(Alert.AlertType.CONFIRMATION, "Usuario actualizado con éxito", ACCEPT_BUTTON);
        verifiedResponseAlert.setTitle("Mensaje");
        verifiedResponseAlert.setHeaderText(null);
        verifiedResponseAlert.showAndWait();

        switch (verifiedResponseAlert.getResult().getText()){
            case "Aceptar":

                actualStage.close();
                loadProfileLayoutAgain();

                break;
        }
    }

    private void loadProfileLayoutAgain() {
        FXMLLoader fxmlLoaderProfileLayout = manageLayoutClass.loadLayout("layout/ProfileLayout.fxml", "Perfil", true);
        ProfileLayoutController profileLayoutController = fxmlLoaderProfileLayout.getController();


        profileLayoutController.setActualUser(DDBBConnectionClass.getOneUser(actualUserEditMode.getId_usuario()));
        profileLayoutController.setActualStage(manageLayoutClass.getStage());
        profileLayoutController.setActualInstance(profileLayoutController);


        //Envia los datos a la ventana de perfil
        profileLayoutController.setUpLayout();
    }

    public void setUpLayoutEditMode() {

        this.txtFieldFormNames.setText(actualUserEditMode.getNombres_usuario());
        this.txtFieldFormLastNames.setText(actualUserEditMode.getApellidos_usuario());
        this.txtFieldFormRFC.setText(actualUserEditMode.getRfc_usuario());
        this.txtFieldFormRFC.setEditable(false);
        //this.choiceBoxGenre
        populateGenreChoiceBox(actualUserEditMode.getId_genero());
        populateMaritalStatusChoiceBox(actualUserEditMode.getId_estado_civil());
        populateTypeUserChoiceBoxOptions(actualUserEditMode.getId_tipo_usuario());
        //this.choiceBoxMaritalStatus
        //this.choiceBoxTypeUser
        this.txtFieldFormAddress.setText(actualUserEditMode.getDireccion_usuario());
        this.txtFieldFormEmail.setText(actualUserEditMode.getCorreo_usuario());
    }

    public void lastValidation(String buttonInvoke) {

        for (TextField textField : textFieldArray) {
            if (textField.getId().equals("txtFieldFormNames") || textField.getId().equals("txtFieldFormLastNames")) {
                validateSignUpFormClass.validateBasicFields(textField);
            }
        }

        for (ChoiceBox choiceBox : choiceBoxArray) {
            validateSignUpFormClass.validateEmptyChoiceBox(choiceBox);
        }

        validateSignUpFormClass.validateEmail(txtFieldFormEmail);
        validateSignUpFormClass.validateAddressField(txtFieldFormAddress);

        if (buttonInvoke.equals("Register")) {
            validateSignUpFormClass.validateRFC(txtFieldFormRFC);
            validateSignUpFormClass.validateFingerprintTemplate(fingerprintTemplateBase64String, fingerprintImageBase64String);
            validateSignUpFormClass.validateUserPhotoBase64(userPhotoBase64String);

        }
    }

    private String serializePhotoUser(String base64Photo, int userID) {
        return "{\"archivo_foto\": \"" + base64Photo + "\", \"id_usuario\"" + ":" + userID + "}";
    }

    //Serializa el objeto java en un JSON
    private String serializeUserObject(User userCreated, String httpInvoker) {

        ObjectMapper objectMapper = new ObjectMapper();
        String userParseJSON = null;
        FilterProvider filters = null;

        if (httpInvoker.equals("UPDATE")) {
            String[] ignoreFields = {"archivo_huella", "imagen_huella", "archivo_foto"};
            SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields);
            filters = new SimpleFilterProvider().addFilter("updateUserJSONFilter", theFilter);
        }

        try {
            //objectMapper.writeValue(new File("target/user.json"), userCreated);
            userParseJSON = objectMapper.writer(filters).writeValueAsString(userCreated);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userParseJSON;
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
        labelsArray.add(btnTakePhotoLabel);

        validateSignUpFormClass.setLabelsArrayList(labelsArray);

        validateSignUpFormClass.validateForm();
    }

    // ----- Cargar la vista de Editar Usuario (?)

    public void setSignUpTitle(String signUpTitle) {
        this.signUpTitle.setText(signUpTitle);
    }

    public void setSubmitButtonTitle(String txtFieldFormSubmit) {
        this.txtFieldFormSubmit.setText(txtFieldFormSubmit);
    }

    public void setVisibleEnrollFingerprint(Boolean choiceVisible) {
        btnEnrollFingerprint.setVisible(choiceVisible);
    }


    //Getters y Setters

    public void setFingerprintTemplate(DPFPTemplate fingerprintTemplate) {
        this.fingerprintTemplate = fingerprintTemplate;
    }

    public void setSignUpParent(Parent parent) {
        this.signUpParent = parent;
    }

    public void setActualUserEditMode(User actualUserEditMode) {
        this.actualUserEditMode = actualUserEditMode;
    }

    public void setSignUpReferenceClass(SignUpLayoutController signUpReferenceClass) {

        if (signUpReferenceClass != null) {
            //System.out.println("Referencia guardada correctamente");
            this.signUpReferenceClass = signUpReferenceClass;
        } else {
            //System.out.println("La referencia a Sign Up está nula");
        }
    }

    public void hideFingerprintLabelWarning() {
        btnEnrollFingerprintLabel.setVisible(false);
    }

    public void hideUserPhotoLabelWarning() {
        btnTakePhotoLabel.setVisible(false);
    }

    public void convertImageToFile(String imageType, BufferedImage imagen) {
        switch (imageType) {
            case "fingerprintImage":
                ByteArrayOutputStream fingerprintImageByteArray = new ByteArrayOutputStream();
                try {
                    ImageIO.write(imagen, "png", fingerprintImageByteArray);
                    fingerprintImageBase64String = Base64.getEncoder().encodeToString(fingerprintImageByteArray.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "userPhoto":
                ByteArrayOutputStream userPhotoByteArray = new ByteArrayOutputStream();
                try {
                    ImageIO.write(imagen, "png", userPhotoByteArray);
                    userPhotoBase64String = Base64.getEncoder().encodeToString(userPhotoByteArray.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void convertDPFPTemplateToBase64(DPFPTemplate fingerprintTemplate) {

        if (fingerprintTemplate != null) {
            fingerprintTemplateBase64String = Base64.getEncoder().encodeToString(fingerprintTemplate.serialize());
        }
    }

    private void populateMaritalStatusChoiceBox(int defaultOptionSelected) {
        ObservableList<Choice> maritalStatusChoiceOptions = FXCollections.observableArrayList();
        Choice defaultOption = null;

        for (MaritalStatus maritalStatusObjectOption : DDBBConnectionClass.getMaritalStatusArrayOptions()) {

            if (defaultOptionSelected == maritalStatusObjectOption.getId_estado_civil()) {
                defaultOption = new Choice(maritalStatusObjectOption.getId_estado_civil(), maritalStatusObjectOption.getNombre_estado_civil());
            }
            maritalStatusChoiceOptions.add(new Choice(maritalStatusObjectOption.getId_estado_civil(), maritalStatusObjectOption.getNombre_estado_civil()));
        }
        choiceBoxMaritalStatus.setItems(maritalStatusChoiceOptions);
        choiceBoxMaritalStatus.getSelectionModel().select(defaultOption);
    }

    private void populateGenreChoiceBox(int defaultOptionSelected) {
        ObservableList<Choice> genreChoiceBoxOptions = FXCollections.observableArrayList();
        Choice defaultOption = null;

        for (Genre genreObjectOption : DDBBConnectionClass.getGenreArrayOptions()) {
            if (defaultOptionSelected == genreObjectOption.getId_genero()) {
                defaultOption = new Choice(genreObjectOption.getId_genero(), genreObjectOption.getNombre_genero());
            }
            genreChoiceBoxOptions.add(new Choice(genreObjectOption.getId_genero(), genreObjectOption.getNombre_genero()));
        }
        choiceBoxGenre.setItems(genreChoiceBoxOptions);
        choiceBoxGenre.getSelectionModel().select(defaultOption);
    }

    private void populateTypeUserChoiceBoxOptions(int defaultOptionSelected) {
        ObservableList<Choice> typeUserChoiceBoxOptions = FXCollections.observableArrayList();
        Choice defaultOption = null;

        for (TypeUser typeUserObjectOption : DDBBConnectionClass.getTypeUserArrayOptions()) {
            if (defaultOptionSelected == typeUserObjectOption.getId_tipos_usuario()) {
                defaultOption = new Choice(typeUserObjectOption.getId_tipos_usuario(), typeUserObjectOption.getNombre_tipos_usuario());
            }
            typeUserChoiceBoxOptions.add(new Choice(typeUserObjectOption.getId_tipos_usuario(), typeUserObjectOption.getNombre_tipos_usuario()));
        }
        choiceBoxTypeUser.setItems(typeUserChoiceBoxOptions);
        choiceBoxTypeUser.getSelectionModel().select(defaultOption);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*
        choiceBoxGenre.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        choiceBoxTypeUser.setItems(FXCollections.observableArrayList("Empleado", "Visitante"));
        choiceBoxMaritalStatus.setItems(FXCollections.observableArrayList("Casado", "Soltero"));
 */

        populateGenreChoiceBox(0);
        populateMaritalStatusChoiceBox(0);
        populateTypeUserChoiceBoxOptions(0);

        validateForm();
    }

    public void setActualStage(Stage actualStage) {
        this.actualStage = actualStage;
    }
}