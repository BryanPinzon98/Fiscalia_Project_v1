package gov.fged.resources;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import gov.fged.main.MainClass;
import gov.fged.storage.Storage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public abstract class LaunchFingerprintReader extends JFrame {

    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();

    public LaunchFingerprintReader() {

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

    public abstract void process(DPFPSample sample);

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

    protected void stop() {
        capturer.stopCapture();
    }

}
