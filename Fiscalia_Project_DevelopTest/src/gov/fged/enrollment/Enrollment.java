package gov.fged.enrollment;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import gov.fged.resources.LaunchFingerprintReader;
import gov.fged.storage.Storage;
import gov.fged.main.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Enrollment extends LaunchFingerprintReader {

    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private JLabel fingerprintImage = new JLabel();

    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";


    public Enrollment() {
        super();

        setPreferredSize(new Dimension(720, 480));
        setResizable(true);

        fingerprintImage.setPreferredSize(new Dimension(300, 480));
        fingerprintImage.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        setLayout(new BorderLayout());
        add(fingerprintImage, BorderLayout.LINE_START);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void process(DPFPSample sample) {
        drawImage(convertSampleToBitmap(sample));

        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (features != null) {
            try {
                enroller.addFeatures(features);
            } catch (DPFPImageQualityException e) {

            } finally {

                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        stop();
                        MainClass mainClass = MainClass.getInstance();
                        mainClass.setTemplate(enroller.getTemplate());

                        Storage storage = new Storage();
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        stop();
                        break;
                }
            }
        }
    }

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    protected Image convertSampleToBitmap(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    public void drawImage(Image image) {
        fingerprintImage.setIcon(new ImageIcon(image.getScaledInstance(fingerprintImage.getWidth(), fingerprintImage.getHeight(), Image.SCALE_DEFAULT)));
    }
}
