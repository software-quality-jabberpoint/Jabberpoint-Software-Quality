package jabberpoint.test;

import jabberpoint.JabberPoint;
import jabberpoint.command.*;
import jabberpoint.core.*;
import jabberpoint.io.*;
import jabberpoint.ui.KeyController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Font;

class StyleTest {
    @Test
    void getStyleClampsLevelsToConfiguredBounds() {
        Style.createStyles();

        Style lowStyle = Style.getStyle(-10);
        Style zeroStyle = Style.getStyle(0);
        Style highStyle = Style.getStyle(999);
        Style maxStyle = Style.getStyle(4);

        assertSame(zeroStyle, lowStyle, "Negative levels should map to the lowest style");
        assertSame(maxStyle, highStyle, "Levels above the configured range should map to the highest style");
    }

    @Test
    void getFontScalesConfiguredSize() {
        Style.createStyles();
        Style style = Style.getStyle(2);

        Font scaledFont = style.getFont(0.5f);
        assertEquals(18, scaledFont.getSize(), "Scaled font size should follow the configured style size");
    }
}
