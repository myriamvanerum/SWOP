package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewParty;
import view.components.ViewResultMessage;

/**
 * Diagram State class
 * @author groep 03
 */
public interface State {
	/**
	 * Get the SubWindow title
	 */
	public String getTitle();
	
	/**
	 * Draw ViewParty
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewParty
	 * 			ViewParty that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewParty viewParty);
	
	/** 
	 * Draw ViewInvocationMessage
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewMessage
	 * 			viewMessage that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewInvocationMessage viewMessage);
	
	/** 
	 * Draw ViewResultMessage
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewMessage
	 * 			viewMessage that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewResultMessage viewMessage);
	
	/**
	 * Move a ViewComponent
	 * @param component
	 * 			Component that is going to be moved
	 * @param clickPosition
	 * 			The clicked position
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 */
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition);
	
	/**
	 * Check if there is a party position at the clicked position
	 * @param party
	 * 			ViewParty that is going to be checked
	 * @param clickPosition
	 * 			The clicked position
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @return  True if the party is positioned at the clicked position
	 * 			False if the party is not positioned at the clicked position
	 */
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition);
}