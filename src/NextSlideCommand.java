public class NextSlideCommand implements Command {
	private final Presentation presentation;

	public NextSlideCommand(Presentation presentation) {
		this.presentation = presentation;
	}

	public void execute() {
		presentation.nextSlide();
	}
}
