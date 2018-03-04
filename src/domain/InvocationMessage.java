package domain;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class InvocationMessage extends Message {

    public InvocationMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
}