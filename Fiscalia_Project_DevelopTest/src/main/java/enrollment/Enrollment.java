package enrollment;

import UIController.SignUpLayout.SignUpLayoutController;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import main.MainClass;
import resources.LaunchFingerprintReader;


public class Enrollment extends LaunchFingerprintReader {

    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private SignUpLayoutController signUpLayoutControllerClass = null;

    public Enrollment() {
        super();
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
                        //---- Envío del template (.fpt) de la huella
                        mainClass.setSignUpLayoutControllerClass(this.signUpLayoutControllerClass);
                        //-----
                        mainClass.setTemplate(enroller.getTemplate());

                        mainClass.setVisible(false);
                        this.setVisible(false);

                        //Envío de la imagen final del template de la huella.
                        signUpLayoutControllerClass.setTemplateFingerprintImage(super.getFingerprintTemplateImage());

                        //Storage storage = new Storage();
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        stop();
                        break;
                }
            }
        }
    }

    public void setSignUpLayoutControllerClass(SignUpLayoutController signUpLayoutControllerClass) {
        if (signUpLayoutControllerClass != null) {
            this.signUpLayoutControllerClass = signUpLayoutControllerClass;
        } else {
            System.out.println("La referencia Sign Up en enrollment está nula");
        }
    }
}
