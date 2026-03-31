import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RenderTest {
	public static void runAll() {
		testTextItemBoundingBoxAndDraw();
		testBitmapItemBoundingBoxAndDraw();
		testSlideDraw();
	}

	private static void testTextItemBoundingBoxAndDraw() {
		Style.createStyles();
		TextItem item = new TextItem(1, "Hello world");
		Style style = Style.getStyle(item.getLevel());

		BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

		Rectangle box = item.getBoundingBox(g, null, 1.0f, style);
		assertTrue(box.width >= 0, "Bounding box width should be non-negative");
		assertTrue(box.height >= 0, "Bounding box height should be non-negative");

		item.draw(0, 0, 1.0f, g, style, null);
		g.dispose();

		TextItem empty = new TextItem(1, "");
		BufferedImage image2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image2.createGraphics();
		empty.draw(0, 0, 1.0f, g2, style, null);
		g2.dispose();
	}

	private static void testBitmapItemBoundingBoxAndDraw() {
		Style.createStyles();
		Style style = Style.getStyle(1);

		File tmp;
		try {
			tmp = File.createTempFile("jabberpoint-test-image", ".png");
			tmp.deleteOnExit();
			BufferedImage img = new BufferedImage(20, 10, BufferedImage.TYPE_INT_RGB);
			ImageIO.write(img, "png", tmp);
		} catch (Exception e) {
			throw new AssertionError("Failed to prepare temporary image file", e);
		}

		BitmapItem bitmapItem = new BitmapItem(1, tmp.getAbsolutePath());
		BufferedImage canvas = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = canvas.createGraphics();

		Rectangle box = bitmapItem.getBoundingBox(g, null, 1.0f, style);
		assertTrue(box.width > 0, "Bitmap bounding box width should be > 0");
		assertTrue(box.height > 0, "Bitmap bounding box height should be > 0");

		bitmapItem.draw(0, 0, 1.0f, g, style, null);
		g.dispose();
	}

	private static void testSlideDraw() {
		Style.createStyles();
		Slide slide = new Slide();
		slide.setTitle("Title");
		slide.append(1, "Line 1");
		slide.append(2, "Line 2");

		BufferedImage canvas = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = canvas.createGraphics();
		slide.draw(g, new Rectangle(0, 0, 1200, 800), null);
		g.dispose();
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}
