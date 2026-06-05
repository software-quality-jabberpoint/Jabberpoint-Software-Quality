package jabberpoint.core;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BitmapItemRenderer {
	public Rectangle getBoundingBox(BitmapItem item, Graphics g, ImageObserver observer,
			float scale, Style style) {
		BufferedImage image = item.getBufferedImage();
		if (image == null) {
			return new Rectangle((int) (style.indent * scale), 0, 0,
					(int) (style.leading * scale));
		}
		return new Rectangle((int) (style.indent * scale), 0,
				(int) (image.getWidth(observer) * scale),
				((int) (style.leading * scale)) + (int) (image.getHeight(observer) * scale));
	}

	public void draw(BitmapItem item, int x, int y, float scale, Graphics g,
			Style style, ImageObserver observer) {
		BufferedImage image = item.getBufferedImage();
		if (image == null) {
			return;
		}
		int width = x + (int) (style.indent * scale);
		int height = y + (int) (style.leading * scale);
		g.drawImage(image, width, height, (int) (image.getWidth(observer) * scale),
				(int) (image.getHeight(observer) * scale), observer);
	}
}
