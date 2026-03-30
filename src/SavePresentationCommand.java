import javax.swing.JOptionPane;
import java.io.IOException;

public class SavePresentationCommand implements Command {
    private Presentation presentation;

    public SavePresentationCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        String filename = JOptionPane.showInputDialog("File name?");
        try {
            new XMLAccessor().saveFile(presentation, filename);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Error: " + ex,
                    "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
