package jabberpoint.ui;

/**
 * <p>Small abstraction for whatever can display the presentation title.</p>
 * <p>Allows {@link SlideViewerComponent} to update the window title without
 * depending on a concrete {@code JFrame}, satisfying the Dependency Inversion
 * and Interface Segregation principles.</p>
 */
@FunctionalInterface
public interface TitleView {
    void setTitle(String title);
}
