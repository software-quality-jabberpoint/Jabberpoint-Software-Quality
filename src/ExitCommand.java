public class ExitCommand implements Command {
	private final Presentation presentation;
	private final int exitCode;

	public ExitCommand(Presentation presentation) {
		this(presentation, 0);
	}

	public ExitCommand(Presentation presentation, int exitCode) {
		this.presentation = presentation;
		this.exitCode = exitCode;
	}

	public void execute() {
		presentation.exit(exitCode);
	}
}
