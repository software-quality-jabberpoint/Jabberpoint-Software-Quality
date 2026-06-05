package jabberpoint.test;

import jabberpoint.core.Presentation;
import jabberpoint.core.Slide;
import jabberpoint.core.Style;
import jabberpoint.io.XMLAccessor;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLAccessorSaveTest {
	@Test
	void saveFileCreatesOutput() {
		Style.createStyles();
		Presentation presentation = new Presentation();
		presentation.setTitle("Save Test");
		Slide slide = new Slide();
		slide.setTitle("S1");
		slide.append(1, "Item1");
		presentation.append(slide);

		File output = assertDoesNotThrow(() -> {
			File file = File.createTempFile("jabberpoint-save-test", ".xml");
			file.deleteOnExit();
			return file;
		}, "Failed to create temporary output file");

		assertDoesNotThrow(() -> new XMLAccessor().saveFile(presentation, output.getAbsolutePath()),
				"saveFile should not throw");
		assertTrue(output.length() > 0, "Saved XML file should not be empty");
	}
}
