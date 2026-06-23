package jabberpoint.command;

/**
 * <p>Abstraction that supplies {@link Command} instances to the UI controllers.</p>
 * <p>Controllers depend on this interface instead of constructing concrete
 * command classes directly, satisfying the Dependency Inversion Principle.</p>
 */
public interface CommandFactory {
    Command nextSlide();
    Command previousSlide();
    Command openPresentation();
    Command savePresentation();
    Command exit();
}
