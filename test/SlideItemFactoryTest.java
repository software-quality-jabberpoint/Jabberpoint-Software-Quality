public class SlideItemFactoryTest {
    public static void runAll() {
        testFactoryCreation();
        testCreateTextItem();
        testCreateBitmapItem();
        testCreateInvalidType();
        testFactoryConstants();
    }

    private static void testFactoryCreation() {
        SlideItemFactory factory = new SlideItemFactory();
        assertNotNull(factory, "SlideItemFactory should be created successfully");
    }

    private static void testCreateTextItem() {
        SlideItemFactory factory = new SlideItemFactory();
        SlideItem item = factory.createSlideItem("text", 1, "Test content");
        
        assertNotNull(item, "Factory should create a slide item for text type");
        assertTrue(item instanceof TextItem, "Factory should create TextItem for text type");
        
        TextItem textItem = (TextItem) item;
        assertEquals(1, textItem.getLevel(), "TextItem should have correct level");
        assertEquals("Test content", textItem.getText(), "TextItem should have correct text");
    }

    private static void testCreateBitmapItem() {
        SlideItemFactory factory = new SlideItemFactory();
        SlideItem item = factory.createSlideItem("image", 2, "test.jpg");
        
        assertNotNull(item, "Factory should create a slide item for image type");
        assertTrue(item instanceof BitmapItem, "Factory should create BitmapItem for image type");
        
        BitmapItem bitmapItem = (BitmapItem) item;
        assertEquals(2, bitmapItem.getLevel(), "BitmapItem should have correct level");
        assertEquals("test.jpg", bitmapItem.getName(), "BitmapItem should have correct image name");
    }

    private static void testCreateInvalidType() {
        SlideItemFactory factory = new SlideItemFactory();
        
        try {
            factory.createSlideItem("invalid", 1, "content");
            throw new AssertionError("Should have thrown IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unknown slide item type"), 
                      "Exception should mention unknown type");
            assertTrue(e.getMessage().contains("invalid"), 
                      "Exception should mention the invalid type");
        }
    }

    private static void testFactoryConstants() {
        assertEquals("text", SlideItemFactory.TEXT, "TEXT constant should be 'text'");
        assertEquals("image", SlideItemFactory.IMAGE, "IMAGE constant should be 'image'");
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
