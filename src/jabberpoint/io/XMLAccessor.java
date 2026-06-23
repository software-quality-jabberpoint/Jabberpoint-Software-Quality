package jabberpoint.io;

import jabberpoint.core.BitmapItem;
import jabberpoint.core.Presentation;
import jabberpoint.core.Slide;
import jabberpoint.core.SlideItem;
import jabberpoint.core.SlideItemFactory;
import jabberpoint.core.TextItem;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {
	
    /** Default API to use. */
    protected static final String DEFAULT_API_TO_USE = "dom";
    private final SlideItemFactory slideItemFactory;
    
    /** namen van xml tags of attributen */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    
    /** tekst van messages */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

    public XMLAccessor() {
        this(new SlideItemFactory());
    }

    public XMLAccessor(SlideItemFactory slideItemFactory) {
        this.slideItemFactory = slideItemFactory;
    }
    
    
    public String getTitle(Element element, String tagName) {
    	NodeList titles = element.getElementsByTagName(tagName);
    	if (titles.getLength() == 0 || titles.item(0) == null) {
    		return "";
    	}
    	return titles.item(0).getTextContent();
    }

	public void loadFile(Presentation presentation, String filename) throws IOException {
		int slideNumber, itemNumber, max = 0, maxItems = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			factory.setExpandEntityReferences(false);
			DocumentBuilder builder = factory.newDocumentBuilder();    
			Document document = builder.parse(new File(filename)); // Create a JDOM document
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTitle(doc, SHOWTITLE));

			NodeList slides = doc.getElementsByTagName(SLIDE);
			max = slides.getLength();
			for (slideNumber = 0; slideNumber < max; slideNumber++) {
				Element xmlSlide = (Element) slides.item(slideNumber);
				Slide slide = new Slide();
				slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
				presentation.append(slide);
				
				NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
				maxItems = slideItems.getLength();
				for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
					Element item = (Element) slideItems.item(itemNumber);
					loadSlideItem(slide, item);
				}
			}
		} 
		catch (SAXException sax) {
			throw new IOException("Malformed presentation XML: " + sax.getMessage(), sax);
		}
		catch (ParserConfigurationException pcx) {
			throw new IOException(PCE, pcx);
		}
	}

	public void loadSlideItem(Slide slide, Element item) {
		int level = 1; // default
		NamedNodeMap attributes = item.getAttributes();
		Node levelNode = attributes.getNamedItem(LEVEL);
		if (levelNode != null) {
			try {
				level = Integer.parseInt(levelNode.getTextContent());
			}
			catch(NumberFormatException x) {
				System.err.println(NFE);
			}
		}
		Node kindNode = attributes.getNamedItem(KIND);
		if (kindNode == null) {
			System.err.println(UNKNOWNTYPE + ": <missing kind>");
			return;
		}
		String type = kindNode.getTextContent();
		try {
			slide.append(slideItemFactory.createSlideItem(type, level, item.getTextContent()));
		}
		catch (IllegalArgumentException exception) {
			System.err.println(UNKNOWNTYPE + ": " + type);
		}
	}

	public void saveFile(Presentation presentation, String filename) throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
			out.println("<?xml version=\"1.0\"?>");
			out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
			out.println("<presentation>");
			out.print("<showtitle>");
			out.print(escape(presentation.getTitle()));
			out.println("</showtitle>");
			for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
				Slide slide = presentation.getSlide(slideNumber);
				out.println("<slide>");
				out.println("<title>" + escape(slide.getTitle()) + "</title>");
				List<SlideItem> slideItems = slide.getSlideItems();
				for (SlideItem slideItem : slideItems) {
					out.print("<item kind="); 
					if (slideItem instanceof TextItem) {
						out.print("\"" + SlideItemFactory.TEXT + "\" level=\"" + slideItem.getLevel() + "\">");
						out.print(escape(((TextItem) slideItem).getText()));
					}
					else {
						if (slideItem instanceof BitmapItem) {
							out.print("\"" + SlideItemFactory.IMAGE + "\" level=\"" + slideItem.getLevel() + "\">");
							out.print(escape(((BitmapItem) slideItem).getName()));
						}
						else {
							System.out.println("Ignoring " + slideItem);
						}
					}
					out.println("</item>");
				}
				out.println("</slide>");
			}
			out.println("</presentation>");
		}
	}

	private static String escape(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
