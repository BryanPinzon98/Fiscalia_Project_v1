package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import com.digitalpersona.onetouch.DPFPTemplate;
import javafx.css.PseudoClass;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSignUpForm {

    private static ValidateSignUpForm validateSignUpFormClass = null;
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private SignUpLayoutController signUpLayoutController = null;

    private ArrayList<TextField> textFieldArrayList = new ArrayList<>();
    private ArrayList<ChoiceBox> choiceBoxArrayList = new ArrayList<>();
    private ArrayList<Label> labelsArrayList = new ArrayList<>();


    private boolean isEmptyNameField = true;
    private boolean isEmptyLastNameField = true;
    private boolean isCorrectRFCField = false;
    private boolean isEmptyGenreField = true;
    private boolean isEmptyMaritalStatus = true;
    private boolean isEmptyAddress = true;
    private boolean isCorrectEmail = false;
    private boolean isEmptyTypeUser = true;
    private boolean fingerprintTemplateIsReady = false;
    private boolean fingerprintTemplateImageIsReady = false;

    // ----------- Constructor
    public ValidateSignUpForm() {
    }

    // ---------- Singleton
    public static ValidateSignUpForm getInstance() {
        if (validateSignUpFormClass == null) {
            validateSignUpFormClass = new ValidateSignUpForm();
        }
        return validateSignUpFormClass;
    }


    // ----------- Inicia las validaciones.
    public void validateForm() {

        signUpLayoutController = manageLayoutClass.getFxmlLoader().getController();

        for (TextField txtField : textFieldArrayList) {

            txtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {

                if (!newValue) {

                    if (!txtField.getId().equals("txtFieldFormEmail")) {
                        validateEmptyForm(txtField);
                    } else {
                        validateEmail(txtField);
                    }


                    if (txtField.getId().equals("txtFieldFormRFC")) {
                        validateRFC(txtField);
                    }
                }
            });
        }

        for (ChoiceBox choiceBox : choiceBoxArrayList) {

            choiceBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {

                if (!newValue) {
                    validateEmptyChoiceBox(choiceBox);
                }
            });
        }
    }


    //--------Validar los Choice Box

    public boolean validateEmptyChoiceBox(ChoiceBox choiceBox) {

        boolean choiceBoxIsEmpty = false;

        if (choiceBox.getSelectionModel().isEmpty()) {
            choiceBoxIsEmpty = true;
            manageChoiceBoxLabelAlert(choiceBox.getId(), "show");
            choiceBoxBorderManager(choiceBox, "show");
        } else {
            choiceBoxIsEmpty = false;
            manageChoiceBoxLabelAlert(choiceBox.getId(), "hide");
            choiceBoxBorderManager(choiceBox, "hide");
        }

        return choiceBoxIsEmpty;
    }

    public void manageChoiceBoxLabelAlert(String choiceBoxID, String warningStatus) {

        switch (choiceBoxID) {
            case "choiceBoxGenre":

                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(choiceBoxID, "El campo GENERO no puede estar vacío.", warningStatus);
                    isEmptyGenreField = true;
                } else {
                    setUpWarningLabelEmptyFields(choiceBoxID, "", warningStatus);
                    isEmptyGenreField = false;
                }
                break;

            case "choiceBoxMaritalStatus":

                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(choiceBoxID, "El campo ESTADO CIVIL no puede estar vacío.", warningStatus);
                    isEmptyMaritalStatus = true;
                } else {
                    setUpWarningLabelEmptyFields(choiceBoxID, "", warningStatus);
                    isEmptyMaritalStatus = false;
                }
                break;

            case "choiceBoxTypeUser":

                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(choiceBoxID, "El campo TIPO USUARIO no puede estar vacío.", warningStatus);
                    isEmptyTypeUser = true;
                } else {
                    setUpWarningLabelEmptyFields(choiceBoxID, "", warningStatus);
                    isEmptyTypeUser = false;
                }
                break;
        }
    }

    //------ Código para la validación de campo vacío.

    public void validateEmptyForm(TextField textField) {
        if (!textField.getId().equals("txtFieldFormEmail")) {
            if (textField.getText().isEmpty()) {
                showEmptyAlert(textField);
            } else {
                hideEmptyAlert(textField);
            }
        }
    }

    public void showEmptyAlert(TextField textField) {
        manageEmptyLabelAlert(textField.getId(), "show");
        textFieldBorderManager(textField, "show");
    }

    public void hideEmptyAlert(TextField textField) {
        manageEmptyLabelAlert(textField.getId(), "hide");
        textFieldBorderManager(textField, "hide");
    }

    public void manageEmptyLabelAlert(String txtFieldID, String warningStatus) {

        switch (txtFieldID) {
            case "txtFieldFormNames":
                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(txtFieldID, "El campo NOMBRE no puede estar vacío.", warningStatus);
                    isEmptyNameField = true;
                } else {
                    setUpWarningLabelEmptyFields(txtFieldID, "", warningStatus);
                    isEmptyNameField = false;
                }
                break;
            case "txtFieldFormLastNames":
                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(txtFieldID, "El campo APELLIDO no puede estar vacío.", warningStatus);
                    isEmptyLastNameField = true;
                } else {
                    setUpWarningLabelEmptyFields(txtFieldID, "", warningStatus);
                    isEmptyLastNameField = false;
                }
                break;
            case "txtFieldFormAddress":
                if (warningStatus.equals("show")) {
                    setUpWarningLabelEmptyFields(txtFieldID, "El campo DIRECCIÓN no puede estar vacío.", warningStatus);
                    isEmptyAddress = true;
                } else {
                    setUpWarningLabelEmptyFields(txtFieldID, "", warningStatus);
                    isEmptyAddress = false;
                }
                break;
            default:
                System.out.println("No se ha encontrado coincidencias de Text Field.");
                break;
        }
    }


    // -------- Validación de RFC

    public void validateRFC(TextField textField) {

        String validationCase = "";
        String regex = "[a-zA-Z0-9]{12}";

        Pattern RFCPattern = Pattern.compile(regex);
        Matcher RFCMatcher = RFCPattern.matcher(textField.getText());
        boolean isCompleteRFC = RFCMatcher.matches();

        if ((!isCompleteRFC && !textField.getText().isEmpty()) || textField.getText().isEmpty()) {

            if (textField.getText().isEmpty()) {
                validationCase = "emptyTextField";
            } else if (!isCompleteRFC) {
                validationCase = "RFCLength";
            }

            manageRFCLabelAlert("show", validationCase, textField);
        } else {
            manageRFCLabelAlert("hide", "NONE", textField);
        }
    }

    public void manageRFCLabelAlert(String warningStatus, String validationCase, TextField textField) {

        switch (warningStatus) {
            case "show":
                //signUpLayoutController.manageRFCLabelWarning(validationCase);
                setUpWarningLabelRFCField(textField.getId(), validationCase, warningStatus);
                textFieldBorderManager(textField, "show");
                isCorrectRFCField = false;
                break;
            case "hide":
                //signUpLayoutController.hideRFCWarningMessage();
                setUpWarningLabelRFCField(textField.getId(), validationCase, warningStatus);
                textFieldBorderManager(textField, "hide");
                isCorrectRFCField = true;
                break;
        }
    }


    //------- Validación de Email

    public void validateEmail(TextField textField) {

        String regex = "^[A-Za-z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[A-Za-z-]+\\.)+[a-zA-Z-]{2,3}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(textField.getText());

        boolean validationResult = matcher.matches();

        if (!validationResult && !textField.getText().isEmpty()) {
            manageEmailLabelAlert(textField.getId(), "Dirección de correo no válida", "show");
            textFieldBorderManager(textField, "show");
            isCorrectEmail = false;
        } else {
            manageEmailLabelAlert(textField.getId(), "", "hide");
            textFieldBorderManager(textField, "hide");
            isCorrectEmail = true;
        }
    }

    public void manageEmailLabelAlert(String emailFieldID, String warningMessage, String warningStatus) {
        setUpWarningLabelEmailField(emailFieldID, warningMessage, warningStatus);
    }

    //-------- Fingerprint label warning

    public void validateFingerprintTemplate(DPFPTemplate fingerprintTemplate, Image templateFingerprintImage) {

        if (fingerprintTemplate == null || templateFingerprintImage == null) {

            for (Label labelItem : labelsArrayList) {
                if (labelItem.getId().equals("btnEnrollFingerprintLabel")) {
                    labelItem.setText("Debe registrar una huella digital.");
                    labelItem.setVisible(true);
                }
            }
        } else {
            fingerprintTemplateIsReady = true;
            fingerprintTemplateImageIsReady = true;
        }
    }

    // ------- Border manager
    public void textFieldBorderManager(TextField textField, String status) {

        switch (status) {
            case "show":
                textField.pseudoClassStateChanged(errorClass, true);
                break;
            case "hide":
                textField.pseudoClassStateChanged(errorClass, false);
                break;
        }
    }

    public void choiceBoxBorderManager(ChoiceBox choiceBox, String status) {

        switch (status) {
            case "show":
                choiceBox.pseudoClassStateChanged(errorClass, true);
                break;
            case "hide":
                choiceBox.pseudoClassStateChanged(errorClass, false);
                break;
        }
    }

    // ------- Getters and Setters.
    public void setTxtFieldArray(ArrayList<TextField> txtFieldArray) {
        this.textFieldArrayList = txtFieldArray;
    }

    public void setChoiceBoxArrayList(ArrayList<ChoiceBox> choiceBoxArrayList) {
        this.choiceBoxArrayList = choiceBoxArrayList;
    }

    public void setLabelsArrayList(ArrayList<Label> labelsArrayList) {
        this.labelsArrayList = labelsArrayList;
    }

    //------- Utilities

    public boolean formIsCorrect() {

        boolean formIsCorrect = false;

        if (!isEmptyNameField && !isEmptyLastNameField && isCorrectRFCField && !isEmptyGenreField && !isEmptyMaritalStatus && !isEmptyAddress && isCorrectEmail && !isEmptyTypeUser && fingerprintTemplateIsReady && fingerprintTemplateImageIsReady) {
            formIsCorrect = true;
        } else {
            formIsCorrect = false;
        }

        return formIsCorrect;
    }

    public void setUpWarningLabelEmptyFields(String componentID, String labelWarningMessage, String warningStatus) {

        String labelIDJoined = componentID + "Label";

        for (Label labelItem : labelsArrayList) {
            if (labelItem.getId().equals(labelIDJoined)) {
                if (warningStatus.equals("show")) {
                    labelItem.setText(labelWarningMessage);
                    labelItem.setVisible(true);
                } else {
                    labelItem.setText(labelWarningMessage);
                    labelItem.setVisible(true);
                }
            }
        }
    }

    public void setUpWarningLabelRFCField(String RFCFieldID, String validationCase, String warningStatus) {

        String RFCLabelIDJoined = RFCFieldID + "Label";

        for (Label labelItem : labelsArrayList) {
            if (labelItem.getId().equals(RFCLabelIDJoined)) {

                if (warningStatus.equals("show")) {
                    switch (validationCase) {
                        case "emptyTextField":
                            labelItem.setText("El campo RFC no puede estar vacío.");
                            break;
                        case "RFCLength":
                            labelItem.setText("El campo no cumple con 12 caracteres.");
                            break;
                    }
                    labelItem.setVisible(true);

                } else {
                    labelItem.setVisible(false);
                }
            }
        }
    }

    public void setUpWarningLabelEmailField(String emailFieldID, String warningMessage, String warningStatus) {

        String emailLabelIDJoined = emailFieldID + "Label";

        for (Label labelItem : labelsArrayList) {
            if (labelItem.getId().equals(emailLabelIDJoined)) {
                if (warningStatus.equals("show")) {
                    labelItem.setText(warningMessage);
                    labelItem.setVisible(true);
                } else {
                    labelItem.setText(warningMessage);
                    labelItem.setVisible(false);
                }
            }
        }
    }
}
