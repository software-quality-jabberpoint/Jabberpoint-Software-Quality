package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemFactoryTest {
    @Test
    void factoryCanBeCreated() {
        assertNotNull(new SlideItemFactory(), "SlideItemFactory should be created successfully");
    }

    @Test
    void factoryCreatesTextItems() {
        SlideItem item = new SlideItemFactory().createSlideItem("text", 1, "Test content");

        assertInstanceOf(TextItem.class, item, "Factory should create TextItem for text type");
        TextItem textItem = (TextItem) item;
        assertEquals(1, textItem.getLevel(), "TextItem should have correct level");
        assertEquals("Test content", textItem.getText(), "TextItem should have correct text");
    }

    @Test
    void factoryCreatesBitmapItems() {
        SlideItem item = new SlideItemFactory().createSlideItem("image", 2, "test.jpg");

        assertInstanceOf(BitmapItem.class, item, "Factory should create BitmapItem for image type");
        BitmapItem bitmapItem = (BitmapItem) item;
        assertEquals(2, bitmapItem.getLevel(), "BitmapItem should have correct level");
        assertEquals("test.jpg", bitmapItem.getName(), "BitmapItem should have correct image name");
    }

    @Test
    void factoryRejectsInvalidTypes() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SlideItemFactory().createSlideItem("invalid", 1, "content"));

        assertTrue(exception.getMessage().contains("Unknown slide item type"), "Exception should mention unknown type");
        assertTrue(exception.getMessage().contains("invalid"), "Exception should mention the invalid type");
    }

    @Test
    void factoryConstantsMatchXmlKinds() {
        assertEquals("text", SlideItemFactory.TEXT, "TEXT constant should be 'text'");
        assertEquals("image", SlideItemFactory.IMAGE, "IMAGE constant should be 'image'");
    }

    @Test
    void slideItemsAcceptRendererDependencies() {
        TextItem textItem = new TextItem(1, "Text", new TextItemRenderer());
        BitmapItem bitmapItem = new BitmapItem(2, "missing-image.jpg", new BitmapItemRenderer());

        assertEquals("Text", textItem.getText(), "TextItem should keep text data separate from renderer");
        assertEquals("missing-image.jpg", bitmapItem.getName(), "BitmapItem should keep image name separate from renderer");
    }
}
