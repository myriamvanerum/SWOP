package domain;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Object extends Party {

    private int defaultWidth = 80;
    private int defaultHeight = 80;
    private boolean highlighted = false;

    public Object(int x, int y, ComponentType type, String label) {
        super(x, y, type, label);
    }
}
