package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.css.PseudoClass;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSignUpForm {

    private static ValidateSignUpForm validateSignUpFormClass = null;
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final PseudoClass checkClass = PseudoClass.getPseudoClass("check");
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private SignUpLayoutController signUpLayoutController = null;

    private ArrayList<TextField> textFieldArrayList = new ArrayList<>();
    private ArrayList<ChoiceBox> choiceBoxArrayList = new ArrayList<>();
    private ArrayList<Label> labelsArrayList = new ArrayList<>();


    private boolean isCorrectNameField = false;
    private boolean isCorrectLastNameField = false;
    private boolean isCorrectRFCField = false;
    private boolean isEmptyGenreField = true;
    private boolean isEmptyMaritalStatus = true;
    private boolean isCorrectAddressField = false;
    private boolean isCorrectEmailField = false;
    private boolean isEmptyTypeUser = true;
    private boolean fingerprintTemplateIsReady = false;
    private boolean fingerprintTemplateImageFileIsReady = false;
    private boolean userPhotoFileIsReady = false;

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

                    switch (txtField.getId()) {
                        case "txtFieldFormRFC":
                            validateRFC(txtField);
                            break;
                        case "txtFieldFormAddress":
                            validateAddressField(txtField);
                            break;
                        case "txtFieldFormEmail":
                            validateEmail(txtField);
                            break;
                        default:
                            validateBasicFields(txtField);
                            break;
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

    //------ Código para la validación de campo vacío y caractéres especiales en campos básicos.

    public void validateBasicFields(TextField textField) {

        String labelMessage = "";

        String regex = "^[a-zA-ZÀ-ÿ ÇçÜü'-.]*$";
        Pattern basicFieldsPattern = Pattern.compile(regex);
        Matcher basicFieldsMatcher = basicFieldsPattern.matcher(textField.getText());
        boolean basicFieldIsCorrect = basicFieldsMatcher.matches();


        if (textField.getText().isEmpty() || (!textField.getText().isEmpty() && !basicFieldIsCorrect)) {

            if (textField.getText().isEmpty()) {

                switch (textField.getId()) {
                    case "txtFieldFormNames":
                        labelMessage = "El campo NOMBRE no puede estar vacío";
                        break;
                    case "txtFieldFormLastNames":
                        labelMessage = "El campo APELLIDO no puede estar vacío";
                        break;
                }

            } else if (!basicFieldIsCorrect) {
                labelMessage = "Caracteres no válidos detectados.";
            }

            switch (textField.getText()) {
                case "txtFieldFormNames":
                    isCorrectNameField = false;
                    break;
                case "txtFieldFormLastNames":
                    isCorrectLastNameField = false;
                    break;
            }

            setUpWarningLabelFields(textField.getId(), labelMessage, "show");
            textFieldBorderManager(textField, "show");

        } else {

            setUpWarningLabelFields(textField.getId(), "", "hide");
            textFieldBorderManager(textField, "hide");

            switch (textField.getId()) {
                case "txtFieldFormNames":
                    isCorrectNameField = true;
                    break;
                case "txtFieldFormLastNames":
                    isCorrectLastNameField = true;
                    break;
            }
        }
    }


    // ------------ Validación del campo dirección.

    public void validateAddressField(TextField textField) {

        String labelMessage = "";

        String regex = "^[a-zA-ZÀ-ÿ0-9 '-.]*$";
        Pattern addressFieldPattern = Pattern.compile(regex);
        Matcher addressFieldMatcher = addressFieldPattern.matcher(textField.getText());
        boolean addressFieldIsCorrect = addressFieldMatcher.matches();

        if (textField.getText().isEmpty() || (!textField.getText().isEmpty() && !addressFieldIsCorrect)) {

            if (textField.getText().isEmpty()) {
                labelMessage = "El campo DIRECCIÓN no puede estar vacío.";
            } else if (!addressFieldIsCorrect) {
                labelMessage = "Caracteres no válidos detectados.";
            }

            setUpWarningLabelFields(textField.getId(), labelMessage, "show");
            textFieldBorderManager(textField, "show");
            isCorrectAddressField = false;
        } else {
            setUpWarningLabelFields(textField.getId(), "", "hide");
            textFieldBorderManager(textField, "hide");
            isCorrectAddressField = true;
        }
    }

    // -------- Validación de RFC

    public void validateRFC(TextField textField) {

        String labelMessage = "";
        String regex = "[a-zA-Z0-9]{18}";

        Pattern RFCPattern = Pattern.compile(regex);
        Matcher RFCMatcher = RFCPattern.matcher(textField.getText());
        boolean isCompleteRFC = RFCMatcher.matches();

        if ((!isCompleteRFC && !textField.getText().isEmpty()) || textField.getText().isEmpty()) {

            if (textField.getText().isEmpty()) {
                labelMessage = "El campo RFC no puede estar vacío.";
            } else if (!isCompleteRFC) {
                labelMessage = "El campo no cumple con 18 caracteres.";
            }

            setUpWarningLabelFields(textField.getId(), labelMessage, "show");
            textFieldBorderManager(textField, "show");
            isCorrectRFCField = false;

        } else {
            setUpWarningLabelFields(textField.getId(), "", "hide");
            textFieldBorderManager(textField, "hide");
            isCorrectRFCField = true;
        }
    }

    //------- Validación de Email

    public void validateEmail(TextField textField) {

        String regex = "^[A-Za-z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[A-Za-z-]+\\.)+[a-zA-Z-]{2,3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean validationResult = matcher.matches();

        if (!validationResult && !textField.getText().isEmpty()) {

            setUpWarningLabelFields(textField.getId(), "Dirección de correo no válida", "show");
            textFieldBorderManager(textField, "show");
            isCorrectEmailField = false;
        } else {

            setUpWarningLabelFields(textField.getId(), "", "hide");
            textFieldBorderManager(textField, "hide");
            isCorrectEmailField = true;
        }
    }


    //--------- Validar la fotografía del usuario:

    public void validateUserPhotoBase64(String userPhoto) {
        if (userPhoto == null) {
            for (Label labelItem : labelsArrayList) {
                if (labelItem.getId().equals("btnTakePhotoLabel")) {
                    labelItem.setText("Debe registrar una fotrografía.");
                    labelItem.setVisible(true);
                    userPhotoFileIsReady = false;
                }
            }
        } else {
            userPhotoFileIsReady = true;
        }
    }

    //-------- Fingerprint label warning

    public void validateFingerprintTemplate(String fingerprintTemplate, String templateFingerprintImage) {

        if (fingerprintTemplate == null || templateFingerprintImage == null) {

            for (Label labelItem : labelsArrayList) {
                if (labelItem.getId().equals("btnEnrollFingerprintLabel")) {
                    labelItem.setText("Debe registrar una huella digital.");
                    labelItem.setVisible(true);
                }
            }
        } else {
            fingerprintTemplateIsReady = true;
            fingerprintTemplateImageFileIsReady = true;
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

    public boolean formIsCorrect(String buttonInvoke) {

        boolean formIsCorrect = false;

        switch (buttonInvoke) {
            case "register":
                if (isCorrectNameField && isCorrectLastNameField && isCorrectRFCField && !isEmptyGenreField && !isEmptyMaritalStatus && isCorrectAddressField && isCorrectEmailField && !isEmptyTypeUser && fingerprintTemplateIsReady && fingerprintTemplateImageFileIsReady && userPhotoFileIsReady) {
                    formIsCorrect = true;
                } else {
                    formIsCorrect = false;
                }
                break;
            case "update":
                if (isCorrectNameField && isCorrectLastNameField && !isEmptyGenreField && !isEmptyMaritalStatus && isCorrectAddressField && isCorrectEmailField && !isEmptyTypeUser) {
                    formIsCorrect = true;
                } else {
                    formIsCorrect = false;
                }
                break;
        }

        return formIsCorrect;
    }

    private void setUpWarningLabelFields(String fieldID, String labelWarningMessage, String warningStatus) {

        String labelIDJoined = fieldID + "Label";

        for (Label labelItem : labelsArrayList) {
            if (labelItem.getId().equals(labelIDJoined)) {

                labelItem.setText(labelWarningMessage);

                switch (warningStatus) {
                    case "show":
                        labelItem.setVisible(true);
                        break;
                    case "hide":
                        labelItem.setVisible(false);
                        break;
                }
            }
        }
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

    //Border manager
    public void textFieldBorderManager(TextField textField, String status) {

        switch (status) {
            case "show":
                textField.pseudoClassStateChanged(checkClass, false);
                textField.pseudoClassStateChanged(errorClass, true);
                break;
            case "hide":
                textField.pseudoClassStateChanged(errorClass, false);
                if (!textField.getId().equals("txtFieldFormEmail")) {
                    textField.pseudoClassStateChanged(checkClass, true);
                }
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
                choiceBox.pseudoClassStateChanged(checkClass, true);
                break;
        }
    }
}
