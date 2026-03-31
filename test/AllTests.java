public class AllTests {
	public static void main(String[] args) {
		PresentationTest.runAll();
		SlideTest.runAll();
		StyleTest.runAll();
		RenderTest.runAll();
		DemoPresentationTest.runAll();
		XMLAccessorSaveTest.runAll();
		CommandTest.runAll();
		XMLAccessorTest.runAll();
		SlideItemFactoryTest.runAll();
		KeyControllerTest.runAll();
		System.out.println("All tests passed.");
	}
}
