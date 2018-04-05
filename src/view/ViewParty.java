package view;

import java.awt.geom.Point2D;

import model.Party;

public class ViewParty {
	public ViewParty(Party party) {
		
	}
	
	public ViewParty(ViewParty viewParty) {
		// copy all parameters from viewParty into new viewParty
	}

	Point2D position;
	
	// TODO "hook" --> template pattern
	public boolean checkCoordinates(Point2D coordinates) { 
		return false;
	}
}
