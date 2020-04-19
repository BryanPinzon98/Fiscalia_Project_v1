package main;

import UIController.SignUpLayout.SignUpLayoutController;
import com.digitalpersona.onetouch.DPFPTemplate;
import enrollment.Enrollment;
import javafx.fxml.FXMLLoader;
import read.Read;
import verify.Verify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class MainClass extends JFrame {

    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    private static MainClass mainClass = null;
    private SignUpLayoutController signUpLayoutControllerClass = null;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeLayout.fxml"));
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


        final JButton readButton = new JButton("Read");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onRead();
            }
        });

        final JButton verifyButton = new JButton("Verify");
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onVerify();
            }
        });

        this.addPropertyChangeListener(TEMPLATE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propChangeEvent) {

                if (propChangeEvent.getNewValue() == propChangeEvent.getOldValue()) {
                    return;
                }
                if (template != null) {
                    System.out.println("The Fingerprint template is ready for fingerprint verification");
                    //Envío del template de la huella.
                    signUpLayoutControllerClass.setFingerprintTemplate(template);
                }
            }
        });

        JPanel centerButtons = new JPanel();
        centerButtons.setLayout(new GridLayout(4, 1, 0, 5));
        centerButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        centerButtons.add(enrollmentButton);
        centerButtons.add(readButton);
        centerButtons.add(verifyButton);

        setLayout(new BorderLayout());
        add(centerButtons, BorderLayout.CENTER);

        setVisible(true);
    }

    private void onVerify() {
        Verify verify = new Verify();
        verify.setVisible(true);
    }

    private void onRead() {
        Read read = new Read();
    }

    private void onEnroll() {
        Enrollment enrollmentProcess = new Enrollment();
        enrollmentProcess.setVisible(true);
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setSignUpLayoutControllerClass(SignUpLayoutController signUpLayoutControllerClass) {

        if (signUpLayoutControllerClass != null) {
            this.signUpLayoutControllerClass = signUpLayoutControllerClass;
            System.out.println("La referencia de Sign Up, llego correctamente al main del Main Fingerprint class");
        } else {
            System.out.println("La referencia de Sign Up en el Main de Fingerprint llegó nula");
        }

    }
}
