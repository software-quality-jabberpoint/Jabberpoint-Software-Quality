package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    @Test
    void nextSlideCommandAdvancesUntilLastSlide() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        presentation.append(new Slide());
        presentation.append(new Slide());

        assertEquals(0, presentation.getSlideNumber(), "Should start at slide 0");

        Command nextCommand = new NextSlideCommand(presentation);
        nextCommand.execute();
        assertEquals(1, presentation.getSlideNumber(), "NextSlideCommand should advance to slide 1");

        nextCommand.execute();
        assertEquals(1, presentation.getSlideNumber(), "NextSlideCommand should not go beyond last slide");
    }

    @Test
    void previousSlideCommandMovesBackUntilFirstSlide() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        presentation.append(new Slide());
        presentation.append(new Slide());
        presentation.setSlideNumber(1);

        Command previousCommand = new PreviousSlideCommand(presentation);
        previousCommand.execute();
        assertEquals(0, presentation.getSlideNumber(), "PreviousSlideCommand should go to slide 0");

        previousCommand.execute();
        assertEquals(0, presentation.getSlideNumber(), "PreviousSlideCommand should not go before first slide");
    }

    @Test
    void exitCommandDelegatesToJabberPoint() {
        TestJabberPoint jabberPoint = new TestJabberPoint();
        new ExitCommand(jabberPoint).execute();

        assertTrue(jabberPoint.exitCalled, "ExitCommand should call JabberPoint.exit()");
    }

    @Test
    void openAndSaveCommandsAcceptInjectedAccessors() {
        Presentation presentation = new Presentation();
        Command openCommand = new OpenPresentationCommand(presentation, new TestPresentationReader());
        Command saveCommand = new SavePresentationCommand(presentation, new TestPresentationWriter());

        assertNotNull(openCommand, "OpenPresentationCommand should accept a reader dependency");
        assertNotNull(saveCommand, "SavePresentationCommand should accept a writer dependency");
    }

    private static class TestJabberPoint extends JabberPoint {
        private boolean exitCalled;

        @Override
        public void exit() {
            exitCalled = true;
        }
    }

    private static class TestPresentationReader implements PresentationReader {
        @Override
        public void loadFile(Presentation presentation, String filename) {
            presentation.setTitle("Injected reader");
        }
    }

    private static class TestPresentationWriter implements PresentationWriter {
        @Override
        public void saveFile(Presentation presentation, String filename) {
        }
    }
}
