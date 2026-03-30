public class PresentationTest {
	public static void runAll() {
		testInitialState();
		testAppendAndGetSlide();
		testNavigationBounds();
		testObserverNotificationLifecycle();
	}

	private static void testInitialState() {
		Presentation presentation = new Presentation();
		assertEquals(0, presentation.getSize(), "Presentation should start empty");
		assertEquals(-1, presentation.getSlideNumber(), "Presentation should start without a current slide");
		assertNull(presentation.getCurrentSlide(), "Current slide should be null when presentation is empty");
	}

	private static void testAppendAndGetSlide() {
		Presentation presentation = new Presentation();
		Slide firstSlide = new Slide();
		firstSlide.setTitle("First");
		Slide secondSlide = new Slide();
		secondSlide.setTitle("Second");

		presentation.append(firstSlide);
		presentation.append(secondSlide);

		assertEquals(2, presentation.getSize(), "Appending slides should increase presentation size");
		assertEquals(0, presentation.getSlideNumber(), "First append should select the first slide");
		assertSame(firstSlide, presentation.getSlide(0), "getSlide(0) should return the first appended slide");
		assertSame(secondSlide, presentation.getSlide(1), "getSlide(1) should return the second appended slide");
		assertNull(presentation.getSlide(-1), "Negative slide indexes should return null");
		assertNull(presentation.getSlide(2), "Out of range slide indexes should return null");
	}

	private static void testNavigationBounds() {
		Presentation presentation = new Presentation();
		Slide firstSlide = new Slide();
		Slide secondSlide = new Slide();

		presentation.append(firstSlide);
		presentation.append(secondSlide);

		presentation.nextSlide();
		assertEquals(1, presentation.getSlideNumber(), "nextSlide should advance to the next slide");

		presentation.nextSlide();
		assertEquals(1, presentation.getSlideNumber(), "nextSlide should stop at the last slide");

		presentation.prevSlide();
		assertEquals(0, presentation.getSlideNumber(), "prevSlide should move back one slide");

		presentation.prevSlide();
		assertEquals(0, presentation.getSlideNumber(), "prevSlide should stop at the first slide");

		presentation.clear();
		assertEquals(0, presentation.getSize(), "clear should remove all slides");
		assertEquals(-1, presentation.getSlideNumber(), "clear should reset current slide number");
		assertNull(presentation.getCurrentSlide(), "clear should leave no current slide");
	}

	private static void testObserverNotificationLifecycle() {
		Presentation presentation = new Presentation();
		CountingObserver observer = new CountingObserver();

		presentation.addObserver(observer);
		assertEquals(1, observer.updateCount, "Observer should receive an immediate update when registered");
		assertSame(presentation, observer.lastPresentation, "Observer should receive the presentation instance");

		Slide slide = new Slide();
		presentation.append(slide);
		assertEquals(2, observer.updateCount, "Appending the first slide should notify observers once");

		presentation.nextSlide();
		assertEquals(2, observer.updateCount, "Navigation past the end should not notify observers");

		presentation.setTitle("Demo");
		assertEquals(3, observer.updateCount, "Changing the title should notify observers");

		presentation.removeObserver(observer);
		presentation.clear();
		assertEquals(3, observer.updateCount, "Removed observers should no longer receive notifications");
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

	private static final class CountingObserver implements PresentationObserver {
		private int updateCount;
		private Presentation lastPresentation;

		public void update(Presentation presentation) {
			updateCount++;
			lastPresentation = presentation;
		}
	}
}
