package jabberpoint.command;

import jabberpoint.core.Presentation;
import jabberpoint.io.PresentationReader;

import javax.swing.JOptionPane;
import java.io.IOException;

public class OpenPresentationCommand implements Command {
    private Presentation presentation;
    private PresentationReader presentationReader;

    public OpenPresentationCommand(Presentation presentation, PresentationReader presentationReader) {
        this.presentation = presentation;
        this.presentationReader = presentationReader;
    }

    @Override
    public void execute() {
        String filename = JOptionPane.showInputDialog("File name?");
        if (filename == null || filename.trim().isEmpty()) {
            return; // user cancelled or entered nothing; keep current presentation
        }
        try {
            presentation.clear();
            presentationReader.loadFile(presentation, filename);
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Error: " + ex,
                    "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
