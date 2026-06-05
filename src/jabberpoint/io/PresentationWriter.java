package jabberpoint.io;

import jabberpoint.core.Presentation;

import java.io.IOException;

public interface PresentationWriter {
	void saveFile(Presentation presentation, String filename) throws IOException;
}
