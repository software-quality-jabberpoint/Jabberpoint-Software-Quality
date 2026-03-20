import java.io.IOException;

public class OpenPresentationCommand implements Command {
	private final Presentation presentation;
	private final Accessor accessor;
	private final String filename;

	public OpenPresentationCommand(Presentation presentation, Accessor accessor, String filename) {
		this.presentation = presentation;
		this.accessor = accessor;
		this.filename = filename;
	}

	public void execute() {
		try {
			presentation.openPresentation(accessor, filename);
		} catch (IOException exception) {
			throw new RuntimeException("Could not open presentation: " + filename, exception);
		}
	}
}
