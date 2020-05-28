package verify;

import UIController.ProfileLayout.ProfileLayoutController;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import resources.LaunchFingerprintReader;

import javax.swing.*;


public class Verify extends LaunchFingerprintReader {

    private ProfileLayoutController profileLayoutController = null;
    private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
    private JLabel fingerprintImage = new JLabel();


    public Verify(ProfileLayoutController profileLayoutController) {
        super();
        this.profileLayoutController = profileLayoutController;
    }

    @Override
    public void process(DPFPSample sample) {
        super.process(sample);

        //MainClass mainClass = MainClass.getInstance();
        DPFPFeatureSet features = super.extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {

            DPFPVerificationResult result = verificator.verify(features, profileLayoutController.getFingerprintTemplateToCompare());

            if (result.isVerified()) {
                showAlertVerificationMessage("VERIFIED");
                System.out.println("The fingerprint was VERIFIED.");
            } else {
                showAlertVerificationMessage("NOT VERIFIED");
                System.out.println("The fingerprint was NOT VERIFIED.");
            }
        }
    }

    private void showAlertVerificationMessage(String verificationResponseMessage) {

        String message = null;

        switch (verificationResponseMessage){
            case "VERIFIED":
                message = "El usuario ha sido VERIFICADO con éxito.";
                break;
            case "NOT VERIFIED":
                message = "El usuario NO CORRESPONDE.";
                break;
        }

        ButtonType ACCEPT_BUTTON = new ButtonType("Aceptar", ButtonBar.ButtonData.YES);

        Alert verifiedResponseAlert = new Alert(Alert.AlertType.CONFIRMATION, message, ACCEPT_BUTTON);
        verifiedResponseAlert.setTitle("¡Verificado!");
        verifiedResponseAlert.setHeaderText(null);
        verifiedResponseAlert.showAndWait();

        switch (verifiedResponseAlert.getResult().getText()) {
            case "Aceptar":
                //Cerrar la ventana de la huella.
                this.setVisible(false);
                break;
        }

    }
}
