package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Component;
import model.Party;
/**
 * ViewParty class. Controls the drawing of Parties
 * @author groep 03
 *
 */
public class ViewParty extends ViewComponent {
	private Party party;
	protected ViewLifeLine viewLifeLine;

	/**
	 * ViewParty Constructor
	 * @param party
	 * 		Party to draw
	 * @param clickPosition
	 * 		Chosen position
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public ViewParty(Party party, Point2D clickPosition, Point2D windowPosition) {
		setParty(party);
		viewLabel = new ViewLabel(party.getLabel());
		viewLifeLine = new ViewLifeLine();
		setPositionCom(new Point2D.Double(clickPosition.getX() - windowPosition.getX(),
				clickPosition.getY() - windowPosition.getY() - 25));
		setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 40));
	}

	/**
	 * Copy Constructor
	 * @param viewParty
	 * 		The ViewParty to copy
	 */
	public ViewParty(ViewParty viewParty) {
		// copy all parameters from viewParty into new viewParty
		setParty(viewParty.getParty());
		setViewLabel(viewParty.getViewLabel());
		setPositionCom(viewParty.getPositionCom());
		setPositionSeq(viewParty.getPositionSeq());
		setViewLifeLine(viewParty.getViewLifeLine());
	}

	/**
	 * Checks if the Party is positioned at the clicked coordinates
	 * @param coordinates
	 * 			The coordinates of a click event
	 * @param position
	 * 			  The Party's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 * @return true if Party positioned at coordinates, false if not
	 */
	public boolean checkCoordinates(Point2D coordinates, Point2D position, Point2D windowPosition) {
		return false;
	}

	/**
	 * Draw a Party in a Communication diagram
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public void drawCom(Graphics2D g, Point2D windowPosition) {
		this.setColor(g);
		draw(g, positionWindow(getPositionCom(), windowPosition));
		this.resetColor(g);
	}

	/**
	 * Draw a Party in a Sequence diagram
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public void drawSeq(Graphics2D g, Point2D windowPosition) {
		setColor(g);
		draw(g, positionWindow(getPositionSeq(), windowPosition));
		setColor(g);
		viewLifeLine.draw(g);
		resetColor(g);
	}

	/**
	 * This method paints an Party on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param position
	 * 			  The position to draw the Party
	 */
	public void draw(Graphics2D g, Point2D position) {
	}
	
	/* GETTERS AND SETTERS */

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public ViewLifeLine getViewLifeLine() {
		return viewLifeLine;
	}

	public void setViewLifeLine(ViewLifeLine viewLifeLine) {
		this.viewLifeLine = viewLifeLine;
	}
	
	@Override 
	public Component getComponent() {
		return this.getParty();
	}
}
