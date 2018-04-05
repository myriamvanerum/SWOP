package view;

import java.util.ArrayList;
import java.awt.geom.*;

// TODO tijdelijke oplossing
public class ClickEventHandler {
	public enum ClickEvent {
		EMPTY, LABEL, PARTY, LIFELINE, CLOSE, ACTIVATESUBWINDOW
	};

	public ClickEvent handleClickEvent(Point2D coordinates, SubWindow subwindow) {
		if (coordinates.getX() < 0 || coordinates.getY() < 0 || subwindow == null)
			throw new IllegalArgumentException();
		
		// click outside active subwindow
		if (coordinates.getX() < subwindow.getX() || 
			coordinates.getY() < subwindow.getY() || 
			coordinates.getX() > subwindow.getX() + subwindow.getWidth() ||
			coordinates.getY() > subwindow.getY() + subwindow.getHeight())
			return ClickEvent.ACTIVATESUBWINDOW;
			
		// Click on closebutton
		if (subwindow != null && 
			coordinates.getX() >= subwindow.getX() + (subwindow.getWidth() - subwindow.getHeightTitlebar()) && 
			coordinates.getX() <= subwindow.getX() + subwindow.getWidth() &&
			coordinates.getY() >= subwindow.getY() && 
			coordinates.getY() <= (subwindow.getY() + subwindow.getHeightTitlebar()))
			return ClickEvent.CLOSE;

		// look for component
		if (checkCoordinates(subwindow.getViewParties(), coordinates) != null)
			return ClickEvent.PARTY;
		// * label or not??		TODO
		// return ClickEvent.LIFELINE;
		
		// LIFELINE	TODO
		// return ClickEvent.LIFELINE;

		return ClickEvent.EMPTY;
	}
	
	/**
	 * 
	 * @param parties
	 * 			Collection of all the parties that belong to the interaction of the active (sub)window
	 * @param coordinates
	 * 			The coordinates of the click event
	 * @return
	 */
	public ViewParty checkCoordinates(ArrayList<ViewParty> parties, Point2D coordinates) {
		for ( ViewParty party: parties ) {
			if (party.checkCoordinates(coordinates))
				return party;
		}
		
		return null;
	}
}
