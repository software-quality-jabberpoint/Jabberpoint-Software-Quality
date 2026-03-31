public class CommandTest {
    public static void runAll() {
        testNextSlideCommand();
        testPreviousSlideCommand();
        testExitCommand();
        testOpenAndSaveCommandConstruction();
    }

    private static void testNextSlideCommand() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        
        // Add test slides
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide1);
        presentation.append(slide2);
        
        // Test initial state
        assertEquals(0, presentation.getSlideNumber(), "Should start at slide 0");
        
        // Test next slide command
        Command nextCmd = new NextSlideCommand(presentation);
        nextCmd.execute();
        assertEquals(1, presentation.getSlideNumber(), "NextSlideCommand should advance to slide 1");
        
        // Test boundary - should not go beyond last slide
        nextCmd.execute();
        assertEquals(1, presentation.getSlideNumber(), "NextSlideCommand should not go beyond last slide");
    }

    private static void testPreviousSlideCommand() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        
        // Add test slides
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide1);
        presentation.append(slide2);
        
        // Start at slide 1
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber(), "Should start at slide 1");
        
        // Test previous slide command
        Command prevCmd = new PreviousSlideCommand(presentation);
        prevCmd.execute();
        assertEquals(0, presentation.getSlideNumber(), "PreviousSlideCommand should go to slide 0");
        
        // Test boundary - should not go before first slide
        prevCmd.execute();
        assertEquals(0, presentation.getSlideNumber(), "PreviousSlideCommand should not go before first slide");
    }

    private static void testExitCommand() {
        TestJabberPoint jabberPoint = new TestJabberPoint();
        Command exitCmd = new ExitCommand(jabberPoint);
        exitCmd.execute();

        assertTrue(jabberPoint.exitCalled, "ExitCommand should call JabberPoint.exit()");
    }

    private static void testOpenAndSaveCommandConstruction() {
        Presentation presentation = new Presentation();
        Command openCmd = new OpenPresentationCommand(presentation);
        Command saveCmd = new SavePresentationCommand(presentation);

        assertNotNull(openCmd, "OpenPresentationCommand should be created successfully");
        assertNotNull(saveCmd, "SavePresentationCommand should be created successfully");
        assertTrue(openCmd instanceof Command, "OpenPresentationCommand should implement Command");
        assertTrue(saveCmd instanceof Command, "SavePresentationCommand should implement Command");
    }

    // Helper assertion methods
    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message + " Expected non-null value");
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
