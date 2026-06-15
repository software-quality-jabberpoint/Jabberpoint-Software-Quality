package jabberpoint.command;

import jabberpoint.core.Presentation;
import jabberpoint.io.PresentationWriter;

import javax.swing.JOptionPane;
import java.io.IOException;

public class SavePresentationCommand implements Command {
    private Presentation presentation;
    private PresentationWriter presentationWriter;

    public SavePresentationCommand(Presentation presentation, PresentationWriter presentationWriter) {
        this.presentation = presentation;
        this.presentationWriter = presentationWriter;
    }

    @Override
    public void execute() {
        String filename = JOptionPane.showInputDialog("File name?");
        try {
            presentationWriter.saveFile(presentation, filename);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Error: " + ex,
                    "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
