package gov.fged.main;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;

public class MainClass extends JFrame {

    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private JLabel fingerprintImage = new JLabel();
    private DPFPTemplate template;

    public static String TEMPLATE_PROPERTY = "template";

    public static void main(String[] args) {

        //Stay in another thread until all process into Main Method be executed.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass();
            }
        });
    }

    public MainClass() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(720, 480));
        //Set the screen resolution.
        pack();
        //Show the window in the middle of screen
        setLocationRelativeTo(null);
        setTemplate(null);
        setTitle("Fingerprint Image Test");
        setResizable(true);

        fingerprintImage.setPreferredSize(new Dimension(300, 480));
        fingerprintImage.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSave();
            }
        });

        setLayout(new BorderLayout());
        add(fingerprintImage, BorderLayout.LINE_START);
        add(saveButton, BorderLayout.PAGE_END);

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

        setVisible(true);
    }

    private void onSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new TemplateFileFilter());

        while (true) {

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    if (!file.toString().toLowerCase().endsWith(".fpt")) {
                        file = new File(file.toString() + ".fpt");
                    }
                    if (file.exists()) {
                        int choice = JOptionPane.showConfirmDialog(this,
                                String.format("File \"%1%s\" already exists. \nDo you want to replace it?", file.toString()),
                                "Fingerprint saving", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (choice == JOptionPane.NO_OPTION) {
                            continue;
                        } else if (choice == JOptionPane.CANCEL_OPTION) {
                            break;
                        }
                    }

                    FileOutputStream stream = new FileOutputStream(file);
                    stream.write(getTemplate().serialize());
                    stream.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Fingerprint saving", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
        }
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    public static class TemplateFileFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(".fpt");
        }

        @Override
        public String getDescription() {
            return "FingerTemplate File (*.fpt)";
        }
    }


    //The following methods are necessary to capture a fingerprint sample.
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
                        setTemplate(enroller.getTemplate());
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
