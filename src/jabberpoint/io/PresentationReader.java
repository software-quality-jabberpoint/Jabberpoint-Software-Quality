package jabberpoint.io;

import jabberpoint.core.Presentation;

import java.io.IOException;

public interface PresentationReader {
	void loadFile(Presentation presentation, String filename) throws IOException;
}
