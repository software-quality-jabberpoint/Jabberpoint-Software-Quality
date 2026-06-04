package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {
    @Test
    void presentationStartsEmpty() {
        Presentation presentation = new Presentation();

        assertEquals(0, presentation.getSize(), "Presentation should start empty");
        assertEquals(-1, presentation.getSlideNumber(), "Presentation should start without a current slide");
        assertNull(presentation.getCurrentSlide(), "Current slide should be null when presentation is empty");
    }

    @Test
    void appendStoresSlidesAndSelectsFirstSlide() {
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

    @Test
    void navigationStaysInsideSlideBounds() {
        Presentation presentation = new Presentation();
        presentation.append(new Slide());
        presentation.append(new Slide());

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

    @Test
    void observersReceiveLifecycleNotifications() {
        Presentation presentation = new Presentation();
        CountingObserver observer = new CountingObserver();

        presentation.addObserver(observer);
        assertEquals(1, observer.updateCount, "Observer should receive an immediate update when registered");
        assertSame(presentation, observer.lastPresentation, "Observer should receive the presentation instance");

        presentation.append(new Slide());
        assertEquals(2, observer.updateCount, "Appending the first slide should notify observers once");

        presentation.nextSlide();
        assertEquals(2, observer.updateCount, "Navigation past the end should not notify observers");

        presentation.setTitle("Demo");
        assertEquals(3, observer.updateCount, "Changing the title should notify observers");

        presentation.removeObserver(observer);
        presentation.clear();
        assertEquals(3, observer.updateCount, "Removed observers should no longer receive notifications");
    }

    private static final class CountingObserver implements PresentationObserver {
        private int updateCount;
        private Presentation lastPresentation;

        @Override
        public void update(Presentation presentation) {
            updateCount++;
            lastPresentation = presentation;
        }
    }
}
