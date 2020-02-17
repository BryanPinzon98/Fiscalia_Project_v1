package gov.fged.main;

import com.digitalpersona.onetouch.*;
import gov.fged.enrollment.EnrollmentForm;
import gov.fged.verification.VerificationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class MainForm extends JFrame {

    //Normal String
    public static String TEMPLATE_PROPERTY = "template";
    //Fingerprint object from .jar digitalPerson drivers
    private DPFPTemplate template;

    public static void main(String[] args) {

        //Stay in another thread until all process into Main Method be executed.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm();
            }
        });
    }

    MainForm() {

        //setState is change. Original is: setState(Frame.NORMAL);
        setExtendedState(Frame.NORMAL);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Fingerprint Enrollment and Verification Sample");
        setResizable(false);

        //Fingerprint enrollment UI Button
        final JButton enroll = new JButton("Fingerprint Enrollment");
        enroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onEnroll();
            }
        });

        //Fingerprint verification UI Button
        final JButton verify = new JButton("Fingerprint Verification");
        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onVerify();
            }
        });

        //Save Fingerprint UI Button
        final JButton save = new JButton("Save Fingerprint Template");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSave();
            }
        });

        //Read Fingerprint UI Button
        final JButton load = new JButton("Read Fingerprint Template");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onLoad();
            }
        });

        //Close UI Button
        final JButton quit = new JButton("Close");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        //Set "Verify Fingerprint" and "Save Fingerprint" UI Buttons as INACTIVE
        this.addPropertyChangeListener(TEMPLATE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propChangeEvent) {
                verify.setEnabled(template != null);
                save.setEnabled(template != null);

                if (propChangeEvent.getNewValue() == propChangeEvent.getOldValue()) {
                    return;
                }
                if (template != null) {
                    JOptionPane.showMessageDialog(MainForm.this, "The Fingerprint template is ready for fingerprint verification", "Fingerprint Enrollment", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Set up UI interface
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(4, 1, 0, 5));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        center.add(enroll);
        center.add(verify);
        center.add(save);
        center.add(load);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        bottom.add(quit);

        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);


        pack();

        setSize((int) (getSize().width * 1.6), getSize().height);
        setLocationRelativeTo(null);
        setTemplate(null);
        setVisible(true);

    }

    private void onEnroll() {
        EnrollmentForm form = new EnrollmentForm(this);
        form.setVisible(true);
    }

    private void onVerify() {
        VerificationForm form = new VerificationForm(this);
        form.setVisible(true);
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
                                "Fingerprint saving",
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if (choice == JOptionPane.NO_OPTION) {
                            continue;
                        } else if (choice == JOptionPane.CANCEL_OPTION) {
                            break;
                        }
                    }

                    FileOutputStream stream = new FileOutputStream(file);
                    stream.write(getTemplate().serialize());
                    stream.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Fingerprint saving", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
        }
    }

    private void onLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new TemplateFileFilter());

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream stream = new FileInputStream(chooser.getSelectedFile());
                byte[] data = new byte[stream.available()];
                stream.read(data);
                stream.close();
                DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
                t.deserialize(data);
                setTemplate(t);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Fingerprint loading", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Used to open browser window and open file with extension filter .fpt
    public class TemplateFileFilter extends javax.swing.filechooser.FileFilter {

        //Accept files ends with .fpt
        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(".fpt");
        }

        //When open browser window in "files of type" shows the following description.
        @Override
        public String getDescription() {
            return "Fingerprint Template File (*.fpt)";
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

}
