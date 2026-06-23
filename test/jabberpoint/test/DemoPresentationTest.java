package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {
    @Test
    void demoPresentationLoadsSlides() {
        Presentation presentation = new Presentation();
        PresentationReader demo = new DemoPresentation();

        assertDoesNotThrow(() -> demo.loadFile(presentation, ""), "Demo presentation should load without throwing");
        assertEquals("Demo Presentation", presentation.getTitle(), "Demo presentation should set title");
        assertTrue(presentation.getSize() > 0, "Demo presentation should load slides");
    }

    @Test
    void demoPresentationOnlyImplementsReader() {
        DemoPresentation demo = new DemoPresentation();

        assertInstanceOf(PresentationReader.class, demo, "Demo presentation should be readable");
        assertFalse(demo instanceof PresentationWriter, "Demo presentation should not pretend it can save");
    }
}
