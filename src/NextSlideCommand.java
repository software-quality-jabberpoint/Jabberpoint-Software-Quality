public class NextSlideCommand implements Command {
    private Presentation presentation;

    public NextSlideCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.nextSlide();
    }
}
