package jabberpoint.ui;

import jabberpoint.JabberPoint;
import jabberpoint.command.CommandFactory;
import jabberpoint.command.DefaultCommandFactory;
import jabberpoint.core.Presentation;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The application window for a slideviewcomponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	
	@SuppressWarnings("this-escape")
	public SlideViewerFrame(String title, Presentation presentation, JabberPoint jabberPoint) {
		super(title);
		CommandFactory commandFactory = new DefaultCommandFactory(presentation, jabberPoint);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this::setTitle);
		setUpWindow(slideViewerComponent, presentation, commandFactory);
	}

// Setup GUI
	public void setUpWindow(SlideViewerComponent 
			slideViewerComponent, Presentation presentation, CommandFactory commandFactory) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					commandFactory.exit().execute();
				}
			});
		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(commandFactory)); // add a controller
		setMenuBar(new MenuController(this, presentation, commandFactory));	// add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes as Slide has.
		setVisible(true);
	}
}
