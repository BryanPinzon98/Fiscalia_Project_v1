package verify;

import UIController.ProfileLayout.ProfileLayoutController;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
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

            DPFPVerificationResult result = verificator.verify(features, profileLayoutController.getDpfpTemplate());

            if (result.isVerified()) {
                System.out.println("The fingerprint was VERIFIED.");
            } else {
                System.out.println("The fingerprint was NOT VERIFIED.");
            }
        }
    }
}
