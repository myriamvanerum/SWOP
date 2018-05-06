package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewComponent;
import view.components.ViewMessage;
import view.components.ViewParty;
/**
 * Sequence Diagram State class
 * @author groep 03
 */
public class SeqState implements State {

	/**
	 * Get the SubWindow title
	 */
	@Override
	public String getTitle() {		
		return "SEQUENCE DIAGRAM";		
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
	 * 		No coordinates supplied
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	@Override
	public void drawContents(Graphics2D g, Point2D windowPosition, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		if (viewParties == null || viewMessages == null || windowPosition == null)
			throw new NullPointerException();
		if (windowPosition.getX() < 0 || windowPosition.getY() < 0)
			throw new IllegalArgumentException();
		
		for (ViewParty viewParty : viewParties) {
	        viewParty.drawSeq(g, windowPosition);
	    }

    	for (ViewMessage viewMessage : viewMessages) {
	    	viewMessage.drawSeq(g, windowPosition);
	    }		
	}
	
	@Override
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		component.setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 40));
	}
	
	@Override
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition) {
		return party.checkCoordinates(clickPosition, party.getPositionSeq(), windowPosition);
	}
	
	@Override
	public boolean checkLabelPosition(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		return component.checkLabelPosition(clickPosition, component.getPositionSeq(), windowPosition);
	}

	/**
	 * Get the State of this SubWindow
	 * @return the current state
	 */
	@Override
	public String getCurrentState() {
		return "SEQ";
	}
}
