package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.css.PseudoClass;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSignUpForm {

    private static ValidateSignUpForm validateSignUpFormClass = null;
    private ArrayList<TextField> textFieldArrayList = new ArrayList<>();
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private ManageLayout manageLayoutClass = ManageLayout.getInstance();
    private SignUpLayoutController signUpLayoutController = null;

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
    }

    //------ Código para la validación de campo vacío.

    public void validateEmptyForm(TextField textField) {
        if (textField.getText().isEmpty()) {
            showEmptyAlert(textField);
        } else {
            hideEmptyAlert(textField);
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
                    signUpLayoutController.manageNameLabelWarning("El campo NOMBRE no puede estar vacío.", "show");
                } else {
                    signUpLayoutController.manageNameLabelWarning("", "hide");
                }
                break;
            case "txtFieldFormLastNames":
                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageLastNameLabelWarning("El campo APELLIDO no puede estar vacío.", "show");
                } else {
                    signUpLayoutController.manageLastNameLabelWarning("", "hide");
                }
                break;
            case "txtFieldFormAddress":
                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageAddressLabelWarning("El campo DIRECCIÓN no puede estar vacío.", "show");
                } else {
                    signUpLayoutController.manageAddressLabelWarning("", "hide");
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
                signUpLayoutController.manageRFCLabelWarning(validationCase);
                textFieldBorderManager(textField, "show");
                break;
            case "hide":
                signUpLayoutController.hideRFCWarningMessage();
                textFieldBorderManager(textField, "hide");
                break;
        }
    }


    //------- Validación de Email

    public void validateEmail(TextField textField) {

        String regex = "^[A-Za-z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[A-Za-z-]+\\.)+[a-zA-Z-]{2,3}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(textField.getText());
        System.out.println("Validación result: " + matcher.matches());

        boolean validationResult = matcher.matches();

        if (!validationResult && !textField.getText().isEmpty()) {
            manageEmailLabelAlert("Dirección de correo no válida", "show");
            textFieldBorderManager(textField, "show");
        } else {
            manageEmailLabelAlert("", "hide");
            textFieldBorderManager(textField, "hide");
        }
    }

    public void manageEmailLabelAlert(String warningMessage, String warningStatus) {
        signUpLayoutController.manageEmailLabelWarning(warningMessage, warningStatus);
    }


    // ------- Text Field border manager
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

    // ------- Getters and Setters.
    public void setTxtFieldArray(ArrayList<TextField> txtFieldArray) {
        this.textFieldArrayList = txtFieldArray;
    }
}
