package model;

import java.awt.*;
import java.util.ArrayList;

public class DiagramComponent implements Focusable {

    private boolean focused;

    @Override
    public Boolean focused() {
        return focused;
    }

    @Override
    public void focus() {
        focused = true;
    }

    @Override
    public void unfocus() {
        focused = false;
    }

    @Override
    public Color getFocusColor() {
        return new Color(159, 236, 249);
    }

    @Override
    public Color getDefaultColor() {
        return new Color(0, 0, 0);
    }
}
