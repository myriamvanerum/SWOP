package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewLifeLine;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.components.ViewResultMessage;
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
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewParty viewParty) {
		viewParty.draw(g, viewParty.positionWindow(viewParty.getPositionSeq(), windowPosition));
        viewParty.getViewLifeLine().draw(g);
	}
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewInvocationMessage viewMessage) {
		drawMessage(g, windowPosition, viewMessage);
	}
	
	public void draw(Graphics2D g, Point2D windowPosition, ViewResultMessage viewMessage) {
		drawMessage(g, windowPosition, viewMessage);
	}
	
	public void drawMessage(Graphics2D g, Point2D windowPosition, ViewMessage viewMessage) {
		ViewLifeLine senderLifeline = viewMessage.getSender().getViewLifeLine();
		ViewLifeLine receiverLifeline = viewMessage.getReceiver().getViewLifeLine();
		int xSender = senderLifeline.getX();
		int xReceiver = receiverLifeline.getX();
		int y = (int) (viewMessage.getPositionSeq().getY() + windowPosition.getY());
				
		viewMessage.draw(g, xSender, xReceiver, y, y);
		viewMessage.drawActivationBar(g, xSender-5, xReceiver-5, y-5);
	}
	
	@Override
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		component.setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 30));
	}
	
	@Override
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition) {
		return party.checkCoordinates(clickPosition, party.getPositionSeq(), windowPosition);
	}
}
