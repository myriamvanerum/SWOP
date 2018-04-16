package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface State {
	/**
	 * Draw the SubWindow title
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinates
	 * @param y
	 * 		Y coordinates
	 */
	public void drawTitle(Graphics2D g, Integer x, Integer y);
	
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
	
	/**
	 * Get the State of this SubWindow
	 * @return the current state
	 */
	public String getCurrentState();
}