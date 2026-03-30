public class SlideItemFactory {
	public static final String TEXT = "text";
	public static final String IMAGE = "image";

	public SlideItem createSlideItem(String type, int level, String content) {
		if (TEXT.equals(type)) {
			return new TextItem(level, content);
		}
		if (IMAGE.equals(type)) {
			return new BitmapItem(level, content);
		}
		throw new IllegalArgumentException("Unknown slide item type: " + type);
	}
}
