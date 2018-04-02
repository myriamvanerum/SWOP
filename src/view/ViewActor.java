package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import model.Actor;
import model.Object;
import model.Party;

public class ViewActor extends ViewParty {
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
	 * @param actor
	 *            The actor that will be displayed as a stickfigure
	 * @throws IllegalArgumentException
	 * 			  Illegal actor, coordinates or size
	 */
	public void draw(Graphics2D g, int size, Party actor) {
		if (position.x < 0 || position.y < 0 || size < 0 || actor == null)
			throw new IllegalArgumentException();
		Shape c = new Ellipse2D.Double(position.x - size, position.y - size, 2.0 * size, 2.0 * size);
		g.draw(c);
		// Draw body actor
		g.draw(new Line2D.Double(position.x, position.y + size, position.x, position.y + size + 50));
		// Draw arms actor
		g.draw(new Line2D.Double(position.x - 20, position.y + size + 25, position.x, position.y + size + 5));
		g.draw(new Line2D.Double(position.x, position.y + size + 5, position.x + 20, position.y + size + 25));
		// draw legs actor
		g.draw(new Line2D.Double(position.x - 20, position.y + size + 70, position.x, position.y + size + 50));
		g.draw(new Line2D.Double(position.x, position.y + size + 50, position.x + 20, position.y + size + 70));		

		//actor.getLabel().setX((int) x + (10 - actor.getLabel().getText().length() * 3));			
		//actor.getLabel().setY((int) y + 115);
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
	public void drawParty(Graphics g, Party party) {
//		if (party == null)
//			throw new IllegalArgumentException();
//		Graphics2D g2 = (Graphics2D) g;
//		int startLifelineX = 0, startLifelineY = 0;
//		
//		if (party instanceof Actor) {			
//			drawActor(g2, party.getXSeq(), party.getYSeq(), 20, party);
//			startLifelineX = (int) party.getXSeq();
//			startLifelineY = (int) party.getYSeq() + 125;
//		} else if (party instanceof Object) {
//			int height = 80, width = 80;			
//			drawObject(g2, party.getXSeq(), party.getYSeq(), height, width, party);
//			startLifelineX = (int) party.getXSeq() + width/2;
//			startLifelineY = (int) party.getYSeq() + height;
//		}
//
//		drawLifeline(g, startLifelineX, startLifelineY, startLifelineY + 400);
	}
}
