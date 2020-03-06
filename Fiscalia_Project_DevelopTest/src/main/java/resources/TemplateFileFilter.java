package resources;

import java.io.File;

public class TemplateFileFilter extends javax.swing.filechooser.FileFilter {
    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".fpt");
    }

    @Override
    public String getDescription() {
        return "FingerTemplate File (*.fpt)";
    }
}
