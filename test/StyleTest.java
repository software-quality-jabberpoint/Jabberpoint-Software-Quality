import java.awt.Font;

public class StyleTest {
	public static void runAll() {
		testGetStyleClampsBounds();
		testGetFontScalesSize();
	}

	private static void testGetStyleClampsBounds() {
		Style.createStyles();

		Style lowStyle = Style.getStyle(-10);
		Style zeroStyle = Style.getStyle(0);
		Style highStyle = Style.getStyle(999);
		Style maxStyle = Style.getStyle(4);

		assertSame(zeroStyle, lowStyle, "Negative levels should map to the lowest style");
		assertSame(maxStyle, highStyle, "Levels above the configured range should map to the highest style");
	}

	private static void testGetFontScalesSize() {
		Style.createStyles();
		Style style = Style.getStyle(2);

		Font scaledFont = style.getFont(0.5f);
		assertEquals(18, scaledFont.getSize(), "Scaled font size should follow the configured style size");
	}

	private static void assertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
		}
	}

	private static void assertSame(Object expected, Object actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message);
		}
	}
}
