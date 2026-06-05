package jabberpoint.core;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.ImageObserver;
import java.text.AttributedString;

/** <p>A tekst item.</p>
 * <p>A TextItem has drawingfunctionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private String text;
	private TextItemRenderer renderer;
	
	private static final String EMPTYTEXT = "No Text Given";

// a textitem of level level, with the text string
	public TextItem(int level, String string) {
		this(level, string, new TextItemRenderer());
	}

	public TextItem(int level, String string, TextItemRenderer renderer) {
		super(level);
		text = string;
		this.renderer = renderer;
	}

// an empty textitem
	public TextItem() {
		this(0, EMPTYTEXT);
	}

// give the text
	public String getText() {
		return text == null ? "" : text;
	}

// geef de AttributedString voor het item
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, getText().length());
		return attrStr;
	}

// give the bounding box of the item
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, 
			float scale, Style myStyle) {
		return renderer.getBoundingBox(this, g, observer, scale, myStyle);
	}

// draw the item
	public void draw(int x, int y, float scale, Graphics g, 
			Style myStyle, ImageObserver o) {
		renderer.draw(this, x, y, scale, g, myStyle, o);
	  }

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}
}
