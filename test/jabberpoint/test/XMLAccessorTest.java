package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest {
    @Test
    void xmlAccessorCanBeCreated() {
        assertNotNull(new XMLAccessor(), "XMLAccessor should be created successfully");
    }

    @Test
    void loadFileReadsValidXmlPresentation() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();

        assertDoesNotThrow(() -> accessor.loadFile(presentation, "test.xml"),
                "loadFile should not throw for valid XML test input");

        assertEquals("XML-Based Presentation for Jabberpoint", presentation.getTitle(),
                "Should load correct title from test.xml");
        assertEquals(5, presentation.getSize(), "Should load 5 slides from test.xml");

        Slide firstSlide = presentation.getSlide(0);
        assertNotNull(firstSlide, "First slide should exist");
        assertEquals("JabberPoint XML-Demo", firstSlide.getTitle(), "First slide should have correct title");
        assertTrue(firstSlide.getSize() > 0, "First slide should have items");
    }

    @Test
    void loadFilePropagatesIOExceptionWhenFileIsMissing() {
        Style.createStyles();
        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();
        int initialSize = presentation.getSize();
        int initialSlideNumber = presentation.getSlideNumber();

        assertThrows(java.io.IOException.class,
                () -> accessor.loadFile(presentation, "nonexistent.xml"),
                "loadFile should propagate an IOException for missing files instead of swallowing it");

        assertEquals(initialSize, presentation.getSize(), "Presentation size should remain unchanged after failed load");
        assertEquals(initialSlideNumber, presentation.getSlideNumber(), "Slide number should remain unchanged after failed load");
        assertNull(presentation.getTitle(), "Presentation title should remain unchanged after failed load");
    }

    @Test
    void xmlAccessorImplementsPersistenceAbstractions() {
        XMLAccessor accessor = new XMLAccessor();

        assertInstanceOf(Accessor.class, accessor, "XMLAccessor should implement Accessor interface");
        assertInstanceOf(PresentationReader.class, accessor, "XMLAccessor should implement reader interface");
        assertInstanceOf(PresentationWriter.class, accessor, "XMLAccessor should implement writer interface");
    }

    @Test
    void xmlAccessorAcceptsFactoryDependency() {
        assertNotNull(new XMLAccessor(new SlideItemFactory()),
                "XMLAccessor should accept a SlideItemFactory dependency");
    }
}
