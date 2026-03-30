import java.awt.Canvas;
import java.awt.event.KeyEvent;

public class KeyControllerTest {
    public static void runAll() {
        testKeyControllerCreation();
        testNextKeyMappings();
        testPreviousKeyMappings();
        testQuitKeyMapping();
    }

    private static void testKeyControllerCreation() {
        Presentation presentation = new Presentation();
        TestJabberPoint jabberPoint = new TestJabberPoint();

        KeyController controller = new KeyController(presentation, jabberPoint);
        assertNotNull(controller, "KeyController should be created successfully");
    }

    private static void testNextKeyMappings() {
        Presentation presentation = twoSlidePresentation();
        TestJabberPoint jabberPoint = new TestJabberPoint();
        KeyController controller = new KeyController(presentation, jabberPoint);

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

    private static void testPreviousKeyMappings() {
        Presentation presentation = twoSlidePresentation();
        TestJabberPoint jabberPoint = new TestJabberPoint();
        KeyController controller = new KeyController(presentation, jabberPoint);

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

    private static void testQuitKeyMapping() {
        Presentation presentation = new Presentation();
        TestJabberPoint jabberPoint = new TestJabberPoint();
        KeyController controller = new KeyController(presentation, jabberPoint);

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
        return new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message + " Expected non-null value");
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static class TestJabberPoint extends JabberPoint {
        private boolean exitCalled = false;

        @Override
        public void exit() {
            exitCalled = true;
        }
    }
}
