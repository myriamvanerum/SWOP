package view.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class CloseButton {

	public void draw(Graphics2D g, Integer x, Integer y, Integer height, Integer padding, Integer paddingBig) {
		g.setColor(Color.RED);
		g.fillRect(x - height, y, height, height);
		g.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
		g.drawLine(x - paddingBig, y + padding, x - padding, y + paddingBig);
		g.drawLine(x - padding, y + padding, x - paddingBig, y + paddingBig);
		stroke = new BasicStroke(1);
		g.setStroke(stroke);
	}

}
