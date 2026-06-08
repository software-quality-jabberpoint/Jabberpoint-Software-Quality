package jabberpoint.command;

import jabberpoint.JabberPoint;
import jabberpoint.core.Presentation;

/**
 * <p>Default {@link CommandFactory} that wires commands to a single
 * {@link Presentation} and {@link JabberPoint} instance.</p>
 * <p>This is the one place where concrete commands are constructed, keeping
 * the UI controllers free of direct command instantiation.</p>
 */
public class DefaultCommandFactory implements CommandFactory {
    private final Presentation presentation;
    private final JabberPoint jabberPoint;

    public DefaultCommandFactory(Presentation presentation, JabberPoint jabberPoint) {
        this.presentation = presentation;
        this.jabberPoint = jabberPoint;
    }

    @Override
    public Command nextSlide() {
        return new NextSlideCommand(presentation);
    }

    @Override
    public Command previousSlide() {
        return new PreviousSlideCommand(presentation);
    }

    @Override
    public Command openPresentation() {
        return new OpenPresentationCommand(presentation);
    }

    @Override
    public Command savePresentation() {
        return new SavePresentationCommand(presentation);
    }

    @Override
    public Command exit() {
        return new ExitCommand(jabberPoint);
    }
}
