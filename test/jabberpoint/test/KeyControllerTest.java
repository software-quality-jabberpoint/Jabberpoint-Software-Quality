package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

class KeyControllerTest {
    @Test
    void keyControllerCanBeCreated() {
        Presentation presentation = new Presentation();
        TestJabberPoint jabberPoint = new TestJabberPoint();

        assertNotNull(new KeyController(presentation, jabberPoint), "KeyController should be created successfully");
    }

    @Test
    void nextKeyMappingsAdvanceSlide() {
        Presentation presentation = twoSlidePresentation();
        KeyController controller = new KeyController(presentation, new TestJabberPoint());

        assertEquals(0, presentation.getSlideNumber(), "Should start at slide 0");
        controller.keyPressed(keyEvent(KeyEvent.VK_PAGE_DOWN));
        assertEquals(1, presentation.getSlideNumber(), "Page down should advance slide");

        presentation.setSlideNumber(0);
        controller.keyPressed(keyEvent(KeyEvent.VK_DOWN));
        assertEquals(1, presentation.getSlideNumber(), "Down arrow should advance slide");

        presentation.setSlideNumber(0);
        controller.keyPressed(keyEvent(KeyEvent.VK_ENTER));
        assertEquals(1, presentation.getSlideNumber(), "Enter should advance slide");

        presentation.setSlideNumber(0);
        controller.keyPressed(keyEvent('+'));
        assertEquals(1, presentation.getSlideNumber(), "+ should advance slide");
    }

    @Test
    void previousKeyMappingsMoveBack() {
        Presentation presentation = twoSlidePresentation();
        KeyController controller = new KeyController(presentation, new TestJabberPoint());

        presentation.setSlideNumber(1);
        controller.keyPressed(keyEvent(KeyEvent.VK_PAGE_UP));
        assertEquals(0, presentation.getSlideNumber(), "Page up should go to previous slide");

        presentation.setSlideNumber(1);
        controller.keyPressed(keyEvent(KeyEvent.VK_UP));
        assertEquals(0, presentation.getSlideNumber(), "Up arrow should go to previous slide");

        presentation.setSlideNumber(1);
        controller.keyPressed(keyEvent('-'));
        assertEquals(0, presentation.getSlideNumber(), "- should go to previous slide");

        controller.keyPressed(keyEvent('-'));
        assertEquals(0, presentation.getSlideNumber(), "Should not go before first slide");
    }

    @Test
    void quitKeyMappingTriggersExitCommand() {
        TestJabberPoint jabberPoint = new TestJabberPoint();
        KeyController controller = new KeyController(new Presentation(), jabberPoint);

        controller.keyPressed(keyEvent(KeyEvent.VK_Q));
        assertTrue(jabberPoint.exitCalled, "Q should trigger exit through ExitCommand");
    }

    private static Presentation twoSlidePresentation() {
        Presentation presentation = new Presentation();
        presentation.append(new Slide());
        presentation.append(new Slide());
        return presentation;
    }

    private static KeyEvent keyEvent(int keyCode) {
        return new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    private static class TestJabberPoint extends JabberPoint {
        private boolean exitCalled;

        @Override
        public void exit() {
            exitCalled = true;
        }
    }
}
