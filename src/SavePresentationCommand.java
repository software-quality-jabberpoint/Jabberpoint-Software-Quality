import java.io.IOException;

public class SavePresentationCommand implements Command {
	private static final String SAVEFILE = "dump.xml";
	private static final String IOEX = "IO Exception: ";

	private final Presentation presentation;

	public SavePresentationCommand(Presentation presentation) {
		this.presentation = presentation;
	}

	public void execute() {
		Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.saveFile(presentation, SAVEFILE);
		}
		catch (IOException exception) {
			System.err.println(IOEX + exception);
		}
	}
}
