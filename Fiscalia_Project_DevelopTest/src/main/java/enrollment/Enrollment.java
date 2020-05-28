package enrollment;

import UIController.ProfileLayout.ProfileLayoutController;
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
    private ProfileLayoutController profileLayoutController = null;
    private String classInvoker = null;

    public Enrollment(String classInvoker) {
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
                        this.setVisible(false);

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
}
