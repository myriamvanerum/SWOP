package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 * Sequence Diagram State class
 * @author groep 03
 */
public class SeqState implements State {

	/**
	 * Draw the SubWindow title
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinates
	 * @param y
	 * 		Y coordinates
	 */
	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		g.drawString("SEQUENCE DIAGRAM", x, y);		
	}

	/**
	 * Draw the Parties and Messages in the SubWindow
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		Window top left coordinates
	 * @param viewParties
	 * 		The Parties in the SubWindow
	 * @param viewMessages
	 * 		The Messages in the SubWindow
	 */
	@Override
	public void drawContents(Graphics2D g, Point2D windowPosition, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		for (ViewParty viewParty : viewParties) {
	        viewParty.drawSeq(g, windowPosition);
	    }

    	for (ViewMessage viewMessage : viewMessages) {
	    	viewMessage.drawSeq(g);
	    }		
	}

	/**
	 * Get the State of this SubWindow
	 */
	@Override
	public String getCurrentState() {
		return "SEQ";
	}
}
