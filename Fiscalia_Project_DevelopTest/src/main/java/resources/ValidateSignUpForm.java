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

    public ValidateSignUpForm() {
    }

    public static ValidateSignUpForm getInstance() {
        if (validateSignUpFormClass == null) {
            validateSignUpFormClass = new ValidateSignUpForm();
        }
        return validateSignUpFormClass;
    }

    //Inicia las validaciones.
    public void validateForm() {

        signUpLayoutController = manageLayoutClass.getFxmlLoader().getController();

        for (TextField txtField : textFieldArrayList) {

            txtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {

                //Si el foco del mause se sale de cualquier TextField
                if (!newValue) {

                    if (!txtField.getId().equals("txtFieldFormEmail")) {
                        //Validar los TextField vacíos excepto el de email.
                        validateEmptyForm(txtField);
                    } else {
                        //Validar el TextField de Email.

                        // validateEmail(txtField);
                    }

                    //Validar RFC
                    if (txtField.getId().equals("txtFieldFormRFC")) {
                        validateRFC(txtField);
                    }
                }
            });
        }
    }


    //Activan la estética de warning.
    public void showFrontValidationFeed(TextField textField) {
        manageLabelAlert(textField.getId(), "show");
        textField.pseudoClassStateChanged(errorClass, true);
    }

    public void hideFrontValidationFeed(TextField textField) {
        manageLabelAlert(textField.getId(), "hide");
        textField.pseudoClassStateChanged(errorClass, false);
    }



    //Validaciones
    public void validateEmptyForm(TextField textField) {
        if (textField.getText().isEmpty()) {
            showFrontValidationFeed(textField);
        } else {
            hideFrontValidationFeed(textField);
        }
    }

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

    

    //Administran y activan la estética de warning
    public void manageLabelAlert(String txtFieldID, String warningStatus) {

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
            case "txtFieldFormEmail":
                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageEmailLabelWarning("El campo EMAIL no puede estar vacío.", "show");
                } else {
                    signUpLayoutController.manageEmailLabelWarning("", "hide");
                }
                break;
        }
    }

    public void manageRFCLabelAlert(String warningStatus, String validationCase, TextField textField) {

        switch (warningStatus) {
            case "show":
                signUpLayoutController.manageRFCLabelWarning(validationCase);
                textField.pseudoClassStateChanged(errorClass, true);
                break;
            case "hide":
                signUpLayoutController.hideRFCWarningMessage();
                textField.pseudoClassStateChanged(errorClass, false);
                break;
        }
    }


    //Getters and Setters.
    public void setTxtFieldArray(ArrayList<TextField> txtFieldArray) {
        this.textFieldArrayList = txtFieldArray;
    }
}
