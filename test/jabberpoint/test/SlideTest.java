package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlideTest {
    @Test
    void slideStoresItemsAndIgnoresNullItems() {
        Slide slide = new Slide();
        slide.setTitle("Agenda");
        slide.append(1, "Intro");
        BitmapItem bitmapItem = new BitmapItem(2, "missing-image.jpg");
        slide.append(bitmapItem);
        slide.append((SlideItem) null);

        assertEquals("Agenda", slide.getTitle(), "Slide title should be stored");
        assertEquals(2, slide.getSize(), "Only non-null slide items should be stored");
        assertInstanceOf(TextItem.class, slide.getSlideItem(0), "append(level, message) should create a TextItem");
        assertSame(bitmapItem, slide.getSlideItem(1), "Slide should retain appended slide item instances");
    }

    @Test
    void slideItemIndexesOutsideBoundsReturnNull() {
        Slide slide = new Slide();
        slide.append(1, "Only item");

        assertNull(slide.getSlideItem(-1), "Negative item indexes should return null");
        assertNull(slide.getSlideItem(1), "Out of range item indexes should return null");
    }
}
