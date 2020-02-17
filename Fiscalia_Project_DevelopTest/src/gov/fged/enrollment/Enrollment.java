package gov.fged.enrollment;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import gov.fged.main.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Enrollment extends JFrame {

    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private JLabel fingerprintImage = new JLabel();

    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";


    public Enrollment() {

        setPreferredSize(new Dimension(720, 480));
        setResizable(true);

        fingerprintImage.setPreferredSize(new Dimension(300, 480));
        fingerprintImage.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        setLayout(new BorderLayout());
        add(fingerprintImage, BorderLayout.LINE_START);

        executeAddComponentListerner();

        pack();
        setLocationRelativeTo(null);
    }

    private void executeAddComponentListerner() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent componentEvent) {
                init();
                start();
            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {
                stop();
            }
        });
    }

    protected void init() {
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Datos adquiridos");
                        process(e.getSample());
                    }
                });
            }
        });

        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(DPFPReaderStatusEvent dpfpReaderStatusEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("The fingerprint reader was connected.");
                    }
                });
            }

            @Override
            public void readerDisconnected(DPFPReaderStatusEvent dpfpReaderStatusEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("The fingerprint reader was disconnected.");
                    }
                });
            }
        });

        capturer.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(DPFPSensorEvent dpfpSensorEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("The fingerprint reader was touched.");
                    }
                });
            }

            @Override
            public void fingerGone(DPFPSensorEvent dpfpSensorEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("The finger was removed from the fingerprint reader.");
                    }
                });
            }
        });

        capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
            @Override
            public void onImageQuality(DPFPImageQualityEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD)) {
                            System.out.println("The quality of the fingerprint sample is good.");
                        } else {
                            System.out.println("The quality of the fingerprint sample is poor.");
                        }
                    }
                });
            }
        });
    }

    protected void start() {
        capturer.startCapture();
    }

    //Complement methods

    protected Image convertSampleToBitmap(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

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
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        stop();
                        break;
                }
            }
        }
    }

    protected void stop() {
        capturer.stopCapture();
    }

    public void drawImage(Image image) {
        fingerprintImage.setIcon(new ImageIcon(image.getScaledInstance(fingerprintImage.getWidth(), fingerprintImage.getHeight(), Image.SCALE_DEFAULT)));
    }

}
