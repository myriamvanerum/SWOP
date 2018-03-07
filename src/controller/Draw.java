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

/**
 * This interface groups together all the methods used to draw components on the
 * window. Actors are drawn as stickmen, objects as rectangles, invocation
 * messages as full lines, and result messages as striped lines. Parties in the
 * communication diagram are painted wherever the user clicked, but in the
 * sequence diagram they are all placed at the same height, next to each other,
 * with a lifeline running underneath. Messages are drawn from party to party in
 * the communication diagram, and from lifeline to lifeline in the sequence
 * diagram.
 * 
 * @author groep 03
 *
 */
public interface Draw {
	
	/**
	 * This method paints an actor on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param x
	 *            The x coordinate to start painting from
	 * @param y
	 *            The y coordinate to start painting from
	 * @param size
	 *            The size of the painted stickfigure
	 * @param totalHeight
	 *            The total height of the stickfigure
	 * @param actor
	 *            The actor that will be displayed as a stickfigure
	 * @throws IllegalArgumentException
	 * 			  Illegal actor, coordinates or size
	 */
	default void drawActor(Graphics2D g, double x, double y, int size, Party actor) {
		if (x < 0 || y < 0 || size < 0 || actor == null)
			throw new IllegalArgumentException();
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

		actor.getLabel().setX((int) x + (10 - actor.getLabel().getText().length() * 3));			
		actor.getLabel().setY((int) y + 115);
	}

	/**
	 * This method paints an object on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param x
	 *            The x coordinate to start painting from
	 * @param y
	 *            The y coordinate to start painting from
	 * @param height
	 *            The height of the rectangle to paint
	 * @param width
	 *            The width of the rectangle to paint
	 * @param object
	 *            The object that will be displayed 
	 * @throws IllegalArgumentException
	 * 			  Illegal object, coordinates or size
	 */
	default void drawObject(Graphics2D g, double x, double y, int height, int width, Party object) {
		if (x < 0 || y < 0 || height < 0 || width < 0 || object == null)
			throw new IllegalArgumentException();
		Rectangle r = new Rectangle((int) x, (int) y, width, height);
		object.getLabel().setX((int)x + ((width/2) - object.getLabel().getText().length() * 2));
		object.getLabel().setY((int)y + height/2);
		g.draw(r);
	}

	/**
	 * This method draws a lifeline underneath parties in the sequence diagram
	 * 
	 * @param g
	 *            The graphics library used
	 * @param x
	 *            The x coordinate of the party
	 * @param startY
	 *            The start y value for the lifeline
	 * @param endY
	 *            The end y value for the lifeline
	 * @throws IllegalArgumentException
	 * 			  Illegal coordinates or size
	 */
	default void drawLifeline(Graphics g, int x, int startY, int endY) {
		if (x < 0 || startY < 0 || endY < 0)
			throw new IllegalArgumentException();
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.draw(new Line2D.Double(x, startY, x, endY));
	}

	/**
	 * This method paints a party on the sequence diagram window. It draws and actor
	 * or object, with a lifeline underneath
	 * 
	 * @param g
	 *            The graphics library used
	 * @param party
	 *            The party to paint on the window
	 * @throws IllegalArgumentException
	 * 			  Illegal party
	 */
	default void drawParty(Graphics g, Party party) {
		if (party == null)
			throw new IllegalArgumentException();
		Graphics2D g2 = (Graphics2D) g;
		int startLifelineX = 0, startLifelineY = 0;
		
		if (party instanceof Actor) {			
			drawActor(g2, party.getXSeq(), party.getYSeq(), 20, party);
			startLifelineX = (int) party.getXSeq();
			startLifelineY = (int) party.getYSeq() + 125;
		} else if (party instanceof Object) {
			int height = 80, width = 80;			
			drawObject(g2, party.getXSeq(), party.getYSeq(), height, width, party);
			startLifelineX = (int) party.getXSeq() + width/2;
			startLifelineY = (int) party.getYSeq() + height;
		}

		drawLifeline(g, startLifelineX, startLifelineY, startLifelineY + 400);
	}

	/**
	 * This method draws a result message on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @param xSender
	 *            The x coordinate of the message sender
	 * @param ySender
	 *            The y coordinate of the message sender
	 * @param xReceiver
	 *            The x coordinate of the message receiver
	 * @param yReceiver
	 *            The y coordinate of the message receiver
	 * @throws IllegalArgumentException
	 * 			  Illegal party coordinates
	 */
	default void drawResultMessage(Graphics2D g, boolean focused, double xSender, double ySender, double xReceiver,
			double yReceiver) {
		if (xSender < 0 || ySender < 0 || xReceiver < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		Stroke dashed = new BasicStroke(getLineWidthResult(focused), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.drawLine((int) xSender, (int) ySender, (int) xReceiver, (int) yReceiver);
	}

	/**
	 * This method determines how wide the line for a result message should be
	 * 
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @return The number of pixels the line width should be
	 */
	default int getLineWidthResult(boolean focused) {
		if (focused) {
			return 5;
		}
		return 3;
	}

	/**
	 * This method draws an invocation message on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @param xSender
	 *            The x coordinate of the message sender
	 * @param ySender
	 *            The y coordinate of the message sender
	 * @param xReceiver
	 *            The x coordinate of the message receiver
	 * @param yReceiver
	 *            The y coordinate of the message receiver
	 * @throws IllegalArgumentException
	 * 			  Illegal party coordinates
	 */
	default void drawInvocationMessage(Graphics2D g, boolean focused, double xSender, double ySender, double xReceiver,
			double yReceiver) {
		if (xSender < 0 || ySender < 0 || xReceiver < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		Stroke full = new BasicStroke(getLineWidthInvocation(focused));
		g.setStroke(full);
		g.drawLine((int) xSender, (int) ySender, (int) xReceiver, (int) yReceiver);
	}

	/**
	 * This method determines how wide the line for a invocation message should be
	 * 
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @return The number of pixels the line width should be
	 */
	default int getLineWidthInvocation(boolean focused) {
		if (focused) {
			return 8;
		}
		return 5;
	}

	/**
	 * This method sets the color for all components. Focused components will be blue, others black.
	 * @param component
	 * 		The component to be painted
	 * @param g
	 * 		The graphics library used
	 * @throws IllegalArgumentException
	 * 		Illegal party
	 */
	default void setColor(Focusable focusable, Graphics2D g) {
		if (focusable == null)
			throw new IllegalArgumentException();
		if (focusable.focused()) {
			g.setPaint(new Color(70, 170, 220));
		} else {
			g.setPaint(new Color(0, 0, 0));
		}
	}
	
	/**
	 * This method draws a label for the components
	 * @param g
	 * 		The graphics library used
	 * @param label
	 * 		The label to draw
	 * @param color
	 * 		The color to draw the label in
	 */
	default void drawLabel(Graphics2D g, Label label, Color color) {	
		if (label.getText() != null)
			label.setWidth(g);
		
		g.setColor(color);
		g.drawString(label.getText(), label.getX(), label.getY());
		g.setColor(new Color(0,0,0));
	}
}
