package jabberpoint.command;

import jabberpoint.core.Presentation;
import jabberpoint.io.PresentationReader;
import jabberpoint.io.XMLAccessor;

import javax.swing.JOptionPane;
import java.io.IOException;

public class OpenPresentationCommand implements Command {
    private Presentation presentation;
    private PresentationReader presentationReader;

    public OpenPresentationCommand(Presentation presentation) {
        this(presentation, new XMLAccessor());
    }

    public OpenPresentationCommand(Presentation presentation, PresentationReader presentationReader) {
        this.presentation = presentation;
        this.presentationReader = presentationReader;
    }

    @Override
    public void execute() {
        presentation.clear();
        String filename = JOptionPane.showInputDialog("File name?");
        try {
            presentationReader.loadFile(presentation, filename);
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Error: " + ex,
                    "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
