package jabberpoint.command;

import jabberpoint.core.Presentation;

public class PreviousSlideCommand implements Command {
    private Presentation presentation;

    public PreviousSlideCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.prevSlide();
    }
}
