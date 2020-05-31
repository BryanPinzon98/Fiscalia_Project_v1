package enrollment;

import UIController.ProfileLayout.ProfileLayoutController;
import UIController.SignUpLayout.SignUpLayoutController;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import main.MainClass;
import resources.LaunchFingerprintReader;


public class Enrollment extends LaunchFingerprintReader {

    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private SignUpLayoutController signUpLayoutControllerClass = null;
    private ProfileLayoutController profileLayoutController = null;
    private String classInvoker = null;

    public Enrollment(String classInvoker) {
        super();
        instructionsAlertMessage();
    }

    @Override
    public void process(DPFPSample sample) {
        super.process(sample);

        DPFPFeatureSet features = super.extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (features != null) {
            try {
                enroller.addFeatures(features);
            } catch (DPFPImageQualityException e) {

            } finally {

                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        stop();
                        MainClass mainClass = MainClass.getInstance();
                        mainClass.setEnrollmentClassInvoker(classInvoker);

                        //---- Envío del template (.fpt) de la huella
                        switch (classInvoker){
                            case "SIGN_UP_CLASS":
                                mainClass.setSignUpLayoutControllerClass(this.signUpLayoutControllerClass);
                                break;
                            case "USER_PROFILE_CLASS":
                                mainClass.setProfileLayoutController(this.profileLayoutController);
                                break;
                        }
                        //-----
                        mainClass.setTemplate(enroller.getTemplate());
                        mainClass.setVisible(false);

                        fingerprintCreatedSuccessfullyAlertMessage();

                        //Envío de la imagen final del template de la huella.
                        if(classInvoker.equals("SIGN_UP_CLASS")){
                            signUpLayoutControllerClass.convertImageToFile("fingerprintImage", super.getFingerprintTemplateBufferedImage());
                        }

                        //Storage storage = new Storage();
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        stop();
                        break;
                }
            }
        }
    }

    private void fingerprintCreatedSuccessfullyAlertMessage() {
        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert userCreatedAlert = new Alert(Alert.AlertType.CONFIRMATION, "¡Huella digital capturada exitosamente!", ACCEPT_BUTTON);
        userCreatedAlert.setTitle("Atención");
        userCreatedAlert.setHeaderText(null);
        userCreatedAlert.showAndWait();

        switch(userCreatedAlert.getResult().getText()){
            case "Aceptar":
                this.setVisible(false);
                break;
        }
    }


    public void setSignUpLayoutControllerClass(SignUpLayoutController signUpLayoutControllerClass) {
        if (signUpLayoutControllerClass != null) {
            this.signUpLayoutControllerClass = signUpLayoutControllerClass;
        } else {
            System.out.println("La referencia Sign Up en enrollment está nula");
        }
    }

    public void setProfileLayoutController(ProfileLayoutController profileLayoutController) {
        this.profileLayoutController = profileLayoutController;
    }

    private void instructionsAlertMessage(){
        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert userCreatedAlert = new Alert(Alert.AlertType.CONFIRMATION, "Toque el sensor hasta que la captura de la huella sea exitosa.", ACCEPT_BUTTON);
        userCreatedAlert.setTitle("Atención");
        userCreatedAlert.setHeaderText(null);
        userCreatedAlert.showAndWait();
    }
}
