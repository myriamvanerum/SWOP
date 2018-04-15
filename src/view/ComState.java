package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 * Communication Diagram State class
 * @author groep 03
 */
public class ComState implements State {

	/**
	 * Draw the SubWindow title
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinates
	 * @param y
	 * 		Y coordinates
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		g.drawString("COMMUNICATION DIAGRAM", x, y);		
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
	 * @throws NullPointerException
	 * 		No coordinates or viewParties or viewmessages supplied
	 * @throws IllegalArgumentException
	 * 		Illegal window position
	 */
	@Override
	public void drawContents(Graphics2D g, Point2D windowPosition, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		if (viewParties == null || viewMessages == null || viewParties.size() == 0 || viewMessages.size() == 0 || windowPosition == null)
			throw new NullPointerException();
		if (windowPosition.getX() < 0 || windowPosition.getY() < 0)
			throw new IllegalArgumentException();
		
		for (ViewParty viewParty : viewParties) {
	        viewParty.drawCom(g, windowPosition);
	    }
	    for (ViewMessage viewMessage : viewMessages) {
	    	if (viewMessage.getClass() == ViewInvocationMessage.class)
	    		viewMessage.drawCom(g, windowPosition);
	    }		
	}

	/**
	 * Get the State of this SubWindow
	 */
	@Override
	public String getCurrentState() {
		return "COM";
	}

}
