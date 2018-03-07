package model;

import java.awt.*;

public interface Focusable {

    Boolean focused();

    void focus();

    void unfocus();

    Color getFocusColor();

    Color getDefaultColor();

}
