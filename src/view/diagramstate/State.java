package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.components.ViewResultMessage;

public interface State {
	/**
	 * Get the SubWindow title
	 */
	public String getTitle();
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewParty viewParty);
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewInvocationMessage viewMessage);
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewResultMessage viewMessage);
	
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition);
	
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition);
}