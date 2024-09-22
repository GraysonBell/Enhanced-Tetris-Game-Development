package ui.panel;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class ShadowBorder extends AbstractBorder {
    private final Color leftTop;
    private final Color rightBottom;
    private final int borderSize;

    public ShadowBorder(Color leftTop, Color rightBottom, int borderSize) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        this.borderSize = borderSize;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(leftTop);
        g.fillRect(x, y, width, borderSize);
        g.fillRect(x, y, borderSize, height);
        g.setColor(rightBottom);
        g.fillRect(x + width - borderSize, y, borderSize, height);
        g.fillRect(x, y + height - borderSize, width, borderSize);

    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(borderSize, borderSize, borderSize, borderSize);
    }
}
