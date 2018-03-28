package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import model.Party;

public class ViewObject {
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
	public void draw(Graphics2D g, double x, double y, int height, int width, Party object) {
		if (x < 0 || y < 0 || height < 0 || width < 0 || object == null)
			throw new IllegalArgumentException();
		Rectangle r = new Rectangle((int) x, (int) y, width, height);
//		object.getLabel().setX((int)x + ((width/2) - object.getLabel().getText().length() * 2));
//		object.getLabel().setY((int)y + height/2);
		g.draw(r);
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
