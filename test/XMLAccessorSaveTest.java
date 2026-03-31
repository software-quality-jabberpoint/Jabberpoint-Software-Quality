import java.io.File;

public class XMLAccessorSaveTest {
	public static void runAll() {
		testSaveFileCreatesOutput();
	}

	private static void testSaveFileCreatesOutput() {
		Style.createStyles();
		Presentation p = new Presentation();
		p.setTitle("Save Test");
		Slide slide = new Slide();
		slide.setTitle("S1");
		slide.append(1, "Item1");
		p.append(slide);

		File out;
		try {
			out = File.createTempFile("jabberpoint-save-test", ".xml");
			out.deleteOnExit();
		} catch (Exception e) {
			throw new AssertionError("Failed to create temporary output file", e);
		}

		try {
			new XMLAccessor().saveFile(p, out.getAbsolutePath());
		} catch (Exception e) {
			throw new AssertionError("saveFile should not throw", e);
		}

		assertTrue(out.length() > 0, "Saved XML file should not be empty");
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}
