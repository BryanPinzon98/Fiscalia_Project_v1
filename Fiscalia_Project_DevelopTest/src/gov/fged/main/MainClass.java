package gov.fged.main;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import gov.fged.enrollment.Enrollment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;

public class MainClass extends JFrame {

    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    private static MainClass mainClass = null;

    public static MainClass getInstance() {
        if (mainClass == null) {
            mainClass = new MainClass();
        }

        return mainClass;
    }

    public static void main(String[] args) {

        //Stay in another thread until all process into Main Method be executed.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainClass runMainClass = getInstance();
            }
        });
    }

    private MainClass() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(720, 480));
        pack();
        setLocationRelativeTo(null);
        setTitle("Sign Up FGED Platform");
        setResizable(true);

        final JButton enrollmentButton = new JButton("Enrollment");
        enrollmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onEnroll();
            }
        });

        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSave();
            }
        });

        JPanel centerButtons = new JPanel();
        centerButtons.setLayout(new GridLayout(4, 1, 0, 5));
        centerButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        centerButtons.add(enrollmentButton);
        centerButtons.add(saveButton);


        setLayout(new BorderLayout());
        add(centerButtons, BorderLayout.CENTER);

        //setTemplate(null);
        setVisible(true);
    }

    public void onEnroll() {
        Enrollment enrollmentProcess = new Enrollment();
        enrollmentProcess.setVisible(true);
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
                                String.format("File \"%1$s\" already exists. \nDo you want to replace it?", file.toString()),
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

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    public DPFPTemplate getTemplate() {
        return template;
    }


}
