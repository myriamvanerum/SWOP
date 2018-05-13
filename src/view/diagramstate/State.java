package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewComponent;
import view.components.ViewMessage;
import view.components.ViewParty;

public interface State {
	/**
	 * Get the SubWindow title
	 */
	public String getTitle();
	
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
	public void drawContents(Graphics2D g, Point2D windowPosition, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages);
	
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition);
	
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition);
	
	/**
	 * Get the State of this SubWindow
	 * @return the current state
	 */
	public String getCurrentState();
}