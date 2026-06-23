package jabberpoint.test;

import jabberpoint.core.Presentation;
import jabberpoint.core.Slide;
import jabberpoint.core.Style;
import jabberpoint.ui.SlideViewerComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SlideViewerComponentTest {

    @BeforeAll
    static void initStyles() {
        Style.createStyles();
    }

    @Test
    void registersAsObserverAndUpdatesTitleOnChange() {
        Presentation presentation = new Presentation();
        String[] lastTitle = new String[1];
        new SlideViewerComponent(presentation, title -> lastTitle[0] = title);

        presentation.setTitle("My Deck");
        Slide slide = new Slide();
        slide.setTitle("Slide one");
        slide.append(1, "Hello");
        presentation.append(slide); // triggers observer notification

        assertEquals("My Deck", lastTitle[0], "Component should push presentation title to the TitleView");
    }

    @Test
    void preferredSizeMatchesSlideDimensions() {
        Presentation presentation = new Presentation();
        SlideViewerComponent component = new SlideViewerComponent(presentation, title -> { });

        assertEquals(Slide.WIDTH, component.getPreferredSize().width, "Preferred width should match slide width");
        assertEquals(Slide.HEIGHT, component.getPreferredSize().height, "Preferred height should match slide height");
    }

    @Test
    void paintComponentDrawsCurrentSlideWithoutError() {
        Presentation presentation = new Presentation();
        SlideViewerComponent component = new SlideViewerComponent(presentation, title -> { });
        Slide slide = new Slide();
        slide.setTitle("Painted slide");
        slide.append(1, "Item");
        presentation.append(slide);
        component.setSize(Slide.WIDTH, Slide.HEIGHT);

        BufferedImage image = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        assertDoesNotThrow(() -> component.paintComponent(graphics),
                "paintComponent should render the current slide without throwing");
    }

    @Test
    void paintComponentReturnsEarlyWhenNoSlideSelected() {
        Presentation presentation = new Presentation();
        SlideViewerComponent component = new SlideViewerComponent(presentation, title -> { });
        component.setSize(Slide.WIDTH, Slide.HEIGHT);

        BufferedImage image = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        assertDoesNotThrow(() -> component.paintComponent(graphics),
                "paintComponent should safely handle an empty presentation");
    }
}
