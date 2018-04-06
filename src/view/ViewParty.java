package view;

import com.sun.javafx.geom.Point2D;

import model.Party;

public class ViewParty {
	public ViewParty(Party party) {
		
	}
	
	public ViewParty(ViewParty viewParty) {
		// copy all parameters from viewParty into new viewParty
	}

	// ik zou de positie relatief tegenover het subwindow bijhouden, dat lijkt mij het gemakkelijkste
	Point2D positionCom; 
	Point2D positionSeq;
}
