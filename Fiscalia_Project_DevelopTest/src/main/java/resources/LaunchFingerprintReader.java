package gov.fged.java.resources;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public abstract class LaunchFingerprintReader extends JFrame {

    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private JLabel fingerprintImage = new JLabel();

    public LaunchFingerprintReader() {

        setPreferredSize(new Dimension(720, 480));
        setResizable(true);

        fingerprintImage.setPreferredSize(new Dimension(300, 480));
        fingerprintImage.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        setLayout(new BorderLayout());
        add(fingerprintImage, BorderLayout.LINE_START);

        pack();
        setLocationRelativeTo(null);

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

    public void process(DPFPSample sample){
        drawImage(convertSampleToBitmap(sample));
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
            public void onImageQuality(final DPFPImageQualityEvent e) {
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

    protected void stop() {
        capturer.stopCapture();
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
