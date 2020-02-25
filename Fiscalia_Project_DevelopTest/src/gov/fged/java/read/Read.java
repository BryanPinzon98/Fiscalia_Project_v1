package gov.fged.java.read;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import gov.fged.java.main.MainClass;
import gov.fged.java.resources.TemplateFileFilter;

import javax.swing.*;
import java.io.FileInputStream;

public class Read extends JFrame{

    public Read() {
        onRead();
    }

    private void onRead() {

        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new TemplateFileFilter());

        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try{
                FileInputStream stream = new FileInputStream(chooser.getSelectedFile());
                byte[] data = new byte[stream.available()];
                stream.read(data);
                stream.close();
                DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
                t.deserialize(data);

                MainClass mainClass = MainClass.getInstance();
                mainClass.setTemplate(t);

                System.out.println("Read successful.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Fingerprint loading", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
