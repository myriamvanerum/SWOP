package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import model.*;
import model.Object;
import model.Label;

public interface Draw {
	default void drawActor(Graphics2D g, double x, double y, Label label, int size, int totalHeight) {
		Shape c = new Ellipse2D.Double(x - size, y - size, 2.0 * size, 2.0 * size);
		g.draw(c);
		// Draw body actor
		g.draw(new Line2D.Double(x, y + size, x, y + size + 50));
		// Draw arms actor
		g.draw(new Line2D.Double(x - 20, y + size + 25, x, y + size + 5));
		g.draw(new Line2D.Double(x, y + size + 5, x + 20, y + size + 25));
		// draw legs actor
		g.draw(new Line2D.Double(x - 20, y + size + 70, x, y + size + 50));
		g.draw(new Line2D.Double(x, y + size + 50, x + 20, y + size + 70));
	}

	default void drawObject(Graphics2D g, double x, double y, Label label, int height, int width) {
		Rectangle r = new Rectangle((int) x, (int) y, width, height);
		g.draw(r);
	}

	default void drawLifeline(Graphics g, int x, int startY, int endY) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.draw(new Line2D.Double(x, startY, x, endY));
	}

	default void drawComponent(Graphics g, Party component) {
		Graphics2D g2 = (Graphics2D) g;
		int startLifelineX = 0, startLifelineY = 0;

		if (component instanceof Actor) {
			drawActor(g2, component.getXSeq(), component.getYSeq(), component.getLabel(), 20, 120);
			startLifelineX = (int) component.getXSeq();
			startLifelineY = (int) component.getYSeq() + 125;
		} else if (component instanceof Object) {
			drawObject(g2, component.getXSeq(), component.getYSeq(), component.getLabel(), 80, 80);
			startLifelineX = (int) component.getXSeq() + 40;
			startLifelineY = (int) component.getYSeq() + 80;
		}

		drawLifeline(g, startLifelineX, startLifelineY, startLifelineY + 400);
	}

	default void drawResultMessage(Graphics2D g, boolean focused, double xSender, double ySender, double xReceiver, double yReceiver) {
		Stroke dashed = new BasicStroke(getLineWidthResult(focused), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.drawLine((int) xSender, (int) ySender, (int) xReceiver, (int) yReceiver);	}

	default int getLineWidthResult(boolean focused) {
		if (focused) {
			return 5;
		}
		return 3;
	}
  
  default void drawInvocationMessage(Graphics2D g, boolean focused, double xSender, double ySender, double xReceiver, double yReceiver) {
      Stroke full = new BasicStroke(getLineWidthInvocation(focused));
      g.setStroke(full);
      g.drawLine((int) xSender,(int) ySender,(int) xReceiver,(int) yReceiver);
  }

  default int getLineWidthInvocation(boolean focused){
      if (focused){
          return 8;
      }
      return 5;
  }

	default void setColor(Party component, Graphics2D g) {
		if (component.focused()) {
			g.setPaint(new Color(70, 170, 220));
		} else {
			g.setPaint(new Color(0, 0, 0));
		}
	}
	
	default void drawLabel(Graphics2D g, Label label, Color color) {
		g.setColor(color);
		g.drawString(label.getText(), label.getX(), label.getY());
		g.setColor(new Color(0,0,0));
	}
}
