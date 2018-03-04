package domain;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Actor extends Party {

    private int defaultWidth = 20;
    private int defaultHeight = 20;
    private int totalHeight = 120;

    public Actor(int x, int y, ComponentType type, String label) {
        super(x, y, type, label);
    }
}
