package resources;

import UIController.SignUpLayout.SignUpLayoutController;
import javafx.css.PseudoClass;
import javafx.scene.control.TextField;

import java.util.ArrayList;
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
                        validateEmail(txtField);
                    }

                    //Validar RFC
                    if (txtField.getId().equals("txtFieldFormRFC")) {
                        validateRFC(txtField);
                    }
                }
            });
        }
    }

    public void validateEmail(TextField textField){

    }

    public void showFrontValidationFeed(TextField textField) {
        manageLabelAlert(textField.getId(), "show");
        textField.pseudoClassStateChanged(errorClass, true);
    }

    public void hideFrontValidationFeed(TextField textField) {
        manageLabelAlert(textField.getId(), "hide");
        textField.pseudoClassStateChanged(errorClass, false);
    }

    public void validateRFC(TextField textField) {
        if ((!Pattern.matches("[a-zA-Z0-9]{12}", textField.getText()) && !textField.getText().isEmpty()) || textField.getText().isEmpty()) {
            showFrontValidationFeed(textField);
        } else {
            hideFrontValidationFeed(textField);
        }
    }

    public void validateEmptyForm(TextField textField) {
        if (textField.getText().isEmpty()) {
            showFrontValidationFeed(textField);
        } else {
            hideFrontValidationFeed(textField);
        }
    }


    /*
    Revisar si es necesario utilziar la validación por ID sino colocar sólo un label en general para todos.
     */
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
            case "txtFieldFormRFC":
                if (warningStatus.equals("show")) {
                    signUpLayoutController.manageRFCLabelWarning();
                    /*
                    else if (validationType.equals("RFCLength")) {
                        signUpLayoutController.setRFCLengthMessage("El campo RFC no tiene 12 caracteres.");
                        System.err.println("Está validando los 12 caracteres");
                        System.err.println(signUpLayoutController.getRFCLabel());
                    }

                     */
                } else {
                    signUpLayoutController.hideRFCWarningMessage();
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

    public void setTxtFieldArray(ArrayList<TextField> txtFieldArray) {
        this.textFieldArrayList = txtFieldArray;
    }
}
