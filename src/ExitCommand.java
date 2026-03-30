public class ExitCommand implements Command {
	private final JabberPoint jabberPoint;

	public ExitCommand(JabberPoint jabberPoint) {
		this.jabberPoint = jabberPoint;
	}

	public void execute() {
		jabberPoint.exit();
	}
}
