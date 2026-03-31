public class DemoPresentationTest {
	public static void runAll() {
		testDemoPresentationLoadsSlides();
	}

	private static void testDemoPresentationLoadsSlides() {
		Presentation p = new Presentation();
		Accessor demo = Accessor.getDemoAccessor();
		try {
			demo.loadFile(p, "");
		} catch (Exception e) {
			throw new AssertionError("Demo accessor load should not throw", e);
		}

		assertTrue(p.getSize() > 0, "Demo presentation should load slides");
		assertNotNull(p.getTitle(), "Demo presentation should set a title");
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	private static void assertNotNull(Object value, String message) {
		if (value == null) {
			throw new AssertionError(message);
		}
	}
}
