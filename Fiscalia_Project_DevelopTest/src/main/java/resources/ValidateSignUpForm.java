package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.css.PseudoClass;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSignUpForm {

    private static ValidateSignUpForm validateSignUpFormClass = null;
    private ArrayList<TextField> textFieldArrayList = new ArrayList<>();
    private ArrayList<ChoiceBox> choiceBoxArrayList = new ArrayList<>();
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private SignUpLayoutController signUpLayoutController = null;
    private HashMap<String, Boolean> fieldsStatusHashMap = new HashMap<String, Boolean>();


    private boolean isEmptyNameField = true;
    private boolean isEmptyLastNameField = true;
    private boolean isCorrectRFCField = false;
    private boolean isEmptyGenreField = true;
    private boolean isEmptyMaritalStatus = true;
    private boolean isEmptyAddress = true;
    private boolean isCorrectEmail = false;
    private boolean isEmptyTypeUser = true;

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
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "El campo GENERO no puede estar vacío.", warningStatus);
                    isEmptyGenreField = true;
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "", warningStatus);
                    isEmptyGenreField = false;
                }
                fieldsStatusHashMap.put(choiceBoxID, isEmptyGenreField);
                break;

            case "choiceBoxMaritalStatus":

                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "El campo ESTADO CIVIL no puede estar vacío.", warningStatus);
                    isEmptyMaritalStatus = true;
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "", warningStatus);
                    isEmptyMaritalStatus = false;
                }
                fieldsStatusHashMap.put(choiceBoxID, isEmptyMaritalStatus);
                break;

            case "choiceBoxTypeUser":

                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "El campo TIPO USUARIO no puede estar vacío.", warningStatus);
                    isEmptyTypeUser = true;
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(choiceBoxID, "", warningStatus);
                    isEmptyTypeUser = false;
                }
                fieldsStatusHashMap.put(choiceBoxID, isEmptyTypeUser);
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
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "El campo NOMBRE no puede estar vacío.", warningStatus);
                    isEmptyNameField = true;
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "", "hide");
                    isEmptyNameField = false;
                }
                fieldsStatusHashMap.put(txtFieldID, isEmptyNameField);
                break;
            case "txtFieldFormLastNames":
                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "El campo APELLIDO no puede estar vacío.", warningStatus);
                    isEmptyLastNameField = true;
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "", "hide");
                    isEmptyLastNameField = false;
                }
                fieldsStatusHashMap.put(txtFieldID, isEmptyLastNameField);
                break;
            case "txtFieldFormAddress":
                if (warningStatus.equals("show")) {
                    isEmptyAddress = true;
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "El campo DIRECCIÓN no puede estar vacío.", warningStatus);
                } else {
                    signUpLayoutController.manageGeneralLabelWarning(txtFieldID, "", "hide");
                    isEmptyAddress = false;
                }
                fieldsStatusHashMap.put(txtFieldID, isEmptyAddress);
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
                signUpLayoutController.manageRFCLabelWarning(validationCase);
                textFieldBorderManager(textField, "show");
                isCorrectRFCField = false;
                break;
            case "hide":
                signUpLayoutController.hideRFCWarningMessage();
                textFieldBorderManager(textField, "hide");
                isCorrectRFCField = true;
                break;
        }
        fieldsStatusHashMap.put(textField.getId(), isCorrectRFCField);
    }


    //------- Validación de Email

    public void validateEmail(TextField textField) {

        String regex = "^[A-Za-z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[A-Za-z-]+\\.)+[a-zA-Z-]{2,3}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(textField.getText());

        boolean validationResult = matcher.matches();

        if (!validationResult && !textField.getText().isEmpty()) {
            manageEmailLabelAlert("Dirección de correo no válida", "show");
            textFieldBorderManager(textField, "show");
            isCorrectEmail = false;
        } else {
            manageEmailLabelAlert("", "hide");
            textFieldBorderManager(textField, "hide");
            isCorrectEmail = true;
        }

        fieldsStatusHashMap.put(textField.getId(), isCorrectEmail);
    }

    public void manageEmailLabelAlert(String warningMessage, String warningStatus) {
        signUpLayoutController.manageEmailLabelWarning(warningMessage, warningStatus);
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

    //------- Utilities
    public HashMap<String, Boolean> getFieldsStatus() {
        return this.fieldsStatusHashMap;
    }

    public void setChoiceBoxArrayList(ArrayList<ChoiceBox> choiceBoxArrayList) {
        this.choiceBoxArrayList = choiceBoxArrayList;
    }
}
