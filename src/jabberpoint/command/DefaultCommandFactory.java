package jabberpoint.command;

import jabberpoint.JabberPoint;
import jabberpoint.core.Presentation;
import jabberpoint.io.PresentationReader;
import jabberpoint.io.PresentationWriter;
import jabberpoint.io.XMLAccessor;

/**
 * <p>Default {@link CommandFactory} that wires commands to a single
 * {@link Presentation} and {@link JabberPoint} instance.</p>
 * <p>This is the one place where concrete commands are constructed, keeping
 * the UI controllers free of direct command instantiation.</p>
 */
public class DefaultCommandFactory implements CommandFactory {
    private final Presentation presentation;
    private final JabberPoint jabberPoint;
    private final PresentationReader presentationReader;
    private final PresentationWriter presentationWriter;

    public DefaultCommandFactory(Presentation presentation, JabberPoint jabberPoint) {
        this(presentation, jabberPoint, new XMLAccessor());
    }

    private DefaultCommandFactory(Presentation presentation, JabberPoint jabberPoint, XMLAccessor xmlAccessor) {
        this(presentation, jabberPoint, xmlAccessor, xmlAccessor);
    }

    public DefaultCommandFactory(Presentation presentation, JabberPoint jabberPoint,
            PresentationReader presentationReader, PresentationWriter presentationWriter) {
        this.presentation = presentation;
        this.jabberPoint = jabberPoint;
        this.presentationReader = presentationReader;
        this.presentationWriter = presentationWriter;
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
        return new OpenPresentationCommand(presentation, presentationReader);
    }

    @Override
    public Command savePresentation() {
        return new SavePresentationCommand(presentation, presentationWriter);
    }

    @Override
    public Command exit() {
        return new ExitCommand(jabberPoint);
    }
}
