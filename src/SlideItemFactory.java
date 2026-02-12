/** Factory for creating SlideItem instances based on type.
 * Centralizes object creation logic for SlideItem subtypes.
 */
public class SlideItemFactory {

	protected static final String TEXT = "text";
	protected static final String IMAGE = "image";
	protected static final String UNKNOWNTYPE = "Unknown Element type";

	public static SlideItem create(String type, int level, String content) {
		if (TEXT.equals(type)) {
			return new TextItem(level, content);
		}
		else if (IMAGE.equals(type)) {
			return new BitmapItem(level, content);
		}
		else {
			System.err.println(UNKNOWNTYPE);
			return null;
		}
	}
}
