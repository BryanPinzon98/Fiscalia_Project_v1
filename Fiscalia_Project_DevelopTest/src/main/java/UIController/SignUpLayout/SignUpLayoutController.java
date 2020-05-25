package UIController.SignUpLayout;


import com.digitalpersona.onetouch.DPFPTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.*;
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

    //private File fingerprintImageFile = null;
    //private File userPhotoFile = null;
    private String fingerprintTemplateBase64String = null;
    private String fingerprintImageBase64String = null;
    private String userPhotoBase64String = null;

    private User actualUserEditMode = null;


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

        if (buttonPressed.getText().equals("Aceptar")) {
            System.out.println("Se cambió al modo de edición");
            setUpLayoutEditMode();
        } else {

            switch (buttonPressed.getId()) {

                case "txtFieldFormSubmit":

                    if (fingerprintTemplate != null) {
                        System.out.println("El archivo .fpt no está vacío y está listo para enviar.");
                    }

                    System.out.println("Submit button pressed");

                    lastValidation();

                    if (!validateSignUpFormClass.formIsCorrect()) {

                        Alert incorrectFormAlert = new Alert(Alert.AlertType.ERROR);
                        incorrectFormAlert.setTitle("¡Alerta!");
                        incorrectFormAlert.setHeaderText(null);
                        incorrectFormAlert.setContentText("Realice las acciones pertinentes con los campos en color rojo.");
                        incorrectFormAlert.showAndWait();

                    } else {

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

                case "btnTakePhoto":
                    cameraManagerInvoker();
                    break;
            }
        }
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

    private void cameraManagerInvoker() {
        FXMLLoader FXMLCameraLayoutController = manageLayoutClass.loadLayout("layout/CameraLayout.fxml", "Camera", true);

        CameraManager cameraManagerController = FXMLCameraLayoutController.getController();
        cameraManagerController.setCameraManagerStage(manageLayoutClass.getStage());
        cameraManagerController.setSignUpLayoutController(signUpReferenceClass);
        cameraManagerController.exitStageHandler();
    }

    public void lastValidation() {

        for (TextField textField : textFieldArray) {
            if (textField.getId().equals("txtFieldFormNames") || textField.getId().equals("txtFieldFormLastNames")) {
                validateSignUpFormClass.validateBasicFields(textField);
            }
        }

        for (ChoiceBox choiceBox : choiceBoxArray) {
            validateSignUpFormClass.validateEmptyChoiceBox(choiceBox);
        }

        validateSignUpFormClass.validateRFC(txtFieldFormRFC);
        validateSignUpFormClass.validateEmail(txtFieldFormEmail);
        validateSignUpFormClass.validateAddressField(txtFieldFormAddress);
        validateSignUpFormClass.validateFingerprintTemplate(fingerprintTemplateBase64String, fingerprintImageBase64String);
        validateSignUpFormClass.validateUserPhotoBase64(userPhotoBase64String);
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

        //System.out.println(userParseJSON);

/*
        PrintWriter stringTxtFile = null;
        try {
            stringTxtFile = new PrintWriter("UserJSON.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        stringTxtFile.println(userParseJSON);
 */

        DDBBConnectionClass.POST_REQUEST(userParseJSON);


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

        int i = 0;
        for (MaritalStatus maritalStatusObjectOption : DDBBConnectionClass.getMaritalStatusArrayOptions()) {

            if (defaultOptionSelected == maritalStatusObjectOption.getId_estado_civil()) {
                choiceBoxGenre.getSelectionModel().select(i);
            }
            maritalStatusChoiceOptions.add(new Choice(maritalStatusObjectOption.getId_estado_civil(), maritalStatusObjectOption.getNombre_estado_civil()));
            i++;
        }
        choiceBoxMaritalStatus.setItems(maritalStatusChoiceOptions);
    }

    private void populateGenreChoiceBox(int defaultOptionSelected) {
        ObservableList<Choice> genreChoiceBoxOptions = FXCollections.observableArrayList();

        int i = 0;
        for (Genre genreObjectOption : DDBBConnectionClass.getGenreArrayOptions()) {
            if (defaultOptionSelected == genreObjectOption.getId_genero()) {
                choiceBoxGenre.getSelectionModel().select(i);
            }
            genreChoiceBoxOptions.add(new Choice(genreObjectOption.getId_genero(), genreObjectOption.getNombre_genero()));
            i++;
            System.out.println(i);
        }
        choiceBoxGenre.setItems(genreChoiceBoxOptions);
    }

    private void populateTypeUserChoiceBoxOptions(int defaultOptionSelected) {
        ObservableList<Choice> typeUserChoiceBoxOptions = FXCollections.observableArrayList();

        int i = 0;
        for (TypeUser typeUserObjectOption : DDBBConnectionClass.getTypeUserArrayOptions()) {
            if (defaultOptionSelected == typeUserObjectOption.getId_tipos_usuario()) {
                choiceBoxGenre.getSelectionModel().select(i);
            }
            typeUserChoiceBoxOptions.add(new Choice(typeUserObjectOption.getId_tipos_usuario(), typeUserObjectOption.getNombre_tipos_usuario()));
            i++;
        }
        choiceBoxTypeUser.setItems(typeUserChoiceBoxOptions);
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
}