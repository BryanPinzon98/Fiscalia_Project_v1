package gov.fged.java.storage;

import gov.fged.java.main.MainClass;
import gov.fged.java.resources.TemplateFileFilter;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

public class Storage extends JFrame {


    public Storage() {
        onSave();
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

                    MainClass mainClass = MainClass.getInstance();

                    FileOutputStream stream = new FileOutputStream(file);
                    stream.write(mainClass.getTemplate().serialize());
                    stream.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Fingerprint saving", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
        }
    }
}
