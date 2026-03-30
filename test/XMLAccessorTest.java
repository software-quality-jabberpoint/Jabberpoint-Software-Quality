import java.io.IOException;

public class XMLAccessorTest {
    public static void runAll() {
        testXMLAccessorCreation();
        testLoadValidXML();
        testLoadNonExistentFile();
        testXMLAccessorImplementsAccessor();
    }

    private static void testXMLAccessorCreation() {
        XMLAccessor accessor = new XMLAccessor();
        assertNotNull(accessor, "XMLAccessor should be created successfully");
    }

    private static void testLoadValidXML() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();

        try {
            accessor.loadFile(presentation, "test.xml");
        } catch (IOException e) {
            throw new AssertionError("loadFile should not throw for valid XML test input", e);
        }

        // Verify the presentation was loaded correctly
        assertTrue(presentation.getSize() > 0, "Presentation should have slides after loading");
        assertNotNull(presentation.getTitle(), "Presentation should have a title");
        assertTrue(presentation.getTitle().length() > 0, "Presentation title should not be empty");

        // Verify specific content from test.xml
        assertEquals("XML-Based Presentation for Jabberpoint", presentation.getTitle(),
                "Should load correct title from test.xml");
        assertEquals(5, presentation.getSize(), "Should load 5 slides from test.xml");

        // Verify first slide content
        Slide firstSlide = presentation.getSlide(0);
        assertNotNull(firstSlide, "First slide should exist");
        assertEquals("JabberPoint XML-Demo", firstSlide.getTitle(), "First slide should have correct title");
        assertTrue(firstSlide.getSize() > 0, "First slide should have items");
    }

    private static void testLoadNonExistentFile() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();
        
        // The XMLAccessor catches IOException and prints to stderr, so we can't test the exception
        // Instead, we test that the presentation remains unchanged after trying to load a non-existent file
        int initialSize = presentation.getSize();
        int initialSlideNumber = presentation.getSlideNumber();
        
        try {
            accessor.loadFile(presentation, "nonexistent.xml");
        } catch (IOException e) {
            throw new AssertionError("loadFile should handle missing files internally in current implementation", e);
        }

        assertEquals(initialSize, presentation.getSize(), "Presentation size should remain unchanged after failed load");
        assertEquals(initialSlideNumber, presentation.getSlideNumber(), "Slide number should remain unchanged after failed load");
        assertTrue(presentation.getTitle() == null, "Presentation title should remain unchanged after failed load");
    }

    private static void testXMLAccessorImplementsAccessor() {
        XMLAccessor accessor = new XMLAccessor();
        assertTrue(accessor instanceof Accessor, "XMLAccessor should implement Accessor interface");
    }

    // Helper assertion methods
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

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " Expected: '" + expected + "', Actual: '" + actual + "'");
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }
}
