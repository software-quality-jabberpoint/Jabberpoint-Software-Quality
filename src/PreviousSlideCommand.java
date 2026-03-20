public class PreviousSlideCommand implements Command {
	private final Presentation presentation;

	public PreviousSlideCommand(Presentation presentation) {
		this.presentation = presentation;
	}

	public void execute() {
		presentation.prevSlide();
	}
}
