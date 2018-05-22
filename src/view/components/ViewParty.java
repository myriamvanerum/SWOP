package view.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import domain.Component;
import domain.party.Party;
import view.labelstate.EditPartyLabelState;
import view.windows.SubWindow;
/**
 * ViewParty class. Controls the drawing of Parties
 * @author groep 03
 *
 */
public abstract class ViewParty extends ViewComponent {
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
		setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 30));
	}

	/**
	 * Copy Constructor
	 * @param viewParty
	 * 		The ViewParty to copy
	 */
	public ViewParty(ViewParty viewParty) {
		// copy all parameters from viewParty into new viewParty
		setParty(viewParty.getParty());
		setViewLabel(new ViewLabel(viewParty.getViewLabel()));
		setPositionCom(viewParty.getPositionCom());
		setPositionSeq(viewParty.getPositionSeq());
		viewLifeLine = new ViewLifeLine();
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
	public abstract boolean checkCoordinates(Point2D coordinates, Point2D position, Point2D windowPosition);
	
	/**
	 * Add the Component position and SubWindow position to get new coordinates
	 * @param position
	 * 		Component position
	 * @param windowPosition
	 * 		SubWindow position
	 * @return added coordinates
	 */
	public Point2D positionWindow(Point2D position, Point2D windowPosition) {		
		return new Point2D.Double(position.getX() + windowPosition.getX(), position.getY() + windowPosition.getY());
	}

	/**
	 * This method paints an Party on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param position
	 * 			  The position to draw the Party
	 */
	public abstract void draw(Graphics2D g, Point2D position);
	
	public abstract ViewParty copy();
	
	public abstract ViewParty changeType();
	
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
	
	@Override
	public void setLabelState(SubWindow subwindow) {
		subwindow.setLabelState(new EditPartyLabelState(subwindow, getViewLabel()));
	}
}
