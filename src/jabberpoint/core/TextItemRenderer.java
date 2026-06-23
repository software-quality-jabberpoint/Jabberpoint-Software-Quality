package jabberpoint.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextItemRenderer {
	public Rectangle getBoundingBox(TextItem item, Graphics g, ImageObserver observer,
			float scale, Style style) {
		List<TextLayout> layouts = getLayouts(item, g, style, scale);
		int xsize = 0, ysize = (int) (style.leading * scale);
		Iterator<TextLayout> iterator = layouts.iterator();
		while (iterator.hasNext()) {
			TextLayout layout = iterator.next();
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > xsize) {
				xsize = (int) bounds.getWidth();
			}
			if (bounds.getHeight() > 0) {
				ysize += (int) bounds.getHeight();
			}
			ysize += (int) (layout.getLeading() + layout.getDescent());
		}
		return new Rectangle((int) (style.indent * scale), 0, xsize, ysize);
	}

	public void draw(TextItem item, int x, int y, float scale, Graphics g,
			Style style, ImageObserver observer) {
		if (item.getText().length() == 0) {
			return;
		}
		List<TextLayout> layouts = getLayouts(item, g, style, scale);
		Point pen = new Point(x + (int) (style.indent * scale),
				y + (int) (style.leading * scale));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(style.color);
		Iterator<TextLayout> iterator = layouts.iterator();
		while (iterator.hasNext()) {
			TextLayout layout = iterator.next();
			pen.y += (int) layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += (int) layout.getDescent();
		}
	}

	private List<TextLayout> getLayouts(TextItem item, Graphics g, Style style, float scale) {
		List<TextLayout> layouts = new ArrayList<TextLayout>();
		AttributedString attributedString = item.getAttributedString(style, scale);
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext frc = g2d.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(), frc);
		float wrappingWidth = (Slide.WIDTH - style.indent) * scale;
		while (measurer.getPosition() < item.getText().length()) {
			layouts.add(measurer.nextLayout(wrappingWidth));
		}
		return layouts;
	}
}
