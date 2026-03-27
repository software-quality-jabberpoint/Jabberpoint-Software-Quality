import javax.swing.JOptionPane;
import java.io.IOException;

public class OpenPresentationCommand implements Command {
    private Presentation presentation;

    public OpenPresentationCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.clear();
        String filename = JOptionPane.showInputDialog("File name?");
        try {
            new XMLAccessor().loadFile(presentation, filename);
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Error: " + ex,
                    "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
