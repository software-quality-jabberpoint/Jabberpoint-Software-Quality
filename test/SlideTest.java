public class SlideTest {
	public static void runAll() {
		testSlideItemStorage();
		testSlideItemBounds();
	}

	private static void testSlideItemStorage() {
		Slide slide = new Slide();
		slide.setTitle("Agenda");
		slide.append(1, "Intro");
		BitmapItem bitmapItem = new BitmapItem(2, "missing-image.jpg");
		slide.append(bitmapItem);
		slide.append((SlideItem) null);

		assertEquals("Agenda", slide.getTitle(), "Slide title should be stored");
		assertEquals(2, slide.getSize(), "Only non-null slide items should be stored");
		assertTrue(slide.getSlideItem(0) instanceof TextItem, "append(level, message) should create a TextItem");
		assertSame(bitmapItem, slide.getSlideItem(1), "Slide should retain appended slide item instances");
	}

	private static void testSlideItemBounds() {
		Slide slide = new Slide();
		slide.append(1, "Only item");

		assertNull(slide.getSlideItem(-1), "Negative item indexes should return null");
		assertNull(slide.getSlideItem(1), "Out of range item indexes should return null");
	}

	private static void assertEquals(String expected, String actual, String message) {
		if (expected == null ? actual != null : !expected.equals(actual)) {
			throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
		}
	}

	private static void assertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
		}
	}

	private static void assertNull(Object value, String message) {
		if (value != null) {
			throw new AssertionError(message + " Expected null but was: " + value);
		}
	}

	private static void assertSame(Object expected, Object actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message);
		}
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}
