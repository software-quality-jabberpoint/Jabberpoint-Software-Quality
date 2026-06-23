package jabberpoint.test;

import jabberpoint.core.BitmapItem;
import jabberpoint.core.Slide;
import jabberpoint.core.Style;
import jabberpoint.core.TextItem;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RenderTest {
	@Test
	void textItemBoundingBoxAndDrawWorkOnGraphicsContext() {
		Style.createStyles();
		TextItem item = new TextItem(1, "Hello world");
		Style style = Style.getStyle(item.getLevel());
		BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		Rectangle box = item.getBoundingBox(graphics, null, 1.0f, style);
		assertTrue(box.width >= 0, "Bounding box width should be non-negative");
		assertTrue(box.height >= 0, "Bounding box height should be non-negative");

		assertDoesNotThrow(() -> item.draw(0, 0, 1.0f, graphics, style, null));
		graphics.dispose();

		BufferedImage emptyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		Graphics2D emptyGraphics = emptyImage.createGraphics();
		assertDoesNotThrow(() -> new TextItem(1, "").draw(0, 0, 1.0f, emptyGraphics, style, null));
		emptyGraphics.dispose();
	}

	@Test
	void bitmapItemBoundingBoxAndDrawWorkOnGraphicsContext() {
		Style.createStyles();
		Style style = Style.getStyle(1);
		File imageFile = assertDoesNotThrow(() -> {
			File tmp = File.createTempFile("jabberpoint-test-image", ".png");
			tmp.deleteOnExit();
			BufferedImage image = new BufferedImage(20, 10, BufferedImage.TYPE_INT_RGB);
			ImageIO.write(image, "png", tmp);
			return tmp;
		});

		BitmapItem bitmapItem = new BitmapItem(1, imageFile.getAbsolutePath());
		BufferedImage canvas = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = canvas.createGraphics();

		Rectangle box = bitmapItem.getBoundingBox(graphics, null, 1.0f, style);
		assertTrue(box.width > 0, "Bitmap bounding box width should be > 0");
		assertTrue(box.height > 0, "Bitmap bounding box height should be > 0");

		assertDoesNotThrow(() -> bitmapItem.draw(0, 0, 1.0f, graphics, style, null));
		graphics.dispose();
	}

	@Test
	void slideDrawsTitleAndItems() {
		Style.createStyles();
		Slide slide = new Slide();
		slide.setTitle("Title");
		slide.append(1, "Line 1");
		slide.append(2, "Line 2");

		BufferedImage canvas = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = canvas.createGraphics();

		assertDoesNotThrow(() -> slide.draw(graphics, new Rectangle(0, 0, 1200, 800), null));
		graphics.dispose();
	}
}
