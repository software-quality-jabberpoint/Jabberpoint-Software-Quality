import java.io.IOException;

public class SavePresentationCommand implements Command {
	private final Presentation presentation;
	private final Accessor accessor;
	private final String filename;

	public SavePresentationCommand(Presentation presentation, Accessor accessor, String filename) {
		this.presentation = presentation;
		this.accessor = accessor;
		this.filename = filename;
	}

	public void execute() {
		try {
			presentation.savePresentation(accessor, filename);
		} catch (IOException exception) {
			throw new RuntimeException("Could not save presentation: " + filename, exception);
		}
	}
}
