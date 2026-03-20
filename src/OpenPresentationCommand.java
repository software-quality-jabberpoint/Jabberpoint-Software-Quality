import java.io.IOException;

public class OpenPresentationCommand implements Command {
	private static final String TESTFILE = "test.xml";
	private static final String IOEX = "IO Exception: ";

	private final Presentation presentation;

	public OpenPresentationCommand(Presentation presentation) {
		this.presentation = presentation;
	}

	public void execute() {
		presentation.clear();
		Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.loadFile(presentation, TESTFILE);
			presentation.setSlideNumber(0);
		}
		catch (IOException exception) {
			System.err.println(IOEX + exception);
		}
	}
}
