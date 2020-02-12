package gov.fged.enrollment;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import gov.fged.capture.CaptureForm;
import gov.fged.main.MainForm;

import javax.swing.*;
import java.awt.*;

public class EnrollmentForm extends CaptureForm {

    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    public EnrollmentForm(Frame owner) {
        super(owner);
    }

    @Override
    protected void init() {
        super.init();
        this.setTitle("Fingerprint Enrollment");
        updateStatus();
    }

    private void updateStatus() {
        //Show number of samples needed.
        setStatus(String.format("Fingerprint samples needed: %1$s", enroller.getFeaturesNeeded()));
    }

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);

        //Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        //Check quality of the sample and add to enroller if it's good.
        if (features != null) {
            try {
                makeReport("The fingerprint feature set was created.");
                enroller.addFeatures(features);
            } catch (DPFPImageQualityException ex) {

            } finally {

                updateStatus();

                //Check if template has been created.
                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY: //Report sucess and stop capturing
                        stop();
                        ((MainForm) getOwner()).setTemplate(enroller.getTemplate());
                        setPrompt("Click Close, and then click Fingerprint Verification");
                        break;

                    case TEMPLATE_STATUS_FAILED:
                        enroller.clear();
                        stop();
                        updateStatus();
                        ((MainForm) getOwner()).setTemplate(null);
                        JOptionPane.showMessageDialog(EnrollmentForm.this, "The fingerprint template is not valid. Repeat fingerprint enrollment.", "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            }
        }
    }
}
