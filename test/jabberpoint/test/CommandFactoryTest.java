package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.Command;
import jabberpoint.command.CommandFactory;
import jabberpoint.command.DefaultCommandFactory;
import jabberpoint.core.Presentation;
import jabberpoint.core.Slide;
import jabberpoint.core.Style;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @BeforeAll
    static void initStyles() {
        Style.createStyles();
    }

    @Test
    void factoryProducesNonNullCommands() {
        CommandFactory factory = new DefaultCommandFactory(new Presentation(), new TestJabberPoint());

        assertNotNull(factory.nextSlide(), "nextSlide should produce a command");
        assertNotNull(factory.previousSlide(), "previousSlide should produce a command");
        assertNotNull(factory.openPresentation(), "openPresentation should produce a command");
        assertNotNull(factory.savePresentation(), "savePresentation should produce a command");
        assertNotNull(factory.exit(), "exit should produce a command");
    }

    @Test
    void nextAndPreviousCommandsAffectTheSamePresentation() {
        Presentation presentation = new Presentation();
        presentation.append(new Slide());
        presentation.append(new Slide());
        CommandFactory factory = new DefaultCommandFactory(presentation, new TestJabberPoint());

        Command next = factory.nextSlide();
        next.execute();
        assertEquals(1, presentation.getSlideNumber(), "nextSlide command should advance the wired presentation");

        Command previous = factory.previousSlide();
        previous.execute();
        assertEquals(0, presentation.getSlideNumber(), "previousSlide command should move back the wired presentation");
    }

    @Test
    void exitCommandDelegatesToWiredJabberPoint() {
        TestJabberPoint jabberPoint = new TestJabberPoint();
        CommandFactory factory = new DefaultCommandFactory(new Presentation(), jabberPoint);

        factory.exit().execute();

        assertTrue(jabberPoint.exitCalled, "exit command should call exit() on the wired JabberPoint");
    }

    private static class TestJabberPoint extends JabberPoint {
        private boolean exitCalled;

        @Override
        public void exit() {
            exitCalled = true;
        }
    }
}
